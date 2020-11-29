package api;

public class edgeData implements edge_data {
	private int src;
	private int dest;
	private double weight;
	private String info;
	private int tag;
	public edgeData(int src, int dest, double weight, String info, int tag) {
		this.src = src;
		this.dest = dest;
		this.weight = weight;
		this.info = info;
		this.tag = tag;
	}
	public edgeData(int src, int dest, double weight) {
		this.src = src;
		this.dest = dest;
		this.weight = weight;
		this.info = "";
		this.tag = 0;
	}
	@Override
	public int getSrc() {
		// TODO Auto-generated method stub
		return src;
	}

	@Override
	public int getDest() {
		// TODO Auto-generated method stub
		return dest;
	}

	@Override
	public double getWeight() {
		// TODO Auto-generated method stub
		return weight;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return info;
	}

	@Override
	public void setInfo(String s) {
		this.info=s;		
	}

	@Override
	public int getTag() {
		// TODO Auto-generated method stub
		return tag;
	}

	@Override
	public void setTag(int t) {
		this.tag=t;		
	}

}
