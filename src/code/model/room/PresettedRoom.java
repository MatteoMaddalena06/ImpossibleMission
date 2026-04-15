package code.model.room;

//data structure modules
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import code.model.gameobjects.Furniture;
import code.model.gameobjects.GameObject;

public enum PresettedRoom 
{
	ROOM3 (RoomMapParser.parse(RoomMap.ROOM3),  Room.Color.PURPLE, Room.ExitLayout.ONLEFT),
	ROOM4 (RoomMapParser.parse(RoomMap.ROOM4),  Room.Color.PURPLE, Room.ExitLayout.ONLEFTANDRIGHT),
	ROOM5 (RoomMapParser.parse(RoomMap.ROOM5),	Room.Color.GREEN,  Room.ExitLayout.ONRIGHT),
	ROOM6 (RoomMapParser.parse(RoomMap.ROOM6),  Room.Color.PURPLE, Room.ExitLayout.ONLEFTANDRIGHT),
	ROOM7 (RoomMapParser.parse(RoomMap.ROOM7),  Room.Color.GREEN,  Room.ExitLayout.ONLEFTANDRIGHT),
	ROOM8 (RoomMapParser.parse(RoomMap.ROOM8),  Room.Color.GREEN,  Room.ExitLayout.ONRIGHT),
	ROOM9 (RoomMapParser.parse(RoomMap.ROOM9),  Room.Color.RED,    Room.ExitLayout.ONLEFTANDRIGHT),
	ROOM10(RoomMapParser.parse(RoomMap.ROOM10), Room.Color.PURPLE, Room.ExitLayout.ONLEFTANDRIGHT),
	ROOM11(RoomMapParser.parse(RoomMap.ROOM11), Room.Color.PURPLE, Room.ExitLayout.ONLEFTANDRIGHT),
	ROOM12(RoomMapParser.parse(RoomMap.ROOM12), Room.Color.GREEN,  Room.ExitLayout.ONRIGHT),
	ROOM13(RoomMapParser.parse(RoomMap.ROOM13), Room.Color.RED,    Room.ExitLayout.ONLEFT),
	ROOM14(RoomMapParser.parse(RoomMap.ROOM14), Room.Color.GREEN,  Room.ExitLayout.ONLEFT),
	ROOM15(RoomMapParser.parse(RoomMap.ROOM15), Room.Color.RED,    Room.ExitLayout.ONLEFTANDRIGHT),
	ROOM16(RoomMapParser.parse(RoomMap.ROOM16), Room.Color.RED,    Room.ExitLayout.ONLEFTANDRIGHT),
	ROOM17(RoomMapParser.parse(RoomMap.ROOM17), Room.Color.RED,    Room.ExitLayout.ONLEFT),
	ROOM18(RoomMapParser.parse(RoomMap.ROOM18), Room.Color.RED,    Room.ExitLayout.ONLEFT),
	ROOM19(RoomMapParser.parse(RoomMap.ROOM19), Room.Color.RED,    Room.ExitLayout.ONRIGHT),
	ROOM20(RoomMapParser.parse(RoomMap.ROOM20), Room.Color.RED,    Room.ExitLayout.ONLEFTANDRIGHT),
	ROOM21(RoomMapParser.parse(RoomMap.ROOM21), Room.Color.GREEN,  Room.ExitLayout.ONLEFTANDRIGHT),
	ROOM22(RoomMapParser.parse(RoomMap.ROOM22), Room.Color.GREEN,  Room.ExitLayout.ONRIGHT),
	ROOM23(RoomMapParser.parse(RoomMap.ROOM23), Room.Color.GREEN,  Room.ExitLayout.ONLEFT),
	ROOM24(RoomMapParser.parse(RoomMap.ROOM24), Room.Color.PURPLE, Room.ExitLayout.ONLEFTANDRIGHT),
	ROOM25(RoomMapParser.parse(RoomMap.ROOM25), Room.Color.RED,    Room.ExitLayout.ONLEFTANDRIGHT),
	ROOM26(RoomMapParser.parse(RoomMap.ROOM26), Room.Color.RED,    Room.ExitLayout.ONRIGHT),
	ROOM27(RoomMapParser.parse(RoomMap.ROOM27), Room.Color.RED,    Room.ExitLayout.ONLEFTANDRIGHT),
	ROOM28(RoomMapParser.parse(RoomMap.ROOM28), Room.Color.RED,    Room.ExitLayout.ONLEFT),
	ROOM29(RoomMapParser.parse(RoomMap.ROOM29), Room.Color.GREEN,  Room.ExitLayout.ONLEFT),
	ROOM30(RoomMapParser.parse(RoomMap.ROOM30), Room.Color.PURPLE, Room.ExitLayout.ONLEFTANDRIGHT),
	ROOM31(RoomMapParser.parse(RoomMap.ROOM31), Room.Color.GREEN,  Room.ExitLayout.ONLEFTANDRIGHT),
	ROOM32(RoomMapParser.parse(RoomMap.ROOM32), Room.Color.PURPLE, Room.ExitLayout.ONRIGHT),
	
