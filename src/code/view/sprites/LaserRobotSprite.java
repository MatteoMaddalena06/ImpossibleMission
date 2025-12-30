package code.view.sprites;

//model import
import code.model.gameobjects.enemy.AttackerRobot;
import code.model.gameobjects.enemy.LaserRobot;
//images import
import code.view.images.Animation;

public class LaserRobotSprite extends AnimatedSprite
{
	private static final Animation LASER_ROBOT_ANIMATION = Animation.LASER_ROBOT;
	private static final double    IMAGE_DURATION   = 0.12f; 
	
	public static class AttackSprite extends AnimatedSprite
	{
		private static final Animation LASER_ROBOT_ATTACK_ANIMATION = Animation.LASER_ROBOT_ATTACK;
		private static final double    IMAGE_DURATION   = 0.05f;
		
		public AttackSprite (AttackerRobot.Attack attack)
		{ super(attack, LASER_ROBOT_ATTACK_ANIMATION, IMAGE_DURATION); }
	}
	
	public LaserRobotSprite(LaserRobot laserRobot)
	{ super(laserRobot, LASER_ROBOT_ANIMATION , IMAGE_DURATION); }
}