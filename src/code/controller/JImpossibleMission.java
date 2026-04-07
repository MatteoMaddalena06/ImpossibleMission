package code.controller;

//graphics import
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
//model import
import code.model.context.GameContext;
import code.model.context.GameContext.UserInput;
import code.model.gameobjects.Player;
import code.model.room.Room;
import code.model.world.GameWorld;
import code.model.Leaderboard;
import code.model.Point;
//view import
import code.view.Renderer;
import code.view.images.StaticImage;
import code.view.menu.LeaderboardMenu;
import code.view.menu.Menu;
import code.view.menu.PlayerNameMenu;
import code.view.menu.event.MenuEventListener;
import code.view.menu.event.ReturnToMenu;
import code.view.menu.event.StartGame;
import code.view.menu.event.CloseGame;
import code.view.menu.event.MenuEvent;
import code.view.menu.event.SwapToLeaderboard;
import code.view.menu.event.SwapToPlayerNameMenu;

public class JImpossibleMission implements MenuEventListener
{
	private static final String WINDOW_TITLE          = "Impossible mission";
	private static final String CUSTOMFONT_LOAD_ERROR = "Unable to load menu custom font";

	private static final float FONT_SIZE = 32f;
	private static Font customFont = UIManager.getFont("Label.font").deriveFont(FONT_SIZE);
	
	private JFrame frame;
	private Menu menu;
	
	public static void main(String[] args)
	{ 
		try 
		{
			customFont = Font.createFont(
					Font.TRUETYPE_FONT, 
					LeaderboardMenu.class.getResourceAsStream("/resources/Menu/LeaderboardFont.ttf")
			).deriveFont(FONT_SIZE);
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
		menu.setEventListener(this);
		
		frame.setIconImage(StaticImage.WINDOW_ICON.getImage());
		frame.add(menu);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	@Override
	public void notifyMenuEvent(MenuEvent event)
	{
		if(event instanceof CloseGame)
			System.exit(0);
		
		else if(event instanceof ReturnToMenu)
			swapPanel(frame, ((ReturnToMenu)event).from(), menu);
		
		else if(event instanceof SwapToLeaderboard)
		{
			LeaderboardMenu leaderboard = new LeaderboardMenu(Leaderboard.load(), customFont);
			leaderboard.setEventListener(this);
			swapPanel(frame, menu,  leaderboard);
		}
		
		else if(event instanceof SwapToPlayerNameMenu)
		{
			PlayerNameMenu playerNameMenu = new PlayerNameMenu(customFont);
			playerNameMenu.setEventListener(this);
			swapPanel(frame, menu, playerNameMenu);
		}
		
		else if(event instanceof StartGame)
		{
			GameWorld world = new GameWorld();
			Player player = new Player(((StartGame)event).playerName(), new Point(60, 60));
			
			Room randRoom = Arrays.stream(world.getWorldMatrix()).flatMap(r -> Arrays.stream(r)).filter(r -> r != null).findAny().get();
			GameContext context = new GameContext(player, randRoom, Leaderboard.load());
			context.setPlayerSpawn(new Point(60, 60));
			
			Renderer gamePanel = new Renderer(player);
			gamePanel.setCurrentSpritesList(player, randRoom);
			
			GameLoop gameLoop = new GameLoop(context, gamePanel);
			
			context.setStateListener(gameLoop);
			context.setEventListener(gamePanel);
			bindAllKey(gamePanel, context);
			swapPanel(frame, ((StartGame)event).from(), gamePanel);
			gameLoop.start();
		}
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
		
		bindKey(renderer, context, "LEFT_RELEASED",  KeyEvent.VK_LEFT,  false);
		bindKey(renderer, context, "RIGHT_RELEASED", KeyEvent.VK_RIGHT, false);
		bindKey(renderer, context, "UP_RELEASED",    KeyEvent.VK_UP,    false);
		bindKey(renderer, context, "DOWN_RELEASED",  KeyEvent.VK_DOWN,  false);
		bindKey(renderer, context, "JUMP_RELEASED",  KeyEvent.VK_SPACE, false);
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
				}
			}	
		});
	}
}
