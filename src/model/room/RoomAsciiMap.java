package model.room;

enum RoomAsciiMap 
{
	//TODO
	ROOM1(""),
	ROOM2(""),
	ROOM3(""),
	ROOM4(""),
	ROOM5(""),
	ROOM6(""),
	ROOM7(""),
	ROOM8(""),
	ROOM9(""),
	ROOM10(""),
	ROOM11(""),
	ROOM12(""),
	ROOM13(""),
	ROOM14(""),
	ROOM15(""),
	ROOM16(""),
	ROOM17(""),
	ROOM18(""),
	ROOM19(""),
	ROOM20(""),
	ROOM21(""),
	ROOM22(""),
	ROOM23(""),
	ROOM24(""),
	ROOM25(""),
	ROOM26(""),
	ROOM27(""),
	ROOM28(""),
	ROOM29(""),
	ROOM30(""),
	ROOM31(""),
	ROOM32("");
	
	private final static char EMPTYCHAR = ' ';
	private final static int  TILESIZE  = 16;
	
	private String asciiMap;
	
	private RoomAsciiMap(String asciiMap)
	{ this.asciiMap = asciiMap; }	
	
	public String getAsciiMap()
	{ return asciiMap; }
	
	public char getEmptyChar()
	{ return EMPTYCHAR; }
	
	public int getTileSize()
	{ return TILESIZE; }
}
