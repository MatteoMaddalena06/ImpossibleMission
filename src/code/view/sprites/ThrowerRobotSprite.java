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
		
		@Override
		protected int nextImageIndex(List<BufferedImage> animationList)
		{ return (getImageIndex() + 1 == animationList.size()) ? 4 : getImageIndex() + 1; }
	}
	
	public ThrowerRobotSprite(ThrowerRobot throwerRobot)
	{ super(throwerRobot, THROWER_ROBOT_ANIMATION, IMAGE_DURATION); }
}
