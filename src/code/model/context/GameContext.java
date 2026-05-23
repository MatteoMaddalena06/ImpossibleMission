package code.model.context;

//inproject import
import code.model.room.Room;
import code.model.GameWorld;
import code.model.Leaderboard;
import code.model.gameobjects.Player;

/** Classe che modella il contesto di gioco */
public class GameContext
{
	/** Il giocatore della partita */
	private Player player; 
	/** Il mondo di gioco della partita */
	private GameWorld world;
	/** La classifica */
	private Leaderboard leaderboard;
	/** L'input dell'utente */
	private boolean[] userInput;
	/** Indica se i robot sono disabilitati */
	private boolean isRobotsDisabled;
	/** Indica il numero di platform da ripristinare */
	private int platformsToReset;
	
	/** La stanze dell'ascensore generata */
	private Room generatedElevatorRoom;
	/** Indica se serve generare la stanze dell'ascensore generata */
	private boolean needToRegenerateElevatorRoom;
	
	/** Il delta time per scandire il tempo */
	private static double deltaTime;

	/** Enumerazione per l'input dell'utente */
	public enum UserInput 
	{ 
		UP(0), DOWN(1), LEFT(2), RIGHT(3), JUMP(4);
		
		/** L'indice dell'input nell'array {@link userInput} */
		private int index;
		
		/**
		 * Costruice l'istanza enumerativa
		 * @param index
		 * l'indici nell'array {@link userInput}
		 */
		private UserInput(int index)
		{ this.index = index; }
		
		/** 
		 * Restituisce l'indice dell'input
		 * @return
		 * l'indice dell'input
		 */
		public int getInput()
		{ return index; }
	}
	
	/**
	 * Costruice la classe
	 * @param player
	 * il giocatore della partita
	 * @param world
	 * il mondo di gioco
	 * @param leaderboard
	 * la classifica
	 */
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

	/**
	 * Restitusice il giocatore della partita
	 * @return
	 * il giocatore della partita
	 */
	public Player getPlayer()
	{ return player; }
	
	/** 
	 * Restituisce la stanza in cui si trova il giocatore
	 * @return
	 * la stanza in cui si trova il giocatore
	 */
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
	
	/**
	 * Imposta lo stato dell'input dall'utente
	 * @param userInput
	 * l'input
	 * @param state
	 * il suo stato
	 */
	public void setUserInput(UserInput userInput, boolean state)
	{ this.userInput[userInput.getInput()] = state; }
	
	/**
	 * Restituisce lo stato dell'input
	 * @param userInput
	 * l'input
	 * @return
	 * lo stato richiesto
	 */
	public boolean getUserInput(UserInput userInput)
	{ return this.userInput[userInput.getInput()]; }
	
	/** Aggiorna lo stato del flag {@link isRobotsDisabled} */
	public void disableRobots()
	{ isRobotsDisabled = player.useRobotPassword(); }
	
	/** Pone a false il flag {@link isRobotsDisabled} */
	public void enableRobots()
	{ isRobotsDisabled = false; }
	
	/**
	 * Dice se i robot sono disabilitati
	 * @return
	 * lo stato do {@link isRobotsDisabled} 
	 */
	public boolean isRobotsDisabled()
	{ return isRobotsDisabled; }
	
	/** Decide se è possibile ripristinare le piattaforme */
	public void resetPlatforms()
	{ platformsToReset = (player.isDead() || player.usePlatoformPassword()) ? getCurrentRoom().getPlatformsNumber() : 0; }
	
	/** Ripristina una piattaforma se possibile */
	public void resetOnePlatform()
	{ platformsToReset--; }
	
	/**
	 * Restituisce quante piattaforme bisogna ripristinare
	 * @return
	 * quante piattaforme bisogna ripristinare
	 */
	public int getPlatformsToReset()
	{ return platformsToReset; }
	
	/**
	 * Restituisce la classifica
	 * @return
	 * la classifica
	 */
	public Leaderboard getLeaderboard()
	{ return leaderboard; }
	
	/**
	 * Restituisce il {@link deltaTime}
	 * @return
	 * il {@link deltaTime}
	 */
	public static double getDeltaTime()
	{ return deltaTime; }
	
	/**
	 * Imposta il {@link deltaTime}
	 * @param deltaTime
	 * il {@link deltaTime}
	 */
	public static void setDeltaTime(double deltaTime)
	{ GameContext.deltaTime = deltaTime; }
}
