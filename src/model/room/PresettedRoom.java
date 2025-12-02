package model.room;

//data structure modules
import java.util.Arrays;

public enum PresettedRoom 
{
	//TODO ROOM1 (new Room(RoomMapParser.parse(RoomMap.ROOM1),  Room.Color.CYAN,   Room.ExitLayout.ONLEFT)),
	//TODO ROOM2 (new Room(RoomMapParser.parse(RoomMap.ROOM2),  Room.Color.YELLOW, Room.ExitLayout.ONRIGHT)),
	ROOM3 (new Room(RoomMapParser.parse(RoomMap.ROOM3),  Room.Color.YELLOW, Room.ExitLayout.ONLEFT)),
	ROOM4 (new Room(RoomMapParser.parse(RoomMap.ROOM4),  Room.Color.YELLOW, Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM5 (new Room(RoomMapParser.parse(RoomMap.ROOM5),	 Room.Color.GREEN,  Room.ExitLayout.ONRIGHT)),
	ROOM6 (new Room(RoomMapParser.parse(RoomMap.ROOM6),  Room.Color.YELLOW, Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM7 (new Room(RoomMapParser.parse(RoomMap.ROOM7),  Room.Color.GREEN,  Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM8 (new Room(RoomMapParser.parse(RoomMap.ROOM8),  Room.Color.GREEN,  Room.ExitLayout.ONRIGHT)),
	ROOM9 (new Room(RoomMapParser.parse(RoomMap.ROOM9),  Room.Color.CYAN,   Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM10(new Room(RoomMapParser.parse(RoomMap.ROOM10), Room.Color.YELLOW, Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM11(new Room(RoomMapParser.parse(RoomMap.ROOM11), Room.Color.YELLOW, Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM12(new Room(RoomMapParser.parse(RoomMap.ROOM12), Room.Color.GREEN,  Room.ExitLayout.ONRIGHT)),
	ROOM13(new Room(RoomMapParser.parse(RoomMap.ROOM13), Room.Color.CYAN,   Room.ExitLayout.ONLEFT)),
	ROOM14(new Room(RoomMapParser.parse(RoomMap.ROOM14), Room.Color.GREEN,  Room.ExitLayout.ONLEFT)),
	ROOM15(new Room(RoomMapParser.parse(RoomMap.ROOM15), Room.Color.CYAN,   Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM16(new Room(RoomMapParser.parse(RoomMap.ROOM16), Room.Color.CYAN,   Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM17(new Room(RoomMapParser.parse(RoomMap.ROOM17), Room.Color.CYAN,   Room.ExitLayout.ONLEFT)),
	ROOM18(new Room(RoomMapParser.parse(RoomMap.ROOM18), Room.Color.CYAN,   Room.ExitLayout.ONLEFT)),
	ROOM19(new Room(RoomMapParser.parse(RoomMap.ROOM19), Room.Color.CYAN,   Room.ExitLayout.ONRIGHT)),
	ROOM20(new Room(RoomMapParser.parse(RoomMap.ROOM20), Room.Color.CYAN,   Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM21(new Room(RoomMapParser.parse(RoomMap.ROOM21), Room.Color.GREEN,  Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM22(new Room(RoomMapParser.parse(RoomMap.ROOM22), Room.Color.GREEN,  Room.ExitLayout.ONRIGHT)),
	ROOM23(new Room(RoomMapParser.parse(RoomMap.ROOM23), Room.Color.GREEN,  Room.ExitLayout.ONLEFT)),
	ROOM24(new Room(RoomMapParser.parse(RoomMap.ROOM24), Room.Color.YELLOW, Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM25(new Room(RoomMapParser.parse(RoomMap.ROOM25), Room.Color.CYAN,   Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM26(new Room(RoomMapParser.parse(RoomMap.ROOM26), Room.Color.CYAN,   Room.ExitLayout.ONRIGHT)),
	ROOM27(new Room(RoomMapParser.parse(RoomMap.ROOM27), Room.Color.CYAN,   Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM28(new Room(RoomMapParser.parse(RoomMap.ROOM28), Room.Color.CYAN,   Room.ExitLayout.ONLEFT)),
	ROOM29(new Room(RoomMapParser.parse(RoomMap.ROOM29), Room.Color.GREEN,  Room.ExitLayout.ONLEFT)),
	ROOM30(new Room(RoomMapParser.parse(RoomMap.ROOM30), Room.Color.YELLOW, Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM31(new Room(RoomMapParser.parse(RoomMap.ROOM31), Room.Color.GREEN,  Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM32(new Room(RoomMapParser.parse(RoomMap.ROOM32), Room.Color.YELLOW, Room.ExitLayout.ONRIGHT));
	
	private static final PresettedRoom[] leftRoom          = Arrays.stream(values()).filter(r -> r.room.getExitLayout() == Room.ExitLayout.ONLEFT).toArray(PresettedRoom[]::new);
	private static final PresettedRoom[] rightRoom         = Arrays.stream(values()).filter(r -> r.room.getExitLayout() == Room.ExitLayout.ONRIGHT).toArray(PresettedRoom[]::new);
	private static final PresettedRoom[] leftAndRightRoom  = Arrays.stream(values()).filter(r -> r.room.getExitLayout() == Room.ExitLayout.ONLEFTANDRIGHT).toArray(PresettedRoom[]::new);
	
	public static final int LEFT_ROOM_NUMBER       = leftRoom.length;
	public static final int RIGHT_ROOM_NUMBER      = rightRoom.length;
	public static final int LEFT_RIGHT_ROOM_NUMBER = leftAndRightRoom.length;
	public static final int ROOM_NUMBER            = LEFT_ROOM_NUMBER + RIGHT_ROOM_NUMBER + LEFT_RIGHT_ROOM_NUMBER;
	
	private Room room;
	
	private PresettedRoom(Room room)
	{ this.room = room; }
	
	public Room getRoom()
	{ return room; }
	
	public static Room getRoom(Room.ExitLayout layout, int index)
	{ 
		return switch(layout) {
			case Room.ExitLayout.ONLEFT  		-> leftRoom[index].room;
			case Room.ExitLayout.ONRIGHT		-> rightRoom[index].room;
			case Room.ExitLayout.ONLEFTANDRIGHT -> leftAndRightRoom[index].room;
		};
	}
}
