package model.room;

import java.util.List;
import java.util.Collections;

import model.gameobject.Furniture;
import model.gameobject.GameObject;
import model.gameobject.Robot;

public class Room
{
	private List<GameObject> otherGameObjectList;
	private List<Robot> robotList;
	private List<Furniture> fornitureList;
	private Color color;
	private ExitLayout layout;

	public enum Color 
	{ CYAN, YELLOW, GREEN };
	
	public enum ExitLayout
	{ ONLEFT, ONRIGHT, ONLEFTANDRIGHT }
	
	public Room(List<GameObject> gameObjectList, Color color, ExitLayout layout)
	{
		this.otherGameObjectList = gameObjectList.stream().filter(g -> !(g instanceof Robot) && !(g instanceof Furniture)).toList();
		this.robotList = gameObjectList.stream().filter(g -> g instanceof Robot).map(g -> (Robot)g).toList();
		this.fornitureList = gameObjectList.stream().filter(g -> g instanceof Furniture).map(g -> (Furniture)g).toList();
		this.color = color;
		this.layout = layout;
	}
	
	public List<GameObject> otherGameObjectList()
	{ return Collections.unmodifiableList(otherGameObjectList); }
	
	public List<Furniture> getFornitureList()
	{ return Collections.unmodifiableList(fornitureList); }
	
	public boolean removeForniture(Furniture object)
	{ return fornitureList.remove(object); }
	
	public List<Robot> getRobotList()
	{ return Collections.unmodifiableList(robotList); }
	
	public Color getColor()
	{ return color; }
	
	public ExitLayout getExitLayout()
	{ return layout; }
}
