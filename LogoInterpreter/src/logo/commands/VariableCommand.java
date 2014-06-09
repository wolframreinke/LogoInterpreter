package logo.commands;

import logo.Command;
import logo.Interpreter;
import logo.Turtle;
import logo.VariableUndefinedException;

public class VariableCommand extends Command {

	public static enum Type {
		LET,
		INCREMENT,
		DECREMENT;
	}
	
	private Type type;
	private String var1, var2;
	
	public VariableCommand( String var, int value, Type type ) {
		String var2 = Interpreter.createHelpVariable();
		Interpreter.setVariableValue( var2, value );
		this.var1 = var;
		this.var2 = var2;
		this.type = type;
	}
	
	public VariableCommand( String var1, String var2, Type type ) {
		this.var1 = var1;
		this.var2 = var2;
		this.type = type;
	}
	
	@Override
	public void execute( Turtle turtle ) throws VariableUndefinedException {

		int value = Interpreter.getVariableValue( var2 );
		int value2;
		
		switch ( type ) {

		case LET:
			Interpreter.setVariableValue( var1, value );
			break;
			
		case INCREMENT:
			value2 = Interpreter.getVariableValue( var1 );
			Interpreter.setVariableValue( var1, value2 + value );
			break;
			
		case DECREMENT:
			value2 = Interpreter.getVariableValue( var1 );
			Interpreter.setVariableValue( var1, value2 - value );
			break;

		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "(var1: " + var1 + ", var2: " + var2 + ", type: " + type + ")";
	}

}
