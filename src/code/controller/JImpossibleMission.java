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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
//model import
import code.model.context.GameContext;
import code.model.gameobjects.Player;
import code.model.room.RoomMap;
import code.model.GameWorld;
import code.model.Leaderboard;
import code.model.Point;
//view import
import code.view.Renderer;
import code.view.audio.AudioPlayer;
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

/**
 * La classe principale del gioco che contiene il metodo main(String[] args).
 * Fa parte del controller e si occupa di creare la finestra e gestire i cambi di stato impartitegli dalla view.
 */
public class JImpossibleMission
{
	/** Il titolo della finestra di gioco */
	private static final String WINDOW_TITLE          = "Impossible mission";
	/** L'errore da visualizzare in caso di errato caricamento del font personalizzato*/
	private static final String CUSTOMFONT_LOAD_ERROR = "Unable to load menu custom font";
	/** ID del menù principale per il {@link CardLayout}*/
	private static final String MAIN_MENU_ID  	    = "MAIN_MENU";
	/** ID del menù per inserire il nome del giocatore per il {@link CardLayout}*/
	private static final String PLAYER_MENU_ID 		= "PLAYER_MENU_ID";
	/** ID del menù per la classifica di gioco per il {@link CardLayout}*/
	private static final String LEADERBOARD_MENU_ID = "LEADERBOARD_MENU_ID";
	/** ID del panello di gioco per il {@link CardLayout}*/
	private static final String GAMEPANEL_ID        = "GAMEPANEL_ID";
	
	/** La dimensione del font personalizzato */
	private static final float FONT_SIZE = 32f;
	/** L'immagine della finestra di gioco */
	private static final BufferedImage CUSTOM_FRAME_ICON = StaticImage.WINDOW_ICON.getImage();
	/** Il font personalizzato */
	private static Font customFont = UIManager.getFont("Label.font").deriveFont(FONT_SIZE);
	
	/** La dimensione orizzontale della finestra di gioco*/
	private static final int FRAME_WIDTH  = RoomMap.PIXELS_MAP_WIDTH;
	/** La dimensione verticale della finestra di gioco*/
	private static final int FRAME_HEIGHT = RoomMap.PIXELS_MAP_HEIGHT + RoomMap.TILE_SIZE;
	
	/**Il mondo di gioco  */
	private GameWorld world;
	/**Il vecchio menù della classifica da sostituire quando il giocatore apre un nuovo menù della classifica di gioco */
	private LeaderboardMenu oldLeaderboardPanel;
	/**Il vecchio pannello di gioco da sostituire quando il giocatore termina la partita */
	private Renderer oldGamePanel;
	/**La vecchia interfaccia del terminale da sostituire quando il giocatore apre un nuovo terminale */
	private TerminalMenu oldTerminalMenu;
	/**La vecchia interfaccia per la composizione dei pezzi di puzzle da sostiture quando il giocatore ne apre un'altra */
	private PuzzleMenu oldPuzzleMenu;
	
	/** Il punto di ingresso del gioco */
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

