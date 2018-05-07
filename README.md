# Java Call Graph Visualizer

This project is a static analysis tool to create and visualize a call graph from Java code. The output of this tool is a directed graph with each node being a method and each edge indicating that one method can potentially call another.  Developers can use this tool to visualize the relationships between different classes and methods in a Java program before the code is run.

### How to Use
To use this tool, simply clone the repository and run `pip install` to gather the neccessary dependencies. Then run the parseJava.py script. We've included some example Java programs that can be used. To specify which program to run the tool on, use the -d command line argument. For example, to run on Steven's COMP 124 Breakout program, run `python3 parseJava.py -d StevenBreakout`. For Danny's Course Matching Algorithms project, run `parseJava.py -d DannyAlgorithmsProject`. You also may run the tool on other Java code by specifying the name of the directory containing the code. It is okay for files to be nested within this directory. 

The output of our program is a gexf file. This is a file used by the network visualization software Gephi to represent a graph. To view a gexf file, install Gephi and then open the gexf file with file -> open. To make the network more legible, we recommend running the Force Atlas layout algorithm followed by a series of Expansions until nodes are fairly spread out. You can press the T on the bottom menu to include node labels and use the slider to adjust their size.

### Example Output

#### Steven's COMP 124 Breakout Program
![Breakout](https://github.com/dochoa1/call-graph/blob/master/ExampleOutput/StevenBreakoutGraph.png)

#### Danny's COMP 221 Course Match Algorithms Program
![CourseMatch](https://github.com/dochoa1/call-graph/blob/master/ExampleOutput/DannyAlgorithmsProjectGraph.png)

### Methodology

Our methodology was heavily influenced by [this](https://ben-holland.com/call-graph-construction-algorithms-explained/) excellent article explaining different call graph construction algorithms. We decided to create a call graph that is sound but not necessarily precise. This means that for every potential method call in a program, our call graph will always include an edge from the callee method to the called method but that there may be edges included where it isn't actually possible for the source node method to call the target node method. This approach allows for a simpler call graph construction implementation and means that our tool can work on programs that don't include all java files where called methods are defined. 

### Limitations

The greatest limitations of our program relate to a lack of precision. In Java, the combination of class name, method name, and parameter types estabishes uniqueness (Java has method overloading). When our program encounters a method call, it searches for methods defined with the same name but doesn't consider the class the method belongs to or its parameter types. Thus, multiple edges may be added to the call graph for a single method call. A more complicated version of this program could work to figure out what class the method is being called on and what the parameter types are, but we were unable to confidently figure this out in all classes using the javalang parsing library. To ensure that our call graph is sound, we include an edge to all methods in the program with the same name. 

Our current network visualization is quite raw. With more time, we would experiment more with Gephi's network layout and clustering algorithms to arrange our network in a more useful way. Our gexf files included unique node colors for each class in a program, but we have been unable to figure out how why Gephi will not display those colors. 




