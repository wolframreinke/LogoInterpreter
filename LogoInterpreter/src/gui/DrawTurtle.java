package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import logo.commands.Turtle;

public class DrawTurtle implements Turtle{

	Graphics graphics;
	
	Point formerPosition = new Point(199,199);
	Point currentPosition = new Point(199,199);
	
	int currentAngleInDegree = 0;
	
	Color currentColor;

	Color[] colors = new Color[]{Color.BLACK, Color.BLUE, Color.GREEN, Color.RED};
	
	boolean penDown = true;
	
	public DrawTurtle(Graphics graphics){
		this.graphics = graphics;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		System.out.println("reset");
	}

	@Override
	public void move(int distance) {
		formerPosition.x = currentPosition.x;
		formerPosition.y = currentPosition.y;
		
		//posx += distance * (sin(aktuellem winkel))
		//pos y += distance * (cos(aktuellem winkel))
		if(penDown == true){
			//drawline
		}
	}

	@Override
	public void turn(int alpha) {
		currentAngleInDegree += alpha;
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
		currentColor = colors[colorID];
	}
}
