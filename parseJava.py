"""
File used to parse Java 8 source code and create a call graph.
"""

import javalang
import networkx as nx
import matplotlib as mpl
mpl.use('TkAgg')  # Configuring matplotlib back-end
import matplotlib.pyplot as plt

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

def construct_call_graph(method_declarations):
    method_declaration_names = {x.name for x in method_declarations}

    method_calls = {}  # Methods name is key, set of methods name that method calls is value
    for method_declaration in method_declarations:
        called_methods = set() # initialize with empty set

        for method_expressions in method_declaration.body:  # The statements/expressions that make up a method
            expression = method_expressions.expression

            if isinstance(expression, javalang.tree.MethodInvocation):  # For each statement that invokes a method
                if expression.member in method_declaration_names:  # compare to names because member =/= javaMethodDeclaration
                    called_methods.add(expression.member)  # added only if method was defined by java_class
        method_calls[method_declaration.name] = called_methods
    return method_calls

with open("sampleJava.java") as java_file:
    java_code = java_file.read()
    tree = javalang.parse.parse(java_code)  # A CompilationUnit (root of AST)
    java_classes = tree.types  # Our sample only has one class

    for java_class in java_classes:  # Assumes class is not calling methods from other class
        declarations_list = java_class.body  # The declarations in each class as a list

        # Dictionary to store a list of declarations for each declaration type
        declarations_dict = construct_declarations_dictionary(declarations_list)
        method_declarations = declarations_dict["MethodDeclaration"]
        method_calls = construct_call_graph(method_declarations)

        print(declarations_dict)
        print(method_calls)
        visualize_call_graph(method_calls)
