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

public class Room implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private List<GameObject> gameObjectList;
	private List<Furniture> furnitureList;
	private Color color;
	private ExitLayout layout;

	public enum Color 
	{ CYAN, YELLOW, GREEN };
	
	public enum ExitLayout
	{ ONLEFT, ONRIGHT, ONLEFTANDRIGHT }
	
	public Room(List<GameObject> gameObjectList, Color color, ExitLayout layout)
	{
		this.gameObjectList = gameObjectList.stream().filter(g -> !(g instanceof Furniture)).collect(Collectors.toList());
		this.furnitureList  = gameObjectList.stream().filter(g -> g instanceof Furniture).map(g -> (Furniture)g).collect(Collectors.toList());
		this.color = color;
		this.layout = layout;
	}
	
	public List<GameObject> getGameObjectList()
	{ return Collections.unmodifiableList(gameObjectList); }
	
	public List<Furniture> getFurnitureList()
	{ return new ArrayList<Furniture>(furnitureList); }
	
	public int getFurnitureNumber()
	{ return furnitureList.size(); }
	
	public boolean removeForniture(Furniture object)
	{ return furnitureList.remove(object); }
	
	public Color getColor()
	{ return color; }
	
	public ExitLayout getExitLayout()
	{ return layout; }
	
	/* remove comment for debugging
	@Override
	public String toString()
	{ return layout.toString(); } */
}
