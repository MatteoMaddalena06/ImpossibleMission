package code.controller;

//data structures import
import java.util.List;
//graphics import
import javax.swing.SwingUtilities;
//model import
import code.model.context.GameContext;
import code.model.gameobjects.GameObject;
import code.model.gameobjects.PlatformCluster;
import code.model.gameobjects.Player;
import code.model.room.Room;
//view import
import code.view.Renderer;
import code.view.sprites.AnimatedSprite;

public class GameLoop extends Thread
{
	private Renderer renderer;
	private GameContext context;
	
	public GameLoop(GameContext context, Renderer renderer)
	{ 
		this.renderer = renderer;
		this.context = context; 
	}
	
	@Override 
	public void run()
	{	
		long previousTime, currentTime;
		
		previousTime = currentTime = System.nanoTime();
		
		Room previousRoom = null;
		List<GameObject> gameObjectList = null;
		List<PlatformCluster> platformClusterList = null;
		Player player = context.getPlayer();
			
		while(true)
		{
			double deltaTime = (currentTime - previousTime) / 1.e9f;

			GameContext.setDeltaTime(deltaTime);
			renderer.getCurrentSpritesList().stream().filter(s -> s instanceof AnimatedSprite).forEach(s -> ((AnimatedSprite)s).updateElapsedTime(deltaTime));
			
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
			
			previousTime = currentTime;
			currentTime = System.nanoTime();
		}
	}
}
