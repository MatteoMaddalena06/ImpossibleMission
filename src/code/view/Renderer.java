package code.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import javax.swing.*;

import code.model.gameobject.*;
import code.model.room.*;
import code.model.utils.GameContext;
import code.model.utils.Point;
import code.model.gameobject.enemy.BlackOrb;
import code.model.gameobject.enemy.Enemy;
import code.model.gameobject.enemy.Enemy;
import code.model.gameobject.enemy.RunnerRobot;

import java.util.List;

public class Renderer extends JPanel
{
	GameContext context;
	int width, height;
	
	public Renderer(GameContext context, int width, int height)
	{
		this.context = context;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public Dimension getPreferredSize() 
    { return new Dimension(width, height); }
	
	@Override
    protected void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        int w1 = getWidth();
        int h1 = getHeight();

        // Buffer dove disegnare la scena originale
        BufferedImage buffer = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_RGB);
        Graphics2D gBuf = buffer.createGraphics();
        gBuf.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        List<GameObject> gameObjectList = context.getCurrentRoom().getGameObjectList();
    	
    	Player player = context.getPlayer();
    	Point playerPosition = player.copyPosition();
    	int px = playerPosition.getX(), py = playerPosition.getY();
    	int pw = player.getWidth(), ph = player.getHeight();
    	
    	for(GameObject gameObject : gameObjectList)
    	{
    		if(gameObject instanceof FixedObject && ((FixedObject)gameObject).getType() == FixedObject.Type.FLOOR)
    			gBuf.setColor(Color.GREEN);
    		
    		else if(gameObject instanceof FixedObject && ((FixedObject)gameObject).getType() == FixedObject.Type.WALL)
    			gBuf.setColor(Color.BLUE);
    		
    		else if(gameObject instanceof Enemy && !(gameObject instanceof BlackOrb))
    		{
    			if(gameObject == GameLoop.robot)
    				gBuf.setColor(Color.PINK);
    			else
    				gBuf.setColor(Color.RED);
    			
    			GameObject fov = ((Enemy) gameObject).getFOV();
    			int x = fov.copyPosition().getX(), y = fov.copyPosition().getY();
    			int w = fov.getWidth(), h = fov.getHeight();
    			gBuf.drawRect(x, y, w, h);
    		}
    		else if(gameObject instanceof Furniture)
    			gBuf.setColor(Color.GRAY);

    		else if(gameObject instanceof Terminal)
    			gBuf.setColor(Color.ORANGE);
    		
    		else if(gameObject instanceof Platform)
    			gBuf.setColor(Color.ORANGE);
    		
    		else 
    			gBuf.setColor(Color.BLUE);
    		
    		Point gameObjectPosition = gameObject.copyPosition();
    		int x = gameObjectPosition.getX(), y = gameObjectPosition.getY();
    		int w = gameObject.getWidth(), h = gameObject.getHeight();
    		
    		gBuf.fillRect(x, y, w, h);
    	}
    	
    	gBuf.setColor(Color.PINK);
    	gBuf.fillRect(px, py, pw, ph);

    	
        // --- Leggero blur (simula glow dei fosfori) ---
        buffer = applyBlur(buffer);

        // --- Disegno del buffer sul pannello ---
        g2.drawImage(buffer, 0, 0, null);

        // --- Scanlines ---
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
        g2.setColor(Color.BLACK);
        for (int y = 0; y < h1; y += 2) {
            g2.drawLine(0, y, w1, y);
        }
      
        g2.dispose();
    }
	
	private BufferedImage applyBlur(BufferedImage img) {
        int radius = 2;
        int size = radius * 2 + 1;
        float[] data = new float[size * size];
        for (int i = 0; i < data.length; i++) data[i] = 1f / data.length;
        
        BufferedImage blurred = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        ConvolveOp op = new ConvolveOp(new Kernel(size, size, data));
        op.filter(img, blurred);
        return blurred;
    }
}
