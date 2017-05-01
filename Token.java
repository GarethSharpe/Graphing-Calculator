package shar1370_a05;

/**
 * Represents any one of three types of tokens: operator, operand, or variable.
 * <p>
 * Consists of two parts:
 * </p>
 * <ul>
 * <li>a string representation of the token</li>
 * <li>an integer value identifying the type of token</li>
 * </ul>
 * 
 * @author Eugene Zima, David Brown
 * @version 2017-02-13
 *
 */
public final class Token {
    // Constant type identifiers.
    public static final int OPERAND = 0;
    public static final int VARIABLE = 1;
    public static final int OPERATOR = 2;
    // Array of operator type names - for testing. Order matches ordinal values
    // of constant type identifiers.
    public static final String[] typeNames = { "Operand", "Variable",
	    "Operator" };
    // Token data.
    private String tokenString = null;
    private int type = -1;

    /**
     * Creates a <code>Token</code> object based upon string and type
     * parameters.
     * 
     * @param tokenString
     *            the String representation of the token.
     * @param type
     *            the type of the token. Invalid values are accepted, but can
     *            never be identified as a valid token type thereafter.
     */
    public Token(String tokenString, int type) {
	this.tokenString = tokenString;
	this.type = type;
    }

    /**
     * Determines if the token is an operand or not.
     * 
     * @return true if the token is an operand, false otherwise.
     */
    public boolean isOperand() {
	return this.type == OPERAND;
    }

    /**
     * Determines if the token is an operator or not.
     * 
     * @return true if the token is an operator, false otherwise.
     */
    public boolean isOperator() {
	return this.type == OPERATOR;
    }

    /**
     * Determines if the token is an variable or not.
     * 
     * @return true if the token is an variable, false otherwise.
     */
    public boolean isVariable() {
	return this.type == VARIABLE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return this.tokenString;
    }

}
