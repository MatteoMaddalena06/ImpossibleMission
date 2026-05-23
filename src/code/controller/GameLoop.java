package code.controller;

//data structures import
import java.util.List;
//graphics import
import javax.swing.SwingUtilities;
//model import
import code.model.context.GameContext;
import code.model.context.GameWillEnd;
import code.model.context.PlayerDied;
import code.model.context.TerminalOpened;
import code.model.context.StopSimulation;
import code.model.gameobjects.GameObject;
import code.model.gameobjects.PlatformCluster;
import code.model.gameobjects.Player;
import code.model.gameobjects.enemy.Enemy;
import code.model.room.Room;
import code.model.Leaderboard;
//view import
import code.view.Renderer;
import code.view.menu.event.RobotDisableRequested;
import code.view.menu.event.PlatformResetRequested;
import code.view.menu.event.PuzzleMenuOpened;
import code.view.menu.event.PuzzleMenuRequested;
import code.view.sprites.AnimatedSprite;
import code.view.sprites.PlayerSprite;
//controller import
import code.controller.event.StopGame;
import code.controller.event.TerminalMenuRequested;
import code.controller.event.GameResumed;
//event import
import code.event.EventDispatcher;

/**
 * La classe per il gameloop del gioco. Si occupa di aggiornare lo stato della simulazione
 */
public class GameLoop extends Thread
{
	/** Il pannello di gioco */
	private Renderer renderer;
	/** Il contesto di gioco corrente */
	private GameContext context;
	
	/** Flag per stabilire se mettere in pausa la simulazione */
	private boolean pauseSimulation;
	
	/** Flag per stabilire se interrompere la simulazione per un dato lasso di tempo */
	private boolean pauseSimulationUntil;
	/** Stabilisce per quanto tempo interrompere la simulazione (valido solo se {@link GameLoop#pauseSimulationUntil} == true)*/
	private long pauseUntil;

	/** Flag per statbilire se saltare l'aggiornamento dello stato del player */
	private boolean skipPlayerUpdateUntil;
	/** Stabilisce per quanto tempo saltare l'aggiornamente dello stato del player (valido solo se {@link GameLoop#skipPlayerUpdateUntil} == true) */
	private long skipUntil;
	
	/** Flag per stabilire se la partita deve terminare */
	private boolean gameWillEnd;
	/** Stabilisce per quanto tempo continuare prima di terminare la partita  */
	private long continueUntil;
	
	/** Stabilisce per quanto tempo disattivare i robot*/
	private long disableRobotsFor;
	
	/**
	 * Costruice la classe e ne registra gli event handler
	 * @param context
	 * il contesto di gioco
	 * @param renderer
	 * il pannello di gioco
	 */
	public GameLoop(GameContext context, Renderer renderer)
	{ 
		this.renderer = renderer;
		this.context = context; 
		pauseSimulationUntil = gameWillEnd = skipPlayerUpdateUntil = pauseSimulation = false;
		
		EventDispatcher.subscribe(PlayerDied.class,             x -> skipPlayerUpdate(((PlayerDied)x).nanos()));
		EventDispatcher.subscribe(StopSimulation.class,		    x -> pauseSimulation(((StopSimulation)x).nanos()));
		EventDispatcher.subscribe(RobotDisableRequested.class,  x -> disableRobots());
		EventDispatcher.subscribe(GameWillEnd.class,    	    x -> setGameEnd(((GameWillEnd)x).nanos()));
		EventDispatcher.subscribe(TerminalOpened.class,		    x -> terminalOpened());
		EventDispatcher.subscribe(GameResumed.class,    	    x -> pauseSimulation = false);
		EventDispatcher.subscribe(PlatformResetRequested.class, x -> context.resetPlatforms());
		EventDispatcher.subscribe(PuzzleMenuOpened.class,       x -> puzzleMenuOpened());
	}
	
