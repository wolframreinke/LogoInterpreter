package testing.logo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Scanner;

import logo.LogoInterpreter;
import logo.commands.Command;
import logo.commands.Turtle;
import logo.commands.VariableUndefinedException;
import logo.parsing.SyntaxError;


public class GeneralParsingTest {

	public static void main( String[] args ) {
	
		LogoInterpreter interpreter = new LogoInterpreter();
		Collection<SyntaxError> syntaxErrors = null;

		try ( Scanner scanner = new Scanner( new File( "test.logo" ) ) ) {

			String sourceCode = "";
			while ( scanner.hasNextLine() )
				sourceCode += scanner.nextLine() + "\n";

			syntaxErrors = interpreter.parse( sourceCode );
		}
		catch ( FileNotFoundException e ) {

			System.err.println( e.getMessage() );
		}

		if ( !syntaxErrors.isEmpty() ) {

			for ( SyntaxError error : syntaxErrors ) {
				System.out.println( error );
			}

			return;
		}

		Command command;
		Turtle turtle = new Turtle() {

			@Override
			public void reset() {}

			@Override
			public void move( int distance ) {}

			@Override
			public void turn( int alpha ) {}

			@Override
			public void setPainting( boolean painting ) {}

			@Override
			public void clear() {}

			@Override
			public void setColor( int ColorID ) {}
			
		};

		try {
			while ( (command = interpreter.getNextCommand()) != null ) {

				command.execute( turtle );

				// Comment out what you dont wanna see
				System.out.println( command );
				// System.out.println( turtle );
			}
		}
		catch ( IllegalStateException e ) {

			System.err.println( e.getMessage() );
		}
		catch ( VariableUndefinedException e ) {

			System.err.println( e.getMessage() );
		}
	
	}
}
