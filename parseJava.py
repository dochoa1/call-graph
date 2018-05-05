"""
File used to parse Java 8 source code and create a call graph.
"""

import javalang
import networkx as nx
import matplotlib as mpl
from Method import Method
from AccessModifier import AccessModifier
mpl.use('TkAgg')  # Configuring matplotlib back-end
import matplotlib.pyplot as plt
import glob

def visualize_call_graph(call_graph):
    """Takes a call graph and visualizes using matplotlib."""
    G = nx.DiGraph()  # Directed graph

    for callee_method_name, called_methods in call_graph.items():
        G.add_node(callee_method_name)
        for called_method_name in called_methods:
            G.add_edge(callee_method_name, called_method_name)

    pos = nx.spring_layout(G)
    nx.draw(G, pos, font_size=16, with_labels=False) # place labels seperately above node
    for p in pos:  # Raise text positions
        pos[p][1] += 0.1
    nx.draw_networkx_labels(G, pos)
    plt.show()

def construct_declarations_dictionary(declarations):
    """Helper method to convert a list of declarations into dictionary that
    groups by type of declaration."""

    declarations_dict = {
                        "MethodDeclaration": [],
                        "FieldDeclaration": []
                        }

    for declaration in declarations:
        if isinstance(declaration, javalang.tree.MethodDeclaration):
            declarations_dict["MethodDeclaration"].append(declaration)
        elif isinstance(declaration, javalang.tree.FieldDeclaration):
            declarations_dict["FieldDeclaration"].append(declaration)
    return declarations_dict

def get_methods_ids_that_match_name(name, graph_dict):
    """Takes a method name and a graph_dict and returns a list of method_ids in the graph_dict
    that match the method name."""
    # TODO: This isn't very efficient
    matched_method_ids = []
    for class_name, class_dict in graph_dict.items():
        for method in class_dict["defined_methods"]:
            if name == method.name:
                matched_method_ids.append(method.id)
    return matched_method_ids

def create_defined_methods_and_fields_dict(java_classes):
    """Takes a list of classes and creates a graph_dict that includes the methods defined in each class."""

    graph_dict = {}
    for java_class in java_classes:  # Assumes class is not calling methods from other class
        class_name = java_class.name

        class_dict = {
                        "defined_methods": [],
                        "called_methods" : {}
                        # "properties": []
                     }

        declarations_list = java_class.body  # The declarations in each class as a list
        declarations_dict = construct_declarations_dictionary(declarations_list)  # Intermediate data structure meant for parsing methods of a class
        method_declarations = declarations_dict["MethodDeclaration"]

        for method_declaration in method_declarations:

            # First add method to defined methods
            # TODO: Fill in modifier parameters
            # method_declaration attrs: ['documentation', 'modifiers', 'annotations', 'type_parameters', 'return_type', 'name', 'parameters', 'throws', 'body']
            # Method Initializer: def __init__(self, name, class_name, is_final, is_static, access_modifier, parameter_types):
            method = Method(method_declaration.name, class_name, False, False, AccessModifier.PUBLIC, [])
            class_dict["defined_methods"].append(method)

        # TODO: add properties now?

        graph_dict[java_class.name] = class_dict
        return graph_dict

def construct_class_dict(declarations, class_name, graph_dict):
    """Parses method declarations in a class and adds called methods to the graph_dict.
    SIDE EFFECT: modifies graph_dict."""
    class_dict = graph_dict[class_name]

    declarations_dict = construct_declarations_dictionary(declarations)  # Intermediate data structure meant for parsing methods of a class
    method_declarations = declarations_dict["MethodDeclaration"]

    for method_declaration in method_declarations:
        # constructing a Method object just to use it's id.
        # TODO: make id a method of this class and then call it here instead of constructing a method
        temp_method = Method(method_declaration.name, class_name, False, False, AccessModifier.PUBLIC, [])

        print(f'++++> {method_declaration.name} is declared.')

        # Now figure out which methods this method calls and add to called_methods
        class_dict["called_methods"][temp_method.id] = set()

        # TODO: Iterating over the method_declaration.body is insufficient here.
        # For instance, the run method has one expression which is a while statement.
        # We need to have some kind of recursive parsing here to read what is inside the loop.
        for method_expression in method_declaration.body:  # The statements/expressions that make up a method

            try:
                expression = method_expression.expression
                if isinstance(expression, javalang.tree.MethodInvocation):  # For each statement that invokes a method

                    # Okay,the goal for now is to just construct a sound call graph (not a precise one), so let's just add an
                    # edge to all of the possible options. We can add CHA later to restrict how many edges are added
                    matched_method_ids = get_methods_ids_that_match_name(expression.member, graph_dict)

                    if len(matched_method_ids) == 0:  # Method may have been defined in super class. We are constructing a partial graph
                        # FIXME: If we add parameters to ID, we'll have a problem here
                        class_dict["called_methods"][temp_method.id].add(class_name + '.' + expression.member)

                    for matched_method_id in matched_method_ids:
                        print("-----> Adding ", matched_method_id)
                        class_dict["called_methods"][temp_method.id].add(matched_method_id)
            except AttributeError:
                continue


for filename in glob.glob('StevenBreakout/*.java'):
    print(f'Processing {filename}')

    with open(filename) as java_file:
        java_code = java_file.read()
        tree = javalang.parse.parse(java_code)  # A CompilationUnit (root of AST)
        java_classes = tree.types # Our sample only has one class

        graph_dict = create_defined_methods_and_fields_dict(java_classes)

        for java_class in java_classes:  # Assumes class is not calling methods from other class
            declarations_list = java_class.body  # The declarations in each class as a list

            construct_class_dict(declarations_list, java_class.name, graph_dict)  # TODO: Rename method

            print(graph_dict)
            visualize_call_graph(graph_dict[java_class.name]["called_methods"])
