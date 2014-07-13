package gui;

import gui.elements.DrawPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import logo.commands.Turtle;

public class DrawTurtle implements Turtle{

	Graphics graphics;
	
	private Point formerPosition = new Point(199,199);
	private Point currentPosition = new Point(199,199);
	private static final Point MAX_POSITION = new Point(399, 399);
	private static final Point MIN_POSITION = new Point(0, 0);
	
	int currentAngleInDegree = 0;
	
	int indexOfCurrentColor = 0;

	Color[] colors = new Color[]{Color.black, Color.blue, Color.green.darker(), Color.red};
	
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
		this.currentPosition.x = 199;
		this.currentPosition.y = 199;
		this.formerPosition.x = 199;
		this.formerPosition.y = 199;
		
		this.currentAngleInDegree = 0;
	}

	@Override
	public void move(int distance) {
		this.formerPosition.x = this.currentPosition.x;
		this.formerPosition.y = this.currentPosition.y;
		
		this.currentPosition.x += distance * Math.cos( Math.toRadians( this.currentAngleInDegree - 90 ) );
		this.currentPosition.y += distance * Math.sin( Math.toRadians( this.currentAngleInDegree - 90 ) );
		
		if ( this.currentPosition.x > MAX_POSITION.x ) 
			this.currentPosition.x = this.currentPosition.x - MAX_POSITION.x + MIN_POSITION.x;
		
		if ( this.currentPosition.x < MIN_POSITION.x ) 
			this.currentPosition.x = MAX_POSITION.x + this.currentPosition.x - MIN_POSITION.x;
		
		if ( this.currentPosition.y > MAX_POSITION.y )
			this.currentPosition.y = this.currentPosition.y - MAX_POSITION.y + MIN_POSITION.y;
		
		if ( this.currentPosition.y < MIN_POSITION.y ) 
			this.currentPosition.y = MAX_POSITION.y + this.currentPosition.y - MIN_POSITION.y;

		if(this.penDown == true){
			Graphics graphics = this.drawPanel.getGraphics();
			graphics.setColor(this.colors[this.indexOfCurrentColor]);
			graphics.drawLine(this.currentPosition.x, this.currentPosition.y, this.formerPosition.x, this.formerPosition.y);
			graphics.dispose();
		}
	}

	@Override
	public void turn(int alpha) {
		this.currentAngleInDegree += alpha;
		while(this.currentAngleInDegree > 360) {
			this.currentAngleInDegree -= 360;
		}
		while(this.currentAngleInDegree < 0) {
			this.currentAngleInDegree += 360;
		}
	}

	@Override
	public void setPainting(boolean painting) {
		this.penDown = painting;
	}

	@Override
	public void clear() {
		this.drawPanel.repaint();
	}

	@Override
	public void setColor(int colorID) {
		if(colorID < 0)
			colorID = 0;
		if(colorID > 3)
			colorID = 3;
		this.indexOfCurrentColor = colorID;
	}
}
