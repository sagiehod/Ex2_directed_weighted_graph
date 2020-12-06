package api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;



public class DWGraph_DS  implements directed_weighted_graph {

	private HashMap <Integer,node_data> nodes_TheGragh_WD;
	private HashMap <Integer, HashMap <Integer,edge_data>> edges_TheGragh_WD;
	private int edgeSize;
	private int MC;

	public DWGraph_DS() {
		this.nodes_TheGragh_WD = new HashMap<>();
		this.edges_TheGragh_WD = new HashMap<>();
		this.edgeSize = 0;
		this.MC = 0;
	}

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

	@Override
	public node_data getNode(int key) {
		return nodes_TheGragh_WD.get(key);
	}


	@Override
	public edge_data getEdge(int src, int dest) {
		if(nodes_TheGragh_WD.containsKey(src) &&nodes_TheGragh_WD.containsKey(dest)) {
			if(this.edges_TheGragh_WD.get(src)!=null&& this.edges_TheGragh_WD.get(src).containsKey(dest)) {
				return this.edges_TheGragh_WD.get(src).get(dest);
			}
		}
		return null;
	}	

	@Override
	public void addNode(node_data n) {

		if(!nodes_TheGragh_WD.containsKey(n.getKey())) {
			nodes_TheGragh_WD.put(n.getKey(), n);
			MC++;
		}
	}


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


	@Override
	public Collection<node_data> getV() {
		return this.nodes_TheGragh_WD.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		if(this.edges_TheGragh_WD.get(node_id)!=null) {
			return this.edges_TheGragh_WD.get(node_id).values();
		}
		return null;
	}


	@Override
	public node_data removeNode(int key) {
		if(this.nodes_TheGragh_WD.containsKey(key)) {
			if(this.edges_TheGragh_WD.get(key)!=null) {
				Iterator<edge_data> iter = getE(key).iterator();
				while(iter.hasNext()) {
					edge_data e =  iter.next();
					this.edges_TheGragh_WD.get(e.getDest()).remove(key);
					edgeSize--;
					MC++;
				}
			}
		}
		MC++;
		return nodes_TheGragh_WD.remove(key);
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		if(getNode(src)!=null&&getNode(dest)!=null&&this.edges_TheGragh_WD.get(src)!=null&&this.edges_TheGragh_WD.get(src).containsKey(dest)) {		
			MC++;
			edgeSize--;
			return this.edges_TheGragh_WD.get(src).remove(dest);
		}
		return null;
	}	

	@Override
	public int nodeSize() {
		return nodes_TheGragh_WD.size();
	}

	@Override
	public int edgeSize() {
		return edgeSize;
	}

	@Override
	public int getMC() {
		return MC;
	}

}
