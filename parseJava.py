"""
File used to parse Java 8 source code and create a call graph.
"""

import javalang
import networkx as nx
import matplotlib.pyplot as plt

def visualize_call_graph(call_graph):
    G = nx.DiGraph()

    for callee_method_name, called_methods in call_graph.items():
        G.add_node(callee_method_name)
        for called_method_name in called_methods:
            G.add_edge(callee_method_name, called_method_name)

    pos = nx.spring_layout(G)
    nx.draw(G, pos, font_size=16, with_labels=False)
    for p in pos:  # Raise text positions
        pos[p][1] += 0.1
    nx.draw_networkx_labels(G, pos)
    plt.show()

with open("sampleJava.java") as javaFile:
    javaCode = javaFile.read()
    tree = javalang.parse.parse(javaCode)  # A CompilationUnit (root of AST)
    javaClasses = tree.types  # Our sample only has one class

    for javaClass in javaClasses:  # Assumes class is not calling methods from other class
        javaMethodDeclarations = javaClass.body  # The methods declared in each class
        javaMethodDeclarationNames = {x.name for x in javaMethodDeclarations}
        javaMethodCalls = {}  # Methods name is key, set of methods name that method calls is value

        for javaMethodDeclaration in javaMethodDeclarations:
            definedMethodsCalled = set()

            for javaStatementExpression in javaMethodDeclaration.body:  # The statements/expressions that make up a method
                javaExpression = javaStatementExpression.expression

                if isinstance(javaExpression, javalang.tree.MethodInvocation):  # For each statement that invokes a method
                    if javaExpression.member in javaMethodDeclarationNames:  # compare to names because member =/= javaMethodDeclaration
                        definedMethodsCalled.add(javaExpression.member)  # added only if method was defined by class

            javaMethodCalls[javaMethodDeclaration.name] = definedMethodsCalled

        print(javaMethodCalls)
        visualize_call_graph(javaMethodCalls)
