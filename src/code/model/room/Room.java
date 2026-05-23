package code.model.room;

//data structure modules
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
//model import
import code.model.Point;
import code.model.gameobjects.FixedObject;
import code.model.gameobjects.Furniture;
import code.model.gameobjects.GameObject;
import code.model.gameobjects.Platform;
import code.model.gameobjects.enemy.AttackerRobot;
import code.model.gameobjects.enemy.Enemy;

/** Modella il concetto di stanza */
public class Room
{
	/** Lista dei gameobject presenti nella stanza */
	private List<GameObject> gameObjectList;
	/** Lista delle piattaforme presenti nella stanza */
	private List<Platform> platformList;
	/** Lista dei muri e pavimenti presenti nella stanza */
	private List<FixedObject> fixedObjectList;
	/** Lista dei mobili presenti nella stanza */
	private List<Furniture> furnitureList;
	/** Lista dei nemici presenti nella stanza */
	private List<Enemy> enemiesList;
	
	/** La posizione sinistra dove il giocatore deve comparire quando muore o entra nella stanza */
	private Point leftSpawnPosition;
	/** La posizione destra dove il giocatore deve comparire quando muore o entra nella stanza */
	private Point rightSpawnPosition;
	/** Il numero di piattaforme nella stanza */
	private int platformsNumber;
	/** Il colore della stanza */
	private Color color;
	/** Il tipo di uscita della stanza */
	private ExitLayout exitLayout;

	/** Dimensione orizzontale della stanza in pixel */
	private int width;
	/** Dimensione verticale della stanza in pixel */
	private int height;

	/** Enumerazione per i colori di una stanza */
	public enum Color 
	{ RED, PURPLE, GREEN };
	
	/** Enumerazione per i tipi di uscita di una stanza */
	public enum ExitLayout
	{ ONLEFT, ONRIGHT, ONLEFTANDRIGHT, NOEXIT }
	
	/**
	 * Costruisce la classe 
	 * @param gameObjectList
	 * la lista dei gameobjects nella stanza
	 * @param color
	 * il colore della stanza
	 * @param width
	 * la dimensione orizzontale in pixel della stanza
	 * @param height
	 * la dimensione verticale in pixel della stanza
	 */
	private Room(List<GameObject> gameObjectList, Color color, int width, int height)
	{ 
		this.gameObjectList = gameObjectList;
		platformList = gameObjectList.stream().filter(g -> g instanceof Platform).map(g -> (Platform)g).collect(Collectors.toList());
		fixedObjectList = gameObjectList.stream().filter(g -> g instanceof FixedObject).map(g -> (FixedObject)g).collect(Collectors.toList());
		furnitureList = gameObjectList.stream().filter(g -> g instanceof Furniture).map(g -> (Furniture)g).collect(Collectors.toList());
		enemiesList = gameObjectList.stream().filter(g -> g instanceof Enemy).map(g -> (Enemy)g).collect(Collectors.toList());
		platformsNumber = platformList.size();
		this.color = color; this.width = width; this.height = height;
	}
	
	/**
	 * Costruisce la stanza
	 **@param gameObjectList
	 * la lista dei gameobjects nella stanza
	 * @param color
	 * il colore della stanza
	 * @param exitLayout
	 * il tipo di uscita della stanza
	 * @param leftSpawnPosition
	 * la posizione sinistra dove il giocatore deve comparire quando muore o entra nella stanza
	 * @param rightSpawnPosition
	 * la posizione destra dove il giocatore deve comparire quando muore o entra nella stanza
	 */
	public Room(List<GameObject> gameObjectList, Color color, ExitLayout exitLayout, Point leftSpawnPosition, Point rightSpawnPosition)
	{ 
		this(gameObjectList, color, RoomMap.PIXELS_MAP_WIDTH, RoomMap.PIXELS_MAP_HEIGHT);
		this.exitLayout = exitLayout;
		this.leftSpawnPosition = leftSpawnPosition;
		this.rightSpawnPosition = rightSpawnPosition;
	}

	/**
	 * Unisce due stanze con la stessa dimensione orizzontale per crearne una più grande
	 * @param room
	 * la stanza da unire
	 * @return
	 * la stanza finale ottenuta dall'unione
	 */
	public Room merge(Room room)
	{
		if(room.color != color)
			throw new IllegalArgumentException("Unable to merge two rooms with different colors");
		
		if(room.width != width)
			throw new IllegalArgumentException("Unable to merge two rooms with different widths");
		
		int room1OtherGameObjectCount = gameObjectList.size() - fixedObjectList.size();
		int room2OtherGameObjectCount = room.gameObjectList.size() - room.fixedObjectList.size();
		
		if(room1OtherGameObjectCount !=0 || room2OtherGameObjectCount != 0)
			throw new IllegalArgumentException("It is possible to merge rooms with only fixed objects");
		
		List<GameObject> mergedGameObjectList = new ArrayList<GameObject>();
		
		fixedObjectList.forEach(f -> {
			mergedGameObjectList.add(new FixedObject(f, f.copyPosition()));
		});
		
		room.fixedObjectList.forEach(f -> {
			Point position = f.copyPosition();
			double newX = position.getX(), newY = position.getY() + height;
			
			mergedGameObjectList.add(new FixedObject(f, new Point(newX, newY)));
		});
		
		return new Room(mergedGameObjectList, color, width, height + room.height);
	}
	
