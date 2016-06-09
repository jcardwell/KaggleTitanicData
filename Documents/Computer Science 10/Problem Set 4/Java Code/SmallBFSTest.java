import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
/**
 * @author Jack Cardwell
 * Professor Bailey-Kellogg
 * May 11, 2015
 * Computer Science 10: Problem Set 4
 */
public class SmallBFSTest{
	
	//construct a trial graph
	public static void createTrialGraph(BreadthFirstSearch trialrun){
		trialrun.input.insertVertex( "Kevin Bacon");
		trialrun.input.insertVertex("actor1");
		trialrun.input.insertVertex("actor2");
		trialrun.input.insertVertex("actor3");
		trialrun.input.insertVertex("actor4");
		trialrun.input.insertVertex("actor5");
		trialrun.input.insertVertex("actor6");
		trialrun.input.insertUndirected("Kevin Bacon", "actor1", "movie1");
		trialrun.input.insertUndirected("Kevin Bacon", "actor2", "movie1");
		trialrun.input.insertUndirected("actor1", "actor2", "movie1");
		trialrun.input.insertUndirected("actor1", "actor3", "movie2");
		trialrun.input.insertUndirected("actor3", "actor2", "movie3");
		trialrun.input.insertUndirected("actor3","actor4","movie4");
		trialrun.input.insertUndirected("actor5","actor6","movie5");	
	}
	public static void main(String args[]){
		BreadthFirstSearch trialrun=new BreadthFirstSearch();
		createTrialGraph(trialrun);
		trialrun.createDirectedGraph("Kevin Bacon", trialrun.input);
		System.out.println(trialrun.result);
		System.out.println(" ");
		System.out.println("Actor1's Bacon Number Is: "+trialrun.findShortestPath("actor1"));
		System.out.println(" ");
		System.out.println("Actor2's Bacon Number Is: "+trialrun.findShortestPath("actor2"));
		System.out.println(" ");
		System.out.println("Actor3's Bacon Number Is: "+trialrun.findShortestPath("actor3"));
		System.out.println(" ");
		System.out.println("Actor4's Bacon Number Is: "+trialrun.findShortestPath("actor4"));
		System.out.println(" ");
		System.out.println("Actor5's Bacon Number Is: "+trialrun.findShortestPath("actor5"));
		System.out.println(" ");
		System.out.println("Actor6's Bacon Number Is: "+trialrun.findShortestPath("actor6"));
		
		
	}
}
