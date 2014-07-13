package logo.parsing;

import java.util.Collection;
import java.util.HashSet;

import logo.commands.Command;

/**
 * Instances of <code>Parser</code> parse groups of tokens to the 
 * corresponding <code>Command</code> instances. One parser may parse
 * multiple groups of tokens, if they  belong to each other 
 * semantically or technically.
 * 
 * @author Wolfram Reinke
 * @version 3.1
 */
public abstract class Parser {

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
	 * 		The keywords of this <code>Parser</code> or <code>null</code>, if
	 * 		this parser does not depend on any keywords.
	 */
	public abstract String[] getKeywords();
	
	/**
	 * <p>Returns an instance of the subclass of <code>Command</code>, that
	 * corresponds to this <code>Parser</code>-subclass. The information,
	 * which is necessary to construct this instance is retrieved from the 
	 * given token stream.</p>
	 * 
	 * <p>If this parser was not able to parse the tokens, <code>null</code>
	 * is returned. Changes to the token stream are not rolled back.</p>
	 * 
	 * @param stream 		
	 * 		The <code>TokenStream</code> which is used to create the
	 * 		<code>Command</code>.
	 * 
	 * @return				
	 * 		An instance of <code>Command</code>, which corresponds
	 * 		to the tokens and this <code>Parser</code>-
	 * 		subclass, or <code>null</code>, if the tokens
	 * 		could not be parsed.
	 */
	public abstract Command parse( TokenStream stream );
	
	/**
	 * <p>Returns the syntax errors which occurred during the parsing procedure,
	 * as well as errors which originate in some kind of unresolved context
	 * (such like a missing closing bracket).</p>
	 *
	 * @return
	 * 		An empty collection.
	 */
	public Collection<SyntaxError> getSyntaxErrors() {
		
		return new HashSet<SyntaxError>( 0 );
	}
}
