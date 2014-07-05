package logo.parsers;

import logo.commands.Command;

/**
 * Instances of <code>Parser</code> parse Logo statements to the 
 * corresponding <code>Command</code> instances. One parser may parse
 * multiple Logo statements, if they  belong to each other 
 * semantically or technically.
 * 
 * @author Wolfram Reinke
 * @version 2.3
 */
public interface Parser {

	// TODO Create Javadoc of this method for implementing classes
	/**
	 * <p>Returns the keywords of this <code>Parser</code>, that is, the
	 * strings that introduce a Logo statement, which can be parsed using
	 * this parser's <code>parse</code> method.</p>
	 * 
	 * <p>If no keywords are available, or this implementation of 
	 * <code>Parser</code> depends not on specific keywords, <code>null</code>
	 * is returned.</p>
	 * 
	 * @return	
	 * 		The keywords of this <code>Parser</code>.
	 */
	public abstract String[] getKeywords();
	
	/**
	 * <p>Returns an instance of the implementation of <code>Command</code>, that
	 * corresponds to this <code>Parser</code>-implementation. The information,
	 * which is necessary to construct this instance is retrieved from the 
	 * given Logo statement.</p>
	 * 
	 * <p>If this parser was not able to parse the statement, <code>null</code>
	 * is returned.</p>
	 * 
	 * @param words 		
	 * 		The Logo statement that is used to create the instance
	 * 		of <code>Command</code> splitted into words.
	 * 
	 * @param lineNumber	
	 * 		The line number, in which the Logo statement was found.
	 * 		This value is used to create the corresponding command
	 * 		object.
	 * 
	 * @return				
	 * 		An instance of <code>Command</code>, which corresponds
	 * 		to the input string and this <code>Parser</code>-
	 * 		implementation, or <code>null</code>, if the Logo statement
	 * 		could not be parsed.
	 */
	public abstract Command parse( String[] words, int lineNumber );
}
