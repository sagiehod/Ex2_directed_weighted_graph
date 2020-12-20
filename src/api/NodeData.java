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
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((GLocation == null) ? 0 : GLocation.hashCode());
			result = prime * result + ((info == null) ? 0 : info.hashCode());
			result = prime * result + key;
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
			if (!(obj instanceof NodeData))
				return false;
			NodeData other = (NodeData) obj;
			if (GLocation == null) {
				if (other.GLocation != null)
					return false;
			} else if (!GLocation.equals(other.GLocation))
				return false;
			if (info == null) {
				if (other.info != null)
					return false;
			} else if (!info.equals(other.info))
				return false;
			if (key != other.key)
				return false;
			if (tag != other.tag)
				return false;
			if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
				return false;
			return true;
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
		/**
		 * Returns the key (id) associated with this node.
		 * @return
		 */
		@Override
		public int getKey() {
			return this.key;
		}
		/** Returns the location of this node, if
		 * none return null.
		 * 
		 * @return
		 */
		@Override
		public geo_location getLocation() {
			return GLocation;

		}
		/** Allows changing this node's location.
		 * @param p - new new location  (position) of this node.
		 */
		@Override
		public void setLocation(geo_location p) {
			this.GLocation=new Point3D(p.x(),p.y(),p.z());
		}
		/**
		 * Returns the weight associated with this node.
		 * @return
		 */
		@Override
		public double getWeight() {
			return weight;
		}
		/**
		 * Allows changing this node's weight.
		 * @param w - the new weight
		 */
		@Override
		public void setWeight(double w) {
			this.weight=w;		
		}
		/**
		 * Returns the remark (meta data) associated with this node.
		 * @return
		 */
		@Override
		public String getInfo() {		
			return info;
		}
		/**
		 * Allows changing the remark (meta data) associated with this node.
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
		 * Allows setting the "tag" value for temporal marking an node - common
		 * practice for marking by algorithms.
		 * @param t - the new value of the tag
		 */
		@Override
		public void setTag(int t) {
			this.tag=t;
		}
		
}
