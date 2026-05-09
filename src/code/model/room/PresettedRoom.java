package code.model.room;

//data structure modules
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
//model import
import code.model.Point;
import code.model.gameobjects.Furniture;
import code.model.gameobjects.GameObject;

public enum PresettedRoom 
{
	ROOM3 (RoomMapParser.parse(RoomMap.ROOM3),  Room.Color.PURPLE, Room.ExitLayout.ONLEFT,         RoomMap.upperLeftSpawnPosition),
	ROOM4 (RoomMapParser.parse(RoomMap.ROOM4),  Room.Color.PURPLE, Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.upperLeftSpawnPosition, RoomMap.upperRightSpawnPosition),
	ROOM5 (RoomMapParser.parse(RoomMap.ROOM5),	Room.Color.GREEN,  Room.ExitLayout.ONRIGHT,        RoomMap.bottomRightSpawnPosition),
	ROOM6 (RoomMapParser.parse(RoomMap.ROOM6),  Room.Color.PURPLE, Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.bottomLeftSpawnPosition, RoomMap.upperRightSpawnPosition),
	ROOM7 (RoomMapParser.parse(RoomMap.ROOM7),  Room.Color.GREEN,  Room.ExitLayout.ONLEFT,         RoomMap.upperLeftSpawnPosition),
	ROOM8 (RoomMapParser.parse(RoomMap.ROOM8),  Room.Color.GREEN,  Room.ExitLayout.ONRIGHT,        RoomMap.upperRightSpawnPosition),
	ROOM9 (RoomMapParser.parse(RoomMap.ROOM9),  Room.Color.RED,    Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.upperLeftSpawnPosition, RoomMap.upperRightSpawnPosition),
	ROOM10(RoomMapParser.parse(RoomMap.ROOM10), Room.Color.PURPLE, Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.bottomLeftSpawnPosition, RoomMap.bottomRightSpawnPosition),
	ROOM11(RoomMapParser.parse(RoomMap.ROOM11), Room.Color.PURPLE, Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.bottomLeftSpawnPosition, RoomMap.bottomRightSpawnPosition),
	ROOM12(RoomMapParser.parse(RoomMap.ROOM12), Room.Color.GREEN,  Room.ExitLayout.ONRIGHT, 	   RoomMap.upperRightSpawnPosition),
	ROOM13(RoomMapParser.parse(RoomMap.ROOM13), Room.Color.RED,    Room.ExitLayout.ONLEFT,		   RoomMap.bottomLeftSpawnPosition),
	ROOM14(RoomMapParser.parse(RoomMap.ROOM14), Room.Color.GREEN,  Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.bottomLeftSpawnPosition, RoomMap.upperRightSpawnPosition),
	ROOM15(RoomMapParser.parse(RoomMap.ROOM15), Room.Color.RED,    Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.upperLeftSpawnPosition, RoomMap.upperRightSpawnPosition),
	ROOM16(RoomMapParser.parse(RoomMap.ROOM16), Room.Color.RED,    Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.upperLeftSpawnPosition, RoomMap.bottomRightSpawnPosition),
	ROOM17(RoomMapParser.parse(RoomMap.ROOM17), Room.Color.RED,    Room.ExitLayout.ONLEFT, 		   RoomMap.bottomLeftSpawnPosition),
	ROOM18(RoomMapParser.parse(RoomMap.ROOM18), Room.Color.RED,    Room.ExitLayout.ONLEFT, 		   RoomMap.upperLeftSpawnPosition),
	ROOM19(RoomMapParser.parse(RoomMap.ROOM19), Room.Color.RED,    Room.ExitLayout.ONRIGHT, 	   RoomMap.bottomRightSpawnPosition),
	ROOM20(RoomMapParser.parse(RoomMap.ROOM20), Room.Color.RED,    Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.upperLeftSpawnPosition, RoomMap.bottomRightSpawnPosition),
	ROOM21(RoomMapParser.parse(RoomMap.ROOM21), Room.Color.GREEN,  Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.bottomLeftSpawnPosition, RoomMap.bottomRightSpawnPosition),
	ROOM22(RoomMapParser.parse(RoomMap.ROOM22), Room.Color.GREEN,  Room.ExitLayout.ONRIGHT,        RoomMap.upperRightSpawnPosition),
	ROOM23(RoomMapParser.parse(RoomMap.ROOM23), Room.Color.GREEN,  Room.ExitLayout.ONLEFT, 		   RoomMap.upperLeftSpawnPosition),
	ROOM24(RoomMapParser.parse(RoomMap.ROOM24), Room.Color.PURPLE, Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.bottomLeftSpawnPosition, RoomMap.bottomRightSpawnPosition),
	ROOM25(RoomMapParser.parse(RoomMap.ROOM25), Room.Color.RED,    Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.bottomLeftSpawnPosition, RoomMap.upperRightSpawnPosition),
	ROOM26(RoomMapParser.parse(RoomMap.ROOM26), Room.Color.RED,    Room.ExitLayout.ONRIGHT, 	   RoomMap.upperRightSpawnPosition),
	ROOM27(RoomMapParser.parse(RoomMap.ROOM27), Room.Color.RED,    Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.upperLeftSpawnPosition, RoomMap.bottomRightSpawnPosition),
	ROOM28(RoomMapParser.parse(RoomMap.ROOM28), Room.Color.RED,    Room.ExitLayout.ONLEFT, 	       RoomMap.bottomLeftSpawnPosition),
	ROOM29(RoomMapParser.parse(RoomMap.ROOM29), Room.Color.GREEN,  Room.ExitLayout.ONLEFT, 	       RoomMap.upperLeftSpawnPosition),
	ROOM30(RoomMapParser.parse(RoomMap.ROOM30), Room.Color.PURPLE, Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.upperLeftSpawnPosition, RoomMap.upperRightSpawnPosition),
	ROOM31(RoomMapParser.parse(RoomMap.ROOM31), Room.Color.GREEN,  Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.bottomLeftSpawnPosition, RoomMap.upperRightSpawnPosition),
	ROOM32(RoomMapParser.parse(RoomMap.ROOM32), Room.Color.PURPLE, Room.ExitLayout.ONRIGHT,        RoomMap.bottomRightSpawnPosition),
	
