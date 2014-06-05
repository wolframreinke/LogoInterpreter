package logo;

import gui.Turtle;

import java.awt.Graphics;


public interface ICommand {

	public abstract void execute( Graphics g, Turtle turtle );
}
