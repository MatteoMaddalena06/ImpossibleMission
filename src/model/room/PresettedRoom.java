package model.room;

public enum PresettedRoom 
{
	ROOM1 (new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM1),  Room.Color.CYAN,   Room.ExitLayout.ONLEFT)),
	ROOM2 (new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM2),  Room.Color.YELLOW, Room.ExitLayout.ONRIGHT)),
	ROOM3 (new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM3),  Room.Color.YELLOW, Room.ExitLayout.ONLEFT)),
	ROOM4 (new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM4),  Room.Color.YELLOW, Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM5 (new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM5),  Room.Color.GREEN,  Room.ExitLayout.ONRIGHT)),
	ROOM6 (new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM6),  Room.Color.YELLOW, Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM7 (new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM7),  Room.Color.GREEN,  Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM8 (new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM8),  Room.Color.GREEN,  Room.ExitLayout.ONRIGHT)),
	ROOM9 (new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM9),  Room.Color.CYAN,   Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM10(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM10), Room.Color.YELLOW, Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM11(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM11), Room.Color.YELLOW, Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM12(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM12), Room.Color.GREEN,  Room.ExitLayout.ONRIGHT)),
	ROOM13(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM13), Room.Color.CYAN,   Room.ExitLayout.ONLEFT)),
	ROOM14(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM14), Room.Color.GREEN,  Room.ExitLayout.ONLEFT)),
	ROOM15(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM15), Room.Color.CYAN,   Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM16(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM16), Room.Color.CYAN,   Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM17(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM17), Room.Color.CYAN,   Room.ExitLayout.ONLEFT)),
	ROOM18(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM18), Room.Color.CYAN,   Room.ExitLayout.ONLEFT)),
	ROOM19(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM19), Room.Color.CYAN,   Room.ExitLayout.ONRIGHT)),
	ROOM20(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM20), Room.Color.CYAN,   Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM21(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM21), Room.Color.GREEN,  Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM22(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM22), Room.Color.GREEN,  Room.ExitLayout.ONRIGHT)),
	ROOM23(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM23), Room.Color.GREEN,  Room.ExitLayout.ONLEFT)),
	ROOM24(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM24), Room.Color.YELLOW, Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM25(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM25), Room.Color.CYAN,   Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM26(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM26), Room.Color.CYAN,   Room.ExitLayout.ONRIGHT)),
	ROOM27(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM27), Room.Color.CYAN,   Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM28(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM28), Room.Color.CYAN,   Room.ExitLayout.ONLEFT)),
	ROOM29(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM29), Room.Color.GREEN,  Room.ExitLayout.ONLEFT)),
	ROOM30(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM30), Room.Color.YELLOW, Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM31(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM31), Room.Color.GREEN,  Room.ExitLayout.ONLEFTANDRIGHT)),
	ROOM32(new Room(RoomAsciiMapParser.parse(RoomAsciiMap.ROOM32), Room.Color.YELLOW, Room.ExitLayout.ONRIGHT));
	
	private Room room;
	
	private PresettedRoom(Room room)
	{ this.room = room; }
	
	public Room getRoom()
	{ return room; }
}
