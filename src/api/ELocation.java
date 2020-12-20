package api;

/**
 * This class represents a position on the graph (a relative position
 * on an edge - between two consecutive nodes).
 */
public class ELocation implements edge_location {

	private edge_data e;
	public ELocation(edge_data _e) {
		e=new EdgeData(((EdgeData)_e));
	}
	 /**
     * Returns the edge on which the location is.
     * @return
     */
	@Override
	public edge_data getEdge() {
		return e;
	}
	/**
     * Returns the relative ration [0,1] of the location between src and dest.
     * @return
     */
	@Override
	public double getRatio() {
		return 0;
	}
}
