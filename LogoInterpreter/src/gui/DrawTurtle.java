package gui;

import gui.elements.DrawPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import logo.commands.Turtle;

/**
 * A <code>DrawTurtle</code>, which can move and turn. It draws a line, when moving. The color of the line can be changed. Drawing can also be turned of.
 * Also, the turtle can be reseted to its starting position. The lines, that were drawn by the turtle can be deleted.
 * <br><b>Note: All methods except the constructor, shall only be used by the interpreter.</b>
 * @author Julian Schäfer
 */
public class DrawTurtle implements Turtle{
	
	/**
	 * An Element in this <code>DrawTurtle</code>'s history. This element consists
	 * of a start point, an end point and a color. The history element can be
	 * restore using {@link #restore(Graphics)}.
	 */
	private static class HistoryElement {
		
		public Point2D.Double start;
		public Point2D.Double dest;
		public Color color;
		
		/**
		 * Creates a new history element
		 * @param start
		 * the start point
		 * @param dest
		 * the end point
		 * @param color
		 * the color
		 */
		public HistoryElement(Point2D.Double start, Point2D.Double dest, Color color) {
			this.start = start;
			this.dest = dest;
			this.color = color;
		}
		
		/**
		 * restores this history element by painting a line
		 * @param graphics
		 * the graphics which is used to paint this history element
		 */
		public void restore(Graphics graphics) {

			if (graphics != null && this.color != null) {

				graphics.setColor(this.color);
				graphics.drawLine(
						(int)this.start.x, 
						(int)this.start.y, 
						(int)this.dest.x, 
						(int)this.dest.y);
			}
		}
	}
	
	private List<HistoryElement> history = new ArrayList<HistoryElement>();
	
	private Point2D.Double formerPosition = new Point2D.Double(199.0,199.0);
	private Point2D.Double currentPosition = new Point2D.Double(199.0,199.0);
	private Point2D.Double maxPosition = new Point2D.Double(399.0, 399.0);
	private static final Point MIN_POSITION = new Point(0, 0);
	
	private int currentAngleInDegree = 0;
	
	private int indexOfCurrentColor = 0;

	private Color[] colors = new Color[]{Color.black, Color.blue, Color.green.darker(), Color.red};
	
	private boolean penDown = true;

	private DrawPanel drawPanel;
	
	/**
	 * Creates a <code>DrawTurtle</code>.<br>
	 * @param drawPanel
	 * <code>drawPanel</code> is the draw panel in which the turtle is drawn.
	 * The turtle will automatically adapt to its size.
	 */
	public DrawTurtle(DrawPanel drawPanel){
		this.drawPanel = drawPanel;
		
		//sets the current position according to panel, in which the turtle is drawn
		this.currentPosition.x = (this.drawPanel.getPreferredSize().width/2) - 1;
		this.currentPosition.x = (this.drawPanel.getPreferredSize().height/2) - 1;
		
		//sets the max position according to panel, in which the turtle is drawn
		this.maxPosition.x = this.drawPanel.getPreferredSize().width;
		this.maxPosition.y = this.drawPanel.getPreferredSize().height;
	}

	@Override
	/**
	 * This method resets the turtle back to its starting position at (200/200) and resets the turn radius to 0.
	 * <br><b>Note: This method shall only be used by the interpreter.</b>
	 */
	public void reset() {
		this.currentPosition.x = 199;
		this.currentPosition.y = 199;
		this.formerPosition.x = 199;
		this.formerPosition.y = 199;
		
		this.currentAngleInDegree = 0;
	}

	@Override
	/**
	 * Moves the turtle by <code>distance</code>
	 * <br><b>Note: This method shall only be used by the interpreter.</b>
	 * @param distance
	 * The distance, the turtle is moving.
	 * <br>Moving forward requires a positive value,
	 * <br>moving backward a negative one.
	 */
	public void move(double distance) {
		//reset the former position
		this.formerPosition.x = this.currentPosition.x;
		this.formerPosition.y = this.currentPosition.y;
		
		//reset the current position
		this.currentPosition.x += distance * Math.cos(Math.toRadians(this.currentAngleInDegree - 90));
		this.currentPosition.y += distance * Math.sin(Math.toRadians(this.currentAngleInDegree - 90));
		
		//helps a little against over- and underflows(i know it could be done better, but due to a lack of time, it isn't...)
		//no painting if the coordinates have an under-/overflow
		boolean overflow = false;
		
		if (this.currentPosition.x > this.maxPosition.x) { 
			this.currentPosition.x = this.currentPosition.x - this.maxPosition.x + MIN_POSITION.x;
			overflow = true;
		}
			
		if (this.currentPosition.x < MIN_POSITION.x) {
			this.currentPosition.x = this.maxPosition.x + this.currentPosition.x - MIN_POSITION.x;
			overflow = true;
		}
		
		if (this.currentPosition.y > this.maxPosition.y) {
			this.currentPosition.y = this.currentPosition.y - this.maxPosition.y + MIN_POSITION.y;
			overflow = true;
		}
			
		if (this.currentPosition.y < MIN_POSITION.y) {
			this.currentPosition.y = this.maxPosition.y + this.currentPosition.y - MIN_POSITION.y;
			overflow = true;
		}
		//Draw, if pen is down
		if(this.penDown == true && !overflow){
			this.history.add(new HistoryElement(
					(Point2D.Double)this.formerPosition.clone(), 
					(Point2D.Double)this.currentPosition.clone(), 
					this.colors[this.indexOfCurrentColor]));
		}
		else
			this.history.add(new HistoryElement(
					(Point2D.Double)this.formerPosition.clone(), 
					(Point2D.Double)this.currentPosition.clone(),
					null));
	
	
		Graphics pen = this.drawPanel.getGraphics();
		
		this.repaint(pen);
		
		for (HistoryElement element : this.history) {
			element.restore(pen);
		}
		
		this.paintTurlte(pen);
		
		pen.dispose();
	}

