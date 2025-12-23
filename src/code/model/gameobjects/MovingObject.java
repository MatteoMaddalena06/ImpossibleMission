package code.model.gameobjects;

//data structure modules
import java.util.List;

import code.model.utils.GameContext;
import code.model.utils.Point;

public abstract class MovingObject extends GameObject
{
	private static final double GRAVITY = 1200f; 
	protected static final int  STANDING_TOLLERANCE = 2; 
	
	private transient double horizontalVelocity;
	private transient double verticalVelocity;
	
	private transient boolean onGround;
	
	private PhysicsState physicsState;
	private Direction direction;
	
	public enum PhysicsState 
	{ IDLE, WALKING, JUMPING, FALLING }
	
	public enum Direction 
	{ LEFT, RIGHT }
	
	public MovingObject(Point position, int width, int height)
	{
		super(position, width, height);
		horizontalVelocity = verticalVelocity = 0;
		onGround = true;
	}
	
	protected void addGravity()
	{ verticalVelocity += GRAVITY * GameContext.getDeltaTime(); }
	
	protected void applyHorizontalForce()
	{ 
		Point thisPosition = getPosition();
		thisPosition.setX(thisPosition.getX() + horizontalVelocity * GameContext.getDeltaTime()); 
	}
	
	protected void applyVerticalForce()
	{ 
		Point thisPosition = getPosition();
		thisPosition.setY(thisPosition.getY() + verticalVelocity * GameContext.getDeltaTime()); 
	}
	
	protected void resolveHorizontalCollision(List<GameObject> gameObjectList)
	{
		Point thisPosition = getPosition();
	    double correctionX = thisPosition.getX(), minDistance = Integer.MAX_VALUE;
	    GameObject nearest = null;

	    for (GameObject fixed : gameObjectList) 
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
	
	protected void resolveVerticalCollision(List<GameObject> gameObjectList)
	{
		Point thisPosition = getPosition();
	    double correctionY = thisPosition.getY(), minDistance = Integer.MAX_VALUE;
		GameObject nearest = null;

	    for (GameObject fixed : gameObjectList)
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

	protected double getHorizontalVelocity()
	{ return horizontalVelocity; }
	
	protected double getVerticalVelocity()
	{ return verticalVelocity; }
	
	protected void setHorizontalVelocity(double velocity)
	{ horizontalVelocity = velocity; }
	
	protected void setVerticalVelocity(double velocity)
	{ verticalVelocity = velocity; }
	
	public boolean isOnGround()
	{ return onGround; }
	
	protected void setPhysicsState(PhysicsState physicsState)
	{ this.physicsState = physicsState; }
	
	public PhysicsState getPhysicsState()
	{ return physicsState; }
	
	protected void setDirection(Direction direction)
	{ this.direction = direction; }
	
	public Direction getDirection()
	{ return direction; }
}
