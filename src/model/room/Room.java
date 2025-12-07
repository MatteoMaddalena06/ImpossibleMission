package model.room;

//data structure modules
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;

//IO modules
import java.io.Serializable;

//inproject import
import model.gameobject.Furniture;
import model.gameobject.GameObject;
import model.gameobject.Platform;
import model.gameobject.Robot;
import model.gameobject.FixedObject;

public class Room implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private List<GameObject> gameObjectList;
	private List<Platform> platformList;
	private List<FixedObject> fixedObjectList;
	private List<Furniture> furnitureList;
	private List<Robot> robotList;
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
		robotList = gameObjectList.stream().filter(g -> g instanceof Robot).map(g -> (Robot)g).collect(Collectors.toList());
		platformsNumber = platformList.size();
		this.color = color;
		this.layout = layout;
	}
	
	public List<GameObject> getGameObjectList()
	{ return Collections.unmodifiableList(gameObjectList); }
	
	public List<GameObject> getPlatformList()
	{ return Collections.unmodifiableList(platformList); }
	
	public List<FixedObject> getFixedObjectList()
	{ return Collections.unmodifiableList(fixedObjectList); }
	
	public List<Furniture> getFurnitureList()
	{ return new ArrayList<Furniture>(furnitureList); }
	
	public List<Robot> getRobotList()
	{ return Collections.unmodifiableList(robotList); }

	public boolean removeForniture(Furniture object)
	{ return furnitureList.remove(object) && gameObjectList.remove(object); }
	
	public int getPlatformsNumber()
	{ return platformsNumber; }
	
	public Color getColor()
	{ return color; }
	
	public ExitLayout getExitLayout()
	{ return layout; }
}
