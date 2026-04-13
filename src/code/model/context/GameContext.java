package code.model.context;

//inproject import
import code.model.room.Room;
import code.model.Leaderboard;
import code.model.Point;
import code.model.gameobjects.Player;

public class GameContext
{
	private Player player; 
	private Room currentRoom;
	private boolean[] userInput;
	private boolean isRobotsDisabled;
	private int platformsToReset;
	private Leaderboard leaderboard;
	private Point playerSpawn;
	
	private static double deltaTime;

	public enum UserInput 
	{ 
		UP(0), DOWN(1), LEFT(2), RIGHT(3), 
		JUMP(4), A_KEY(5), B_KEY(6), E_KEY(7), H_KEY(8);
		
		private int index;
		
		private UserInput(int index)
		{ this.index = index; }
		
		public int getInput()
		{ return index; }
	}
	
	public GameContext(Player player, Room currentRoom, Leaderboard leaderboard)
	{	
		this.player = player;
		this.currentRoom = currentRoom;
		this.leaderboard = leaderboard;
		userInput = new boolean[UserInput.values().length];
		isRobotsDisabled = false;
		platformsToReset = 0;
	}

	public Player getPlayer()
	{ return player; }
	
	public void setCurrentRoom(Room currentRoom)
	{ this.currentRoom = currentRoom; }
	
	public Room getCurrentRoom()
	{ return currentRoom; }
	
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
	
	public void resetPlatform()
	{ platformsToReset = (player.usePlatoformPassword()) ? currentRoom.getPlatformsNumber() : 0; }
	
	public void resetOnePlatform()
	{ platformsToReset--; }
	
	public int getPlatformsToReset()
	{ return platformsToReset; }
	
	public Leaderboard getLeaderboard()
	{ return leaderboard; }
	
	public Point getPlayerSpawn()
	{ return playerSpawn; }
	
	public void setPlayerSpawn(Point playerSpawn)
	{ this.playerSpawn = playerSpawn; }
	
	public static double getDeltaTime()
	{ return deltaTime; }
	
	public static void setDeltaTime(double deltaTime)
	{ GameContext.deltaTime = deltaTime; }
}
