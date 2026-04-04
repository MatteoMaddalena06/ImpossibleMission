package code.view.menu;

//graphics import
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.Box;
//view import
import code.view.images.StaticImage;

public class Menu extends JPanel
{
	private static final BufferedImage titleImage                     = StaticImage.MENU_TITLE.getImage();
	private static final BufferedImage normalStartButtonImage         = StaticImage.NORMAL_START_BUTTON.getImage();
	private static final BufferedImage normalLeaderboardButtonImage   = StaticImage.NORMAL_LEADERBOARD_BUTTON.getImage();
	private static final BufferedImage normalExitButtonImage          = StaticImage.NORMAL_EXIT_BUTTON.getImage();
	private static final BufferedImage selectedStartButtonImage       = StaticImage.SELECTED_START_BUTTON.getImage();
	private static final BufferedImage selectedLeaderboardButtonImage = StaticImage.SELECTED_LEADERBOARD_BUTTON.getImage();
	private static final BufferedImage selectedExitButtonImage        = StaticImage.SELECTED_EXIT_BUTTON.getImage();
	private static final BufferedImage backgroundImage       	      = StaticImage.MENU_BACKGROUND.getImage();
	
	private static final ImageIcon titleIcon 			         = new ImageIcon(titleImage);
	private static final ImageIcon normalStartButtonIcon 	     = new ImageIcon(normalStartButtonImage);
	private static final ImageIcon normalLeaderboardButtonIcon   = new ImageIcon(normalLeaderboardButtonImage);
	private static final ImageIcon normalExitButtonIcon          = new ImageIcon(normalExitButtonImage);
	private static final ImageIcon selectedStartButtonIcon       = new ImageIcon(selectedStartButtonImage);
	private static final ImageIcon selectedLeaderboardButtonIcon = new ImageIcon(selectedLeaderboardButtonImage);
	private static final ImageIcon selectedExitButtonIcon        = new ImageIcon(selectedExitButtonImage);
	
	private static final String normalIconID   = "normalIcon";
	private static final String selectedIconID = "selectedIcon";
	
	private EventListener listener;
	
	public interface EventListener
	{ public void notifyMenuEvent(MenuEvent event); }
	
	public Menu()
	{
		JLabel title = new JLabel(titleIcon);
		JButton startButton = new JButton(normalStartButtonIcon);
		JButton leaderboardButton = new JButton(normalLeaderboardButtonIcon);
		JButton exitButton = new JButton(normalExitButtonIcon);
		
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		startButton.putClientProperty(normalIconID, normalStartButtonIcon);
		startButton.putClientProperty(selectedIconID, selectedStartButtonIcon);
		startButton.setPreferredSize(new Dimension(normalStartButtonImage.getWidth(), normalStartButtonImage.getHeight()));
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		leaderboardButton.putClientProperty(normalIconID, normalLeaderboardButtonIcon);
		leaderboardButton.putClientProperty(selectedIconID, selectedLeaderboardButtonIcon);
		leaderboardButton.setPreferredSize(new Dimension(selectedStartButtonImage.getWidth(), selectedStartButtonImage.getHeight()));
		leaderboardButton.setBorderPainted(false);
		leaderboardButton.setContentAreaFilled(false);
		leaderboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		exitButton.putClientProperty(normalIconID, normalExitButtonIcon);
		exitButton.putClientProperty(selectedIconID, selectedExitButtonIcon);
		exitButton.setPreferredSize(new Dimension(normalExitButtonImage.getWidth(), normalExitButtonImage.getHeight()));
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(title);
		this.add(Box.createRigidArea(new Dimension(0, 20))); 
		this.add(startButton);
		this.add(Box.createRigidArea(new Dimension(0, 20))); 
		this.add(leaderboardButton);
		this.add(Box.createRigidArea(new Dimension(0, 20))); 
		this.add(exitButton);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		
		MouseAdapter buttonListener = new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) 
		    {
		    	JButton button = (JButton)e.getSource();
		    	button.setIcon((Icon)button.getClientProperty(selectedIconID));
		    }
		
		    @Override
		    public void mouseExited(MouseEvent e) 
		    {
		    	JButton button = (JButton)e.getSource();
		    	button.setIcon((Icon)button.getClientProperty(normalIconID));
		    }
		    
		    @Override
		    public void mousePressed(MouseEvent e) 
		    {
		    	JButton button = (JButton)e.getSource();
		    	button.setBorder(BorderFactory.createEmptyBorder(3, 3, 0, 0));
		    }

		    @Override
		    public void mouseReleased(MouseEvent e) 
		    {
		    	JButton button = (JButton)e.getSource();
		    	button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		    }
		};
		
		startButton.addMouseListener(buttonListener);
		leaderboardButton.addMouseListener(buttonListener);
		exitButton.addMouseListener(buttonListener);
		
		exitButton.addActionListener(e -> listener.notifyMenuEvent(new CloseGame()));
		startButton.addActionListener(e -> listener.notifyMenuEvent(new StartGame()));
		leaderboardButton.addActionListener(e -> listener.notifyMenuEvent(new  SwapToLeaderboard()));
	}
	
	@Override 
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	}
	
	public void setListener(EventListener listener)
	{ this.listener = listener; }
}
