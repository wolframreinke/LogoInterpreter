package gui.elements.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.text.Element;
import javax.swing.text.ParagraphView;
import javax.swing.text.View;

public class LinenumberParagraphView extends ParagraphView {

	private static final short NUMBERS_WIDTH = 30;

	private SourceCodeEditorPane editor;
	
	public LinenumberParagraphView( Element element, SourceCodeEditorPane editor ) {

		super( element );

		this.editor = editor;
		
		short top = 0;
		short left = 0;
		short bottom = 0;
		short right = 0;
		this.setInsets( top, left, bottom, right );
	}

	@Override
	protected void setInsets( short top, short left, short bottom, short right ) {

		super.setInsets( top, (short) (left + NUMBERS_WIDTH), bottom, right );
	}

	@Override
	protected void paintChild( Graphics g, Rectangle alloc, int index ) {

		int lineNumber = getPreviosLineCount() + index + 1;
		int numberX = alloc.x - getLeftInset();
		int numberY = alloc.y + alloc.height - 3;

		
		Color c;
		if ( lineNumber == this.editor.getCurrentLineNumber() )
			c = Color.BLUE;
		else
			c = new Color( 0xF9, 0xF9, 0xF9 );
		
		g.setColor( c );

		g.fillRect( alloc.x - getLeftInset(), alloc.y, getLeftInset() - 5,
				alloc.height );

		g.setColor( Color.BLACK );
		g.drawString( Integer.toString( lineNumber ), numberX, numberY );

		super.paintChild( g, alloc, index );

	}

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
