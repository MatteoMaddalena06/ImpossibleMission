package code.model.gameobjects;

import code.model.Point;
import code.model.context.GameContext;

/** Modella gli oggetti del gioco */
public abstract class GameObject
{	
	/** Posizione nella stanza del gameobject */
	private Point position;
	/** Larghezza del gameobject */
	private int width;
	/** Altezza del gameobject */
	private int height;
	
	/**
	 * Costruice la classe
	 * @param position
	 * la posizione di partenza del gameobject
	 * @param width
	 * la sua larghezza
	 * @param height
	 * la sua lunghezza
	 */
	public GameObject(Point position, int width, int height)
	{	
		this.position = position;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Controlla se due gameobject collidono
	 * @param other
	 * l'altro gameobject
	 * @return
	 * true se e solo se c'è una collisione
	 */
	public boolean isColliding(GameObject other)
	{		
		double x1 = position.getX(), y1 = position.getY();
		int w1 = width, h1 = height;
		
		double x2 = other.position.getX(), y2 = other.position.getY();
		int w2 = other.width, h2 = other.height;
		
		boolean firstCheck  = x1 < x2 + w2 && y1 < y2 + h2;
		boolean secondCheck = x2 < x1 + w1 && y2 < y1 + h1;
		
		return firstCheck && secondCheck;
	}
	
	/**
	 * Controlla se il gameobject contiene un dato punto
	 * @param point
	 * il punto
	 * @return
	 * true se e solo se il gameobject contiene il punto
	 */
	public boolean containsPoint(Point point)
	{ 
		double fx = getPosition().getX(), fy = getPosition().getY();
		int fw = getWidth(), fh = getHeight();
		
		double px = point.getX(), py = point.getY();
		
		return px > fx && px < fx + fw && py > fy && py < fy + fh;
	}

	/** 
	 * Aggiorna lo stato del gameobject
	 * @param context
	 * il contesto di gioco da considerare
	 */
	public abstract void update(GameContext context);
	
	/**
	 * Restituisce la posizione corrente del gameobject 
	 * @return
	 * la posizione
	 */
	protected Point getPosition()
	{ return position; }
	
	/**
	 * Imposta la posizione corrente del gameobject
	 * @param position
	 * la posizione
	 */
	protected void setPosition(Point position)
	{ this.position = position; }
	
	/** 
	 * Restituisce una copia della posizione corrente del gameobject
	 * @return
	 * copia della posizione corrente del gameobject
	 */
	public Point copyPosition()
	{ return new Point(position); }
	
	/**
	 * Restituisce la larghezza del gameobject
	 * @return
	 * la larghezza del gameobject
	 */
	public int getWidth()
	{ return width; }
	
	/**
	 * Restituisce l'altezza del gameobject
	 * @return
	 * l'altezza del gameobject
	 */
	public int getHeight()
	{ return height; }
	
	/**
	 * Imposta la larghezza del gaemobject
	 * @param width
	 * la larghezza
	 */
	protected void setWidth(int width)
	{ this.width = width; }
	
	/**
	 * Imposta l'altezza del gaemobject
	 * @param width
	 * l'altezza
	 */
	protected void setHeight(int height)
	{ this.height = height; }
}
