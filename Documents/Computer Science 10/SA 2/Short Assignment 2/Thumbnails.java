import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
//updated by @author Jack Cardwell on April 5, 2015

/**
 * Thumbnail display of a set of slides.
 * Scaffold for SA-2
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 and Spring 2015
 */
public class Thumbnails extends DrawingGUI {
	private static final int trows = 3, tcols = 3; 	// setup: number of thumbnails per row and column

	private ArrayList<BufferedImage> slides; 		// the slides
	private ArrayList<BufferedImage> thumbs;		// thumbnail versions
	private int zoomedRow = -1, zoomedCol = -1; 		// selected slide; -1 for none
	private int thumbWidth, thumbHeight; 			// scaled size of thumbnails (computed)

	public Thumbnails(ArrayList<BufferedImage> images) {
		slides = images;		

		// Separate method handles thumbnail creation.
		createThumbs();

		initWindow(images.get(0).getWidth(), images.get(0).getHeight());
	}

	/**
	 * Creates the thumbnails as scaled-down versions of the images.
	 */
	private void createThumbs() {		
		thumbs = new ArrayList<BufferedImage>();
		thumbWidth = slides.get(0).getWidth()/tcols;
		thumbHeight = slides.get(0).getHeight()/trows;
		for (int i=0; i<trows*tcols; i++) {
			thumbs.add(scaleTo(slides.get(i), thumbWidth, thumbHeight));
		}
	}
	
	/**
	 * Crude scaling down of an image to a width and height
	 * @param image		image to scale
	 * @param width		scaled down width
	 * @param height	scaled down height
	 * @return			scaled image
	 */
	private static BufferedImage scaleTo(BufferedImage image, int width, int height) {
		BufferedImage result = new BufferedImage(width, height, image.getType());
		
		/*
		*lines 59-75 added by @author Jack Cardwell on April 5, 2015
		*/
		
		//determines the ratio of pixels from the smaller picture (new) to the larger picture (old)
		double xScale=image.getWidth()/width;
		double yScale=image.getHeight()/height;
		
		//for loop cycling through the pixels of the scaled image 
		for (int x=0; x<width; x++){
			for (int y=0; y<height; y++){
				
				//multiply the pixel that the loop is going through by the scale factor
				int scaledX=(int) (xScale*x);
				int scaledY=(int) (yScale*y);
				
				//get the color of the pixel from the scaled point on the original picture
				Color colorFromPicture= new Color(image.getRGB(scaledX, scaledY));
				
				//set the color from the scaled pixel value to the (x,y) pixel that is currently referenced within the loop
				result.setRGB(x, y, colorFromPicture.getRGB());
				
			}
		}
		return result;
	}

	/**
	 * Handle click on image, either selecting a thumbnail or going back to the thumbnails
	 * @param x
	 * @param y
	 */
	public void handleMousePress(int x, int y) {
		if (zoomedRow != -1) {
			// Zoom back out
			zoomedRow = -1;
		}
		else {
			// Zoom in to whichever thumbnail it was
			
			/*
			 * Lines 99-102 added by @Jack Cardwell on April 5, 2015
			 */
			
			//divide the value of the pixels by the conversion factor pixels per thumbWidth...or thumbHeight...to get to columns and rows
			
			zoomedRow= (int) (x/thumbWidth);
			zoomedCol= (int) (y/thumbHeight);
		
		}
		repaint();
	}

	/**
	 * Draws either the grid of images or the selected thumbnail.
	 */
	public void draw(Graphics g) {
		if (zoomedRow != -1) {
			// Show the selected slide.
			g.drawImage(slides.get(zoomedCol*tcols+zoomedRow), 0, 0, null);
		}
		else {
			// Lay out the thumbnails
			
			/*
			 * Lines 123-137 added by Jack Cardwell on April 5, 2015
			 */
			
			//circle through each of the BufferedImages in the ArrayList thumbs and draw them 
			
				int n=0;
				
				//change the position using a nested for loop
				for (int j=0; j<tcols; j++){
					for (int i=0; i< trows; i++){
					
						
								
						//draw the current thumbnail at i,j
						g.drawImage(thumbs.get(n), i*thumbWidth, j*thumbHeight, null);	
						
						n++;
					}
				}
		}
	}

	/**
	 * Main method for the application
	 * @param args		command-line arguments (ignored)
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Read the images, named dart0.jpg ... dart<numSlides>.jpg, and store in array.
				ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
				for (int i=0; i<trows; i++) {
					for (int j=0; j<tcols; j++) {
						images.add(loadImage("pictures/dart"+(i*tcols+j)+".jpg"));
					}
				}
				// Fire off the thumbnail viewer.
				new Thumbnails(images);
			}
		});
	}
}
