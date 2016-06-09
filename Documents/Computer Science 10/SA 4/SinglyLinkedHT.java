/**
 * @author Jack Cardwell
 * Professor Bailey-Kellogg
 * April 10, 2015
 * CS 10 Short Assignment 4
 */

/**
 * A singly-linked list implementation of the SimpleList interface
 * Now with tail pointer (once you finish the assignment)
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012, 
 * based on a more feature-ful version by Tom Cormen and Scot Drysdale
 */
public class SinglyLinkedHT<T> implements SimpleList<T> {
	private Element<T> head;	// front of the linked list
	private Element<T> tail;	// end
	private int size;					// # elements in the list

	/**
	 * The linked elements in the list: each has a piece of data and a next pointer
	 */
	private class Element<T> {
		private T data;
		private Element<T> next;
		

		public Element(T data, Element<T> next) {
			this.data = data;
			this.next = next;
			
			
		}
	}

	public SinglyLinkedHT() {
		head = null;
		size = 0;
		tail = null;
	}

	public int size() {
		return size;
	}
	
	/**function added by Jack Cardwell on April 13, 2015 to find the tail of a list
	 * 
	 * @return tail
	 * @throws Exception
	 */
	private Element<T>getTail(){
		//start a loop at the head of the list until there is no next
		Element<T> e = head;
		Element <T> currentElement=head;
		
		if (size()>0){
			//it will exit once currentElement.next is undefined so that the one it stops on is the tail
			while (currentElement.next!=null){
				currentElement=currentElement.next;
			}
			//set the tail equal to the currentElement
			tail=currentElement;
		}
		else if (size==0){
			tail=null;
		}
		//return the tail
		return tail;
	}
	
	/**
	 * Helper function, advancing to the nth Element in the list and returning it
	 * (exception if not that many elements)
	 */
	private Element<T> advance(int n) throws Exception {
		Element<T> e = head;
		while (n > 0) {
			// Just follow the next pointers
			e = e.next;
			if (e == null) throw new Exception("invalid index");
			n--;
		}
		return e;
	}

	public void add(int idx, T item) throws Exception {        
		if (idx == 0) {
			// Insert at head
			head = new Element<T>(item, head);
		}
		else {
			// It's the next thing after element # idx-1
			Element<T> e = advance(idx-1);
			// Splice it in
			e.next = new Element<T>(item, e.next);
		}
		size++;
		
		//change the tail using the getTail function from above
		tail=getTail();
	}

	public void remove(int idx) throws Exception {
		if (idx == 0) {
			// Just pop off the head
			if (head == null) throw new Exception("invalid index");
			head = head.next;
		}
		else {
			// It's the next thing after element # idx-1
			Element<T> e = advance(idx-1);
			if (e.next == null) throw new Exception("invalid index");
			// Splice it out
			e.next = e.next.next;	
		}
		size--;
		
		//get the tail using the getTail function from above
		tail=getTail();
	}

	public T get(int idx) throws Exception {
		Element<T> e = advance(idx);
		return e.data;
	}

	public void set(int idx, T item) throws Exception {
		Element<T> e = advance(idx);
		e.data = item;
	}
	
	/**
	 * Appends other to the end of this in constant time, by manipulating head/tail pointers
	 */
	public void append(SinglyLinkedHT<T> other) {
		//only do the rest if the list contains elements
		if (size>0 && other.size>0){
			
			//get the tail from the first list by using the getTail() function
			tail=getTail();
			
			//get the head of the next list 
			Element<T>firstOfNew=other.head;
			
			//set the tail's next pointer to the head of the second list
			tail.next=firstOfNew;
			
			//increment the size of the list 
			size=size+other.size;
			
		}
		
		else if(size==0){
			//make the head of the second list the head of the first list
			head=other.head;
			
			//increment the size of the list
			size=other.size;
		}
		
		//update the tail
		tail=getTail();
	}
	
	public String toString() {
		String result = "";
		for (Element<T> x = head; x != null; x = x.next) 
			result += x.data + "->"; 
		result += "[/]";

		return result;
	}

	public static void main(String[] args) throws Exception {
		SinglyLinkedHT<String> list1 = new SinglyLinkedHT<String>();
		SinglyLinkedHT<String> list2 = new SinglyLinkedHT<String>();
		
		System.out.println("empty + empty");
		list1.append(list2);
		System.out.println(list1);
		
		list2.add(0, "a");
		System.out.println("empty + nonempty");
		list1.append(list2);
		System.out.println(list1);
		
		list1.add(1, "b");
		list1.add(2, "c");
		SinglyLinkedHT<String> list3 = new SinglyLinkedHT<String>();
		System.out.println("nonempty + empty");
		list1.append(list3);
		System.out.println(list1);
		
		SinglyLinkedHT<String> list4 = new SinglyLinkedHT<String>();
		list4.add(0, "z");
		list4.add(0, "y");
		list4.add(0, "x");		
		System.out.println("nonempty + nonempty");
		list1.append(list4);
		System.out.println(list1);
		
		list1.remove(5);
		list1.remove(4);
		SinglyLinkedHT<String> list5 = new SinglyLinkedHT<String>();
		list5.add(0, "z");
		list5.add(0, "y");
		System.out.println("removed nonempty + nonempty");
		list1.append(list5);
		System.out.println(list1);
	}
}