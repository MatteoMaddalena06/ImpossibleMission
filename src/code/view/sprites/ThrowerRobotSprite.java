package code.view.sprites;

import code.model.gameobjects.enemy.AttackerRobot;
//model import
import code.model.gameobjects.enemy.ThrowerRobot;
//images import
import code.view.images.Animation;

public class ThrowerRobotSprite extends AnimatedSprite
{
	private static final Animation THROWER_ROBOT_ANIMATION = Animation.THROWER_ROBOT;
	private static final double    IMAGE_DURATION   = 0.12f;
	
	public static class AttackSprite extends AnimatedSprite
	{
		private static final Animation THROWER_ROBOT_ATTACK_ANIMATION = Animation.THROWER_ROBOT_ATTACK;
		private static final double    IMAGE_DURATION   = 0.05f;
		
		public AttackSprite (AttackerRobot.Attack attack)
		{ super(attack, THROWER_ROBOT_ATTACK_ANIMATION, IMAGE_DURATION); }
	}
	
	public ThrowerRobotSprite(ThrowerRobot player)
	{ super(player, THROWER_ROBOT_ANIMATION, IMAGE_DURATION); }
}
