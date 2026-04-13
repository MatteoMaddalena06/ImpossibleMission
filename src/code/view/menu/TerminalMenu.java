package code.view.menu;

//graphics import
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
//view import
import code.view.images.StaticImage;
import code.view.menu.event.PlatformResetRequested;
import code.view.menu.event.RobotDisableRequested;
import code.view.menu.event.TerminalClosed;
import code.event.EventDispatcher;
//model import
import code.model.gameobjects.Player;

public class TerminalMenu extends JPanel
{
	public static final BufferedImage terminalWallpaperImage = StaticImage.TERMINAL_WALLPAPER.getImage();
	
	public static final BufferedImage normalRobotDisableButtonImage    = StaticImage.NORMAL_ROBODISABLE_BUTTON.getImage();
	public static final BufferedImage normalPlatformResetButtonImage   = StaticImage.NORMAL_PLATFORMREST_BUTTON.getImage();
	public static final BufferedImage normalExitButtonButtonImage      = StaticImage.NORMAL_EXIT_BUTTON.getImage();
	public static final BufferedImage selectedRobotDisableButtonImage  = StaticImage.SELECTED_ROBOTDISABLE_BUTTON.getImage();
	public static final BufferedImage selectedPlatformResetButtonImage = StaticImage.SELECTED_PLATFORMRESET_BUTTON.getImage(); 
	public static final BufferedImage selectedExtiButtonImage          = StaticImage.SELECTED_EXIT_BUTTON.getImage();
	
	public static final BufferedImage labelBackgroundIcon = StaticImage.ENTRY_BACKGROUND.getImage();
	
	private static final int ROBOTDISABLEBUTTON_WIDTH   = 300;
	private static final int ROBOTDISABLEBUTTON_HEIGHT  = 100;
	private static final int PLATFORMRESETBUTTON_WIDTH  = 300;
	private static final int PLATFORMRESETBUTTON_HEIGHT = 100;
	private static final int EXITBUTTON_WIDTH         	= 250;
	private static final int EXITBUTTON_HEIGHT         	= 50;
	
	private static final int Y_SPACING = 20;
	private static final int X_SPACING = 20;
	
	private static final int WIDTH  = 500;
	private static final int HEIGHT = 350;
	
	private static final float TERMINAL_FONT_SIZE = 45f;
	
	public TerminalMenu(Player player, Font terminalFont)
	{
		JLabel numberOfRobotDisablePasswords = new JLabel(Integer.toString(player.getRobotPasswordsObtained()));
		JLabel numberOfPlatformRestPasswords = new JLabel(Integer.toString(player.getPlatformPasswordsObtained()));
		
		MenuButton robotDisableButton = new MenuButton(
				normalRobotDisableButtonImage, selectedRobotDisableButtonImage,
				ROBOTDISABLEBUTTON_WIDTH, ROBOTDISABLEBUTTON_HEIGHT
		);
		MenuButton platformResetButton = new MenuButton(
				normalPlatformResetButtonImage, selectedPlatformResetButtonImage,
				PLATFORMRESETBUTTON_WIDTH, PLATFORMRESETBUTTON_HEIGHT
		);
		MenuButton exitButton = new MenuButton(
				normalExitButtonButtonImage, selectedExtiButtonImage,
				EXITBUTTON_WIDTH , EXITBUTTON_HEIGHT
		);
		
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		numberOfRobotDisablePasswords.setFont(terminalFont.deriveFont(TERMINAL_FONT_SIZE));
		numberOfPlatformRestPasswords.setFont(terminalFont.deriveFont(TERMINAL_FONT_SIZE));
		
		JPanel firstRowPanel = new JPanel();
		JPanel secondRowPanel = new JPanel();
		
		firstRowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		firstRowPanel.add(Box.createRigidArea(new Dimension(X_SPACING, 0)));
		firstRowPanel.add(numberOfRobotDisablePasswords);
		firstRowPanel.add(Box.createRigidArea(new Dimension(X_SPACING, 0)));
		firstRowPanel.add(robotDisableButton);
		firstRowPanel.add(Box.createRigidArea(new Dimension(X_SPACING, 0)));
		
		secondRowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		secondRowPanel.add(Box.createRigidArea(new Dimension(X_SPACING, 0)));
		secondRowPanel.add(numberOfPlatformRestPasswords);
		secondRowPanel.add(Box.createRigidArea(new Dimension(X_SPACING, 0)));
		secondRowPanel.add(platformResetButton);
		secondRowPanel.add(Box.createRigidArea(new Dimension(X_SPACING, 0)));
		
		firstRowPanel.setOpaque(false);
		secondRowPanel.setOpaque(false);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createRigidArea(new Dimension(0, Y_SPACING)));
		this.add(firstRowPanel);
		this.add(Box.createRigidArea(new Dimension(0, Y_SPACING)));
		this.add(secondRowPanel);
		this.add(Box.createRigidArea(new Dimension(0, Y_SPACING)));
		this.add(exitButton);
		this.add(Box.createRigidArea(new Dimension(0, Y_SPACING)));
		
		robotDisableButton.addActionListener(e -> {
			if(player.getRobotPasswordsObtained() != 0)
				EventDispatcher.notify(new TerminalClosed());
			
			EventDispatcher.notify(new RobotDisableRequested());
		});
		platformResetButton.addActionListener(e -> {
			if(player.getPlatformPasswordsObtained() != 0)
				EventDispatcher.notify(new TerminalClosed());
			
			EventDispatcher.notify(new PlatformResetRequested());
		});
		exitButton.addActionListener(e -> EventDispatcher.notify(new TerminalClosed()));
	}
	
	public void setPositionInFrame(int frameWidth, int frameHeight)
	{ this.setBounds((frameWidth - WIDTH) / 2, (frameHeight - HEIGHT) / 2, WIDTH, HEIGHT); }
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(terminalWallpaperImage, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
