import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**Jack Cardwell
 * Professor Bailey-Kellogg
 * Computer Science 10: Problem Set 4
 * May 13, 2015
*/

public class BreadthFirstSearch{
	protected Queue<String> toVisit;		//queue of places that we have yet to visit
	protected ArrayList<String> visited;	//places we have visited and have added into the tree
	protected Graph<String, String> input;	//the initial undirected graph of vertices and their edges
	protected Graph<String,String> result;	//the resulting graph
	
	/**
	 * @author Jack Cardwell
	 * @param an undirected graph entitled input
	 * the constructor for the BreadthFirstSearch class
	 */
	public BreadthFirstSearch(Graph<String,String> input){
		toVisit=new LinkedList<String>();
		result=new AdjacencyMapGraph<String,String>();
		visited=new ArrayList<String>();
		input=new AdjacencyMapGraph<String,String>();
	}
	
	/**@author Jack Cardwell
	 * @param end root, input graph
	 * adds to the tree
	 */
	public void createDirectedGraph (String goalVertex, Graph<String,String> input){
		
		//instantiate the queue, visited list, and the resulting tree and then add the start vertex, which is the goal to the queue and the tree
		toVisit.add(goalVertex);
		result.insertVertex(goalVertex);
		
		while (toVisit.size()>0){
			//pop from the queue
			String currentVertex=toVisit.remove();
			
			//for all neighbors of the current vertex
			for (String vertexNeighbor : input.inNeighbors(currentVertex)) {
			
				if (visited.contains(vertexNeighbor) == false && result.hasVertex(vertexNeighbor)==false){
					//create a directed edge from the vertexNeighbor to currentVertex via its edge
					result.insertVertex(vertexNeighbor);
					result.insertDirected(vertexNeighbor, currentVertex, input.getLabel(currentVertex, vertexNeighbor));
					
					//add the neighbor to the queue
					toVisit.add(vertexNeighbor);
					
				}
			}
			//add the current vertex to the visited list, after all of the neighbors have been added to the queue
			visited.add(currentVertex);
		}
	}
	
	/**
	 * @author: Jack Cardwell
	 * method that will find and print the shortest path from a vertex to the root of the directed graph
	 * @param vertex
	 */
	public int findShortestPath(String vertex){
		int n=0;
		//if the graph doesn't contain the vertex, print that the vertex is invalid
		if (result.hasVertex(vertex)==false){
			System.out.println("No connection found");
			return -1;
		}
		//otherwise there is a connection between the root of the graph and the vertex in the query
		else{
			for (String nextStep:result.outNeighbors(vertex)){
				if (nextStep.equals(null)){
					return 0;
				}
				System.out.println(vertex.toString() + " appeared in " + result.getLabel(vertex, nextStep) + " with " +nextStep.toString()+".");
				//recurse on the node that is one level higher than the input vertex
				n= 1+findShortestPath(nextStep);
				
			}
		}
		return n;
	}
	/**
	 * runs and prints the relationship and the Bacon number
	 * @autor Jack Cardwell
	 * @param goalActor, always Kevin Bacon
	 * @param connectingActor, the actor whose relationship with Kevin Bacon we want to find 
	 * @param input...the undireted graph
	 */
	public static void findConnections(String goalActor, String connectingActor,Graph<String,String> input){
		//create a new BreadthFirstSearch object
		BreadthFirstSearch newRun=new BreadthFirstSearch(input);
		
		//turn the undirected graph into the directed graph and store it as newRun.result
		newRun.createDirectedGraph(goalActor,input);
		
		//store the baconNumber to a variable, calling the function prints the relationship tree
		int baconNumber=newRun.findShortestPath(connectingActor);
		
		//if actor has a relationship the bacon number won't be -1, so print the baconNumber
		if (baconNumber!=-1){
			System.out.print(connectingActor+"'s Bacon Number is: "+baconNumber+".");
		}
		
		//otherwise print that the bacon number is infinity
		else{
			System.out.println(connectingActor +"'s Bacon Number is: infinity.");
		}
	}
}




