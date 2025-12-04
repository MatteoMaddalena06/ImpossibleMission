package model.gameobject;

//inproject import
import model.puzzle.PuzzlePiece;

//TODO put information on the specific puzzle piece
public class Furniture extends GameObject
{
    private static final long serialVersionUID = 1L;
   
	private LootType content;
    private Type type;
	private PuzzlePiece puzzlePiece;
    private int remainingTicksForSearch;
    
    public enum LootType
    { EMPTY, PUZZLE_PIECE, ROBOT_PASSWORD, PLATFORM_PASSWORD }

    public enum Type
    {
    	NIGHTSTAND, TRASH_CAN, SINK, BATHTUB, TOILET, LARGE_DISCS, 
    	MONITOR, DESK, PRINTER, KITCHEN, REFRIGERATOR, BED,
    	LARGE_DOOR, CANDY, LARGE_COMPUTER, SMALL_DISKS, FIREPLACE, BOOKCASE, 
    	ARMCHAIR, LAMP, READERS, SPEAKERS, SMALL_SOFA, LARGE_SOFA,
    	RANDOM
    }
    
    public Furniture(Point position, int width, int height, Type type)
    {
    	super(position, width, height);
    	this.content = LootType.EMPTY;
    	this.type = type;
    	this.remainingTicksForSearch = 42; //TODO
    }
    
	@Override
	public void update(GameContext context) 
	{
		Player player = context.getPlayer();
		
		if(context.getUserInput() != GameContext.UserInput.UP || !isColliding(player))
			return;
		
		if(remainingTicksForSearch-- != 0)
			return;
		
		//TODO method implementation in Player class
		switch(content)
		{
			case LootType.PUZZLE_PIECE      -> player.givePuzzlePiece();
			case LootType.ROBOT_PASSWORD    -> player.giveRobotPassword();  
			case LootType.PLATFORM_PASSWORD -> player.givePlatfromPassword();
			case LootType.EMPTY             -> { /*do nothing */} 
		}
		
		context.getCurrentRoom().removeForniture(this);
	}
	
	public void setContent(LootType content)
	{ this.content = content; }
	
	public void setPuzzlePiece(PuzzlePiece puzzlePiece)
	{ this.puzzlePiece = puzzlePiece; }
}
