package model;

import java.util.Map;
import static java.util.Map.entry;

public abstract class PresettedRoomFactory
{
	private static Map<String, Room> pressettedRoomsWithExitOnLeft = Map.ofEntries(
		entry("ROOM1", null),
		entry("ROOM3", null),
		entry("ROOM13", null),
		entry("ROOM14", null),
		entry("ROOM17", null),
		entry("ROOM18", null),
		entry("ROOM23", null),
		entry("ROOM28", null),
		entry("ROOM29", null)
	);
	
	private static Map<String, Room> pressettedRoomsWithExitOnRight = Map.ofEntries(
		entry("ROOM2", null),
		entry("ROOM5", null),
		entry("ROOM8", null),
		entry("ROOM12", null),
		entry("ROOM19", null),
		entry("ROOM22", null),
		entry("ROOM26", null),
		entry("ROOM32", null)
	);
	
	private static Map<String, Room> pressettedRoomsWithExitOnLeftAndRight = Map.ofEntries(
		entry("ROOM4", null),
		entry("ROOM6", null),
		entry("ROOM7", null),
		entry("ROOM9", null),
		entry("ROOM10", null),
		entry("ROOM11", null),
		entry("ROOM15", null),
		entry("ROOM16", null),
		entry("ROOM20", null),
		entry("ROOM21", null),
		entry("ROOM24", null),
		entry("ROOM25", null),
		entry("ROOM27", null),
		entry("ROOM30", null),
		entry("ROOM31", null)
	);
	
	public static Room getRoom(String roomID, Room.ExitLayout roomLayout)
	{
		return switch(roomLayout) {
			case Room.ExitLayout.ONLEFT         -> pressettedRoomsWithExitOnLeft.get(roomID);
			case Room.ExitLayout.ONRIGHT        -> pressettedRoomsWithExitOnRight.get(roomID);
			case Room.ExitLayout.ONLEFTANDRIGHT -> pressettedRoomsWithExitOnLeftAndRight.get(roomID);
		};
	}
}
