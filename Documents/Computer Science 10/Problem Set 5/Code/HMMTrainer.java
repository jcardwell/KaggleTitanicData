import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;

/**
 * @author Jack Cardwell
 * Professor Bailey-Kellogg
 * Computer Science 10: Problem Set 5
 * May 20, 2015
 */

public class HMMTrainer {
	protected Map<String,Map<String,Double>> emissionProbs;		//map of the probabilities of the emissions
	protected Map<String,Map<String,Double>> transitionProbs;		//map of the probabilities of the transitions
	protected ArrayList<String[]> wordList;
	protected ArrayList<String[]> tagList;
	
	/**
	 * @author Jack Cardwell
	 * constructor for the HMM Trainer class that binds all of these specific maps and lists to the specific object
	 */
	public HMMTrainer(){
		
		//create a new ArrayList that will hold all of the words and one to hold all of the tags
		wordList=new ArrayList<String[]>();
		tagList=new ArrayList<String[]>();
		
		//create two Maps of emissions frequencies and transition frequencies
		emissionProbs=new HashMap<String,Map<String,Double>>();
		transitionProbs=new HashMap<String,Map<String,Double>>();
	}
	
	/**
	 * @author Jack Cardwell
	 * updates lists that are the result of splitting the file
	 * @throws IOException 
	 */
	public void getLists(String wordFile, String tagsFile) throws IOException{
		
		//open up the new FileReaders and read through the words and the tags
		FileReader reader=new FileReader(wordFile);
		BufferedReader currentFile=new BufferedReader(reader);
		String newLine=currentFile.readLine();
		
		//only read so long as the line isn't empty
		while (newLine!=null){
			String [] splitList=newLine.split(" ");
			wordList.add(splitList);
			newLine=currentFile.readLine();
		}
		//read the second file of all of the tags
		FileReader tagReader=new FileReader(tagsFile);
		BufferedReader secondFile=new BufferedReader(tagReader);
		String tagLine=secondFile.readLine();
		
		//continue to read through the next file until it's empty
		while (tagLine!=null){
			String[] splitLine=tagLine.split(" ");
			tagList.add(splitLine);
			tagLine=secondFile.readLine();
		}
		//close the two files
		currentFile.close();
		secondFile.close();
	}
	
	/**
	 * @author Jack Cardwell
	 * updates the emissionProbs map
	 */
	public void getEmissionsProbability(){
		for (int i=0;i<tagList.size();i++){
			
			//go through all of the words in the lists of tags and words
			for	(int j=0;j<tagList.get(i).length;j++){
				
				//check to see if the tag is already in the map 
				//if the key is already in the map, check to see if the word is in the map of that key
				if (emissionProbs.containsKey(tagList.get(i)[j])){
					
					//the map that will be linked to the current key
					Map<String,Double> innerMap=emissionProbs.get(tagList.get(i)[j]);
					
					//if the word is already in the inner map, just increment the counter
					if (emissionProbs.get(tagList.get(i)[j]).containsKey(wordList.get(i)[j])){
						innerMap.put(wordList.get(i)[j], emissionProbs.get(tagList.get(i)[j]).get(wordList.get(i)[j])+1);
					}
					
					//if the word isn't already in the inner map, set the counter to 1
					else{
						innerMap.put(wordList.get(i)[j], 1.0);
					}
					//place the inner map as the value of the larger key
					emissionProbs.put(tagList.get(i)[j],innerMap);
				}
				
				//if the tag isn't in the map already, add it to the map
				else {
					Map<String,Double> innerMap=new HashMap<String,Double>();
					innerMap.put(wordList.get(i)[j], 1.0);
					emissionProbs.put(tagList.get(i)[j],innerMap);
				}
			}
		}
		//go back through the map and convert the counters into probabilities
		for (String key:emissionProbs.keySet()){
			//initialize the number of tag occurances to 0 at the start of a new tag
			double numOccurances=0.0;
			
			//get the map value of the tag key
			Map<String,Double> innerMap=emissionProbs.get(key);
			
			//sum up all of the occurances of the tag
			for (String innerKey:emissionProbs.get(key).keySet()){
				numOccurances+=emissionProbs.get(key).get(innerKey);
			}
			
			//go back through all of the words and divide by the tag's number of occurances
			for(String innerKey:emissionProbs.get(key).keySet()){
				innerMap.put(innerKey, Math.log((emissionProbs.get(key).get(innerKey)/numOccurances)));
			}
			//put the new inner map into the emissionsProbs
			emissionProbs.put(key, innerMap);
		}
	}
	/**
	 * @author Jack Cardwell
	 * updates the transitionProbs map
	 */
	public void getTransitionsProbabilities(){
		for (String[] tagArray:tagList){
		//initialize the previous tag to a star, to mark the beginning of a sentence
		String previous="*";
		
			//go through all of the tags in the tagList
			for (String tag:tagArray){
				//set the current tag to the current tag in the loop
				String current=tag;
				
				//check to see if the previous tag is already a key in the transitionProbs map
				//if the tag exists, check to see if the current tag that the previous tag points to exists
				if (transitionProbs.containsKey(previous)){
					//the inner Map that has the tags being pointed to as well as how many times they appear
					Map<String,Double> innerMap=transitionProbs.get(previous);
					
					//if the current tag is a value to the previous tag key, only increment its value
					if (transitionProbs.get(previous).containsKey(current)){
						innerMap.put(current, transitionProbs.get(previous).get(current)+1.0);
						}
					
					//if the current tag is not a value to the previous tag, set its initial count to 1
					else{
						innerMap.put(current,1.0);
					}
					
					//add the inner Map to the outer transitionProbs map
					transitionProbs.put(previous, innerMap);
				}
				//if the previous tag is not yet in the outer Map, add it to the map
				else{
					Map<String,Double> innerMap=new HashMap<String,Double>();
					innerMap.put(current,1.0);
					transitionProbs.put(previous,innerMap);
				}
				previous=current;
			}
		}
		
		//go back through all of the tags and count up how many times they are used
		for (String key:transitionProbs.keySet()){
			
			//initialize the number of tag occurrences to 0 at the start of a new tag
			double numOccurances=0.0;
			
			//get the map value of the tag key
			Map<String,Double> innerMap=transitionProbs.get(key);
			
			//sum up all of the occurrences of the tag
			for (String innerKey:transitionProbs.get(key).keySet()){
				numOccurances+=transitionProbs.get(key).get(innerKey);
			}
			
			//go back through all of the words and divide by the tag's number of occurrences
			for(String innerKey:transitionProbs.get(key).keySet()){
				innerMap.put(innerKey, Math.log((transitionProbs.get(key).get(innerKey)/numOccurances)));
			}
			//put the new inner map into the emissionsProbs
			transitionProbs.put(key, innerMap);
		}
	}
	
}
