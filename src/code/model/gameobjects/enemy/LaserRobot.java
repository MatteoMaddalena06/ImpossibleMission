package code.model.gameobjects.enemy;

//inproject import
import code.model.room.RoomMap;
import code.model.Point;
import code.model.context.AttackEnded;
import code.model.context.AttackLaunched;
import code.model.context.GameContext;
import code.model.gameobjects.MovingObject;
//event import
import code.event.EventDispatcher;

/** Classe che modella i robot che sparano laser */
public class LaserRobot extends AttackerRobot
{
	/** Velocità orizzontale del robot */
	private static final double HORIZONTAL_SPEED   = 150f;
	/** Larghezza della FOV */
	private static final int    FOV_WIDTH          = 3 * RoomMap.TILE_SIZE;
	/** Altezza della FOV */
	private static final int    FOV_HEIGHT         = 3 * RoomMap.TILE_SIZE;
	/** Larghezza dell'attacco */
	private static final int    ATTACK_WIDTH       = FOV_WIDTH;
	/** Altezza dell'attacco */
	private static final int    ATTACK_HEIGHT      = FOV_HEIGHT;
	/** Durata dell'attacco*/
	private static final double ATTACK_DURATION    = 3f; 
	
	/** Per quanto ancora attaccare */
	private double attackDuration;
	
	/**
	 * Costruisce la classe
	 * @param position
	 * la posizione originale
	 * @param width
	 * la larghezza
	 * @param height
	 * l'altezza
	 */
	public LaserRobot(Point position, int width, int height)
	{ 
		super(position, width, height);
		setFov(this.new FieldOfView(copyPosition(), FOV_WIDTH, FOV_HEIGHT));
		attackDuration = ATTACK_DURATION;
	}
	
	/**
	 * Aggiorna lo stato del robot attaccando il giocatore quando si avvicina
	 * @param context 
	 * il contesto in cui operare
	 */
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
			EventDispatcher.notify(new AttackLaunched(attack));
			setAttackingState(true);
			return;
		}
		
		applyGroundMovement(context);
		
		double currentHorizontalVelocity = getHorizontalVelocity();
		double thisX = getPosition().getX(), thisY = getPosition().getY();
		
		if(currentHorizontalVelocity != 0)
		{
			thisFov.setX((currentHorizontalVelocity > 0) ? thisX : thisX - FOV_WIDTH + getWidth());
			thisFov.setY(thisY - FOV_HEIGHT + getHeight());
		}
	}
	
	/** Produce l'attacco */
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
		
		return new Attack(Attack.Type.PROLONGED, new Point(attackX, attackY), ATTACK_WIDTH, ATTACK_HEIGHT) {
			@Override
			public void update(GameContext context) 
			{
				if((attackDuration -= GameContext.getDeltaTime()) > 0)
					return;
				
				setAttackingState(false);
				attackDuration = ATTACK_DURATION;
				context.getCurrentRoom().removeEnemyAttack(this);
				EventDispatcher.notify(new AttackEnded(this));
			} 
		};
	}
}
