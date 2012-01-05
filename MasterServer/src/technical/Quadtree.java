package technical;

import java.util.ArrayList;

public class Quadtree<T extends Location> {	
	public class QuadtreeNode
	{
		QuadtreeNode parent;
		ArrayList<QuadtreeNode> children = null;
		T item = null;
		double latitude;
		double longitude;
		double longitudeRange;
		double latitudeRange;
		
		public QuadtreeNode(QuadtreeNode parent, double latitude, double longitude, double latitudeRange, double longitudeRange)
		{
			this.parent = parent;
			this.latitude = latitude;
			this.longitude = longitude;
			this.latitudeRange = latitudeRange;
			this.longitudeRange = longitudeRange;
			item = null;
		}
		
		private int getQuadrant(double latitude, double longitude)
		{
			return ((latitude < this.latitude) ? 0 : 1) + ((longitude < this.longitude) ? 2 : 0);
		}
		
		private double getQuadrantLatitude(int quadrant)
		{
			switch (quadrant){
				case 0:  
				case 2: 
					return latitude - 0.5 * latitudeRange;
				case 1:
				case 3:
					return latitude + 0.5 * latitudeRange;
			}
			return 0;
		}
		
		private double getQuadrantLongitude(int quadrant)
		{
			switch (quadrant){
			case 0:  
			case 1: 
				return longitude - 0.5 * longitudeRange;
			case 2:
			case 3:
				return longitude + 0.5 * longitudeRange;
		}
		return 0;
		}
		
		public void addItem(T item)
		{
			if (children == null) {
				if (this.item != null)
					split();
				addItem(item);
			}
			else {
				int quadrant = getQuadrant(item.getLatitude(), item.getLongitude());
				if (children.get(quadrant) == null)
					children.add(quadrant, new QuadtreeNode(this, getQuadrantLatitude(quadrant), getQuadrantLongitude(quadrant),  latitudeRange*0.5, longitudeRange*0.5));
				children.get(quadrant).addItem(item);
			}
		}
		
		public T getItem(double latitude, double longitude)
		{
			if (children == null)
				return item;
			QuadtreeNode child = children.get(getQuadrant(latitude, longitude));
			if (child == null)
				return null;
			return child.getItem(latitude, longitude);
		}
		
		public void split()
		{
			assert(children == null);
			assert(item != null);
			children = new ArrayList<QuadtreeNode>(4);
			addItem(item);
		}
		
		public void merge()
		{
			
		}
	}
	
	private QuadtreeNode head = null;
	
	public Quadtree()
	{
		
	}
	
	public void add(T item)
	{
		if (head == null)
			head = new QuadtreeNode(null, 0.0, 0.0, 90.0, 180.0);
		head.addItem(item);
	}
	
	public T get(double latitude, double longitude)
	{
		if (head == null)
			return null;
		return head.getItem(latitude, longitude);
	}
}
