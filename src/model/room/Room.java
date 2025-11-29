package model.room;

import java.util.List;
import java.util.Collections;
import java.util.LinkedList;

import model.gameobject.Forniture;
import model.gameobject.GameObject;
import model.gameobject.Robot;

public class Room
{
	private List<GameObject> gameObjectList;
	private List<Robot> robotList;
	private List<Forniture> fornitureList;
	private Color color;
	private ExitLayout layout;

	public enum Color 
	{ CYAN, YELLOW, GREEN };
	
	public enum ExitLayout
	{ ONLEFT, ONRIGHT, ONLEFTANDRIGHT }
	
	public enum PresettedRoom 
	{
		ROOM1 (new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM2 (new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM3 (new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM4 (new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM5 (new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM6 (new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM7 (new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM8 (new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM9 (new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM10(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM11(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM12(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM13(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM14(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM15(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM16(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM17(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM18(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM19(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM20(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM21(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM22(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM23(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM24(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM25(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM26(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM27(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM28(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM29(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM30(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM31(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT)),
		ROOM32(new Room(new LinkedList<GameObject>(), new LinkedList<Robot>(), new LinkedList<Forniture> (), Room.Color.CYAN, Room.ExitLayout.ONLEFT));
		
		private Room room;
		
		private PresettedRoom(Room room)
		{ this.room = room; }
	}
	
	public Room(List<GameObject> gameObjectList, List<Robot> robotList, List<Forniture> fornitureList, Color roomColor, ExitLayout layout)
	{
		this.gameObjectList = gameObjectList;
		this.robotList = robotList;
		this.fornitureList = fornitureList;
		this.color = roomColor;
		this.layout = layout;
	}
	
	public List<GameObject> getGameObjectList()
	{ return Collections.unmodifiableList(gameObjectList); }
	
	public List<Forniture> getFornitureList()
	{ return Collections.unmodifiableList(fornitureList); }
	
	public boolean removeForniture(Forniture object)
	{ return fornitureList.remove(object); }
	
	public List<Robot> getRobotList()
	{ return Collections.unmodifiableList(robotList); }
	
	public Color getColor()
	{ return color; }
	
	public ExitLayout getExitLayout()
	{ return layout; }
}
