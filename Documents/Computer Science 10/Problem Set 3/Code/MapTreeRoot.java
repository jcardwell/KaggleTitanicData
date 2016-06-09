/**
 * 
 * @author Jack Cardwell
 *Professor Bailey-Kellogg
 *Computer Science 10
 *April 26, 2015
 */

//create a class that will store the elements of a map into a node for a binary tree
public class MapTreeRoot {
	private char charKey;		//the character of the map
	private int charFrequency;	//frequency of the character in the map

	//create the constructor for the MapTreeRoot class and store the parameters as instance variables to the object in this class
	public MapTreeRoot(char key, int value){
		charKey=key;
		charFrequency=value;
	}
	
	//create a constructor that is only for integers for the instance where we are combining our binary trees with frequencies as our data
	public MapTreeRoot(int value){
		charFrequency=value;
	}
	
	//create an accessor method for this class to return the character
	public char getCharacter(){
		return charKey;
	}
	
	//create an accessor method for this class to return the frequency
	public int getCharacterFrequency(){
		return charFrequency;
	}
}
