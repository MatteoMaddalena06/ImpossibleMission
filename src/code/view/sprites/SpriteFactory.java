package code.view.sprites;

//model import
import code.model.gameobjects.GameObject;
import code.model.gameobjects.Platform;
import code.model.gameobjects.Player;
import code.model.gameobjects.Terminal;
import code.model.gameobjects.enemy.AttackerRobot;
import code.model.gameobjects.enemy.BlackOrb;
import code.model.gameobjects.enemy.JumperRobot;
import code.model.gameobjects.enemy.LaserRobot;
import code.model.gameobjects.enemy.RunnerRobot;
import code.model.gameobjects.enemy.ThrowerRobot;
import code.model.room.Room;
import code.model.gameobjects.FixedObject;
import code.model.gameobjects.Furniture;

public abstract class SpriteFactory 
{
	public static Sprite produce(GameObject gameObject)
	{
		if(gameObject instanceof AttackerRobot.Attack && ((AttackerRobot.Attack)gameObject).getAttacker() instanceof LaserRobot)
			return new LaserRobotSprite.AttackSprite((AttackerRobot.Attack)gameObject);
		
		if(gameObject instanceof AttackerRobot.Attack && ((AttackerRobot.Attack)gameObject).getAttacker() instanceof ThrowerRobot)
			return new ThrowerRobotSprite.AttackSprite((AttackerRobot.Attack)gameObject);
		
		if(gameObject instanceof Player) 	   return new PlayerSprite((Player)gameObject);
		if(gameObject instanceof RunnerRobot)  return new RunnerRobotSprite((RunnerRobot)gameObject);
		if(gameObject instanceof Terminal)     return new TerminalSprite((Terminal)gameObject);
		if(gameObject instanceof ThrowerRobot) return new ThrowerRobotSprite((ThrowerRobot)gameObject);
		if(gameObject instanceof LaserRobot)   return new LaserRobotSprite((LaserRobot)gameObject);
		if(gameObject instanceof JumperRobot)  return new JumperRobotSprite((JumperRobot)gameObject);
		if(gameObject instanceof BlackOrb)     return new BlackOrbSprite((BlackOrb)gameObject);
		if(gameObject instanceof Platform)     return new PlatformSprite((Platform)gameObject);
		
		throw new IllegalArgumentException("Unable to instantiate sprite for type " + gameObject.getClass().getSimpleName());
	}
	
	public static Sprite produce(GameObject gameObject, Room.Color color)
	{
		if(gameObject instanceof FixedObject && ((FixedObject)gameObject).getType() == FixedObject.Type.FLOOR)
			return new FloorSprite((FixedObject)gameObject, color);
		
		if(gameObject instanceof FixedObject && ((FixedObject)gameObject).getType() == FixedObject.Type.WALL)
			return new WallSprite((FixedObject)gameObject, color);
		
		if(gameObject instanceof Furniture)
			return new FurnitureSprite((Furniture)gameObject, color);
		
		throw new IllegalArgumentException("Unable to instantiate sprite for type " + gameObject.getClass().getSimpleName() + "with " + color + " color");
	}
}
