package cs10;
import javax.imageio.*;

import java.io.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

/**
 * A class demonstrating manipulation of image pixels.
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author CBK, Winter 2014, rewritten for BufferedImage
 * @author CBK, Spring 2015, refactored to separate GUI from operations
 */
public class ImageProcessingGUI extends DrawingGUI {
	private ImageProcessor proc;		// handles the image processing

	/**
	 * Creates the GUI for the image processor, with the window scaled to the to-process image's size
	 */
	public ImageProcessingGUI(ImageProcessor proc) {
		this.proc = proc;
		initWindow(proc.getImage().getWidth(), proc.getImage().getHeight());
	}

	/**
	 * DrawingGUI method, here showing the current image
	 */
	public void draw(Graphics g) {
		g.drawImage(proc.getImage(), 0, 0, null);
	}

	/**
	 * DrawingGUI method, here dispatching on image processing operations.
	 * Note that there are some magic numbers here that you can play with.
	 * While that's generally bad practice, it's just for simplicity in this hodge-podge of examples.
	 */
	public void handleKeyPress(char op) {
		System.out.println("Handling key '"+op+"'");
		if (op=='a') { // average
			proc.average(1);
		}
		else if (op=='b') { // brighten
			proc.brighten();
		}
		else if (op=='c') { // funky color scaling
			proc.scaleColor(1.25, 1.0, 0.75);
		}
		else if (op=='d') { // dim
			proc.dim();
		}
		else if (op=='f') { // flip
			proc.flip();
		}
		else if (op=='g') { // gray
			proc.gray();
		}
		else if (op=='h') { // sharpen
			proc.sharpen(3);
		}
		else if (op=='m') { // scramble
			proc.scramble(5);
		}
		else if (op=='n') { // noise
			proc.noise(20);
		}
		else if (op=='s') { // save a snapshot
			saveImage(proc.getImage(), "pictures/snapshot.jpg", "jpg");
		}
		
		
		/**
		 * Jack Cardwell added the border and exchange conditionals
		 * April 3, 2015
		 */
		
		//implement a condition to create a border
		else if (op==' '){ //create a border
			proc.border();
		}
		//implement the exchange method
		else if (op=='e'){//change color values
			proc.exchange();
		}
		else {
			System.out.println("Unknown operation");
		}

		repaint(); // Re-draw everything, since image has changed
	}

	/**
	 * Main method for the application
	 * @param args		command-line arguments (ignored)
	 */
	public static void main(String[] args) { 
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Load the image to process
				BufferedImage baker = loadImage("pictures/baker.jpg");
				// Create a new processor, and a GUI to handle it
				new ImageProcessingGUI(new ImageProcessor(baker));
			}
		});
	}
}
