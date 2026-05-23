package code.model.gameobjects.enemy;

import code.model.Point;

/** Classe per la factory di nemici */
public abstract class EnemyFactory 
{
	/** Enumerazione per i nemici che si possono produrre nella {@link EnemyFactory} */
	public enum Type 
	{ RUNNER, JUMPER, THROWER, LASER, BLACKORB }
	
	/**
	 * Prodcue un nemico
	 * @param position
	 * la posizione del nemico
	 * @param width
	 * la larghezza
	 * @param height
	 * l'altezza
	 * @param enemyType
	 * il tipo di nemico
	 * @return
	 * l'istanza del nemico
	 */
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
