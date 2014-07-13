package gui.elements.editor;

import javax.swing.text.AbstractDocument;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

/**
 * A <code>LinenumberEditorKit</code> is a <code>StyledEditorKit</code> which addes
 * a line number bar on the left hand side of <code>SourceCodeEditorPane</code>s.
 * 
 * @author Wolfram Reinke
 * @version 1.0
 * 
 */
@SuppressWarnings( "serial" )
public class LinenumberEditorKit extends StyledEditorKit {

	/**
	 * A <code>LinenumberViewFactory</code> creates views of some portions of a
	 * <code>SourceCodeEditorPane</code>'s document. Among others, it addes a line
	 * number bar on the left hand side of the <code>SourceCodeEditorPane</code>. 
	 * 
	 * @author Wolfram Reinke
	 * @version 1.0
	 * @see {@link LinenumberParagraphView}
	 */
	public static class LinenumberViewFactory implements ViewFactory {

		/**
		 * The line number bar will be added to this <code>SourceCodeEditorPane</code>.
		 */
		private SourceCodeEditorPane editor;

		/**
		 * Creates a new <code>LinenumberViewFactory</code> which creates views for
		 * the specified <code>SourceCodeEditorPane</code>.
		 *
		 * @param editor
		 * 		The <code>SourceCodeEditorPane</code> for which the views are
		 * 		created. If this value is <code>null</code>, the 
		 * 		current-line-highlighting will not work, but line numbers will be added
		 * 		though.
		 */
		public LinenumberViewFactory( SourceCodeEditorPane editor ) {

			this.editor = editor;
		}

		/**
		 * Creates a suitable view for the given element.
		 * 
		 * @param element
		 * 		The <code>Element</code>, for which the view is created.
		 * 
		 * @return
		 * 		A suitable view for the specified element.
		 * 
		 */
		@Override
		public View create( Element element ) {

			String type = element.getName();

			switch ( type ) {

			case AbstractDocument.ContentElementName:
				return new LabelView( element );

			case AbstractDocument.ParagraphElementName:
				return new LinenumberParagraphView( element, this.editor );

			case AbstractDocument.SectionElementName:
				return new BoxView( element, View.Y_AXIS );

			case StyleConstants.ComponentElementName:
				return new ComponentView( element );

			case StyleConstants.IconElementName:
				return new IconView( element );

			default:
				return new LabelView( element );

			}
		}

	}

	/**
	 * The line number bar will be added to this <code>SourceCodeEditorPane</code>.
	 */
	private SourceCodeEditorPane editor;

	/**
	 * Creates a new <code>LinenumberEditorKit</code> with the specified
	 * <code>SourceCodeEditorPane</code>.
	 *
	 * @param editor
	 * 		The <code>SourceCodeEditorPane</code> to which the line number bar will
	 * 		be added. If this parameter is <code>null</code>, the line number bar
	 * 		will be added though, but the current-line-highlighting won't work.
	 */
	public LinenumberEditorKit( SourceCodeEditorPane editor ) {

		super();
		this.editor = editor;
	}

	@Override
	public ViewFactory getViewFactory() {

		return new LinenumberViewFactory( this.editor );
	}
}
