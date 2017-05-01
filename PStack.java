package shar1370_a05;

/**
 * The Stack class represents a last-in-first-out (LIFO) 
 * stack of objects. The usual push and pop operations 
 * are provided, as well as a method to peek at the top 
 * item on the stack, a method to test for whether the 
 * stack is empty, and a method to identify the number
 * of items in the stack.
 * 
 * When a stack is first created, it contains no items.
 * 
 * @author Gareth Sharpe
 * @since 01/13/2016
 */
/**
 * A simple linked stack structure of <code>PolyNode</code> objects. Only the
 * <code>Poly</code> data contained in the stack is visible through the standard
 * stack methods.
 *
 * @author Dr. Eugene Zima
 * @author David Brown
 * @version 2017-02-09
 */

public class PStack {
    // The top node of the stack.
    private PNode top;

    public PStack() {
	this.top = null;
    }

    /**
     * Returns whether the stack is empty or not.
     *
     * @return <code>true</code> if the stack is empty, <code>false</code>
     *         otherwise.
     */
    public boolean isEmpty() {
	return this.top == null;
    }

    /**
     * Returns a reference to the top data element of the stack without removing
     * that data from the stack.
     *
     * @return The <code>Poly</code> object at the top of the stack.
     */
    public Poly peek() {
	return this.top.getElement();
    }

    /**
     * Returns the top data element of the stack and removes that element from
     * the stack. The next node in the stack becomes the new top node.
     *
     * @return The <code>Poly</code> object at the top of the stack.
     */
    public Poly pop() {
	final PNode node = this.top;
	this.top = this.top.getNext();
	return node.getElement();
    }

    /**
     * Adds a <code>Poly</code> object to the top of the stack.
     *
     * @param element
     *            The <code>Poly</code> object to add to the top of the stack.
     */
    public void push(final Poly element) {
	final PNode node = new PNode(element, this.top);
	this.top = node;
    }

}