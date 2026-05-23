package code.view.sprites;

//model import
import code.model.gameobjects.enemy.AttackerRobot;
import code.model.gameobjects.enemy.LaserRobot;
//images import
import code.view.images.Animation;

/** Classe per la sprite del laser robot */
public class LaserRobotSprite extends AnimatedSprite
{
	/** L'animazione della sprite */
	private static final Animation LASER_ROBOT_ANIMATION = Animation.LASER_ROBOT;
	/** La durata dei frame dell'animazione */
	private static final double    IMAGE_DURATION   = 0.12f; 
	
	/** Classe per la sprite dell'attacco del laser robot */
	public static class AttackSprite extends AnimatedSprite
	{
		/** L'animazione della sprite */
		private static final Animation LASER_ROBOT_ATTACK_ANIMATION = Animation.LASER_ROBOT_ATTACK;
		/** La durata dei frame dell'animazione */
		private static final double    IMAGE_DURATION   = 0.05f;
		
		/**
		 * Costruisce la classe
		 * @param attack
		 * l'attacco a cui associare la sprite
		 */
		public AttackSprite(AttackerRobot.Attack attack)
		{ super(attack, LASER_ROBOT_ATTACK_ANIMATION, IMAGE_DURATION); }
	}
	
	/**
	 * Costruisce la classe
	 * @param attack
	 * il robot a cui associare la sprite
	 */
	public LaserRobotSprite(LaserRobot laserRobot)
	{ super(laserRobot, LASER_ROBOT_ANIMATION , IMAGE_DURATION); }
}