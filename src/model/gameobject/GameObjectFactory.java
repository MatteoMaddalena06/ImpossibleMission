package model.gameobject;

public class GameObjectFactory 
{
	public static GameObject produce(int symbol, int x, int y, int w, int h)
	{ 
		return new GameObject(symbol, x, y, w, h);
	}
}
