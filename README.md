# Java Call Graph Visualizer

This project is a static analysis tool to create and visualize a call graph from Java code. The output of this tool is a directed graph with each node being a method and each edge indicating that one method can potentially call another.  Developers can use this tool to visualize the relationships between different classes and methods in a Java program before the code is run.

### How to Use
To use this tool, simply clone this repository and run `pip install` to gather the neccessary dependencies. Then run the parseJava.py script. We've included some example Java programs that can be used. To specify which program to run the tool on, use the -d command line argument. For example, to run on Steven's COMP 124 Breakout program, run `python3 parseJava.py -d StevenBreakout`. For Danny's Course Matching Algorithms project, run `parseJava.py -d DannyAlgorithmsProject`. You also may run the tool on other Java code by specifying the name of the directory containing the code. It is okay for files to be nested within this directory. 

The output of our program is a gexf file. This is a file used by the network visualization software Gephi to represent a graph. To view a gexf file, install Gephi and then open the gexf file with file -> open. To make the network more legible, we recommend running the Force Atlas layout algorithm followed by a series of Expansions until nodes are fairly spread out. You can press the T on the bottom menu to include node labels and use the slider to adjust their size.

### Example Output





##### Steven's COMP 124 Breakout Program





### Methodology

Reachability Analysis

Sound call graph

Not aiming for precision


### Limitations

Just connect edge based on method name, don't consider parameters so overloaded methods will include an extra edge, 
If multiple classes have the same method name, they will have an extra edge as well. 

Visualization is quite raw. More work in with network in Gephi would be helpful to make visualization more useful. Colors should be included in the gephi graph but it's not working. 




