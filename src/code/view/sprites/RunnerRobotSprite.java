package code.view.sprites;

//model import
import code.model.gameobjects.enemy.RunnerRobot;
//images import
import code.view.images.Animation;

public class RunnerRobotSprite extends AnimatedSprite
{
	private static final Animation RUNNER_ROBOT_ANIMATION = Animation.RUNNER_ROBOT;
	private static final double    IMAGE_DURATION   = 0.1f; 
	
	public RunnerRobotSprite(RunnerRobot player)
	{ super(player, RUNNER_ROBOT_ANIMATION, IMAGE_DURATION); }
}