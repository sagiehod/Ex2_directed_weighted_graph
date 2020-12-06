package api;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;



public class DWGraph_Algo implements dw_graph_algorithms{
	private directed_weighted_graph WGraph;

	/**
	 * constructor
	 */
	 public DWGraph_Algo() {
				WGraph=new DWGraph_DS();
			}

	@Override
	public void init(directed_weighted_graph g) {
		this.WGraph=  g;
	}

	@Override
	public directed_weighted_graph getGraph() {
		return this.WGraph;
	}
	@Override
	public directed_weighted_graph copy() {
		directed_weighted_graph copyGraph= new DWGraph_DS (this.WGraph);
		return copyGraph;
	}
	 
		private	HashMap<Integer, Boolean> Visited_graph = new HashMap<Integer, Boolean>();
		private void BfsForisConnected (node_data start) {
			// Bfs algorithm using LinkedList of Integer type 
			LinkedList<Integer> queue = new LinkedList<Integer>(); 
			//create a container- Visited_graph Which contains the key and whether we visited it or not
			Visited_graph = new HashMap<Integer, Boolean>();
			for(node_data Node : WGraph.getV()) {

				//We initialized the container to false
				// so we know we have already visited these nodes
				Visited_graph.put(Node.getKey(), false);
			}
			//Mark the current node as visited (true) and enqueue it 
			Visited_graph.replace(start.getKey(), true);
			queue.add(start.getKey()); 

			while((!queue.isEmpty())){
				int st=queue.remove();
				for (node_data Node : WGraph.getV(st)){

					if(!Visited_graph.get(Node.getKey())) {

						Visited_graph.replace(Node.getKey(), true);

						queue.add(Node.getKey()); 

					} 
				}
			}
		}
	@Override
	public boolean isConnected() {
		if( WGraph==null||WGraph.nodeSize()==0||WGraph.nodeSize()==1) {
			return true;
		}
		/**
		 * Check if there is a node that initializes to false, if so the graph does not connected
		 */
		BfsForisConnected(WGraph.getV().iterator().next());
		for(Boolean B_node: Visited_graph.values()) {
			if(B_node == false ) {
				return false;
			}
		}	
		return true;
	}
	// Initialize all the nodes as INFINITE
	private void inittag() {
		for (node_data i :this.WGraph.getV()) {
			i.setTag(Double.POSITIVE_INFINITY);
		}
	}
	

	@Override
	public double shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub
		if(WGraph.getNode(src)==null||WGraph.getNode(dest)==null) 
			return-1;	
		if(src==dest) { 
			return 0.0;
		}
		PriorityQueue<node_data> queue = new PriorityQueue<node_data>();

		//create a container- Visited_graph Which contains the key and whether we visited it or not
		Visited_graph = new HashMap<Integer, Boolean>();
		for(node_data Node : WGraph.getV()) {
			//We initialized the container to false
			// so we know we have already visited these nodes
			Visited_graph.put(Node.getKey(), false);
		}
		//create a container-parentNodes Which contains the nodes Which led to reaching the dest node in the shortest path
		//Map<node_info, node_info> parentNodes = new HashMap<node_info, node_info>();

		//Mark the current node as visited (true) and enqueue it 
		Visited_graph.replace(src, true);
		//this is the node that we will start with him- src 
		node_data start = this.WGraph.getNode(src);
		//add the node to the queue,and initialize its weight to 0.0
		queue.add(start); 
		start.setTag((int) 0.0);

		while((!queue.isEmpty())){
			node_data u=queue.poll();
			//Run through the edges(with weight) of neighbor of this node (u) 
			for (edges e : ((NodeInfo) u).getEdges().values()){
				//if not 
				if(!Visited_graph.get(e.getDestination().getKey())) {
					//dist is the weight of the node consisting of the weight of 
					//its parent node plus the distance between them 
					double dist = u.getTag()+e.getWeight();

					// if dist is smaller than the weight that exists for this node
					if(dist<e.getDestination().getTag()) {

						//replace the old weight with the new-dist
						e.getDestination().setTag(dist);

						//After the weight has changed to this node it should insert its parent node
						//through which it has reached a smaller weight into the queue of parentNodes
						//parentNodes.put(e.getDestination(), u);

						//After the weight of this nod has changed,
						//it will be deleted from the queue and returned with the new weight
						queue.remove(e.getDestination());
						queue.add(e.getDestination());
					}
				} 
			}
			Visited_graph.replace(u.getKey(), true);
		}
		//Checks if the node we wanted to reach is initialized as infinity' 
		//so there is no way to reach it -then return -1
		double d = WGraph.getNode(dest).getTag();
		if(d==Double.POSITIVE_INFINITY) {
			inittag();
			return -1;
		}
		inittag();
		return d;
	}

	

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		// TODO Auto-generated method stub
		return null;
	}


/**
 * Saves this weighted (undirected) graph to the given
 * file name
 * @param file - the file name (may include a relative path).
 * @return true - iff the file was successfully saved
 */
	@Override
	public boolean save(String file) {
		try
		{
			FileOutputStream file1 = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(file1);

			out.writeObject(this.WGraph);

			out.close();
			file1.close();
            return true;
		}
		catch(IOException ex)
		{
			System.out.println("IOException is caught");
			return false;

		}
	}
	 /**
     * This method load a graph to this graph algorithm.
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
	@Override
	public boolean load(String file) {
		try {
			FileInputStream file1 = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(file1);
			this.WGraph = (directed_weighted_graph) in.readObject();
			in.close();
			file1.close();
			System.out.println("Object has been deserialized");
			return true;
		}
		catch(IOException ex)
		{
			System.out.println("IOException is caught");
			return false;
		}
		catch(ClassNotFoundException ex)
		{
			System.out.println("ClassNotFoundException is caught");
			return false;

		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
