package code.model.gameobjects.enemy;

//data structure module
import java.util.List;
//model import
import code.model.Point;
import code.model.context.GameContext;
import code.model.gameobjects.FixedObject;
import code.model.gameobjects.GameObject;
import code.model.gameobjects.MovingObject;

/** Classe che modella i nemici */
public abstract class Enemy extends MovingObject 
{ 
	/** Per quanto tempo i robot vanno disabilitati */
	public static final long ROBOT_DISABLE_NANOS = 10000000000L;
	/** Velocità orizzontale condivisa fra i robot */
	private static final double SHARED_HORIZONTAL_SPEED = 150f;
	
	/** FOV del robot */
	private FieldOfView fieldOfView;
	/** ritardo casuale per il movimento */
	private double randomDelay;
	
	/** Enumerazione per i movimenti concessi a un robot */
	private enum RandomHorizontalMovement
	{ LEFT, RIGHT, IDLE }

	/** Classe per la FOV dei robot */
	public class FieldOfView extends GameObject
	{	
		/**
		 * Costruisce la classe
		 * @param position
		 * la posizione originale
		 * @param width
		 * la larghezza
		 * @param height
		 * l'altezza
		 */
		public FieldOfView(Point position, int width, int height)
		{ super(position, width, height);} 
		
		/**
		 * Imposta la cooridnata x della FOV
		 * @param x
		 * la cooridnata x
		 */
		public void setX(double x) { getPosition().setX(x); }
		/**
		 * Imposta la cooridnata y della FOV
		 * @param x
		 * la cooridnata y
		 */
		public void setY(double y) { getPosition().setY(y); }
		
		/** Aggiorna lo stato della FOV 
		 * @param context
		 * il contesto in cui operare
		 */
		@Override
		public void update(GameContext context) {}	
	}
	
	/**
	 * Costruisce la classe
	 * @param position
	 * la posizione originale
	 * @param width
	 * la larghezza
	 * @param height
	 * l'altezza
	 */
	public Enemy(Point position, int width, int height)
	{ 
		super(position, width, height);
		randomDelay = 0;
	}
	
	/**
	 * Muove orizzontalmente il robot 
	 * @param context
	 * il contesto in cui operare
	 */
	protected void applyGroundMovement(GameContext context)
	{
		setRandomHorizontalVelocity();
		
		List<FixedObject> fixedObjects = context.getCurrentRoom().getFixedObjectList();
		List<GameObject> interestingGameObjects = fixedObjects.stream().map(f -> (GameObject)f).toList();
		
		if(isOnLedge(fixedObjects))
			setHorizontalVelocity(0);
		
		addGravity();
		
		applyHorizontalForce();
		resolveHorizontalCollision(interestingGameObjects);
		
		applyVerticalForce();
		resolveVerticalCollision(interestingGameObjects);
		
		double currentHorizontalVelocity = getHorizontalVelocity();
		
		if(currentHorizontalVelocity != 0)
		{
			setPhysicsState(MovingObject.PhysicsState.WALKING);
			setDirection((currentHorizontalVelocity > 0) ? MovingObject.Direction.RIGHT : MovingObject.Direction.LEFT);
		}
		else 
			setPhysicsState(MovingObject.PhysicsState.IDLE);
	}
	
	/**
	 * Controlla se il robot se sul bordo di un pavimento
	 * @param fixedObjectList
	 * la lista dei pavimenti nella mappa
	 * @return
	 * true se e solo se il nemico è sul bordo
	 */
	public boolean isOnLedge(List<FixedObject> fixedObjectList)
	{
		Point thisPosition = getPosition();
		double footX = thisPosition.getX() + ((getHorizontalVelocity() > 0) ? getWidth() : -1);
		double footY = thisPosition.getY() + getHeight() + 1;
		Point footPosition = new Point(footX, footY);
		return !fixedObjectList.stream().filter(f -> f.getType() == FixedObject.Type.FLOOR).anyMatch(f -> f.containsPoint(footPosition));
	}
	
	/** Imposta la velocità orizzontale del robot generandola casualmente */
	private void setRandomHorizontalVelocity()
	{
		int randomMovementNumber = RandomHorizontalMovement.values().length;
		RandomHorizontalMovement randomMovement = RandomHorizontalMovement.values()[(int)(Math.random() * randomMovementNumber)];
		
		if((randomDelay -= GameContext.getDeltaTime()) > 0)
			return;
		
		double randomAdjust = (Math.random() * .5f) + .5f;
		
		setHorizontalVelocity(switch(randomMovement) {
			case RandomHorizontalMovement.RIGHT -> randomAdjust * SHARED_HORIZONTAL_SPEED;
			case RandomHorizontalMovement.LEFT  -> randomAdjust * -SHARED_HORIZONTAL_SPEED;
			case RandomHorizontalMovement.IDLE  -> 0; 
		});
		
		randomDelay = (int)(Math.random() * 3);
	}
	
	/**
	 * Impost la FOV
	 * @param fov
	 * la FOV
	 */
	protected void setFov(FieldOfView fov)
	{ fieldOfView = fov; }
	
	/**
	 * Restituisce la FOV
	 * @return
	 * la FOV
	 */
	protected FieldOfView getFov()
	{ return fieldOfView; }
}
