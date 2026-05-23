package code.view;

//councurrency import
import java.util.concurrent.CountDownLatch;
//data structure import
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
//graphics import
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
//view import
import code.view.sprites.Sprite;
import code.view.sprites.SpriteFactory;
import code.view.images.ImageUtils;
import code.view.images.StaticImage;
import code.view.menu.event.PuzzleMenuOpened;
import code.view.sprites.PlatformSprite;
import code.view.sprites.PlayerSprite;
import code.view.sprites.SearchingWindow;
//model import
import code.model.gameobjects.FixedObject;
import code.model.gameobjects.Furniture;
import code.model.gameobjects.GameObject;
import code.model.gameobjects.Platform;
import code.model.gameobjects.Player;
import code.model.gameobjects.Terminal;
import code.model.gameobjects.enemy.AttackerRobot;
import code.model.gameobjects.enemy.Enemy;
import code.model.room.Room;
import code.model.room.RoomMap;
import code.event.EventDispatcher;
import code.model.Point;
import code.model.context.AttackEnded;
import code.model.context.AttackLaunched;
import code.model.context.FurnitureSearchEnded;
import code.model.context.GameContext;
import code.model.context.PlayerFoundSomething;
import code.model.context.PlayerIsSearching;
import code.model.context.GameContext.UserInput;

/** Classe per il pannello di gioco */
public class Renderer extends JPanel
{
	/** Immagine di sfondo */
    private static final BufferedImage background  = StaticImage.BACKGROUND.getImage();
	/** Immagine delle vite */
	private static final BufferedImage lifeIcon    = StaticImage.LIFE_ICON.getImage();
	/** Lista delle cifre per il punteggio */
	private static final StaticImage[] numbersList = StaticImage.getNumbersList();
	
	/** Dimensione dell'icona per le vite */
	private static final int LIFEICON_SIZE     = 35;
	/** Spiazzamento delle icone per le vite */
	private static final int LIFEICON_PADDING  = 5;
	/** Dimensione dell'icona per le cifre del punteggio */
	private static final int DIGITICON_SIZE    = 35;
	/** Spiazzamento delle icone per le cifre del punteggio */ 
	private static final int DIGITICON_PADDING = 5;
	
	/** {@link Sprite} del giocatore */
	private PlayerSprite playerSprite;
	/** Contesto di gioco */
	private GameContext context;
	/** Lista delle {@link Sprite} da visualizzare */
	private List<Sprite> currentSpritesList; 
	/** Cache per la generazione delle liste di {@link Sprite} */
	private Map<Room, List<Sprite>> spritesListsCache;
	
	/** Altezza della finestra virtuale da visualizzare */
	private double currentWindowY;
	
	/** Indica se è necessario visualizzare lo stato di una ricerca in un mobile */
	private boolean printSearchingState;
	/** Indica se è necessario visualizzare il contenuto di un mobile */
	private boolean printFurnitureLoot;
	/** Il mobile in cui il giocatore sta cercando (valido solo se {@link printSearchingState} == true o {@link printFurnitureLoot} == true) */
	private Furniture interestingFurniture;
	
	/** Sincronizza il thread {@link code.controller.GameLoop} con il thread AWT durante la prima visualizzazione del pannello di gioco */
	private CountDownLatch firstPaintLatch;
	/** Indica se è la prima visualizzazione del pannello di gioco */
	private boolean isFirstPaint;
	
	/**
	 * Costruisce la classe
	 * @param context
	 * il contesto di gioco in cui operare
	 */
	public Renderer(GameContext context)
	{	
		currentSpritesList = new LinkedList<Sprite>();
		spritesListsCache = new HashMap<Room, List<Sprite>>();
		currentWindowY = 0;
		printSearchingState = printFurnitureLoot = false;
		this.playerSprite = (PlayerSprite)SpriteFactory.produce(context.getPlayer());
		this.context = context;
		isFirstPaint = true;
		
		EventDispatcher.subscribe(AttackLaunched.class,       x -> addAttackSprite(((AttackLaunched)x).source()));
		EventDispatcher.subscribe(AttackEnded.class,          x -> removeAttackSprite(((AttackEnded)x).source()));
		EventDispatcher.subscribe(FurnitureSearchEnded.class, x -> removeFurnitureSprite(((FurnitureSearchEnded)x).source()));
		EventDispatcher.subscribe(PlayerFoundSomething.class, x -> printFurnitureLoot(((PlayerFoundSomething)x).source()));
		EventDispatcher.subscribe(PlayerIsSearching.class, 	  x -> printSearchingState(((PlayerIsSearching)x).source()));
		
		bindAllKey(context);
	}
	
