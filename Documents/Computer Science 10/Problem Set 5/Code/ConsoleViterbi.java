import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
/**
 * @author Jack Cardwell
 * Professor Bailey-Kellogg
 * Computer Science 10: Problem Set 7
 * May 20, 2015
 */

public class ConsoleViterbi {
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
		else{
			return "";
		}
	}
	public static void main(String[]args) throws IOException{
	
		//create a new Viterbi run object
		Viterbi consoleRun=new Viterbi("/Users/Jack/Documents/workspace/Problem Set 5/./brown-train-sentences.txt", "/Users/Jack/Documents/workspace/Problem Set 5/./brown-train-tags.txt");
		
		boolean toRun=true;
		
		//create a new console scanner
		Scanner newInputStream=new Scanner(System.in);
		
		while (toRun){
			System.out.println("Please enter the sentence you would like to tag.");
			String input=newInputStream.nextLine();
			
			//terminate the program if the input is quit
			if (input.equals("quit") || input.equals("Quit")){
				System.out.println("Program terminated!");
				toRun=false;
			}
			else{
				//split the user input
				String[] sentenceList=input.split(" ");
				
				//get the result of the tags
				ArrayList<String> result=consoleRun.ViterbiPath(sentenceList);
				
				for (String answer: result){
					System.out.print(answer + " ");
				}
				System.out.println("");
			}
		}
		//close the scanner
		newInputStream.close();
	}
}
