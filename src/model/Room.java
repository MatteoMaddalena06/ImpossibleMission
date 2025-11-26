package model;

import java.util.List;
import java.util.Collections;

public class Room
{
	private List<GameObject> gameObjectList;
	private List<Robot> robotList;
	private Color color;

	public enum Color 
	{ CYAN, YELLOW, GREEN };
	
	public enum ExitLayout
	{ ONLEFT, ONRIGHT, ONLEFTANDRIGHT }
	
	public Room(List<GameObject> gameObjectList, List<Robot> robotList, Color roomColor)
	{
		this.gameObjectList = gameObjectList;
		this.robotList = robotList;
		this.color = roomColor;
	}
	
	public static Room getPresettedRoom(String roomID, ExitLayout exitLayout)
	{ return PresettedRoomFactory.getRoom(roomID, exitLayout); }
	
	public List<GameObject> getGameObjectList()
	{ return Collections.unmodifiableList(gameObjectList); }
	
	public boolean removeForniture(Forniture object)
	{ return gameObjectList.remove(object); }
	
	public List<Robot> getRobotList()
	{ return Collections.unmodifiableList(robotList); }
	
	public Color getColor()
	{ return color; }
}
