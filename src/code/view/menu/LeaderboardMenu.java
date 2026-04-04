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
import java.awt.Dimension;
import java.awt.Font;
//model import
import code.model.Leaderboard;
//view import
import code.view.images.StaticImage;

public class LeaderboardMenu extends JPanel
{
	private static final BufferedImage backgroundImage = StaticImage.MENU_BACKGROUND.getImage();
	private static final BufferedImage entryBackground = StaticImage.ENTRY_BACKGROUND.getImage();
	private static final List<StaticImage> awardsList = StaticImage.getAwardsList();
	
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
	
	private static final int PERMOUSEWHEEL_PIXEL = 16;
	
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
		
		this.add(scrollPane);
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
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	}
}
