package code.model.gameobject.enemy;

//inproject import
import code.model.utils.Point;

public abstract class EnemyFactory 
{
	public enum Type 
	{ RUNNER, JUMPER, THROWER, LASER, BLACKORB }
	
	public static Enemy produce(Point position, int width, int height, Type enemyType)
	{
		return switch(enemyType) {
			case Type.RUNNER   -> new RunnerRobot(position, width, height);
			case Type.JUMPER   -> new JumperRobot(position, width, height);
			case Type.THROWER  -> new ThrowerRobot(position, width, height);
			case Type.LASER    -> new LaserRobot(position, width, height);
			case Type.BLACKORB -> new BlackOrb(position, width, height);
		};
	}
}
