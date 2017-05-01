package shar1370_a05;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Tokenizer takes an equation string as input and tokenizes it, breaking it up
 * into operands, operators, and variables. Each call to <code>next</code>
 * retrieves the next identifiable substring as a <code>Token</code> object,
 * then removes that string from the remaining input string. When all tokens
 * have been removed the string is empty. The initial string is assigned by
 * passing it to the Tokenizer constructor.
 * </p>
 * 
 * <p>
 * This methods of this class follow the approach of similar methods in the
 * <code>Scanner</code> class. Thus <code>next</code> throws the same exceptions
 * as those thrown by <code>Scanner.nextInt</code>.
 * </p>
 * 
 * <p>
 * Note that the tokenizer does not distinguish between a minus sign used as an
 * operator and a minus sign at the beginning of an operand value. Because it
 * tests for operators before operands it treats the minus sign as an operator.
 * </p>
 * 
 * @author David Brown
 * @version 2017-02-13
 */
public class Tokenizer {
    /**
     * <p>
     * A pattern for matching operands using the regular expression
     * <code>"^\\s*-?[0-9]+"</code>. The expression has a number of sections:
     * </p>
     * <ol>
     * <li><code>^</code> : attempts to match the expression against only the
     * beginning of a string.</li>
     * <li><code>\\s*</code> : attempts to match 0 or more space characters
     * (i.e. space, tab, etc.).</li>
     * <li><code>-?</code> : attempts to match a minus sign prefix, if one
     * exists. (Thus if the string begins with a minus sign the pattern is
     * considered to match; if there is no minus sign the pattern is considered
     * to match - so far.)</li>
     * <li><code>[0-9]</code> attempts to match the digits 0 through 9.</li>
     * <li>+ attempts to match the previous set of digits 1 or more times - i.e.
     * there must be at least one digit for it to be seen as a valid operand -
     * in this case an integer.</li>
     * </ol>
     * <p>
     * Thus the pattern can match both positive and negative integers at the
     * beginning of the target string.
     * </p>
     * <p>
     * This pattern could be expanded fairly easily to match float or double
     * numbers as well.
     * </p>
     */
    private final static Pattern operandPattern = Pattern
	    .compile("^\\s*-?[0-9]+");

    /**
     * <p>
     * A pattern for matching operators using the regular expression
     * <code>"^\\s*[+-/*%()]"</code>. The expression has two sections:
     * </p>
     * <ol>
     * <li><code>^</code> : attempts to match the expression against only the
     * beginning of a string.</li>
     * <li><code>\\s*</code> : attempts to match 0 or more space characters
     * (i.e. space, tab, etc.).</li>
     * <li><code>[+-/*%()^]</code> attempts to match any one of the operator
     * symbols.</li>
     * </ol>
     * <p>
     * A few changes in this pattern could allow it to match longer operators as
     * well.
     * </p>
     */
    private final static Pattern operatorPattern = Pattern
	    .compile("^\\s*[+-/*%()^]");

    /**
     * <p>
     * A pattern for matching single-letter variables using the regular
     * expression <code>"^[a-zA-Z]"</code>. The expression has two sections:
     * </p>
     * <ol>
     * <li><code>^</code> : attempts to match the expression against only the
     * beginning of a string.</li>
     * <li><code>\\s*</code> : attempts to match 0 or more space characters
     * (i.e. space, tab, etc.).</li>
     * <li><code>[a-zA-Z]</code> attempts to match the any one of the upper or
     * lower case letters.</li>
     * </ol>
     * <p>
     * A few changes in this pattern could allow it to match longer
     * multi-character variables as well.
     * </p>
     */
    private final static Pattern variablePattern = Pattern
	    .compile("^\\s*[a-zA-Z]");

    /**
     * An array of the patterns to apply against the tokenizer string. It
     * applies the patterns in the order operator, operand, variable.
     */
    private final static Pattern patterns[] = { operatorPattern, operandPattern,
	    variablePattern };

    /**
     * An array of the <code>Token</code> type identifiers in the same order as
     * the patterns are applied. This and the above array work in parallel.
     */
    private final static int patternIds[] = { Token.OPERATOR, Token.OPERAND,
	    Token.VARIABLE };

    /**
     * @param args
     * 
     *            Testing for Tokenizer. Walks through keyboard input until the
     *            input string is exhausted, and returns the appropriate
     *            <code>Token</code> object found with each iteration.
     */
    public static void main(String[] args) {
	Scanner keyboard = new Scanner(System.in);
	System.out.print("String to tokenize: ");
	String string = keyboard.nextLine();
	Tokenizer tokenizer = new Tokenizer(string);

	while (tokenizer.hasNext()) {
	    try {
		System.out.println(tokenizer.next());
	    } catch (Exception e) {
		System.out.println(e.getMessage());
	    }
	}
	keyboard.close();
    }

