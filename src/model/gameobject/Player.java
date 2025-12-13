package model.gameobject;

//data structure modules
import java.util.List;
import java.util.ArrayList;

//inproject import
import model.puzzle.PuzzlePiece;
import model.room.Room;
import model.room.RoomMap;
import model.utils.GameContext;
import model.utils.Point;

public class Player extends MovingObject
{	
	private static final int    NORMAL_WIDTH     = 1 * RoomMap.TILE_SIZE;
	private static final int    NORMAL_HEIGHT    = 3 * RoomMap.TILE_SIZE; 
	private static final int    JUMP_WIDTH       = NORMAL_WIDTH;
	private static final int    JUMP_HEIGHT      = 1 * RoomMap.TILE_SIZE; 
	private static final double HORIZONTAL_SPEED = 300f; 
	private static final double JUMP_INCREASE    = 150f;
	private static final double VERTICAL_SPEED   = 700f;
	
	private List<PuzzlePiece> puzzlePiecesObtained;
	private int robotPasswordsObtained;
	private int platformPasswordsObtained;
	
	private boolean isSearching;
	
	public Player(Point position)
	{
		super(position, NORMAL_WIDTH, NORMAL_HEIGHT); 
		puzzlePiecesObtained = new ArrayList<PuzzlePiece>();
		robotPasswordsObtained = platformPasswordsObtained = 0;
		setPhysicsState(MovingObject.PhysicsState.IDLE);
	}

	@Override
	public void update(GameContext context) 
	{
		Room currentRoom = context.getCurrentRoom();
		List<GameObject> interestingGameObjects = 
				currentRoom.getGameObjectList().stream().filter(g -> g instanceof FixedObject || g instanceof Platform).toList();
		
		setHorizontalVelocity(0);
		
		if(context.getUserInput(GameContext.UserInput.LEFT)  && !isSearching)
			setHorizontalVelocity(-HORIZONTAL_SPEED - ((!isOnGround()) ? JUMP_INCREASE : 0));
		
		if(context.getUserInput(GameContext.UserInput.RIGHT) && !isSearching) 
			setHorizontalVelocity(HORIZONTAL_SPEED + ((!isOnGround()) ? JUMP_INCREASE : 0));
		
		if(context.getUserInput(GameContext.UserInput.JUMP) && isOnGround())
		{ setVerticalVelocity(-VERTICAL_SPEED); shrinkHitbox(JUMP_WIDTH, JUMP_HEIGHT); }	
		
		addGravity();
		
		applyHorizontalForce();
		resolveHorizontalCollision(interestingGameObjects);
		
		applyVerticalForce();
		resolveVerticalCollision(interestingGameObjects);
		
		if(isOnGround() && getHorizontalVelocity() == 0) 
		{
			setPhysicsState(MovingObject.PhysicsState.IDLE);
			setDirection(getPreviousDirection());
		}
		else if(isOnGround())  
		{
			setPhysicsState(MovingObject.PhysicsState.WALKING);
			setDirection((getHorizontalVelocity() > 0) ? MovingObject.Direction.RIGHT : MovingObject.Direction.LEFT);
		}
		else if(getHorizontalVelocity() == 0) 
		{
			setPhysicsState((getVerticalVelocity() < 0) ? MovingObject.PhysicsState.JUMPING : MovingObject.PhysicsState.FALLING);
			setDirection(getPreviousDirection());
		}
		else
		{
			setPhysicsState((getVerticalVelocity() < 0) ? MovingObject.PhysicsState.JUMPING : MovingObject.PhysicsState.FALLING);
			setDirection((getHorizontalVelocity() > 0) ? MovingObject.Direction.RIGHT : MovingObject.Direction.LEFT);	
		}

		setPreviousDirection(getDirection());
		
		if(isOnGround() && wasHitboxModified()) expandHitbox(NORMAL_WIDTH, NORMAL_HEIGHT);
		
		if((currentRoom.getEnemiesList().stream().anyMatch(g -> isColliding(g)) && !context.isRobotsDisabled()) || getPosition().getY() >= RoomMap.MAP_HEIGHT * RoomMap.TILE_SIZE) 
		{ /*die */ }
	}
	
	public void givePuzzlePiece(PuzzlePiece piece)
	{ puzzlePiecesObtained.add(piece); }
	
	public void giveRobotPassword()
	{ robotPasswordsObtained++; }
	
	public void givePlatformPassword()
	{ platformPasswordsObtained++; }
	
	public boolean useRobotPassword()
	{ return (robotPasswordsObtained == 0) ? false : robotPasswordsObtained-- >= 0; }
	
	public boolean usePlatoformPassword()
	{ return (platformPasswordsObtained == 0) ? false : platformPasswordsObtained-- >= 0; }
	
	public void setSearchingState(boolean isSearching)
	{ this.isSearching = isSearching; }
	
	public boolean isSearching()
	{ return isSearching; }
}
