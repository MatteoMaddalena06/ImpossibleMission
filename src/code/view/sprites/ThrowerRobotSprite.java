package code.view.sprites;

//data strucutre import
import java.util.List;
//graphics import
import java.awt.image.BufferedImage;
//model import
import code.model.gameobjects.enemy.ThrowerRobot;
import code.model.gameobjects.enemy.AttackerRobot;
//images import
import code.view.images.Animation;

/** Classe per la sprite del robot che lancia gli attacchi */
public class ThrowerRobotSprite extends AnimatedSprite
{
	/** L'animazione della sprite */
	private static final Animation THROWER_ROBOT_ANIMATION = Animation.THROWER_ROBOT;
	/** La durata dei frame dell'animazione */
	private static final double    IMAGE_DURATION   = 0.12f;
	
	/** Classe per la sprite dell'attacco del del robot che lancia gli attacchi*/
	public static class AttackSprite extends AnimatedSprite
	{
		/** L'animazione della sprite */
		private static final Animation THROWER_ROBOT_ATTACK_ANIMATION = Animation.THROWER_ROBOT_ATTACK;
		/** La durata dei frame dell'animazione */
		private static final double    IMAGE_DURATION   = 0.05f;
		
		/**
		 * Costruice la classe
		 * @param attack
		 * l'attacco a cui associare la sprite
		 */
		public AttackSprite(AttackerRobot.Attack attack)
		{ super(attack, THROWER_ROBOT_ATTACK_ANIMATION, IMAGE_DURATION); }
		
		@Override
		protected int nextImageIndex(List<BufferedImage> animationList)
		{ return (getImageIndex() + 1 == animationList.size()) ? 4 : getImageIndex() + 1; }
	}
	
	/**
	 * Costruice la classe
	 * @param attack
	 * il robot a cui associare la sprite
	 */
	public ThrowerRobotSprite(ThrowerRobot throwerRobot)
	{ super(throwerRobot, THROWER_ROBOT_ANIMATION, IMAGE_DURATION); }
}
