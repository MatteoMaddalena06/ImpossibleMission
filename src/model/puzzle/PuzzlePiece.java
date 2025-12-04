package model.puzzle;

//data structure modules
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
  
public enum PuzzlePiece 
{
	A_UPPER_RIGHT (Position.UPPER_RIGHT,  'A'),
	A_UPPER_LEFT  (Position.UPPER_LEFT,   'A'),  
	A_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'A'),
	A_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'A'),
	
	B_UPPER_RIGHT (Position.UPPER_RIGHT,  'B'),
	B_UPPER_LEFT  (Position.UPPER_LEFT,   'B'),  
	B_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'B'),
	B_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'B'),
	
	C_UPPER_RIGHT (Position.UPPER_RIGHT,  'C'),
	C_UPPER_LEFT  (Position.UPPER_LEFT,   'C'),  
	C_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'C'),
	C_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'C'),
	
	D_UPPER_RIGHT (Position.UPPER_RIGHT,  'D'),
	D_UPPER_LEFT  (Position.UPPER_LEFT,   'D'),  
	D_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'D'),
	D_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'D'),
	
	E_UPPER_RIGHT (Position.UPPER_RIGHT,  'E'),
	E_UPPER_LEFT  (Position.UPPER_LEFT,   'E'),  
	E_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'E'),
	E_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'E'),
	
	F_UPPER_RIGHT (Position.UPPER_RIGHT,  'F'),
	F_UPPER_LEFT  (Position.UPPER_LEFT,   'F'),  
	F_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'F'),
	F_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'F'),
	
	G_UPPER_RIGHT (Position.UPPER_RIGHT,  'G'),
	G_UPPER_LEFT  (Position.UPPER_LEFT,   'G'),  
	G_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'G'),
	G_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'G'),
	
	H_UPPER_RIGHT (Position.UPPER_RIGHT,  'H'),
	H_UPPER_LEFT  (Position.UPPER_LEFT,   'H'),  
	H_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'H'),
	H_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'H'),
	
	I_UPPER_RIGHT (Position.UPPER_RIGHT,  'I'),
	I_UPPER_LEFT  (Position.UPPER_LEFT,   'I'),  
	I_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'I'),
	I_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'I'),
	
	J_UPPER_RIGHT (Position.UPPER_RIGHT,  'J'),
	J_UPPER_LEFT  (Position.UPPER_LEFT,   'J'),  
	J_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'J'),
	J_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'J'),
	
	K_UPPER_RIGHT (Position.UPPER_RIGHT,  'K'),
	K_UPPER_LEFT  (Position.UPPER_LEFT,   'K'),  
	K_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'K'),
	K_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'K'),
	
	L_UPPER_RIGHT (Position.UPPER_RIGHT,  'L'),
	L_UPPER_LEFT  (Position.UPPER_LEFT,   'L'),  
	L_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'L'),
	L_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'L'),
	
	M_UPPER_RIGHT (Position.UPPER_RIGHT,  'M'),
	M_UPPER_LEFT  (Position.UPPER_LEFT,   'M'),  
	M_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'M'),
	M_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'M'),
	
	N_UPPER_RIGHT (Position.UPPER_RIGHT,  'N'),
	N_UPPER_LEFT  (Position.UPPER_LEFT,   'N'),  
	N_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'N'),
	N_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'N'),
	
	O_UPPER_RIGHT (Position.UPPER_RIGHT,  'O'),
	O_UPPER_LEFT  (Position.UPPER_LEFT,   'O'),  
	O_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'O'),
	O_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'O'),
	
	P_UPPER_RIGHT (Position.UPPER_RIGHT,  'P'),
	P_UPPER_LEFT  (Position.UPPER_LEFT,   'P'),  
	P_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'P'),
	P_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'P'),
	
	Q_UPPER_RIGHT (Position.UPPER_RIGHT,  'Q'),
	Q_UPPER_LEFT  (Position.UPPER_LEFT,   'Q'),  
	Q_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'Q'),
	Q_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'Q'),

	R_UPPER_RIGHT (Position.UPPER_RIGHT,  'R'),
	R_UPPER_LEFT  (Position.UPPER_LEFT,   'R'),  
	R_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'R'),
	R_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'R'),

	S_UPPER_RIGHT (Position.UPPER_RIGHT,  'S'),
	S_UPPER_LEFT  (Position.UPPER_LEFT,   'S'),  
	S_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'S'),
	S_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'S'),

	T_UPPER_RIGHT (Position.UPPER_RIGHT,  'T'),
	T_UPPER_LEFT  (Position.UPPER_LEFT,   'T'),  
	T_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'T'),
	T_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'T'),

	U_UPPER_RIGHT (Position.UPPER_RIGHT,  'U'),
	U_UPPER_LEFT  (Position.UPPER_LEFT,   'U'),  
	U_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'U'),
	U_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'U'),

	W_UPPER_RIGHT (Position.UPPER_RIGHT,  'W'),
	W_UPPER_LEFT  (Position.UPPER_LEFT,   'W'),  
	W_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'W'),
	W_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'W'),
	
	X_UPPER_RIGHT (Position.UPPER_RIGHT,  'X'),
	X_UPPER_LEFT  (Position.UPPER_LEFT,   'X'),  
	X_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'X'),
	X_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'X'),

	Y_UPPER_RIGHT (Position.UPPER_RIGHT,  'Y'),
	Y_UPPER_LEFT  (Position.UPPER_LEFT,   'Y'),  
	Y_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'Y'),
	Y_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'Y'),

	Z_UPPER_RIGHT (Position.UPPER_RIGHT,  'Z'),
	Z_UPPER_LEFT  (Position.UPPER_LEFT,   'Z'),  
	Z_BOTTOM_RIGHT(Position.BOTTOM_RIGHT, 'Z'),
	Z_BOTTOM_LEFT (Position.BOTTOM_LEFT,  'Z');

	private static final int LETTER_SIZE = 4;
	
	private static final Map<Character, PuzzlePiece> upperRightPieces = 
			Arrays.stream(values()).filter(p -> p.position == Position.UPPER_RIGHT).collect(Collectors.toMap(PuzzlePiece::getLetter, Function.identity()));
	private static final Map<Character, PuzzlePiece> upperLeftPieces = 
			Arrays.stream(values()).filter(p -> p.position == Position.UPPER_LEFT).collect(Collectors.toMap(PuzzlePiece::getLetter, Function.identity()));
	private static final Map<Character, PuzzlePiece> bottomRightPieces = 
			Arrays.stream(values()).filter(p -> p.position == Position.BOTTOM_RIGHT).collect(Collectors.toMap(PuzzlePiece::getLetter, Function.identity()));
	private static final Map<Character, PuzzlePiece> bottomLeftPieces = 
			Arrays.stream(values()).filter(p -> p.position == Position.BOTTOM_LEFT).collect(Collectors.toMap(PuzzlePiece::getLetter, Function.identity()));
	
	private Position position;
	private char letter;
	
	public enum Position 
	{
		UPPER_RIGHT, UPPER_LEFT,
		BOTTOM_RIGHT, BOTTOM_LEFT
	};
	
	private PuzzlePiece(Position position, char letter)
	{
		this.position = position;
		this.letter = letter;
	}
	
	public static PuzzlePiece[] getPieces(String password)
	{
		int passwordLength = password.length();
		PuzzlePiece[] pieces = new PuzzlePiece[passwordLength * LETTER_SIZE];
		
		for(int i = 0; i < passwordLength; i++)
		{
			int arrayIndex = i * LETTER_SIZE;
			pieces[arrayIndex]     = upperRightPieces.get(password.charAt(i));
			pieces[arrayIndex + 1] = upperLeftPieces.get(password.charAt(i));
			pieces[arrayIndex + 2] = bottomRightPieces.get(password.charAt(i));
			pieces[arrayIndex + 3] = bottomLeftPieces.get(password.charAt(i));
		}
		
		return pieces;
	}
	
	public Position getPosition()
	{ return position; }
	
	public char getLetter()
	{ return letter; }
	
}
