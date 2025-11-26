package model.room;

import java.util.LinkedList;
import java.util.Map;
import static java.util.Map.entry;

//inproject import
import model.GameObject;
import model.Robot;

abstract class PresettedRoomFactory
{
	//need gameObjectList implementation
	private static Map<String, RoomPrototype> pressettedRoomsWithExitOnLeft = Map.ofEntries(
		entry("Room1",  new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room3",  new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room13", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room14", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room17", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room18", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room23", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room28", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room29", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN))
	);
	
	private static Map<String, RoomPrototype> pressettedRoomsWithExitOnRight = Map.ofEntries(
		entry("Room2",  new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room5",  new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room8",  new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room12", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room19", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room22", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room26", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room32", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN))
	);
	
	private static Map<String, RoomPrototype> pressettedRoomsWithExitOnLeftAndRight = Map.ofEntries(
		entry("Room4",  new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room6",  new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room7",  new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room9",  new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room10", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room11", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room15", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room16", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room20", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room21", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room24", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room25", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room27", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room30", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN)),
		entry("Room31", new RoomPrototype(new LinkedList<GameObject>(), new LinkedList<Robot>(), Room.Color.CYAN))
	);
	
	public static Room getRoom(String roomID, Room.ExitLayout roomLayout) throws Room.RoomNotFoundException
	{
		RoomPrototype prototype = switch(roomLayout) {
			case Room.ExitLayout.ONLEFT         -> pressettedRoomsWithExitOnLeft.get(roomID);
			case Room.ExitLayout.ONRIGHT        -> pressettedRoomsWithExitOnRight.get(roomID);
			case Room.ExitLayout.ONLEFTANDRIGHT -> pressettedRoomsWithExitOnLeftAndRight.get(roomID);
		};
		
		if(prototype == null)
			throw new Room.RoomNotFoundException(roomID + " with " + roomLayout + " layout not found");
		
		return new Room(prototype.gameObjectList(), prototype.robotList(), prototype.color());
	}
}
