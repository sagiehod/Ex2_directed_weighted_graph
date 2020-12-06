package api;

public class ELocation implements edge_location {

	private edge_data e;
	public ELocation(edge_data _e) {
		e=new EdgeData(((EdgeData)_e));
	}
	@Override
	public edge_data getEdge() {
		return e;
	}

	@Override
	public double getRatio() {
		return 0;
	}
}
