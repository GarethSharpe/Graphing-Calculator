package shar1370_a05;

/**
 * The individual node of a linked structure that stores <code>Poly</code>
 * polynommial objects. This is a single forward linked structure.
 *
 * @author Dr. Eugene Zima
 * @author David Brown
 * @version 2017-02-09
 */
public class PNode {

    // The Poly data.
    private final Poly element;
    // Link to the next PolyNode.
    private final PNode next;

    /**
     * Creates a new <code>PNode</code> object with a <code>Poly</code> data
     * element and a link to the next node in the linked structure.
     *
     * @param element
     *            The polynomial data to store in the node.
     * @param next
     *            The next node in the linked structure. A value of
     *            <code>null</code> marks the end of the linked structure.
     */
    public PNode(final Poly element, final PNode next) {
	this.element = element;
	this.next = next;
    }

    /**
     * Returns the node data.
     *
     * @return The <code>Poly</code> object that is the data portion of the
     *         node.
     */
    public Poly getElement() {
	return this.element;
    }

    /**
     * Returns the next node in the linked structure.
     *
     * @return The <code>PNode</code> that follows this node.
     */
    public PNode getNext() {
	return this.next;
    }

}