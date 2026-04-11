package code.model.gameobjects.enemy;

//data structure module
import java.util.List;

import code.model.Point;
import code.model.context.GameContext;
import code.model.gameobjects.FixedObject;
import code.model.gameobjects.GameObject;
import code.model.gameobjects.MovingObject;

public abstract class Enemy extends MovingObject 
{ 
	private static final double SHARED_HORIZONTAL_SPEED = 150f;
	
	private FieldOfView fieldOfView;
	private double randomDelay;
	
	private enum RandomHorizontalMovement
	{ LEFT, RIGHT, IDLE }

	public class FieldOfView extends GameObject
	{	
		public FieldOfView(Point position, int width, int height)
		{ super(position, width, height);} 
		
		public void setX(double x) { getPosition().setX(x); }
		public void setY(double y) { getPosition().setY(y); }
		
		@Override
		public void update(GameContext context) {}	
	}
	
	public Enemy(Point position, int width, int height)
	{ 
		super(position, width, height);
		randomDelay = 0;
	}
	
	protected void applyGroundMovement(GameContext context, double horizontalSpeed)
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
	
	public boolean isOnLedge(List<FixedObject> fixedObjectList)
	{
		Point thisPosition = getPosition();
		double footX = thisPosition.getX() + ((getHorizontalVelocity() > 0) ? getWidth() : -1);
		double footY = thisPosition.getY() + getHeight() + 1;
		Point footPosition = new Point(footX, footY);
		return !fixedObjectList.stream().filter(f -> f.getType() == FixedObject.Type.FLOOR).anyMatch(f -> f.containsPoint(footPosition));
	}
	
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
	
	protected void setFov(FieldOfView fov)
	{ fieldOfView = fov; }
	
	protected FieldOfView getFov()
	{ return fieldOfView; }
	
	/* to delete */
	public FieldOfView getFOV()
	{ return fieldOfView; }
}
