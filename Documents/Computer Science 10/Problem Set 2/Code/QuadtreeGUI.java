import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Driver for interacting with a quadtree:
 * inserting points, viewing the tree, and finding points near a mouse press
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2015
 * updated by @authors Jack Cardwell and Alex Wolf for CS 10 on April 22, 2015
 */
public class QuadtreeGUI extends DrawingGUI {
	private static final int width=800, height=600;		// size of the universe
	private static final int agentRadius = 5;			// uniform radius of all agents
	private static final Color[] rainbow = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA};
			// to color different levels differently

	private Quadtree<Agent> qt = null;					// the quadtree holding the agents
	private char mode = 'a';								// 'a': adding points; 'q': querying with the mouse
	private int mouseX, mouseY;							// current mouse location, when querying
	private int mouseRadius = 10;						// circle around mouse location, for querying
	private ArrayList<Agent> found = null;				// who was found near mouse, when querying
	
	public QuadtreeGUI() {
		super("quadtree", width, height);
	}

	/**
	 * 
	 * DrawingGUI method, here keeping track of the location and redrawing to show it
	 */
	public void handleMouseMotion(int x, int y) {
		if (mode == 'q') {
			mouseX = x; mouseY = y;
			repaint();
		}
	}

	/**
	 * DrawingGUI method, here either adding a new point or querying near the mouse
	 * updated by @authors Jack Cardwell and Alex Wolf
	 */
	public void handleMousePress(int x, int y) {
		if (mode == 'a') {
			// TODO: YOUR CODE HERE
			
			// Add a new agent at the point, depending on if the quadtree is empty or not
			Agent newAgent=new Agent(x,y,agentRadius);
			
			//if the quadtree is empty, create a new quadtree
			if (qt==null){
				qt=new Quadtree<Agent>(x,y, newAgent);
			}
			
			//otherwise the quadtree isn't empty, so insert instead of add
			else{
				//insert this agent into the quadtree that keeps track of the agents that are drawn
				qt.insert(x,y,newAgent);
			}
		}
		else if (mode == 'q') {
			// Set "found" to what qt says is near the mouse press
			// TODO: YOUR CODE HERE
			
			//call on the findInCircle method from Quadtree and store the list into the ArrayList found
			found=qt.findInCircle(x, y, mouseRadius+agentRadius, 0, 0, width, height);
		}
		else {
			System.out.println("clicked at "+x+","+y);
		}
		repaint();
	}
	
	/**
	 * DrawingGUI method, here toggling the mode between 'a' and 'q'
	 */
	public void handleKeyPress(char key) {
		mode = key;
	}

	/**
	 * DrawingGUI method, here drawing the quadtree
	 * and if in query mode, the mouse location and any found agents
	 */
	public void draw(Graphics g) {
		if (qt != null) drawQT(g, qt, 0, 0, 0, width, height);
		if (mode == 'q') {
			g.setColor(Color.BLACK);
			g.drawOval(mouseX-mouseRadius, mouseY-mouseRadius, 2*mouseRadius, 2*mouseRadius);			
			if (found != null) {
				g.setColor(Color.BLACK);
				for (Agent b : found) b.draw(g);
			}
		}
	}

	/**
	 * Draws the quadtree
	 * updated by @authors Jack Cardwell and Alex Wolf
	 * @param g		the graphics object for drawing
	 * @param qt		a quadtree (not necessarily root)
	 * @param level	how far down from the root qt is (0 for root, 1 for its children, etc.)
	 * @param x1 	rectangle min x
	 * @param y1  	rectangle min y
	 * @param x2  	rectangle max x
	 * @param y2  	rectangle max y
	 */
	public void drawQT(Graphics g, Quadtree<Agent> qt, int level, double x1, double y1, double x2, double y2) {
		// Set the color for this level
		g.setColor(rainbow[level % rainbow.length]);
		// Draw this node center
		qt.getElem().draw(g);
		// and lines
		g.drawLine((int)qt.getX(), (int)y1, (int)qt.getX(), (int)y2);
		g.drawLine((int)x1, (int)qt.getY(), (int)x2, (int)qt.getY());
		// Recurse with children
		// TODO: YOUR CODE HERE
		
		//use conditionals to recursively call on this function so long as the quadtree has a child, depending on the quadrant
		if (qt.hasChild(1)){		//quadrant 1
			drawQT(g, qt.getChild(1), level+1,qt.getX(),y1,x2,qt.getY());
		}
		if (qt.hasChild(2)){		//quadrant 2
			drawQT(g, qt.getChild(2), level+1,x1,y1,qt.getX(),qt.getY());
		}
		if(qt.hasChild(3)){			//quadrant 3
			drawQT(g, qt.getChild(3), level+1,x1,qt.getY(),qt.getX(),y2);
		}
		if(qt.hasChild(4)){			//quadrant 4
			drawQT(g, qt.getChild(4), level+1,qt.getX(),qt.getY(),x2,y2);
		}
	}

	/**
	 * Main method for the application
	 * 
	 * @param args	ignored
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new QuadtreeGUI();
			}
		});
	}
}
