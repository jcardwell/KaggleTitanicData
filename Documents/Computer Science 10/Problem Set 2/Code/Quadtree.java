import java.util.ArrayList;

/**
 * A point quadtree: stores an element at a 2D position, 
 * with children at the subdivided quadrants
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2015
 * updated by @authors Jack Cardwell and Alex Wolf for CS10 on April 22, 2015
 *
 * @param <E>	the type of element
 */
public class Quadtree<E> {
	private double px, py;					// where the node is
	private E elem;							// associated element
	private Quadtree<E> c1, c2, c3, c4;		// children

	/**
	 * Initializes a leaf quadtree, holding the element at the point
	 */
	public Quadtree(double x, double y, E elem) {
		px = x; py = y;
		this.elem = elem; 
	}

	public E getElem() {
		return elem;
	}

	public double getX() {
		return px;
	}

	public double getY() {
		return py;
	}
		
	/**
	 * Returns the child (if any) at the given quadrant, 1-4
	 * @param quadrant	1 through 4
	 */
	public Quadtree<E> getChild(int quadrant) {
		// TODO: YOUR CODE HERE
		
		//if there is no child in any quadrant
		Quadtree<E> child=null;
		
		//if quadrant 1 and there is a child save the child to the variable child
		if (quadrant==1 && hasChild(1)){
			child=c1;
		}
		//if quadrant 2 and there is a child save the child to the variable child
		else if (quadrant==2 && hasChild(2)){
			child=c2;
		}
		//if quadrant 3 and there is a child save the child to the variable child
		else if (quadrant==3 && hasChild(3)){
			child=c3;
		}
		//if quadrant 4 and there is a child save the child to the variable child
		else if (quadrant==4 && hasChild(4)){
			child=c4;
		}
		
		//return what is stored in child, which will either be null or have an element 
		return child;
	}
		
		
	/** @authors Jack Cardwell and Alex Wolf
	 * Returns whether or not there is a child at the given quadrant, 1-4
	 * @param quadrant	1 through 4
	 */
	public boolean hasChild(int quadrant) {
		// TODO: YOUR CODE HERE
		//look at c1 if quadrant 1 is selected
		if (quadrant==1 && c1!=null){
			return true;
		}
		//look at c2 if quadrant 2 is selected
		if (quadrant==2 && c2!=null){
			return true;
		}
		//look at c3 if quadrant 3 is selected
		if (quadrant==3 && c3!=null){
			return true;
		}
		//look at c4 if quadrant 4 is selected
		if (quadrant==4 && c4!=null){
			return true;
		}
		
		//if it hasn't already returned true, return false
		return false;
	}
	
		
	/**
	 * @author Jack Cardwell and Alex Wolf
	 * Inserts the element into the quadtree at the point
	 */
	public void insert(double x, double y, E elem) {
		// TODO: YOUR CODE HERE
		
		//store the element as a new quadtreee
		Quadtree<E> element=new Quadtree<E> (x,y,elem);
		
		//use a conditional to check if the point is in quadtree's quadrant 1
		if (x>=px && y<=py){
			//check to see if the quadtree has a child already in quadrant one
			//if it doesn't store it as the quadtree's c1
			if(hasChild(1)==false){
				c1=element;
			}
			//otherwise recursively insert the element into the child that is in quadrant 1
			else{
				c1.insert(x,y,elem);
			}
		}
		
		//use a conditional to check if the point is in quadtree's quadrant 2
		else if (x<px && y<=py){
			//if there isn't c2, store the element there
			if(hasChild(2)==false){
				c2=element;
			}
			//otherwise make the recursive call into c2
			else{
				c2.insert(x, y, elem);
			}
		}
		
		//use a conditional to check if the point is in quadtree's quadrant 3
		else if (x<px && y>py){
			//if there isn't a c3, store the element at c3
			if (hasChild(3)==false){
				c3=element;
			}
			//otherwise make the recursive call into c3
			else{
				c3.insert(x, y, elem);
			}
		}
		
		//use a conditional to check if the point is in quadtree's quadrant 4
		else if (x>=px && y>py){
			//if there isn't a c4, store the element at c4
			if (hasChild(4)==false){
				c4=element;
			}
			//otherwise recursively store the element in the node that will be empty eventually
			else{
				c4.insert(x, y, elem);
			}
		}
	}

