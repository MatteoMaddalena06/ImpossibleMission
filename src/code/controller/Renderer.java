package code.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import code.model.utils.GameContext;
import code.model.utils.Point;
import code.model.gameobjects.*;
import code.model.gameobjects.enemy.BlackOrb;
import code.model.gameobjects.enemy.Enemy;
import code.model.gameobjects.enemy.JumperRobot;
import code.model.gameobjects.enemy.ThrowerRobot;

import java.util.List;

import code.view.images.Animation;
import code.view.sprites.*;

public class Renderer extends JPanel
{
	GameContext context;
	int width, height;
	List<Sprite> sprites;
	
	private BufferedImage playerSprite;
	private BufferedImage playerSpriteScaled;
	private int maxW, maxH;

	
	public Renderer(GameContext context, int width, int height)
	{
	    this.context = context;
	    this.width = width;
	    this.height = height;
	    
        try {
			playerSprite = ImageIO.read(
			    getClass().getResourceAsStream(
			        "/resoruces/Player/Walking/Jot_0-38.png"
			    )
			);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        maxH = maxW = 0;
        playerSprite = cropTransparent(playerSprite);
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
        
        List<Sprite> otherSprite = sprites.stream().filter(s -> !(s instanceof AnimatedSprite)).toList();
        List<Sprite> animatedSprite = sprites.stream().filter(s -> s instanceof AnimatedSprite).toList();
        
        for(Sprite s : otherSprite)
        {
        	if(s == null)
        		continue;
        	
        	GameObject go = s.getGameObject();
        	Point goPosition = go.copyPosition();
        	int goX = (int)goPosition.getX(), goY = (int)goPosition.getY();
        	BufferedImage spriteImage = s.getImage();
        	int overflow = spriteImage.getHeight() - go.getHeight();
        	
        	gBuf.drawImage(spriteImage, goX, goY - overflow, null);
        }
        
        for(Sprite s : animatedSprite)
        {
        	if(s == null)
        		continue;
        	
        	GameObject go = s.getGameObject();
        	Point goPosition = go.copyPosition();
        	int goX = (int)goPosition.getX(), goY = (int)goPosition.getY();
        	s.computeImage();
        	BufferedImage spriteImage = s.getImage();
        	int overflow = spriteImage.getHeight() - go.getHeight();
        	
        	gBuf.drawImage(spriteImage, goX, goY - overflow, null);
        }
        
        for(GameObject a : context.getCurrentRoom().getGameObjectList())
        {
        	Point aP = a.copyPosition();
        	
        	if(context.getUserInput(GameContext.UserInput.H_KEY))
        		{gBuf.setColor(Color.red); gBuf.drawRect((int)aP.getX(), (int)aP.getY(), a.getWidth(), a.getHeight()); }
        }
        
        Player p = context.getPlayer();
        Point pP = p.copyPosition();
        
        if(context.getUserInput(GameContext.UserInput.H_KEY))
			{gBuf.setColor(Color.red); gBuf.drawRect((int)pP.getX(), (int)pP.getY(), p.getWidth(), p.getHeight()); }
        
        g2.drawImage(buffer, 0, 0, null);

        g2.dispose();
    }

	public static BufferedImage cropTransparent(BufferedImage src) {
	    int w = src.getWidth();
	    int h = src.getHeight();

	    int minX = w, minY = h, maxX = 0, maxY = 0;
	    boolean found = false;

	    for (int y = 0; y < h; y++) {
	        for (int x = 0; x < w; x++) {
	            int alpha = (src.getRGB(x, y) >> 24) & 0xff;
	            if (alpha > 0) {
	                found = true;
	                minX = Math.min(minX, x);
	                minY = Math.min(minY, y);
	                maxX = Math.max(maxX, x);
	                maxY = Math.max(maxY, y);
	            }
	        }
	    }

	    if (!found) return src; // tutta trasparente

	    return src.getSubimage(
	        minX,
	        minY,
	        maxX - minX + 1,
	        maxY - minY + 1
	    );
	}
	
	public void setSprites(List<Sprite> sprites)
	{ this.sprites = sprites; }
}
