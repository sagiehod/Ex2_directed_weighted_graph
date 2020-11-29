package api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;





public class D_WGraph  implements directed_weighted_graph {
	private final HashMap<Integer, node_data> nodes;
	private int numOfEdges;
	private int Mc;

	
	 private HashMap<Integer,HashMap<Integer,edge_data>> Edges;
	
	
	public D_WGraph() {
		this.nodes = new HashMap<>();
		this.Edges=new HashMap<>();
		this.numOfEdges = 0;
		this.Mc = 0;
	}
	public D_WGraph (directed_weighted_graph gh) {

		this.Mc=gh.getMC();
		this.numOfEdges=gh.edgeSize();

		Iterator<node_data> it = gh.getV().iterator();
		HashMap< Integer,node_data> temp=new HashMap< Integer,node_data>();
		while(it.hasNext()) {
			node_data t=it.next();
			temp.put(t.getKey(),t);
		}
		nodes=temp;
	}
	@Override
	public node_data getNode(int key) {
		// TODO Auto-generated method stub
		return nodes.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		// TODO Auto-generated method stub
		if(Edges.get(src)!=null&&Edges.get(src).containsKey(dest))
			return Edges.get(src).get(dest);
		else
			return null;
	}

	@Override
	public void addNode(node_data n) {
		//
		if(!nodes.containsKey(n.getKey())) {
			//
			nodes.put(n.getKey(), n);
			Mc++;
		}
			}
		
	

	@Override
	public void connect(int src, int dest, double w) {
		node_data nd1 = nodes.get(src);
		node_data nd2 = nodes.get(dest);
		if(src==dest) {
			return;
		}
			
		//weight < 0 throw exception
		try {
			if(w<0)
				throw new Exception("The weight must be positive");
		} catch (Exception e) {e.printStackTrace(); return;	}	


//		if(!hasEdge(src,dest)&&nd1!=null&&nd2!=null) {
//
//			((nodeData) nd1).getNi().put(node2, nd2);
//			((nodeData) nd1).getWi().put(node2, w);
//
//			((nodeData) nd2).getNi().put(node1, nd1);
//			((nodeData) nd2).getWi().put(node1, w);
//			numOfEdges++;
//			Mc++;
//		}
	}

	@Override
	public Collection<node_data> getV() {
		// TODO Auto-generated method stub
		return this.nodes.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
return null;
		}

	@Override
	public node_data removeNode(int key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int nodeSize() {
		// TODO Auto-generated method stub
		return this.nodes.size();
	}

	@Override
	public int edgeSize() {
		// TODO Auto-generated method stub
		return numOfEdges;
	}

	@Override
	public int getMC() {
		// TODO Auto-generated method stub
		return Mc;
	}

}
