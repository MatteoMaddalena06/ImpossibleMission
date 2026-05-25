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
//event import
import code.event.EventDispatcher;

/** Classe che modella il giocatore del gioco */
public class Player extends MovingObject
{	
	/** La dimensione orizzontale normale del giocatore */
	private static final int    NORMAL_WIDTH     = 2 * RoomMap.TILE_SIZE;
	/** La dimensione verticale normale del giocatore */
	private static final int    NORMAL_HEIGHT    = 3 * RoomMap.TILE_SIZE;
	/** La dimensione orizzontale della hitbox del giocatore quando salta */
	private static final int    JUMP_WIDTH       = NORMAL_WIDTH;
	/** La dimensione verticale della hitbox del giocatore quando salta */
	private static final int    JUMP_HEIGHT      = 1 * RoomMap.TILE_SIZE;
	/** La velocità orizzontale del giocatore */
	private static final double HORIZONTAL_SPEED = 300f;
	/** Di quanto aumentare la velocità orizzontale quando il giocatore salta */
	private static final double JUMP_INCREASE    = 50f;
	/** La velocità verticale del giocatre */
	private static final double VERTICAL_SPEED   = 600f;
	/** La tolleranza usata per capire se il giocatore si trova su una piattaforma */
	protected static final int  STANDING_TOLLERANCE = 2; 
	
	/** Quante vite ha il giocatore */
	private static final int  PLAYER_FULL_LIFES = 3;
	/** Quanto tempo aspettare per considerare il giocatore morto*/
	private static final long DIE_WAITING = 1500000000L;
	/** I punti tolti al giocatore quando muore */
	private static final int  DIE_PENALITY = 350;
	
	/** La coordinata x del player nella stanza iniziale quando inizia la partita */
	public static final int START_GAME_SPAWN_X = RoomMap.PIXELS_MAP_WIDTH / 2;
	/** La coordinata y del player nella stanza iniziale quando inizia la partita */
	public static final int START_GAME_SPAWN_Y = RoomMap.PIXELS_MAP_HEIGHT - 200;
	/** La coordinata x nella mappa di gioco all'inizio della partita */
	public static final int START_GAME_WORLD_X = 1;
	/** La coordinata y nella mappa di gioco all'inizio della partita */
	public static final int START_GAME_WORLD_Y = 0;
	
	/** Il punto dove il player deve comparire nella mappa quando muore*/
	private Point spawnPosition;
	/** La posizione del player nella mappa di gioco */
	private Point worldPosition;
	
	/** Le vite rimaste al giocatore */
	private int lifes;
	/** Il nome del giocatore */
	private String name;
	/** I punti posseduti dal giocatore */
	private int points;
	
	/** La lista dei {@link PuzzlePiece} ottenuti */
	private List<PuzzlePiece> puzzlePiecesObtained;
	/** Il numero di password per disattivare i robot ottenute */
	private int robotPasswordsObtained;
	/** Il numero di password per ripristinare le piattaforme ottenute */
	private int platformPasswordsObtained;
	
	/** Indica se il giocatore è su una piattaforma */
	private boolean isOnPlatform;
	/** Indica la piattaforma usata dal giocatoe (valido solo se {@link isOnPlatform} == true) */
	private Platform usedPlatform;
	
	/** Indica se il giocatore è su un ascensore */
	private boolean isOnElevator;
	/** Indica se il giocatore è morto */
	private boolean isDead;
	/** Indica se il giocatore sta cercando in un mobile */
	private boolean isSearching;
	/** Indica il mobile in cui il giocatore sta cercando (valido solo se {@link isSearching} == true) */
	private Furniture usedFurniture;
	
	/** Indica se la hitbox del player è stat modificata */
	private boolean wasHitboxModified;
		
	/**
	 * Costruisce la classe 
	 * @param name
	 * il nome del giocatore
	 * @param spawnPosition
	 * il punto dove il player deve comparire nella mappa quando muore e anche le sue coordinate iniziali
	 * @param worldPosition
	 * la posizione del player nella mappa di gioco
	 */
	public Player(String name, Point spawnPosition, Point worldPosition)
	{
		super(spawnPosition, NORMAL_WIDTH, NORMAL_HEIGHT); 
		lifes = PLAYER_FULL_LIFES;
		this.name = name;
		points = 0;
		puzzlePiecesObtained = new ArrayList<PuzzlePiece>();
		robotPasswordsObtained = platformPasswordsObtained = 0;
		setPhysicsState(MovingObject.PhysicsState.IDLE);
		wasHitboxModified = isSearching = isOnPlatform  = false;
		isOnElevator = true;
		this.spawnPosition = new Point(spawnPosition);
		this.worldPosition = new Point(worldPosition);
	}

