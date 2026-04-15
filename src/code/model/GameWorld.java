package code.model;

//data structure modules
import java.util.List;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
//inproject import
import code.model.room.Room;
import code.model.room.Room.Color;
import code.model.room.RoomMap;
import code.model.room.PresettedRoom;
import code.model.gameobjects.Furniture;
import code.model.gameobjects.GameObjectFactory;
import code.model.gameobjects.Platform;
import code.model.gameobjects.Player;
import code.model.puzzle.PresettedPassword;
import code.model.puzzle.PuzzlePiece;

public class GameWorld 
{
	private static final int ELEVATOR_NUMBER       = 8;
	private static final int WORLD_DEPTH           = 6;
	private static final int MAX_ROBOT_PASSWORD    = 10;
	private static final int MAX_PLATFORM_PASSWORD = 10;

	private Room[][] worldMatrix;

	public GameWorld()
	{ this.worldMatrix = randomGeneration(); }
	
	private static Room[][] randomGeneration()
	{
		Room[][] worldMatrix = new Room[WORLD_DEPTH][(ELEVATOR_NUMBER << 1) + 1];
		
		createTraversableRandomMap(worldMatrix);
		PresettedPassword randomPassword = PresettedPassword.values()[(int)(Math.random() * PresettedPassword.PASSWORD_NUMBER)];
		List<Furniture> allFurnitures =
				Arrays.stream(PresettedRoom.values()).flatMap(r -> r.getFurnitures().stream()).collect(Collectors.toList());
		makeTheMapPlayable(allFurnitures, randomPassword);
		
		return worldMatrix;
	}
	
	private static void createTraversableRandomMap(Room[][] worldMatrix)
	{
		int rows = worldMatrix.length, cols = worldMatrix[0].length;
		 
		List<Point> pointToRemove = new LinkedList<Point>();
		int leftRoomCounter = 0, rightRoomCounter = 0, leftRightRoomCounter = 0;
			 
		for(int x = 0; x < cols; x += 2)
		{
			int y = (int)(Math.random() * rows);
			pointToRemove.add(new Point(x, y));
		 
			if(x == 0)
				worldMatrix[y][x] = PresettedRoom.getRoom(Room.ExitLayout.ONRIGHT, rightRoomCounter++);
		 
			else if(x == cols - 1)
				worldMatrix[y][x] = PresettedRoom.getRoom(Room.ExitLayout.ONLEFT, leftRoomCounter++);
		 
			else
				worldMatrix[y][x] = PresettedRoom.getRoom(Room.ExitLayout.ONLEFTANDRIGHT, leftRightRoomCounter++);
		}
	 
		List<Point> pointToUse = IntStream.range(0, cols).filter(x -> x % 2 == 0).mapToObj(
				x -> IntStream.range(0, rows).mapToObj(y -> new Point(x, y))).flatMap(p -> p).collect(Collectors.toList());
		pointToUse.removeAll(pointToRemove);
		Collections.shuffle(pointToUse);
	 		 
		while(leftRoomCounter + rightRoomCounter + leftRightRoomCounter < PresettedRoom.ROOM_NUMBER)
		{
			Point point = pointToUse.remove(0);
			int x = (int)point.getX(), y = (int)point.getY();
		 
			if(x == 0 && rightRoomCounter < PresettedRoom.RIGHT_ROOM_NUMBER)
				worldMatrix[y][x] = PresettedRoom.getRoom(Room.ExitLayout.ONRIGHT, rightRoomCounter++);
		 
			else if(x == cols - 1 && leftRoomCounter < PresettedRoom.LEFT_ROOM_NUMBER)
				worldMatrix[y][x] = PresettedRoom.getRoom(Room.ExitLayout.ONLEFT, leftRoomCounter++);
		 
			else if(x != 0 && x != cols - 1)
			{
				List<Room.ExitLayout> toChoose = Arrays.asList(Room.ExitLayout.values());
				Collections.shuffle(toChoose);
			 
				for(Room.ExitLayout layout : toChoose)
				{
					if(layout == Room.ExitLayout.ONLEFT && leftRoomCounter < PresettedRoom.LEFT_ROOM_NUMBER)
					{ worldMatrix[y][x] = PresettedRoom.getRoom(Room.ExitLayout.ONLEFT, leftRoomCounter++); break; }
					
					if(layout == Room.ExitLayout.ONRIGHT && rightRoomCounter < PresettedRoom.RIGHT_ROOM_NUMBER)	
					{ worldMatrix[y][x] = PresettedRoom.getRoom(Room.ExitLayout.ONRIGHT, rightRoomCounter++); break; }
				 
					if(layout == Room.ExitLayout.ONLEFTANDRIGHT && leftRightRoomCounter < PresettedRoom.LEFT_RIGHT_ROOM_NUMBER)
					{ worldMatrix[y][x] = PresettedRoom.getRoom(Room.ExitLayout.ONLEFTANDRIGHT, leftRightRoomCounter++); break; }
				}	
			}	
		}
		
		for(int x = 1; x < cols; x += 2)
		{
			List<Room.Color> colors = new ArrayList<Room.Color>(Arrays.asList(Room.Color.values()));
			colors.remove(Color.ANY);
			Collections.shuffle(colors);
			Room.Color randomColor = colors.get(0);
			
			for(int y = 0; y < rows; y++)
			{
				if(worldMatrix[y][x - 1] == null && worldMatrix[y][x + 1] == null)
					worldMatrix[y][x] = PresettedRoom.ELEVATOR_NOEXIT.getRoom();
				
				else if(worldMatrix[y][x - 1] != null && worldMatrix[y][x + 1] == null)
					worldMatrix[y][x] = PresettedRoom.ELEVATOR_LEFT_EXIT.getRoom();
				
				else if(worldMatrix[y][x - 1] == null && worldMatrix[y][x + 1] != null)
					worldMatrix[y][x] = PresettedRoom.ELEVATOR_RIGHT_EXIT.getRoom();
				
				else
					worldMatrix[y][x] = PresettedRoom.ELEVATOR_RIGHTLEFT_EXIT.getRoom();
				
				worldMatrix[y][x].setColor(randomColor);
			}
		}
	}
	
