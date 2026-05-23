package code.view.sprites;

//data structures import
import java.util.List;
//graphics import
import java.awt.image.BufferedImage;
//model import
import code.model.gameobjects.enemy.JumperRobot;
import code.model.gameobjects.MovingObject;
//images import
import code.view.images.Animation;

/** Class per la sprite del robot che salta */
public class JumperRobotSprite extends AnimatedSprite
{
	/** L'animazione della sprite */
	private static final Animation JUMPER_ROBOT_ANIMATION = Animation.JUMPER_ROBOT;
	/** La durata dei frame dell'animazione*/
	private static final double    IMAGE_DURATION   = 0.12f; 
	
	/**
	 * Costruice la classe
	 * @param jumperRobot
	 * il robot a cui associare la sprite
	 */
	public JumperRobotSprite(JumperRobot jumperRobot)
	{ super(jumperRobot, JUMPER_ROBOT_ANIMATION, IMAGE_DURATION); }
	
	/** 
	 * Stabilisce il prossimo indice del frame nell'animazione corrente seguendo la logica dell'animazione del robot che salta
	 * @param animationList
	 * la lista dei frame
	 * @return
	 * l'indice stabilito
	 */
	@Override
	protected int nextImageIndex(List<BufferedImage> animationList)
	{
		JumperRobot bindedJumperRobot = (JumperRobot)getGameObject();
		MovingObject.PhysicsState state = bindedJumperRobot.getPhysicsState();
		
		int animationSize = animationList.size();
		int nextIndex = getImageIndex() + 1;
		
		if((state == MovingObject.PhysicsState.FALLING && nextIndex == animationSize) || (state == MovingObject.PhysicsState.JUMPING && nextIndex == 5))
			nextIndex--;

		else
			nextIndex %= animationSize;
		
		return nextIndex;
	}
}
