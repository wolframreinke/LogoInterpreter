package logo.parsing;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * <p>A <code>TokenStream</code> is a stream of {@link Token tokens}, which can be
 * accessed one after another using {@link #getNext()} and 
 * {@link #getNextCarefully()}.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.0
 * 
 * @see {@link Token TokenStream.Token}
 *
 */
public class TokenStream {

	/**
	 * <p>A <code>Token</code> is a piece of textual user input associated with a 
	 * line number.</p>
	 * 
	 * @author Wolfram Reinke
	 * @version 1.0
	 *
	 */
	protected static class Token {
		
		/**
		 * The text, this <code>Token</code> was created from.
		 */
		private String lexeme;
		
		/**
		 * The line number, where the text, this <code>Token</code> was created
		 * from, was found.
		 */
		private int lineNumber;
		
		/**
		 * Creates a new <code>Token</code> by specifying a lexeme and the line
		 * number, where the lexeme was found in the user input.
		 *
		 * @param lexeme
		 * 		The string, this <code>Token</code> shall be created with.
		 * 
		 * @param lineNumber
		 * 		The line number, where the lexeme was found in the user input.
		 * 
		 */
		public Token( String lexeme, int lineNumber ) {

			super();
			this.lexeme = lexeme;
			this.lineNumber = lineNumber;
		}

		/**
		 * Returns the string, this <code>Token</code> was created from.
		 *
		 * @return
		 * 		the lexeme of this token.
		 */
		public String getLexeme() {
		
			return this.lexeme;
		}
		
		/**
		 * Returns the line number, where the lexeme, this <code>Token</code> was
		 * created from, was found.
		 *
		 * @return
		 * 		the line number of this token.
		 */
		public int getLineNumber() {
		
			return this.lineNumber;
		}
		
		@Override
		public String toString() {
		
			return "Token(lexeme: " + this.lexeme + ", line: " + this.lineNumber + ")";
		}
	}
	
	/**
	 * The queue of <code>Token</code>s whose elements are returned in
	 * <code>getNext()</code> and <code>getNextCarefully()</code> seriatim.
	 */
	private final Queue<Token> tokens;
	
	/**
	 * <p>Creates a new <code>TokenStream</code> by tokenizing the specified input
	 * string. This will automaticall strip of (duplicate) whitespaces, as well as
	 * line comments (introduced by a leading '#'). Each resulting 
	 * <code>Token</code> will constist of one word.</p>
	 *
	 * @param input
	 * 		The input string, which shall be tokenized.
	 */
	public TokenStream( String input ) {
	
		this.tokens = new ArrayDeque<Token>();
		String currentLexeme = "";
		
		// determines whether the method is currently in "comment mode"
		boolean isComment = false;
		int lineNumber = 1;
		
		for ( int i = 0; i < input.length(); i++ ) {
			
			char current = input.charAt( i );
			
			// comments start with a leading '#'
			if ( current == '#' )
				isComment = true;
			
			// and comments end with a trailing new line
			if ( current == '\n' ) {
				lineNumber++;
				isComment = false;
			}
			
			// if the current character is inside a comment, it is ignored
			if ( !isComment ) {
				
				if ( Character.isWhitespace( current ) ) {
				
					// if the current lexeme is not empty, and the current 
					// character is a whitespace, the current lexeme is added to the
					// result
					if ( !currentLexeme.isEmpty() ) {
						
						this.tokens.add( new Token( currentLexeme, lineNumber ) );
						currentLexeme = "";
					}
				} else currentLexeme += current;
			}
		}
		
		// Add the last lexeme
		if ( !currentLexeme.isEmpty() )
			this.tokens.add( new Token( currentLexeme, lineNumber ) );
	}
	
	/**
	 * Creates a new, empty <code>TokenStream</code>.
	 */
	private TokenStream() {
		
		this.tokens = new ArrayDeque<Token>();
	}
	
	/**
	 * <p>Creates a flat copy of this <code>TokenStream</code>. That is, this stream
	 * shares its <code>Token</code>s with the new stream.</p>
	 *
	 * @return
	 * 		A copy of this <code>TokenStream</code>.
	 */
	public TokenStream copy() {
		
		TokenStream copy = new TokenStream();
		
		// add all of elements to the copy
		for ( Token token : this.tokens ) {
			copy.tokens.add( token );
		}
		
		return copy;
	}
	
	/**
	 * Returns the next <code>Token</code>'s lexeme, if this <code>TokenStream</code>
	 * is not empty. If it is empty, a <code>NoSuchElementException</code> is thrown.
	 *
	 * @return
	 * 		The next <code>Token</code>'s lexeme.
	 * 
	 * @throws NoSuchElementException
	 * 		If this <code>TokenStream</code> is empty.
	 */
	public String getNext() throws NoSuchElementException {
		
		return this.tokens.remove().getLexeme();
	}
	
	/**
	 * Returns the next <code>Token</code>'s lexeme, if this <code>TokenStream</code>
	 * is not empty. Contrary to {@link #getNext()}, no exception is thrown, if this
	 * stream is empty, but <code>null</code> is returned.
	 *
	 * @return
	 * 		The next <code>Token</code>'s lexeme, or <code>null</code>, if this
	 * 		<code>TokenStream</code> is empty.
	 */
	public String getNextCarefully() {
		
		Token token = this.tokens.poll();
		if ( token == null )
			return null;
		else return token.getLexeme();
	}
	
	/**
	 * Returns the next <code>Token</code>'s line number, without removing it
	 * from the stream.
	 *
	 * @return
	 *		The line number of the next <code>Token</code>.
	 * 
	 * @throws NoSuchElementException
	 * 		If this <code>TokenStream</code> is empty.
	 */
	public int getCurrentLineNumber() throws NoSuchElementException {
		
		return this.tokens.element().getLineNumber();
	}
	
	/**
	 * Returns, whether this <code>TokenStream</code> contains at least one
	 * element. If not, this <code>TokenStream</code> is empty.
	 *
	 * @return
	 * 		<code>false</code>, if this stream is empty, <code>true</code>
	 * 		otherwise.
	 */
	public boolean hasNext() {
		
		return this.tokens.size() > 0;
	}
}
