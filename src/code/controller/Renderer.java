package code.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import code.model.gameobject.*;
import code.model.utils.GameContext;
import code.model.utils.Point;
import code.model.gameobject.enemy.BlackOrb;
import code.model.gameobject.enemy.Enemy;

import java.util.List;

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
        
        for(Sprite s : sprites)
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
    	
    	for(GameObject gameObject : gameObjectList)
    	{
    		if(gameObject instanceof Platform || gameObject instanceof FixedObject)
    			continue;
    		
    		if(gameObject instanceof Enemy && !(gameObject instanceof BlackOrb))
    		{
    			if(gameObject == GameLoop.robot)
    				gBuf.setColor(Color.PINK);
    			else
    				gBuf.setColor(Color.RED);
    			
    			GameObject fov = ((Enemy) gameObject).getFOV();
    			int x = (int)fov.copyPosition().getX(), y = (int)fov.copyPosition().getY();
    			int w = fov.getWidth(), h = fov.getHeight();
    			gBuf.drawRect(x, y, w, h);
    		}
    		else if(gameObject instanceof Furniture)
    			gBuf.setColor(Color.BLUE);	
    		else 
    			gBuf.setColor(Color.BLUE);
    		
    		Point gameObjectPosition = gameObject.copyPosition();
    		int x = (int)gameObjectPosition.getX(), y = (int)gameObjectPosition.getY();
    		int w = gameObject.getWidth(), h = gameObject.getHeight();
    		
    		if(context.getUserInput(GameContext.UserInput.H_KEY))
    			gBuf.drawRect(x, y, w, h);
    	}
    	
    	// --- PLAYER SPRITE ---
    	Player player = context.getPlayer();
    	Point pos = player.copyPosition();

    	int px = (int)pos.getX();
    	int py = (int)pos.getY();
    	int pw = player.getWidth();
    	int ph = player.getHeight();
    	
    	maxW = (pw > maxW) ? px : maxW;
    	maxH = (ph > maxH) ? py : maxH;

    	// Scala la sprite UNA SOLA VOLTA se necessario
    	if (playerSpriteScaled == null ||
    	    playerSpriteScaled.getWidth() != maxW ||
    	    playerSpriteScaled.getHeight() != maxH)
    	{
    	    double scale = Math.max(
    	        maxW / (double) playerSprite.getWidth(),
    	        maxH / (double) playerSprite.getHeight()
    	    );

    	    int sw = (int) Math.round(playerSprite.getWidth() * scale);
    	    int sh = (int) Math.round(playerSprite.getHeight() * scale);

    	    playerSpriteScaled =
    	        new BufferedImage(sw, sh, BufferedImage.TYPE_INT_ARGB);

    	    Graphics2D gImg = playerSpriteScaled.createGraphics();
    	    gImg.setRenderingHint(
    	        RenderingHints.KEY_INTERPOLATION,
    	        RenderingHints.VALUE_INTERPOLATION_BILINEAR
    	    );
    	    gImg.drawImage(playerSprite, 0, 0, sw, sh, null);
    	    gImg.dispose();
    	}

    	// Centra la sprite nella hitbox
    	int drawX = px + (maxW - playerSpriteScaled.getWidth()) / 2;
    	int drawY = py + (maxH - playerSpriteScaled.getHeight());

    	// Disegno nel buffer
    	gBuf.drawImage(playerSpriteScaled, drawX, drawY, null);

    	gBuf.setColor(Color.PINK);
    	gBuf.drawRect(px, py, pw, ph);
        // --- Disegno del buffer sul pannello ---
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
