package code.controller;

//graphics import
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
//model import
import code.model.context.GameContext;
import code.model.gameobjects.Player;
import code.model.puzzle.PresettedPassword;
import code.model.puzzle.PuzzlePiece;
import code.model.room.RoomMap;
import code.model.GameWorld;
import code.model.Leaderboard;
import code.model.Point;
//view import
import code.view.Renderer;
import code.view.images.StaticImage;
import code.view.menu.LeaderboardMenu;
import code.view.menu.Menu;
import code.view.menu.PlayerNameMenu;
import code.view.menu.TerminalMenu;
import code.view.menu.event.*;
import code.view.menu.PuzzleMenu;
//controller import
import code.controller.event.StopGame;
import code.controller.event.TerminalMenuRequested;
import code.controller.event.GameResumed;
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
	
	private static final int FRAME_WIDTH  = RoomMap.PIXELS_MAP_WIDTH;
	private static final int FRAME_HEIGHT = RoomMap.PIXELS_MAP_HEIGHT + RoomMap.TILE_SIZE;
	
	private LeaderboardMenu oldLeaderboardPanel;
	private Renderer oldGamePanel;
	private TerminalMenu oldTerminalMenu;
	
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

		JFrame frame = new JFrame();
		PuzzleMenu puzzle = new PuzzleMenu(PresettedPassword.PASSWORD1, List.of(PuzzlePiece.values()), customFont);
		
		frame.add(puzzle);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//new JImpossibleMission().start();
	}

	private void start()
	{
		JFrame frame = new JFrame(WINDOW_TITLE);
		JPanel rootPanel = new JPanel();
		Menu mainMenu = new Menu();
		PlayerNameMenu playerMenu = new PlayerNameMenu(customFont);
		JLayeredPane layeredPane = new JLayeredPane();
		
		CardLayout layout = new CardLayout();
		rootPanel.setLayout(layout);
		rootPanel.add(mainMenu, MAIN_MENU_ID);
		rootPanel.add(playerMenu, PLAYER_MENU_ID);
		rootPanel.add(layeredPane, GAMEPANEL_ID);
		
		initEventHandler(rootPanel, layout, layeredPane);
		
		frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		frame.pack();
		frame.setContentPane(rootPanel);	
		frame.setIconImage(CUSTOM_FRAME_ICON);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	private void initEventHandler(JPanel rootPanel, CardLayout layout, JLayeredPane layeredPane)
	{
		EventDispatcher.subscribe(CloseGame.class,                x -> System.exit(0));
		EventDispatcher.subscribe(SecondaryMenuClosed.class,      x -> layout.show(rootPanel, MAIN_MENU_ID));
		EventDispatcher.subscribe(InputBoxMenuRequested.class,    x -> layout.show(rootPanel, PLAYER_MENU_ID));
		EventDispatcher.subscribe(GamePanelRequested.class,       x -> { x = (GamePanelRequested)x; startGame(x.playerName(), rootPanel, layout, layeredPane); });
		EventDispatcher.subscribe(LeaderboardMenuRequested.class, x -> swapToLeaderboard(rootPanel, layout));
		EventDispatcher.subscribe(StopGame.class,                 x -> layout.show(rootPanel, MAIN_MENU_ID) );
		EventDispatcher.subscribe(TerminalMenuRequested.class,    x -> swapToTerminalMenu(((TerminalMenuRequested)x).player(), layeredPane));
		EventDispatcher.subscribe(TerminalClosed.class,           x -> { oldTerminalMenu.setVisible(false); EventDispatcher.notify(new GameResumed()); });
	}
	
	private void startGame(String playerName, JPanel rootPanel, CardLayout layout, JLayeredPane layeredPane)
	{
		Player player = new Player(playerName, new Point(Player.START_GAME_SPAWN_X, Player.START_GAME_SPAWN_Y), new Point(Player.START_GAME_WORLD_X, Player.START_GAME_WORLD_Y));
		GameContext context = new GameContext(player, new GameWorld(), Leaderboard.load());
		
		Renderer gamePanel = new Renderer(player, context);
		GameLoop gameLoop = new GameLoop(context, gamePanel);
		
		CountDownLatch latch = new CountDownLatch(1);
		gamePanel.setFirstPaintLatch(latch);
	
		if(oldGamePanel != null)
			layeredPane.remove(oldGamePanel);

		oldGamePanel = gamePanel;
		
		gamePanel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
		layout.show(rootPanel, GAMEPANEL_ID);
		
		new Thread(() -> {
		    try { latch.await(); } catch (InterruptedException e) {}
		    gameLoop.start();
		}).start();
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
	
	private void swapToTerminalMenu(Player player, JLayeredPane layeredPane)
	{
		TerminalMenu terminalMenu = new TerminalMenu(player, customFont);
			
		if(oldTerminalMenu != null)
			layeredPane.remove(oldTerminalMenu);
		
		oldTerminalMenu = terminalMenu;
		
		terminalMenu.setPositionInFrame(FRAME_WIDTH, FRAME_HEIGHT);
		layeredPane.add(terminalMenu, JLayeredPane.PALETTE_LAYER);
	}
}
