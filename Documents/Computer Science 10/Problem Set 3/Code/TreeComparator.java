import java.util.Comparator;

/**@author Jack Cardwell
 * Professor Bailey-Kelogg
 *Computer Science 10
 *April 26, 2015
 */

//create a Tree Comparator class for use in Compressor
public class TreeComparator<E> implements Comparator<BinaryTree<MapTreeRoot>>{
	
	public int compare(BinaryTree<MapTreeRoot> first, BinaryTree<MapTreeRoot> second){
		
		if ((first.getData().getCharacterFrequency())<(second.getData().getCharacterFrequency())){
			return -1;
		}
		else if ((first.getData().getCharacterFrequency())<(second.getData().getCharacterFrequency())){
			return 1;
		}
		else {
			return 0;
		}
	}	 
}
