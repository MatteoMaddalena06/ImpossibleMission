package code.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import code.model.gameobjects.FixedObject;
import code.model.gameobjects.Furniture;
import code.model.gameobjects.GameObject;
import code.model.gameobjects.MovingObject;
import code.model.gameobjects.PlatformCluster;
import code.model.gameobjects.enemy.Enemy;
import code.model.gameobjects.enemy.JumperRobot;
import code.model.gameobjects.enemy.LaserRobot;
import code.model.gameobjects.enemy.RunnerRobot;
import code.model.room.Room;
import code.model.room.RoomMap;
import code.model.utils.GameContext;
import code.view.sprites.AnimatedSprite;
import code.view.sprites.Sprite;
import code.view.sprites.SpriteFactory;

public class GameLoop implements Runnable 
{
    private static final double FPS = 60.0;
    private static final double FRAME_TIME = 1.0 / FPS;

    private GameContext context;
    private Renderer renderer;
    
    public static Enemy robot;

    public GameLoop(GameContext context, Renderer renderer)
    {
        this.context = context;
        this.renderer = renderer;
    }

    @Override
    public void run()
    {
        double lastTime = System.nanoTime() / 1e9;
       
    	context.enableRobots();
    	
    	Room prevRoom = null;
    	List<PlatformCluster> platformClusters = null;
    	
        while (true)
        {
            double now = System.nanoTime() / 1e9;
            double delta = now - lastTime;
            lastTime = now;
            
            System.out.println("FPS: " + 1/delta);
            
            GameContext.setDeltaTime(delta);
            
            renderer.sprites.stream().filter(s -> s instanceof AnimatedSprite).forEach(s -> ((AnimatedSprite)s).updateElapsedTime(delta));

            robot = context.getCurrentRoom().getEnemiesList().get(0);
            
            if(context.getCurrentRoom() != prevRoom)
            {
            	platformClusters = PlatformCluster.getPlatformClusters(context.getCurrentRoom());
            	prevRoom = context.getCurrentRoom();
            }
            
            for(int i = context.getCurrentRoom().getGameObjectList().size() - 1; i >= 0; i--)
            	context.getCurrentRoom().getGameObjectList().get(i).update(context);
	 
        	platformClusters.forEach(c -> c.update(context));
        	
            context.getPlayer().update(context);
            
            // render
            SwingUtilities.invokeLater(() -> renderer.repaint());
            
            try { Thread.sleep(1); } catch (Exception e) {}
        }
    }
}
