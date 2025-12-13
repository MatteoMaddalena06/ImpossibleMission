package model.gameobject.enemy;

//data structure modules
import java.util.List;

//inproject import
import model.gameobject.FixedObject;
import model.gameobject.GameObject;
import model.gameobject.Player;
import model.room.Room;
import model.room.RoomMap;
import model.utils.GameContext;
import model.utils.Point;
import model.gameobject.MovingObject;

public class LaserRobot extends AttackerRobot
{
	private static final long serialVersionUID = 1L;
	
	private static final double HORIZONTAL_SPEED   = 300f;
	private static final int    FOV_WIDTH          = 6 * RoomMap.TILE_SIZE;
	private static final int    FOV_HEIGHT         = 3 * RoomMap.TILE_SIZE;
	private static final double ACTION_DELAY       = 0.01f;
	private static final int    ATTACK_WIDTH       = FOV_WIDTH;
	private static final int    ATTACK_HEIGHT      = FOV_HEIGHT;
	private static final int    ATTACK_DURATION    = 60; 
	private static final double ATTACK_PROBABILITY = 0.50;
	
	private int attackCounter;
	
	public LaserRobot(Point position, int width, int height)
	{ 
		super(position, width, height, ACTION_DELAY);
		setFov(this.new FieldOfView(copyPosition(), FOV_WIDTH, FOV_HEIGHT));
		attackCounter = ATTACK_DURATION;
	}
	
	@Override
	public void update(GameContext context)
	{	
		if(isAttacking())
			return;
			
		Player player = context.getPlayer();
		Enemy.FieldOfView thisFov = getFov();
		
		double pThisFrame = 1.0 - Math.pow(1.0 - ATTACK_PROBABILITY, getDeltaTime());
		
		if(thisFov.isColliding(player) && Math.random() < pThisFrame)
		{
			context.getCurrentRoom().addEnemyAttack(produceAttack());
			setAttackingState(true);
			return;
		}
		
		Point thisPosition = getPosition();
		int thisX = thisPosition.getX(), thisY = thisPosition.getY();
		
		List<FixedObject> fixedObjects = context.getCurrentRoom().getFixedObjectList();
		List<GameObject> interestingGameObjects = fixedObjects.stream().map(f -> (GameObject)f).toList();
		
		double currentHorizontalVelocity = getHorizontalVelocity();
		Point currentPlayerPosition = player.copyPosition();
		
		if(thisFov.isColliding(player))
		{
			if(getPreviousPlayerPosition() == null)
				setPreviousPlayerPosition(currentPlayerPosition);
			
			int targetX = getTargetPosition(currentPlayerPosition).getX();
			setHorizontalVelocity(0);
			
			System.out.println(thisX + "/" + targetX);
			
			if(Math.abs(thisX - targetX) >= 3) 
				setHorizontalVelocity((thisX > targetX) ? -HORIZONTAL_SPEED : HORIZONTAL_SPEED);
		}
		else
		{
			setPreviousPlayerPosition(currentPlayerPosition);
			setRandomHorizontalVelocity();
		}
		
		if(isOnLedge(fixedObjects))
			setHorizontalVelocity(0);
		
		addGravity();
		
		applyHorizontalForce();
		resolveHorizontalCollision(interestingGameObjects);
		
		applyVerticalForce();
		resolveVerticalCollision(interestingGameObjects);
		
		if(getHorizontalVelocity() == 0)
		{
			setPhysicsState(MovingObject.PhysicsState.IDLE);
			setDirection(getPreviousDirection());
		}
		else 
		{
			setPhysicsState(MovingObject.PhysicsState.WALKING);
			setDirection((getHorizontalVelocity() > 0) ? MovingObject.Direction.RIGHT : MovingObject.Direction.LEFT);
		}
		
		setPreviousDirection(getDirection());
		
		if(currentHorizontalVelocity != 0)
		{
			thisFov.setX((currentHorizontalVelocity > 0) ? thisX : thisX - FOV_WIDTH + getWidth());
			thisFov.setY(thisY);
		}
	}
	
	@Override
	protected GameObject produceAttack()
	{
		Point thisPosition = getPosition();
		int thisX = thisPosition.getX();
		
		int attackX, attackY = thisPosition.getY();
		
		if(getHorizontalVelocity() == 0) 
			attackX = (getPreviousDirection() == MovingObject.Direction.RIGHT) ? thisX : thisX - ATTACK_WIDTH + getWidth();
		
		else 
			attackX = (getHorizontalVelocity() > 0 ) ? thisX :  thisX - ATTACK_WIDTH + getWidth();
		
		return new GameObject(new Point(attackX, attackY), ATTACK_WIDTH, ATTACK_HEIGHT) {
			@Override
			public void update(GameContext context) 
			{
				if(--attackCounter > 0)
					return;
				
				setAttackingState(false);
				attackCounter = ATTACK_DURATION;
				context.getCurrentRoom().removeEnemyAttack(this);
			} 
		};
	}
}
