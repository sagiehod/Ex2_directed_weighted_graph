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
	public static void main(String[] args) {
		directed_weighted_graph g = new DWGraph_DS();

		g.addNode(new NodeData(0));
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));
		//the node that already exist
		g.addNode(new NodeData(4));
		g.addNode(new NodeData(5));
		g.addNode(new NodeData(6));


		g.connect(0,1,2);
		g.connect(0,5,3);
		g.connect(1,2,1);
		g.connect(2,0,5);
		g.connect(2,6,2);
		g.connect(4,5,8);
		g.connect(5,6,1);
		g.connect(6,0,1);
		g.connect(6,4,3);

		//ga.init(g);
		//check the number of the list of the shortest path
		//assertEquals(3, ga.shortestPath(1,6).size());
		//assertEquals(4, ga.shortestPath(2,5).size());
	
		// 11 is node that not exist in the graph
		//assertEquals(null, ga.shortestPath(1,11));
		//path from the same node--> 1
		//assertEquals(1, ga.shortestPath(2,2).size());
		//two nodes that not exist in the graph
		//assertEquals(null, ga.shortestPath(12,12));

		//there is not path between them- 6 is not exist in the graph
	//	g.removeNode(6);
		//assertEquals(null, ga.shortestPath(2,6));

	}
}