	/**
	 * repaints the draw panel by using the specified <code>Graphics</code> 
	 * object
	 * @param pen
	 * The <code>Graphics</code> which used to repaint the canvas.
	 */
	private void repaint(Graphics pen) {

		pen.setColor(Color.white);
		pen.fillRect(
				MIN_POSITION.x, 
				MIN_POSITION.y, 
				(int)this.maxPosition.x, 
				(int)this.maxPosition.y);
	}

	/**
	 * Paints the turtle by using the specified <code>Graphics</code> object.
	 * @param pen
	 * The graphics object which is used to paint the turtle
	 */
	private void paintTurlte(Graphics pen) {

		pen.setColor(Color.BLACK);
		
		// paint the turtle by using vectors
		Point2D.Double vector = new Point2D.Double();
		vector.x = (15 * Math.cos(Math.toRadians(this.currentAngleInDegree - 90)));
		vector.y = (15 * Math.sin(Math.toRadians(this.currentAngleInDegree - 90)));
		
		Point2D.Double point = (Point2D.Double) this.currentPosition.clone();
		
		Polygon p = new Polygon();
		p.addPoint((int)point.x, (int)point.y);
		
		point.x -= vector.x + vector.y/3;
		point.y -= vector.y - vector.x/3;
		p.addPoint((int)point.x, (int)point.y);
		
		point = (Point2D.Double) this.currentPosition.clone();
		point.x -= 1.5 * vector.x;
		point.y -= 1.5 * vector.y;
		p.addPoint((int)point.x, (int)point.y);
		
		point = (Point2D.Double) this.currentPosition.clone();
		point.x -= vector.x - vector.y/3;
		point.y -= vector.y + vector.x/3;
		p.addPoint((int)point.x, (int)point.y);
		
		point = (Point2D.Double) this.currentPosition.clone();
		p.addPoint((int)point.x, (int)point.y);
		
		pen.fillPolygon(p);
		
	}

	@Override
	/**
	 * This method adds <code>alpha</code> to the variable <code>currentAngleInDegree</code>.
	 * <br><b>Note: This method shall only be used by the interpreter.</b>
	 * @param alpha
	 * <code>alpha</code> <b>must<b> be in degree.
	 * If the turtle shall turn:<br>
	 * -leftbound, <code>alpha</code> must be neative<br>
	 * -rightbound, <code>alpha</code> must be positive.<br><br><b>
	 */
	public void turn(double alpha) {
		this.currentAngleInDegree += alpha;
		//makes sure, we don't get any overflows
		while(this.currentAngleInDegree > 360) {
			this.currentAngleInDegree -= 360;
		}
		while(this.currentAngleInDegree < 0) {
			this.currentAngleInDegree += 360;
		}
	}

	@Override
	/**
	 * The boolean variable <code>penDown</code> is set. This variable determines, weather the turtle is drawing, when it moves.<br><br><b>
	 * Note: This method shall only be used by the interpreter.</b>
	 * @param painting
	 * -true: the turtle draws<br>
	 * -false: the turtle doesn't draw
	 */
	public void setPainting(boolean painting) {
		this.penDown = painting;
	}

	@Override
	/**
	 * This method clears the drawing panel, which means, that all line, that were drawn by the turtle, will be gone.<br><br><b>
	 * Note: This method shall only be used by the interpreter.</b>
	 */
	public void clear() {
		this.history.clear();
		this.drawPanel.repaint();
	}

	@Override
	/**
	 * This method sets the color of the lines, the turtle is printing.<br>
	 * Values:<br
	 * <= 0: black
	 * == 1: blue
	 * == 2: darkgreen
	 * >= 3: red<br><br><b>
	 * Note: This method shall only be used by the interpreter.</b>
	 * @param colorID
	 */
	public void setColor(int colorID) {
		if(colorID < 0)
			colorID = 0;
		if(colorID > 3)
			colorID = 3;
		this.indexOfCurrentColor = colorID;
	}
}