	/**
	 * Uses the quadtree to find all elements within the circle,
	 * as well as within the rectangle defining the space covered by the quadtree.
	 * @param cx	  circle center x
	 * @param cy  circle center y
	 * @param cr  circle radius
	 * @param x1  rectangle min x
	 * @param y1  rectangle min y
	 * @param x2  rectangle max x
	 * @param y2  rectangle max y
	 * @return    the elements in the circle (and the rectangle)
	 */
	public ArrayList<E> findInCircle(double cx, double cy, double cr, double x1, double y1, double x2, double y2) {
		// TODO: YOUR CODE HERE
		
		//create an empty ArrayList that will store all of the points that are in the circle and return this list at the end
		ArrayList<E> inCircleList = new ArrayList<E>();
		
		//call on the findInCircleHelper
		findInCircleHelper(inCircleList,cx,cy,cr,x1,y1,x2,y2);
		
		//return the list
		return inCircleList;
	}
	
	/**
	 * create a helper method that recursively adds points to a list
	 * @author Jack Cardwell and Alex Wolf
	 */
	public void findInCircleHelper(ArrayList<E> input, double cx, double cy,double cr, double x1, double y1, double x2, double y2){
		
		//check to see if the circle intersects with the rectangle
		if (circleIntersectsRectangle(cx,cy,cr,x1,y1,x2,y2)){
			
			//check to see if there is a point in that circle
			if (pointInCircle(cx,cy,cr,this.px,this.py)){
			
				//if so, add the element to the list
				input.add(elem);
			}	
			//go through all quadrants, if there is a child in the quadrant recursively call this function
			if(hasChild(1)){		//quadrant 1
				getChild(1).findInCircleHelper(input, cx, cy, cr, px, y1, x2, py);
			}
			if(hasChild(2)){		//quadrant 2
				getChild(2).findInCircleHelper(input, cx, cy, cr, x1, y1, px, py);
			}
			if(hasChild(3)){		//quadrant 3
				getChild(3).findInCircleHelper(input, cx, cy, cr, x1, py, px, y2);
			}
			if(hasChild(4)){		//quadrant 4
				getChild(4).findInCircleHelper(input, cx, cy, cr, px, py, x2, y2);
			}
			
		}
	}
	
	/**
	 * create a helper method to see if a point is in a circle within a given radius
	 * @author Jack Cardwell and Alex Wolf
	 */
	public boolean pointInCircle(double cx, double cy,double cr,double x,double y){
		//find the distance between the point and the center of the circle
		double distanceSquared=((x-cx)*(x-cx))+((y-cy)*(y-cy));
		double distance=Math.sqrt(distanceSquared);
		
		//if the distance between the points is less than the input radius away, then return true
		if (distance<=cr){
			return true;
		}
		//otherwise the point doesn't lie inside the circle
		else{
			return false;
		}
	}
		
	
	/**
	 * Returns whether or not the circle intersects the rectangle
	 * Based on discussion at http://stackoverflow.com/questions/401847/circle-rectangle-collision-detection-intersection
	 * @param cx		circle center x
	 * @param cy		circle center y
	 * @param cr		circle radius
	 * @param x1 	rectangle min x
	 * @param y1  	rectangle min y
	 * @param x2  	rectangle max x
	 * @param y2  	rectangle max y
	 */
	public static boolean circleIntersectsRectangle(double cx, double cy, double cr, double x1, double y1, double x2, double y2) {
		double closestX = Math.min(Math.max(cx, x1), x2);
		double closestY = Math.min(Math.max(cy, y1), y2);
		return (cx-closestX)*(cx-closestX) + (cy-closestY)*(cy-closestY) <= cr*cr;
	}
}
