package core;

import java.util.Stack;

/**
 * Reversiable stack in constant time. 
 * @author Mohsin Hijazee
 * @param <E> Objects to be saved.
 */
public class ReversibleStack<E> extends Stack<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean reversed = false;
	
	/**
	 * Pop as usual but if reversed, get and remove first item.
	 */
	@Override
	public E pop() {
		
		if(!reversed)
			return super.pop();
		
		
		return super.remove(0);
		
	}
	
	/**
	 * Push as usual but if reversed, insert at the start.
	 */
	@Override
	public E push(E item) {
		
		if(!reversed)
			return super.push(item);
		
		super.insertElementAt(item, 0);
		return item;
	}
	
	/**
	 * Work as normal but if reversed, return the first item.
	 */
	@Override
	public E peek() {
		
		if(!reversed)
			return super.peek();
		
		return super.get(0);
	}
	
	/**
	 * Reverses the direction of the stack. 
	 * @return boolean. Old direction
	 */
	public boolean reverse() {
		boolean oldStatus = reversed;
		
		reversed = !reversed;
		
		return oldStatus;
	}
	
	/**
	 * Returns true if the stack is reversed from when it was created. Or false indicating it is functioning as a normal stack (FILO)
	 * @author sean.ford
	 * @return boolean.
	 */
	public boolean isReversed() {
		return reversed;
	}
}