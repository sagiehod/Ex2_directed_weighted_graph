package api;

public class ELocation implements edge_location {

	private edge_data e;
	/**
	 * A copy constructor of this edge_data 
	 * @param edge_data _e
	 */	public ELocation(edge_data _e) {
		e=new EdgeData(((EdgeData)_e));
	}
	 /**
	     * Returns the edge on which the location is.
	     * @return e
	     */
	@Override
	public edge_data getEdge() {
		return e;
	}
	
	@Override
	public double getRatio() {
		return 0;
	}
}
