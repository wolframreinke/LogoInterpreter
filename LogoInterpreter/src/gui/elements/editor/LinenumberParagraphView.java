package gui.elements.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.text.Element;
import javax.swing.text.ParagraphView;
import javax.swing.text.View;

/**
 * The <code>LinenumberParagraphView</code> displays the line number on the left
 * hand side of each line.
 * 
 * @author Wolfram Reinke
 * @version 1.0
 *
 */
public class LinenumberParagraphView extends ParagraphView {

	/**
	 * The width in pixels of the line number bar.
	 */
	private static final short NUMBERS_WIDTH = 30;

	/**
	 * The <code>SourceCodeEditorPane</code> which is used to retrieve the current
	 * line. This line won't be displayed in light gray, but in blue.
	 */
	private SourceCodeEditorPane editor;
	
	/**
	 * Creates a new <code>LinenumberParagraphView</code> of the given element. The
	 * specified <code>SourceCodeEditorPane</code> is used to highlight the current
	 * line number.
	 *
	 * @param element
	 * 		The element for which this <code>View</code> is created.
	 * 
	 * @param editor
	 * 		The editor whose current line number will be rendered specially in the
	 * 		line number bar.
	 * 
	 */
	public LinenumberParagraphView( Element element, SourceCodeEditorPane editor ) {

		super( element );

		this.editor = editor;
		
		short top = 0;
		short left = 0;
		short bottom = 0;
		short right = 0;
		this.setInsets( top, left, bottom, right );
	}

	/*
	 * overwritten Javadoc comment is suitable
	 */
	@Override
	protected void setInsets( short top, short left, short bottom, short right ) {

		super.setInsets( top, (short) (left + NUMBERS_WIDTH), bottom, right );
	}

	/**
	 * Paints the line number bar on the left hand side by using the specified
	 * <code>Graphics</code> object.
	 * 
	 */
	@Override
	protected void paintChild( Graphics g, Rectangle alloc, int index ) {

		int lineNumber = getPreviosLineCount() + index + 1;
		int numberX = alloc.x - getLeftInset();
		// the 3 is used to move the output to the center of the line
		int numberY = alloc.y + alloc.height - 3;

		
		Color c;
		if ( this.editor != null && lineNumber == this.editor.getCurrentLineNumber() )
			c = Color.BLUE;
		else
			c = new Color( 0xF9, 0xF9, 0xF9 ); // very light gray
		
		g.setColor( c );

		// fill the bar with the color
		g.fillRect( alloc.x - getLeftInset(), alloc.y, getLeftInset() - 5,
				alloc.height );

		// and apint the number on it
		g.setColor( Color.BLACK );
		g.drawString( Integer.toString( lineNumber ), numberX, numberY );

		super.paintChild( g, alloc, index );

	}

	/**
	 * Calculates the number of lines before this <code>View</code>.
	 *
	 * @return
	 * 		The number of lines before this view.
	 */
	private int getPreviosLineCount() {

		int lineCount = 0;
		View parent = this.getParent();

		int count = parent.getViewCount();
		for ( int i = 0; i < count; i++ ) {

			if ( parent.getView( i ) == this )
				break;
			else
				lineCount += parent.getView( i ).getViewCount();
		}

		return lineCount;
	}
}
