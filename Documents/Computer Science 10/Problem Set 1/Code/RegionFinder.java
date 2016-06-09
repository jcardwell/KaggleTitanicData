import java.awt.*;
import java.awt.image.*;
import java.util.*;

/*@author Jack Cardwell
 * Professor Bailey-Kellog
 * Problem Set 1
 * April 9, 2015
 */

/**
 * Region growing algorithm: finds and holds regions in an image.
 * Each region is a list of contiguous points with colors similar to a target color.
 * Template for PS-1, Dartmouth CS 10, Spring 2015
 * 
 * @author Chris Bailey-Kellogg, Winter 2014 (based on a very different structure from Fall 2012)
 * @author Travis W. Peters, Dartmouth CS 10, Updated Winter 2015
 * @author CBK, Spring 2015, updated for CamPaint
 */
public class RegionFinder {
	private static final int maxColorDiff = 20;				// how similar a pixel color must be to the target color, to belong to a region
	private static final int minRegion = 50; 				// how many points in a region to be worth considering

	private BufferedImage image;                            // the image in which to find regions
	private BufferedImage recoloredImage;  					// the image with identified regions recolored
	

	private ArrayList<ArrayList<Point>> regions;				// a region is a list of points
	// so the identified regions are in a list of lists of points

	public RegionFinder() {
		this.image = null;
		this.regions = new ArrayList<ArrayList<Point>>();
	}

	public RegionFinder(BufferedImage image) {
		this.image = image;
		this.regions = new ArrayList<ArrayList<Point>>();
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public ArrayList<ArrayList<Point>> getRegions() {
		return regions;
	}

	public BufferedImage getRecoloredImage() {
		return recoloredImage;
	}

	/**
	 * Sets regions to the flood-fill regions in the image, similar enough to the trackColor.
	 */
	public void findRegions(Color targetColor) {
		// TODO: YOUR CODE HERE
		regions=new ArrayList<ArrayList<Point>>();
		
		//create a black buffered image to tell which pixels are visited later on
		BufferedImage blank=new BufferedImage(image.getWidth(),image.getWidth(),image.getType());
		
		for (int i=0; i<blank.getWidth();i++){
			for(int j=0; j<blank.getHeight();j++){
		
				//turn all of the pixels black
				Color black=new Color(0,0,0);
				blank.setRGB(0,0,black.getRGB());
			}
		}
		
		//loop through all of the pixels of the image that we are examining
		for(int x=0; x<image.getWidth();x++){
			for(int y=0; y<image.getHeight(); y++){
				
				//create comparison colors
				Color examColor=new Color(image.getRGB(x,y));
				Color blankColor=new Color(blank.getRGB(x,y));	
				Color marked=new Color(1,0,0);
				
				//check to make sure the color at the point is the same as the target color
				//only do this if the pixel is still unvisited...black in the blank image, not red
				if (colorMatch(examColor, targetColor) && (blankColor.getRGB()!=marked.getRGB())){
					
					//create a new region that will hold all of the points that fit the color, so long as they are neighbors
					ArrayList<Point> newRegion=new ArrayList<Point>();
					
					//create a list to keep track of all of the points that need to be visited
					ArrayList<Point> toVisit=new ArrayList<Point>();
					toVisit.add(new Point(x,y));
					
					//create a loop that continues so long as there are pixels to visit in the toVisit list
					while (toVisit.size() > 0){
						
						Point currentVisit=(Point) toVisit.get(0);
						
						int currentVisitX=(int)currentVisit.getX();
						int currentVisitY=(int)currentVisit.getY();
						
						//store the colors at the point
						Color currentVisitColor= new Color(image.getRGB(currentVisitX, currentVisitY));
						Color colorFromBlank=new Color(blank.getRGB(currentVisitX, currentVisitY));
						Color Red=new Color(1,0,0);
						
						//determine if it matches the correct color
						if ((colorMatch(currentVisitColor , targetColor)) && (colorFromBlank.getRGB()!=Red.getRGB())){
							
							//if it does match the current color, add it to the region 
							newRegion.add(currentVisit);
							
							//mark it as visited by changing the color in the copied image
							blank.setRGB(currentVisitX, currentVisitY, Red.getRGB());
							
							//add all of it's eight neighbors to the to visit list, given the constraint of the window
							for (int i=currentVisitX-1; i<=currentVisitX+1; i++){
								for (int j=currentVisitY-1;  j<=currentVisitY+1; j++){
									
									//add point to the toVisit list as long as it is within the constraints of the image
									if(i > 0 && j > 0 && i < image.getWidth() && j < image.getHeight()){
										toVisit.add(new Point(i,j));
									}
								}
							}
						}
						//delete the currentVisit point from the list of toVisit
						toVisit.remove(0);
					}
					
					//add this region to the list of regions as long as it is big enough
					if(newRegion.size()>=minRegion){
						regions.add(newRegion);
					}
						
				}
			}
		}
		
	}

	/**
	 * Tests whether the two colors are "similar enough" (your definition, subject to the static threshold).
	 */
	private static boolean colorMatch(Color c1, Color c2) {
		// TODO: YOUR CODE HERE
		
		//as long as the colors are within a certain value of each other consider them the same
		//first find each of the differences in channels
		double redComparison=Math.abs(c1.getRed()-c2.getRed());
		double greenComparison=Math.abs(c1.getGreen()-c2.getGreen());
		double blueComparison=Math.abs(c1.getBlue()-c2.getBlue());
		
		
		//use a conditional to determine if each channel is within the accepted range 
		//first within the range
		if (redComparison<=maxColorDiff && greenComparison<=maxColorDiff && blueComparison<=maxColorDiff){
			return true;
		}
		
		//then outside of the acceptable range...not the same
		else{
			return false;
		}
	}

	/**
	 * Returns the largest region detected (if any region has been detected)
	 */
	public ArrayList<Point> findLargestRegion() {
		// TODO: YOUR CODE HERE
	    
		//initialize a variable to keep track of the largest region's size, initially 0, but will grow as the list is examined
		int compareLength=0;
		
		ArrayList<Point> largestRegion=null;
		
		//cycle through each of the regions within regions
		for (int n=0; n<regions.size();n++){
			
			//if the region is bigger than the compare length store it as the largest length
			if (regions.get(n).size()>compareLength){
				largestRegion=regions.get(n);
			}
		}
		//return the largest region
		return largestRegion;
	}

	/**
	 * Sets recoloredImage to be a copy of image, 
	 * but with each region a random uniform color, 
	 * so we can see where they are
	 */
	public void recolorRegions() {
		// First copy the original
		recoloredImage = new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);
		
		// Now re-color the regions
		
		// TODO: YOUR CODE HERE
		//cycle through all of the regions and paint each point in the region a uniform random color for the region
		for (int n=0; n<regions.size();n++){
			
			//make the color for this region, first as a double and then convert into an integer for the color constructor
			double RandomRed= (Math.random()*255);
			int RedColor=(int)RandomRed;
			
			double RandomGreen=(Math.random()*255);
			int GreenColor=(int)RandomGreen;
			
			double RandomBlue=(Math.random()*255);
			int BlueColor=(int)(RandomBlue);
			
			Color uniformColor=new Color(RedColor,GreenColor,BlueColor);
			
			//assign this color to all points within the current region
			for (int c=0; c<regions.get(n).size(); c++){
				
				//get the current point
				Point currentPoint=regions.get(n).get(c);
				
				recoloredImage.setRGB((int)currentPoint.getX(),(int) currentPoint.getY(),uniformColor.getRGB());
			}
		}
	}
}
