package model.gameobject;

//data structure modules
import java.util.List;

//inproject import
import model.puzzle.PuzzlePiece;
import model.room.Room;

public class Player extends MovingObject
{	
	private static final double HORIZONTAL_SPEED = 200f; 
	private static final double VERTICAL_SPEED   = 350f; 
	
	private List<PuzzlePiece> puzzlePiecesObtained;
	private int robotPasswordsObtained;
	private int platformPasswordsObtained;
	
	public Player(Point position, int width, int height)
	{ super(position, width, height, HORIZONTAL_SPEED, VERTICAL_SPEED); }

	@Override
	public void update(GameContext context) 
	{
		
	}
	
	public void givePuzzlePiece(PuzzlePiece piece)
	{ puzzlePiecesObtained.add(piece); }
	
	public void giveRobotPassword()
	{ robotPasswordsObtained++; }
	
	public void givePlatformPassword()
	{ platformPasswordsObtained++; }
	
	public boolean useRobotPassword()
	{ return (robotPasswordsObtained == 0) ? false : robotPasswordsObtained-- >= 0; }
	
	public boolean usePlatoformPassword()
	{ return (platformPasswordsObtained == 0) ? false : platformPasswordsObtained-- >= 0; }
}
