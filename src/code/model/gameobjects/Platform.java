package code.model.gameobjects;

import code.model.Point;
import code.model.context.GameContext;

/** Classe che modella le piattaforme del gioco */
public class Platform extends MovingObject
{
	/** posizione originale della piattaforma */
	private Point originalPosition;
	
	/**
	 * Costruisce la classe
	 * @param position
	 * posizione di partenza 
	 * @param width
	 * larghezza
	 * @param height
	 * altezza
	 */
	public Platform(Point position, int width, int height)
	{ 
		super(position, width, height);
		originalPosition = new Point(position);
	}

	/**
	 * Aggiorna lo stato della piattaforma controllando se è necessario ripristinare la sua posizione
	 * @param context
	 * il consteso su cui operare
	 */
	@Override
	public void update(GameContext context)
	{
		if(context.getPlatformsToReset() == 0)
			return;

		setPosition(new Point(originalPosition));
		context.resetOnePlatform();		
	}
}
