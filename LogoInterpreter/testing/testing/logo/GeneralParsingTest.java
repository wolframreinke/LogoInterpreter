package testing.logo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Scanner;

import logo.Interpreter;
import logo.SyntaxError;
import logo.Turtle;
import logo.VariableUndefinedException;
import logo.commands.Command;

public class GeneralParsingTest {

	public static void main( String[] args ) {

		Interpreter interpreter = new Interpreter();
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

		if ( syntaxErrors != null ) {

			for ( SyntaxError error : syntaxErrors ) {
				System.out.println( error );
			}

			return;
		}

		Command command;
		Turtle turtle = new Turtle();

		try {
			while ( (command = interpreter.getNextCommand()) != null ) {

				command.execute( turtle );

				// Comment out what you dont wanna see
				// System.out.println( command );
				System.out.println( turtle );
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
