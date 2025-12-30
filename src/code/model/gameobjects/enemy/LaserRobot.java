package code.model.gameobjects.enemy;

//inproject import
import code.model.room.RoomMap;
import code.model.Point;
import code.model.context.AttackEnded;
import code.model.context.AttackLaunched;
import code.model.context.GameContext;
import code.model.gameobjects.MovingObject;

public class LaserRobot extends AttackerRobot
{
	private static final long serialVersionUID = 1L;
	
	private static final double HORIZONTAL_SPEED   = 150f;
	private static final int    FOV_WIDTH          = 6 * RoomMap.TILE_SIZE;
	private static final int    FOV_HEIGHT         = 3 * RoomMap.TILE_SIZE;
	private static final int    ATTACK_WIDTH       = FOV_WIDTH;
	private static final int    ATTACK_HEIGHT      = FOV_HEIGHT;
	private static final double ATTACK_DURATION    = 2f; 
	
	private double attackDuration;
	
	public LaserRobot(Point position, int width, int height)
	{ 
		super(position, width, height);
		setFov(this.new FieldOfView(copyPosition(), FOV_WIDTH, FOV_HEIGHT));
		attackDuration = ATTACK_DURATION;
	}
	
	@Override
	public void update(GameContext context)
	{	
		if(isAttacking() || context.isRobotsDisabled())
			return;

		Enemy.FieldOfView thisFov = getFov();
		
		if(thisFov.isColliding(context.getPlayer()))
		{
			Attack attack = produceAttack();
			context.getCurrentRoom().addEnemyAttack(attack);
			context.getListener().notifyEvent(new AttackLaunched(attack));
			setAttackingState(true);
			return;
		}
		
		applyGroundMovement(context, HORIZONTAL_SPEED);
		
		double currentHorizontalVelocity = getHorizontalVelocity();
		double thisX = getPosition().getX(), thisY = getPosition().getY();
		
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
		double thisX = thisPosition.getX();
		
		double attackX, attackY = thisPosition.getY();
		
		if(getHorizontalVelocity() == 0) 
			attackX = (getDirection() == MovingObject.Direction.RIGHT) ? thisX : thisX - ATTACK_WIDTH + getWidth();
		
		else 
			attackX = (getHorizontalVelocity() > 0 ) ? thisX :  thisX - ATTACK_WIDTH + getWidth();
		
		return new Attack(new Point(attackX, attackY), ATTACK_WIDTH, ATTACK_HEIGHT) {
			@Override
			public void update(GameContext context) 
			{
				if((attackDuration -= GameContext.getDeltaTime()) > 0)
					return;
				
				setAttackingState(false);
				attackDuration = ATTACK_DURATION;
				context.getCurrentRoom().removeEnemyAttack(this);
				context.getListener().notifyEvent(new AttackEnded(this));
			} 
		};
	}
}
