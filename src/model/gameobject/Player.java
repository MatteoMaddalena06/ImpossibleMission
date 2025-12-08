package model.gameobject;

//data structure modules
import java.util.List;
import java.util.ArrayList;

//inproject import
import model.puzzle.PuzzlePiece;
import model.room.Room;
import model.room.RoomMap;

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
	
	private State state;
	
	public enum State 
	{ 
		WALKING_LEFT, WALKING_RIGHT, JUMPING_LEFT, JUMPING_RIGHT, FALLING_LEFT, 
		FALLING_RIGHT, JUMPING, FALLING, SEARCHING, IDLE 
	}
	
	public Player(Point position)
	{
		super(position, NORMAL_WIDTH, NORMAL_HEIGHT); 
		puzzlePiecesObtained = new ArrayList<PuzzlePiece>();
		robotPasswordsObtained = platformPasswordsObtained = 0;
		state = State.IDLE;
	}

	@Override
	public void update(GameContext context) 
	{
		Room currentRoom = context.getCurrentRoom();
		List<GameObject> interestingGameObjects = 
				currentRoom.getGameObjectList().stream().filter(g -> g instanceof FixedObject || g instanceof Platform).toList();
		
		setHorizontalVelocity(0);
		
		if(context.getUserInput(GameContext.UserInput.LEFT)  && state != State.SEARCHING) setHorizontalVelocity(-HORIZONTAL_SPEED);
		if(context.getUserInput(GameContext.UserInput.RIGHT) && state != State.SEARCHING) setHorizontalVelocity(HORIZONTAL_SPEED);
		
		if(context.getUserInput(GameContext.UserInput.JUMP) && isOnGround())
		{ setVerticalVelocity(-VERTICAL_SPEED); shrinkHitbox(JUMP_WIDTH, JUMP_HEIGHT); }	
		
		addGravity();
		
		applyHorizontalForce();
		resolveHorizontalCollision(interestingGameObjects);
		
		applyVerticalForce();
		resolveVerticalCollision(interestingGameObjects);
		
		if(isOnGround() && getHorizontalVelocity() == 0) state = State.IDLE;
		else if(isOnGround())  state = (getHorizontalVelocity() > 0) ? State.WALKING_RIGHT : State.WALKING_LEFT;
		else if(getHorizontalVelocity() == 0) state = (getVerticalVelocity() < 0) ? State.JUMPING : State.FALLING;
		else if(getHorizontalVelocity() > 0) state = (getVerticalVelocity() < 0) ? State.JUMPING_RIGHT : State.FALLING_RIGHT;
		else state = (getVerticalVelocity() < 0) ? State.JUMPING_LEFT : State.FALLING_LEFT;

		if(isOnGround() && wasHitboxModified()) expandHitbox(NORMAL_WIDTH, NORMAL_HEIGHT);
		
		if(currentRoom.getEnemiesList().stream().anyMatch(g -> isColliding(g)) || getPosition().getY() >= RoomMap.MAP_HEIGHT * RoomMap.TILE_SIZE) 
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
	
	public State getState()
	{ return state; }
	
	public void setStateOnSearching()
	{ state = State.SEARCHING; }
}