	/** 
	 * Crea una copia della lista dei gameobject della stanza
	 * @return
	 * la copia della lista dei gameobject della stanza
	 */
	public List<GameObject> getGameObjectList()
	{ return new ArrayList<GameObject>(gameObjectList); }
	
	/** 
	 * Crea una copia della lista delle piattaforme della stanza
	 * @return
	 * la copia della lista delle piattaforme della stanza
	 */
	public List<Platform> getPlatformList()
	{ return new ArrayList<Platform>(platformList); }
	
	/** 
	 * Crea una copia della lista dei muri e dei pavimenti della stanza
	 * @return
	 * la copia della lista dei muri e dei pavimenti della stanza
	 */
	public List<FixedObject> getFixedObjectList()
	{ return new ArrayList<FixedObject>(fixedObjectList); }
	
	/** 
	 * Crea una copia della lista dei mobili della stanza
	 * @return
	 * la copia della lista dei mobili della stanza
	 */
	public List<Furniture> getFurnitureList()
	{ return new ArrayList<Furniture>(furnitureList); }
	
	/** 
	 * Crea una copia della lista dei nemici della stanza
	 * @return
	 * la copia della lista dei nemici della stanza
	 */
	public List<Enemy> getEnemiesList()
	{ return new ArrayList<Enemy>(enemiesList); }

	/**
	 * Rimuove un mobile dalla stanza
	 * @param object
	 * il mobile 
	 * @return
	 * true se e solo se è stata possibile la rimozione
	 */
	public boolean removeForniture(Furniture object)
	{ return furnitureList.remove(object) && gameObjectList.remove(object); }
	
	/**
	 * Aggiunge un attacco alla stanza
	 * @param attack
	 * l'attacco
	 * @return
	 * true se e solo se è stata possibile l'aggiunta
	 */
	public boolean addEnemyAttack(AttackerRobot.Attack attack)
	{ return gameObjectList.add(attack); }
	
	/**
	 * Rimuove un attacco dalla stanza
	 * @param object
	 * l'attacco
	 * @return
	 * true se e solo se è stata possibile la rimozione
	 */
	public boolean removeEnemyAttack(AttackerRobot.Attack attack)
	{ return gameObjectList.remove(attack); }
	
	/**
	 * Aggiunge una piattaforma alla stanza
	 * @param platform
	 * la piattaforma
	 * @return
	 * true se e solo se è stata possibile l'aggiunta
	 */
	public boolean addPlatform(Platform platform)
	{ 
		if(!gameObjectList.add(platform))
			return false;
		
		platformList.add(platform);
		platformsNumber++;
		
		return true;
	}
	
	/**
	 * Rimuove una piattaforma dalla stanza
	 * @param platform
	 * la piattaforma
	 * @return
	 * true se e solo se è stata posisbile la rimozione
	 */
	public boolean removePlatform(Platform platform)
	{ 
		if(!gameObjectList.remove(platform))
			return false;
		
		platformList.remove(platform);
		platformsNumber--;
		
		return true;
	}
	
	/**
	 * Imposta la posizione di spawn sinistra
	 * @param leftSpawnPosition
	 * la posizione di spawn sinistra
	 */
	public void setLeftSpawnPosition(Point leftSpawnPosition)
	{ this.leftSpawnPosition = leftSpawnPosition; }
	
	/**
	 * Imposta la posizione di spawn destra
	 * @param rightSpawnPosition
	 * la posizione di spawn destra
	 */
	public void setRightSpawnPosition(Point rightSpawnPosition)
	{ this.rightSpawnPosition = rightSpawnPosition; }
	
	/**
	 * Restituisce la posizione di spawn sinistra
	 * @return
	 * la posizione di spawn sinistra
	 */
	public Point getLeftSpawnPosition()
	{ return (leftSpawnPosition != null) ? new Point(leftSpawnPosition) : null; }
	
	/**
	 * Restituisce la posizione di spawn destra
	 * @return
	 * la posizione di spawn destra
	 */
	public Point getRightSpawnPosition()
	{ return (rightSpawnPosition != null) ? new Point(rightSpawnPosition) : null; }
	
	/**
	 * Restituisce il numero di piattaforme nella stanza
	 * @return
	 * il numero di piattaforme nella stanza
	 */
	public int getPlatformsNumber()
	{ return platformsNumber; }

	/**
	 * Restituisce il tipo di uscita della stanza
	 * @return
	 * il tipo di uscita della stanza
	 */
	public ExitLayout getExitLayout()
	{ return exitLayout; }
	
	/**
	 * Restituisce il colore della staza
	 * @return
	 * il colore della stanza
	 */
	public Color getColor()
	{ return color; }
	
	/**
	 * Restituisce la dimensione orizzontale della stanza
	 * @return
	 * la dimensione orizzontale della stanza
	 */
	public int getWidth()
	{ return width; }
	
	/**
	 * Restituisce la dimensione verticale della stanza
	 * @return
	 * la dimensione verticale della stanza
	 */
	public int getHeight()
	{ return height; }
}