	/**
	 * Si occupa di creare il necessario per l'avvio del gioco:
	 * <ul>
	 * 	<li>crea la finestra di gioco</li>
	 *  <li>registra gli event handler di questa classe (si veda {@link code.controller.JImpossibleMission#initEventHandler(JPanel, CardLayout, JLayeredPane)})</li>
	 *  <li>avvia la musica di sottofondo (si veda {@link code.view.audio.AudioPlayer#playBackgroundMusic()})</li>
	 * </ul>
	 */
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
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		
		AudioPlayer.getInstance().playBackgroundMusic();
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{ closeGame(); }
		});
	}
	
	/**
	 * Registra gli event handler necessari per rispondere correttamente agli eventi generati dalla View e dalla Model.
	 * @param rootPanel il {@link JPanel} principale della finestra di gioco.
	 * @param layout il {@link CardLayout} usato per il menù.
	 * @param layeredPane il {@link JLayeredPane} della finestra di gioco
	 */
	private void initEventHandler(JPanel rootPanel, CardLayout layout, JLayeredPane layeredPane)
	{
		EventDispatcher.subscribeAsStatic(CloseGame.class,                x -> closeGame());
		EventDispatcher.subscribeAsStatic(SecondaryMenuClosed.class,      x -> layout.show(rootPanel, MAIN_MENU_ID));
		EventDispatcher.subscribeAsStatic(InputBoxMenuRequested.class,    x -> layout.show(rootPanel, PLAYER_MENU_ID));
		EventDispatcher.subscribeAsStatic(GamePanelRequested.class,       x -> { x = (GamePanelRequested)x; startGame(x.playerName(), rootPanel, layout, layeredPane); });
		EventDispatcher.subscribeAsStatic(LeaderboardMenuRequested.class, x -> swapToLeaderboard(rootPanel, layout));
		EventDispatcher.subscribeAsStatic(StopGame.class,                 x -> stopGame(rootPanel, layout));
		EventDispatcher.subscribeAsStatic(TerminalMenuRequested.class,    x -> swapToTerminalMenu(((TerminalMenuRequested)x).player(), layeredPane));
		EventDispatcher.subscribeAsStatic(TerminalClosed.class,           x -> { oldTerminalMenu.setVisible(false); EventDispatcher.notify(new GameResumed()); });
		EventDispatcher.subscribeAsStatic(PuzzleMenuRequested.class,      x -> swapToPuzzleMenu(((PuzzleMenuRequested)x).player(), layeredPane));
		EventDispatcher.subscribeAsStatic(PuzzleMenuClosed.class,         x -> { oldPuzzleMenu.setVisible(false); EventDispatcher.notify(new GameResumed()); });
	}
	
	/**
	 * Avvia la partita (un event handler)
	 * @param playerName il nome del giocatore che intende iniziare la partita
	 * @param rootPanel il {@link JPanel} principale della finestra di gioco.
	 * @param layout il {@link CardLayout} usato per il menù.
	 * @param layeredPane il {@link JLayeredPane} della finestra di gioco
	 */
	private void startGame(String playerName, JPanel rootPanel, CardLayout layout, JLayeredPane layeredPane)
	{
		Player player = new Player(playerName, new Point(Player.START_GAME_SPAWN_X, Player.START_GAME_SPAWN_Y), new Point(Player.START_GAME_WORLD_X, Player.START_GAME_WORLD_Y));
		world = new GameWorld();
		GameContext context = new GameContext(player, world, Leaderboard.load());
		
		Renderer gamePanel = new Renderer(context);
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
	
	/**
	 * Termina una partita precedentemente iniziata (un event handler)
	 * @param rootPanel il {@link JPanel} principale della finestra di gioco.
	 * @param layout il {@link CardLayout} usato per il menù.
	 */
	private void stopGame(JPanel rootPanel, CardLayout layout)
	{
		 layout.show(rootPanel, MAIN_MENU_ID); 
		 EventDispatcher.disposeListeners();
		 AudioPlayer.getInstance().disposeRunningClips(); 	
		 world = null;
		 oldLeaderboardPanel = null;
		 oldTerminalMenu = null; 
		 oldPuzzleMenu = null;
	}

	/** Termina il gioco (un event handler) */
	private void closeGame()
	{ 
		AudioPlayer.getInstance().disposeBackgroundMusic();
		AudioPlayer.getInstance().disposeRunningClips(); 
		System.exit(0);
	}
	
	/**
	 * Permette di passare all'interfaccia della classifica di gioco (un event handler)
	 * @param rootPanel il {@link JPanel} principale della finestra di gioco.
	 * @param layout il {@link CardLayout} usato per il menù.
	 */
	private void swapToLeaderboard(JPanel rootPanel, CardLayout layout)
	{
	    LeaderboardMenu leaderboardPanel = new LeaderboardMenu(Leaderboard.load(), customFont);

	    if(oldLeaderboardPanel != null)
	    	rootPanel.remove(oldLeaderboardPanel);
	    
	    oldLeaderboardPanel = leaderboardPanel;
	    
	    rootPanel.add(leaderboardPanel, LEADERBOARD_MENU_ID);   
	    layout.show(rootPanel, LEADERBOARD_MENU_ID);
	}
	
	/**
	 * Permette di passare all'interfaccia dei terminali (un event handler)
	 * @param rootPanel il {@link JPanel} principale della finestra di gioco.
	 * @param layout il {@link CardLayout} usato per il menù.
	 */
	private void swapToTerminalMenu(Player player, JLayeredPane layeredPane)
	{
		TerminalMenu terminalMenu = new TerminalMenu(player, customFont);
			
		if(oldTerminalMenu != null)
			layeredPane.remove(oldTerminalMenu);
		
		oldTerminalMenu = terminalMenu;
		
		terminalMenu.setPositionInFrame(FRAME_WIDTH, FRAME_HEIGHT);
		layeredPane.add(terminalMenu, JLayeredPane.PALETTE_LAYER);
	}
	
	/**
	 * Permette di passare all'interfaccia del menu per la composizione dei pezzi di puzzle (un event handler)
	 * @param rootPanel il {@link JPanel} principale della finestra di gioco.
	 * @param layout il {@link CardLayout} usato per il menù.
	 */
	private void swapToPuzzleMenu(Player player, JLayeredPane layeredPane)
	{
		PuzzleMenu puzzleMenu = new PuzzleMenu(world.getWorldPassword(), player.getPuzzlePiecesObtained(), customFont);
			
		if(oldPuzzleMenu != null)
			layeredPane.remove(oldPuzzleMenu);
		
		oldPuzzleMenu = puzzleMenu;
		
		puzzleMenu.setPositionInFrame(FRAME_WIDTH, FRAME_HEIGHT);
		layeredPane.add(puzzleMenu, JLayeredPane.PALETTE_LAYER);
	}
}
