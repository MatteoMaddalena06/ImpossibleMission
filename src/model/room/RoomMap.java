package model.room;

enum RoomMap 
{
	//TODO
	ROOM1 (new int[][] {}),
	ROOM2 (new int[][] {}),
	ROOM3 (new int[][] {}),
	ROOM4 (new int[][] {}),
	ROOM5 (new int[][] {}),
	ROOM6 (new int[][] {}),
	ROOM7 (new int[][] {}),
	ROOM8 (new int[][] {}),
	ROOM9 (new int[][] {}),
	ROOM10(new int[][] {}),
	ROOM11(new int[][] {}),
	ROOM12(new int[][] {}),
	ROOM13(new int[][] {}),
	ROOM14(new int[][] {}),
	ROOM15(new int[][] {}),
	ROOM16(new int[][] {}),
	ROOM17(new int[][] {}),
	ROOM18(new int[][] {}),
	ROOM19(new int[][] {}),
	ROOM20(new int[][] {}),
	ROOM21(new int[][] {}),
	ROOM22(new int[][] {}),
	ROOM23(new int[][] {}),
	ROOM24(new int[][] {}),
	ROOM25(new int[][] {}),
	ROOM26(new int[][] {}),
	ROOM27(new int[][] {}),
	ROOM28(new int[][] {}),
	ROOM29(new int[][] {}),
	ROOM30(new int[][] {}),
	ROOM31(new int[][] {}),
	ROOM32(new int[][] {});
	
	private final static int EMPTYSPACE = 0;
	private final static int TILESIZE   = 16;
	
	private int[][] asciiMap;
	
	private RoomMap(int[][] asciiMap)
	{ this.asciiMap = asciiMap; }	
	
	public int[][] getMap()
	{ return asciiMap; }
	
	public char getEmptySpace()
	{ return EMPTYSPACE; }
	
	public int getTileSize()
	{ return TILESIZE; }
}
