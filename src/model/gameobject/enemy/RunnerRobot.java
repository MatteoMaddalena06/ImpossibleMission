package model.gameobject.enemy;

//inproject import
import model.room.RoomMap;
import model.utils.GameContext;
import model.utils.Point;

public class RunnerRobot extends Enemy
{	
	private static final long serialVersionUID = 1L;
	
	private static final double HORIZONTAL_SPEED = 500f;
	private static final int    FOV_WIDTH        = 16 * RoomMap.TILE_SIZE;
	private static final int    FOV_HEIGHT       = 4 * RoomMap.TILE_SIZE;
	private transient final int INITIAL_FOV_X    = getPosition().getX() - (FOV_WIDTH - getWidth())/2;
	private transient final int INITIAL_FOV_Y    = getPosition().getY() - (FOV_HEIGHT - getHeight()); 
	private static final double ACTION_DELAY     = 0.03f;
	private static final double BOUND            = 3f;
	
	public RunnerRobot(Point point, int width, int height)
	{ 
		super(point, width, height, ACTION_DELAY);
		setFov(this.new FieldOfView(new Point(INITIAL_FOV_X, INITIAL_FOV_Y), FOV_WIDTH, FOV_HEIGHT));
	}

	@Override
	public void update(GameContext context) 
	{
		if(context.isRobotsDisabled())
			return;
		
		applyGroundMovement(context, HORIZONTAL_SPEED, BOUND);
		
		Enemy.FieldOfView thisFov = getFov();
		int thisX = getPosition().getX(), thisY = getPosition().getY();

		thisFov.setX(thisX - (FOV_WIDTH - getWidth())/2); 
		thisFov.setY(thisY - (FOV_HEIGHT - getHeight()));
	}
}
