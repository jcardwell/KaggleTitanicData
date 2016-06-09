import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;

/**
 * @author Jack Cardwell
 * Professor Bailey-Kellogg
 * Computer Science 10: Problem Set 4
 * May 11, 2015
 */

public class FileSorter {
	protected String actorFile;			//name of the file that holds actors
	protected String movieFile;			//name of the file that holds movies
	protected String actorMovieFile;	//name of the file that holds actor ids and movie ids
	protected Map<String,String> movieMap; //map of movies and their ids
	protected Map <String, String> actorMap; //map of actors and their ids
	
	/**
	 * @author Jack Cardwell
	 * @param actor
	 * @param movie
	 * @param actorMovie
	 */
	public FileSorter(String actor, String movie, String actorMovie){
		actorFile=actor;			
		movieFile=movie;
		actorMovieFile=actorMovie;
		actorMap=new HashMap<String,String>();
		movieMap=new HashMap<String,String>();
	}
	
	/**
	 * @author Jack Cardwell
	 * @throws IOException
	 * updates the Map of movies/actors and their ids that belongs to the FileSorter object
	 */
	public void createMap(String fileName,Map<String,String> objectMap) throws IOException{
		//open up the text file and read the first line
		FileReader reader=new FileReader(fileName);
		BufferedReader currentFile=new BufferedReader(reader);
		String newLine=currentFile.readLine();
		
		//continue reading the lines until the end of the file
		while(newLine!=null){
			//add to the map the movies and their ids given by the split line
			String [] splitList=newLine.split("\\|");
			objectMap.put(splitList[0],splitList[1]);
			newLine=currentFile.readLine();
		}
		currentFile.close();
	}
	
	/**
	 * @author Jack Cardwell
	 * examines a file and creates an undirected graph of actors, from their movies
	 * @throws IOException 
	 * @returns a Graph of actors, with their movies as edges
	 */
	public Graph<String, String> createGraph(String movieActorFile) throws IOException{
		//creates an empty graph that will hold actors as vertices and movies as edges
		Graph<String,String> undirected=new AdjacencyMapGraph<String,String>();
		//create an empty Map to hold movies to a list of their Actors;
		Map <String, ArrayList<String>> movieActorMap=new HashMap<String,ArrayList<String>>();
		
		//add all of the actors from the actorMap as vertices in the Map
		for (String actorID:actorMap.keySet()){
			//add the name, the value, not the id, which is the key, as a vertex
			undirected.insertVertex(actorMap.get(actorID));
		}
		
		//open a the file of movies and actors and create a map of movies to a string of their actors
		FileReader reader=new FileReader(movieActorFile);
		BufferedReader currentFile=new BufferedReader(reader);
		String newLine=currentFile.readLine();
		
		//continue adding to the map of strings and ArrayLists while the line is not empty
		while(newLine!=null){
			//split the data into strings
			String [] splitList=newLine.split("\\|");
			
			//only add the new key to the map if it doesn't already exist there
			if (movieActorMap.containsKey(movieMap.get(splitList[0]))==false){
				//create an arraylist of actors and add the movie to the map with the list as its value
				ArrayList<String> actorsList=new ArrayList<String>();
				actorsList.add(actorMap.get(splitList[1]));
				movieActorMap.put(movieMap.get(splitList[0]),actorsList);
			}
			//otherwise, the key already exists so just add the actor to the actorList
			else{
				movieActorMap.get(movieMap.get(splitList[0])).add(actorMap.get(splitList[1]));
			}
			//increment the loop by reading the next line
			newLine=currentFile.readLine();
		}
		//close the file
		currentFile.close();
		//go through all of the keys and their lists adding edges between all of the actors 
		for (String key:movieActorMap.keySet()){
			for(int n=0; n<movieActorMap.get(key).size();n++){
				for(int i=n+1;i<movieActorMap.get(key).size();i++){
					undirected.insertUndirected(movieActorMap.get(key).get(n),movieActorMap.get(key).get(i),key);
				}
			}
		}
		return undirected;
	}

}

