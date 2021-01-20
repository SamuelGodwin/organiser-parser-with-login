/**
 * 
 */
package model;

/**
 * 'Token' class. Stores Strings along with their type; 'DATE', 'EVENT' etc.
 * Enforces strict typing of strings. Contains additional String
 * representation method 'toString'. This class and its methods are public, so
 * are accessible anywhere.
 *
 * @author Patrick & Samuel
 *
 */
public class Token {

	private String contents;
	private TokenType type;

	/**
	 * Constructor method for Token. Sets values of above fields.
	 * 
	 * @param contents
	 * @param type
	 */
	public Token(String contents, TokenType type) {
		this.contents = contents;
		this.type = type;
	}

	/**
	 * String representation of String field 'contents'.
	 */
	public String toString() {
		return contents;
	}

	/**
	 * Returns object of TokenType 'type'.
	 * 
	 * @return type
	 */
	public TokenType type() {
		return type;
	}

}