	ELEVATOR_RIGHT_EXIT    (RoomMapParser.parse(RoomMap.RIGHTEXIT_ELEVATOR_ROOM),     Room.Color.ANY, Room.ExitLayout.ONRIGHT),
	ELEVATOR_LEFT_EXIT     (RoomMapParser.parse(RoomMap.LEFTEXIT_ELEVATOR_ROOM),      Room.Color.ANY, Room.ExitLayout.ONLEFT),
	ELEVATOR_RIGHTLEFT_EXIT(RoomMapParser.parse(RoomMap.RIGHTLEFTEXIT_ELEVATOR_ROOM), Room.Color.ANY, Room.ExitLayout.ONRIGHT),
	ELEVATOR_NOEXIT        (RoomMapParser.parse(RoomMap.NOEXIT_ELEVATOR_ROOM),        Room.Color.ANY, Room.ExitLayout.NOEXIT);
	
	private static final PresettedRoom[] leftRoom          = Arrays.stream(values()).filter(r -> r.exitLayout == Room.ExitLayout.ONLEFT).toArray(PresettedRoom[]::new);
	private static final PresettedRoom[] rightRoom         = Arrays.stream(values()).filter(r -> r.exitLayout == Room.ExitLayout.ONRIGHT).toArray(PresettedRoom[]::new);
	private static final PresettedRoom[] leftAndRightRoom  = Arrays.stream(values()).filter(r -> r.exitLayout == Room.ExitLayout.ONLEFTANDRIGHT).toArray(PresettedRoom[]::new);
	
	public static final int LEFT_ROOM_NUMBER       = leftRoom.length;
	public static final int RIGHT_ROOM_NUMBER      = rightRoom.length;
	public static final int LEFT_RIGHT_ROOM_NUMBER = leftAndRightRoom.length;
	public static final int ROOM_NUMBER            = LEFT_ROOM_NUMBER + RIGHT_ROOM_NUMBER + LEFT_RIGHT_ROOM_NUMBER;
	
	private List<GameObject> gameObjectList;
	private Room.Color color;
	private Room.ExitLayout exitLayout;
	
	private PresettedRoom(List<GameObject> gameObjectList, Room.Color color, Room.ExitLayout exitLayout)
	{ 
		this.gameObjectList = gameObjectList;
		this.color = color;
		this.exitLayout = exitLayout;
	}
	
	public static Room getRoom(Room.ExitLayout layout, int index)
	{ 
		return switch(layout) {
			case Room.ExitLayout.ONLEFT  		-> leftRoom[index].getRoom();
			case Room.ExitLayout.ONRIGHT		-> rightRoom[index].getRoom();
			case Room.ExitLayout.ONLEFTANDRIGHT -> leftAndRightRoom[index].getRoom();
			case Room.ExitLayout.NOEXIT         -> ELEVATOR_NOEXIT.getRoom();
		};
	}
	
	public Room getRoom()
	{ return new Room(gameObjectList, color); }
	
	public List<Furniture> getFurnitures()
	{ return gameObjectList.stream().filter(g -> g instanceof Furniture).map(g -> (Furniture)g).toList(); }
}
