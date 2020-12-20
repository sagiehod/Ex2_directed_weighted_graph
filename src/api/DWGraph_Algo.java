package api;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import gameClient.util.Point3D;
import com.google.gson.JsonParser;



public class DWGraph_Algo implements dw_graph_algorithms{

	private directed_weighted_graph WGraph;
	/**
	 * constructor
	 */
	public DWGraph_Algo() {
		WGraph=new DWGraph_DS();
	}
	/**
	 *  Init this set of algorithms on the parameter - graph.
	 *  @param g
	 */
	@Override
	public void init(directed_weighted_graph g) {
		this.WGraph=  g;
	}
	/**
	 * Return the underlying graph of which this class works.
	 * @return
	 */
	@Override
	public directed_weighted_graph getGraph() {
		return this.WGraph;
	}
	/**
	 * This function sends to the copy constructor and compute a deep copy of this graph
	 *  @return the copied graph.
	 */
	@Override
	public directed_weighted_graph copy() {
		directed_weighted_graph copyGraph= new DWGraph_DS (this.WGraph);
		return copyGraph;
	}

	//for isconnected
	Stack<node_data> stack=new Stack<node_data>();
	int sccCount=0;
	int id=0;

	/**
	 * This function checks if there is a valid path from EVREY node to each other node 
	 * ( Checks if the graph is connected)
	 * @return false if there more than one SccCount=Binding components.
	 */
	@Override
	public boolean isConnected() {
		if( WGraph==null||WGraph.nodeSize()==0||WGraph.nodeSize()==1) {
			return true;
		}
		init_nodes();
		stack=new Stack<node_data>();
		sccCount=0;
		id=0;
		for(node_data i: this.WGraph.getV()) {
			if(i.getTag()==0) {
				tarjan(i);
			}
		}
		System.out.println(sccCount);
		if(sccCount==1) return true;
		return false;
	}

	/**
	 * The tarjan algorithm takes a directed graph as input,
	 *  and produces a partition of the graph's vertices into the graph's strongly connected components.
	 *  Each vertex of the graph appears in exactly one of the strongly connected components.
	
	Any vertex that is not on a directed cycle forms a strongly connected component all by itself
	 */
	private void tarjan(node_data at) {

		//ids - > tag 
		//low -> weight 
		//on stack -> info "true" - on stack "" no on stack

		stack.push(at);
		at.setInfo("true");
		id++;
		at.setTag(id);
		at.setWeight(id);
		if(this.getGraph().getE(at.getKey())!=null) {
			for(edge_data edge:this.getGraph().getE(at.getKey())) {
				node_data to =this.getGraph().getNode(edge.getDest());
				if(to.getTag()==0) tarjan(to);
				if(to.getInfo().equals("true")) at.setWeight(Math.min(at.getWeight(), to.getWeight()));
			}
		}

		if(at.getTag() == at.getWeight()) {
			while(!stack.isEmpty()) {
				node_data node=stack.pop();
				//path
				node.setInfo("");
				node.setWeight(at.getTag());
				if(node==at) break;
			}
			sccCount++;
			//[num comp][path]
		}

	}
	/**
	 *  * @param src
	 * @param dest
	 * This function uses the Dijkstra's algorithm to find the shortest path between src and dest 
	 * and the function returns the weight of the dest which should be the lowest weight
	 *@return the length of the shortest path between src to dest
	 */

	@Override
	public double shortestPathDist(int src, int dest) {

		if(WGraph.getNode(src)==null||WGraph.getNode(dest)==null) 
			return-1;	

		if(src==dest) { 
			return 0.0;
		}
		this.dijkstra(src, dest);
		double d = WGraph.getNode(dest).getWeight();
		if(d==Double.POSITIVE_INFINITY) {

			return -1;
		}
		else 
		{
			return d;
		}
	}

	/**
	 this function uses the Dijkstra's algorithm to finds the shortest path between src and dest 
	 and returns a list of the nodes through which we passed.
	 This list contains the ancestors of the nodes through which we passed.
    The list was made by the hashmap that for each current node puts into the container the node that 
    brought us to the current node which is its parent node
	 @param src - start node
	 * @param dest - end (target) node
	 * @return 
	 * */
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		if(WGraph.getNode(src)==null||WGraph.getNode(dest)==null) 
			return null;

		List<node_data> ans=new ArrayList<node_data>();

		if(src==dest) {
			ans.add(WGraph.getNode(src));
			return ans;
		}
// a container for the nodes that in the shortest path
		Map<node_data,node_data> parentNodes = this.dijkstra(src, dest);
		node_data node = this.WGraph.getNode(dest);

