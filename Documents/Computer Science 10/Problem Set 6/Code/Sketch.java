import java.awt.Graphics;
import java.util.ArrayList;

/**
 * @author Jack Heneghan and Jack Cardwell
 * Computer Science 10: Problem Set 6
 * class that manages the local and master version of all of the shapes
 */
public class Sketch {
	//stores a new ArrayList of shapes into the sketch class
	public ArrayList<Shape> shapeList = new ArrayList<Shape>();
	
	//constructor that binds the shapeList to the sketch reference
	public Sketch(){
		this.shapeList=shapeList;
	}
	
	//returns the shape at an index
	public synchronized Shape get(int idx) {
		return shapeList.get(idx);
	}
	
	//adds a shape to the shapeList
	public synchronized void add(Shape shape) {
		shapeList.add(shape);
	}
	
	//sets a shape to null in the shapeList in order to preserve order
	public synchronized void remove(int idx){
		shapeList.set(idx, null);
	}
	
	//updates the position of a shape within the list
	public synchronized void move(int idx, int dx, int dy){
		shapeList.get(idx).moveTo(dx, dy);
	}
	
	//draws all of the objects in the shapeList
	public synchronized void drawSketch(Graphics g){
		for (Shape currentObject:shapeList){
			if (currentObject!=null){
				currentObject.draw(g);
			}
		}
	}
	
	//finds the index of a shape given a position within a canvas
	public synchronized int findIndex(int x, int y){
		int returnValue=-1;
		for (int i=0;i<shapeList.size();i++){
			Shape currentShape=shapeList.get(i);
			if (currentShape!=null){
				if (currentShape.contains(x,y)){
					returnValue=i;
				}
			}
		}
		return returnValue;
	}
}
