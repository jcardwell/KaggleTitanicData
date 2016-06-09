import java.awt.Color;
import java.io.*;
import java.net.Socket;

/**
 * @authors Jack Heneghan and Jack Cardwell
 * Computer Science 10: Problem Set 6
 * Handles communication between the server and one client, for SketchServer
 * Dartmouth CS 10, Winter 2015
 */
public class SketchServerCommunicator extends Thread {
	private Socket sock;					// to talk with client
	private BufferedReader in;				// from client
	private PrintWriter out;				// to client
	private SketchServer server;			// handling communication for

	public SketchServerCommunicator(Socket sock, SketchServer server) {
		this.sock = sock;
		this.server = server;
	}

	/**
	 * Sends a message to the client
	 * @param msg
	 */
	public void send(String msg) {
		out.println(msg);
	}
	
	/**
	 * Keeps listening for and handling (your code) messages from the client
	 */
	public void run() {
		try {
			System.out.println("someone connected");
			
			// Communication channel
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);

			// Tell the client the current state of the world
			// TODO: YOUR CODE HERE
			
			//go through all of the shapes in the master shapelist and tell the new editor 
			//to add the shapes to its local version
			for (Shape toSend:server.getSketch().shapeList){
				send(toSend.toString()+" add");
			}
			
			// Keep getting and handling messages from the client
			// TODO: YOUR CODE HERE
			String currentLine=in.readLine();
			while (currentLine!=null){
				
				//send the command from the one editor to all of the editor communicators that have a connection
				server.broadcast(currentLine);
				
				//use the messages class to parse the command line into meaningful information
				Messages newMessage = new Messages(currentLine);
				int x1=newMessage.x1;
				int x2=newMessage.x2;
				int y1=newMessage.y1;
				int y2=newMessage.y2;
				
				//if the editor requests an add, also add the shape to the master sketch
				if (newMessage.commandType.compareTo("add")==0){
					if (newMessage.objectType.compareTo("ellipse")==0){
						Ellipse newObject= new Ellipse(x1,y1,x2,y2,newMessage.setColor);
						server.getSketch().add(newObject);
					}
					else if (newMessage.objectType.compareTo("rectangle")==0){
						Rectangle newObject=new Rectangle(x1,y1,x2,y2,newMessage.setColor);
						server.getSketch().add(newObject);
					}
					else if (newMessage.objectType.compareTo("segment")==0){
						Segment newObject=new Segment(x1,y1,x2,y2,newMessage.setColor);
						server.getSketch().add(newObject);
					}
				}
				
				//remove the object from the master sketch
				else if (newMessage.commandType.compareTo("delete")==0){
					server.getSketch().remove(newMessage.index);
				}
				
				//recolor the object in the master sketch
				else if (newMessage.commandType.compareTo("recolor")==0){
					server.getSketch().get(newMessage.index).setColor(newMessage.setColor);
				}
				
				//move the object in the master sketch
				else if (newMessage.commandType.compareTo("move")==0){
					server.getSketch().move(newMessage.index,newMessage.moveX,newMessage.moveY);
				}
				
				//update the current line
				currentLine=in.readLine();
			}

			
			// Clean up -- note that also remove self from server's list so it doesn't broadcast here
			server.removeCommunicator(this);
			out.close();
			in.close();
			sock.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
