package code.model.gameobjects.enemy;

//data structure import
import java.util.List;

import code.model.Point;
import code.model.context.GameContext;
import code.model.gameobjects.FixedObject;
import code.model.gameobjects.GameObject;
import code.model.gameobjects.MovingObject;
import code.model.gameobjects.Player;
//inproject import
import code.model.room.RoomMap;

public class RunnerRobot extends Enemy
{	
	private static final double    HORIZONTAL_SPEED = 350f;
	private static final int       FOV_WIDTH        = 16 * RoomMap.TILE_SIZE;
	private static final int   	   FOV_HEIGHT       = 4 * RoomMap.TILE_SIZE;
	private transient final double INITIAL_FOV_X    = getPosition().getX() - (FOV_WIDTH - getWidth())/2;
	private transient final double INITIAL_FOV_Y    = getPosition().getY() - (FOV_HEIGHT - getHeight()); 
	private static final double    ACTION_DELAY     = 0.5f;
	
	private double actionDelay;
	
	public RunnerRobot(Point point, int width, int height)
	{ 
		super(point, width, height);
		setFov(this.new FieldOfView(new Point(INITIAL_FOV_X, INITIAL_FOV_Y), FOV_WIDTH, FOV_HEIGHT));
		actionDelay = ACTION_DELAY;
	}

	@Override
	public void update(GameContext context) 
	{
		if(context.isRobotsDisabled())
			return;
		
		Player player = context.getPlayer();
		double playerX = player.copyPosition().getX();
		double thisX = getPosition().getX(), thisY = getPosition().getY();
		int thisWidth = getWidth();
		
		Enemy.FieldOfView thisFov = getFov();
		
		if(!player.isOnGround() && playerX >= thisX && playerX <= thisX + thisWidth)
			actionDelay = ACTION_DELAY / 2;
		
		if(thisFov.isColliding(player) && (actionDelay -= GameContext.getDeltaTime()) <= 0)
		{
			List<FixedObject> fixedObjects = context.getCurrentRoom().getFixedObjectList();
			List<GameObject> interstingObjects = fixedObjects.stream().map(f -> (GameObject)f).toList();
			
			setHorizontalVelocity((thisX > playerX) ? -HORIZONTAL_SPEED : HORIZONTAL_SPEED);
			
			if(isOnLedge(fixedObjects))
				setHorizontalVelocity(0);
			
			applyHorizontalForce();
			resolveHorizontalCollision(interstingObjects);
			
			double currentHorizontalVelocity = getHorizontalVelocity();
			
			if(currentHorizontalVelocity != 0)
			{
				setPhysicsState(MovingObject.PhysicsState.WALKING);
				setDirection((currentHorizontalVelocity > 0) ? MovingObject.Direction.RIGHT : MovingObject.Direction.LEFT);
			}
			else 
				setPhysicsState(MovingObject.PhysicsState.IDLE);
		}	
		else if(!thisFov.isColliding(player))
		{ applyGroundMovement(context, HORIZONTAL_SPEED); actionDelay = ACTION_DELAY; }
		
		thisFov.setX(thisX - (FOV_WIDTH - getWidth())/2); 
		thisFov.setY(thisY - (FOV_HEIGHT - getHeight()));
	}
}
