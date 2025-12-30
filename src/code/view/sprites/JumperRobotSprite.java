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

public class JumperRobotSprite extends AnimatedSprite
{
	private static final Animation JUMPER_ROBOT_ANIMATION = Animation.JUMPER_ROBOT;
	private static final double    IMAGE_DURATION   = 0.12f; 
	
	public JumperRobotSprite(JumperRobot jumperRobot)
	{ super(jumperRobot, JUMPER_ROBOT_ANIMATION, IMAGE_DURATION); }
	
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
