package testing.logo;

import logo.parsing.TokenStream;


public class TokenizerTest {

	public static void main( String[] args ) {


		TokenStream stream = TokenStream.tokenize( "forward 12\n backward 14 right 90\nforward 20#meters\nbackward 10" );
		
		while ( stream.hasNext() )
			System.out.println( stream.getCurrentLineNumber() + ":\t" + stream.getNext() );
	}

}
