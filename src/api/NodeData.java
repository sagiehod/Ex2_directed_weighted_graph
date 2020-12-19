package api;
import gameClient.util.Point3D;

	public class NodeData implements node_data  {
		private int key;
		private static int idCounter = 0;
		private geo_location GLocation;	
		private double weight;
		private String info;
		private int tag;	
		/**
		 * constructor of NodeInfo
		 * @param i 
		 */
		/**
		 *   a empty constructor of this NodeData
		 *
		 */
		public NodeData()  {
			key=idCounter++;
			this.GLocation = new Point3D(0,0,0);
			info="";
			tag=0;
			weight=Double.POSITIVE_INFINITY;
		}
		/**
		 *  constructor of this NodeData
		 * @param i
		 */
		public NodeData(int i)  {
			key=i;
			this.GLocation = new Point3D(0,0,0);
			info="";
			tag=0;
			weight=Double.POSITIVE_INFINITY;
		}
		/**
		 * A copy constructor of this NodeData
		 * @param other
		 */
		public NodeData(node_data other) {
			this.key=other.getKey();
			this.info=other.getInfo();
			this.tag = other.getTag();
			this.weight = other.getWeight();
			this.GLocation=new Point3D(other.getLocation().x(),other.getLocation().y(),other.getLocation().z());
		}
		
		@Override
		public int getKey() {
			return this.key;
		}
		@Override
		public geo_location getLocation() {
			return GLocation;

		}
		@Override
		public void setLocation(geo_location p) {
			this.GLocation=new Point3D(p.x(),p.y(),p.z());
		}
		@Override
		public double getWeight() {
			return weight;
		}
		@Override
		public void setWeight(double w) {
			this.weight=w;		
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
