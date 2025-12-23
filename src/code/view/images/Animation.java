package code.view.images;

//data strucute import
import java.util.Map;
import static java.util.Map.entry;
import java.util.List;
//graphics import
import java.awt.image.BufferedImage;
//model import
import code.model.gameobjects.MovingObject;
import code.model.gameobjects.Player;
import code.model.gameobjects.enemy.AttackerRobot;

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
		WALKING_LEFT(1), WALKING_RIGHT(-1),
		JUMPING_LEFT(2), JUMPING_RIGHT(-2),
		FALLING_LEFT(3), FALLING_RIGHT(-3),
		ATTACKING_LEFT(4), ATTACKING_RIGHT(-4),
		IDLE_LEFT(5), IDLE_RIGHT(-5),
		DIE_LEFT(6), DIE_RIGHT(-6), 
		SEARCHING();	
		
		private int id;
		
		private State(int id)
		{ this.id = id;}
		
		private State()
		{ this(0); }
	
		public static State getState(MovingObject movingObject)
		{			
			MovingObject.Direction direction = movingObject.getDirection();
			 
			if(movingObject instanceof AttackerRobot && ((AttackerRobot)movingObject).isAttacking())
				return (direction == MovingObject.Direction.LEFT) ? ATTACKING_LEFT : ATTACKING_RIGHT;
			 
			else if(movingObject instanceof Player && ((Player)movingObject).isSearching())
				return SEARCHING;
			 
			MovingObject.PhysicsState physicsState = movingObject.getPhysicsState();
			 
			return  switch(physicsState) {
			 	case MovingObject.PhysicsState.WALKING -> (direction == MovingObject.Direction.LEFT) ? WALKING_LEFT : WALKING_RIGHT;
			 	case MovingObject.PhysicsState.JUMPING -> (direction == MovingObject.Direction.LEFT) ? JUMPING_LEFT : JUMPING_RIGHT;
			 	case MovingObject.PhysicsState.FALLING -> (direction == MovingObject.Direction.LEFT) ? FALLING_LEFT : FALLING_RIGHT;
			 	case MovingObject.PhysicsState.IDLE    -> (direction == MovingObject.Direction.LEFT) ? IDLE_LEFT    : IDLE_RIGHT;
			};
		}
		 
		 
		public boolean isMirrored(State otherState)
		{ return otherState != null && this.id == -otherState.id; }
	}
	
	private Animation(Map<State, List<BufferedImage>> animationLists)
	{ this.animationLists = animationLists; }
	
	public Map<State, List<BufferedImage>> getAnimationLists()
	{ return animationLists; }
}
