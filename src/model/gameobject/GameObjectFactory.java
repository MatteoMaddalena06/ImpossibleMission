package model.gameobject;

//inproject import
import model.room.RoomMap;
import model.utils.Point;
import model.gameobject.enemy.BlackOrb;
import model.gameobject.enemy.RunnerRobot;

public abstract class GameObjectFactory 
{	
	private static final String FACTORY_ERROR = "Unexpected value: ";
	
	public static GameObject produce(int type, Point position, int width, int height)
	{ 
		return switch(type) {
			case RoomMap.WALL_ID      -> new FixedObject(FixedObject.Type.WALL, position, width, height);
			case RoomMap.FLOOR_ID     -> new FixedObject(FixedObject.Type.FLOOR, position, width, height);
			case RoomMap.PLATFORM_ID  -> new Platform(position, width, height);
			case RoomMap.ROBOT_ID     -> new RunnerRobot(position, width, height);
			case RoomMap.FURNITURE_ID -> new Furniture(position, width, height, Furniture.Type.RANDOM);
			case RoomMap.TERMINAL_ID  -> new Terminal(position, width, height);
			case RoomMap.BLACK_ORB_ID -> new BlackOrb(position, width, height);
			default -> throw new IllegalArgumentException(FACTORY_ERROR + type);
		};
	}
}
