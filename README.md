## Ex2_directed_weighted_graph  

# pokemon game :    ![image](https://www.altoonalibrary.org/wp-content/uploads/2019/08/pokemon-845x321.jpg)



In this project, we have developed a system that enables the collection of geographical information and the presentation of information in graphical tools.

We built a Pokemon game that works on the graph.
We have developed a game logic in which a group of agents has to perform tasks (capturing Pokemon) by moving along the graph for the purpose towards the side.
The object of the game is to catch all the Pokemon in the shortest time possible.
At each stage the Pokemon are placed at random, for each content we plan movement and placement so that in the allotted time he can eat as many Pokemon as possible.
After the explanation it is divided into types of Pokemon, by is their value from the lowest to the highest which is the rare Pokemon. This is basically the points of each agent.
Pokemon are represented by images of Pokemon.
Agents are represented by images of Pokemon Fire.
There are 0-23 stages, each with a different amount of Pokemon and agents,
Each time an agent catches the Pokemon, the agent's speed increases so he can get to the next Pokemon faster by crossing the ribs between the nodes.
And accumulate more points.
• Automatic management - our algorithm decides on the agents locations and transfers them to Pokemon in order to get as many points as possible. 
The algorithm finds the nearest Pokemon with the highest value and moves there - according to dijkstra's algorithm.
In this project we received data from a server (jar file) on which the game is performed.
Obtaining the information was based on strings represented as JSON.

## The Game :
[![Watch the video](https://github.com/sagiehod/Ex2_directed_weighted_graph/blob/main/data/ezgif.com-gif-maker%20(1).gif)]




## Description the packages and classes
Our packages and classes:
This project has 2 parts.
The first part - api represents a collection of algorithms on a deliberate weighted graph. Which is the basis for creating the graph, the ribs, the nodes, the connections between them, finding the shortest path in the graph, checking if the graph is a link and more.
The second part is a game client, where we created the game, created the moves of capturing the Pokemon, placed them on the graph, created the gui of the graph display, the music, the images and the entry into the game.
# Packages:
# - Api packege -
In this part of the project we have 7 departments that we implemented and another 6 departments from which we inherited the functions.
### The departments we have implemented and used are:                                   

|         Name       |    Description   |                                                                                                                                 
| ------------------ | ----------------       |
|  NodeData          |  In this class we implemented operations at a node (vertex) in a weighted(directional) graph. (Location, weight of each node) |                                    
|  EdgeData          | In this class we performed operations on the ribs in a rib graph composed of src and dest and it has weight.                  |                        
|  DWGraph_DS        |  We created the hashmap within the hashmap which first contains the nodes in the graph and each node has the shampoo that contains the list of neighbors - the ribs they are connected to along with the weight of each edge. |                
|  DWGraph_Algo      |  We created a number of algorithms for several methods: we checked graphs, found the shortest route, loaded and saved a json file  |                                      
|  Elocation         |    This class represents a position on the graph (a relative position on an edge - between two consecutive nodes).|
| Geo_location       |  This interface represents a geo location <x,y,z>, aka Point3D   |                                  
|  GameSrevice       |  This interface represents the main functionality of the game-server, allowing a User the following:, getInfo, getGraph, getAgents getPokemon, JSON format   |
                                   

## In the DWGraph_DS class:
We created the shampoo within the shampoo that will contain the vertices of the graph along with their ribs and weight.
The first shampoo contains the vertices that each vertex actually has the shampoo (which is the second) that contains their edge data neighbors. That is, this side is 2 vertices that are connected together with the distance between them. And so you can reach every vertex in the graph and its neighbors.
A get edge function which we will use to get a side from the graph.
Connect function - connected 2 neighboring vertices src and dest with the direction of the trajectory and formed a side from src to, dest together with the weight of the side which is the distance between them.
Finally, the goal is to create a weighted and directed graph consisting of nodes and ribs connecting them. And more in this class we have created functions that add and delete nodes and ribs with their weights and with the distance between the different nodes. And functions that count the number of ribs and nodes found in the chart as well as the number of operations we smoked in the graph. And also functions that create a collection of all vertices.

## In the DWGraph_Algo class:
### we used 2 algorithms:
1) Tarjan for checking the bond tying. The method is connected
2) Dijkstra - to find the shortest route in the graph, and to return the list of sides in the shortest route in the graph. In methods: shortestpathDist, shortestpath

### Using the tarjan algorithm:
Nodes are placed on a pile in the order of their visit.
 When the first depth search recursively visits the v node and its descendants, not all of these nodes are necessarily sucked out of the pile when this recursive call returns.
 The essential unchanging characteristic is that a node remains in a stack after its visit if and only if there is a path in the input graph from it to some node earlier in the stack.
 In other words, it means that in DFS a node is only removed from the stack after all its connected paths have been crossed. 
 When the DFS goes back it will remove the nodes in a single path and return to the root to start a new path.

### Using the dijkstra algorithm:
It gets 2 nodes- src and dest should go from the src node to the destination node and go through the nodes with the lowest weight.
- The algorithm works as follows: 
First we will initialize all the weights of the nodes to infinity so that we know which node we have not yet updated, 
 and then we set a priority queue that will contain the nodes we will visit and update their weights.
