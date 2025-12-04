package model.gameobject;

//inproject import
import model.room.RoomMap;

public abstract class GameObjectFactory 
{	
	private static final String FACTORY_ERROR = "Unexpected value: ";
	
	public static GameObject produce(int type, Point point, int width, int height)
	{ 
		return switch(type) {
			case RoomMap.WALL_ID      -> new FixedObject(type, new Point(x, y), width, height);
			case RoomMap.FLOOR_ID     -> new FixedObject(type, new Point(x, y), width, height);
			case RoomMap.PLATFORM_ID  -> new Platform(new Point(x, y), width, height);
			case RoomMap.ROBOT_ID     -> new Robot(new Point(x, y), width, height);
			case RoomMap.FURNITURE_ID -> new Furniture(point, width, height, Furniture.Type.RANDOM);
			case RoomMap.TERMINAL_ID  -> new Terminal(new Point(x, y), width, height);
			case RoomMap.BLACK_ORB_ID -> new BlackOrb(new Point(x, y), width, height);
			default -> throw new IllegalArgumentException(FACTORY_ERROR + type);
		};
	}
}
