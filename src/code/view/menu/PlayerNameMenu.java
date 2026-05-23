package code.view.menu;

//graphics import
import java.awt.image.BufferedImage;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
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
import code.view.menu.event.SecondaryMenuClosed;
import code.view.menu.event.GamePanelRequested;

/** Classe per il menù dove l'utente può selezionare il nome */
public class PlayerNameMenu extends JPanel
{
	/** L'immagine di sfondo */
	private static final BufferedImage backgroundImage = StaticImage.MENU_BACKGROUND.getImage();
	
	/** L'immagine del bottonoe di inizio partita quando non viene cliccato */
	private static final BufferedImage normalStartButtonImage   = StaticImage.NORMAL_START_BUTTON.getImage();
	/** L'immagine del bottonoe di inizio partita quando viene cliccato */
	private static final BufferedImage selectedStartButtonImage = StaticImage.SELECTED_START_BUTTON.getImage();
	/** L'immagine del bottonoe di uscita quando non viene cliccato */
	private static final BufferedImage normalExitButtonImage    = StaticImage.NORMAL_EXIT_BUTTON.getImage();
	/** L'immagine del bottonoe di uscita quando viene cliccato */
	private static final BufferedImage selectedExitButtonImage  = StaticImage.SELECTED_EXIT_BUTTON.getImage();
	/** Sfondo della casella di input dove l'utente può inserire il nome */
	private static final BufferedImage inputBoxBackground       = StaticImage.ENTRY_BACKGROUND.getImage();
	
	/** Larghezza del bottone di inizio gioco */
	private static final int STARTBUTTON_WIDTH  = 743;
	/** Altezza del bottone di inizio gioco */
	private static final int STARTBUTTON_HEIGHT = 127;
	/** Larghezza del bottone di inizioe gioco */
	private static final int EXITBUTTON_WIDTH   = 743;
	/** Altezza del bottone di inizio gioco */
	private static final int EXITBUTTON_HEIGHT  = 127;
	
	/** Spiazzamento verticale */
	private static final int Y_SPACING = 20;
	/** Dimensione massima dell'input dell'utente */
	private static final int MAX_INPUT_SIZE = 10;
	
	/** Il font personalizzato */
	private Font inputBoxFont;
	
	/** Classe per controllare l'input dell'utente */
	private class LimitDocumentFilter extends DocumentFilter 
	{
		/** Dimensione massima dell'input */
	    private final int max;

	    /**
	     * Costruice la classe
	     * @param max
	     * la dimensione massima dell'input
	     */
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
	
	/**
	 * Costruisce la classe e disegna il menù
	 * @param inputBoxFont
	 * il font personalizzato
	 */
	public PlayerNameMenu(Font inputBoxFont)
	{ 
		this.inputBoxFont = inputBoxFont;
		
		JTextField inputBox = new JTextField() {
			@Override
			public void paintComponent(Graphics g)
			{ 
				g.drawImage(inputBoxBackground, 0, 0, this.getWidth(), this.getHeight(), this); 
				super.paintComponent(g);
			}
		};
		
		MenuButton exitButton = new MenuButton(
				normalExitButtonImage, selectedExitButtonImage,
				EXITBUTTON_WIDTH, EXITBUTTON_HEIGHT
		);
		MenuButton startButton = new MenuButton(
				normalStartButtonImage, selectedStartButtonImage,
				STARTBUTTON_WIDTH, STARTBUTTON_HEIGHT
		);

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
		this.add(Box.createRigidArea(new Dimension(0, 5*Y_SPACING)));
		this.add(inputBox);
		SwingUtilities.invokeLater(() -> {inputBox.requestFocusInWindow(); });
		this.add(Box.createRigidArea(new Dimension(0, 5*Y_SPACING)));
		this.add(startButton);
		this.add(Box.createRigidArea(new Dimension(0, Y_SPACING)));
		this.add(exitButton);
		this.add(Box.createRigidArea(new Dimension(0, Y_SPACING)));
		
		exitButton.addActionListener(e -> EventDispatcher.notify(new SecondaryMenuClosed()));
		startButton.addActionListener(e -> EventDispatcher.notify(new GamePanelRequested(inputBox.getText())));
	}

	/** Usato per disegnare lo sfondo */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	    g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
