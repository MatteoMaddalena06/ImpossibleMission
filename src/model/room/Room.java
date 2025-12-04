package model.room;

//data structure modules
import java.util.List;
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
	private Color color;
	private ExitLayout layout;

	public enum Color 
	{ CYAN, YELLOW, GREEN };
	
	public enum ExitLayout
	{ ONLEFT, ONRIGHT, ONLEFTANDRIGHT }
	
	public Room(List<GameObject> gameObjectList, Color color, ExitLayout layout)
	{
		this.gameObjectList = gameObjectList;
		this.color = color;
		this.layout = layout;
	}
	
	public List<GameObject> getGameObjectList()
	{ return Collections.unmodifiableList(gameObjectList); }
	
	public boolean removeForniture(Furniture object)
	{ return gameObjectList.remove(object); }
	
	public Color getColor()
	{ return color; }
	
	public ExitLayout getExitLayout()
	{ return layout; }
	
	/* remove comment for debugging
	@Override
	public String toString()
	{ return layout.toString(); } */
}
