package logo;

@SuppressWarnings( "serial" )
public class VariableUndefinedException extends Exception {

	private String variableName;
	
	public VariableUndefinedException( String variableName ) {
		super( "The variable \"" + variableName +"\" is undefined." );
	
		this.variableName = variableName;
	}
	
	public String getVariableName() {
		return variableName;
	}
}
