package api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;

public class D_GAlgo implements dw_graph_algorithms,Serializable{
	private directed_weighted_graph WGraph;
	

	/**
	 * constructor
	 */
	 public D_GAlgo() {
		 
				WGraph=(directed_weighted_graph) new D_GAlgo();
			}
	
	@Override
	public void init(directed_weighted_graph g) {
		// TODO Auto-generated method stub
		this.WGraph=  g;
	}

	@Override
	public directed_weighted_graph getGraph() {
		// TODO Auto-generated method stub
		return this.WGraph;
	}

	@Override
	public directed_weighted_graph copy() {
		directed_weighted_graph copyGraph=  new D_GAlgo (WGraph);
		return copyGraph;
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub
		return 0;
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

}
