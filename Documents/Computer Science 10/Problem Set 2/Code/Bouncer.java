/**
 * An agent that moves in a particular direction, but bounces off the walls.
 */
public class Bouncer extends Agent {
	private int xmax, ymax;	// size of bouncing area
	private double dx, dy;	// step size in x and y

	/**
	 * Initializes with the given coordinates and bouncing area size,
	 * and random step sizes
	 */
	public Bouncer(double x, double y, int xmax, int ymax) {
		super(x, y);
		this.xmax = xmax; this.ymax = ymax;

		// Step size randomly between -5 and +5
		dx = 10 * (Math.random() - 0.5);
		dy = 10 * (Math.random() - 0.5);
	}

	/**
	 * Initializes with the given coordinates and bouncing area size,
	 * and step sizes
	 */
	public Bouncer(double x, double y, int xmax, int ymax, double dx, double dy) {
		super(x, y);
		this.xmax = xmax; this.ymax = ymax;
		this.dx = dx; this.dy = dy;
	}

	public void move() {
		x += dx;
		y += dy;
		// Handle the bounce, accounting for radius.
		if (x > xmax - r) {
			x = xmax - r;
			dx = -dx;
		}
		else if (x < r) {
			x = r;
			dx = -dx;
		}
		if (y > ymax - r) {
			y = ymax - r;
			dy = -dy;
		}
		else if (y < r) {
			y = r;
			dy = -dy;
		}
	}

}
