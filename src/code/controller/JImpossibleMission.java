package code.controller;

//graphics import
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
//model import
import code.model.context.GameContext;
import code.model.gameobjects.Player;
import code.model.room.Room;
import code.model.room.RoomMap;
import code.model.world.GameWorld;
import code.model.Leaderboard;
import code.model.Point;
//view import
import code.view.Renderer;
import code.view.images.StaticImage;
import code.view.menu.LeaderboardMenu;
import code.view.menu.Menu;
import code.view.menu.PlayerNameMenu;
import code.view.menu.event.ReturnToMenu;
import code.view.menu.event.StartGame;
import code.view.menu.event.CloseGame;
import code.view.menu.event.SwapToLeaderboard;
import code.view.menu.event.SwapToPlayerNameMenu;
//controller import
import code.controller.event.StopGame;
import code.controller.event.SwitchToTerminal;
//event import
import code.event.EventDispatcher;

public class JImpossibleMission
{
	private static final String WINDOW_TITLE          = "Impossible mission";
	private static final String CUSTOMFONT_LOAD_ERROR = "Unable to load menu custom font";
	
	private static final String MAIN_MENU_ID  	    = "MAIN_MENU";
	private static final String PLAYER_MENU_ID 		= "PLAYER_MENU_ID";
	private static final String LEADERBOARD_MENU_ID = "LEADERBOARD_MENU_ID";
	private static final String GAMEPANEL_ID        = "GAMEPANEL_ID";
	
	private static final float FONT_SIZE = 32f;
	private static final BufferedImage CUSTOM_FRAME_ICON = StaticImage.WINDOW_ICON.getImage();
	private static Font customFont = UIManager.getFont("Label.font").deriveFont(FONT_SIZE);
	
	private static final int FRAME_WIDTH  = RoomMap.MAP_WIDTH * RoomMap.TILE_SIZE;
	private static final int FRAME_HEIGHT = RoomMap.MAP_HEIGHT * RoomMap.TILE_SIZE;
	
	private LeaderboardMenu oldLeaderboardPanel;
	private Renderer oldGamePanel;
	
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
		JFrame frame = new JFrame(WINDOW_TITLE);
		JPanel rootPanel = new JPanel();
		Menu mainMenu = new Menu();
		PlayerNameMenu playerMenu = new PlayerNameMenu(customFont);
		
		CardLayout layout = new CardLayout();
		rootPanel.setLayout(layout);
		rootPanel.add(mainMenu, MAIN_MENU_ID);
		rootPanel.add(playerMenu, PLAYER_MENU_ID);
		
		initEventHandler(rootPanel, layout);
		
		frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		frame.pack();
		frame.setContentPane(rootPanel);	
		frame.setIconImage(CUSTOM_FRAME_ICON);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	private void initEventHandler(JPanel rootPanel, CardLayout layout)
	{
		EventDispatcher.subscribe(CloseGame.class,            x -> System.exit(0));
		EventDispatcher.subscribe(ReturnToMenu.class,         x -> layout.show(rootPanel, MAIN_MENU_ID));
		EventDispatcher.subscribe(SwapToPlayerNameMenu.class, x -> layout.show(rootPanel, PLAYER_MENU_ID));
		EventDispatcher.subscribe(StartGame.class,            x -> { x = (StartGame)x; startGame(x.playerName(), rootPanel, layout); });
		EventDispatcher.subscribe(SwapToLeaderboard.class,    x -> swapToLeaderboard(rootPanel, layout));
		EventDispatcher.subscribe(StopGame.class,             x -> layout.show(rootPanel, MAIN_MENU_ID) );
		EventDispatcher.subscribe(SwitchToTerminal.class,     x -> {});
	}
	
	private void startGame(String playerName, JPanel rootPanel, CardLayout layout)
	{
		GameWorld world = new GameWorld();
		Player player = new Player(playerName, new Point(60, 60));
		Room rndRoom = Arrays.stream(world.getWorldMatrix()).flatMap(r -> Arrays.stream(r)).filter(r -> r != null).findAny().get();
		GameContext context = new GameContext(player, rndRoom, Leaderboard.load());
		context.setPlayerSpawn(new Point(60, 60));
		
		Renderer gamePanel = new Renderer(player, context);
		GameLoop gameLoop = new GameLoop(context, gamePanel);
		
		if(oldGamePanel != null)
			rootPanel.remove(oldGamePanel);

		oldGamePanel = gamePanel;
		
		rootPanel.add(gamePanel, GAMEPANEL_ID);
		layout.show(rootPanel, GAMEPANEL_ID);
		gameLoop.start();
	}
	
	private void swapToLeaderboard(JPanel rootPanel, CardLayout layout)
	{
	    LeaderboardMenu leaderboardPanel = new LeaderboardMenu(Leaderboard.load(), customFont);

	    if(oldLeaderboardPanel != null)
	    	rootPanel.remove(oldLeaderboardPanel);
	    
	    oldLeaderboardPanel = leaderboardPanel;
	    
	    rootPanel.add(leaderboardPanel, LEADERBOARD_MENU_ID);   
	    layout.show(rootPanel, LEADERBOARD_MENU_ID);
	}
	
	private void swapToTerminalMenu(JFrame frame)
	{
		
	}
}
