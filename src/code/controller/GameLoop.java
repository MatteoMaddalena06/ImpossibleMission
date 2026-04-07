package code.controller;

//data structures import
import java.util.List;
//graphics import
import javax.swing.SwingUtilities;
//model import
import code.model.context.GameContext;
import code.model.context.GameWillEnd;
import code.model.context.GameState;
import code.model.context.PlayerDied;
import code.model.gameobjects.GameObject;
import code.model.gameobjects.PlatformCluster;
import code.model.gameobjects.Player;
import code.model.room.Room;
import code.controller.event.GameLoopEvent;
import code.model.Leaderboard;
//view import
import code.view.Renderer;
import code.view.sprites.AnimatedSprite;
//model state import
import code.model.context.StopSimulation;
//controller import
import code.controller.event.StopGame;

public class GameLoop extends Thread implements GameContext.StateListener
{
	private Renderer renderer;
	private GameContext context;
	
	private volatile boolean pauseSimulation;
	private volatile long pauseUntil;

	private volatile boolean skipPlayerUpdate;
	private volatile long skipUntil;
	
	private volatile boolean gameWillEnd;
	private volatile long continueUntil;
	
	private GameLoopListener listener;
	
	public interface GameLoopListener
	{ public void notifyGameLoopEvent(GameLoopEvent event); }
	
	public GameLoop(GameContext context, Renderer renderer)
	{ 
		this.renderer = renderer;
		this.context = context; 
		pauseSimulation = gameWillEnd = skipPlayerUpdate = false;
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
			long currentTime = System.nanoTime();
			
			if(pauseSimulation)
			{ 
				pauseSimulation = currentTime < pauseUntil;
			    try { Thread.sleep(10); } catch (Exception e) {}
			    continue; 
			}
			
		    double deltaTime = (currentTime - previousTime) / 1e9;
		    previousTime = currentTime;
		    
		    double dt = Math.min(deltaTime, 0.005f);
		    
			GameContext.setDeltaTime(dt);
			renderer.getCurrentSpritesList().stream().filter(s -> s instanceof AnimatedSprite).forEach(s -> ((AnimatedSprite)s).updateElapsedTime(dt));
			
			Room currentRoom = context.getCurrentRoom();
			gameObjectList = currentRoom.getGameObjectList();
			
			if(previousRoom != currentRoom)
			{
				renderer.setCurrentSpritesList(player, currentRoom);
				platformClusterList = PlatformCluster.getPlatformClusters(currentRoom);
				previousRoom = currentRoom;
			}
			
			for(int i = gameObjectList.size() - 1; i >= 0; i--)
				gameObjectList.get(i).update(context);
			
			platformClusterList.forEach(c -> c.update(context));
			
			if(gameWillEnd && currentTime > continueUntil)
				break;
			
			if(skipPlayerUpdate)
				skipPlayerUpdate = currentTime < skipUntil;
			else
				player.update(context);
			
			SwingUtilities.invokeLater(() -> renderer.repaint());
			
			try { Thread.sleep(1); } catch (Exception e) {}
		}

		SwingUtilities.invokeLater(() -> { listener.notifyGameLoopEvent(new StopGame(renderer)); });
	}

	@Override
	public void notifyState(GameState state)
	{ 
		if(state instanceof StopSimulation)
		{ 
			pauseSimulation = true; 
			pauseUntil = System.nanoTime() + ((StopSimulation)state).nanos();
		}
		else if(state instanceof GameWillEnd)
		{
			Player player = context.getPlayer();
			Leaderboard leaderboard = context.getLeaderboard();
			leaderboard.addEntry(new Leaderboard.Entry(player.getName(), player.getPoints()));
			leaderboard.store();
			
			gameWillEnd = true;
			continueUntil =  System.nanoTime() + ((GameWillEnd)state).nanos();
		}
		else if(state instanceof PlayerDied)
		{
			skipPlayerUpdate = true;
			skipUntil = System.nanoTime() + ((PlayerDied)state).nanos();
		}
	}	
	
	public void setListener(GameLoopListener listener)
	{ this.listener = listener; }
}
