package model.gameobject;

//data structure modules
import java.util.List;

//inproject import
import model.puzzle.PuzzlePiece;
import model.room.Room;
import model.room.RoomMap;

public class Player extends MovingObject
{	
	private static final int    NORMAL_WIDTH  = 1 * RoomMap.TILE_SIZE;
	private static final int    NORMAL_HEIGHT = 3 * RoomMap.TILE_SIZE; 
	private static final int    JUMP_WIDTH    = NORMAL_WIDTH;
	private static final int    JUMP_HEIGHT   = 1 * RoomMap.TILE_SIZE; 
	private static final double HORIZONTAL_SPEED = 200f; 
	private static final double VERTICAL_SPEED   = 500f; 
	
	private List<PuzzlePiece> puzzlePiecesObtained;
	private int robotPasswordsObtained;
	private int platformPasswordsObtained;
	
	public Player(Point position)
	{ super(position, NORMAL_WIDTH, NORMAL_HEIGHT, HORIZONTAL_SPEED, VERTICAL_SPEED); }

	@Override
	public void update(GameContext context) 
	{
		Room currentRoom = context.getCurrentRoom();
		List<GameObject> interestingGameObject = 
				currentRoom.getGameObjectList().stream().filter(g -> g instanceof FixedObject || g instanceof Platform).toList();
		
		horizontalVelocity = 0;
		
		if(context.getUserInput(GameContext.UserInput.LEFT))  horizontalVelocity = -HORIZONTAL_SPEED;
		if(context.getUserInput(GameContext.UserInput.RIGHT)) horizontalVelocity = HORIZONTAL_SPEED;
		
		if(context.getUserInput(GameContext.UserInput.JUMP) && onGround)
		{ 
			verticalVelocity = -VERTICAL_SPEED; 
			onGround = false;
			shrinkHitbox();		
		}
		
		addGravity();
		
		applyHorizontalForce();
		resolveHorizontalCollision(interestingGameObject);
		
		applyVerticalForce();
		resolveVerticalCollision(interestingGameObject);
		
		if(onGround)
			expandHitbox();
		
		if(currentRoom.getRobotList().stream().anyMatch(g -> isColliding(g)) || position.getY() >= RoomMap.MAP_HEIGHT * RoomMap.TILE_SIZE) 
		{ /* die */ }
	}
	
	private void shrinkHitbox() 
	{
	    int oldWidth = width;
	    int oldHeight = height;

	    width  = JUMP_WIDTH;
	    height = JUMP_HEIGHT;

	    position.setX(position.getX() + (oldWidth - width) / 2);
	    position.setY(position.getY() + (oldHeight - height));
	}
	
	private void expandHitbox() 
	{
	    int oldWidth = width;
	    int oldHeight = height;

	    width  = NORMAL_WIDTH;
	    height = NORMAL_HEIGHT;

	    position.setX(position.getX() - (width - oldWidth) / 2);
	    position.setY(position.getY() - (height - oldHeight));
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
}