	private static void makeTheMapPlayable(List<Furniture> furnitureList, PresettedPassword password)
	{
		Collections.shuffle(furnitureList);
		distributeInFurniture(furnitureList, PuzzlePiece.getPieces(password.getPassword()));
		distributeInFurniture(furnitureList, (int)(Math.random() * MAX_ROBOT_PASSWORD) + 1, Furniture.LootType.ROBOT_PASSWORD);
		distributeInFurniture(furnitureList, (int)(Math.random() * MAX_PLATFORM_PASSWORD) + 1, Furniture.LootType.PLATFORM_PASSWORD);
	}
	
	private static List<Furniture> distributeInFurniture(List<Furniture> furnitureList, int amount, Furniture.LootType type)
	{
		List<Furniture> visitedFurniture = new ArrayList<Furniture>();
		
		for(int i = 0; i < amount; i++)
		{
			Furniture furniture = furnitureList.remove(i);
			visitedFurniture.add(furniture);
			furniture.setContent(type);
		}
		
		return visitedFurniture;
	}
	
	private static void distributeInFurniture(List<Furniture> furnitureList, PuzzlePiece[] puzzlePieces)
	{ 
		List<Furniture> visitedFurniture =  distributeInFurniture(furnitureList, puzzlePieces.length, Furniture.LootType.PUZZLE_PIECE); 
		IntStream.range(0, visitedFurniture.size()).forEach(i -> visitedFurniture.get(i).setPuzzlePiece(puzzlePieces[i]));
	}
	
	public Room getElevatorColumnAsRoom(int column, Player player)
	{
		if(column % 2 == 0)
			throw new IllegalArgumentException("worldMatrix[][" + column + "] is not an elevator column");
		
		Room result = worldMatrix[0][column];
		
		for(int y = 1; y < worldMatrix.length; y++)
			result = result.merge(worldMatrix[y][column]);
		
		double playerY = player.copyPosition().getY();
	
		result.addPlatform(
				(Platform)GameObjectFactory.produce
				(
						RoomMap.PLATFORM_ID, 
						new Point(RoomMap.ELEVATOR_X, RoomMap.ELEVATOR_Y + (int)(playerY / RoomMap.PIXELS_MAP_HEIGHT)),
						RoomMap.ELEVATOR_WIDTH, RoomMap.ELEVATOR_HEIGHT
				)
		);
		
		return result;
	}
	
	public Room[][] getWorldMatrix()
	{ return worldMatrix; }
}
