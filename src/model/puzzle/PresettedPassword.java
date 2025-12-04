package model.puzzle;

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
	
	private PresettedPassword (String password)
	{ this.password = password; }
	
	public String getPassword()
	{ return password; }
}
