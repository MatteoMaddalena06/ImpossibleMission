package code.view.images;

//data strucute import
import java.util.Map;
import static java.util.Map.entry;
import java.util.List;
//grphics import
import java.awt.image.BufferedImage;

public enum Animation
{
	PLAYER(Map.ofEntries(
			entry(State.WALKING_RIGHT, ImageUtils.loadAnimation("/resoruces/Player/Walking/Jot_0-%d.png", 38, 52)),
			entry(State.WALKING_LEFT,  ImageUtils.loadFlippedAnimation("/resoruces/Player/Walking/Jot_0-%d.png", 38, 52)),
			entry(State.JUMPING_RIGHT, ImageUtils.loadAnimation("/resoruces/Player/Jumping/Jot_0-%d.png", 86, 89)),
			entry(State.JUMPING_LEFT,  ImageUtils.loadFlippedAnimation("/resoruces/Player/Jumping/Jot_0-%d.png", 86, 89)),
			entry(State.FALLING_RIGHT, ImageUtils.loadAnimation("/resoruces/Player/Falling/Jot_0-%d.png", 90, 95)),
			entry(State.FALLING_LEFT,  ImageUtils.loadFlippedAnimation("/resoruces/Player/Falling/Jot_0-%d.png", 90, 95)),
			entry(State.IDLE_RIGHT,    ImageUtils.loadAnimation("/resoruces/Player/Idle/Jot_0-%d.png", 0, 9)), 
			entry(State.IDLE_LEFT,     ImageUtils.loadFlippedAnimation("/resoruces/Player/Idle/Jot_0-%d.png", 0, 9)), 
			entry(State.DIE_RIGHT,     ImageUtils.loadAnimation("/resoruces/Player/Die/Jot_0-%d.png", 437, 448)),
			entry(State.DIE_LEFT,      ImageUtils.loadFlippedAnimation("/resoruces/Player/Die/Jot_0-%d.png", 437, 448))
	)),
	BLACKORB(Map.ofEntries(
			entry(State.WALKING_RIGHT, ImageUtils.loadAnimation("/resoruces/Enemies/BlackOrb/Walking/PS_0-%d.png", 0, 4)),
			entry(State.WALKING_LEFT,  ImageUtils.loadFlippedAnimation("/resoruces/Enemies/BlackOrb/Walking/PS_0-%d.png", 0, 4)),
			entry(State.JUMPING_RIGHT, ImageUtils.loadAnimation("/resoruces/Enemies/BlackOrb/Jumping/PS_0-%d.png", 158, 165)),
			entry(State.JUMPING_LEFT,  ImageUtils.loadFlippedAnimation("/resoruces/Enemies/BlackOrb/Jumping/PS_0-%d.png", 158, 165)),
			entry(State.FALLING_RIGHT, ImageUtils.loadAnimation("/resoruces/Enemies/BlackOrb/Falling/PS_0-%d.png", 158, 165)),
			entry(State.FALLING_LEFT,  ImageUtils.loadFlippedAnimation("/resoruces/Enemies/BlackOrb/Falling/PS_0-%d.png", 158, 165)),
			entry(State.IDLE_RIGHT,    ImageUtils.loadAnimation("/resoruces/Enemies/BlackOrb/Idle/PS_0-%d.png", 35, 39)), 
			entry(State.IDLE_LEFT,     ImageUtils.loadFlippedAnimation("/resoruces/Enemies/BlackOrb/Idle/PS_0-%d.png", 35, 39))
	)),
	JUMPER_ROBOT(Map.ofEntries(
			entry(State.WALKING_RIGHT, ImageUtils.loadAnimation("/resoruces/Enemies/JumperRobot/Walking/Khan_0-%d.png", 29, 54)),
			entry(State.WALKING_LEFT,  ImageUtils.loadFlippedAnimation("/resoruces/Enemies/JumperRobot/Walking/Khan_0-%d.png", 29, 54)),
			entry(State.JUMPING_RIGHT, ImageUtils.loadAnimation("/resoruces/Enemies/JumperRobot/Jumping/Khan_0-%d.png", 60, 67)),
			entry(State.JUMPING_LEFT,  ImageUtils.loadFlippedAnimation("/resoruces/Enemies/JumperRobot/Jumping/Khan_0-%d.png", 60, 67)),
			entry(State.FALLING_RIGHT, ImageUtils.loadAnimation("/resoruces/Enemies/JumperRobot/Falling/Khan_0-%d.png", 68, 71)),
			entry(State.FALLING_LEFT,  ImageUtils.loadFlippedAnimation("/resoruces/Enemies/JumperRobot/Falling/Khan_0-%d.png", 68, 71)),
			entry(State.IDLE_RIGHT,    ImageUtils.loadAnimation("/resoruces/Enemies/JumperRobot/Idle/Khan_0-%d.png", 0, 9)), 
			entry(State.IDLE_LEFT,     ImageUtils.loadFlippedAnimation("/resoruces/Enemies/JumperRobot/Idle/Khan_0-%d.png", 0, 9))
	)),
	LASER_ROBOT(Map.ofEntries(
			entry(State.WALKING_RIGHT,   ImageUtils.loadAnimation("/resoruces/Enemies/LaserRobot/Robot/Walking/DIO_0-%d.png", 54, 69)),
			entry(State.WALKING_LEFT,    ImageUtils.loadFlippedAnimation("/resoruces/Enemies/LaserRobot/Robot/Walking/DIO_0-%d.png", 54, 69)),
			entry(State.IDLE_RIGHT,      ImageUtils.loadAnimation("/resoruces/Enemies/LaserRobot/Robot/Idle/DIO_0-%d.png", 0, 37)), 
			entry(State.IDLE_LEFT,       ImageUtils.loadFlippedAnimation("/resoruces/Enemies/LaserRobot/Robot/Idle/DIO_0-%d.png", 0, 37)),
			entry(State.ATTACKING_RIGHT, ImageUtils.loadAnimation("/resoruces/Enemies/LaserRobot/Robot/Attacking/DIO_0-%d.png", 146, 149)), 
			entry(State.ATTACKING_LEFT,  ImageUtils.loadFlippedAnimation("/resoruces/Enemies/LaserRobot/Robot/Attacking/DIO_0-%d.png", 146, 149))
	)),
	LASER_ROBOT_ATTACK(Map.ofEntries(
			entry(State.WALKING_RIGHT, ImageUtils.loadAnimation("/resoruces/Enemies/LaserRobot/Attack/TheWorld_0-%d.png", 240, 252))
	)),
	RUNNER_ROBOT(Map.ofEntries(
			entry(State.WALKING_RIGHT, ImageUtils.loadAnimation("/resoruces/Enemies/RunnerRobot/Walking/Hol_0-%d.png", 35, 40)),
			entry(State.WALKING_LEFT,  ImageUtils.loadFlippedAnimation("/resoruces/Enemies/RunnerRobot/Walking/Hol_0-%d.png", 35, 40)),
			entry(State.IDLE_RIGHT,    ImageUtils.loadAnimation("/resoruces/Enemies/RunnerRobot/Idle/Hol_0-%d.png", 0, 9)), 
			entry(State.IDLE_LEFT,     ImageUtils.loadFlippedAnimation("/resoruces/Enemies/RunnerRobot/Idle/Hol_0-%d.png", 0, 9))
	)),
	THROWER_ROBOT(Map.ofEntries(
			entry(State.WALKING_RIGHT,   ImageUtils.loadAnimation("/resoruces/Enemies/ThrowerRobot/Robot/Walking/Pol_0-%d.png", 32, 42)),
			entry(State.WALKING_LEFT,    ImageUtils.loadFlippedAnimation("/resoruces/Enemies/ThrowerRobot/Robot/Walking/Pol_0-%d.png", 32, 42)),
			entry(State.IDLE_RIGHT,      ImageUtils.loadAnimation("/resoruces/Enemies/ThrowerRobot/Robot/Idle/Pol_0-%d.png", 0, 9)), 
			entry(State.IDLE_LEFT,       ImageUtils.loadFlippedAnimation("/resoruces/Enemies/ThrowerRobot/Robot/Idle/Pol_0-%d.png", 0, 9)),
			entry(State.ATTACKING_RIGHT, ImageUtils.loadAnimation("/resoruces/Enemies/ThrowerRobot/Robot/Attacking/Pol_0-%d.png", 402, 407)), 
			entry(State.ATTACKING_LEFT,  ImageUtils.loadFlippedAnimation("/resoruces/Enemies/ThrowerRobot/Robot/Attacking/Pol_0-%d.png", 402, 407))
	)),
	THROWER_ROBOT_ATTACK(Map.ofEntries(
			entry(State.WALKING_RIGHT,   ImageUtils.loadAnimation("/resoruces/Enemies/ThrowerRobot/Attack/VIce_0-%d.png", 882, 893))
	));
	
	private Map<State, List<BufferedImage>> animationLists;
	
	public enum State
	{
		 WALKING_LEFT, WALKING_RIGHT,
		 JUMPING_LEFT, JUMPING_RIGHT,
		 FALLING_LEFT, FALLING_RIGHT,
		 ATTACKING_LEFT, ATTACKING_RIGHT,
		 IDLE_LEFT, IDLE_RIGHT,
		 DIE_LEFT, DIE_RIGHT
	}
	
	private Animation(Map<State, List<BufferedImage>> animationLists)
	{ this.animationLists = animationLists; }
	
	public Map<State, List<BufferedImage>> getAnimationLists()
	{ return animationLists; }
}
