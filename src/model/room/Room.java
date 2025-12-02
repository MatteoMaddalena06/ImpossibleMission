package model.room;

//data structure modules
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

//IO modules
import java.io.Serializable;

//inproject import
import model.gameobject.Furniture;
import model.gameobject.GameObject;
import model.gameobject.Robot;

public class Room implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static final String CONSTRUCTOR_ERROR_MSG = "Only not null parameters accepted";
	
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
		if(gameObjectList == null || color == null || layout == null)
			throw new IllegalArgumentException(CONSTRUCTOR_ERROR_MSG);
		
		this.otherGameObjectList = gameObjectList.stream().filter(g -> !(g instanceof Robot) && !(g instanceof Furniture)).collect(Collectors.toList());
		this.robotList = gameObjectList.stream().filter(g -> g instanceof Robot).map(g -> (Robot)g).collect(Collectors.toList());
		this.fornitureList = gameObjectList.stream().filter(g -> g instanceof Furniture).map(g -> (Furniture)g).collect(Collectors.toList());
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
	
	/* remove comment for debugging
	@Override
	public String toString()
	{ return layout.toString(); } */
}
