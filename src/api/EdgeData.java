package api;


public class EdgeData implements edge_data {
	private int src;
	private int dest;
	private double weight;
	private String info;
	private int tag;
	
	/**
	 * constructor
	 * @param weight,src,dest
	 */
		
	public EdgeData(double w, int s,int d) {
		this.dest=d;
		this.weight = w;
		this.src=s;
		this.info="";
		this.tag=0;
	    }
	/**
	 * A copy constructor of this Edge data 
	 * @param Edge data e
	 */
public EdgeData(EdgeData e) {
	this.weight=e.getWeight();
	this.dest =e.getDest();
	this.src=e.getSrc();
	this.tag=e.getTag();
	this.info=e.getInfo();
}
	
	@Override
	public int getSrc() {
		return src;
	}

	@Override
	public int getDest() {
		return dest;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public String getInfo() {

		return info;
	}

	@Override
	public void setInfo(String s) {
		this.info=s;		
	}

	@Override
	public int getTag() {
		return tag;
	}

	@Override
	public void setTag(int t) {
		this.tag=t;		
	}
}
