package code.view.sprites;

//model import
import code.model.gameobjects.enemy.RunnerRobot;
//images import
import code.view.images.Animation;

/** Classe per la sprite del robot che corre */
public class RunnerRobotSprite extends AnimatedSprite
{
	/** L'animazione della sprite */
	private static final Animation RUNNER_ROBOT_ANIMATION = Animation.RUNNER_ROBOT;
	/** La durata dei frame dell'animazione */
	private static final double    IMAGE_DURATION   = 0.1f; 
	
	/**
	 * Costruice la classe
	 * @param runnerRobot
	 * il robot a cui associare la sprite
	 */
	public RunnerRobotSprite(RunnerRobot runnerRobot)
	{ super(runnerRobot, RUNNER_ROBOT_ANIMATION, IMAGE_DURATION); }
}