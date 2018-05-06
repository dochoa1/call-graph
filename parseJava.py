"""
File used to parse Java 8 source code and create a call graph.
"""

import javalang
import networkx as nx
import matplotlib as mpl
from Method import Method
mpl.use('TkAgg')  # Configuring matplotlib back-end
import matplotlib.pyplot as plt
import glob
from collections import Iterable as Iterable
from sys import argv


class CallGraph:

    # ---- Visualization ----
    def visualize_call_graph(self, G):
        """Takes a networkx graph and visualizes using matplotlib."""

        pos = nx.spring_layout(G)
        nx.draw(G, pos, font_size=16, with_labels=False) # place labels seperately above node
        for p in pos:  # Raise text positions
            pos[p][1] += 0.1
        nx.draw_networkx_labels(G, pos)
        plt.show()


    # ------------------------------------------------------------------------------
    # ----NetworkX ----

    def create_networkx_graph(self, graph_dict):
        G = nx.DiGraph()  # Directed graph

        for java_class, class_dict in graph_dict.items():
            call_graph = class_dict["called_methods"]
            for callee_method_name, called_methods in call_graph.items():
                G.add_node(callee_method_name)
                for called_method_name in called_methods:
                    G.add_edge(callee_method_name, called_method_name)
        return G


    def construct_method_declarations_list(self, declarations):
        """Helper method to convert a list of declarations into dictionary that
        groups by type of declaration."""

        method_declarations = []
        for declaration in declarations:
            if isinstance(declaration, javalang.tree.MethodDeclaration):
                method_declarations.append(declaration)
        return method_declarations

    # ------------------------------------------------------------------------------
    # ---- Call Graph Helper Methods ----

    def get_methods_ids_that_match_name(self, name, graph_dict):
        """Takes a method name and a graph_dict and returns a list of method_ids in the graph_dict
        that match the method name."""
        # TODO: This isn't very efficient
        matched_method_ids = []
        for class_name, class_dict in graph_dict.items():
            for method in class_dict["defined_methods"]:
                if name == method.name:
                    matched_method_ids.append(method.id)
        return matched_method_ids


    def create_method_id(self, class_name, name):
        return class_name + '.' + name

    # ------------------------------------------------------------------------------
    # ---- Utilities ----

    def flatten_attributes(self, l):
        """Utility function to because certain Statements are blocks with a list of statements as their attributes"""
        flattened_list = []
        for elem in l:
            if isinstance(elem, Iterable) and not isinstance(elem, (str, bytes)):
                flattened_list.extend(elem)
            elif elem is not None:
                flattened_list.append(elem)
        return flattened_list


    def getopts(self, argv):
        """Collect command-line options in a dictionary. From https://gist.github.com/dideler/2395703"""
        opts = {}  # Empty dictionary to store key-value pairs.
        while argv:  # While there are arguments left to parse...
            if argv[0][0] == '-':  # Found a "-name value" pair.
                opts[argv[0]] = argv[1]  # Add key and value to the dictionary.
            argv = argv[1:]  # Reduce the argument list by copying it starting from index 1.
        return opts

    # ------------------------------------------------------------------------------
    # ---- Graph Dictionary Construction ----

    def create_defined_methods_and_fields_dict(self, java_classes):
        """Takes a list of classes and creates a graph_dict that includes the methods defined in each class."""

        graph_dict = {}
        for java_class in java_classes:  # Assumes class is not calling methods from other class
            class_name = java_class.name

            class_dict = {
                            "defined_methods": [],
                            "called_methods" : {}
                         }

            declarations_list = java_class.body  # The declarations in each class as a list

            method_declarations = self.construct_method_declarations_list(declarations_list)  # Intermediate data structure meant for parsing methods of a class
            for method_declaration in method_declarations:

                method_id = self.create_method_id(class_name, method_declaration.name)
                method = Method(method_id, method_declaration.name, class_name)
                class_dict["defined_methods"].append(method)

            graph_dict[java_class.name] = class_dict
        return graph_dict


    def construct_class_dict(self, declarations, class_name, graph_dict):
        """Parses method declarations in a class and adds called methods to the graph_dict.
        SIDE EFFECT: modifies graph_dict."""
        class_dict = graph_dict[class_name]

        method_declarations = self.construct_method_declarations_list(declarations)  # Intermediate data structure meant for parsing methods of a class
        for method_declaration in method_declarations:
            method_id = self.create_method_id(class_name, method_declaration.name)

            print(f'++++++++++++++++++++++++++++++++++++++++++++++++++++> {method_declaration.name} is declared.')

            # Now figure out which methods this method calls and add to called_methods
            class_dict["called_methods"][method_id] = self.construct_called_methods(class_name, graph_dict, set(), method_declaration.body)


    recursive_statements = {javalang.tree.WhileStatement, javalang.tree.BlockStatement, javalang.tree.IfStatement, javalang.tree.BinaryOperation, javalang.tree.Creator}


    def add_method(self, class_name, expression, graph_dict):
        # Okay,the goal for now is to just construct a sound call graph (not a precise one), so let's just add an
        # edge to all of the possible options. We can add CHA later to restrict how many edges are added
        methods = set()

        matched_method_ids = self.get_methods_ids_that_match_name(expression.member, graph_dict)

        if len(matched_method_ids) == 0:  # Method may have been defined in super class. We are constructing a partial graph
            # FIXME: If we add parameters to ID, we'll have a problem here
            methods.add(class_name + '.' + expression.member)

        for matched_method_id in matched_method_ids:
            print("-----> Adding ", matched_method_id)
            methods.add(matched_method_id)
        return methods


    def construct_called_methods(self, class_name, graph_dict, called_methods, body):
        if body == None:
            return called_methods

        for method_expression in body:  # The statements/expressions that make up a method
            try:
                expression = method_expression.expression
                if isinstance(expression, javalang.tree.MethodInvocation):  # For each statement that invokes a method
                    called_methods.update(self.add_method(class_name, expression, graph_dict))

                if isinstance(expression, javalang.tree.Assignment):
                    statement_attributes = [getattr(expression, attr) for attr in expression.attrs]
                    called_methods.update(self.construct_called_methods(class_name, graph_dict,
                                                                    called_methods, statement_attributes))

            except AttributeError:
                if isinstance(method_expression, javalang.tree.MethodInvocation):  # Sometimes expression is a MethodInvocation itself
                    called_methods.update(self.add_method(class_name, method_expression, graph_dict))

                if isinstance(method_expression, tuple(self.recursive_statements)):
                    print("Recursing into: {}".format(method_expression))

                    statement_attributes = [getattr(method_expression, attr) for attr in method_expression.attrs]

                    if isinstance(method_expression, (javalang.tree.WhileStatement, javalang.tree.IfStatement, javalang.tree.BinaryOperation)):
                        called_methods.update(self.construct_called_methods(class_name, graph_dict,
                                                                        called_methods, statement_attributes))

                    elif isinstance(method_expression, (javalang.tree.BlockStatement, javalang.tree.Creator)):
                        called_methods.update(self.construct_called_methods(class_name, graph_dict,
                                                                        called_methods, self.flatten_attributes(statement_attributes)))
        return called_methods

    # ------------------------------------------------------------------------------
    # ---- Main ----

    def main(self):

        parent_directory = "StevenBreakout"
        myargs = self.getopts(argv)
        if '-d' in myargs:
            parent_directory = myargs['-d']


        java_classes = []
        for filename in glob.glob(f'{parent_directory}/**/*.java', recursive = True):
            print(f'Parsing {filename}')

            with open(filename) as java_file:
                java_code = java_file.read()
                try:
                    tree = javalang.parse.parse(java_code)  # A CompilationUnit (root of AST)
                    java_classes.extend(tree.types)
                except:
                    continue

        graph_dict = self.create_defined_methods_and_fields_dict(java_classes)

        for java_class in java_classes:
            declarations_list = java_class.body  # The declarations in each class as a list
            self.construct_class_dict(declarations_list, java_class.name, graph_dict)  # TODO: Rename method

        print(f'Graph Dictionary {graph_dict}')
        G = self.create_networkx_graph(graph_dict)
        nx.write_gexf(G, f"gephiGraphs/{parent_directory}_call_graph.gexf")
        self.visualize_call_graph(G)

    # ------------------------------------------------------------------------------

if __name__ == '__main__':
    call_graph = CallGraph()
    call_graph.main()
