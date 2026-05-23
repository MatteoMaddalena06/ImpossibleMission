package code.model.gameobjects;

//data structure module
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.Comparator;
//inproject import
import code.model.room.Room;
import code.event.EventDispatcher;
import code.model.context.GameContext;
import code.model.context.PlayerOnPlatform;

/** Classe che modella una colonna di piattaforme */
public class PlatformCluster 
{
	/** Velocità verticale delle piattaforma nel cluster */
	private static final double VERTICAL_SPEED = 300f;
	/** Limite oltre il quale la piattaforma viene considerata arrivata a destinazione */
	private static final int 	BOUND = 4;
	
	/** Lista delle piattaforme nel cluster */
	private List<Platform> platforms;
	/** Piattaforma più in alto nel cluster */
	Platform upperPlatform;
	/** Piattaforma più in basso nel cluster */
	Platform bottomPlatform;
	
	/** Larghezza del cluster */
	private int width;
	/** Indica se il cluster è in movimento */
	private boolean isMoving;
	/** Indica la direzione in cui si sta muovendo il cluster (valido solo se {@link isMoving} == true) */
	private Direction direction;
	/** Indica se è il primo movimento del cluster */
	private boolean firstTime;
	
	/** Enumerazione per i movimenti consentiti ai {@link PlatformCluster} */
	private enum Direction
	{ UP, DOWN }
	
	/**
	 * Costruisce la classe
	 * @param platforms
	 * le piattaforme appartenenti al cluster
	 */
	private PlatformCluster(List<Platform> platforms)
	{
		this.platforms = platforms;
		upperPlatform = platforms.stream().min(Comparator.comparingDouble(p -> p.getPosition().getY())).get();
		bottomPlatform = platforms.stream().max(Comparator.comparingDouble(p -> p.getPosition().getY())).get();
		width = platforms.getFirst().getWidth();
		isMoving = false;
		firstTime = true;
	}

	/**
	 * Aggiorna lo stato del cluster muovendolo quando necessario nella direzione desiderata dal giocatore
	 * @param context
	 * il contesto di gioco su cui operare
	 */
	public void update(GameContext context)
	{
		if(context.getPlatformsToReset() != 0)
			return;
		
		Player player = context.getPlayer();
		boolean playerPressedUp = context.getUserInput(GameContext.UserInput.UP);
		boolean playerPressedDown = context.getUserInput(GameContext.UserInput.DOWN);
		
		Platform playerUsedPlatform = platforms.stream().filter(p -> player.isStandingOnTopOf(p)).findFirst().orElse(null);
		boolean playerOnPlatform = playerUsedPlatform != null;

		if(!isMoving && (!playerPressedUp && !playerPressedDown || !playerOnPlatform))
		{
			if(platforms.contains(player.getUsedPlatform()))
				player.setOnPlatformState(false);
			
			firstTime = true;
			
			return;
		}
		
		if(firstTime)
			EventDispatcher.notify(new PlayerOnPlatform());
		
		firstTime = false;
			
		player.setOnPlatformState(true);
		player.setUsedPlatform(playerUsedPlatform);
		
		if(!isMoving && playerPressedUp) direction = Direction.UP;
		if(!isMoving && playerPressedDown) direction = Direction.DOWN;
		
		Room currentRoom = context.getCurrentRoom();
		List<FixedObject> floorList = currentRoom.getFixedObjectList().stream().filter(f -> f.getType() == FixedObject.Type.FLOOR).toList();
		
		Map<Platform, FixedObject> destinationFloors = getDestinations(floorList);
		boolean reachedDestination = destinationFloors.size() != 0;
		
		if(direction == Direction.UP)
		{
			boolean canGoUp = canGoUp(floorList);
			isMoving = canGoUp && (!reachedDestination || playerPressedUp);
			platforms.forEach(p -> p.setVerticalVelocity((isMoving) ? -VERTICAL_SPEED : 0));
		}
		else
		{
			boolean canGoDown = canGoDown(floorList);
			isMoving = canGoDown && (!reachedDestination || playerPressedDown);
			platforms.forEach(p -> p.setVerticalVelocity((isMoving) ? VERTICAL_SPEED : 0));
		}

		if(!reachedDestination || isMoving) platforms.forEach(p -> p.applyVerticalForce());
		else destinationFloors.forEach((p, f) -> p.getPosition().setY(f.getPosition().getY()));
	}
	
	/**
	 * Controlla se il cluseter può muoversi in alto
	 * @param floorList
	 * la lista di pavimenti presenti nella stanza
	 * @return
	 * true se e solo se il cluster può muoversi in alto
	 */
	private boolean canGoUp(List<FixedObject> floorList)
	{
		double upperPlatformX = upperPlatform.getPosition().getX(), upperPlatformY = upperPlatform.getPosition().getY();
		
		return floorList.stream().anyMatch(f ->
			f.getPosition().getY() < upperPlatformY && (f.getPosition().getX() == upperPlatformX + width || f.getPosition().getX() + f.getWidth() == upperPlatformX)
		);	
	}
	
	/**
	 * Controlla se il cluseter può muoversi in basso
	 * @param floorList
	 * la lista di pavimenti presenti nella stanza
	 * @return
	 * true se e solo se il cluster può muoversi in basso
	 */
	private boolean canGoDown(List<FixedObject> floorList)
	{
		double bottomPlatformX = bottomPlatform.getPosition().getX(), bottomPlatformY = bottomPlatform.getPosition().getY();
			
		return floorList.stream().anyMatch(f -> 
			f.getPosition().getY() > bottomPlatformY && (f.getPosition().getX() == bottomPlatformX + width || f.getPosition().getX() + f.getWidth() == bottomPlatformX)
		);	
	}
	
	/**
	 * Restituisce le destinazioni verso le quali si stanno muovendo le piattaforme del cluster
	 * @param floorList
	 * la lista di pavimenti presenti nella stanza
	 * @return
	 * per ogni platform il suo pavimento di destinazione 
	 */
	private Map<Platform, FixedObject> getDestinations(List<FixedObject> floorList)
	{
		return platforms.stream().map(p -> {
			double platformX = p.getPosition().getX(), platformY = p.getPosition().getY(), platformWidth = p.getWidth();
			
			FixedObject wantedFloor = floorList.stream().filter(f -> {	
				double floorX = f.getPosition().getX(), floorY = f.getPosition().getY(), floorWidth = f.getWidth();
				return (floorX + floorWidth == platformX || floorX == platformX + platformWidth) && Math.abs(floorY - platformY) <= BOUND;
				
			}).findFirst().orElse(null);
			
			return (wantedFloor != null) ? Map.entry(p, wantedFloor) : null;
			
		}).filter(Objects::nonNull).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
	
	/** 
	 * Restituisce la lista dei cluster presenti nella stanza specificata
	 * @param room
	 * la stanza 
	 * @return
	 * la lista dei cluster presenti nella stanza
	 */
	public static List<PlatformCluster> getPlatformClusters(Room room)
	{ 
		return room.getPlatformList().stream().collect(Collectors.groupingBy(p -> p.getPosition().getX()))
				.values().stream().map(pl -> new PlatformCluster(pl)).toList();
	}
}
