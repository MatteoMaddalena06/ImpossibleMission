package code.view.sprites;

//graphics import
import java.awt.image.BufferedImage;

import code.model.gameobjects.GameObject;

public abstract class Sprite 
{
	private BufferedImage image;
	private GameObject gameObject;
	
	public Sprite(GameObject gameObject)
	{ this.gameObject = gameObject; }
	
	public abstract void computeImage();
	
	public BufferedImage getImage()
	{ return image; }
	
	protected void setImage(BufferedImage image)
	{ this.image = image; }
	
	public GameObject getGameObject()
	{ return gameObject; }
}
