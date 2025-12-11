package view;

import javax.swing.SwingUtilities;

import model.gameobject.MovingObject;
import model.gameobject.enemy.Enemy;
import model.gameobject.enemy.RunnerRobot;
import model.utils.GameContext;

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

        while (true)
        {
            double now = System.nanoTime() / 1e9;
            double delta = now - lastTime;
            lastTime = now;

            accumulator += delta;
            robot = context.getCurrentRoom().getEnemiesList().get(0);

            // aggiornamento logico fisso
            while (accumulator >= FRAME_TIME)
            {
                context.getPlayer().update(context);
                
                for(int i = context.getCurrentRoom().getGameObjectList().size() - 1; i >= 0; i--)
                {
                	context.getCurrentRoom().getGameObjectList().get(i).update(context);
                
                	if(robot == context.getCurrentRoom().getGameObjectList().get(i))
                		System.out.println(((RunnerRobot)robot).getState());
                }

                accumulator -= FRAME_TIME;
            }

            // render
            SwingUtilities.invokeLater(() -> renderer.repaint());

            // prevenire uso CPU al 100%
            try { Thread.sleep(1); } catch (Exception e) {}
        }
    }
}
