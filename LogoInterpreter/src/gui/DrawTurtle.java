package gui;

import gui.elements.DrawPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import logo.commands.Turtle;

public class DrawTurtle implements Turtle{

	Graphics graphics;
	
	Point formerPosition = new Point(199,199);
	Point currentPosition = new Point(199,199);
	
	int currentAngleInDegree = 0;
	
	int indexOfCurrentColor = 0;

	Color[] colors = new Color[]{Color.BLACK, Color.BLUE, Color.GREEN, Color.RED};
	
	boolean penDown = true;

	private DrawPanel drawPanel;
	
	public DrawTurtle(Graphics graphics){
		this.graphics = graphics;
		this.reset();
	}
	
	public DrawTurtle(DrawPanel drawPanel){
		this.drawPanel = drawPanel;
	}

	@Override
	public void reset() {
		this.currentPosition.x = 0;//199;
		this.currentPosition.y = 0;//199;
		this.formerPosition.x = 199;
		this.formerPosition.y = 199;
	}

	@Override
	public void move(int distance) {
		this.formerPosition.x = this.currentPosition.x;
		this.formerPosition.y = this.currentPosition.y;
		
		this.currentPosition.x  += distance * Math.sin(this.currentAngleInDegree);
		this.currentPosition.y += distance * Math.cos(this.currentAngleInDegree);
	//	if(penDown == true){
			this.drawPanel.getGraphics().setColor(this.colors[this.indexOfCurrentColor]);
			this.drawPanel.getGraphics().drawLine(this.currentPosition.x, this.currentPosition.y, this.formerPosition.x, this.formerPosition.y);
		//}
	}

	@Override
	public void turn(int alpha) {
		this.currentAngleInDegree += alpha;
	}

	@Override
	public void setPainting(boolean painting) {
		this.penDown = painting;
	}

	@Override
	public void clear() {
		// TODO zeichfläche leer machen
		System.out.println("clear");
		
	}

	@Override
	public void setColor(int colorID) {
		if(colorID < 0)
			colorID = 0;
		if(colorID > 3)
			colorID= 3;
		this.indexOfCurrentColor = colorID;
	}
}
