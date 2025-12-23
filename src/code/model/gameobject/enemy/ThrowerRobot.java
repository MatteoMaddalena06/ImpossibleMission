package code.model.gameobject.enemy;

//data structure modules
import java.util.List;

//inproject import
import code.model.utils.Point;
import code.model.utils.GameContext;
import code.model.gameobjects.FixedObject;
import code.model.gameobjects.GameObject;
import code.model.gameobjects.MovingObject;
import code.model.gameobjects.Platform;
import code.model.room.Room;
import code.model.room.RoomMap;

public class ThrowerRobot extends AttackerRobot
{
	private static final double HORIZONTAL_SPEED        = 200f;
	private static final int    FOV_WIDTH               = 12 * RoomMap.TILE_SIZE;
	private static final int    FOV_HEIGHT              = 5 * RoomMap.TILE_SIZE;
	private final  double       INITIAL_FOV_X           = getPosition().getX();
	private final  double       INITIAL_FOV_Y           = getPosition().getY() - (FOV_HEIGHT - getHeight());
	private static final double ATTACK_PROBABILITY      = 0.5f;
 	private static final int    ATTACK_WIDTH      	    = 1 * RoomMap.TILE_SIZE;
	private static final int    ATTACK_HEIGHT     	    = 1 * RoomMap.TILE_SIZE;
	private static final double ATTACK_HORIZONTAL_SPEED = 600f;
	private static final double ATTACK_VERTICAL_SPEED   = 450f;;
	
	private int attackCounter;
	
	public ThrowerRobot(Point position, int width, int height)
	{
		super(position, width, height);
		setFov(this.new FieldOfView(new Point(INITIAL_FOV_X, INITIAL_FOV_Y), FOV_WIDTH, FOV_HEIGHT));
		attackCounter = 0;
	}
	
	@Override
	public void update(GameContext context)
	{
		if(isAttacking() || context.isRobotsDisabled())
			return;

		Enemy.FieldOfView thisFov = getFov();
		
		double pThisFrame = 1.0 - Math.pow(1.0 - ATTACK_PROBABILITY, GameContext.getDeltaTime());
		
		if(thisFov.isColliding(context.getPlayer()) && Math.random() < pThisFrame)
		{
			context.getCurrentRoom().addEnemyAttack(produceAttack());
			setAttackingState(true);
			return;
		}
		
		applyGroundMovement(context, HORIZONTAL_SPEED);
		
		double currentHorizontalVelocity = getHorizontalVelocity();
		double thisX = getPosition().getX(), thisY = getPosition().getY();
		
		if(currentHorizontalVelocity != 0)
		{
			thisFov.setX((currentHorizontalVelocity > 0) ? thisX : thisX - FOV_WIDTH + getWidth());
			thisFov.setY(thisY - (FOV_HEIGHT - getHeight()));
		}
	}
	
	@Override 
	protected Attack produceAttack()
	{
		Point thisPosition = getPosition();
		double thisX = thisPosition.getX();
		
		double attackX, attackY = thisPosition.getY();
		
		if(getHorizontalVelocity() == 0) 
			attackX = ((getDirection()) == MovingObject.Direction.RIGHT) ? thisX + RoomMap.TILE_SIZE: thisX;
		
		else 
			attackX = (getHorizontalVelocity() > 0 ) ? thisX + RoomMap.TILE_SIZE: thisX;
		
		return new Attack(new Point(attackX, attackY), ATTACK_WIDTH, ATTACK_HEIGHT) {
			@Override
			public void update(GameContext context)
			{
				Room currentRoom = context.getCurrentRoom();
				
				if((attackCounter != 0 && isOnGround()) || getPosition().getY() >= RoomMap.MAP_HEIGHT * RoomMap.TILE_SIZE)
				{
					currentRoom.removeEnemyAttack(this);
					setAttackingState(false); attackCounter = 0;
					return;
				}
				
				List<GameObject> interestingObjects = 
						currentRoom.getGameObjectList().stream().filter(g -> g instanceof FixedObject || g instanceof Platform).toList();
				
				setHorizontalVelocity((getDirection() == MovingObject.Direction.RIGHT) ? ATTACK_HORIZONTAL_SPEED : -ATTACK_HORIZONTAL_SPEED);
				if(attackCounter == 0) setVerticalVelocity(-ATTACK_VERTICAL_SPEED);
				
				addGravity();
				
				applyHorizontalForce();
				resolveHorizontalCollision(interestingObjects);
				
				applyVerticalForce();
				resolveVerticalCollision(interestingObjects);
				
				attackCounter++;
			}
		};
	}
}
