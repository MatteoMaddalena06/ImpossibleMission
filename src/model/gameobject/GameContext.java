package model.gameobject;

//inproject import
import model.room.Room;

public class GameContext
{
	private Player player; 
	private Room currentRoom;
	private UserInput userInput;
	
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
	
	public Player getPlayer()
	{ return player; }
	
	public Room getCurrentRoom()
	{ return currentRoom; }
	
	public UserInput getUserInput()
	{ return userInput; }
}
