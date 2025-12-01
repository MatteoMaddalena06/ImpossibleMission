package model.gameobject;

public class GameObjectFactory 
{
	public static GameObject produce(int symbol, int x, int y, int w, int h)
	{ 
		switch(symbol)
		{
			case FixedObject.TYPE_SOLID:
				return new FixedObject(symbol, x, y, w, h);
			case Player.TYPE_PLAYER:
				return new Player(x, y);
			//TODO altre entità

			default:
				return null;
		}
	}
}
