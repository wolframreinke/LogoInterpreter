package logo.parsing;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;


public class TokenStream {

	private static class Token {
		
		private String text;
		private int lineNumber;
		
		public Token( String text, int lineNumber ) {

			super();
			this.text = text;
			this.lineNumber = lineNumber;
		}

		public String getText() {
		
			return this.text;
		}
		
		public int getLineNumber() {
		
			return this.lineNumber;
		}
	}
	
	public static final TokenStream tokenize( String input ) {
		
		return new TokenStream( input );
	}
	
	private final Queue<Token> tokens;
	
	private TokenStream( String input ) {
	
		this.tokens = new ArrayDeque<Token>();
		String currentLexeme = "";
		
		boolean isComment = false;
		int lineNumber = 1;
		
		for ( int i = 0; i < input.length(); i++ ) {
			
			char current = input.charAt( i );
			
			if ( current == '#' )
				isComment = true;
			
			if ( current == '\n' ) {
				lineNumber++;
				isComment = false;
			}
			
			if ( !isComment ) {
				
				if ( Character.isWhitespace( current )
						&& !currentLexeme.isEmpty() ) {
					
					this.tokens.add( new Token( currentLexeme.trim(), lineNumber ) );
					currentLexeme = "";
				}
				else {
					
					currentLexeme += current;
				}
			}
		}
		
		if ( !currentLexeme.isEmpty() )
			this.tokens.add( new Token( currentLexeme.trim(), lineNumber ) );
	}
	
	private TokenStream() {
		
		this.tokens = new ArrayDeque<Token>();
	}
	
	public TokenStream copy() {
		
		TokenStream copy = new TokenStream();
		
		for ( Token token : this.tokens ) {
			copy.tokens.add( token );
		}
		
		return copy;
	}
	
	public String getNext() throws NoSuchElementException {
		
		return this.tokens.remove().getText();
	}
	
	public String getNextCarefully() {
		
		Token token = this.tokens.poll();
		if ( token == null )
			return null;
		else return token.getText();
	}
	
	public int getCurrentLineNumber() throws NoSuchElementException {
		
		return this.tokens.element().getLineNumber();
	}
	
	public boolean hasNext() {
		
		return this.tokens.size() > 0;
	}
}
