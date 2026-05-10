package code.model.puzzle;

public enum PresettedPassword 
{
	PASSWORD1 ("Adventure"),
	PASSWORD2 ("Butterfly"),
	PASSWORD3 ("Education"),
	PASSWORD4 ("Downgrade"),
	PASSWORD5 ("Framework"),
	PASSWORD6 ("Highlight"),
	PASSWORD7 ("Challenge"),
	PASSWORD8 ("Landscape"),
	PASSWORD9 ("Marketing"), 
	PASSWORD10("Nearshore"),
	PASSWORD11("Overgrown"),
	PASSWORD12("Playfully"),
	PASSWORD13("Rainstorm"),
	PASSWORD14("Signature"),
	PASSWORD15("Timestamp"),
	PASSWORD16("Underflow"),
	PASSWORD17("Visionary"),
	PASSWORD18("Wonderful"),
	PASSWORD19("Windmills"), 
	PASSWORD20("Knowledge");
	
	public static final int SIZE = 9;
	public static final int PASSWORD_NUMBER = 20;
	
	private String password;
	
	private PresettedPassword(String password)
	{ this.password = password; }

	public static boolean checkAttempt(PresettedPassword password, PuzzlePiece[][][] attempt)
	{
		if(password == null || attempt == null || attempt.length != SIZE)
			return false;
		
		String passwordString = password.getPassword();
		
		for(int i = 0; i < passwordString.length(); i++)
		{
			PuzzlePiece[][] puzzleMatrix = attempt[i];
			char correctLetter = passwordString.charAt(i);
			
			if(puzzleMatrix == null)
				return false;
			
			if(puzzleMatrix[0][0] == null || puzzleMatrix[0][0].getPosition() != PuzzlePiece.Position.UPPER_LEFT || puzzleMatrix[0][0].getLetter() != correctLetter)
				return false;
			
			if(puzzleMatrix[0][1] == null || puzzleMatrix[0][1].getPosition() != PuzzlePiece.Position.UPPER_RIGHT || puzzleMatrix[0][1].getLetter() != correctLetter)
				return false;
			
			if(puzzleMatrix[1][0] == null || puzzleMatrix[1][0].getPosition() != PuzzlePiece.Position.BOTTOM_LEFT || puzzleMatrix[1][0].getLetter() != correctLetter)
				return false;
			
			if(puzzleMatrix[0][1] == null || puzzleMatrix[1][1].getPosition() != PuzzlePiece.Position.BOTTOM_RIGHT || puzzleMatrix[1][1].getLetter() != correctLetter)
				return false;
		}
		
		return true;
	}
	
	public String getPassword()
	{ return password; }
}
