package model.gameobject.enemy;

//inproject import
import model.room.RoomMap;
import model.utils.GameContext;
import model.utils.Point;
import model.gameobject.MovingObject;

public class LaserRobot extends AttackerRobot
{
	private static final long serialVersionUID = 1L;
	
	private static final double HORIZONTAL_SPEED   = 200f;
	private static final int    FOV_WIDTH          = 6 * RoomMap.TILE_SIZE;
	private static final int    FOV_HEIGHT         = 3 * RoomMap.TILE_SIZE;
	private static final double ACTION_DELAY       = 0.01f;
	private static final int    ATTACK_WIDTH       = FOV_WIDTH;
	private static final int    ATTACK_HEIGHT      = FOV_HEIGHT;
	private static final int    ATTACK_DURATION    = 60; 
	private static final double ATTACK_PROBABILITY = 0.50;
	private static final double BOUND              = 3f;
	
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

		Enemy.FieldOfView thisFov = getFov();
		
		double pThisFrame = 1.0 - Math.pow(1.0 - ATTACK_PROBABILITY, getDeltaTime());
		
		if(thisFov.isColliding(context.getPlayer()) && Math.random() < pThisFrame)
		{
			context.getCurrentRoom() .addEnemyAttack(produceAttack());
			setAttackingState(true);
			return;
		}
		
		applyGroundMovement(context, HORIZONTAL_SPEED, BOUND);
		
		double currentHorizontalVelocity = getHorizontalVelocity();
		int thisX = getPosition().getX(), thisY = getPosition().getY();
		
		if(currentHorizontalVelocity != 0)
		{
			thisFov.setX((currentHorizontalVelocity > 0) ? thisX : thisX - FOV_WIDTH + getWidth());
			thisFov.setY(thisY);
		}
	}
	
	@Override
	protected Attack produceAttack()
	{
		Point thisPosition = getPosition();
		int thisX = thisPosition.getX();
		
		int attackX, attackY = thisPosition.getY();
		
		if(getHorizontalVelocity() == 0) 
			attackX = (getDirection() == MovingObject.Direction.RIGHT) ? thisX : thisX - ATTACK_WIDTH + getWidth();
		
		else 
			attackX = (getHorizontalVelocity() > 0 ) ? thisX :  thisX - ATTACK_WIDTH + getWidth();
		
		return new Attack(new Point(attackX, attackY), ATTACK_WIDTH, ATTACK_HEIGHT) {
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
