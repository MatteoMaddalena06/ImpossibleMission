package code.view.sprites;

//graphics import
import java.awt.image.BufferedImage;

import code.model.gameobjects.GameObject;

/** Classe che modella la sprite */
public abstract class Sprite 
{
	/** Immagine della sprite */
	private BufferedImage image;
	/** Gameobject associato alla sprite */
	private GameObject gameObject;
	
	/**
	 * Costruice la classe
	 * @param gameObject
	 * gameobject associato alla sprite
	 */
	public Sprite(GameObject gameObject)
	{ this.gameObject = gameObject; }
	
	/** Stabilisce come calcolare l'immagine della sprite */
	public abstract void computeImage();
	
	/**
	 * Restituisce l'immagine della sprite 
	 * @return
	 * l'immagine della sprite 
	 */
	public BufferedImage getImage()
	{ return image; }
	
	/** 
	 * Imposta l'immagine della sprite 
	 * @param image
	 * l'immagine
	 */
	protected void setImage(BufferedImage image)
	{ this.image = image; }
	
	/**
	 * Restituisce il gameobject associato
	 * @return
	 * il gameobject associato
	 */
	public GameObject getGameObject()
	{ return gameObject; }
}
