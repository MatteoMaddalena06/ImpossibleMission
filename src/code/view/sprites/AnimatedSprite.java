package code.view.sprites;

//data structures import
import java.util.List;
//graphics import
import java.awt.image.BufferedImage;
//model import
import code.model.gameobjects.MovingObject;
//view import
import code.view.images.Animation;

/** Classe che modella la sprite animata */
public abstract class AnimatedSprite extends Sprite 
{
	/** L'animazione della sprite */
	private Animation animation;
	/** Lo stato precendete dell'animazione */
	private Animation.State previousState;
	/** Durata dei frame dell'animazione */
	private double imageDuration;
	/** Tempo trascorso dall'ultimo aggiornamento dell'animazione */
	private double elapsedTime;
	/** Indice del frame nell'animazione corrente */
	private int imageIndex;
 
	/**
	 * Costruisce la classe
	 * @param gameObject
	 * il gameobject associato alla sprite
	 * @param animation
	 * l'animazione della sprite
	 * @param imageDuration
	 * la durata dei frame dell'animazione
	 */
	public AnimatedSprite(MovingObject gameObject, Animation animation, double imageDuration)
	{
		super(gameObject);
		this.animation = animation;
		this.imageDuration = imageDuration;
	}
 
	/** 
	 * Calcola l'immagine corrente della sprite seguendo la sua animazione e la imposta con {@link setImage}
	 * @see setImage
	 */
	@Override 
	public void computeImage()
	{
		MovingObject bindedMovingObject = (MovingObject)getGameObject();
		Animation.State currentState = Animation.State.getState(bindedMovingObject);
		 
		if(previousState != currentState && !currentState.isMirrored(previousState))
			imageIndex = 0;
	 
		previousState = currentState;
		 
		List<BufferedImage> animationList = animation.getAnimationLists().get(currentState);
		 
		while(elapsedTime >= imageDuration)
		{
			elapsedTime -= imageDuration;
			imageIndex = nextImageIndex(animationList);
		}
		 
		setImage(animationList.get(imageIndex));
	}
 
	/** 
	 * Stabilisce il prossimo indice del frame nell'animazione corrente
	 * @param animationList
	 * la lista dei frame
	 * @return
	 * l'indice stabilito
	 */
	protected int nextImageIndex(List<BufferedImage> animationList)
	{ return (imageIndex + 1) % animationList.size(); }

	/** 
	 * Aggiorna il tempo trascorso dall'ultimo aggiornamento dell'animazione
	 * @param deltaTime
	 * il delta time
	 */
	public void updateElapsedTime(double deltaTime)
	{ elapsedTime += deltaTime; }
 
	/**
	 * Restituisce l'indice del frame corrente nell'animazione corrente
	 * @return
	 * l'indice del frame corrente
	 */
	protected int getImageIndex()
	{ return imageIndex; }
}
