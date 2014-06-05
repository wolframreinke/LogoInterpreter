package logo;

public interface ICommand {

	public abstract void execute( Turtle turtle ) throws VariableUndefinedException;
}
