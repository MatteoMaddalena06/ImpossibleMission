package code.model.gameobjects;

//model import
import code.model.puzzle.PuzzlePiece;
import code.model.room.RoomMap;
import code.model.Point;
import code.model.context.FurnitureSearchEnded;
import code.model.context.GameContext;
import code.model.context.PlayerFoundSomething;
import code.model.context.PlayerIsSearching;
import code.model.context.StopSimulation;
//event import
import code.event.EventDispatcher;

/** Modella i mobili presenti nelle stanze */
public class Furniture extends GameObject
{
	/** Per quanto tempo interrompere la simulazione se si trova qualcosa */
    private static final long LOOT_WAITING = 1000000000L;
   
    /** Il tipo di contenuto */
    private LootType content;
    /** Il tipo di mobile */
    private Type type;
    /** Il pezzo di puzzle contenuto (valido solo se {@link content} == LootType.PUZZLE_PIECE} */
	private PuzzlePiece puzzlePiece;
	/** Tempo necessario per cercare nel mobile */
	private double timeForSearch;
	/** Tempo rimanente per finire la ricerca */
    private double remainingTimeForSearch;
    
    /** Enumerazione per i tipi di contenuto di un mobile */
    public enum LootType
    {
    	EMPTY(0), PUZZLE_PIECE(1000), ROBOT_PASSWORD(250), PLATFORM_PASSWORD(250); 
    	
    	/** I punti dati al giocatore dal tipo di contentuto */
    	private int points;
    	
    	/**
    	 * Costruisce l'istanza enumerativa
    	 * @param points
    	 * quanti punti
    	 */
    	private LootType(int points)
    	{ this.points = points; }
    	
    	/**
    	 * Restituisce i punti
    	 * @return
    	 * i punti
    	 */
    	public int getPoints()
    	{ return points; }
    }

    /** Enumerazione per i  tipi di mobili possibili */
    public enum Type
    {
    	ARMOR_TYPE1, ARMOR_TYPE2, BARREL, BIGMIRROR, BIGTOTEM, BOOKSHELF_TYPE1,
    	BOOKSHELFT_TYPE2, CAVE, CHALICE, CHEST, CLOCK, COLUMN_TYPE1, COLUMN_TYPE2,
    	LANTERN, LOG, LOGCHEST, MEDIUMMIRROR_TYPE1, MEDIUMMIRROR_TYPE2, PYRAMIDOFBARRELS,
    	RUINS_TYPE1, RUINS_TYPE2, SHIELD_TYPE1, SHIEDL_TYPE2, STRANGEDOOR, TABLE_TYPE1,
    	TABLE_TYP2, TOTEM_TYPE1, TOTEM_TYPE2, VASE, WARDROBE_TYPE1, WARDOROBE_TYPE2,
    	BROKEN_COLUMN, IDK,
    	RANDOM
    }
    
    /**
     * Costruisce la classe
     * @param position
     * posizione originale
     * @param width
     * la larghezza
     * @param height
     * l'altezza
     * @param type
     * il tipo di mobile
     */
    public Furniture(Point position, int width, int height, Type type)
    {
    	super(position, width, height);
    	this.content = LootType.EMPTY;
    	this.type = type;
    	this.remainingTimeForSearch = this.timeForSearch = (width * height) / (2*(RoomMap.TILE_SIZE*RoomMap.TILE_SIZE));
    }
    
    /**
     * Aggiorna lo stato del mobile controllando se il player sta cercando in lui e assegnandogli il suo contenuto
     * @param context
     * il contesto in cui operare
     */
	@Override
	public void update(GameContext context) 
	{
		Player player = context.getPlayer();
		
		if(player.isSearching() && player.getUsedFurniture() != this)
			return;
		
		if(!context.getUserInput(GameContext.UserInput.UP) || !isColliding(player))
		{ player.setSearchingState(false); return; } 

		player.setUsedFurniture(this);
		player.setSearchingState(true);	

		if((remainingTimeForSearch -= GameContext.getDeltaTime()) > 0)
		{ EventDispatcher.notify(new PlayerIsSearching(this)); return; }
		
		player.updatePoints(content.getPoints());

		switch(content)
		{
			case LootType.PUZZLE_PIECE      -> player.givePuzzlePiece(puzzlePiece); 
			case LootType.ROBOT_PASSWORD    -> player.giveRobotPassword();  
			case LootType.PLATFORM_PASSWORD -> player.givePlatformPassword(); 
			case LootType.EMPTY             -> { /*do nothing */ }
		}
		
		EventDispatcher.notify(new PlayerFoundSomething(this));
		EventDispatcher.notify(new FurnitureSearchEnded(this));
		EventDispatcher.notify(new StopSimulation(LOOT_WAITING));
		
		player.setSearchingState(false);
		context.getCurrentRoom().removeFurniture(this);
	}
	
	/**
	 * Imposta il tipo di contenuto
	 * @param content
	 * il tipo di contenuto
	 */
	public void setContent(LootType content)
	{ this.content = content; }
	
	/**
	 * Restituisce il tipo di contenuto
	 * @return
	 * il tipo di contenuto
	 */
	public LootType getContent()
	{ return content; }
	
	/** 
	 * Restituisce il tipo di mobile
	 * @return
	 * il tipo di mobile
	 */
	public Type getType()
	{ return type; }
	
	/**
	 * Imposta il {@link PuzzlePiece} contenuto nel mobile
	 * @param puzzlePiece
	 * il {@link PuzzlePiece}
	 */
	public void setPuzzlePiece(PuzzlePiece puzzlePiece)
	{ this.puzzlePiece = puzzlePiece; }	
	
	/**
	 * Restituisce il {@link PuzzlePiece} contenuto nel mobile
	 * @return
	 * il {@link PuzzlePiece}
	 */
	public PuzzlePiece getPuzzlePiece()
	{ return puzzlePiece; }
	
	/**
	 * Restituisce il tempo necessario per cercare nel mobile
	 * @return
	 * il tempo necessario per cercare nel mobile
	 */
	public double getTimeForSearch()
	{ return timeForSearch; }
	
	/**
	 * Restituisce il tempo necessario per terminare la ricerca
	 * @return
	 * il tempo necessario per terminare la ricerca
	 */
	public double getRemainingTimeForSearch()
	{ return remainingTimeForSearch; }
}
