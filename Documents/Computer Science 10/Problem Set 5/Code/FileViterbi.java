import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

/**
 * @author Jack Cardwell
 * Professor Bailey-Kellogg
 * Computer Science 10: Problem Set 7
 * May 20,2015
 */
public class FileViterbi{
	
	public static void writeTags(String outputFile, String inputTag, String inputSentence, String testFile) throws IOException{
		//open up the file to which we will write the resulting tags
		FileWriter outputWrite=new FileWriter(outputFile);
		
		//open up the test file that we are reading sentences from
		FileReader testReader=new FileReader(testFile);
		BufferedReader test=new BufferedReader(testReader);
		
		//create a new Viterbi object
		Viterbi fileRun=new Viterbi(inputSentence,inputTag);
		
		//read the first line in the file
		String currentLine=test.readLine();
		
		//continue reading so long as the lines are not null
		while (currentLine!=null){
			
			//split the string of the line into a list so that we can call Viterbi on it
			String[] lineSplit=currentLine.split(" ");
			
			ArrayList<String> tagAnswer=fileRun.ViterbiPath(lineSplit);
			
			//write each string of the arrayList to the output file 
			for (String tag:tagAnswer){
				outputWrite.write(tag +" ");
			}
			//after going through the whole sentence go to a new line
			outputWrite.write("\n");
			currentLine=test.readLine();
		}
		test.close();
		outputWrite.close();
	}
	public static Integer countWords(String testTag) throws IOException{
		//initialize the number of words read to 0
		int numWords=0;
		
		//open up the test file 
		FileReader testOutput=new FileReader(testTag);
		BufferedReader test= new BufferedReader(testOutput);
		
		//read through the lines of the test file and keep track of the length of each line, by adding it to the numWords read
		String testLine=test.readLine();
		while (testLine!=null){
			String[] splitLine=testLine.split(" ");
			numWords+=splitLine.length;
			testLine=test.readLine();
		}
		test.close();
		return numWords;
	}
	
	public static Integer countErrors(String outputFile, String testTag) throws IOException{
		//initialize the count of the errors
		int numErrors=0;
		
		//open up both of the files to that we are comparing
		FileReader viterbiOutput=new FileReader(outputFile);
		FileReader testOutput=new FileReader(testTag);
		BufferedReader resultReader=new BufferedReader(viterbiOutput);
		BufferedReader testFileReader=new BufferedReader(testOutput);
		
		//read the first line of both files
		String viterbiLine=resultReader.readLine().toLowerCase();
		String testLine=testFileReader.readLine().toLowerCase();
		
		//continue comparing the files until the lines are empty
		while (viterbiLine!=null && testLine!=null){
			//split both of the lines of Strings into arrays
			String[] programOutput=viterbiLine.split(" ");
			String[] givenOutput=testLine.split(" ");
			
			for (int n=0; n<programOutput.length;n++){
				//if the two component strings are not equal, increment the counter of errors
				if (programOutput[n].compareTo(givenOutput[n])!=0){
					numErrors++;
				}
			}
			//read the next lines in each file
			viterbiLine=resultReader.readLine();
			testLine=testFileReader.readLine();
		}
		//close both of the fileReaders
		resultReader.close();
		testFileReader.close();
		
		//return the number of errors
		return numErrors;
	}
	
	public static void main(String[]args) throws IOException{
		writeTags("/Users/Jack/Documents/workspace/Problem Set 5/./ViterbiOutput.txt","/Users/Jack/Documents/workspace/Problem Set 5/./brown-train-tags.txt","/Users/Jack/Documents/workspace/Problem Set 5/./brown-train-sentences.txt","/Users/Jack/Documents/workspace/Problem Set 5/./brown-test-sentences.txt");
		int words=countWords("/Users/Jack/Documents/workspace/Problem Set 5/./brown-test-tags.txt");
		int errors=countErrors("/Users/Jack/Documents/workspace/Problem Set 5/./ViterbiOutput.txt","/Users/Jack/Documents/workspace/Problem Set 5/./brown-test-tags.txt");
		System.out.println("There are " + errors+ " words tagged incorrectly and "+ (words-errors)+ " words tagged correctly by the Viterbi algorithim.");
	}
}