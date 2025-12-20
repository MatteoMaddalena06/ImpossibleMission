package code.model.gameobject.enemy;

import java.util.List;

//inproject import
import code.model.gameobject.FixedObject;
import code.model.gameobject.GameObject;
import code.model.gameobject.MovingObject;
import code.model.gameobject.Platform;
import code.model.room.RoomMap;
import code.model.utils.GameContext;
import code.model.utils.Point;

public class JumperRobot extends Enemy
{
	private static final double HORIZONTAL_SPEED = 200f;
	private static final double VERTICAL_SPEED   = 500f;
	private static final int    FOV_WIDTH        = 6 * RoomMap.TILE_SIZE;
	private static final int    FOV_HEIGHT       = 5 * RoomMap.TILE_SIZE;
	private transient final int INITIAL_FOV_X    = getPosition().getX() - (FOV_WIDTH - getWidth())/2;
	private transient final int INITIAL_FOV_Y    = getPosition().getY() - (FOV_HEIGHT - getHeight()); 
	private static final double ACTION_DELAY     = 0.1f;
	private static final double BOUND            = 48f;
	private static final int    JUMP_INTERVAL    = 30;
	
	private int jumpInterval;
	
	public JumperRobot(Point point, int width, int height)
	{ 
		super(point, width, height, ACTION_DELAY);
		setFov(this.new FieldOfView(new Point(INITIAL_FOV_X, INITIAL_FOV_Y), FOV_WIDTH, FOV_HEIGHT));
		jumpInterval = JUMP_INTERVAL;
	}
	
	@Override
	public void update(GameContext context)
	{
		if(context.isRobotsDisabled())
			return;
		
		Enemy.FieldOfView thisFov = getFov();
		List<GameObject> interestingGameObjects = 
				context.getCurrentRoom().getGameObjectList().stream().filter(g -> g instanceof FixedObject || g instanceof Platform).toList();
		
		if(thisFov.isColliding(context.getPlayer()))
		{
			if(isOnGround() && --jumpInterval <= 0) 
			{ setVerticalVelocity(-VERTICAL_SPEED); jumpInterval = JUMP_INTERVAL; }
			
			addGravity();
			applyVerticalForce();
			resolveVerticalCollision(interestingGameObjects);
			
			int playerX  = context.getPlayer().copyPosition().getX();
			int thisX = getPosition().getX();
			
			setDirection((thisX > playerX) ? MovingObject.Direction.LEFT : MovingObject.Direction.RIGHT);
			
			if(!isOnGround())
				setPhysicsState((getVerticalVelocity() < 0) ? MovingObject.PhysicsState.JUMPING : MovingObject.PhysicsState.FALLING);
			
			else 
				setPhysicsState(MovingObject.PhysicsState.IDLE);
		}
		else 
		{
			jumpInterval = 0;
			applyGroundMovement(context, HORIZONTAL_SPEED, BOUND);
		}
		
		int thisX = getPosition().getX(), thisY = getPosition().getY();

		thisFov.setX(thisX - (FOV_WIDTH - getWidth())/2); 
		thisFov.setY(thisY - (FOV_HEIGHT - getHeight()));
	}
}
