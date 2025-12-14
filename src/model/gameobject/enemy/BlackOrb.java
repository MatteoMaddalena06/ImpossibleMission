package model.gameobject.enemy;

//inproject import
import model.utils.Point;

import java.util.List;

import model.gameobject.FixedObject;
import model.gameobject.GameObject;
import model.gameobject.Platform;
import model.utils.GameContext;

public class BlackOrb extends Enemy
{
	private static final double HORIZONTAL_SPEED = 150f;
	private static final double VERTICAL_SPEED   = 150f;
	private static final double ACTION_DELAY     = 0.03f;
	private static final double BOUND            = 4;
	
	public BlackOrb(Point position, int width, int height)
	{ super(position, width, height, ACTION_DELAY); }
	
	@Override
	public void update(GameContext context)
	{
		Point targetPosition = getTargetPosition(context.getPlayer().copyPosition());
		int targetX = targetPosition.getX(), targetY = targetPosition.getY();
		int thisX = getPosition().getX(), thisY = getPosition().getY();
		
		setHorizontalVelocity(0); setVerticalVelocity(0);
				
		if(Math.abs(thisX - targetX) >= BOUND) 
			setHorizontalVelocity((thisX > targetX) ? -HORIZONTAL_SPEED : HORIZONTAL_SPEED);
		
		if(Math.abs(thisY - targetY) >= BOUND)
			setVerticalVelocity((thisY > targetY) ? -VERTICAL_SPEED : VERTICAL_SPEED);
		
		List<GameObject> interestingGameObjects = 
				context.getCurrentRoom().getGameObjectList().stream().filter(g -> g instanceof FixedObject || g instanceof Platform).toList();
		
		applyHorizontalForce();
		resolveHorizontalCollision(interestingGameObjects);
		
		applyVerticalForce();
		resolveVerticalCollision(interestingGameObjects);
	}
}
