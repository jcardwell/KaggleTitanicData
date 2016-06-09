import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Webcam-based drawing 
 * Sample solution to PS-1, Dartmouth CS 10, Spring 2015
 * 
 * @author Chris Bailey-Kellogg, Spring 2015 (based on a different webcam app from previous terms)
 */
public class CamPaint extends Webcam {
	private char display = 'w';				// what to display: 'p': painting, 'r': regions, 'w': webcam
	private RegionFinder finder;				// handles the finding
	private Color targetColor;          		// color of regions of interest (set by mouse press)
	private Color paintColor = Color.blue;	// the color to put into the painting from the "brush"
	private BufferedImage painting;			// the resulting masterpiece
	private int regionRed=102;				//red value of the largest region
	private int regionGreen=255;			//green value of the largest region
	private int regionBlue=102;				//blue value of the largest region
	private boolean enableDraw=true;		//initialize a boolean that will pause and restart a painting
	/**
	 * Initializes the region finder and the drawing
	 */
	public CamPaint() {
		finder = new RegionFinder();
		clearPainting();
	}

	/**
	 * Resets the painting to a blank image
	 */
	protected void clearPainting() {
		painting = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	/**
	 * Overrides the DrawingGUI method to draw one of webcam, recolored image, or painting, 
	 * depending on display variable ('w', 'r', or 'p')
	 */
	public void draw(Graphics g) {
		// TODO: YOUR CODE HERE
		
		//display the webcam if the w key is pressed
		if(display=='w'){
			g.drawImage(image,0,0,null);
		}
		
		//display the painting if the p key is pressed
		else if(display=='p'){
			g.drawImage(painting,0,0,null);
		}
		
		//display the recolored image if the r key is pressed
		else if(display=='r'){
			g.drawImage(finder.getRecoloredImage(),0,0,null);
		}
		}
	

	/**
	 * Overrides Webcam method to do the region finding and update the painting.
	 */
	public void processImage() {
		// TODO: YOUR CODE HERE
		if (targetColor!=null){
			//find all regions of the targetColor, which returns an array list of points regions
			
			//set the image to the default webcam in order to isolate the regions
			finder.setImage(image);
			finder.findRegions(targetColor);
			
			//recolor the regions of the image
			finder.recolorRegions();
			
			//find and store the largest region into largest region
			ArrayList<Point> largestRegion=finder.findLargestRegion();
			
			if (largestRegion!=null){
				//go through all of the points in the largestRegion and make them a different color
				for (Point currentPoint : largestRegion){
				
					//set the color at this point to the colors defined at the start of this class
					Color largestRegionColor=new Color(regionRed,regionGreen,regionBlue);
					
					painting.setRGB(currentPoint.x,currentPoint.y,largestRegionColor.getRGB());
				}
			}	
		}	
	}

	/**
	 * Overrides the DrawingGUI method to set the track color.
	 */
	public void handleMousePress(int x, int y) {
		// TODO: YOUR CODE HERE
		
		//find the color of the point where the user clicks
		Color clickedColor=new Color(image.getRGB(x,y));
		
		//store this color to the variable for targetColor
		targetColor=clickedColor;
	}

	/**
	 * DrawingGUI method, here doing various drawing commands
	 */
	public void handleKeyPress(char k) {
		if (k == 'p' || k == 'r' || k == 'w') { // display: painting, regions, or webcam
			display = k;
		}
		else if (k == 'c') { // clear
			clearPainting();
		}
		else if (k == 'o') { // save the regions
			saveImage(finder.getRecoloredImage(), "pictures/regions.png", "png");
		}
		else if (k == 's') { // save the drawing
			saveImage(painting, "pictures/webdraw.png", "png");
		}

		else System.out.println("unhandled key "+k);
	}

	/**
	 * Main method for the application
	 * @param args      command-line arguments (ignored)
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CamPaint();
			}
		});
	}
}

