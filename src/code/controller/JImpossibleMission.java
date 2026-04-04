package code.controller;

import java.util.Arrays;
//graphics import
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
//model import
import code.model.context.GameContext;
import code.model.context.GameContext.UserInput;
import code.model.gameobjects.Player;
import code.model.world.GameWorld;
import code.model.Leaderboard;
import code.model.Point;
//view import
import code.view.Renderer;
import code.view.images.StaticImage;
import code.view.menu.CloseGame;
import code.view.menu.LeaderboardMenu;
import code.view.menu.Menu;
import code.view.menu.MenuEvent;
import code.view.menu.StartGame;
import code.view.menu.SwapToLeaderboard;

public class JImpossibleMission implements Menu.EventListener
{
	private static final String WINDOW_TITLE          = "Impossible mission";
	private static final String CUSTOMFONT_LOAD_ERROR = "Unable to load the leaderboard custom font";
	
	private JFrame frame;
	private Menu menu;
	private LeaderboardMenu leaderboard;
	private Renderer gamePanel;
	
	private static Font customFont;
	
	public static void main(String[] args)
	{ 
		try 
		{
			customFont = Font.createFont(
					Font.TRUETYPE_FONT, 
					LeaderboardMenu.class.getResourceAsStream("/resources/Menu/LeaderboardFont.ttf")
			).deriveFont(32f);
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont);
		} 
		catch (FontFormatException | IOException e)
		{ System.out.println(CUSTOMFONT_LOAD_ERROR); }
		
		new JImpossibleMission().start();
	}

	private void start()
	{
		frame = new JFrame(WINDOW_TITLE);
		menu = new Menu();
		
		menu.setListener(this);
		
		frame.setIconImage(StaticImage.WINDOW_ICON.getImage());
		frame.add(menu);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	@Override
	public void notifyMenuEvent(MenuEvent event)
	{
		if(event instanceof CloseGame)
			System.exit(0);
		
		if(event instanceof SwapToLeaderboard)
			printLeaderboard();
	}
	
	private void printLeaderboard()
	{
		leaderboard = new LeaderboardMenu(Leaderboard.load(), customFont);
		swapPanel(frame, menu, leaderboard);
	}

	private void swapPanel(JFrame frame, JPanel src, JPanel dest)
	{
		frame.remove(src);
		frame.add(dest);
		frame.revalidate(); 
		frame.pack();
		frame.repaint();
	}
	
	private static void bindAllKey(Renderer renderer, GameContext context)
	{
		bindKey(renderer, context, "LEFT_PRESSED",  KeyEvent.VK_LEFT,  true);
		bindKey(renderer, context, "RIGHT_PRESSED", KeyEvent.VK_RIGHT, true);
		bindKey(renderer, context, "UP_PRESSED",    KeyEvent.VK_UP,    true);
		bindKey(renderer, context, "DOWN_PRESSED",  KeyEvent.VK_DOWN,  true);
		bindKey(renderer, context, "JUMP_PRESSED",  KeyEvent.VK_SPACE, true);
		bindKey(renderer, context, "A_PRESSED",     KeyEvent.VK_A,     true);
		
		bindKey(renderer, context, "LEFT_RELEASED",  KeyEvent.VK_LEFT,  false);
		bindKey(renderer, context, "RIGHT_RELEASED", KeyEvent.VK_RIGHT, false);
		bindKey(renderer, context, "UP_RELEASED",    KeyEvent.VK_UP,    false);
		bindKey(renderer, context, "DOWN_RELEASED",  KeyEvent.VK_DOWN,  false);
		bindKey(renderer, context, "JUMP_RELEASED",  KeyEvent.VK_SPACE, false);
		bindKey(renderer, context, "A_RELEASED",     KeyEvent.VK_A,     false);
	}
	
	private static void bindKey(Renderer renderer, GameContext context, String keyName, int keyCode , boolean pressed)
	{
		InputMap inputMap = renderer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = renderer.getActionMap();
		
		inputMap.put(KeyStroke.getKeyStroke(keyCode, 0, !pressed), keyName);
		actionMap.put(keyName, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				switch(keyCode)
				{
					case KeyEvent.VK_LEFT  -> context.setUserInput(UserInput.LEFT,  pressed);
					case KeyEvent.VK_RIGHT -> context.setUserInput(UserInput.RIGHT, pressed);
					case KeyEvent.VK_UP    -> context.setUserInput(UserInput.UP,    pressed);
					case KeyEvent.VK_DOWN  -> context.setUserInput(UserInput.DOWN,  pressed);
					case KeyEvent.VK_SPACE -> context.setUserInput(UserInput.JUMP,  pressed);
					case KeyEvent.VK_A     -> context.setUserInput(UserInput.A_KEY, pressed);
				}
			}	
		});
	}
}