	/** Il codice del thread */
	@Override 
	public void run()
	{	
		long previousTime = System.nanoTime();
		
		Room previousRoom = null;
		List<GameObject> gameObjectList = null;
		List<PlatformCluster> platformClusterList = null;
		Player player = context.getPlayer();
			
		while(true)
		{
			if(gameWillEnd && System.nanoTime() > continueUntil)
				break;
			
			if(pauseSimulationUntil || pauseSimulation)
			{ 
				long currentTime = previousTime = System.nanoTime();
				pauseSimulationUntil = currentTime < pauseUntil;
			    try { Thread.sleep(10); } catch (Exception e) {}
			    continue; 
			}
			
			long currentTime = System.nanoTime();
			long deltaTimeNanos =  currentTime - previousTime;
			
			if(context.isRobotsDisabled() && (disableRobotsFor -= deltaTimeNanos) <= 0)
				context.enableRobots();
			
		    double deltaTimeSeconds = deltaTimeNanos / 1e9;
		    previousTime = currentTime;
		    
		    double dt = Math.min(deltaTimeSeconds, 0.005f);
		    
			GameContext.setDeltaTime(dt);
			renderer.getCurrentSpritesList().stream().filter(
					s -> (!context.isRobotsDisabled() && s instanceof AnimatedSprite) || s instanceof PlayerSprite
			).forEach(s -> ((AnimatedSprite)s).updateElapsedTime(dt));
			
			renderer.getPlayerSprite().updateElapsedTime(dt);
			
			Room currentRoom = context.getCurrentRoom();
			gameObjectList = currentRoom.getGameObjectList();
			
			if(previousRoom != currentRoom)
			{
				renderer.setCurrentSpritesList();
				platformClusterList = PlatformCluster.getPlatformClusters(currentRoom);
				previousRoom = currentRoom;
			}
			
			platformClusterList.forEach(c -> c.update(context));
			
			for(int i = gameObjectList.size() - 1; i >= 0; i--)
				gameObjectList.get(i).update(context);
				
			if(skipPlayerUpdateUntil)
				skipPlayerUpdateUntil = currentTime < skipUntil;
			else
				player.update(context);
			
			try { SwingUtilities.invokeAndWait(() -> renderer.repaint()); } catch(Exception e) {}
			
			try { Thread.sleep(1); } catch (Exception e) {}
		}

		try { SwingUtilities.invokeAndWait(() -> { EventDispatcher.notify(new StopGame()); }); } catch(Exception e) {}
		
		Leaderboard leaderboard = context.getLeaderboard();
		leaderboard.addEntry(new Leaderboard.Entry(player.getName(), player.getPoints()));
		leaderboard.store();		
	}	
	
	/**
	 * Salta gli aggiornamente dello stato del player
	 * @param nanos
	 * per quanto tempo saltare gli aggiornamenti
	 */
	private void skipPlayerUpdate(long nanos)
	{ skipPlayerUpdateUntil = true; skipUntil = System.nanoTime() + nanos; }
	
	/**
	 * Mette in pausa la simulazione
	 * @param nanos
	 * per quanto tempo mettere in pausa la simulazione
	 */
	private void pauseSimulation(long nanos)
	{ pauseSimulationUntil = true; pauseUntil = System.nanoTime() + nanos; }
	
	/** 
	 * Termina la partita dopo il lasso di tempo prestabilito 
	 * @param nanos 
	 * il tempo da aspettare 
	 */
	private void setGameEnd(long nanos)
	{ gameWillEnd = true; continueUntil = System.nanoTime() + nanos; }
	
	/** Interrompe la simulazione quando si apre un terminale */
	private void terminalOpened()
	{ pauseSimulation = true; EventDispatcher.notify(new TerminalMenuRequested(context.getPlayer())); }
	
	/** Interrompe la simulazione quando si apre il menù per la composizione dei puzzle */
	private void puzzleMenuOpened()
	{ pauseSimulation = true; EventDispatcher.notify(new PuzzleMenuRequested(context.getPlayer())); }
	
	/** Disattiva i robot */
	private void disableRobots()
	{ context.disableRobots(); disableRobotsFor = Enemy.ROBOT_DISABLE_NANOS; }
}
