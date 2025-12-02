package model.gameobject;

public class GameObjectFactory 
{
	public static GameObject produce(int symbol, int x, int y, int w, int h)
	{ 
		switch(symbol)
		{
			case FixedObject.TYPE_FLOOR:
			case FixedObject.TYPE_WALL:
				return new FixedObject(symbol, x, y, w, h);

			case Player.TYPE_PLAYER:
				return new Player(x, y);

			case Robot.TYPE_ROBOT:
				return new Robot(x, y);

			case BlackOrb.TYPE_BLACKORB:
				return new BlackOrb(x, y);

			case Furniture.TYPE_DESK:
			case Furniture.TYPE_BOOKSHELF:
			case Furniture.TYPE_TERMINAL:
			case Furniture.TYPE_VENDING_MACHINE:
				return new Furniture(symbol, x, y, w, h);

			//TODO: movingPlatform 
			
			default:
				return null;
		}
	}
}
