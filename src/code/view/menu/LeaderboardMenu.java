package code.view.menu;

//data structures import
import java.util.List;
//graphics import
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
//event import
import code.event.EventDispatcher;
//model import
import code.model.Leaderboard;
//view import
import code.view.images.StaticImage;
import code.view.menu.event.SecondaryMenuClosed;

/** Classe per il menù della classifica */
public class LeaderboardMenu extends JPanel
{
	/** L'immagine di sfondo */
	private static final BufferedImage backgroundImage = StaticImage.MENU_BACKGROUND.getImage();
	/** L'immagine di sfondo di una riga della classifica */
	private static final BufferedImage entryBackground = StaticImage.ENTRY_BACKGROUND.getImage();
	/** Le immagini delle ricompense che appaiono in classifica */
	private static final List<StaticImage> awardsList  = StaticImage.getAwardsList();
	
	/** L'immagine del bottone di uscita quando non viene premuto */
	private static final BufferedImage normalExitButtonImage  = StaticImage.NORMAL_EXIT_BUTTON.getImage();
	/** L'immagine del bottone di uscita quando viene premuto */
	private static final BufferedImage selectedExitButtonImage = StaticImage.SELECTED_EXIT_BUTTON.getImage();
	
	/** Larghezza della finestra a scorrimento */
	private static final int SCROLLPANE_WIDTH  = 749;
	/** Altezza della finestra a scorrimento */
	private static final int SCROLLPANE_HEIGHT = 605;
	/** Larghezza di una riga della classifica */
	private static final int ENTRY_WIDTH       = 720;
	/** Altezza di una riga della classifica */
	private static final int ENTRY_HEIGHT      = 65;
	/** Larghezza dell'icona della ricompensa */
	private static final int AWARD_WIDTH       = 58;
	/** Altezza dell'icona della ricompensa */
	private static final int AWARD_HEIGHT      = 58;
	/** Larghezza del bottone per uscire */
	private static final int EXITBUTTON_WIDTH  = 749;
	/** Altezza del botton per uscire */
	private static final int EXITBUTTON_HEIGHT = 122;
	
	/** Spiazzamento orizzontale AWARD | NAME */
	private static final int AWARD_NAME_PADDING  	  = 50;
	/** Spizzamaneto orizzontale NAME | POINTS */
	private static final int NAME_POINTS_PADDING 	  = 10;
	/** Spiazzamento orizzontale sinistro per l'icona della ricompensa */
	private static final int LEFT_AWARD_PADDING 	  = 10;
	/** Spizzamento verticale per le righe della classifica */
	private static final int VERTICAL_ENTRIES_PADDING = 10;
	/** Spizzamento verticale fra la classifica e il bottone di uscita */
	private static final int EXIT_LEADERBOARD_PADDING = 15;
	/** Spiazzamento verticale per la scritta che informa l'assenza di contenuto nella classifica*/
	private static final int EMPTYLEADERBOARD_SPACING = 100;
	
	/** Numero di pixel da scorrere nella finestra a scorrimento per ogni rotazione della rotellina del mouse */
	private static final int PERMOUSEWHEEL_PIXEL = 16;
	
	/** Messaggio da visualizzare quando la classfica è vuota */
	private static final String EMPTY_LEADERBOARD_MSG = "Leaderboard is empty";
	
