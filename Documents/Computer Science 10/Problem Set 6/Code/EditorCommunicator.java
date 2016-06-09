import java.awt.Color;
import java.io.*;
import java.net.Socket;

/**
 * @authors Jack Heneghan and Jack Cardwell 
 * Computer Science 10: Problem Set 6
 * Handles communication to/from the server for the editor
 * Dartmouth CS 10, Winter 2015
 */
public class EditorCommunicator extends Thread {
	private PrintWriter out;		// to server
	private BufferedReader in;		// from server
	protected Editor editor;		// handling communication for

	/**
	 * Establishes connection and in/out pair
	 */
	public EditorCommunicator(String serverIP, Editor editor) {
		this.editor = editor;
		System.out.println("connecting to " + serverIP + "...");
		try {
			Socket sock = new Socket(serverIP, 4242);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			System.out.println("...connected");
		}
		catch (IOException e) {
			System.err.println("couldn't connect");
			System.exit(-1);
		}
	}

	/**
	 * Sends message to the server
	 */
	public void send(String msg) {
		out.println(msg);
	}

	/**
	 * Keeps listening for and handling (your code) messages from the server
	 */
	public void run() {
		try {
			// Handle messages
			// TODO: YOUR CODE HERE
			//get the first line that comes in from the server and continue reading so long as the currentline isn't empty
			String currentLine=in.readLine();
			while(currentLine!=null){
				//use the messages class to parse the line
				Messages newMessage = new Messages(currentLine);
				int x1=newMessage.x1;
				int x2=newMessage.x2;
				int y1=newMessage.y1;
				int y2=newMessage.y2;
				
				//if the user requests an add, add the object to the local editor sketch 
				if (newMessage.commandType.compareTo("add")==0){
					if (newMessage.objectType.compareTo("ellipse")==0){
						Ellipse newObject= new Ellipse(x1,y1,x2,y2,newMessage.setColor);
						editor.getSketch().add(newObject);
					}
					else if (newMessage.objectType.compareTo("rectangle")==0){
						Rectangle newObject=new Rectangle(x1,y1,x2,y2,newMessage.setColor);
						editor.getSketch().add(newObject);
					}
					else if (newMessage.objectType.compareTo("segment")==0){
						Segment newObject=new Segment(x1,y1,x2,y2,newMessage.setColor);
						editor.getSketch().add(newObject);
					}
				}
				
				//if the user requests a delete use the sketch method to set that object to null
				else if (newMessage.commandType.compareTo("delete")==0){
					editor.getSketch().remove(newMessage.index);
				}
				
				//if the user requests a recolor, use the shape method to reset the color
				else if (newMessage.commandType.compareTo("recolor")==0){
					editor.getSketch().get(newMessage.index).setColor(newMessage.setColor);
				}
				
				//if the user requests a move, use the sketch method to move the shape
				else if (newMessage.commandType.compareTo("move")==0){
					editor.getSketch().move(newMessage.index,newMessage.moveX,newMessage.moveY);
				}
				editor.repaint();
				//read the next line of commands from the server
				currentLine=in.readLine();
			}
		
			}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("server hung up");
		}
	}	
	// Send editor requests to the server
	// TODO: YOUR CODE HERE
	
	//all of these methods are called upon within editor to establish communication between the editor and the server
	
	//method that sends an add message to the server
	public void requestAdd(Shape shape){
		send(shape.toString()+" add");
	}
	
	//method that sends a delete message to the server
	public void requestDelete(Shape shape, int idx){
		send(shape.toString()+ " delete "+idx);	
	}
	
	//method that sends a move message to the server
	public void requestMove(Shape shape, int idx, int moveX, int moveY){
		send(shape.toString()+ " move "+idx+" " + moveX + " "+moveY);
	}
	
	//method that sends a recolor message to the server
	public void requestRecolor(Shape shape, int idx){
		send(shape.toString()+ " recolor "+idx);
		
	}
}

