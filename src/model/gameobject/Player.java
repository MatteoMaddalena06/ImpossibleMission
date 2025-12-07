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
	
	private boolean wasHitboxModified;
	
	public enum State 
	{ 
		WALKING_LEFT, WALKING_RIGHT, JUMPING_LEFT, JUMPING_RIGHT, FALLING_LEFT, 
		FALLING_RIGHT, JUMPING, FALLING, SEARCHING, IDLE 
	}
	
	public Player(Point position)
	{
		super(position, NORMAL_WIDTH, NORMAL_HEIGHT, HORIZONTAL_SPEED, VERTICAL_SPEED); 
		puzzlePiecesObtained = new ArrayList<PuzzlePiece>();
		robotPasswordsObtained = platformPasswordsObtained = 0;
		state = State.IDLE;	wasHitboxModified = false;
	}

	@Override
	public void update(GameContext context) 
	{
		Room currentRoom = context.getCurrentRoom();
		List<GameObject> interestingGameObject = 
				currentRoom.getGameObjectList().stream().filter(g -> g instanceof FixedObject || g instanceof Platform).toList();
		
		horizontalVelocity = 0;
		
		if(context.getUserInput(GameContext.UserInput.LEFT))  horizontalVelocity = -HORIZONTAL_SPEED;
		if(context.getUserInput(GameContext.UserInput.RIGHT)) horizontalVelocity = HORIZONTAL_SPEED;
		if(state == State.SEARCHING) horizontalVelocity = 0;
		
		if(context.getUserInput(GameContext.UserInput.JUMP) && onGround)
		{ 
			verticalVelocity = -VERTICAL_SPEED; 
			shrinkHitbox(JUMP_WIDTH, JUMP_HEIGHT);
			wasHitboxModified = true;
		}	
		
		addGravity();
		
		applyHorizontalForce();
		resolveHorizontalCollision(interestingGameObject);
		
		applyVerticalForce();
		resolveVerticalCollision(interestingGameObject);
		
		if(onGround && horizontalVelocity == 0) state = State.IDLE;
		else if(onGround)  state = (horizontalVelocity > 0) ? State.WALKING_RIGHT : State.WALKING_LEFT;
		else if(horizontalVelocity == 0) state = (verticalVelocity < 0) ? State.JUMPING : State.FALLING;
		else if(horizontalVelocity > 0) state = (verticalVelocity < 0) ? State.JUMPING_RIGHT : State.FALLING_RIGHT;
		else state = (verticalVelocity < 0) ? State.JUMPING_LEFT : State.FALLING_LEFT;

		if(onGround && wasHitboxModified) 
		{ expandHitbox(NORMAL_WIDTH, NORMAL_HEIGHT); wasHitboxModified = false; }
		
		if(currentRoom.getRobotList().stream().anyMatch(g -> isColliding(g)) || position.getY() >= RoomMap.MAP_HEIGHT * RoomMap.TILE_SIZE) 
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
