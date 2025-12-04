package model.gameobject;

//inproject import
import model.room.Room;

public class GameContext
{
	private Player player; 
	private Room currentRoom;
	private UserInput userInput;
	private boolean isRobotsDisabled;
	private boolean isPlatformResetted;
	
	public enum UserInput 
	{ UP, DOWN, LEFT, RIGHT, JUMP }
	
	public GameContext(Player player, Room currentRoom)
	{	
		this.player = player;
		this.currentRoom = currentRoom;
	}
	
	public void setPlayer(Player player)
	{ this.player = player; }
	
	public void setCurrentRoom(Room currentRoom)
	{ this.currentRoom = currentRoom; }
	
	public void setUserInput(UserInput userInput)
	{ this.userInput = userInput; }
	
	public void disableRobots()
	{ isRobotsDisabled = true; }
	
	public void enableRobots()
	{ isRobotsDisabled = false; }
	
	public void resetPlatform()
	{ isPlatformResetted = true; }
	
	public Player getPlayer()
	{ return player; }
	
	public Room getCurrentRoom()
	{ return currentRoom; }
	
	public UserInput getUserInput()
	{ return userInput; }
	
	public boolean isRobotsDisabled()
	{ return isRobotsDisabled; }
	
	public boolean isPlatformResetted()
	{ return isPlatformResetted; }
}
