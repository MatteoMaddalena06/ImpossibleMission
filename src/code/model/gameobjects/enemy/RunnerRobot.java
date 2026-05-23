package code.model.gameobjects.enemy;

//data structure import
import java.util.List;
//model import
import code.model.Point;
import code.model.context.GameContext;
import code.model.context.RunnerStartRunning;
import code.model.context.RunnerStopRunning;
import code.model.gameobjects.FixedObject;
import code.model.gameobjects.GameObject;
import code.model.gameobjects.MovingObject;
import code.model.gameobjects.Player;
//inproject import
import code.model.room.RoomMap;
//event import
import code.event.EventDispatcher;

/** Classe per i robot che corronno */
public class RunnerRobot extends Enemy
{	
	/** Velocità orizzontale del robot */
	private static final double    HORIZONTAL_SPEED = 350f;
	/** Larghezza della FOV */
	private static final int       FOV_WIDTH        = 16 * RoomMap.TILE_SIZE;
	/** Altezza della FOV */
	private static final int  	   FOV_HEIGHT       = 4 * RoomMap.TILE_SIZE;
	/** Coordinata x iniziale della FOV */
	private transient final double INITIAL_FOV_X    = getPosition().getX() - (FOV_WIDTH - getWidth())/2;
	/** Coordinate y iniziale della FOV */
	private transient final double INITIAL_FOV_Y    = getPosition().getY() - (FOV_HEIGHT - getHeight());
	/** Quanto tempo aspettare prima di correre contro il giocatore */
	private static final double    ACTION_DELAY     = 0.5f;

	/** Quanto tempo aspettare prima di correre contro il giocatore */
	private double actionDelay;
	/** Indica se è la prima che il giocatore entra nella FOV del robot */
	private boolean firstTimeColliding;
	
	/**
	 * Costruice la classe
	 * @param point
	 * la posizione originale
	 * @param width
	 * la larghezza
	 * @param height
	 * l'altezza
	 */
	public RunnerRobot(Point point, int width, int height)
	{ 
		super(point, width, height);
		setFov(this.new FieldOfView(new Point(INITIAL_FOV_X, INITIAL_FOV_Y), FOV_WIDTH, FOV_HEIGHT));
		actionDelay = ACTION_DELAY;
		firstTimeColliding = true;
	}

	/** 
	 * Aggiorna lo stato del robot attaccando il player quando si avvicina
	 * @param context 
	 * il contesto di gioco in cui operare
	 */
	@Override
	public void update(GameContext context) 
	{
		if(context.isRobotsDisabled())
			return;
		
		Player player = context.getPlayer();
		double playerX = player.copyPosition().getX();
		double thisX = getPosition().getX(), thisY = getPosition().getY();
		int thisWidth = getWidth();
		
		Enemy.FieldOfView thisFov = getFov();
		
		if(!player.isOnGround() && playerX >= thisX && playerX <= thisX + thisWidth)
			actionDelay = ACTION_DELAY / 2;
		
		if(thisFov.isColliding(player) && (actionDelay -= GameContext.getDeltaTime()) <= 0)
		{
			List<FixedObject> fixedObjects = context.getCurrentRoom().getFixedObjectList();
			List<GameObject> interestingObjects = fixedObjects.stream().map(f -> (GameObject)f).toList();
			
			setHorizontalVelocity((thisX > playerX) ? -HORIZONTAL_SPEED : HORIZONTAL_SPEED);
			
			if(isOnLedge(fixedObjects))
				setHorizontalVelocity(0);
			
			applyHorizontalForce();
			resolveHorizontalCollision(interestingObjects);
			
			double currentHorizontalVelocity = getHorizontalVelocity();
			
			if(currentHorizontalVelocity != 0)
			{
				setPhysicsState(MovingObject.PhysicsState.WALKING);
				setDirection((currentHorizontalVelocity > 0) ? MovingObject.Direction.RIGHT : MovingObject.Direction.LEFT);
			}
			else 
				setPhysicsState(MovingObject.PhysicsState.IDLE);
		}	
		else if(!thisFov.isColliding(player))
		{ applyGroundMovement(context); actionDelay = ACTION_DELAY; }

		if(firstTimeColliding && thisFov.isColliding(player))
		{ EventDispatcher.notify(new RunnerStartRunning(this)); firstTimeColliding = false; }
		
		if(!firstTimeColliding && !thisFov.isColliding(player))
		{ EventDispatcher.notify(new RunnerStopRunning(this)); firstTimeColliding = true; }

		thisFov.setX(thisX - (FOV_WIDTH - getWidth())/2); 
		thisFov.setY(thisY - (FOV_HEIGHT - getHeight()));
	}
}