    /**
     * The string to tokenize.
     */
    private String string = null;

    /**
     * Create a <code>Tokenizer</code> object.
     * 
     * @param string
     *            The string to tokenize.
     */
    public Tokenizer(String string) {
	this.string = string;
    }

    /**
     * Closes the tokenizer. The tokenizer string is set to <code>null</code>.
     * Tokens cannot be extracted from a closed tokenizer and the tokenizer
     * throws an IllegalStateException if any attempt is made to extract a token
     * from it.
     */
    public void close() {
	this.string = null;
    }

    /**
     * Returns true if potentially there are tokens remaining in the tokenizer
     * string, and false otherwise. The tokenizer string may have tokens if the
     * string has a length greater than 0.
     * 
     * @return true if and only if potentially there are tokens remaining in the
     *         tokenizer string
     * @throws IllegalStateException
     *             if the tokenizer is closed (matches <code>Scanner</code>
     *             behaviour.)
     */
    public boolean hasNext() {

	if (this.string == null) {
	    throw new IllegalStateException("Tokenizer is closed.");
	} else {
	    return this.string.length() > 0;
	}
    }

    /**
     * Looks for and generates a <code>Token</code> object from the tokenizer
     * string. Attempts to match operators, operands, and variables in that
     * order.
     * 
     * @return a new Token object
     * @throws IllegalStateException
     *             if the tokenizer is closed (matches <code>Scanner</code>
     *             behaviour.)
     * @throws NoSuchElementException
     *             if the tokenizer string is exhausted (matches
     *             <code>Scanner</code> behaviour.)
     */
    public Token next() {
	Token token = null;

	if (this.string == null) {
	    throw new IllegalStateException("Tokenizer is closed.");
	} else if (this.string.length() == 0) {
	    throw new NoSuchElementException("Tokenizer string is exhausted");
	} else {
	    Matcher matcher = null;
	    int i = 0;

	    // Apply the operator, operand, and variable patterns against the
	    // tokenizer string in turn.
	    while (token == null && i < patterns.length) {
		matcher = patterns[i].matcher(this.string);

		if (matcher.lookingAt()) {
		    // The current pattern has matched.
		    String data = matcher.toMatchResult().group();
		    // Create the new token and remove the matched string.
		    // Remove surrounding space characters.
		    token = new Token(data.trim(), patternIds[i]);
		    this.skip(data);
		} else {
		    i += 1;
		}
	    }
	    if (token == null) {
		// None of the patterns applied matched.
		// Remove surrounding space characters.
		String skipped = this.skip();
		throw new InputMismatchException(
			"No valid token starting with: '" + skipped + "'");
	    }
	}
	return token;
    }

    /**
     * Removes the first character from the tokenizer string only if the
     * character is not part of an operator, operand, or variable. It should not
     * be used to remove an arbitrary character from the beginning of the
     * tokenizer string.
     * 
     * @return the one-character string that could not be interpreted as a
     *         <code>Token</code>.
     * @throws IllegalStateException
     *             if the tokenizer is closed (matches <code>Scanner</code>
     *             behaviour.)
     * @throws NoSuchElementException
     *             if the tokenizer string is exhausted (matches
     *             <code>Scanner</code> behaviour.)
     */
    public String skip() {

	if (this.string == null) {
	    throw new IllegalStateException("Tokenizer is closed.");
	} else if (this.string.length() == 0) {
	    throw new NoSuchElementException("Tokenizer string is exhausted");
	} else {
	    String skipped = null;
	    // Get rid of surrounding space characters.
	    this.string = this.string.trim();

	    if (this.string.length() > 0) {
		// Remove and return the first non-space character from the
		// tokenizer string.
		skipped = this.string.substring(0, 1);
		this.string = this.string.substring(1);
	    } else {
		// Tokenizer string consisted of space characters only.
		skipped = "";
	    }
	    return skipped;
	}
    }

    /**
     * Removes the parameter string from the beginning of the tokenizer, if it
     * exists, throws an exception otherwise.
     * 
     * @param s
     *            the string to be removed from the beginning of the tokenizer
     * @return the string that was to be stripped from the tokenizer string.
     * @throws IllegalStateException
     *             if the tokenizer is closed (matches <code>Scanner</code>
     *             behaviour.)
     * @throws NoSuchElementException
     *             if the tokenizer string is exhausted (matches
     *             <code>Scanner</code> behaviour.)
     */
    public String skip(String s) {

	if (this.string == null) {
	    throw new IllegalStateException("Tokenizer is closed.");
	} else if (this.string.startsWith(s)) {
	    // Remove the substring s from the tokenizer string.
	    this.string = this.string.substring(s.length());
	    return s;
	} else {
	    throw new NoSuchElementException("Error: '" + s
		    + "' cannot be found in '" + this.string + "'");
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return this.string;
    }
}
