package model.gameobject.enemy;

//data structure modules
import java.util.List;
import model.room.RoomMap;
import model.utils.GameContext;
import model.utils.Point;
import model.gameobject.FixedObject;
import model.gameobject.GameObject;
import model.gameobject.MovingObject;
import model.gameobject.Player;

public class RunnerRobot extends Enemy
{	
	private static final long serialVersionUID = 1L;
	
	private static final double HORIZONTAL_SPEED = 500f;
	private static final double VERTICAL_SPEED   = 0f;
	private static final int    FOV_WIDTH        = 16 * RoomMap.TILE_SIZE;
	private static final int    FOV_HEIGHT       = 4 * RoomMap.TILE_SIZE;
	private transient final int FOV_X            = getPosition().getX() - (FOV_WIDTH - getWidth())/2;
	private transient final int FOV_Y            = getPosition().getY() - (FOV_HEIGHT - getHeight()); 
	private static final double RESPONSE_DELAY   = 0.03f;
	
	public RunnerRobot(Point point, int width, int height)
	{ 
		super(point, width, height, RESPONSE_DELAY);
		setFov(this.new FieldOfView(new Point(FOV_X, FOV_Y), FOV_WIDTH, FOV_HEIGHT));
	}

	@Override
	public void update(GameContext context) 
	{
		Player player = context.getPlayer();
		Point currentPlayerPosition = player.copyPosition();
		Point thisPosition = getPosition();
		int thisX = thisPosition.getX(), thisY = thisPosition.getY();
		
		if(getFov().isColliding(player))
		{
			if(getPreviousPlayerPosition() == null)
				setPreviousPlayerPosition(currentPlayerPosition);
			
			int targetX = getTargetPosition(currentPlayerPosition).getX();
			setHorizontalVelocity(0);
			
			if(Math.abs(thisX - targetX) >= 3) 
				setHorizontalVelocity((thisX > targetX) ? -HORIZONTAL_SPEED : HORIZONTAL_SPEED);
		}
		else
			setRandomHorizontalVelocity();
		
		if(isOnLedge(context.getCurrentRoom().getFixedObjectList()))
			setHorizontalVelocity(0);
		
		List<GameObject> interestingGameObjects = 
				context.getCurrentRoom().getGameObjectList().stream().filter(g -> g instanceof FixedObject).toList();
	
		addGravity();
		
		applyHorizontalForce();
		resolveHorizontalCollision(interestingGameObjects);
		
		applyVerticalForce();
		resolveVerticalCollision(interestingGameObjects);
		
		setState((getHorizontalVelocity() > 0) ? MovingObject.State.WALKING_RIGHT : MovingObject.State.WALKING_LEFT);
		if (getHorizontalVelocity() == 0) setState(MovingObject.State.IDLE);
		
		Enemy.FieldOfView thisFov = getFov();
		thisFov.setX(thisX - (FOV_WIDTH - getWidth())/2); 
		thisFov.setY(thisY - (FOV_HEIGHT - getHeight()));
	}
}
