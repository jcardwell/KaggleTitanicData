import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;

/**
 * @author Jack Cardwell
 *Professor Bailey-Kellogg
 *Computer Science 10: Problem Set 4
 *May 13, 2015
 */
public class KevinBaconDriver {
	
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
	/**
	 * @author Jack Cardwell
	 * @param args
	 * @throws IOException
	 * the main method for the KevinBaconDriver which calls on other classes
	 */
	public static void main(String args[]) throws IOException{
		Graph<String,String> undirectedGraph =new AdjacencyMapGraph<String,String>();
		
		//collect the input files, first the actors data, then the movies data, and finally the movies-actors data
		String actorFile=getFilePath();
		String movieFile=getFilePath();
		String movieActorFile=getFilePath();
		
		//use the file sorter to create the undirected graph
		FileSorter run=new FileSorter(actorFile,movieFile,movieActorFile);
		
		//update the actor and movie maps
		run.createMap(run.actorFile, run.actorMap);
		run.createMap(run.movieFile, run.movieMap);
		
		//return the undirected graph
		undirectedGraph=run.createGraph(run.actorMovieFile);
		
		//implement a Boolean that determines whether the user would like to run the program
		boolean toRun=true;
		
		//prompt the user for input as to which actor to search for
		Scanner newActorScanner=new Scanner(System.in);
		
		while (toRun){
			System.out.println("Which actor would you like to search for?");
			String actorName=newActorScanner.nextLine();
			
			//exit the program if the user types "exit"
			if (actorName.equals("exit")){
				System.out.println("Program Terminated!");
				toRun=false;
			}
			//if the actor is in the database, print the progression and the Bacon number
			else if (undirectedGraph.hasVertex(actorName)){
				BreadthFirstSearch.findConnections("Kevin Bacon", actorName, undirectedGraph);
				System.out.println(" ");		//skips a line before prompting again
			}
			//otherwise the input was invalid. Repeat the loop
			else{
				System.out.println("Actor is not in the database. Please try again!");
			}
		}
		//close the scanner
		newActorScanner.close();
	}
}
