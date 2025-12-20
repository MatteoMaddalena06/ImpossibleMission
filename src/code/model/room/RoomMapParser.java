package code.model.room;

//data structure modules
import java.util.List;
import java.util.Stack;
import java.util.LinkedList;

//inproject import
import code.model.gameobject.GameObject;
import code.model.gameobject.GameObjectFactory;
import code.model.utils.Point;

abstract class RoomMapParser
{	
	public static List<GameObject> parse(RoomMap room)
	{
		List<GameObject> result = new LinkedList<GameObject>();
		
		int[][] map = room.getMap();
		int rows = map.length, cols  = map[0].length;
		int tileSize = RoomMap.TILE_SIZE, emptySpace = RoomMap.EMPTY_SPACE;
		boolean[][] visited = new boolean[rows][cols];
		
		for(int y = 0; y < rows; y++)
		{
			for(int x = 0; x < cols; x++)
			{
				int type = map[y][x];
				
				if(type == emptySpace || visited[y][x]) continue;
				
				List<Point> cluster = floodFill(map, x, y, visited, type);
				
				int minX = cluster.stream().mapToInt(p -> p.getX()).min().getAsInt();
				int maxX = cluster.stream().mapToInt(p -> p.getX()).max().getAsInt();
				int minY = cluster.stream().mapToInt(p -> p.getY()).min().getAsInt();
				int maxY = cluster.stream().mapToInt(p -> p.getY()).max().getAsInt();
				
				int realX  = x * tileSize, realY = y * tileSize; 
				int width  = (maxX - minX + 1) * tileSize;
				int height = (maxY - minY + 1) * tileSize;
				
				result.add(GameObjectFactory.produce(type, new Point(realX, realY), width, height));
			}
		}
	
		return result;
	}
	
	private static List<Point> floodFill(int[][] map, int x, int y, boolean[][] visited, int type)
	{
		List<Point> cluster = new LinkedList<Point>();
		
		Stack<Point> stack = new Stack<Point>();
		int rows = map.length, cols = map[0].length;
		
		stack.push(new Point(x, y));
		
		while(!stack.empty())
		{
			Point currentPoint = stack.pop();
			int currX = currentPoint.getX(), currY = currentPoint.getY();
			
			if(currX < 0 || currY < 0 || currX >= cols || currY >= rows || visited[currY][currX] || map[currY][currX] != type) 
				continue;
			
			visited[currY][currX] = true;
			cluster.add(currentPoint);
			
			stack.push(new Point(currX + 1, currY));
			stack.push(new Point(currX,     currY + 1));
			stack.push(new Point(currX - 1, currY));		
			stack.push(new Point(currX,     currY - 1));
		}
			
		return cluster;	
	}
}
