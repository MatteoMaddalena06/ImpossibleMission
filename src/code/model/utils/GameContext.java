package code.model.utils;

//inproject import
import code.model.room.Room;
import code.model.gameobject.Player;

public class GameContext
{
	private Player player; 
	private Room currentRoom;
	private boolean[] userInput;
	private boolean isRobotsDisabled;
	private int platformsToReset;
	
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
	
	public GameContext(Player player, Room currentRoom)
	{	
		this.player = player;
		this.currentRoom = currentRoom;
		userInput = new boolean[UserInput.values().length];
		isRobotsDisabled = false;
		platformsToReset = 0;
	}
	
	public void setPlayer(Player player)
	{ this.player = player; }
	
	public void setCurrentRoom(Room currentRoom)
	{ this.currentRoom = currentRoom; }
	
	public void setUserInput(UserInput userInput, boolean state)
	{ this.userInput[userInput.getInput()] = state; }
	
	public void disableRobots()
	{ isRobotsDisabled = true; }
	
	public void enableRobots()
	{ isRobotsDisabled = false; }
	
	public void resetPlatform()
	{ platformsToReset = currentRoom.getPlatformsNumber(); }
	
	public void resetOnePlatform()
	{ platformsToReset--; }
	
	public Player getPlayer()
	{ return player; }
	
	public Room getCurrentRoom()
	{ return currentRoom; }
	
	public boolean getUserInput(UserInput userInput)
	{ return this.userInput[userInput.getInput()]; }
	
	public boolean isRobotsDisabled()
	{ return isRobotsDisabled; }
	
	public int getPlatformsToReset()
	{ return platformsToReset; }
	
	public static double getDeltaTime()
	{ return deltaTime; }
	
	public static void setDeltaTime(double deltaTime)
	{ GameContext.deltaTime = deltaTime; }
}
