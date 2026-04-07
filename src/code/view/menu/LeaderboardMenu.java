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
//model import
import code.model.Leaderboard;
//view import
import code.view.images.StaticImage;
import code.view.menu.event.ReturnToMenu;

public class LeaderboardMenu extends AbstractMenu
{
	private static final BufferedImage entryBackground = StaticImage.ENTRY_BACKGROUND.getImage();
	private static final List<StaticImage> awardsList = StaticImage.getAwardsList();
	
	private static final BufferedImage normalExitButtonImage  = StaticImage.NORMAL_EXIT_BUTTON.getImage();
	private static final BufferedImage selectedExitButtonImage = StaticImage.SELECTED_EXIT_BUTTON.getImage();
	
	private static final int SCROLLPANE_WIDTH  = 749;
	private static final int SCROLLPANE_HEIGHT = 605;
	private static final int ENTRY_WIDTH       = 720;
	private static final int ENTRY_HEIGHT      = 65;
	private static final int AWARD_WIDTH       = 58;
	private static final int AWARD_HEIGHT      = 58;
	
	private static final int AWARD_NAME_PADDING  	  = 50;
	private static final int NAME_POINTS_PADDING 	  = 10;
	private static final int LEFT_AWARD_PADDING 	  = 10;
	private static final int VERTICAL_ENTRIES_PADDING = 10;
	private static final int EXIT_LEADERBOARD_PADDING = 15;
	private static final int EMPTYLEADERBOARD_SPACING = 100;
	
	private static final int PERMOUSEWHEEL_PIXEL = 16;
	
	private static final String EMPTY_LEADERBOARD_MSG = "Leaderboard is empty";
	
	private Leaderboard leaderboard;
	private Font leaderboardFont;
	
	public LeaderboardMenu(Leaderboard leaderboard, Font leaderboardFont)
	{
		this.leaderboard = leaderboard;
		this.leaderboardFont = leaderboardFont;
		
		buildMenu();
	}
	
	@Override 
	protected void buildMenu()
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
	
		MenuButton exitButton = new MenuButton(normalExitButtonImage, selectedExitButtonImage);
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.addActionListener(e -> getEventListener().notifyMenuEvent(new ReturnToMenu(this)));
		
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
}
