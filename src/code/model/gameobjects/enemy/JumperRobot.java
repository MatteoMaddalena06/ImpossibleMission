package code.model.gameobjects.enemy;

import java.util.List;

import code.model.Point;
import code.model.context.GameContext;
import code.model.gameobjects.FixedObject;
import code.model.gameobjects.GameObject;
import code.model.gameobjects.MovingObject;
import code.model.gameobjects.Platform;
import code.model.room.RoomMap;

/** Classe che modella il robot che salta */
public class JumperRobot extends Enemy
{
	/** Velocità orizzontale del robot */
	private static final double    HORIZONTAL_SPEED = 200f;
	/** Velocità verticale del robot */
	private static final double    VERTICAL_SPEED   = 350f;
	/** Larghezza della FOV */
	private static final int       FOV_WIDTH        = 6 * RoomMap.TILE_SIZE;
	/** Altezza della FOV */
	private static final int       FOV_HEIGHT       = 5 * RoomMap.TILE_SIZE;
	/** Coordinata x originale della FOV */
	private final double 		   INITIAL_FOV_X    = getPosition().getX() - (FOV_WIDTH - getWidth())/2;
	/** Coordinata u originale della FOV */
	private final double 		   INITIAL_FOV_Y    = getPosition().getY() - (FOV_HEIGHT - getHeight()); 
	/** Quanto tempo aspettare prima di saltare */
	private static final double    JUMP_DELAY       = 0.7f;
	
	/** Il tempo passato dal salto */
	private double jumpInterval;
	
	/**
	 * Costruice la classe
	 * @param point
	 * la posizione originale
	 * @param width
	 * la larghezza
	 * @param height
	 * l'altezza
	 */
	public JumperRobot(Point point, int width, int height)
	{ 
		super(point, width, height);
		setFov(this.new FieldOfView(new Point(INITIAL_FOV_X, INITIAL_FOV_Y), FOV_WIDTH, FOV_HEIGHT));
		jumpInterval = JUMP_DELAY;
	}
	
	/**
	 * Aggiorna lo stato del robot saltando quando si avvicina
	 * @param context
	 * il contesto in cui operare
	 */
	@Override
	public void update(GameContext context)
	{
		if(context.isRobotsDisabled())
			return;
		
		Enemy.FieldOfView thisFov = getFov();
		List<GameObject> interestingGameObjects = 
				context.getCurrentRoom().getGameObjectList().stream().filter(g -> g instanceof FixedObject || g instanceof Platform).toList();
		
		if(thisFov.isColliding(context.getPlayer()))
		{
			if(isOnGround() && (jumpInterval -= GameContext.getDeltaTime()) <= 0) 
			{ setVerticalVelocity(-VERTICAL_SPEED); jumpInterval = JUMP_DELAY; }
			
			addGravity();
			applyVerticalForce();
			resolveVerticalCollision(interestingGameObjects);
			
			double playerX  = context.getPlayer().copyPosition().getX();
			double thisX = getPosition().getX();
			
			setDirection((thisX > playerX) ? MovingObject.Direction.LEFT : MovingObject.Direction.RIGHT);
			
			if(!isOnGround())
				setPhysicsState((getVerticalVelocity() < 0) ? MovingObject.PhysicsState.JUMPING : MovingObject.PhysicsState.FALLING);
			
			else 
				setPhysicsState(MovingObject.PhysicsState.IDLE);
		}
		else 
		{
			jumpInterval = 0;
			applyGroundMovement(context);
		}
		
		double thisX = getPosition().getX(), thisY = getPosition().getY();

		thisFov.setX(thisX - (FOV_WIDTH - getWidth())/2); 
		thisFov.setY(thisY - (FOV_HEIGHT - getHeight()));
	}
}
