package code.controller;

//data structures import
import java.util.List;
//graphics import
import javax.swing.SwingUtilities;
//model import
import code.model.context.GameContext;
import code.model.context.GameState;
import code.model.gameobjects.GameObject;
import code.model.gameobjects.PlatformCluster;
import code.model.gameobjects.Player;
import code.model.room.Room;
//view import
import code.view.Renderer;
import code.view.sprites.AnimatedSprite;
//model state import
import code.model.context.StopSimulation;

public class GameLoop extends Thread implements GameContext.StateListener
{
	private Renderer renderer;
	private GameContext context;
	
	private boolean pauseSimulation;
	private long pauseUntil;
	
	public GameLoop(GameContext context, Renderer renderer)
	{ 
		this.renderer = renderer;
		this.context = context; 
		pauseSimulation = false;
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
			player.update(context);
			
			SwingUtilities.invokeLater(() -> renderer.repaint());
			
			try { Thread.sleep(1); } catch (Exception e) {}
		}
	}

	@Override
	public void notifyState(GameState state)
	{ 
		if(state instanceof StopSimulation)
			pauseSimulation = true; pauseUntil = System.nanoTime() + ((StopSimulation)state).nanos();
	}	
}
