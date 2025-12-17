package model.gameobject.enemy;

//inproject import
import model.utils.Point;

import java.util.List;

import model.gameobject.FixedObject;
import model.gameobject.GameObject;
import model.gameobject.MovingObject;
import model.gameobject.Platform;
import model.utils.GameContext;

public class BlackOrb extends Enemy
{
	private static final double HORIZONTAL_SPEED = 100f;
	private static final double VERTICAL_SPEED   = 150f;
	private static final double ACTION_DELAY     = 0.03f;
	private static final double BOUND            = 3;
	
	public BlackOrb(Point position, int width, int height)
	{ super(position, width, height, ACTION_DELAY); }
	
	@Override
	public void update(GameContext context)
	{
		if(context.isRobotsDisabled())
			return;
		
		Point targetPosition = getTargetPosition(context.getPlayer().copyPosition());
		int targetX = targetPosition.getX(), targetY = targetPosition.getY();
		int thisX = getPosition().getX(), thisY = getPosition().getY();
		
		setHorizontalVelocity(0); setVerticalVelocity(0);
				
		if(Math.abs(thisX - targetX) >= BOUND) 
			setHorizontalVelocity((thisX > targetX) ? -HORIZONTAL_SPEED : HORIZONTAL_SPEED);
		
		if(Math.abs(thisY - targetY) >= BOUND)
			setVerticalVelocity((thisY > targetY) ? -VERTICAL_SPEED : VERTICAL_SPEED);
		
		List<GameObject> interestingGameObjects = 
				context.getCurrentRoom().getGameObjectList().stream().filter(g -> g instanceof Platform || g instanceof FixedObject).map(g -> (GameObject)g).toList();
		
		applyHorizontalForce();
		resolveHorizontalCollision(interestingGameObjects);
		
		applyVerticalForce();
		resolveVerticalCollision(interestingGameObjects);
		
		double currentHorizontalVelocity = getHorizontalVelocity();
		double currentVerticalVelocity = getVerticalVelocity();
		
		if(isOnGround() && currentHorizontalVelocity == 0) 
			setPhysicsState(MovingObject.PhysicsState.IDLE);
		
		else if(isOnGround())  
		{
			setPhysicsState(MovingObject.PhysicsState.WALKING);
			setDirection((currentHorizontalVelocity > 0) ? MovingObject.Direction.RIGHT : MovingObject.Direction.LEFT);
		}	
		else if(currentHorizontalVelocity == 0) 
			setPhysicsState((currentVerticalVelocity <= 0) ? MovingObject.PhysicsState.JUMPING : MovingObject.PhysicsState.FALLING);

		else
		{
			setPhysicsState((currentVerticalVelocity <= 0) ? MovingObject.PhysicsState.JUMPING : MovingObject.PhysicsState.FALLING);
			setDirection((currentHorizontalVelocity > 0) ? MovingObject.Direction.RIGHT : MovingObject.Direction.LEFT);	
		}
	}
}
