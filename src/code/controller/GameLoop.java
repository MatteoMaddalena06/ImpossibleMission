package code.controller;

import java.util.List;

import javax.swing.SwingUtilities;

import code.model.gameobject.MovingObject;
import code.model.gameobject.PlatformCluster;
import code.model.gameobject.enemy.Enemy;
import code.model.gameobject.enemy.LaserRobot;
import code.model.gameobject.enemy.RunnerRobot;
import code.model.room.Room;
import code.model.utils.GameContext;

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
        double accumulator = 0.0;
        
        MovingObject.setDeltaTime(1/60f);
       
    	context.enableRobots();
    	
    	Room prevRoom = null;
    	List<PlatformCluster> platformClusters = null;
  	
        while (true)
        {
            double now = System.nanoTime() / 1e9;
            double delta = now - lastTime;
            lastTime = now;

            accumulator += delta;
            robot = context.getCurrentRoom().getEnemiesList().get(0);
            
            if(context.getCurrentRoom() != prevRoom)
            {
            	platformClusters = PlatformCluster.getPlatformClusters(context.getCurrentRoom());
            	prevRoom = context.getCurrentRoom();
            }

            // aggiornamento logico fisso
            while (accumulator >= FRAME_TIME)
            {
            	 for(int i = context.getCurrentRoom().getGameObjectList().size() - 1; i >= 0; i--)
                 {
                 	context.getCurrentRoom().getGameObjectList().get(i).update(context);
                 }
            	 
            	platformClusters.forEach(c -> c.update(context));
            	
                context.getPlayer().update(context);

                accumulator -= FRAME_TIME;
            }
            
            // render
            SwingUtilities.invokeLater(() -> renderer.repaint());

            // prevenire uso CPU al 100%
            try { Thread.sleep(1); } catch (Exception e) {}
        }
    }
}
