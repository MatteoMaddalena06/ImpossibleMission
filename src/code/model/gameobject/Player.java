package code.model.gameobject;

//data structure modules
import java.util.List;
import java.util.ArrayList;

//inproject import
import code.model.puzzle.PuzzlePiece;
import code.model.room.Room;
import code.model.room.RoomMap;
import code.model.utils.GameContext;
import code.model.utils.Point;
import code.model.gameobject.enemy.AttackerRobot;

public class Player extends MovingObject
{	
	private static final int    NORMAL_WIDTH     = 1 * RoomMap.TILE_SIZE;
	private static final int    NORMAL_HEIGHT    = 3 * RoomMap.TILE_SIZE; 
	private static final int    JUMP_WIDTH       = NORMAL_WIDTH;
	private static final int    JUMP_HEIGHT      = 1 * RoomMap.TILE_SIZE; 
	private static final double HORIZONTAL_SPEED = 300f; 
	private static final double JUMP_INCREASE    = 50f;
	private static final double VERTICAL_SPEED   = 600f;
	
	private List<PuzzlePiece> puzzlePiecesObtained;
	private int robotPasswordsObtained;
	private int platformPasswordsObtained;
	
	private boolean isOnPlatform;
	private Platform usedPlatform;
	
	private boolean isSearching;
	private Furniture usedFurniture;
	
	private boolean wasHitboxModified;
		
	public Player(Point position)
	{
		super(position, NORMAL_WIDTH, NORMAL_HEIGHT); 
		puzzlePiecesObtained = new ArrayList<PuzzlePiece>();
		robotPasswordsObtained = platformPasswordsObtained = 0;
		setPhysicsState(MovingObject.PhysicsState.IDLE);
		wasHitboxModified = isSearching = isOnPlatform  = false;
	}

	@Override
	public void update(GameContext context) 
	{	
		Room currentRoom = context.getCurrentRoom();
		List<GameObject> interestingGameObjects = 
				currentRoom.getGameObjectList().stream().filter(g -> g instanceof FixedObject || g instanceof Platform).toList();
	
		if(isOnPlatform)
		{ getPosition().setY(usedPlatform.getPosition().getY() - getHeight()); return; }

		setHorizontalVelocity(0);
		
		if(context.getUserInput(GameContext.UserInput.LEFT) && !isSearching)
			setHorizontalVelocity(-HORIZONTAL_SPEED - ((!isOnGround()) ? JUMP_INCREASE : 0));
		
		if(context.getUserInput(GameContext.UserInput.RIGHT) && !isSearching) 
			setHorizontalVelocity(HORIZONTAL_SPEED + ((!isOnGround()) ? JUMP_INCREASE : 0));
		
		if(context.getUserInput(GameContext.UserInput.JUMP) && isOnGround() && !isSearching)
		{ setVerticalVelocity(-VERTICAL_SPEED); shrinkHitbox(JUMP_WIDTH, JUMP_HEIGHT); } 	

		applyHorizontalForce();
		resolveHorizontalCollision(interestingGameObjects);
		
		addGravity();
		applyVerticalForce();

		resolveVerticalCollision(interestingGameObjects);	
		
		double currentHorizontalVelocity = getHorizontalVelocity();
		double currentVerticalVelocity = getVerticalVelocity();
		
		if(isOnGround() && currentHorizontalVelocity == 0) 
			setPhysicsState(MovingObject.PhysicsState.IDLE);
		
		else if(isOnGround())  
		{
			setPhysicsState(MovingObject.PhysicsState.WALKING);
			setDirection((currentHorizontalVelocity > 0) ? MovingObject.Direction.RIGHT : MovingObject.Direction.LEFT);
		}
		else if(currentHorizontalVelocity == 0) 
			setPhysicsState((currentVerticalVelocity < 0) ? MovingObject.PhysicsState.JUMPING : MovingObject.PhysicsState.FALLING);

		else
		{
			setPhysicsState((currentVerticalVelocity < 0) ? MovingObject.PhysicsState.JUMPING : MovingObject.PhysicsState.FALLING);
			setDirection((currentHorizontalVelocity > 0) ? MovingObject.Direction.RIGHT : MovingObject.Direction.LEFT);	
		}
		
		if(isOnGround() && wasHitboxModified) expandHitbox(NORMAL_WIDTH, NORMAL_HEIGHT);
		
		boolean isCollidingWithEnemy = currentRoom.getEnemiesList().stream().anyMatch(g -> isColliding(g));
		boolean isCollidingWithAttack = currentRoom.getGameObjectList().stream().filter(g -> g instanceof AttackerRobot.Attack).anyMatch(a -> isColliding(a));
		
		if((isCollidingWithEnemy || isCollidingWithAttack) && !context.isRobotsDisabled() || getPosition().getY() >= RoomMap.MAP_HEIGHT * RoomMap.TILE_SIZE) 
		{ }
	}
	
	private void shrinkHitbox(int newWidth, int newHeight) 
	{
		Point thisPosition = getPosition();
		int thisWidth = getWidth(), thisHeight = getHeight();
	    thisPosition.setX(thisPosition.getX() + (thisWidth - newWidth) / 2);
	    thisPosition.setY(thisPosition.getY() + (thisHeight - newHeight));
	    setWidth(newWidth); setHeight(newHeight);
	    
	    wasHitboxModified = true;
	}
	
	private void expandHitbox(int newWidth, int newHeight) 
	{
		Point thisPosition = getPosition();
		int thisWidth = getWidth(), thisHeight = getHeight();
	    thisPosition.setX(thisPosition.getX() - (newWidth - thisWidth) / 2);
	    thisPosition.setY(thisPosition.getY() - (newHeight - thisHeight));
	    setWidth(newWidth); setHeight(newHeight);
	    
	    wasHitboxModified = false;
	}
	
	public boolean isStandingOnTopOf(Platform obj)
	{
	    double playerLeft   = getPosition().getX();
	    double playerRight  = playerLeft + getWidth();
	    double playerBottom = getPosition().getY() + getHeight();

	    double objLeft   = obj.getPosition().getX();
	    double objRight  = objLeft + obj.getWidth();
	    double objTop    = obj.getPosition().getY();

	    boolean horizontalOverlap =
	        playerRight > objLeft && playerLeft < objRight;

	    boolean verticalContact =
	        Math.abs(playerBottom - objTop) <= STANDING_TOLLERANCE;

	    return horizontalOverlap && verticalContact;
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
	
	public void setOnPlatformState(boolean isOnPlatform)
	{ this.isOnPlatform = isOnPlatform; }
	
	public boolean isOnPlatform()
	{ return isOnPlatform; }
	
	public void setUsedPlatform(Platform platform)
	{ usedPlatform = platform; }
	
	public Platform getUsedPlatform()
	{ return usedPlatform; }
	
	void setSearchingState(boolean isSearching)
	{ this.isSearching = isSearching; }
	
	public boolean isSearching()
	{ return isSearching; }
	
	public void setUsedFurniture(Furniture furniture)
	{ usedFurniture = furniture; }
	
	public Furniture getUsedFurniture()
	{ return usedFurniture; }
}
