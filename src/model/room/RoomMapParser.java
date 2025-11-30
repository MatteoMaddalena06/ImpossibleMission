package model.room;

import java.util.List;
import java.util.Stack;
import java.util.LinkedList;

//inproject import
import model.gameobject.GameObject;
import model.gameobject.GameObjectFactory;
import model.gameobject.Point;

abstract class RoomMapParser
{	
	public static List<GameObject> parse(RoomMap room)
	{
		List<GameObject> result = new LinkedList<GameObject>();
		
		int[][] map = room.getMap();
		int rows = map.length, cols  = map[0].length;
		int tileSize = room.getTileSize(), emptySpace = room.getEmptySpace();
		boolean[][] visited = new boolean[rows][cols];
		
		for(int y = 0; y < rows; y++)
		{
			for(int x = 0; x < cols; x++)
			{
				int type = map[y][x];
				
				if(type == emptySpace || visited[y][x]) continue;
				
				List<Point> cluster = floodFill(map, x, y, visited, type);
				
				int minX = cluster.stream().mapToInt(p -> p.x()).min().getAsInt();
				int maxX = cluster.stream().mapToInt(p -> p.x()).max().getAsInt();
				int minY = cluster.stream().mapToInt(p -> p.y()).min().getAsInt();
				int maxY = cluster.stream().mapToInt(p -> p.y()).max().getAsInt();
				
				int realX  = x * tileSize, realY = y * tileSize; 
				int width  = (maxX - minX + 1) * tileSize;
				int height = (maxY - minY + 1) * tileSize;
				
				result.add(GameObjectFactory.produce(type, realX, realY, width, height));
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
			int currX = currentPoint.x(), currY = currentPoint.y();
			
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
