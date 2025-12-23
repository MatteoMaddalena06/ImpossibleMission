package code.model.gameobject.enemy;

//data structure modules
import java.util.List;

//inproject import
import code.model.utils.Point;
import code.model.gameobjects.FixedObject;
import code.model.gameobjects.GameObject;
import code.model.gameobjects.MovingObject;
import code.model.gameobjects.Platform;
import code.model.gameobjects.Player;
import code.model.utils.GameContext;

public class BlackOrb extends Enemy
{
	private static final double HORIZONTAL_SPEED = 100f;
	private static final double VERTICAL_SPEED   = 150f;
	private static final double BOUND            = 3;
	
	public BlackOrb(Point position, int width, int height)
	{ super(position, width, height); }

	@Override
	public void update(GameContext context)
	{
		if(context.isRobotsDisabled())
			return;

		Player player = context.getPlayer();
		Point playerPosition = player.copyPosition();
		double playerX = playerPosition.getX(), playerY = playerPosition.getY();
		double thisX = getPosition().getX(), thisY = getPosition().getY();
		
		setHorizontalVelocity(0); setVerticalVelocity(0);
				
		if(Math.abs(thisX - playerX) >= BOUND) 
			setHorizontalVelocity((thisX > playerX) ? -HORIZONTAL_SPEED : HORIZONTAL_SPEED);
		
		if(Math.abs(thisY - playerY) >= BOUND)
			setVerticalVelocity((thisY > playerY) ? -VERTICAL_SPEED : VERTICAL_SPEED);
		
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
