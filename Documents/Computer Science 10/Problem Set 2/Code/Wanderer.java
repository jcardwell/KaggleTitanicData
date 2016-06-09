/**
 * An agent that moves randomly.
 */
public class Wanderer extends Agent {
	public Wanderer(double x, double y) {
		super(x, y);
	}
		
	public void move() {	
		// Move an amount between -5 and +5
		x += r * (Math.random() - 0.5);
		y += r * (Math.random() - 0.5);
	}
}
