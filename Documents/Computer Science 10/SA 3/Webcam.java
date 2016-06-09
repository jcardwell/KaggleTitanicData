import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

import org.bytedeco.javacv.*;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

/**
 * Class to handle webcam capture and processing, packaging up JavaCV stuff.
 * Subclasses can conveniently process webcam video by extending this and overriding the processImage methods.
 * Since it's an extension of DrawingGUI, they can also override draw(), handleMousePress(), etc.
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Winter 2014
 * @author Travis Peters, Dartmouth CS 10, Winter 2015 -- updated for JavaCV 0.10
 * @author CBK, Spring 2015, integrated with DrawingGUI
 *
 */
public class Webcam extends DrawingGUI {
	private static final double scale = 0.5;		// to downsize the image (for speed), set this to a fraction <= 1
	private static final boolean mirror = true;		// make true in order to mirror left<->right so your left hand is on the left side of the image

	protected BufferedImage image;					// image grabbed from webcam (if any)

	private Grabby grabby;							// handles webcam grabbing
	private FrameGrabber grabber;					// JavaCV

	public Webcam() {
		super("Webcam");

		// Set up webcam
		grabber = new OpenCVFrameGrabber(0); 
		// Repeated attempts following discussion on javacv forum, fall 2013 (might be fixed internally in future versions)
		final int MAX_ATTEMPTS = 60;
		int attempt = 0;
		System.out.print("Initializing camera");
		while (attempt < MAX_ATTEMPTS) {
			System.out.print('.');
			attempt++;
			try {
				grabber.start();
				break;
			} 
			catch (Exception e) { }
		}
		if (attempt == MAX_ATTEMPTS) {
			System.err.println("Failed after "+attempt+" attempts");
			return;
		}
		System.out.println("done");

		// Get size and figure out scaling
		int width = grabber.getImageWidth();
		int height = grabber.getImageHeight();
		System.out.println("Native camera size "+width+"*"+height);
		if (scale != 1) {
			width = (int)(width*scale);
			height = (int)(height*scale);
			System.out.println("Scaled to "+width+"*"+height);
		}
		initWindow(width,height);

		// Spawn a separate thread to handle grabbing.
		grabby = new Grabby();
		grabby.execute();
	}

	/**
	 * Processes the grabbed image.
	 */
	public void processImage() {
		// Default: nothing
	}
	
	/**
	 * DrawingGUI method, here showing the current image.
	 */
	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}

	/**
	 * Handles grabbing an image from the webcam (following JavaCV examples)
	 * storing it in image, and telling the canvas to repaint itself.
	 */
	private class Grabby extends SwingWorker<Void, Void> {
		protected Void doInBackground() throws Exception {
			while (!isCancelled()) {
				IplImage grabbed = null;
				while (grabbed == null) {
					try {
						grabbed = grabber.grab();
					}	
					catch (Exception e) {
						Thread.sleep(100); // wait a bit
					}
				}
				if (mirror) {
					cvFlip(grabbed, grabbed, 1);
				}
				if (scale != 1) {
					IplImage resized = IplImage.create(width, height, grabbed.depth(), grabbed.nChannels());
					cvResize(grabbed, resized);
					grabbed = resized;
				}
				image = grabbed.getBufferedImage();
				processImage();
				canvas.repaint();
				Thread.sleep(100); // slow it down
			}
			// All done; clean up
			grabber.stop();
			grabber.release();
			grabber = null;
			return null;
		}
	}

	/**
	 * Main method for the webcam test application
	 * 
	 * @param args	ignored
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Webcam();
			}
		});
	}
}