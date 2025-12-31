package code.model.gameobjects;

//inproject import
import code.model.puzzle.PuzzlePiece;
import code.model.room.RoomMap;
import code.model.Point;
import code.model.context.FurnitureSearchEnded;
import code.model.context.GameContext;
import code.model.context.PlayerFoundSomething;
import code.model.context.PlayerIsSearching;
import code.model.context.StopSimulation;

public class Furniture extends GameObject
{
    private static final long serialVersionUID = 1L;
    
    private static final double LOOT_WAITING = 1.5f;
   
    private LootType content;
    private Type type;
	private PuzzlePiece puzzlePiece;
	private double timeForSearch;
    private double remainingTimeForSearch;
    
    public enum LootType
    { EMPTY, PUZZLE_PIECE, ROBOT_PASSWORD, PLATFORM_PASSWORD }

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
    
    public Furniture(Point position, int width, int height, Type type)
    {
    	super(position, width, height);
    	this.content = LootType.EMPTY;
    	this.type = type;
    	this.remainingTimeForSearch = this.timeForSearch = (width * height) / (2*(RoomMap.TILE_SIZE*RoomMap.TILE_SIZE));
    }
    
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
		
		GameContext.EventListener eventListener = context.getEventListener();

		if((remainingTimeForSearch -= GameContext.getDeltaTime()) > 0)
		{ eventListener.notifyEvent(new PlayerIsSearching(this)); return; }

		switch(content)
		{
			case LootType.PUZZLE_PIECE      -> player.givePuzzlePiece(puzzlePiece); 
			case LootType.ROBOT_PASSWORD    -> player.giveRobotPassword();  
			case LootType.PLATFORM_PASSWORD -> player.givePlatformPassword(); 
			case LootType.EMPTY             -> { /*do nothing */ }
		}
		
		eventListener.notifyEvent(new PlayerFoundSomething(this));
		eventListener.notifyEvent(new FurnitureSearchEnded(this));
		context.getStatetListener().notifyState(new StopSimulation(LOOT_WAITING));
		
		player.setSearchingState(false);
		context.getCurrentRoom().removeForniture(this);
	}
	
	public void setContent(LootType content)
	{ this.content = content; }
	
	public LootType getContent()
	{ return content; }
	
	public Type getType()
	{ return type; }
	
	public void setPuzzlePiece(PuzzlePiece puzzlePiece)
	{ this.puzzlePiece = puzzlePiece; }	
	
	public PuzzlePiece getPuzzlePiece()
	{ return puzzlePiece; }
	
	public double getTimeForSearch()
	{ return timeForSearch; }
	
	public double getRemainingTimeForSearch()
	{ return remainingTimeForSearch; }
}