In addition, we created the parentNodes shamp that will eventually contain the updated nodes through which we passed the shortest trajectory in the graph from the vertex src and dest.
We enter the first node and initialize its weight to 0, and all the other nodes in the graph are initialized to infinity. 
The current junction will include all of its neighbors and will update its temporary weights.
The weight of each node is updated according to the parent weight of that node plus the temporary distance between them which is the weight at the end.
Then the same junction we started with becomes the father of this junction and leaves the queue, it is already marked that we have already visited it and we will not return to it again.
Each of the neighbors presented treats him in the same way:
Put his neighbors in line and update their weights.
Each node can have several neighbors and then also some fathers through which they come, so if one of the neighbors is already updated in weight because we reached it through another parent node, we will check through which neighbor it will be the lowest weight node, then we will keep the lower weight.
We will remove the node from the queue and return it with the updated weight. And we will put it in the shampoo .parentNodes for the same node,
the new parent was also updated, through which we reached a node with a lower weight. And so for each node up to the node, we set to reach the graph.

### The methods we practiced in this department:

- Copy: In this class we created a deep copy of the weighted graph so we could     duplicate the graph.   
- Get grap- Return the underlying graph of which this class works-
- Init: Init the graph on which this set of algorithms operates on
- is connected: This function checks if there is a valid path from EVREY node to each other node ( Checks if the graph is connected) 
- shortestpathDist : For a given source node in the graph, use the Dixtra algorithm and find the shortest path between src and dest and the function returns the target weight which should be the lowest continuation.
- shortestpath: very short route for a given node in a graph, use the Dixtr algorithm The algorithm finds the shortest path between src and dest and returns a list of the nodes through which we passed. This list contains the ancestors of the nodes through which we passed.
- Save: Saves this weighted (directed) graph to the given file name - in JSON format-
- Load- This method load a graph to this graph algorithm.-


![picture](https://www.techiedelight.com/wp-content/uploads/weighted-edges.png)
  
# -	Game client packege -
The second part of the project mainly deals with the creation of the game:
We have 8 departments:
 Algorithms:
 
|         Name       |    Description   |                                                                                                                                 
| ------------------ | ----------------       |
|  Arena          | A department that consolidates all the data of the game: graph, Pokemon-collection, agents-collection, and then it has the ability to make a set of all this data and bring it to us |                                    
|  CL_Agent          | Receives from the server a collection of agents with all their data and converts them using json                 |                        
| CL_Pokemon        | Brings all the Pokemon with all their features, position, value, getLocation and more |                
|  Ex2      | This is the main class, where we implemented functions and algorithms for the course of the game, positioning the agents for the first time and moving them on the graph, choosing the path of the agents to the Pokemon.  |                                      
|  Game_Frame   | In this class we used for the graph display, we created the display window, the time left for the game and the game stage. We used this class because in a framework there are options and things that are not in a panel and vice versa |
|  Game_Panel       |  This class is the game view - in which we implemented functions of the graph display, the game background, the agent Pokemon, the points and the time left.  |                                  
| login_gui  | In this class we created the user login by id and select the game stage
|  music       |  In this class we created background music at the entrance to the game and then the music changes during the game.   |
                                   
 
 
 
The departments we have implemented and used are:
Extension to some of the departments:

## In the Ex2 class:
 This class is the "main" method that runs the whole project. In order to start playing the game you must pass this lesson.
This class has several algorithms used during the game such as: positioning and moving the automatic agent, creating the elements and more.
In this department we implemented the methods:
- run: that runs the game, gets the graph, the Pokemon, the agents, and calls the other functions that the game will run and runs like: move agent, music, time left, points and more.
- move agent-: 
In this method we created the displacement of the agents and their arrival in the Pokemon by calculations and using the shortestpath function so that each agent catches as many Pokemon as possible in the allotted time and reaches as many points as he can.
agentsFirstPlace-: We created an agent placement function the first time it calculates all the possible options that agents have and their score from each choice of place, and it implements the getVchooseK function

## In the Game_Panel class:   
 Displays the game in JAVA, we implemented methods of: graph display, Pokemon images and their display and agents as an image along with their score, graph background creation, game time remaining, points, and what stage of the game is at.
 drawGraph, drawPokemons, drawAgants and more.

# - package tests - : June 5
We created a JUNIT 5 test (using the JUNIT 5 version).
In this package we have created 2 classes of tests:
 - DWGraph_DS,
 - DWGraph_Algo
 
 # - Sources - 
 - https://www.youtube.com/watch?v=TyWtx7q2D7Y Explanation of the tarjan algorithm.
 - https://www.coursera.org/lecture/advanced-data-structures/core-dijkstras-algorithm-2ctyF Explanation of the dijkstra algorithm.


An explanation of the game, the solution idea and the expansion of each package in the project can be found on WIKI.
Example of stage 10 in the game:
Implements the "graph" of the interface and represents a deliberately weighted graph.
The interface has a routing system or communication network - and is supposed to support a large number of nodes (over 100,000).
The application is based on efficient compact representation

