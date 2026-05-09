package code.model.context;

//inproject import
import code.model.room.Room;
import code.model.GameWorld;
import code.model.Leaderboard;
import code.model.gameobjects.Player;

public class GameContext
{
	private Player player; 
	private GameWorld world;
	private Leaderboard leaderboard;
	private boolean[] userInput;
	private boolean isRobotsDisabled;
	private int platformsToReset;
	
	private Room generatedElevatorRoom;
	private boolean needToRegenerateElevatorRoom;
	
	private static double deltaTime;

	public enum UserInput 
	{ 
		UP(0), DOWN(1), LEFT(2), RIGHT(3), JUMP(4);
		
		private int index;
		
		private UserInput(int index)
		{ this.index = index; }
		
		public int getInput()
		{ return index; }
	}
	
	public GameContext(Player player, GameWorld world, Leaderboard leaderboard)
	{	
		this.player = player;
		this.world = world;
		this.leaderboard = leaderboard;
		userInput = new boolean[UserInput.values().length];
		isRobotsDisabled = false;
		platformsToReset = 0;
		needToRegenerateElevatorRoom = true;
	}

	public Player getPlayer()
	{ return player; }
	
	public Room getCurrentRoom()
	{ 
		int worldX = (int)player.getWorldPosition().getX();
		int worldY = (int)player.getWorldPosition().getY();
		
		if(worldX % 2 == 0)
		{
			needToRegenerateElevatorRoom = true;
			return world.getWorldMatrix()[worldY][worldX];
		}
		
		if(needToRegenerateElevatorRoom)
		{
			needToRegenerateElevatorRoom = false;
			generatedElevatorRoom = world.getElevatorColumnAsRoom(worldX, player);
		}

		return generatedElevatorRoom;
	}
	
	public void setUserInput(UserInput userInput, boolean state)
	{ this.userInput[userInput.getInput()] = state; }
	
	public boolean getUserInput(UserInput userInput)
	{ return this.userInput[userInput.getInput()]; }
	
	public void disableRobots()
	{ isRobotsDisabled = player.useRobotPassword(); }
	
	public void enableRobots()
	{ isRobotsDisabled = false; }
	
	public boolean isRobotsDisabled()
	{ return isRobotsDisabled; }
	
	public void resetPlatforms()
	{ platformsToReset = (player.usePlatoformPassword()) ? getCurrentRoom().getPlatformsNumber() : 0; }
	
	public void resetOnePlatform()
	{ platformsToReset--; }
	
	public int getPlatformsToReset()
	{ return platformsToReset; }
	
	public Leaderboard getLeaderboard()
	{ return leaderboard; }
	
	public static double getDeltaTime()
	{ return deltaTime; }
	
	public static void setDeltaTime(double deltaTime)
	{ GameContext.deltaTime = deltaTime; }
}