	/**
	 * Disegna a schermo tutte le componenti del pannello di gioco:
	 * <ul>
	 *   <li>le sprite nella {@link currentSpritesList}</li>
	 *   <li>la {@link playerSprite}</li>
	 *   <li>le icone delle vite e del punteggio</li>
	 * </ul>
	 * @param g
	 * il contesto grafico
	 * @see drawHUD
	 * @see paintImage
	 * @see paintFurnitureInfo
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
    	super.paintComponent(g);
    	
    	g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
    	
    	List<Sprite> spritesList = currentSpritesList;
    	
    	Player player = context.getPlayer();
    	
    	if(!player.isOnElevator())
    		currentWindowY = 0;
    	
    	if(player.isOnElevator())
    	{
    		double referenceY = context.getCurrentRoom().getPlatformList().get(0).copyPosition().getY();
    		
    		spritesList = currentSpritesList.stream().filter(s -> {
    			double spriteY = s.getGameObject().copyPosition().getY();
    			return spriteY + s.getGameObject().getHeight() >= currentWindowY;
    		}).toList();	
    		
    		currentWindowY = referenceY - RoomMap.PIXELS_MAP_HEIGHT + RoomMap.TILE_SIZE;
    	}
    	
    	List<Sprite> firstLayerSprites = spritesList.stream().filter(s -> {
    		GameObject go = s.getGameObject();
    		return go instanceof FixedObject || go instanceof Furniture || go instanceof Terminal || go instanceof Platform;
    	}).toList();
    	
    	List<Sprite> secondLayerSprites = spritesList.stream().filter(s -> {
    		GameObject go = s.getGameObject();
    		return go instanceof Enemy ||  go instanceof AttackerRobot.Attack;
    	}).toList();
    	
    	firstLayerSprites.forEach(s -> paintImage(s.getGameObject(), s.getImage(), g));
    	
    	secondLayerSprites.forEach(s -> {
    		s.computeImage(); 
    		paintImage(s.getGameObject(), s.getImage(), g)
    	;});
    	
    	playerSprite.computeImage();
    	paintImage(playerSprite.getGameObject(), playerSprite.getImage(), g);
    	
    	if(printSearchingState)
    	{ paintFurnitureInfo(interestingFurniture, SearchingWindow.getSearchingWindow(interestingFurniture), g); printSearchingState = false; }
    	
    	if(printFurnitureLoot)
    	{ 
    		Furniture.LootType furnitureLootType = interestingFurniture.getContent();
    		
    		if(furnitureLootType == Furniture.LootType.PUZZLE_PIECE) 
    			paintFurnitureInfo(interestingFurniture, StaticImage.getPuzzlePiece(interestingFurniture.getPuzzlePiece()).getImage(), g);
    		
    		else 
    			paintFurnitureInfo(interestingFurniture, StaticImage.getFurnitureLoot(furnitureLootType).getImage(), g);
    		
    		printFurnitureLoot = false;
    	}
    	
    	drawHUD(g);
    	g.dispose();
    	
    	if(isFirstPaint && firstPaintLatch != null)
    	{
    		isFirstPaint = false;
    		firstPaintLatch.countDown();
    	}
    } 
	
	/**
	 * Disegna sul pannello di gioco le vite e il punteggio del giocatore
	 * @param g
	 * il contesto grafico
	 */
	private void drawHUD(Graphics g)
	{
		Player player = context.getPlayer();
		
		for(int i = 0; i < player.getLifes(); i++)
			g.drawImage(lifeIcon, i * (LIFEICON_SIZE + LIFEICON_PADDING), 0, LIFEICON_SIZE, LIFEICON_SIZE, null);
		
		List<BufferedImage> digitsList = ImageUtils.getNumberAsImagesList(player.getPoints(), numbersList);
		
		for(int i = 0; i < digitsList.size(); i++)
			g.drawImage(digitsList.get(i), this.getWidth() - (i + 1) * (DIGITICON_SIZE + DIGITICON_PADDING), 0, DIGITICON_SIZE, DIGITICON_SIZE, null);
	}
	