	/**
	 * Aggiorna lo stato del giocatore
	 * @param context 
	 * il contesto di gioco da utilizzare
	 */
	@Override
	public void update(GameContext context) 
	{	
		if(isDead)
		{		
			setPosition(new Point(spawnPosition));
			isDead = false;
		}
		
		Room currentRoom = context.getCurrentRoom();
		List<GameObject> interestingGameObjects = 
				currentRoom.getGameObjectList().stream().filter(g -> g instanceof FixedObject || g instanceof Platform).toList();
	
		if(isOnPlatform)
		{
			setPhysicsState(MovingObject.PhysicsState.IDLE);
			getPosition().setY(usedPlatform.getPosition().getY() - getHeight());
			expandHitbox();
			return; 
		}

		setHorizontalVelocity(0);
		
		if(context.getUserInput(GameContext.UserInput.LEFT) && !isSearching)
			setHorizontalVelocity(-HORIZONTAL_SPEED - ((!isOnGround()) ? JUMP_INCREASE : 0));
		
		if(context.getUserInput(GameContext.UserInput.RIGHT) && !isSearching) 
			setHorizontalVelocity(HORIZONTAL_SPEED + ((!isOnGround()) ? JUMP_INCREASE : 0));
		
		if(context.getUserInput(GameContext.UserInput.JUMP) && isOnGround() && !isSearching)
		{ setVerticalVelocity(-VERTICAL_SPEED); shrinkHitbox(); } 	

		applyHorizontalForce();
		resolveHorizontalCollision(interestingGameObjects);
		
		if(getPosition().getX() < RoomMap.PIXELS_MAP_WIDTH && getPosition().getX() >= 0)
		{ addGravity(); applyVerticalForce(); }

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
		
		if(isOnGround() && wasHitboxModified) expandHitbox();
		
		if(getPosition().getX() >= RoomMap.PIXELS_MAP_WIDTH)
		{
			worldPosition.setX(worldPosition.getX() + 1);
			spawnPosition = context.getCurrentRoom().getLeftSpawnPosition();
			setPosition(new Point(spawnPosition));
			isOnElevator = !isOnElevator;
		}
		else if(getPosition().getX() < 0)
		{
			worldPosition.setX(worldPosition.getX() - 1);
			spawnPosition = context.getCurrentRoom().getRightSpawnPosition();
			setPosition(new Point(spawnPosition));
			isOnElevator = !isOnElevator;
		}
		
		if(isOnElevator && getPosition().getY() >= (worldPosition.getY() + 1) * RoomMap.PIXELS_MAP_HEIGHT)
			worldPosition.setY(worldPosition.getY() + 1);
		
		else if(isOnElevator && getPosition().getY() <= worldPosition.getY() * RoomMap.PIXELS_MAP_HEIGHT)
			worldPosition.setY(worldPosition.getY() - 1);
		
		boolean isCollidingWithEnemy = currentRoom.getEnemiesList().stream().anyMatch(g -> isColliding(g));
		boolean isCollidingWithAttack = currentRoom.getGameObjectList().stream().filter(g -> g instanceof AttackerRobot.Attack).anyMatch(a -> isColliding(a));
		
		if((isCollidingWithEnemy || isCollidingWithAttack) && !context.isRobotsDisabled() || (getPosition().getY() >= RoomMap.PIXELS_MAP_HEIGHT && !isOnElevator))
		{
			isDead = true;
			points = Math.max(0, points - DIE_PENALITY);
			EventDispatcher.notify(new PlayerDied(DIE_WAITING));
			
			context.resetPlatforms();
			
			if(--lifes == 0)
				EventDispatcher.notify(new GameWillEnd(DIE_WAITING));
		}
	}
	
	/** Rimpicciolisce la hitbox del giocatore */
	private void shrinkHitbox() 
	{
	    changeHitbox(JUMP_WIDTH, JUMP_HEIGHT);
	    wasHitboxModified = true;
	}
	
	/** Espande la hitbox del giocatre */
	private void expandHitbox() 
	{
		changeHitbox(NORMAL_WIDTH, NORMAL_HEIGHT);
	    wasHitboxModified = false;
	}
	
	/**
	 * Modifica la dimensione della hitbox del giocatore
	 * @param newWidth
	 * nuova dimensione orizzontale
	 * @param newHeight
	 * nuova dimensione verticale
	 */
	private void changeHitbox(int newWidth, int newHeight)
	{
		Point thisPosition = getPosition();
		int thisWidth = getWidth(), thisHeight = getHeight();
	    thisPosition.setX(thisPosition.getX() - (newWidth - thisWidth) / 2);
	    thisPosition.setY(thisPosition.getY() - (newHeight - thisHeight));
	    setWidth(newWidth); setHeight(newHeight);
	}
	
	/**
	 * Controlla se il giocatore è sopra una piattaforma
	 * @param obj
	 * la piatafforma
	 * @return
	 * true se e solo se il giocatore è su una piattaforma
	 */
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
	
	/**
	 * Restituisce il nome del giocatore
	 * @return
	 * il nome del giocatore
	 */
	public String getName()
	{ return name; }
	
	/**
	 * Restituisce le vite del giocatore
	 * @return
	 * le vite del giocatore
	 */
	public int getLifes()
	{ return lifes; }
	
	/**
	 * Restituisce i punti ottenuti dal giocatore
	 * @return
	 * i punti ottenuti dal giocatore
	 */
	public int getPoints()
	{ return points; }
	
