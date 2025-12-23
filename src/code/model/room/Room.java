package code.model.room;

//data structure modules
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

//IO modules
import java.io.Serializable;

import code.model.gameobjects.FixedObject;
import code.model.gameobjects.Furniture;
import code.model.gameobjects.GameObject;
import code.model.gameobjects.Platform;
import code.model.gameobjects.enemy.AttackerRobot;
import code.model.gameobjects.enemy.Enemy;

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
	{ RED, PURPLE, GREEN };
	
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
	
	public void addEnemyAttack(AttackerRobot.Attack attack)
	{ gameObjectList.add(attack); }
	
	public boolean removeEnemyAttack(AttackerRobot.Attack attack)
	{ return gameObjectList.remove(attack); }
	
	public int getPlatformsNumber()
	{ return platformsNumber; }
	
	public Color getColor()
	{ return color; }
	
	public ExitLayout getExitLayout()
	{ return layout; }
}
