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

	//for isconnected
	Stack<node_data> stack=new Stack<node_data>();
	int sccCount=0;
	int id=0;

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
				dfs(i);
			}
		}
		System.out.println(sccCount);
		if(sccCount==1) return true;
		return false;
	}

	private void dfs(node_data at) {

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
				if(to.getTag()==0) dfs(to);
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


	@Override
	public List<node_data> shortestPath(int src, int dest) {
		if(WGraph.getNode(src)==null||WGraph.getNode(dest)==null) 
			return null;

		List<node_data> ans=new ArrayList<node_data>();

		if(src==dest) {
			ans.add(WGraph.getNode(src));
			return ans;
		}

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
				//if( v.getTag()==0) {
					double dist = u.getWeight()+e.getWeight();
					if (dist < v.getWeight()) {       
						v.setWeight(dist);
						parentNodes.put(v, u);
						queue.remove(v);
						queue.add(v);
					}
				}
			//}
			//u.setTag(1);
		}
		return parentNodes;
}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
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
		g.addNode(new NodeData(3));
		g.addNode(new NodeData(7));

		g.connect(6,0,5);
		g.connect(5,0,4);
		g.connect(2,0,9);
		g.connect(0,1,3);
		g.connect(1,2,5);
		g.connect(6,2,3);
		g.connect(6,4,3);
		g.connect(5,6,7);
		g.connect(4,5,3);
		g.connect(3,4,7);
		g.connect(3,7,3);
		g.connect(7,3,7);
		g.connect(7,5,3);

		//		
		//		g.addNode(new NodeData(0));
		//		g.addNode(new NodeData(1));
		//		
		//		g.connect(0,1,3);
		//
		//		g.connect(1,0,3);


		dw_graph_algorithms algo=new DWGraph_Algo();
		algo.init(g);
		algo.isConnected();
		List<node_data> a=algo.shortestPath(0, 2);
		for(node_data i:a) {
			System.out.print(i.getKey()+" ");
		}

		//
		//	algo.save("test1");
		//		algo.load("test1");
		//		directed_weighted_graph g1=algo.getGraph();
		//		
		//		System.out.println(g1.nodeSize());
		//		System.out.println(g1.edgeSize());

	}
}