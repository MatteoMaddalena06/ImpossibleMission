package code.view.menu;

//graphics import
import java.awt.image.BufferedImage;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
//event import
import code.event.EventDispatcher;
//view import
import code.view.images.StaticImage;
import code.view.menu.event.ReturnToMenu;
import code.view.menu.event.StartGame;

public class PlayerNameMenu extends AbstractMenu 
{
	private static final BufferedImage normalStartButtonImage   = StaticImage.NORMAL_START_BUTTON.getImage();
	private static final BufferedImage selectedStartButtonImage = StaticImage.SELECTED_START_BUTTON.getImage();
	private static final BufferedImage normalExitButtonImage    = StaticImage.NORMAL_EXIT_BUTTON.getImage();
	private static final BufferedImage selectedExitButtonImage  = StaticImage.SELECTED_EXIT_BUTTON.getImage();
	private static final BufferedImage inputBoxBackground       = StaticImage.ENTRY_BACKGROUND.getImage();
	
	private static final int Y_SPACING = 20;
	private static final int MAX_INPUT_SIZE = 10;
	
	private Font inputBoxFont;
	
	private class LimitDocumentFilter extends DocumentFilter 
	{
	    private final int max;

	    public LimitDocumentFilter(int max) 
	    { this.max = max; }

	    @Override
	    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException 
	    {
	        if (fb.getDocument().getLength() + string.length() <= max) 
	            super.insertString(fb, offset, string, attr);
	    }

	    @Override
	    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException
	    {
	        if (fb.getDocument().getLength() - length + text.length() <= max) 
	        	super.replace(fb, offset, length, text, attrs);
	    }
	}
	
	public PlayerNameMenu(Font inputBoxFont)
	{ 
		this.inputBoxFont = inputBoxFont;
		buildMenu(); 
	}

	@Override
	protected void buildMenu() 
	{
		JTextField inputBox = new JTextField() {
			@Override
			public void paintComponent(Graphics g)
			{ 
				g.drawImage(inputBoxBackground, 0, 0, this.getWidth(), this.getHeight(), this); 
				super.paintComponent(g);
			}
		};
		
		MenuButton exitButton = new MenuButton(normalExitButtonImage, selectedExitButtonImage);
		MenuButton startButton = new MenuButton(normalStartButtonImage, selectedStartButtonImage);
		
		inputBox.setOpaque(false);
		inputBox.setFont(inputBoxFont);
		inputBox.setFocusable(true);
		inputBox.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
		inputBox.setHorizontalAlignment(JTextField.CENTER);
		((AbstractDocument)inputBox.getDocument()).setDocumentFilter(new LimitDocumentFilter(MAX_INPUT_SIZE));

		inputBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createRigidArea(new Dimension(0, Y_SPACING)));
		this.add(inputBox);
		SwingUtilities.invokeLater(() -> {inputBox.requestFocusInWindow(); });
		this.add(Box.createRigidArea(new Dimension(0, Y_SPACING)));
		this.add(startButton);
		this.add(Box.createRigidArea(new Dimension(0, Y_SPACING)));
		this.add(exitButton);
		this.add(Box.createRigidArea(new Dimension(0, Y_SPACING)));
		
		exitButton.addActionListener(e -> EventDispatcher.notify(new ReturnToMenu()));
		startButton.addActionListener(e -> EventDispatcher.notify(new StartGame(inputBox.getText())));
	}	
}
