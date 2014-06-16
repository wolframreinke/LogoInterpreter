package testing.logo.testing.logo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import logo.Interpreter;
import logo.ParsingException;
import logo.Turtle;
import logo.VariableUndefinedException;
import logo.commands.Command;


public class GeneralParsingTest {

	public static void main( String[] args ) {

		Interpreter interpreter = new Interpreter();
		
		try ( Scanner scanner = new Scanner( new File( "test.logo" ) ) ) {
			
			String sourceCode = "";
			while ( scanner.hasNextLine() )
				sourceCode += scanner.nextLine() + "\n" ;
			
			interpreter.parse( sourceCode );
		}
		catch ( FileNotFoundException e ) {
			
			System.err.println( e.getMessage() );
		}
		catch ( ParsingException e ) {
			
			System.err.println( e.getMessage() );
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