	/**
	 * Disegna sul pannelo di gioco un'immagine
	 * @param bindedGameObject
	 * il gameobject associato all'immagine
	 * @param image
	 * l'immagine
	 * @param g
	 * il contesto grafico
	 */
	private void paintImage(GameObject bindedGameObject, BufferedImage image, Graphics g)
	{
		Point gameObjectPosition = bindedGameObject.copyPosition();
		int paintX = (int)gameObjectPosition.getX();
		int paintY = (int)(gameObjectPosition.getY() - currentWindowY);
	
		int overflow = image.getHeight() - bindedGameObject.getHeight();
		
		g.drawImage(image, paintX + bindedGameObject.getWidth() / 2 - image.getWidth() / 2, paintY - overflow, null);
	}
	
	/**
	 * Disegna sul pannello di gioco le informazione sulla ricerca in un mobile
	 * @param furniture
	 * il mobile in questione
	 * @param image
	 * l'immagine delle informazioni
	 * @param g
	 * il contesto grafico
	 */
	private void paintFurnitureInfo(Furniture furniture, BufferedImage image, Graphics g)
	{
		Point furniturePosition = interestingFurniture.copyPosition();
		int furnitureX = (int)furniturePosition.getX(), furnitureY = (int)furniturePosition.getY();
		g.drawImage(image,  furnitureX + furniture.getWidth() / 2 - image.getWidth() / 2, furnitureY - image.getHeight(), null);
	}
	
	/** Genera la lista delle {@link Sprite} corrente */
	public void setCurrentSpritesList()
	{
		Room currentRoom = context.getCurrentRoom();
		
		if(!spritesListsCache.containsKey(currentRoom))
		{
			currentSpritesList = currentRoom.getGameObjectList().stream().map(g -> {
				if(g instanceof FixedObject || g instanceof Furniture)
					return SpriteFactory.produce(g, currentRoom.getColor());
							
				return SpriteFactory.produce(g);
			}).collect(Collectors.toList());
			
			spritesListsCache.put(currentRoom, currentSpritesList);
		}
		else
			currentSpritesList = spritesListsCache.get(currentRoom);
		
		if(context.getPlayer().getWorldPosition().getX() % 2 != 0)
		{
			Sprite spriteToRemove = currentSpritesList.stream().filter(s -> s instanceof PlatformSprite).findAny().get();
			currentSpritesList.remove(spriteToRemove);
			currentSpritesList.add(SpriteFactory.produce(currentRoom.getPlatformList().get(0)));
		}
	}
	
	/**
	 * Aggiunge un attaco nella lista delle {@link Sprite} corrente 
	 * @param attack
	 * l'attacco
	 */
	private void addAttackSprite(AttackerRobot.Attack attack)
	{ currentSpritesList.add(SpriteFactory.produce(attack)); }
	
	/**
	 * Rimuove un attacco dalla lista delle {@link Sprite} corrente
	 * @param attack
	 * l'attacco
	 */
	private void removeAttackSprite(AttackerRobot.Attack attack)
	{
		Sprite spriteToRemove = currentSpritesList.stream().filter(s -> s.getGameObject() == attack).findFirst().get();
		currentSpritesList.remove(spriteToRemove);
	}
	
	/**
	 * Rimuove un mobile dalla lista delle {@link Sprite} corrente
	 * @param furniture
	 * il mobile
	 */
	private void removeFurnitureSprite(Furniture furniture)
	{ 
		Sprite spriteToRemove = currentSpritesList.stream().filter(s -> s.getGameObject() == furniture).findFirst().get();
		currentSpritesList.remove(spriteToRemove);
	}
	
	/** 
	 * Imposta gli attributi {@link printFurnitureLoot} e {@link  interestingFurniture} 
	 * per segnalare il bisogno di visualizzare il contenuto di un mobile
	 * @param furniture
	 * il mobile 
	 */
	private void printFurnitureLoot(Furniture furniture)
	{ printFurnitureLoot = true; interestingFurniture = furniture; }
	
