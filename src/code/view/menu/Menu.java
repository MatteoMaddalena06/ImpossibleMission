package code.view.menu;

//graphics import
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.Box;
//event import
import code.event.EventDispatcher;
//view import
import code.view.images.StaticImage;
import code.view.menu.event.CloseGame;
import code.view.menu.event.LeaderboardMenuRequested;
import code.view.menu.event.InputBoxMenuRequested;

public class Menu extends JPanel
{
	private static final BufferedImage backgroundImage = StaticImage.MENU_BACKGROUND.getImage();
	
	private static final ImageIcon     titleIcon                      = new ImageIcon(StaticImage.MENU_TITLE.getImage());
	private static final BufferedImage normalStartButtonImage         = StaticImage.NORMAL_START_BUTTON.getImage();
	private static final BufferedImage normalLeaderboardButtonImage   = StaticImage.NORMAL_LEADERBOARD_BUTTON.getImage();
	private static final BufferedImage normalExitButtonImage          = StaticImage.NORMAL_EXIT_BUTTON.getImage();
	private static final BufferedImage selectedStartButtonImage       = StaticImage.SELECTED_START_BUTTON.getImage();
	private static final BufferedImage selectedLeaderboardButtonImage = StaticImage.SELECTED_LEADERBOARD_BUTTON.getImage();
	private static final BufferedImage selectedExitButtonImage        = StaticImage.SELECTED_EXIT_BUTTON.getImage();
	
	private static final int STARTBUTTON_WIDTH  	  = 743;
	private static final int STARTBUTTON_HEIGHT 	  = 127;
	private static final int LEADERBOARDBUTTON_WIDTH  = 743;
	private static final int LEADERBOARDBUTTON_HEIGHT = 127;
	private static final int EXITBUTTON_WIDTH         = 743;
	private static final int EXITBUTTON_HEIGHT        = 127;
	
	private static final int Y_SPACING = 20;
	
	public Menu()
	{ 
		JLabel title = new JLabel(titleIcon);
		
		MenuButton startButton = new MenuButton(
				normalStartButtonImage, selectedStartButtonImage, 
				STARTBUTTON_WIDTH, STARTBUTTON_HEIGHT
		);
		MenuButton leaderboardButton = new MenuButton(
				normalLeaderboardButtonImage, selectedLeaderboardButtonImage,
				LEADERBOARDBUTTON_WIDTH, LEADERBOARDBUTTON_HEIGHT
		);
		MenuButton exitButton = new MenuButton(
				normalExitButtonImage, selectedExitButtonImage,
				EXITBUTTON_WIDTH, EXITBUTTON_HEIGHT
		);
		
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);	
		leaderboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createRigidArea(new Dimension(0, Y_SPACING)));
		this.add(title);
		this.add(Box.createRigidArea(new Dimension(0, Y_SPACING))); 
		this.add(startButton);
		this.add(Box.createRigidArea(new Dimension(0, Y_SPACING))); 
		this.add(leaderboardButton);
		this.add(Box.createRigidArea(new Dimension(0, Y_SPACING))); 
		this.add(exitButton);
		this.add(Box.createRigidArea(new Dimension(0, Y_SPACING)));
		
		startButton.addActionListener(e -> EventDispatcher.notify(new InputBoxMenuRequested()));
		leaderboardButton.addActionListener(e -> EventDispatcher.notify(new LeaderboardMenuRequested()));
		exitButton.addActionListener(e -> EventDispatcher.notify(new CloseGame()));
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	    g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
