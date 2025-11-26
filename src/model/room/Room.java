package model.room;

import java.util.List;
import java.util.Collections;

//inproject import
import model.Forniture;
import model.GameObject;
import model.Robot;

public class Room
{
	private List<GameObject> gameObjectList;
	private List<Robot> robotList;
	private Color color;

	public enum Color 
	{ CYAN, YELLOW, GREEN };
	
	public enum ExitLayout
	{ ONLEFT, ONRIGHT, ONLEFTANDRIGHT }
	
	public static class RoomNotFoundException extends Exception 
	{
		public RoomNotFoundException(String errorMessage)
		{ super(errorMessage); }
	}
	
	public Room(List<GameObject> gameObjectList, List<Robot> robotList, Color roomColor)
	{
		this.gameObjectList = gameObjectList;
		this.robotList = robotList;
		this.color = roomColor;
	}
	
	public static Room getPresettedRoom(String roomID, ExitLayout exitLayout) throws RoomNotFoundException
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