	/** 
	 * Imposta gli attributi {@link printSearchingState} e {@link  interestingFurniture} 
	 * per segnalare il bisogno di visualizzare le informazioni sulla ricerca in un mobile
	 * @param furniture
	 * il mobile
	 */
	private void printSearchingState(Furniture furniture)
	{ printSearchingState = true; interestingFurniture = furniture; }
	
	/**
	 * Associa ai tasti della tastiera un'azione 
	 * @param context
	 * il contesto di gioco
	 */
	private void bindAllKey(GameContext context)
	{
		bindKey(context, "LEFT_PRESSED",  KeyEvent.VK_LEFT,  true);
		bindKey(context, "RIGHT_PRESSED", KeyEvent.VK_RIGHT, true);
		bindKey(context, "UP_PRESSED",    KeyEvent.VK_UP,    true);
		bindKey(context, "DOWN_PRESSED",  KeyEvent.VK_DOWN,  true);
		bindKey(context, "JUMP_PRESSED",  KeyEvent.VK_SPACE, true);
		bindKey(context, "M_PRESSED",     KeyEvent.VK_M,     true);
		
		bindKey(context, "LEFT_RELEASED",  KeyEvent.VK_LEFT,  false);
		bindKey(context, "RIGHT_RELEASED", KeyEvent.VK_RIGHT, false);
		bindKey(context, "UP_RELEASED",    KeyEvent.VK_UP,    false);
		bindKey(context, "DOWN_RELEASED",  KeyEvent.VK_DOWN,  false);
		bindKey(context, "JUMP_RELEASED",  KeyEvent.VK_SPACE, false);
	}
	
	/**
	 * Associa ad un tasto della tastiera un'azione
	 * @param context
	 * il contesto di gioco
	 * @param keyName
	 * l'ID del tasto
	 * @param keyCode
	 * il codice del tasto
	 * @param pressed
	 * true se e solo se il tasto è stato premuto
	 */
	private void bindKey(GameContext context, String keyName, int keyCode , boolean pressed)
	{
		InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = this.getActionMap();
		
		inputMap.put(KeyStroke.getKeyStroke(keyCode, 0, !pressed), keyName);
		actionMap.put(keyName, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				switch(keyCode)
				{
					case KeyEvent.VK_LEFT  -> context.setUserInput(UserInput.LEFT,  pressed);
					case KeyEvent.VK_RIGHT -> context.setUserInput(UserInput.RIGHT, pressed);
					case KeyEvent.VK_UP    -> context.setUserInput(UserInput.UP,    pressed);
					case KeyEvent.VK_DOWN  -> context.setUserInput(UserInput.DOWN,  pressed);
					case KeyEvent.VK_SPACE -> context.setUserInput(UserInput.JUMP,  pressed);
					case KeyEvent.VK_M     -> { if (pressed) EventDispatcher.notify(new PuzzleMenuOpened()); }
				}
			}	
		});
	}
	
	/**
	 * Imposta il {@link CountDownLatch} per la sincronizzazione fra {@link code.controller.GameLoop} e il thread AWT durante il primo {@link paintComponent}
	 * @param latch
	 * il {@link CountDownLatch} 
	 */
	public void setFirstPaintLatch(CountDownLatch latch)
	{ firstPaintLatch = latch; }
	
	/**
	 * Restituisce la lista delle {@link Sprite} corrente
	 * @return
	 * la lista delle {@link Sprite} corrente
	 */
	public List<Sprite> getCurrentSpritesList()
	{ return currentSpritesList; }
	
	/**
	 * Restituisce la {@link Sprite} del giocatore
	 * @return
	 * la {@link Sprite} del giocatore
	 */
	public PlayerSprite getPlayerSprite()
	{ return playerSprite; }
	
	/**
	 * Imposta la dimensione del pannello di gioco
	 * @return 
	 * la dimensione desiderata
	 */
	@Override
	public Dimension getPreferredSize() 
	{ return new Dimension(RoomMap.PIXELS_MAP_WIDTH, RoomMap.PIXELS_MAP_HEIGHT); }
}
