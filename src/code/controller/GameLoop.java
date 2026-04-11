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
import code.view.sprites.AnimatedSprite;
//controller import
import code.controller.event.StopGame;
import code.controller.event.TerminalMenuRequested;
import code.controller.event.GameResumed;
//event import
import code.event.EventDispatcher;

public class GameLoop extends Thread
{
	private Renderer renderer;
	private GameContext context;
	
	private boolean pauseSimulation;
	
	private boolean pauseSimulationUntil;
	private long pauseUntil;

	private boolean skipPlayerUpdateUntil;
	private long skipUntil;
	
	private boolean gameWillEnd;
	private long continueUntil;
	
	private long disableRobotsFor;
	
	public GameLoop(GameContext context, Renderer renderer)
	{ 
		this.renderer = renderer;
		this.context = context; 
		pauseSimulationUntil = gameWillEnd = skipPlayerUpdateUntil = pauseSimulation = false;
		
		EventDispatcher.subscribe(PlayerDied.class,            x -> skipPlayerUpdate(((PlayerDied)x).nanos()));
		EventDispatcher.subscribe(StopSimulation.class,		   x -> pauseSimulation(((StopSimulation)x).nanos()));
		EventDispatcher.subscribe(RobotDisableRequested.class, x -> disableRobots());
		EventDispatcher.subscribe(GameWillEnd.class,    	   x -> setGameEnd(((GameWillEnd)x).nanos()));
		EventDispatcher.subscribe(TerminalOpened.class,		   x -> terminalOpened());
		EventDispatcher.subscribe(GameResumed.class,    	   x -> pauseSimulation = false);
	}
	
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
			renderer.getCurrentSpritesList().stream().filter(s -> s instanceof AnimatedSprite).forEach(s -> ((AnimatedSprite)s).updateElapsedTime(dt));
			
			Room currentRoom = context.getCurrentRoom();
			gameObjectList = currentRoom.getGameObjectList();
			
			if(previousRoom != currentRoom)
			{
				renderer.setCurrentSpritesList();
				platformClusterList = PlatformCluster.getPlatformClusters(currentRoom);
				previousRoom = currentRoom;
			}
			
			for(int i = gameObjectList.size() - 1; i >= 0; i--)
				gameObjectList.get(i).update(context);
			
			platformClusterList.forEach(c -> c.update(context));
			
			if(gameWillEnd && currentTime > continueUntil)
				break;
			
			if(skipPlayerUpdateUntil)
				skipPlayerUpdateUntil = currentTime < skipUntil;
			else
				player.update(context);
			
			SwingUtilities.invokeLater(() -> renderer.repaint());
			
			try { Thread.sleep(1); } catch (Exception e) {}
		}

		SwingUtilities.invokeLater(() -> { EventDispatcher.notify(new StopGame()); });
		
		Leaderboard leaderboard = context.getLeaderboard();
		leaderboard.addEntry(new Leaderboard.Entry(player.getName(), player.getPoints()));
		leaderboard.store();		
	}	
	
	private void skipPlayerUpdate(long nanos)
	{ skipPlayerUpdateUntil = true; skipUntil = System.nanoTime() + nanos; }
	
	private void pauseSimulation(long nanos)
	{ pauseSimulationUntil = true; pauseUntil = System.nanoTime() + nanos; }
	
	private void setGameEnd(long nanos)
	{ gameWillEnd = true; continueUntil = System.nanoTime() + nanos; }
	
	private void terminalOpened()
	{ pauseSimulation = true; EventDispatcher.notify(new TerminalMenuRequested(context.getPlayer())); }
	
	private void disableRobots()
	{ context.disableRobots(); disableRobotsFor = Enemy.ROBOT_DISABLE_NANOS; }
}
