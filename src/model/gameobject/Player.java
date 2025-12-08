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
	private static final double VERTICAL_SPEED   = 700f;
	
	private List<PuzzlePiece> puzzlePiecesObtained;
	private int robotPasswordsObtained;
	private int platformPasswordsObtained;
	
	public Player(Point position)
	{
		super(position, NORMAL_WIDTH, NORMAL_HEIGHT); 
		puzzlePiecesObtained = new ArrayList<PuzzlePiece>();
		robotPasswordsObtained = platformPasswordsObtained = 0;
		setState(MovingObject.State.IDLE);
	}

	@Override
	public void update(GameContext context) 
	{
		Room currentRoom = context.getCurrentRoom();
		List<GameObject> interestingGameObjects = 
				currentRoom.getGameObjectList().stream().filter(g -> g instanceof FixedObject || g instanceof Platform).toList();
		
		setHorizontalVelocity(0);
		
		if(context.getUserInput(GameContext.UserInput.LEFT)  && getState() != State.SEARCHING) setHorizontalVelocity(-HORIZONTAL_SPEED);
		if(context.getUserInput(GameContext.UserInput.RIGHT) && getState() != State.SEARCHING) setHorizontalVelocity(HORIZONTAL_SPEED);
		
		if(context.getUserInput(GameContext.UserInput.JUMP) && isOnGround())
		{ setVerticalVelocity(-VERTICAL_SPEED); shrinkHitbox(JUMP_WIDTH, JUMP_HEIGHT); }	
		
		addGravity();
		
		applyHorizontalForce();
		resolveHorizontalCollision(interestingGameObjects);
		
		applyVerticalForce();
		resolveVerticalCollision(interestingGameObjects);
		
		if(isOnGround() && getHorizontalVelocity() == 0) 
			setState(MovingObject.State.IDLE);
		
		else if(isOnGround())  
			setState((getHorizontalVelocity() > 0) ? MovingObject.State.WALKING_RIGHT : MovingObject.State.WALKING_LEFT);
		
		else if(getHorizontalVelocity() == 0) 
			setState((getVerticalVelocity() < 0) ? MovingObject.State.JUMPING : MovingObject.State.FALLING);
		
		else if(getHorizontalVelocity() > 0)
			setState((getVerticalVelocity() < 0) ? MovingObject.State.JUMPING_RIGHT : MovingObject.State.FALLING_RIGHT);
		
		else setState((getVerticalVelocity() < 0) ? MovingObject.State.JUMPING_LEFT : MovingObject.State.FALLING_LEFT);

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
	
	public void setStateOnSearching()
	{ setState(MovingObject.State.SEARCHING); }
}