	/**
	 * Aggiorna i punti ottenuti dal giocaotre
	 * @param amount
	 * quanti punti aggiungere
	 */
	public void updatePoints(int amount)
	{ points += amount; }
	
	/**
	 * Da al giocatore un {@link PuzzlePiece}
	 * @param piece
	 * il {@link PuzzlePiece}
	 */
	public void givePuzzlePiece(PuzzlePiece piece)
	{ puzzlePiecesObtained.add(piece); }
	
	/**
	 * Restituisce la lista di {@link PuzzlePiece} ottenuti
	 * @param piece
	 * la lista di {@link PuzzlePiece} ottenuti
	 */
	public List<PuzzlePiece> getPuzzlePiecesObtained()
	{ return new ArrayList<PuzzlePiece>(puzzlePiecesObtained); }
	
	/** Da al giocatore una password per disattivare i robot */
	public void giveRobotPassword()
	{ robotPasswordsObtained++; }
	
	/** Da al giocatore una password per ripristinare le piattaforma */
	public void givePlatformPassword()
	{ platformPasswordsObtained++; }
	
	/** Fa usare al giocaotre una password per disattivare i robot */
	public boolean useRobotPassword()
	{ return (robotPasswordsObtained == 0) ? false : robotPasswordsObtained-- >= 0; }
	
	/** Fa usare al giocatore una password per ripristinare le piattaforme */
	public boolean usePlatoformPassword()
	{ return (platformPasswordsObtained == 0) ? false : platformPasswordsObtained-- >= 0; }
	
	/** 
	 * Restituisce il numero di password per disattivare i robot ottenute
	 * @return
	 * il numero di password per disattivare i robot ottenute
	 */
	public int getRobotPasswordsObtained()
	{ return robotPasswordsObtained; }
	
	/** 
	 * Restituisce il numero di password per rispristinare le piattaforme ottenute
	 * @return
	 * il numero di password per rispristinare le piattaforme ottenute
	 */
	public int getPlatformPasswordsObtained()
	{ return platformPasswordsObtained; }
	
	/**
	 * Imposta il flag {@link isOnPlatform}
	 * @param isOnPlatform
	 * lo stato da impostare
	 */
	public void setOnPlatformState(boolean isOnPlatform)
	{ this.isOnPlatform = isOnPlatform; }
	
	/**
	 * Dice se il giocatore è su una piattaforma
	 * @return
	 * lo stato del flag {@link isOnPlatform}
	 */
	public boolean isOnPlatform()
	{ return isOnPlatform; }
	
	/** 
	 * Imposta la piattaforma usata dal giocatore
	 * @param platform
	 * la piattaforma 
	 */
	public void setUsedPlatform(Platform platform)
	{ usedPlatform = platform; }
	
	/**
	 * Restituisce la piattaforma usata dal giocatore
	 * @return
	 * la piattaforma usata dal giocatore
	 */
	public Platform getUsedPlatform()
	{ return usedPlatform; }
	
	/** 
	 * Imposta lo stato del flag {@link isSearching}
	 * @param isSearching
	 * lo stato del flag {@link isSearching}
	 */
	public void setSearchingState(boolean isSearching)
	{ this.isSearching = isSearching; }
	
	/**
	 * Dice se il giocatore è in un ascensore 
	 * @return
	 * il flag {@link isOnElevator}
	 */
	public boolean isOnElevator()
	{ return isOnElevator; }
	
	/**
	 * Dice se il giocatore è morto
	 * @return
	 * lo stato del flag {@link isDead}
	 */
	public boolean isDead()
	{ return isDead; }
	
	/**
	 * Dice se il giocaotre sta cercando in un mobile 
	 * @return
	 * lo stato del flag {@link isSearching}
	 */
	public boolean isSearching()
	{ return isSearching; }
	
	/**
	 * Imposta il mobile in cui sta cercando il giocatore
	 * @param furniture
	 * il mobile
	 */
	public void setUsedFurniture(Furniture furniture)
	{ usedFurniture = furniture; }
	
	/**
	 * Restituisce il mobile in cui sta cercando il giocatore
	 * @return
	 * il mobile in cui sta cercando il giocatore
	 */
	public Furniture getUsedFurniture()
	{ return usedFurniture; }
	
	/**
	 * Imposta la posizione del giocatore di spawn nella stanza 
	 * @param spawnPosition
	 * la posizione di spawn
	 */
	public void setSpawnPosition(Point spawnPosition)
	{ this.spawnPosition = spawnPosition; }
	
	/** 
	 * Imposta la posizione del giocatore nella mappa di gioco
	 * @param worldPosition
	 * la posizione nella mappa
	 */
	public void setWorldPosition(Point worldPosition)
	{ this.worldPosition = worldPosition; }
	
	/**
	 * Restituisce la posizione del giocatore nella mappa di gioco
	 * @return
	 * la posizione del giocatore nella mappa di gioco
	 */
	public Point getWorldPosition()
	{ return worldPosition; }
}
