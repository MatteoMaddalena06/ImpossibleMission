package code.model.puzzle;

/** Enumerazione per le password previste dal gioco */
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
	
	/** Numero di caratteri delle password */
	public static final int SIZE = 9;
	/** Numero totale di password previste dal gioco */
	public static final int PASSWORD_NUMBER = 20;
	
	/** La password */
	private String password;
	
	/**
	 * Costruice l'istanza enumerativa
	 * @param password
	 * la password
	 */
	private PresettedPassword(String password)
	{ this.password = password; }

	/**
	 * Controlla se il tentativo del player di comporre la password è corretto
	 * @param attempt
	 * il tentativo rappresentato come lista di matrici di {@link PuzzlePiece}
	 * @return
	 * true se e solo se il giocatore è riuscito nel tentativo
	 */
	public boolean checkAttempt(PuzzlePiece[][][] attempt)
	{
		if(password == null || attempt == null || attempt.length != SIZE)
			return false;
		
		for(int i = 0; i < password.length(); i++)
		{
			PuzzlePiece[][] puzzleMatrix = attempt[i];
			char correctLetter = password.charAt(i);
			
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
	
	/**
	 * Restituisce la password
	 * @return
	 * la password
	 */
	public String getPassword()
	{ return password; }
}
