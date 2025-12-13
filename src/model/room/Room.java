package model.room;

//data structure modules
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

//IO modules
import java.io.Serializable;

//inproject import
import model.gameobject.Furniture;
import model.gameobject.GameObject;
import model.gameobject.Platform;
import model.gameobject.enemy.LaserRobot;
import model.gameobject.enemy.Enemy;
import model.gameobject.FixedObject;

public class Room implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private List<GameObject> gameObjectList;
	private List<Platform> platformList;
	private List<FixedObject> fixedObjectList;
	private List<Furniture> furnitureList;
	private List<Enemy> enemiesList;
	private int platformsNumber;
	private Color color;
	private ExitLayout layout;

	public enum Color 
	{ CYAN, YELLOW, GREEN };
	
	public enum ExitLayout
	{ ONLEFT, ONRIGHT, ONLEFTANDRIGHT }
	
	public Room(List<GameObject> gameObjectList, Color color, ExitLayout layout)
	{
		this.gameObjectList = gameObjectList;
		platformList = gameObjectList.stream().filter(g -> g instanceof Platform).map(g -> (Platform)g).collect(Collectors.toList());
		fixedObjectList = gameObjectList.stream().filter(g -> g instanceof FixedObject).map(g -> (FixedObject)g).collect(Collectors.toList());
		furnitureList = gameObjectList.stream().filter(g -> g instanceof Furniture).map(g -> (Furniture)g).collect(Collectors.toList());
		enemiesList = gameObjectList.stream().filter(g -> g instanceof Enemy).map(g -> (Enemy)g).collect(Collectors.toList());
		platformsNumber = platformList.size();
		this.color = color;
		this.layout = layout;
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
	
	public void addEnemyAttack(GameObject attack)
	{ gameObjectList.add(attack); }
	
	public boolean removeEnemyAttack(GameObject attack)
	{ return gameObjectList.remove(attack); }
	
	public int getPlatformsNumber()
	{ return platformsNumber; }
	
	public Color getColor()
	{ return color; }
	
	public ExitLayout getExitLayout()
	{ return layout; }
}
