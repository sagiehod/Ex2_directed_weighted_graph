package api;

/**
 * This class represents the set of operations applicable on a
 * directional edge(src,dest) in a (directional) weighted graph.
 */

public class EdgeData implements edge_data {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dest;
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + src;
		result = prime * result + tag;
		long temp;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof EdgeData))
			return false;
		EdgeData other = (EdgeData) obj;
		if (dest != other.dest)
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (src != other.src)
			return false;
		if (tag != other.tag)
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}
	private int src;
	private int dest;
	private double weight;
	private String info;
	private int tag;
	
	
	  /**
     * a parametric constructor
     * @param w-weight a double type
     * @param s-source a node_data type (the source of the edge)
     * @param d-dest  a node_data type (the destination of the edge)
    
     */
	public EdgeData(double w, int s,int d) {
		this.dest=d;
		this.weight = w;
		this.src=s;
		this.info="";
		this.tag=0;
	    }
	  /**
     * a copy constructor.
     * @param edge an Edges type
     */

public EdgeData(EdgeData e) {
	this.weight=e.getWeight();
	this.dest =e.getDest();
	this.src=e.getSrc();
	this.tag=e.getTag();
	this.info=e.getInfo();
}
	
/**
 * The id of the source node of this edge.
 * @return
 */
	@Override
	public int getSrc() {
		return src;
	}
	/**
	 * The id of the destination node of this edge
	 * @return
	 */
	@Override
	public int getDest() {
		return dest;
	}
	/**
	 * @return the weight of this edge (positive value).
	 */
	@Override
	public double getWeight() {
		return weight;
	}
	/**
	 * Returns the remark (meta data) associated with this edge.
	 * @return
	 */
	@Override
	public String getInfo() {

		return info;
	}
	/**
	 * Allows changing the remark (meta data) associated with this edge.
	 * @param s
	 */
	@Override
	public void setInfo(String s) {
		this.info=s;		
	}
	/**
	 * Temporal data (aka color: e,g, white, gray, black) 
	 * which can be used be algorithms 
	 * @return
	 */
	@Override
	public int getTag() {
		return tag;
	}
	/** 
	 * This method allows setting the "tag" value for temporal marking an edge - common
	 * practice for marking by algorithms.
	 * @param t - the new value of the tag
	 */
	@Override
	public void setTag(int t) {
		this.tag=t;		
	}
}