		while(node != null) {

			ans.add(node);

			node = parentNodes.get(node);
		}
		//Checks if the node we wanted to reach is initialized as infinity 
		//so there is no way to reach it -then return null
		double d = WGraph.getNode(dest).getWeight();
		if(d==Double.POSITIVE_INFINITY) {
			return null;
		}
		Collections.reverse(ans);
		return ans;
	}



	// Initialize all the nodes as INFINITE
	private void init_nodes() {
		for (node_data i :this.WGraph.getV()) {
			i.setTag(0);
			i.setWeight(Double.POSITIVE_INFINITY);
			i.setInfo("");
		}
	}

	/**
	 * 
	 * Dijkstra's algorithm: An algorithm for finding the shortest paths between nodes in a graph
he gets 2 nodes- src and -dest should get from the src node to the dest node and go through the nodes with the lowest weight.
We will first initialize all the weights of the nodes to infinity so that we know which node we have not yet updated,
 and then we set a priority queue that will contain the nodes we will visit and update their weights.
In addition, we set the Hashmap to initialize all nodes in false.
Each node he visited will be marked as a visit (correct), 
and that way we will know if we visited this node or not, 
and finally, if there is such a node, then he is not connected to the other node and he will remain marked as false.
.
In the priority queue, we enter the first node and initialize its weight to 0,
 and all the other nodes in the graph are initialized to infinity. 
 For the current junction,we will include all its neighbors and update their temporary weights.
The weight of each node is updated according to the parent weight of that node plus the temporary distance between them which is the weight on the edge.
Then the same node we started with becomes the father of this node and leaves the queue, 
it is marked as one we have already visited and we will not return to it again. 
Each of the introduced neighbors treats him the same way:
 putting his neighbors in line and updating their weights.
so if one of the neighbors is already updated with weight because we reached it through another father-node, then we will check through which neighbor that node will have the lowest weight, then we will keep the lower weight. We will take the node out of the queue and return it with the updated weight. To the same junction is also updated the new father through which we reached a junction with a lower weight.
  And so for each node up to the node, we set to reach in the graph.
	 * @param src
	 * @param dest
	 * This function uses the Dijkstra's algorithm to find the shortest path between src and dest 
	 * and the function returns the weight of the dest which should be the lowest weight
	 *@return parentNodes
	 */
	private  Map<node_data, node_data> dijkstra (int src , int dest) {
		init_nodes();
		PriorityQueue<node_data> queue = new PriorityQueue<node_data>(new Comparator<node_data>() {
			@Override
			public int compare(node_data o1, node_data o2) {
				return - Double.compare(o2.getWeight(),o1.getWeight());
			}
		});

		Map<node_data, node_data> parentNodes = new HashMap<node_data, node_data>();

		node_data start = this.WGraph.getNode(src);

		queue.add(start); 
		start.setWeight(0);

		while((!queue.isEmpty())){
			node_data u=queue.poll();

			for (edge_data e :  WGraph.getE(u.getKey())){
				node_data v= this.WGraph.getNode(e.getDest());
				double dist = u.getWeight()+e.getWeight();
				if (dist < v.getWeight()) {       
					v.setWeight(dist);
					parentNodes.put(v, u);
					queue.remove(v);
					queue.add(v);
				}
			}
		}
		return parentNodes;
	}

	/**
	 * Saves this weighted (directed) graph to the given
	 * file name - in JSON format
	 * @param file - the file name (may include a relative path).
	 * @return true - iff the file was successfully saved
	 */
	@Override
	public boolean save(String file) {
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
			FileOutputStream out= new FileOutputStream(file);
			JsonWriter write = new JsonWriter(new OutputStreamWriter(out)); 

			List<JsonObject> vertexs=new ArrayList<>();
			List<JsonObject> edges=new ArrayList<>();
			//{"pos":"35.19381366747377,32.102419275630254,0.0","id":16}
			for(node_data i: this.WGraph.getV()) {
				JsonObject v=new JsonObject();
				v.addProperty("pos",i.getLocation().toString());
				v.addProperty("id",i.getKey());
				vertexs.add(v);
				//{"src":0,"w":1.3118716362419698,"dest":16}
				if(this.WGraph.getE(i.getKey())!=null) {
					for(edge_data e:this.WGraph.getE(i.getKey())) {
						JsonObject edge=new JsonObject();
						edge.addProperty("src",e.getSrc());
						edge.addProperty("w",e.getWeight());
						edge.addProperty("dest",e.getDest());
						edges.add(edge);
					}
				}
			}

			JsonObject _dgrap =new JsonObject ();
			_dgrap.add("Edges", gson.toJsonTree(edges.toArray()));
			_dgrap.add("Nodes",  gson.toJsonTree(vertexs.toArray()));

			gson.toJson(_dgrap,write);
			write.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * This method load a graph to this graph algorithm.
	 * if the file was successfully loaded - the underlying graph
	 * of this class will be changed (to the loaded one), in case the
	 * graph was not loaded the original graph should remain "as is".
	 * @param file - file name of JSON file
	 * @return true - iff the graph was successfully loaded.
	 */
	@Override
	public boolean load(String file) {
		try {

			FileInputStream input = new FileInputStream(file);

			JsonReader read = new JsonReader(new InputStreamReader(input));
			JsonObject elements = JsonParser.parseReader(read).getAsJsonObject(); 

			directed_weighted_graph _g = new DWGraph_DS();

			for(JsonElement i: elements.getAsJsonArray("Nodes")) {
				int id = i.getAsJsonObject().get("id").getAsInt();
				node_data node=new NodeData(id);
				String[] pos= i.getAsJsonObject().get("pos").getAsString().split(",");
				node.setLocation(new Point3D(Double.parseDouble(pos[0]),Double.parseDouble(pos[0]),Double.parseDouble(pos[0])));	 
				_g.addNode(node);
			}
			for(JsonElement i: elements.getAsJsonArray("Edges")) {
				_g.connect(i.getAsJsonObject().get("src").getAsInt(), i.getAsJsonObject().get("dest").getAsInt(), i.getAsJsonObject().get("w").getAsDouble());		 
			}
			init(_g);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}