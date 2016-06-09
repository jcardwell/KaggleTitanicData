/*Jack Cardwell
 * April 20, 2015
 * Professor Bailey-Kellog
 * Short Assignment 5
 */
public class StackTest {
	/**
	 * Returns a copy of the stack, leaving the stack in the same state it was.
	 * Thus both stacks will have the same items in the same (original) order.
	 * @throws Exception 
	 */
	public static <T> SimpleStack<T> copy(SimpleStack<T> stack) throws Exception {
		// YOUR CODE HERE
		
		//create two stacks, a temporary and a copy
		SimpleStack<T> temporary=new SLLStack<T>();
		SimpleStack<T> copy=new SLLStack<T>();
		
		//while the stack is not empty continue to pop and push into a temporary stack
		while (stack.isEmpty()==false){
			//pop the old stack into a temporary stack which will be flipped
			temporary.push(stack.pop());
		}
		
		//go through the whole temporary list
		while (temporary.isEmpty()==false){
			//save the element that is popped
			T element=temporary.pop();
			
			//add the element back to the stack, as well as to the copy
			copy.push(element);
			stack.push(element);	
		}
		//return the copy of the original stack
		return copy;
	}
	
	public static void main(String[] args) throws Exception {
		// A simple test
		SimpleStack<String> s1 = new SLLStack<String>();
		s1.push("a");
		s1.push("b");
		s1.push("c");
		s1.push("d");
		s1.push("e");
		SimpleStack<String> s2 = copy(s1);
		System.out.println("comparing stacks:");
		boolean pass = true;
		while (!s1.isEmpty() && !s2.isEmpty()) {
			String e1 = s1.pop(), e2 = s2.pop();
			if (e1.equals(e2)) System.out.println("agreed on "+e1);
			else {
				System.out.println("!!! disgreed on "+e1+" vs. "+e2);
				pass = false;
			}
		}
		if (s1.isEmpty() && s2.isEmpty()) System.out.println("both empty now");
		else {
			pass = false;
			if (s1.isEmpty()) System.out.println("!!! s1 empty but s2 not");
			else System.out.println("!!! s2 empty but s1 not");
		}
		if (pass) System.out.println("test passed");
		else System.out.println("test failed");
	}
}
