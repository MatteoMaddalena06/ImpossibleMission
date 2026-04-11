package code.model.gameobjects;

//data strucutre modeules
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;

import code.model.Point;
import code.model.gameobjects.enemy.EnemyFactory;
//inproject import
import code.model.room.RoomMap;

public abstract class GameObjectFactory 
{	
	private static final String FACTORY_ERROR = "Unexpected value: ";
	
	public static GameObject produce(int type, Point position, int width, int height)
	{ 
		return switch(type) {
			case RoomMap.WALL_ID      -> new FixedObject(FixedObject.Type.WALL, position, width, height);
			case RoomMap.FLOOR_ID     -> new FixedObject(FixedObject.Type.FLOOR, position, width, height);
			case RoomMap.PLATFORM_ID  -> new Platform(position, width, height);
			case RoomMap.ROBOT_ID     -> {
				List<EnemyFactory.Type> wantedEnemyType = new LinkedList<EnemyFactory.Type>(Arrays.asList(EnemyFactory.Type.values()));
				wantedEnemyType.remove(EnemyFactory.Type.BLACKORB);
				Collections.shuffle(wantedEnemyType);
				EnemyFactory.Type rndEnemyType = wantedEnemyType.getFirst();
				
				yield EnemyFactory.produce(position, width, height / 2, rndEnemyType);
			}
			case RoomMap.FURNITURE_ID -> new Furniture(position, width, height, Furniture.Type.RANDOM);
			case RoomMap.TERMINAL_ID  -> new Terminal(position, width, height);
			case RoomMap.BLACK_ORB_ID -> EnemyFactory.produce(position, width, height, EnemyFactory.Type.BLACKORB);
			default -> throw new IllegalArgumentException(FACTORY_ERROR + type);
		};
	}
}