	/**
	 * Costruisce la classe e disegna la classifica
	 * @param leaderboard
	 * la classifica da cui estrapolare i dati
	 * @param leaderboardFont
	 * il font personalizzato 
	 */
	public LeaderboardMenu(Leaderboard leaderboard, Font leaderboardFont)
	{
		JPanel entriesPanel = new JPanel();
		List<Leaderboard.Entry> leaderboardContent = leaderboard.getContent();
		
		entriesPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		entriesPanel.setOpaque(false);
		entriesPanel.setLayout(new BoxLayout(entriesPanel, BoxLayout.Y_AXIS));
		
		for(int i = 0; i < leaderboardContent.size(); i++)
		{
			entriesPanel.add(Box.createRigidArea(new Dimension(0, VERTICAL_ENTRIES_PADDING)));
			entriesPanel.add(createEntryPanel(leaderboardContent.get(i), leaderboardFont));
		}
	
		MenuButton exitButton = new MenuButton(
				normalExitButtonImage, selectedExitButtonImage,
				EXITBUTTON_WIDTH, EXITBUTTON_HEIGHT
		);
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.addActionListener(e -> EventDispatcher.notify(new SecondaryMenuClosed()));
		
		JScrollPane scrollPane = new JScrollPane(entriesPanel);
		scrollPane.setPreferredSize(new Dimension(SCROLLPANE_WIDTH, SCROLLPANE_HEIGHT));
		scrollPane.setMinimumSize(new Dimension(SCROLLPANE_WIDTH,SCROLLPANE_HEIGHT));
		scrollPane.setMaximumSize(new Dimension(SCROLLPANE_WIDTH, SCROLLPANE_HEIGHT));
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(PERMOUSEWHEEL_PIXEL);
		
		JLabel emptyLeaderboardLabel = new JLabel(EMPTY_LEADERBOARD_MSG);
		emptyLeaderboardLabel.setFont(leaderboardFont);
		emptyLeaderboardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(exitButton);
		
		if(leaderboardContent.size() != 0)
		{
			this.add(Box.createRigidArea(new Dimension(0, EXIT_LEADERBOARD_PADDING)));
			this.add(scrollPane);
		}
		else
		{
			this.add(Box.createRigidArea(new Dimension(0, EMPTYLEADERBOARD_SPACING)));
			this.add(emptyLeaderboardLabel);
			this.add(Box.createRigidArea(new Dimension(0, EMPTYLEADERBOARD_SPACING)));
		}
	}
	
	/**
	 * Crea uan rida della classifica
	 * @param entry
	 * la riga da cui estrapolare i dati
	 * @param leaderboardFont
	 * il font personalizzato
	 * @return 
	 * la riga
	 */
	private JPanel createEntryPanel(Leaderboard.Entry entry, Font leaderboardFont)
	{
		JPanel entryPanel = new JPanel() {
			@Override
		    protected void paintComponent(Graphics g) 
		    { 
		    	super.paintComponent(g);
		    	g.drawImage(entryBackground, 0, 0, getWidth(), getHeight(), this);
		    }
		};
		
		entryPanel.setPreferredSize(new Dimension(ENTRY_WIDTH, ENTRY_HEIGHT));
		entryPanel.setMinimumSize(new Dimension(ENTRY_WIDTH, ENTRY_HEIGHT));
		entryPanel.setMaximumSize(new Dimension(ENTRY_WIDTH, ENTRY_HEIGHT));
		
		JLabel awardLabel = new JLabel() {
		    @Override
		    protected void paintComponent(Graphics g) 
		    { 
		    	super.paintComponent(g);
		    	BufferedImage awardImage;
		    	
		    	int rank = entry.getRank();
		    	
		    	if(entry.getPoints() == 0)
		    		awardImage = awardsList.get(awardsList.size() - 1).getImage();
		    	
		    	else
		    		awardImage = awardsList.get((rank < 2) ? rank : awardsList.size() - 2).getImage(); 
		    	
		    	g.drawImage(awardImage, 0, 0, getWidth(), getHeight(), this);
		    }
		};
		
		awardLabel.setPreferredSize(new Dimension(AWARD_WIDTH, AWARD_HEIGHT));
		awardLabel.setMinimumSize(new Dimension(AWARD_WIDTH, AWARD_HEIGHT));
		awardLabel.setMaximumSize(new Dimension(AWARD_WIDTH, AWARD_HEIGHT));
		
		JLabel nameLabel = new JLabel(entry.getName());
		JLabel pointsLabel = new JLabel("Points: " + Integer.toString(entry.getPoints()));
		
		nameLabel.setFont(leaderboardFont);
		pointsLabel.setFont(leaderboardFont);
		
		entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.X_AXIS));
		entryPanel.add(Box.createRigidArea(new Dimension(LEFT_AWARD_PADDING, 0)));
		entryPanel.add(awardLabel);
		entryPanel.add(Box.createRigidArea(new Dimension(AWARD_NAME_PADDING , 0)));
		entryPanel.add(nameLabel);
		entryPanel.add(Box.createHorizontalGlue());
		entryPanel.add(pointsLabel);
		entryPanel.add(Box.createRigidArea(new Dimension(NAME_POINTS_PADDING, 0)));
		
		return entryPanel;
	}
	
	/** Usato per disegnare lo sfondo */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	    g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
