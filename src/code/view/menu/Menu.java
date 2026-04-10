package code.view.menu;

//graphics import
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.Box;
//event import
import code.event.EventDispatcher;
//view import
import code.view.images.StaticImage;
import code.view.menu.event.CloseGame;
import code.view.menu.event.SwapToLeaderboard;
import code.view.menu.event.SwapToPlayerNameMenu;

public class Menu extends AbstractMenu
{
	private static final ImageIcon     titleIcon                      = new ImageIcon(StaticImage.MENU_TITLE.getImage());
	private static final BufferedImage normalStartButtonImage         = StaticImage.NORMAL_START_BUTTON.getImage();
	private static final BufferedImage normalLeaderboardButtonImage   = StaticImage.NORMAL_LEADERBOARD_BUTTON.getImage();
	private static final BufferedImage normalExitButtonImage          = StaticImage.NORMAL_EXIT_BUTTON.getImage();
	private static final BufferedImage selectedStartButtonImage       = StaticImage.SELECTED_START_BUTTON.getImage();
	private static final BufferedImage selectedLeaderboardButtonImage = StaticImage.SELECTED_LEADERBOARD_BUTTON.getImage();
	private static final BufferedImage selectedExitButtonImage        = StaticImage.SELECTED_EXIT_BUTTON.getImage();
	
	private static final int Y_SPACING = 20;
	
	public Menu()
	{ buildMenu(); }
	
	@Override 
	protected void buildMenu()
	{
		JLabel title = new JLabel(titleIcon);
		MenuButton startButton = new MenuButton(normalStartButtonImage, selectedStartButtonImage);
		MenuButton leaderboardButton = new MenuButton(normalLeaderboardButtonImage, selectedLeaderboardButtonImage);
		MenuButton exitButton = new MenuButton(normalExitButtonImage, selectedExitButtonImage);
		
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
		
		startButton.addActionListener(e -> EventDispatcher.notify(new SwapToPlayerNameMenu()));
		leaderboardButton.addActionListener(e -> EventDispatcher.notify(new SwapToLeaderboard()));
		exitButton.addActionListener(e -> EventDispatcher.notify(new CloseGame()));
	}
}
