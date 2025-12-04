package model.world;

//data structure modules
import java.util.List;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//IO modules
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;

//inproject import
import model.room.Room;
import model.room.PresettedRoom;
import model.gameobject.Point;
import model.puzzle.PresettedPassword;
import model.puzzle.PuzzlePiece;
import model.gameobject.Furniture;

public class GameWorld implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private static final int STD_ELEVATOR_NUMBER       = 8;
	private static final int STD_WORLD_DEPTH           = 6;
	private static final int STD_MAX_ROBOT_PASSWORD    = 10;
	private static final int STD_MAX_PLATFORM_PASSWORD = 10;

	private Room[][] worldMatrix;

	public GameWorld(Room[][] worldMatrix)
	{ this.worldMatrix = worldMatrix; }
	
	public static GameWorld load(Path pathname) throws IOException, ClassNotFoundException 
	{
		FileInputStream fileInput = new FileInputStream(pathname.toFile());
		ObjectInputStream objectInput = new ObjectInputStream(fileInput);
		
		GameWorld world = (GameWorld)objectInput.readObject();
		objectInput.close();
		
		return world;
	}
	
	public void store(Path pathname) throws IOException
	{
		FileOutputStream fileOutput = new FileOutputStream(pathname.toFile());
		ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
		
		objectOutput.writeObject(this);
		objectOutput.close();
	}

	public static GameWorld randomGeneration()
	{
		Room[][] worldMatrix = new Room[STD_WORLD_DEPTH][(STD_ELEVATOR_NUMBER << 1) + 1];
		
		List<List<Furniture>> furnituresPerRoom = createTraversableRandomMap(worldMatrix);
		PresettedPassword randomPassword = PresettedPassword.values()[(int)(Math.random() * PresettedPassword.PASSWORD_NUMBER)];
		makeTheMapPlayable(worldMatrix, furnituresPerRoom, randomPassword);
		
		return new GameWorld(worldMatrix);
	}
	
	private static List<List<Furniture>> createTraversableRandomMap(Room[][] worldMatrix)
	{
		int rows = worldMatrix.length, cols = worldMatrix[0].length;
		 
		List<Point> pointToRemove = new LinkedList<Point>();
		int leftRoomCounter = 0, rightRoomCounter = 0, leftRightRoomCounter = 0;
	 
		List<List<Furniture>> furnituresPerRoom = new LinkedList<List<Furniture>>();
			 
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
		 
			List<Furniture> furnitureListCopy = worldMatrix[y][x].getFurnitureList();
			Collections.shuffle(furnitureListCopy);
			furnituresPerRoom.add(furnitureListCopy);
		}
	 
		List<Point> pointToUse = IntStream.range(0, cols).filter(x -> x % 2 == 0).mapToObj(
				x -> IntStream.range(0, rows).mapToObj(y -> new Point(x, y))).flatMap(p -> p).collect(Collectors.toList());
		pointToUse.removeAll(pointToRemove);
		Collections.shuffle(pointToUse);
	 		 
		while(leftRoomCounter + rightRoomCounter + leftRightRoomCounter < PresettedRoom.ROOM_NUMBER)
		{
			Point point = pointToUse.remove(0);
			int x = point.getX(), y = point.getY();
		 
			if(x == 0 && rightRoomCounter < PresettedRoom.RIGHT_ROOM_NUMBER)
				worldMatrix[y][x] = PresettedRoom.getRoom(Room.ExitLayout.ONRIGHT, rightRoomCounter++);
		 
			else if(x == cols - 1 && leftRoomCounter < PresettedRoom.LEFT_ROOM_NUMBER)
				worldMatrix[y][x] = PresettedRoom.getRoom(Room.ExitLayout.ONLEFT, leftRoomCounter++);
		 
			else if(x != 0 && x != cols - 1)
			{
				List<Room.ExitLayout> toChoose = Arrays.asList(Room.ExitLayout.ONLEFT, Room.ExitLayout.ONRIGHT, Room.ExitLayout.ONLEFTANDRIGHT);
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
		 
			if(worldMatrix[y][x] != null)
			{
				List<Furniture> furnitureListCopy = worldMatrix[y][x].getFurnitureList();
				Collections.shuffle(furnitureListCopy);
				furnituresPerRoom.add(furnitureListCopy);
			}
		}
	 
		return furnituresPerRoom;
	}
	
	private static void makeTheMapPlayable(Room[][] worldMatrix, List<List<Furniture>> furnituresPerRoom, PresettedPassword password)
	{
		distributeInFurniture(furnituresPerRoom, PuzzlePiece.getPieces(password.getPassword()));
		distributeInFurniture(furnituresPerRoom, (int)(Math.random() * STD_MAX_ROBOT_PASSWORD) + 1, Furniture.LootType.ROBOT_PASSWORD);
		distributeInFurniture(furnituresPerRoom, (int)(Math.random() * STD_MAX_PLATFORM_PASSWORD) + 1, Furniture.LootType.PLATFORM_PASSWORD);
	}
	
	private static List<Furniture> distributeInFurniture(List<List<Furniture>> furnituresPerRoom, int amount, Furniture.LootType type)
	{
		List<Furniture> visitedFurniture = new ArrayList<Furniture>();
		int index = 0;
		
		while(index < amount)
		{
			int randomRoom = (int)(Math.random() * furnituresPerRoom.size());
			
			if(furnituresPerRoom.get(randomRoom).size() == 0)
			{
				furnituresPerRoom.remove(randomRoom);
				continue;
			}
			
			Furniture chosenFurniture = furnituresPerRoom.get(randomRoom).remove(0);
			chosenFurniture.setContent(type);
			visitedFurniture.add(chosenFurniture);
			index++;
		}
		
		return visitedFurniture;
	}
	
	private static void distributeInFurniture(List<List<Furniture>> furnituresPerRoom, PuzzlePiece[] puzzlePieces)
	{ 
		List<Furniture> visitedFurniture =  distributeInFurniture(furnituresPerRoom, puzzlePieces.length, Furniture.LootType.PUZZLE_PIECE); 
		IntStream.range(0, visitedFurniture.size()).forEach(i -> visitedFurniture.get(i).setPuzzlePiece(puzzlePieces[i]));
	}
	
	public Room[][] getWorldMatrix()
	{ return worldMatrix; }
	
	/* remove comment for debugging
	@Override
	public String toString()
	{
		String out = "";
		
		for(int i = 0; i < worldMatrix.length; i++)
		{
			for(int j = 0; j < worldMatrix[0].length; j++)
			{
				out += worldMatrix[i][j] + " ";
				
				if(worldMatrix[i][j] == null)
					out += "          ";
				
				else if(worldMatrix[i][j].getExitLayout() == Room.ExitLayout.ONLEFT)
					out += "        ";
				
				else if(worldMatrix[i][j].getExitLayout() == Room.ExitLayout.ONRIGHT)
					out += "       ";			
			}

			out += "\n";
		}
		
		return out;
	}
	*/
}
