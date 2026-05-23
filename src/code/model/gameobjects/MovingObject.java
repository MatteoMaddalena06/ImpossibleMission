package code.model.gameobjects;

//data structure modules
import java.util.List;
//model import
import code.model.Point;
import code.model.context.GameContext;

/** Classe che modella tutti gli oggetti del gioco in movimento */
public abstract class MovingObject extends GameObject
{
	/** Costante gravitazionale */
	private static final double GRAVITY = 1000f; 
	
	/** velocità orizzontale del gameobject*/
	private transient double horizontalVelocity;
	/** velocità verticale del gameobject */
	private transient double verticalVelocity;
	
	/** indica se il gameobject è a terra */
	private boolean onGround;
	
	/** Lo stato fisico del gameobject */
	private PhysicsState physicsState;
	/** La direzione in cui si sta muovendo il gameobject */
	private Direction direction;
	
	/** Enumerazione per gli stati fisici consentiti ai {@link MovingObject} */
	public enum PhysicsState 
	{ IDLE, WALKING, JUMPING, FALLING }
	
	/** Enumerazione per le direzione consentite ai {@link MovingObject} */
	public enum Direction 
	{ LEFT, RIGHT }
	
	/**
	 * Costruice la classe
	 * @param position
	 * la posizioen di partenza 
	 * @param width
	 * la larghezza
	 * @param height
	 * l'altezza
	 */
	public MovingObject(Point position, int width, int height)
	{
		super(position, width, height);
		horizontalVelocity = verticalVelocity = 0;
		onGround = true;
		physicsState = PhysicsState.IDLE;
		direction = Direction.RIGHT;
	}
	
	/** Applica la gravità */
	protected void addGravity()
	{ verticalVelocity += GRAVITY * GameContext.getDeltaTime(); }
	
	/** Applica la forza orizzontale */
	protected void applyHorizontalForce()
	{ 
		Point thisPosition = getPosition();
		thisPosition.setX(thisPosition.getX() + horizontalVelocity * GameContext.getDeltaTime()); 
	}
	
	/** Applica la forza verticale */
	protected void applyVerticalForce()
	{ 
		Point thisPosition = getPosition();
		thisPosition.setY(thisPosition.getY() + verticalVelocity * GameContext.getDeltaTime()); 
	}
	
	/** 
	 * Risolve le collisione orizzontali
	 * @param gameObjectList
	 * la lista di gameobject nella stanza
	 */
	protected void resolveHorizontalCollision(List<GameObject> gameObjectList)
	{
		Point thisPosition = getPosition();
	    double correctionX = thisPosition.getX(), minDistance = Integer.MAX_VALUE;
	    GameObject nearest = null;

	    for(GameObject fixed : gameObjectList) 
	    {
	        if(!isColliding(fixed))
	        	continue;

	        double fixedX = fixed.copyPosition().getX();
	        double newX = (horizontalVelocity > 0) ? fixedX - getWidth() : fixedX + fixed.getWidth();
	        double distance = Math.abs(newX - thisPosition.getX());

	        if(distance < minDistance) 
	        {
	            minDistance = distance;
	            nearest = fixed;
	            correctionX = newX;
	        }
	    }

	    if(nearest != null) 
	    { thisPosition.setX(correctionX); horizontalVelocity = 0; }
	}
	
	/**
	 * Risolve le colliszioni verticali
	 * @param gameObjectList
	 * la lista di gameobject nella stanza
	 */
	protected void resolveVerticalCollision(List<GameObject> gameObjectList)
	{
		Point thisPosition = getPosition();
	    double correctionY = thisPosition.getY(), minDistance = Integer.MAX_VALUE;
		GameObject nearest = null;

	    for(GameObject fixed : gameObjectList)
	    {
	        if(!isColliding(fixed)) 
	        	continue;

	        double fixedY = fixed.copyPosition().getY();
	        double newY = (verticalVelocity > 0) ? fixedY - getHeight() : fixedY + fixed.getHeight();
	        double distance = Math.abs(newY - thisPosition.getY());

	        if(distance < minDistance) 
	        {
	            minDistance = distance;
	            nearest = fixed;
	            correctionY = newY;
	        }
	    }

	    if(nearest != null) 
	    {
	        thisPosition.setY(correctionY);
	        if(verticalVelocity > 0) onGround = true;	
	        verticalVelocity = 0;
	        return;
	    }
	    
		onGround = false;
	}

	/**
	 * Restituisce la velocità orizzontale
	 * @return
	 * la velocità orizzontale
	 */
	protected double getHorizontalVelocity()
	{ return horizontalVelocity; }
	
	/**
	 * Restituisce la velocità verticale
	 * @return
	 * la velocità orizzontale
	 */
	protected double getVerticalVelocity()
	{ return verticalVelocity; }
	
	/**
	 * Imposta la velocità orizzontale
	 * @param velocity
	 * la velocità orizzontale
	 */
	protected void setHorizontalVelocity(double velocity)
	{ horizontalVelocity = velocity; }
	
	/**
	 * Imposta la velocità verticale
	 * @param velocity
	 * la velocità verticale
	 */
	protected void setVerticalVelocity(double velocity)
	{ verticalVelocity = velocity; }
	
	/** 
	 * Dice se il gameobject è a terra
	 * @return
	 * lo stato del flag {@link onGround}
	 */
	public boolean isOnGround()
	{ return onGround; }
	
	/**
	 * Imposta lo stato fisico del gameobject
	 * @param physicsState
	 * lo stato fisico
	 */
	protected void setPhysicsState(PhysicsState physicsState)
	{ this.physicsState = physicsState; }
	
	/**
	 * Restituisce lo stato fisico del gameobject
	 * @return
	 * lo stato fisico del gameobject
	 */
	public PhysicsState getPhysicsState()
	{ return physicsState; }
	
	/**
	 * Imposta la direzione del gameobject
	 * @param direction
	 * la direzione del gameobject
	 */
	protected void setDirection(Direction direction)
	{ this.direction = direction; }
	
	/**
	 * Restituisce la direzione del gameobject
	 * @return
	 * la direzione del gameobject
	 */
	public Direction getDirection()
	{ return direction; }
}
