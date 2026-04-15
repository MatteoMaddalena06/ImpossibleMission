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

public class Room
{
	private List<GameObject> gameObjectList;
	private List<Platform> platformList;
	private List<FixedObject> fixedObjectList;
	private List<Furniture> furnitureList;
	private List<Enemy> enemiesList;
	
	private int platformsNumber;
	private Color color;
	
	private int width;
	private int height;

	public enum Color 
	{ RED, PURPLE, GREEN, ANY };
	
	public enum ExitLayout
	{ ONLEFT, ONRIGHT, ONLEFTANDRIGHT, NOEXIT }
	
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
	
	public Room(List<GameObject> gameObjectList, Color color)
	{ this(gameObjectList, color, RoomMap.PIXELS_MAP_WIDTH, RoomMap.PIXELS_MAP_HEIGHT); }
	
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
	
	public List<GameObject> getGameObjectList()
	{ return new ArrayList<GameObject>(gameObjectList); }
	
	public List<Platform> getPlatformList()
	{ return new ArrayList<Platform>(platformList); }
	
	public List<FixedObject> getFixedObjectList()
	{ return new ArrayList<FixedObject>(fixedObjectList); }
	
	public List<Furniture> getFurnitureList()
	{ return new ArrayList<Furniture>(furnitureList); }
	
	public List<Enemy> getEnemiesList()
	{ return new ArrayList<Enemy>(enemiesList); }

	public boolean removeForniture(Furniture object)
	{ return furnitureList.remove(object) && gameObjectList.remove(object); }
	
	public void addEnemyAttack(AttackerRobot.Attack attack)
	{ gameObjectList.add(attack); }
	
	public boolean removeEnemyAttack(AttackerRobot.Attack attack)
	{ return gameObjectList.remove(attack); }
	
	public boolean addPlatform(Platform platform)
	{ 
		if(!gameObjectList.add(platform))
			return false;
		
		platformList.add(platform);
		platformsNumber++;
		
		return true;
	}
	
	public boolean removePlatform(Platform platform)
	{ 
		if(!gameObjectList.remove(platform))
			return false;
		
		platformList.remove(platform);
		platformsNumber--;
		
		return true;
	}
	
	public int getPlatformsNumber()
	{ return platformsNumber; }
	
	public void setColor(Color color)
	{
		if(this.color != Color.ANY)
			throw new IllegalArgumentException("Cannot set a color for an object with no color");
		
		this.color = color;
	}
	
	public Color getColor()
	{ return color; }
	
	public int getWidth()
	{ return width; }
	
	public int getHeight()
	{ return height; }
}
