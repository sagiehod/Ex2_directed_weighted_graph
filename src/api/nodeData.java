package api;

import java.util.Objects;

public class nodeData implements node_data  {
	private int key;
	private geo_location GLocation;	
	private double weight;
	private String info;
	private int tag;	
	public nodeData(int key, geo_location GLocation, double weight, String info, int tag) {		
		this.key = key;
		this.GLocation = GLocation;
		this.weight = weight;
		this.info = info;
		this.tag = tag;
	}
	public nodeData() {
		this.key = 0;       
		this.info= "";
		this.weight = 0;
	}
	@Override
	public int getKey() {
		// TODO Auto-generated method stub
		return key;
	}
	@Override
	public geo_location getLocation() {
		// TODO Auto-generated method stub
		return GLocation;

	}
	@Override
	public void setLocation(geo_location p) {
		// TODO Auto-generated method stub
		this.GLocation=p;
	}
	@Override
	public double getWeight() {
		// TODO Auto-generated method stub
		return weight;
	}
	@Override
	public void setWeight(double w) {
		this.weight=w;		
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
