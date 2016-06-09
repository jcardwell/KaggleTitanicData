import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import javax.swing.JFileChooser;


/**@author Jack Cardwell
 * Professor Bailey-Kellog
 * Computer Science 10: Problem Set 3
 * April 29, 2015
 */

public class Compressor {
	private HashMap<Character, Integer> frequencyTable;						//private map of the characters and their frequencies from the map
	private PriorityQueue<BinaryTree<MapTreeRoot>> characterPriority;		//private priority queue of all of the characters
	private BinaryTree<MapTreeRoot> HuffmanTree;							//private Huffman tree of codeWord paths and their characters
	private HashMap<Character,String> codeMap;								//map that has a character and the string of the ints of its codeWord
	
	/**@author Jack Cardwell
	 * @parameters string of file Path Name
	 * constructor for the Compressor class that runs through all of the functions that set up the compress and decompress methods
	 * @throws IOException 
	 */
	public Compressor(String q) {
		try {
			getCharacterFrequencies(q);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createPriorityQueue();
		createHuffmanTree();
		codeMap = getEncodedMap();
	}
	
	/**method taken from Problem Set 3 Handout from Professor Bailey-Kellogg
	 * puts up a fileChooser and gets pathname for file to be opened.
	 * returns an empty string if the user clicks cancel
	 * @return path name of the file chosen
	 */
	public static String getFilePath() {
		JFileChooser fc=new JFileChooser("."); //started at current dictionary
		
		int returnVal= fc.showOpenDialog(null);
		if(returnVal==JFileChooser.APPROVE_OPTION) {
			File file= fc.getSelectedFile();
			String pathName= file.getAbsolutePath();
			return pathName;
		}
		else 
			return "";
	}
	
	/**create a method to read through the uncompressed file and store the bits and frequencies to a map
	 * @author Jack Cardwell
	 * @param string of the file path name
	 * @return frequencyTable as a HashMap
	 * @throws IOException 
	 */
	public void getCharacterFrequencies(String pathName) throws IOException{
		
		//initialize the frequencyTable
		frequencyTable=new HashMap<Character,Integer>();
		
		//use the BufferedReader class to access the read function in order to access characters in the file
		BufferedReader charReader=new BufferedReader(new FileReader(pathName));
		
		//intitialize the current character and the integer value of that read character
		char currentLetter;
		int read;
		
		//go through the text so long as the read method doesn't return -1, indicating the end of the file
		while((read = charReader.read()) != -1){
			
			//convert int value of character to character type
			currentLetter = (char) read;
			
			//if currentLetter is not in the frequencyTable, add it to the HashMap frequencyTable
			if (frequencyTable.containsKey(currentLetter)==false){
				frequencyTable.put(currentLetter, 1);
			}
			
			//otherwise increment the map's value
			else{
				frequencyTable.put(currentLetter, frequencyTable.get(currentLetter)+1);
			}
		} 
		
		//close the charReader
		charReader.close(); 
	
	}
	
	/**
	 * turns the map of the cahracters and their frequencies into a queue
	 * @author Jack Cardwell
	 * @return priority queue of all of the characters and their frequencies
	 */
	public void createPriorityQueue(){
		
		//create the new priority queue that was mentioned at the top of the class, starting with the compareFunction from TreeComparator
		Comparator<BinaryTree<MapTreeRoot>> compareFunction=new TreeComparator<MapTreeRoot>();
		characterPriority=new PriorityQueue<BinaryTree<MapTreeRoot>>(frequencyTable.size(),compareFunction);
		
		//go through all of the elements in the map and store them as MapTreeRoots
		for (char key:frequencyTable.keySet()){
			
			//store the key and value in a MapTreeRoot
			MapTreeRoot node=new MapTreeRoot(key,frequencyTable.get(key));
			
			//store the current node as a binary tree with itself as the only node
			BinaryTree<MapTreeRoot> currentCharacterTree=new BinaryTree<MapTreeRoot>(node);
			
			//add the currentCharacterTree into the priority queue
			characterPriority.add(currentCharacterTree);
		}
	}
	
	/**
	 * @author Jack Cardwell
	 * @returns a sorted Frequency Tree of the characters
	 */
	public void createHuffmanTree(){
		
		//continue iterating through the following steps so long as the priority queue has more than one tree in it
		while (characterPriority.size()>1){
			
			//save the lowest two binary trees into temporary trees
			BinaryTree<MapTreeRoot> L1=characterPriority.remove();
			BinaryTree<MapTreeRoot> L2=characterPriority.remove();
			
			//construct a node of type MapTreeRoot in order to add nodes to it
			MapTreeRoot frequencyHead=new MapTreeRoot(L1.getData().getCharacterFrequency()+L2.getData().getCharacterFrequency());
			
			//create a new tree by attaching L1 and L2 as the left and right subtrees, with the sum of their frequencies as the value of the node
			BinaryTree<MapTreeRoot> newTreeNode=new BinaryTree<MapTreeRoot>(frequencyHead, L1, L2);
			
			//insert this new tree node back into the priority queue
			characterPriority.add(newTreeNode);
		}
		//the last element in the priority queue is the Huffman tree so remove the final tree and store it into a BinaryTree
		HuffmanTree=characterPriority.remove();
	}
	
	/**
	 * @author Jack Cardwell
	 * helper function for the getEncodedMap function
	 * @param input the Huffman Tree
	 * @param codeWord initially an empty string that will update the codeword
	 * @param codeMap that will eventually hold the characters and their codewords
	 * @return a completed codeMap
	 */
	public HashMap<Character,String> findPath(BinaryTree<MapTreeRoot> input, String codeWord, HashMap<Character,String> codeMap){
		
		//the base case is that you are at a leaf, in which case do not add to the string and add the character and code word to the map
		if(input.isLeaf()){
			codeMap.put(input.getData().getCharacter(),codeWord);
		}
		
		//check to see if there is a right child and then add 1 and recurse on that element
		if(input.hasRight()){
			//add 1 to the codeWord string
			findPath(input.right,codeWord+"1",codeMap);
		}
		
		//check to see if there is a left child and then add 0 recurse on that element
		if(input.hasLeft()){
			//add 0 to the codeWord string
			findPath(input.left,codeWord+"0",codeMap);
		}
		
		//at the end of all of the recursive calls, return the map
		return codeMap;
	}
	
	/**@author Jack Cardwell
	 * uses the helper functino findPath to recursively find the codeWord values of characters from the Huffman Tree  
	 * @return the map of characters and their codes
	 */
	public HashMap<Character,String> getEncodedMap(){
		
		//create an empty HashMap that will store a character and its binary code word
		HashMap<Character,String> codeMap=new HashMap<Character,String>();
		
		//create an empty string that will keep track of the 0's and 1's that represent the direction of traversal
		String codeWord="";
		
		//store the codeMap which is the return of the helper function
		codeMap=findPath(HuffmanTree, codeWord,codeMap);
		
		//return the codeMap
		return codeMap;
	}
	
	/**
	 * @author Jack Cardwell
	 * compresses a normal text file into a series of bits
	 * @param input--the text file that is being read
	 * @param output--the bit file to write to 
	 * @throws IOException
	 */
	public void compress(String input, String output) throws IOException{
		
		//open up the readFrom file and the write to file
		BufferedReader inputFile=new BufferedReader(new FileReader(input));
		BufferedBitWriter bitOutput= new BufferedBitWriter(output);
		
		//read through all of the characters in the read-in file that are represented by the character currentLetter marked by the integer read
		char currentLetter;
		int read;
		
		//go through the text so long as the read method doesn't return -1, indicating the end of the file
		while((read = inputFile.read()) != -1){
			
			//store the current character into a temporary variable
			currentLetter = (char) read;
			
			//store the binary codeWord that corresponds to the currentLetter
			String binaryOutput=codeMap.get(currentLetter);
			
			//go through each part of the string and convert it to an int, in order to pass into the BufferedBitWriter
			for (int n=0;n<binaryOutput.length();n++){
				
				//find number at the current point of the string and convert it to a string
				String currentIntBit=Character.toString(binaryOutput.charAt(n));
				
				//convert the number into an int that can be passed into the writeBit function of the BufferedBitWriter class
				bitOutput.writeBit(Integer.parseInt(currentIntBit));
			}
		}
		//close the input file
		inputFile.close();
		
		//close the bitOutput file
		bitOutput.close();
	}

	/**
	 * @author Jack Cardwell
	 * @param compressedFile
	 * @throws IOException
	 * translates bits into characters and writes them into a file
	 */
	public void decompress(String compressedFile, String DecompressedFileName) throws IOException{
		
		//initialize a BufferedBit reader and the Buffered Writer, store the original Huffman Tree to refer back to later
		BufferedBitReader newBitReader=new BufferedBitReader(compressedFile);
		BufferedWriter outputWriter=new BufferedWriter(new FileWriter(DecompressedFileName));
		BinaryTree<MapTreeRoot> wholeTree=HuffmanTree;
		
		//store the bit that is currently being read into a variable for the conditionals later
		int currentBit=newBitReader.readBit();
		
		//continue so long as there are bits to read
		while (currentBit!=-1){
			
			//go down the right of the Huffman Tree
			if(currentBit==1){
				HuffmanTree=HuffmanTree.right;
			}
			
			//go down the left of the Huffman Tree
			else if(currentBit==0){
				HuffmanTree=HuffmanTree.left;
			}
			
			//if the current position in the tree is the leaf, print the character stored at that position
			if (HuffmanTree.isLeaf()){
				
				//find the current character to write into the output file
				char toPrint=HuffmanTree.getData().getCharacter();
				
				//write the character into the output file
				outputWriter.write(toPrint);
				
				//reset the Huffman Tree to the beginning
				HuffmanTree=wholeTree;
			}
			
			//read the next character in the file
			currentBit=newBitReader.readBit();
		}	
		//close the bitReader file and the output file
		newBitReader.close();
		outputWriter.close();
	}
	
	/**
	 * @author Jack Cardwell
	 * main method for the Compressor Class
	 * @param args
	 */
	public static void main(String args[]){
		//get all of the file paths
		String inputFilePath=getFilePath();			//the original to be compressed
		String outputFilePath=getFilePath();		//the compressed version of the original
		String finalTextPath=getFilePath();			//the decompressed version
		
		//initialize the compressor class, which will run through all of the functions as defined by the constructor
		Compressor n=new Compressor(inputFilePath);
		
		//run through the compression
		try {
			n.compress(inputFilePath,outputFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//run through the decompression
		try {
			n.decompress(outputFilePath,finalTextPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
