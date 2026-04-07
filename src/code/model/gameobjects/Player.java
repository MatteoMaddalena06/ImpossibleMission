package code.model.gameobjects;

//data structure modules
import java.util.List;
import java.util.ArrayList;
//inproject import
import code.model.puzzle.PuzzlePiece;
import code.model.room.Room;
import code.model.room.RoomMap;
import code.model.Point;
import code.model.context.GameContext;
import code.model.context.GameWillEnd;
import code.model.context.PlayerDied;
import code.model.gameobjects.enemy.AttackerRobot;

public class Player extends MovingObject
{	
	private static final int    NORMAL_WIDTH     = 2 * RoomMap.TILE_SIZE;
	private static final int    NORMAL_HEIGHT    = 3 * RoomMap.TILE_SIZE; 
	private static final int    JUMP_WIDTH       = NORMAL_WIDTH;
	private static final int    JUMP_HEIGHT      = 1 * RoomMap.TILE_SIZE; 
	private static final double HORIZONTAL_SPEED = 300f; 
	private static final double JUMP_INCREASE    = 50f;
	private static final double VERTICAL_SPEED   = 600f;
	protected static final int  STANDING_TOLLERANCE = 2; 
	
	private static final int  PLAYER_FULL_LIFES = 3;
	private static final long DIE_WAITING = 1500000000L;
	private static final int  DIE_PENALITY = 350;
	
	private int lifes;
	private String name;
	private int points;
	
	private List<PuzzlePiece> puzzlePiecesObtained;
	private int robotPasswordsObtained;
	private int platformPasswordsObtained;
	
	private boolean isOnPlatform;
	private Platform usedPlatform;
	
	private boolean isDead;
	private boolean isSearching;
	private Furniture usedFurniture;
	
	private boolean wasHitboxModified;
		
	public Player(String name, Point position)
	{
		super(position, NORMAL_WIDTH, NORMAL_HEIGHT); 
		lifes = PLAYER_FULL_LIFES;
		this.name = name;
		points = 0;
		puzzlePiecesObtained = new ArrayList<PuzzlePiece>();
		robotPasswordsObtained = platformPasswordsObtained = 0;
		setPhysicsState(MovingObject.PhysicsState.IDLE);
		wasHitboxModified = isSearching = isOnPlatform  = false;
	}

	@Override
	public void update(GameContext context) 
	{	
		if(isDead)
		{
			Point spawnPosition = context.getPlayerSpawn();
			Point thisPosition = getPosition();
			
			thisPosition.setX(spawnPosition.getX());
			thisPosition.setY(spawnPosition.getY());
			
			isDead = false;
		}
		
		Room currentRoom = context.getCurrentRoom();
		List<GameObject> interestingGameObjects = 
				currentRoom.getGameObjectList().stream().filter(g -> g instanceof FixedObject || g instanceof Platform).toList();
	
		if(isOnPlatform)
		{
			setPhysicsState(MovingObject.PhysicsState.IDLE);
			getPosition().setY(usedPlatform.getPosition().getY() - getHeight());
			expandHitbox(NORMAL_WIDTH, NORMAL_HEIGHT);
			return; 
		}

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
		{
			isDead = true;
			points = Math.max(0, points - DIE_PENALITY);
			context.getStatetListener().notifyState(new PlayerDied(DIE_WAITING));
			
			if(--lifes == 0)
				context.getStatetListener().notifyState(new GameWillEnd(DIE_WAITING));
		}
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

	public String getName()
	{ return name; }
	
	public int getLifes()
	{ return lifes; }
	
	public int getPoints()
	{ return points; }
	
	public void updatePoints(int amount)
	{ points += amount; }
	
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
	
	public boolean isDead()
	{ return isDead; }
	
	public boolean isSearching()
	{ return isSearching; }
	
	public void setUsedFurniture(Furniture furniture)
	{ usedFurniture = furniture; }
	
	public Furniture getUsedFurniture()
	{ return usedFurniture; }
}