	ELEVATOR_RIGHT_EXIT    (RoomMapParser.parse(RoomMap.RIGHTEXIT_ELEVATOR_ROOM),     Room.Color.GREEN, Room.ExitLayout.ONRIGHT,        RoomMap.bottomRightSpawnPosition),
	ELEVATOR_LEFT_EXIT     (RoomMapParser.parse(RoomMap.LEFTEXIT_ELEVATOR_ROOM),      Room.Color.GREEN, Room.ExitLayout.ONLEFT,  	      RoomMap.bottomLeftSpawnPosition),
	ELEVATOR_RIGHTLEFT_EXIT(RoomMapParser.parse(RoomMap.RIGHTLEFTEXIT_ELEVATOR_ROOM), Room.Color.GREEN, Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.bottomLeftSpawnPosition, RoomMap.bottomRightSpawnPosition),
	ELEVATOR_NOEXIT        (RoomMapParser.parse(RoomMap.NOEXIT_ELEVATOR_ROOM),        Room.Color.GREEN, Room.ExitLayout.NOEXIT); 
	
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
	private Point leftSpawnPosition, rightSpawnPosition;

	private PresettedRoom(List<GameObject> gameObjectList, Room.Color color, Room.ExitLayout exitLayout, Point leftSpawnPosition, Point rightSpawnPosition)
	{ 
		this.gameObjectList = gameObjectList;
		this.color = color;
		this.exitLayout = exitLayout;
		this.leftSpawnPosition = leftSpawnPosition;
		this.rightSpawnPosition = rightSpawnPosition;
	}
	
	private PresettedRoom(List<GameObject> gameObjectList, Room.Color color, Room.ExitLayout exitLayout, Point spawnPosition)
	{ this(gameObjectList, color, exitLayout, spawnPosition, spawnPosition); }
	
	private PresettedRoom(List<GameObject> gameObjectList, Room.Color color, Room.ExitLayout exitLayout)
	{ this(gameObjectList, color, exitLayout, null); }
	
	public static Room getRoom(Room.ExitLayout layout, int index)
	{ 
		return switch(layout) {
			case Room.ExitLayout.ONLEFT  		-> leftRoom[index].getRoom();
			case Room.ExitLayout.ONRIGHT		-> rightRoom[index].getRoom();
			case Room.ExitLayout.ONLEFTANDRIGHT -> leftAndRightRoom[index].getRoom();
			case Room.ExitLayout.NOEXIT         -> ELEVATOR_NOEXIT.getRoom();
			default -> throw new IllegalArgumentException("ExitLayout \"ANY\" don't accept for this method");
		};
	}
	
	public Room getRoom()
	{ return new Room(gameObjectList, color, exitLayout, leftSpawnPosition, rightSpawnPosition); }
	
	public List<Furniture> getFurnitures()
	{ return gameObjectList.stream().filter(g -> g instanceof Furniture).map(g -> (Furniture)g).toList(); }
}
