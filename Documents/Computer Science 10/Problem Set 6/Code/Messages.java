import java.awt.Color;

/**
 * @author Jack Heneghan and Jack Cardwell
 * Computer Science 10: Problem Set 6
 * helps parse messages between the server communicator and the editor communicator
 */
public class Messages {
	public String[] tokens;			//the whole input line, split by spaces
	String objectType;			//the first input in the line, the type of shape
	String commandType;			//what the command is for the shape	
	int x1; int x2; int y1; int y2;	//the position values for the shape
	Color setColor;			//the color of the shape
	int index;				//only in delete, move, and recolor
	int moveX; int moveY;	//only in move
	
	//constructor for the messages class
	public Messages(String currentLine){
		
		//split the line by spaces
		String[] tokens=currentLine.split(" ");
		
		//the type of shape is the first thing passed in the command line
		objectType=tokens[0];
		
		//turn the input position values into integers from strings
		x1=Integer.parseInt(tokens[1]);
		y1=Integer.parseInt(tokens[2]);
		x2=Integer.parseInt(tokens[3]);
		y2=Integer.parseInt(tokens[4]);
		
		//create a new color to hold onto the integer color passed in
		setColor=new Color(Integer.parseInt(tokens[5]));
		commandType = tokens[6];
		
		//if the command is move or delete, the user passes in an index
		if (tokens.length>=8){
			index = Integer.parseInt(tokens[7]);
		}
		
		//if the command is move, the command is longer and contains an x coordinate and y coordinate to move to
		if (tokens.length==10){
			moveX = Integer.parseInt(tokens[8]);
			moveY = Integer.parseInt(tokens[9]);
		}
	}
	
}
