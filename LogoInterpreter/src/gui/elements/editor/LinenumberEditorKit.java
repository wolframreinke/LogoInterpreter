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


@SuppressWarnings( "serial" )
public class LinenumberEditorKit extends StyledEditorKit {

	public static class LinenumberViewFactory implements ViewFactory {

		private SourceCodeEditorPane editor;

		public LinenumberViewFactory( SourceCodeEditorPane editor ) {
			this.editor = editor;
		}
		
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

	private SourceCodeEditorPane editor;
	
	public LinenumberEditorKit( SourceCodeEditorPane editor ) {

		super();
		this.editor = editor;
	}

	@Override
	public ViewFactory getViewFactory() {
		
		return new LinenumberViewFactory( this.editor );
	}
}

