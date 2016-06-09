import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * @author Jack Cardwell
 * Professor Bailey-Kellogg
 * Computer Science 10: Problem Set 5
 * May 20,2015
 */

public class Viterbi {
	protected Map<String,Map<String,Double>> emissionProbs;		//probabilities of emissions based on trained files
	protected Map<String,Map<String,Double>> transitionProbs;	//probabilities of transitions based on trained files

	/**
	 * @author Jack Cardwell
	 * @param tag
	 * @param wordTrainer
	 * @param test
	 * @throws IOException
	 * the constructor for the Viterbi class
	 */
	public Viterbi(String wordTrainer, String tag) throws IOException{
		HMMTrainer trainer=new HMMTrainer();		//initialize a new HMMTrainer object in order to get the probability maps
		trainer.getLists(wordTrainer, tag);
		trainer.getEmissionsProbability();
		trainer.getTransitionsProbabilities();
		emissionProbs=trainer.emissionProbs;		//save the map of emission probabilities
		transitionProbs=trainer.transitionProbs;	//save the map of transition probabilities
	}
	
	/**
	 * @author Jack Cardwell
	 * method to find the most probable path that implements the Viterbi algorithm
	 * @throws IOException 
	 * @return the most accurate path of a sentence
	 */
	public ArrayList<String> ViterbiPath(String[] observations) {
		//an array list to be returned later of the most correct path
		ArrayList<String> correctPath=new ArrayList<String>();
		ArrayList<Map<String,String>> backtrace=new ArrayList<Map <String,String>>();
		
		
		//initialize the queue of previous states with the start
		Set<String> prevStates=new HashSet<String>();			
		prevStates.add("*");
		
		//initialize the map of scores with start and 0 as the score
		Map<String,Double> prevScores=new HashMap<String,Double>();
		prevScores.put("*", 0.0);
			
		//go through all of the positions in the observations line
		for (String currentWord:observations){
			Map<String,String> pathMap=new HashMap<String,String>();
			//an array list of where to go for the next tags, a map of the next scores to be added to the array list of maps 
			Map<String,Double> nextScores=new HashMap<String,Double>();
			
			//go through all of the possible transitions from the previous state from the trained tag list
			for (String state: prevScores.keySet()){
				
				//make sure that the tag(state) is a valid state
				if (transitionProbs.containsKey(state)){
					
					//go through all of the possible transitions from the state
					for(String transition:transitionProbs.get(state).keySet()){
						
						Double nextScore = null;
						
						if(emissionProbs.get(transition).containsKey(currentWord.toLowerCase())){	
							//if the word is in the map, add its log probability
							nextScore=prevScores.get(state)+transitionProbs.get(state).get(transition)+emissionProbs.get(transition).get(currentWord.toLowerCase());
						}
						
						else {
							//if the word isn't in the emission map, add -100 as it's log probability
							nextScore=prevScores.get(state)+transitionProbs.get(state).get(transition)-100;
						}
						
						//check to see if we already have the path in our map of next states or if it is worth adding...has a higher score, which means it is more valid
						if (nextScores.containsKey(transition)==false || nextScore>nextScores.get(transition)){
							//then put the next state and score into the map nextScores
							nextScores.put(transition, nextScore);
							pathMap.put(transition,state);
						}	
					}
				}
			}
			//increment the loop, by updating the previous states and scores...add to the path score ArrayList this map of scores for this part of the sentence
			prevScores=nextScores;
			backtrace.add(pathMap);
		}
		//get the highest score from the scores of the last part of the sentence
		double currentScore=-10000000000000.0;
		String correctTag=null;
		
		for (String key: prevScores.keySet()){
			
			if (prevScores.get(key)>currentScore && prevScores.get(key)!=0.0){
				currentScore=prevScores.get(key);
				correctTag=key;
			}
		}
		
		correctPath.add(correctTag);
		int listPosition=backtrace.size()-1;
		
		//go through the backtrace map following back from the most likely score
		while(backtrace.get(listPosition).get(correctTag)!="*"){
			correctTag=backtrace.get(listPosition).get(correctTag);
			correctPath.add(correctTag);
			listPosition--;
		}
		Collections.reverse(correctPath);
		return correctPath;
	}
	public static void main(String[]args) throws IOException{
		Viterbi test=new Viterbi("/Users/Jack/Documents/workspace/Problem Set 5/./simple-train-sentences.txt", "/Users/Jack/Documents/workspace/Problem Set 5/./simple-train-tags.txt");
		String sentence= "This is";
		String[] input=sentence.split(" ");
		test.ViterbiPath(input);
	}
}
