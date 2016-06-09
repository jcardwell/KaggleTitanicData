/* @author Jack Cardwell
 * Professor Bailey-Kellogg
 * Short Assignment 3
 * April 8, 2015
 * referenced @author Chris Bailey-Kellog
 */

//import all of the necessary color and graphics information
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

//create the public class Warp that extends DrawingGUI
public class Warp extends Webcam {
	
	//create the necessary instance variables
	private int cx=0;
	private int cy=0;
	private double distance;
	private int angleMultiplier=0;
	private double angleFactor=((Math.PI)/16);
	
	
	//handle the mouse press
	public void handleMousePress (int x, int y){
			
			//set the new center to where the mouse was clicked
			cx=x;
			cy=y;	
			
			//print that the new center was set
			System.out.println("New center set at mouse-click");
			
		}
	
	//use the keys to determine if to increase the angle by more or less 
	public void handleKeyPress (char key){
		
		if (key=='m'){
			//print to the console how the keypress increased the angle by a factor of pi/16 and change the factor
			System.out.println("Increasing the angle by a factor of Pi/16!");
			angleMultiplier++;
			
			
		}
		
		else if (key=='l'){
			//print to the console and decrement the factor by Pi/16
			System.out.println("Decreasing the angle by a factor of Pi/16!");
			angleMultiplier--;	
			
		}
	}
	

	public void processImage(){
		
		//create a Buffered Image that stores a "snapshot" of the image
		BufferedImage product=new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);
		
		//create the limitation based on the size of the image
		int sizeOfImage=Math.max(image.getWidth(), image.getHeight());
		
		//create a counter to go through all of the pixels in the original image
		for (int x=0; x<image.getWidth(); x++){
			for (int y=0; y<image.getHeight(); y++){
				
				//compute the distance by taking the square root of dx^2+dy^2 where dx=x-cx and dy=y-cx
				int dx=x-cx;
				int dy=y-cy;
				
				double distance=(Math.sqrt((dx*dx)+(dy*dy)));	
				
				//compute the angleAdjustor, adjusted for both distance and the user keypress
				double angleAdjustor=(angleMultiplier*angleFactor)*(distance / sizeOfImage);
				
				//compute the angle, made by the point (x,y) and (cx,cy)
				double originalAngle=Math.atan2(dy,dx);
				
				//adjust the angle by adding the angleAdjustor to the originalAngle
				double sourceAngle= originalAngle + angleAdjustor;
				
				//find the source distance in x and y using the sin, convert from a double to an int
				double sourceDistanceY=(distance*Math.sin(sourceAngle));
				double sourceDistanceX=(distance*Math.cos(sourceAngle));
				
				//add the distance to cx and cy to get sourceX and sourceY
				int sourceX=(int) ImageProcessor.constrain(cx + sourceDistanceX, 0, image.getWidth()-1);
				int sourceY=(int) ImageProcessor.constrain(cy + sourceDistanceY,0, image.getHeight()-1);
				
				//set the new color
				product.setRGB(x, y, image.getRGB(sourceX,sourceY));
				
			}
		}

		//replace the old image with the edited image
		image=product;
	}

	//write the main method
	public static void main (String[]args){
		SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				new Warp();
			}
		});
	}
}

