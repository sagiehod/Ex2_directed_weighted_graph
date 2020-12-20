package api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;



public class DWGraph_DS  implements directed_weighted_graph {

	private HashMap <Integer,node_data> nodes_TheGragh_WD;
	private HashMap <Integer, HashMap <Integer,edge_data>> edges_TheGragh_WD;
	private int edgeSize;
	private int MC;

	/**
	 * An empty constructor
	 */
	public DWGraph_DS() {
		this.nodes_TheGragh_WD = new HashMap<>();
		this.edges_TheGragh_WD = new HashMap<>();
		this.edgeSize = 0;
		this.MC = 0;
	}
	/**
	 * A copy constructor of this DWGraph_DS 
	 * @param gh
	 */
	public DWGraph_DS (directed_weighted_graph gh) {
		this.MC=gh.getMC();
		this.edgeSize=gh.edgeSize();

		Iterator<node_data> it = gh.getV().iterator();
		while(it.hasNext()) {
			node_data s=it.next();
			this.nodes_TheGragh_WD.put(s.getKey(),s);

			Iterator<edge_data> it1 = gh.getE(s.getKey()).iterator();
			while(it1.hasNext()) {
				edge_data e=it1.next();
				this.edges_TheGragh_WD.put(s.getKey(),new HashMap<>());
				this.edges_TheGragh_WD.get(s.getKey()).put(e.getDest(), e);
			}
		}
	}
	/**
	 * return the node_data by the node_id,
	 * @param key - the node_id
	 * @return the node_data by the node_id, null if none.
	 */
	@Override
	public node_data getNode(int key) {
		return nodes_TheGragh_WD.get(key);
	}
	/**this function return the weight between 2 nodes that she get (src, dest)
	 * if there is a edge between them, if not return -1
	 * @param src
	 * @param dest
	 * @return
	 */    
	@Override
	public edge_data getEdge(int src, int dest) {
		
		
		if(nodes_TheGragh_WD.containsKey(src) &&nodes_TheGragh_WD.containsKey(dest)) {
			if(this.edges_TheGragh_WD.get(src)!=null&& this.edges_TheGragh_WD.get(src).containsKey(dest)) {
				return this.edges_TheGragh_WD.get(src).get(dest);
			}
		}
		return null;
	}	
	/**
	 * add a new node to the graph with the given node_data.
	 * @param n
	 */

	@Override
	public void addNode(node_data n) {

		if(!nodes_TheGragh_WD.containsKey(n.getKey())) {
			nodes_TheGragh_WD.put(n.getKey(), n);
			MC++;
		}
	}
	/**
	 * This function add an edge into the graph- between two nodes that she get ,with an edge with weight >=0.
	 * if the edge src-dest already exists, the method simply updates the weight of the edge
	 * @param src 
	 * @param dest
	 * @param w
	 */
	@Override
	public void connect(int src, int dest, double w) {
		if (getNode(src)==null ||getNode(dest)==null) return; 
		if(w<0) {
			throw new IllegalArgumentException("Error: weight should be bigger or equal than 0");
		}
		if(this.edges_TheGragh_WD.get(src)==null) {
			this.edges_TheGragh_WD.put(src, new HashMap<>());
		}
		if(this.edges_TheGragh_WD.get(src).containsKey(dest)
				&& getEdge(src,dest).getWeight()== w) {
			return;
		}
		else {
			if(this.edges_TheGragh_WD.get(src).containsKey(dest)
					&& getEdge(src,dest).getWeight()!= w) {
				this.edges_TheGragh_WD.get(src).put(dest, new EdgeData(w,src,dest));
				MC++;
			}
			else {
				this.edges_TheGragh_WD.get(src).put(dest, new EdgeData(w,src,dest));
				MC++;
				edgeSize++;
			}
		}
	}

	/**
	 * This method return a pointer for the collection representing all the nodes in the graph.
	 * @return Collection<node_info>  
	 */
	@Override
	public Collection<node_data> getV() {
		return this.nodes_TheGragh_WD.values();
	}
	/**
	 * This method returns a pointer (shallow copy) for the
	 * collection representing all the edges getting out of 
	 * the given node (all the edges starting (source) at the given node). 
	 * @param node_id
	 * @return Collection<edge_data>
	 */
	@Override
	public Collection<edge_data> getE(int node_id) {
		if(this.edges_TheGragh_WD.get(node_id)!=null) {
			return this.edges_TheGragh_WD.get(node_id).values();
		}
		return null;
	}
	/**
	 * Deletes the node (with the given ID) from the graph -
	 * and removes all edges which starts or ends at this node.
	 * @return the data of the removed node (null if none). 
	 * @param key
	 */
	@Override
	public node_data removeNode(int key) {
		if(this.nodes_TheGragh_WD.containsKey(key)) {
			Iterator<node_data> iterv = getV().iterator();
			while(iterv.hasNext()) {
				node_data n =  iterv.next();
				if(this.edges_TheGragh_WD.get(n.getKey())!=null && 
						this.edges_TheGragh_WD.get(n.getKey()).containsKey(key)) {
					this.edges_TheGragh_WD.get(n.getKey()).remove(key);
					edgeSize--;
					MC++;
				}
			}

			if(this.edges_TheGragh_WD.get(key)!=null) {
				edgeSize-=getE(key).size();
				MC+=getE(key).size();
			this.edges_TheGragh_WD.remove(key);
			}
			
			
			MC++;
			return nodes_TheGragh_WD.remove(key);
		}
		return null;
	}
	/**
	 * Deletes the edge from the graph,
	 * @param src
	 * @param dest
	 * @return the data of the removed edge (null if none).
	 */
	@Override
	public edge_data removeEdge(int src, int dest) {
		if(getNode(src)!=null&&getNode(dest)!=null&&this.edges_TheGragh_WD.get(src)!=null&&this.edges_TheGragh_WD.get(src).containsKey(dest)) {		
			MC++;
			edgeSize--;
			return this.edges_TheGragh_WD.get(src).remove(dest);
		}
		return null;
	}	
	/** Returns the number of vertices (nodes) in the graph.
	 * Note: this method should run in O(1) time. 
	 * @return nodeSize
	 */
	@Override
	public int nodeSize() {
		return nodes_TheGragh_WD.size();
	}
	/** 
	 * Returns the number of edges (assume directional graph).
	 * Note: this method should run in O(1) time.
	 * @return  edgeSize
	 */
	@Override
	public int edgeSize() {
		return edgeSize;
	}
	/**
	 * Returns the Mode Count - for testing changes in the graph.
	 * @return MC
	 */
	@Override
	public int getMC() {
		return MC;
	
	}
}
