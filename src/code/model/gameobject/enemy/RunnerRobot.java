package code.model.gameobject.enemy;

//data structure import
import java.util.List;
//inproject import
import code.model.room.RoomMap;
import code.model.utils.GameContext;
import code.model.utils.Point;
import code.model.gameobject.FixedObject;
import code.model.gameobject.GameObject;
import code.model.gameobject.Player;

public class RunnerRobot extends Enemy
{	
	private static final long serialVersionUID = 1L;
	
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
		}	
		else if(!thisFov.isColliding(player))
		{ applyGroundMovement(context, HORIZONTAL_SPEED); actionDelay = ACTION_DELAY; }
		
		thisFov.setX(thisX - (FOV_WIDTH - getWidth())/2); 
		thisFov.setY(thisY - (FOV_HEIGHT - getHeight()));
	}
}
