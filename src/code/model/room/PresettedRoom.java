package code.model.room;

//data structure modules
import java.util.Arrays;
import java.util.List;
//model import
import code.model.Point;
import code.model.gameobjects.Furniture;

/** 
 * Enumerazione per le stanze previste dal gioco. Memorizza staticamente le informazioni sulle stanze del gioco ed espone un metodo per creare un'istanza di {@link Room} con queste quando richiesto
 * @see Room
 * */
public enum PresettedRoom 
{
	ROOM3 (RoomMap.ROOM3,  Room.Color.PURPLE, Room.ExitLayout.ONLEFT,         RoomMap.upperLeftSpawnPosition),
	ROOM4 (RoomMap.ROOM4,  Room.Color.PURPLE, Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.upperLeftSpawnPosition,  RoomMap.upperRightSpawnPosition),
	ROOM5 (RoomMap.ROOM5,  Room.Color.GREEN,  Room.ExitLayout.ONRIGHT,        RoomMap.bottomRightSpawnPosition),
	ROOM6 (RoomMap.ROOM6,  Room.Color.PURPLE, Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.bottomLeftSpawnPosition, RoomMap.upperRightSpawnPosition),
	ROOM7 (RoomMap.ROOM7,  Room.Color.GREEN,  Room.ExitLayout.ONLEFT,         RoomMap.upperLeftSpawnPosition),
	ROOM8 (RoomMap.ROOM8,  Room.Color.GREEN,  Room.ExitLayout.ONRIGHT,        RoomMap.upperRightSpawnPosition),
	ROOM9 (RoomMap.ROOM9,  Room.Color.RED,    Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.upperLeftSpawnPosition,  RoomMap.upperRightSpawnPosition),
	ROOM10(RoomMap.ROOM10, Room.Color.PURPLE, Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.bottomLeftSpawnPosition, RoomMap.bottomRightSpawnPosition),
	ROOM11(RoomMap.ROOM11, Room.Color.PURPLE, Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.bottomLeftSpawnPosition, RoomMap.bottomRightSpawnPosition),
	ROOM12(RoomMap.ROOM12, Room.Color.GREEN,  Room.ExitLayout.ONRIGHT, 	      RoomMap.upperRightSpawnPosition),
	ROOM13(RoomMap.ROOM13, Room.Color.RED,    Room.ExitLayout.ONLEFT,		  RoomMap.bottomLeftSpawnPosition),
	ROOM14(RoomMap.ROOM14, Room.Color.GREEN,  Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.bottomLeftSpawnPosition, RoomMap.upperRightSpawnPosition),
	ROOM15(RoomMap.ROOM15, Room.Color.RED,    Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.upperLeftSpawnPosition,  RoomMap.upperRightSpawnPosition),
	ROOM16(RoomMap.ROOM16, Room.Color.RED,    Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.upperLeftSpawnPosition,  RoomMap.bottomRightSpawnPosition),
	ROOM17(RoomMap.ROOM17, Room.Color.RED,    Room.ExitLayout.ONLEFT, 		  RoomMap.bottomLeftSpawnPosition),
	ROOM18(RoomMap.ROOM18, Room.Color.RED,    Room.ExitLayout.ONLEFT, 		  RoomMap.upperLeftSpawnPosition),
	ROOM19(RoomMap.ROOM19, Room.Color.RED,    Room.ExitLayout.ONRIGHT, 	   	  RoomMap.bottomRightSpawnPosition),
	ROOM20(RoomMap.ROOM20, Room.Color.RED,    Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.upperLeftSpawnPosition,  RoomMap.bottomRightSpawnPosition),
	ROOM21(RoomMap.ROOM21, Room.Color.GREEN,  Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.bottomLeftSpawnPosition, RoomMap.bottomRightSpawnPosition),
	ROOM22(RoomMap.ROOM22, Room.Color.GREEN,  Room.ExitLayout.ONRIGHT,        RoomMap.upperRightSpawnPosition),
	ROOM23(RoomMap.ROOM23, Room.Color.GREEN,  Room.ExitLayout.ONLEFT, 		  RoomMap.upperLeftSpawnPosition),
	ROOM24(RoomMap.ROOM24, Room.Color.PURPLE, Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.bottomLeftSpawnPosition, RoomMap.bottomRightSpawnPosition),
	ROOM25(RoomMap.ROOM25, Room.Color.RED,    Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.bottomLeftSpawnPosition, RoomMap.upperRightSpawnPosition),
	ROOM26(RoomMap.ROOM26, Room.Color.RED,    Room.ExitLayout.ONRIGHT, 	      RoomMap.upperRightSpawnPosition),
	ROOM27(RoomMap.ROOM27, Room.Color.RED,    Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.upperLeftSpawnPosition,  RoomMap.bottomRightSpawnPosition),
	ROOM28(RoomMap.ROOM28, Room.Color.RED,    Room.ExitLayout.ONLEFT, 	      RoomMap.bottomLeftSpawnPosition),
	ROOM29(RoomMap.ROOM29, Room.Color.GREEN,  Room.ExitLayout.ONLEFT, 	      RoomMap.upperLeftSpawnPosition),
	ROOM30(RoomMap.ROOM30, Room.Color.PURPLE, Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.upperLeftSpawnPosition,  RoomMap.upperRightSpawnPosition),
	ROOM31(RoomMap.ROOM31, Room.Color.GREEN,  Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.bottomLeftSpawnPosition, RoomMap.upperRightSpawnPosition),
	ROOM32(RoomMap.ROOM32, Room.Color.PURPLE, Room.ExitLayout.ONRIGHT,        RoomMap.bottomRightSpawnPosition),
	
