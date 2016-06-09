import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


/**
 * @authors Jack Heneghan and Jack Cardwell
 * A rectangle-shaped Shape
 * Dartmouth CS 10, Winter 2015
 */
public class Rectangle extends Shape {
	// TODO: YOUR CODE HERE
	
	//constructor for the Rectangle class, takes the position coordinates and color as input
	public Rectangle(int x1, int y1, int x2, int y2, Color c){
		
		//passes of the information to the shape constructor
		super(Math.min(x1, x2), Math.min(y1, y2), Math.max(x1, x2), Math.max(y1,  y2), c);
	}
	
	//checks to see whether a coordinate falls within rectangle
	public boolean contains(int x, int y){
		
		//return true if it falls within the rectangle
		if (x >= x1 && x<= x2 && y >=y1 && y <= y2){
			return true;
		}
		
		else{
			return false;
		}
	}
	
	//updates the corners of the rectangle 
	public void setCorners(int x1, int y1, int x2, int y2){
		this.x1 = Math.min(x1, x2); this.y1 = Math.min(y1, y2);
		this.x2 = Math.max(x1, x2); this.y2 = Math.max(y1,  y2);
	}
	
	//draws the rectangle with the color at the current point
	public void draw(Graphics g){
		g.setColor(color);
		g.fillRect(x1, y1, Math.abs(x2-x1), Math.abs(y2-y1));
	}
	
	//draws a dotted border around the rectangle in green
	public void border(Graphics g){
		((Graphics2D)g).setStroke(dottedStroke);
		g.setColor(Color.green);
		g.drawRect(x1, y1, x2-x1, y2-y1);
	}
	
	//calls on the shape toString to return all of the information about the rectangle as a string
	public String toString(){
		return "rectangle "+super.toString();
	}
}
