package model.gameobject;

//inproject import
import model.room.RoomMap;

public abstract class GameObjectFactory 
{	
	private static final String FACTORY_ERROR = "Unexpected value: ";
	
	public static GameObject produce(int type, int x, int y, int width, int height)
	{ 
		return switch(type) {
			case RoomMap.WALL_ID      -> new FixedObject(type, x, y, width, height);
			case RoomMap.FLOOR_ID     -> new FixedObject(type, x, y, width, height);
			case RoomMap.PLATFORM_ID  -> new Platform(x, y, width, height);
			case RoomMap.ROBOT_ID     -> new Robot(x, y, width, height);
			case RoomMap.FURNITURE_ID -> new Furniture(x, y, width, height);
			case RoomMap.TERMINAL_ID  -> new Terminal(x, y, width, height);
			case RoomMap.BLACK_ORB_ID -> new BlackOrb(x, y, width, height);
			default -> throw new IllegalArgumentException(FACTORY_ERROR + type);
		};
	}
}