	ELEVATOR_RIGHT_EXIT    (RoomMap.RIGHTEXIT_ELEVATOR_ROOM,     Room.Color.GREEN, Room.ExitLayout.ONRIGHT,        RoomMap.bottomRightSpawnPosition, true),
	ELEVATOR_LEFT_EXIT     (RoomMap.LEFTEXIT_ELEVATOR_ROOM,      Room.Color.GREEN, Room.ExitLayout.ONLEFT,  	   RoomMap.bottomLeftSpawnPosition, true),
	ELEVATOR_RIGHTLEFT_EXIT(RoomMap.RIGHTLEFTEXIT_ELEVATOR_ROOM, Room.Color.GREEN, Room.ExitLayout.ONLEFTANDRIGHT, RoomMap.bottomLeftSpawnPosition, RoomMap.bottomRightSpawnPosition, true),
	ELEVATOR_NOEXIT        (RoomMap.NOEXIT_ELEVATOR_ROOM,        Room.Color.GREEN, Room.ExitLayout.NOEXIT, true); 
	
	/** Array delle stanze con l'uscita a sinistra che non sono ascensori */
	private static final PresettedRoom[] leftRoom          = Arrays.stream(values()).filter(r -> r.exitLayout == Room.ExitLayout.ONLEFT && !r.isElevator).toArray(PresettedRoom[]::new);
	/** Array delle stanze con l'uscita a destra che non sono ascensori */
	private static final PresettedRoom[] rightRoom         = Arrays.stream(values()).filter(r -> r.exitLayout == Room.ExitLayout.ONRIGHT && !r.isElevator).toArray(PresettedRoom[]::new);
	/** Array delle stanze con l'uscita sia a destra che a sinitra che non sono ascensori */
	private static final PresettedRoom[] leftAndRightRoom  = Arrays.stream(values()).filter(r -> r.exitLayout == Room.ExitLayout.ONLEFTANDRIGHT && !r.isElevator).toArray(PresettedRoom[]::new);
	
	/** Numero di stanze con l'uscita a sinistra che non sono ascensori */
	public static final int LEFT_ROOM_NUMBER       = leftRoom.length;
	/**  Numero di stanze con l'uscita a destra che non sono ascensori */
	public static final int RIGHT_ROOM_NUMBER      = rightRoom.length;
	/** Numero di stanze con l'uscita sia a destra che a sinistra che non sono ascensori */
	public static final int LEFT_RIGHT_ROOM_NUMBER = leftAndRightRoom.length;
	/** Numero di stanze totali che non sono ascensori */
	public static final int ROOM_NUMBER            = LEFT_ROOM_NUMBER + RIGHT_ROOM_NUMBER + LEFT_RIGHT_ROOM_NUMBER;
	
	/** La mappa della stanza */
	private RoomMap roomMap;
	/** Colore della stanza */
	private Room.Color color;
	/** Il tipo di uscita della stanza */
	private Room.ExitLayout exitLayout;
	/** La posizione sinistra dove il giocatore deve comparire quando muore o entra nella stanza */
	private Point leftSpawnPosition;
	/** La posizione destra dove il giocatore deve comparire quando muore o entra nella stanza */
	private Point rightSpawnPosition;
	/** Indica se la stanza è un ascensore */
	private boolean isElevator;

