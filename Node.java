package shar1370_a05;

/**
 * for Stack class implementation
 * 
 */
public class Node {
	private String el;
	private Node next;

	/**
	 * creates a new node, sets eq as its element
	 * 
	 * @param eq
	 *            String - new element
	 * @param node
	 *            pointer to next node
	 */
	public Node(String eq, Node node) {
		el = eq;
		next = node;
	}

	/**
	 * accessor method to get value in node
	 * 
	 * @return element object of type String
	 */
	public String getEl() {
		return el;
	}

	/**
	 * accessor method to get pointer to the next node
	 * 
	 * @return next object of type Node
	 */
	public Node getNext() {
		return next;
	}

	/**
	 * prints values contained in node
	 */
	public String toString() {
		return "Node contains " + el;
	}

}