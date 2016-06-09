import java.sql.Array;
import java.util.ArrayList;
import java.util.Scanner;

/**Jack Cardwell
 * Professor Bailey-Kellogg
 * Computer Science 10
 * May 11, 2015
 */

public class QueenFinder {
	private int numQueens;
	private ArrayList<int[]> solutionList;							//list to hold onto all of the solutions
	private int[] boardPlacement;									 //create an array of the current
	
	//create the constructor for the method
	private QueenFinder(int numQueens){
		this.numQueens=numQueens;				//number of queens to be placed 
		boardPlacement=new int[numQueens];		//bind the array to the object
		solutionList= new ArrayList<int[]>();	//bind the list to the object
	}
	
	//a method that determines whether a placement is legal
	private boolean placementLegal(int[] columnList,int row, int column){
		for (int j=0; j<column; j++){
			
			//this would mean that there are queens in diagonal because the rows and the columns would increase by the same amount...constant slope of board
			//take the absolute value because the "slope=1" could be positive or negative...not a solution
			//second part of the conditional is whether or not the queens are in the same row
			if(Math.abs(row-columnList[j])==Math.abs(column-j) || columnList[j]==row){
				return false;
			}
		}
		//otherwise return true
		return true;
	}
	
	//a method that finds all possible placements of the queens on the board
	private void findSolutions(int column,int[] boardPlacement){
		
		//add the solutions to the list of solutions if there are enough queens legally placed...current column is past the size of the array
		if (column==numQueens && solutionList.contains(boardPlacement)==false){
			int[] boardPlacementCopy=boardPlacement.clone();
			solutionList.add(boardPlacementCopy);
		}
		
		//otherwise we must continue our recursive search
		else {
			for (int i=0; i<numQueens; i++){
				
				//if a queen can be placed safely, continue
				if (placementLegal(boardPlacement,i,column)){
					
					//place solution into the array and then recurse on the next column
					boardPlacement[column]=i;
					findSolutions(column+1,boardPlacement);
				}
			}
		}
	}
	//method to print all of the solutions
	private void printSolutions(){
		if (solutionList.size()>0){
			System.out.print("The solutions are [");
			
			//cycle through all arrays in the solutionList except the last one
			for (int n=0; n<solutionList.size()-1;n++){
				System.out.print("{");
				
				//cycle through all integers in the array that is in the solutionList
				for (int i=0;i<solutionList.get(n).length-1;i++){
					System.out.print(solutionList.get(n)[i] +",");
				}
				System.out.print(solutionList.get(n)[solutionList.get(n).length-1]+ "},");
			}
			
			//print the last array in SolutionList without a comma after }
			System.out.print("{");
			for (int i=0; i<solutionList.get(solutionList.size()-1).length-1;i++){
				System.out.print(solutionList.get(solutionList.size()-1)[i]+ ",");
			}
			System.out.print(solutionList.get(solutionList.size()-1)[solutionList.get(solutionList.size()-1).length-1]+ "}");
			
			System.out.print("]");
		}
		else{
			System.out.print("There are no possible solutions for this number of queens");
		}
	}
	
	
	public static void main(String[]args){
		boolean toRun=true;
		//prompt the user with how many queens they would like to place on the board using a new Scanner
		Scanner newInputScanner=new Scanner(System.in);
		
		while (toRun){
			System.out.println("How many queens would you like to safely place?");
			int input=newInputScanner.nextInt();
					
			//terminate the program if the user types in 0 or a negative number
			if(input<=0){
				System.out.println("Program terminated");
				toRun=false;
			}
					
			//if the user asked for a solution between 1 and 9, print the possible solutions
			if (input<9 && input>0){
				//initialize a QueenFinder object and call findSolutions on this object
				QueenFinder newRun= new QueenFinder(input);
				newRun.findSolutions(0,newRun.boardPlacement);
					
				//print the solutions
				newRun.printSolutions();
				System.out.println("");
			}
					
			//if the user asked for a number higher than 9...just return the length of the list
			else if(input>=9){
				//initialize a QueenFinder object and call findSolutions on this object
				QueenFinder newRun= new QueenFinder(input);
				newRun.findSolutions(0,newRun.boardPlacement);
						
				//print the number of possible solutions
				System.out.println("There are " + newRun.solutionList.size() + " solutions.");
			}
		}	
		newInputScanner.close();
	}
}