	/**
	 * Costruisce l'istanza enumerativa 
	 * @param roomMap
	 * la mappa della stanza
	 * @param color
	 * il colore della stanza
	 * @param exitLayout
	 * il tipo di uscita dellla stanxa
	 * @param leftSpawnPosition
	 * la posizione sinistra dove il giocatore deve comparire quando muore o entra nella stanza 
	 * @param rightSpawnPosition
	 * la posizione destra dove il giocatore deve comparire quando muore o entra nella stanza
	 * @param isElevator
	 * indica se la stanza è un ascensore 
	 */
	private PresettedRoom(RoomMap roomMap, Room.Color color, Room.ExitLayout exitLayout, Point leftSpawnPosition, Point rightSpawnPosition, boolean isElevator)
	{ 
		this.roomMap = roomMap;
		this.color = color;
		this.exitLayout = exitLayout;
		this.leftSpawnPosition = leftSpawnPosition;
		this.rightSpawnPosition = rightSpawnPosition;
		this.isElevator = isElevator;
	}
	
	/**
	 * Costruisce l'istanza enumerativa 
	 * @param roomMap
	 * la mappa della stanza
	 * @param color
	 * il colore della stanza
	 * @param exitLayout
	 * il tipo di uscita dellla stanxa
	 * @param leftSpawnPosition
	 * la posizione sinistra dove il giocatore deve comparire quando muore o entra nella stanza 
	 * @param rightSpawnPosition
	 * la posizione destra dove il giocatore deve comparire quando muore o entra nella stanza
	 */
	private PresettedRoom(RoomMap roomMap, Room.Color color, Room.ExitLayout exitLayout, Point leftSpawnPosition, Point rightSpawnPosition)
	{ this(roomMap, color, exitLayout, leftSpawnPosition, rightSpawnPosition, false); }
	
	/**
	 * Costruisce l'istanza enumerativa 
	 * @param roomMap
	 * la mappa della stanza
	 * @param color
	 * il colore della stanza
	 * @param exitLayout
	 * il tipo di uscita dellla stanxa
	 * @param spawnPosition
	 * la posizione dove il giocatore deve comparire quando muore o entra nella stanza 
	 * @param isElevator
	 * indica se la stanza è un ascensore 
	 */
	private PresettedRoom(RoomMap roomMap, Room.Color color, Room.ExitLayout exitLayout, Point spawnPosition, boolean isElevator)
	{ this(roomMap, color, exitLayout, spawnPosition, spawnPosition, isElevator); }
	
	/**
	 * Costruisce l'istanza enumerativa 
	 * @param roomMap
	 * la mappa della stanza
	 * @param color
	 * il colore della stanza
	 * @param exitLayout
	 * il tipo di uscita dellla stanxa
	 * @param spawnPosition
	 * la posizione dove il giocatore deve comparire quando muore o entra nella stanza 
	 */
	private PresettedRoom(RoomMap roomMap, Room.Color color, Room.ExitLayout exitLayout, Point spawnPosition)
	{ this(roomMap, color, exitLayout, spawnPosition, spawnPosition); }
	
	/**
	 * Costruisce l'istanza enumerativa 
	 * @param roomMap
	 * la mappa della stanza
	 * @param color
	 * il colore della stanza
	 * @param exitLayout
	 * il tipo di uscita dellla stanxa
	 * @param isElevator
	 * indica se la stanza è un ascensore 
	 */
	private PresettedRoom(RoomMap roomMap, Room.Color color, Room.ExitLayout exitLayout, boolean isElevator)
	{ this(roomMap, color, exitLayout, null, isElevator); }
	
	/**
	 * Costruisce l'istanza enumerativa 
	 * @param roomMap
	 * la mappa della stanza
	 * @param color
	 * il colore della stanza
	 * @param exitLayout
	 * il tipo di uscita dellla stanxa
	 */
	private PresettedRoom(RoomMap roomMap, Room.Color color, Room.ExitLayout exitLayout)
	{ this(roomMap, color, exitLayout, null); }
	
	/**
	 * Restituisce la stanza richiesta
	 * @param layout
	 * il tipo di uscita 
	 * @param index
	 * il numero della stanza 
	 * @return
	 *  la stanza richiesta
	 */
	public static Room getRoom(Room.ExitLayout layout, int index)
	{ 
		return switch(layout) {
			case Room.ExitLayout.ONLEFT  		-> leftRoom[index].getRoom();
			case Room.ExitLayout.ONRIGHT		-> rightRoom[index].getRoom();
			case Room.ExitLayout.ONLEFTANDRIGHT -> leftAndRightRoom[index].getRoom();
			default -> throw new IllegalArgumentException("Invalid ExitLayout");
		};
	}
	
	/**
	 * Restituisce un'istanza della stanza che l'istanza enumerativa su cui viene chiamato il metodo rappresenta
	 * @return
	 * un'istanza di {@link Room}
	 */
	public Room getRoom()
	{ return new Room(RoomMapParser.parse(roomMap), color, exitLayout, (leftSpawnPosition != null) ? new Point(leftSpawnPosition): null, (rightSpawnPosition != null) ? new Point(rightSpawnPosition) : null); }
}
