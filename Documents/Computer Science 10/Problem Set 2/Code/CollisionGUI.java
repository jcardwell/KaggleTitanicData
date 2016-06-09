import java.awt.*;

import javax.swing.*;

import java.util.ArrayList;

/**
 * Using a quadtree for collision detection
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2015
 * updated by @authors Jack Cardwell and Alex Wolf for CS 10 on April 22,2015
 */
public class CollisionGUI extends DrawingGUI {
	private static final int width=800, height=600;		// size of the universe

	private ArrayList<Agent> agents;						// all the agents
	private ArrayList<Agent> colliders;					// the agents who collided at this step
	private char agentType = 'b';						// what type of agent to create
	private char collisionHandler = 'c';					// when there's a collision, 'c'olor them, or 'd'estroy them
	private int delay = 100;								// timer control

	public CollisionGUI() {
		super("super-collider", width, height);

		agents = new ArrayList<Agent>();

		// Timer drives the animation.
		startTimer();
	}

	/**
	 * Adds an agent of the current agentType at the location
	 */
	private void add(int x, int y) {
		if (agentType=='b') {
			agents.add(new Bouncer(x,y,width,height));
		}
		else if (agentType=='w') {
			agents.add(new Wanderer(x,y));
		}
		else {
			System.err.println("Unknown agent type "+agentType);
		}
	}

	/**
	 * DrawingGUI method, here creating a new agent
	 */
	public void handleMousePress(int x, int y) {
		add(x,y);
		repaint();
	}

	/**
	 * DrawingGUI method
	 */
	public void handleKeyPress(char k) {
		if (k == 'f') { // faster
			if (delay>1) delay /= 2;
			setTimerDelay(delay);
			System.out.println("delay:"+delay);
		}
		else if (k == 's') { // slower
			delay *= 2;
			setTimerDelay(delay);
			System.out.println("delay:"+delay);
		}
		else if (k == 'r') { // add some new agents at random positions
			for (int i=0; i<10; i++) {
				add((int)(width*Math.random()), (int)(height*Math.random()));
				repaint();
			}			
		}
		else if (k == 'c' || k == 'd') { // control how collisions are handled
			collisionHandler = k;
			System.out.println("collision:"+k);
		}
		else { // set the type for new agents
			agentType = k;			
		}
	}

	/**
	 * @authors Jack Cardwell and Alex Wolf
	 * DrawingGUI method, here drawing all the agents and then re-drawing the colliders in red
	 */
	public void draw(Graphics g) {
		// TODO: YOUR CODE HERE
		
		//set the color that all points will be drawn in
		Color black=new Color(0,0,0);
		Color red=new Color(255,0,0);
		
		//cycle through all of the agents in the agents list and draw them
		for (Agent element:agents){
			g.setColor(black);
			element.draw(g);
		}
		
		//only go through this cycle if the program is in the coloring collider mode
		if (collisionHandler=='c'){
			
			//only proceed if there are agents in colliders
			if (colliders!=null){
				
				//cycle through all of the colliders and draw them in red
				for (Agent hit:colliders){
					g.setColor(red);
					hit.draw(g);
				}
			}
		}
	}
	/**
	 * sets up a quadtree with all of the agents in the window that were held in agents, an ArrayList
	 * helper function for findColliders
	 * @authors Jack Cardwell and Alex Wolf
	 * @return qt, a quadtree of agents
	 */
	private Quadtree<Agent> setQuadtree(){
		//initialize the quadtree
		Quadtree<Agent> qt=null;
		
		//cycle through all agents
		for (Agent element:agents){
			
			//check to see if there is an element already in the quadtree and then add it to the quadtree accordingly
			if (qt==null){
				qt=new Quadtree<Agent>(element.getX(),element.getY(),element);
			}
			else{
				qt.insert(element.getX(),element.getY(),element);
			}
		}
		return qt;
	}
	/**
	 * @authors Jack Cardwell and Alex Wold updated this method
	 * Sets colliders to include all agents in contact with another agent
	 */
	private void findColliders() {
		// TODO: YOUR CODE HERE
		
		//set up the quadtree, the collider list for each agent, and the universal collider list
		Quadtree<Agent>qt=setQuadtree();						//quadtree of agents
		ArrayList<Agent> agentCollider=new ArrayList<Agent>();	//agent-specific collider list
		colliders=new ArrayList<Agent>();						//universal collider list
		
		//look at all the agents in the list and see if anything is colliding with them using the quadtree find method
		for (Agent element:agents){
			
			//add the found agents to a list of colliders
			agentCollider=qt.findInCircle(element.getX(),element.getY(),element.getRadius()*2,0,0,width,height);
			
			//go through the agent's individual collider list and only add the elements to the universal collider list if the agent is not equal to itself
			for (Agent point:agentCollider){
				
				if (point.equals(element)==false){
					//add to the universal collider list
					colliders.add(point);
				}
			}
		}
		
	}

	/**
	 * DrawingGUI method, here moving all the agents and checking for collisions
	 */
	public void handleTimer() {
		// Ask all the agents to move themselves.
		for (Agent agent : agents) {
			agent.move();
		}
		// Check for collisions
		if (agents.size() > 0) {
			findColliders();
			if (collisionHandler=='d') {
				agents.removeAll(colliders);
				colliders = null;
			}
		}
		// Now update the drawing
		repaint();
	}

	/**
	 * Main method for the application
	 * 
	 * @param args	ignored
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CollisionGUI();
			}
		});
	}
}
