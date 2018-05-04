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

def construct_class_dict(declarations, class_name):

    class_dict = {
                    "defined_methods": [],
                    "called_methods": {},
                    "properties": []
                 }

    declarations_dict = construct_declarations_dictionary(declarations)  # Intermediate data structure meant for parsing methods of a class
    method_declarations = declarations_dict["MethodDeclaration"]

    for method_declaration in method_declarations:

        # First add method to defined methods
        # TODO: Fill in modifier parameters
        # method_declaration attrs: ['documentation', 'modifiers', 'annotations', 'type_parameters', 'return_type', 'name', 'parameters', 'throws', 'body']
        # Method Initializer: def __init__(self, name, class_name, is_final, is_static, access_modifier, parameter_types):
        method = Method(method_declaration.name, class_name, False, False, AccessModifier.PUBLIC, [])
        class_dict["defined_methods"].append(method)

        # Now figure out which methods this method calls and add to called_methods
        class_dict["called_methods"][method.id] = set()
        for method_expressions in method_declaration.body:  # The statements/expressions that make up a method

            try:
                expression = method_expressions.expression

                if isinstance(expression, javalang.tree.MethodInvocation):  # For each statement that invokes a method
                    # if expression.member in method_declaration_names:  # compare to names because member =/= javaMethodDeclaration

                    # Debugging Help
                    # print(expression.member)
                    # print(expression.attrs)
                    # print(expression.qualifier)
                    # print(expression.selectors)
                    # print(expression.type_arguments)
                    # print(expression.arguments)
                    # TODO: We need to figure out how to add the method id here but we can't
                    # build it from the attributes of an expression
                    # We could instead use the method name but a method name isn't unique because different
                    # classes can have the same method name and methods can be overloaded with different arguments.
                    # The qualifier attribute is a string that tells us the variable name of the object we are calling the method on.
                    # We need to figure out the class of that object.
                    # Also, the type_arguments provided by javalang here are super generic.
                    # i.e. Cast, MemberReference, Literal, NumberReference.
                    # I'm not sure how to solve this.
                    # We probably need to do a pass where we build a data structure with all of the defined methods
                    # in our program and then after that, work on the call graph.
                    class_dict["called_methods"][method.id].add(expression.member)
            except AttributeError:
                pass
    return class_dict


for filename in glob.glob('StevenBreakout/*.java'):
    print(f'Processing {filename}')

    with open(filename) as java_file:
        java_code = java_file.read()
        tree = javalang.parse.parse(java_code)  # A CompilationUnit (root of AST)
        java_classes = tree.types # Our sample only has one class

        graph_dict = {}

        for java_class in java_classes:  # Assumes class is not calling methods from other class
            declarations_list = java_class.body  # The declarations in each class as a list

            class_dict = construct_class_dict(declarations_list, java_class.name)
            graph_dict[java_class.name] = class_dict

            print(graph_dict)
            visualize_call_graph(class_dict["called_methods"])
