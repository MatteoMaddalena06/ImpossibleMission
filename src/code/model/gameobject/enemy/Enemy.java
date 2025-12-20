package code.model.gameobject.enemy;

//data structure module
import java.util.List;

//inproject import
import code.model.gameobject.MovingObject;
import code.model.gameobject.Player;
import code.model.utils.GameContext;
import code.model.utils.Point;
import code.model.gameobject.FixedObject;
import code.model.gameobject.GameObject;

public abstract class Enemy extends MovingObject 
{ 
	private static final long serialVersionUID = 1L;
	
	private static final double SHARED_HORIZONTAL_SPEED = 150f;
	
	private FieldOfView fieldOfView;
	private Point previousPlayerPosition;
	private double actionDelay;
	
	private int randomDelay;
	
	private enum RandomHorizontalMovement
	{ LEFT, RIGHT, IDLE }

	protected class FieldOfView extends GameObject
	{	
		public FieldOfView(Point position, int width, int height)
		{ super(position, width, height);} 
		
		public void setX(int x) { getPosition().setX(x); }
		public void setY(int y) { getPosition().setY(y); }
		
		@Override
		public void update(GameContext context) {}	
	}
	
	public Enemy(Point position, int width, int height, double actionDelay)
	{ 
		super(position, width, height);
		this.actionDelay = actionDelay;
		randomDelay = 0;
	}
	
	protected void applyGroundMovement(GameContext context, double horizontalSpeed, double bound)
	{
		Player player = context.getPlayer();
		int thisX = getPosition().getX();
		Point currentPlayerPosition = player.copyPosition();
		
		if(fieldOfView.isColliding(player))
		{
			int targetX = getTargetPosition(currentPlayerPosition).getX();
			setHorizontalVelocity(0);
			
			if(Math.abs(thisX - targetX) >= bound) 
				setHorizontalVelocity((thisX > targetX) ? -horizontalSpeed : horizontalSpeed);
		}
		else
		{
			previousPlayerPosition = currentPlayerPosition;
			setRandomHorizontalVelocity();
		}
		
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
	
	protected Point getTargetPosition(Point currentPlayerPosition)
	{ 
		if(previousPlayerPosition == null)
			previousPlayerPosition = currentPlayerPosition;
		
		int targetX = (int)(previousPlayerPosition.getX() + (currentPlayerPosition.getX() - previousPlayerPosition.getX()) * actionDelay);
		int targetY = (int)(previousPlayerPosition.getY() + (currentPlayerPosition.getY() - previousPlayerPosition.getY()) * actionDelay);
		
		previousPlayerPosition.setX(targetX);	
		previousPlayerPosition.setY(targetY);
		return new Point(targetX, targetY);
	}
	
	private boolean isOnLedge(List<FixedObject> fixedObjectList)
	{
		Point thisPosition = getPosition();
		int footX = thisPosition.getX() + ((getHorizontalVelocity() > 0) ? getWidth() : -1);
		int footY = thisPosition.getY() + getHeight() + 1;
		Point footPosition = new Point(footX, footY);
		return !fixedObjectList.stream().filter(f -> f.getType() == FixedObject.Type.FLOOR).anyMatch(f -> f.containsPoint(footPosition));
	}
	
	private void setRandomHorizontalVelocity()
	{
		int randomMovementNumber = RandomHorizontalMovement.values().length;
		RandomHorizontalMovement randomMovement = RandomHorizontalMovement.values()[(int)(Math.random() * randomMovementNumber)];
		
		if(randomDelay-- > 0)
			return;
		
		double randomAdjust = (Math.random() * .5f) + .5f;
		
		setHorizontalVelocity(switch(randomMovement) {
			case RandomHorizontalMovement.RIGHT -> randomAdjust * SHARED_HORIZONTAL_SPEED;
			case RandomHorizontalMovement.LEFT  -> randomAdjust * -SHARED_HORIZONTAL_SPEED;
			case RandomHorizontalMovement.IDLE  -> 0; 
		});
		
		randomDelay = 60 + (int)(Math.random() * 60);
	}
	
	protected void setFov(FieldOfView fov)
	{ fieldOfView = fov; }
	
	protected void setActionDelay(double delay)
	{ actionDelay = delay; }
	
	protected FieldOfView getFov()
	{ return fieldOfView; }
	
	/* to delete */
	public FieldOfView getFOV()
	{ return fieldOfView; }
}
