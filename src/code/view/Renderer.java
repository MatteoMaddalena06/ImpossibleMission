package code.view;

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

import code.model.gameobjects.enemy.Enemy.FieldOfView;
import code.model.gameobjects.enemy.BlackOrb;

public class Renderer extends JPanel
{
	private static final BufferedImage background  = StaticImage.BACKGROUND.getImage();
	private static final BufferedImage lifeIcon    = StaticImage.LIFE_ICON.getImage();
	private static final StaticImage[] numbersList = StaticImage.getNumbersList();
	
	private static final int LIFEICON_SIZE     = 35;
	private static final int LIFEICON_PADDING  = 5;
	private static final int DIGITICON_SIZE    = 35;
	private static final int DIGITICON_PADDING = 5;
	
	private Player player;
	private GameContext context;
	private List<Sprite> currentSpritesList; 
	private Map<Room, List<Sprite>> spritesListsCache;
	
	private double currentWindowY;
	
	private boolean printSearchingState;
	private boolean printFurnitureLoot;
	private Furniture interestingFurniture;
	
	public Renderer(Player player, GameContext context)
	{	
		currentSpritesList = new LinkedList<Sprite>();
		spritesListsCache = new HashMap<Room, List<Sprite>>();
		currentWindowY = 0;
		printSearchingState = printFurnitureLoot = false;
		this.player = player;
		this.context = context;
		
		EventDispatcher.subscribe(AttackLaunched.class,       x -> addAttackSprite(((AttackLaunched)x).source()));
		EventDispatcher.subscribe(AttackEnded.class,          x -> removeAttackSprite(((AttackEnded)x).source()));
		EventDispatcher.subscribe(FurnitureSearchEnded.class, x -> removeFurnitureSprite(((FurnitureSearchEnded)x).source()));
		EventDispatcher.subscribe(PlayerFoundSomething.class, x -> printFurnitureLoot(((PlayerFoundSomething)x).source()));
		EventDispatcher.subscribe(PlayerIsSearching.class, 	  x -> printSearchingState(((PlayerIsSearching)x).source()));
		
		bindAllKey(this, context);
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
    	super.paintComponent(g);
    	
    	g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
    	
    	List<Sprite> spritesList = currentSpritesList;
    	
    	if(player.isInElevator())
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
    		return go instanceof Enemy || go instanceof Player || go instanceof AttackerRobot.Attack;
    	}).toList();
    	
    	firstLayerSprites.forEach(s -> paintImage(s.getGameObject(), s.getImage(), g));
    	
    	secondLayerSprites.forEach(s -> {
    		s.computeImage(); 
    		paintImage(s.getGameObject(), s.getImage(), g)
    	;});
    	
    	if(printSearchingState)
    	{ paintFurnitureInfo(interestingFurniture, SearchingWindow.getSearchingWindow(interestingFurniture), g); printSearchingState = false; }
    	
    	if(printFurnitureLoot)
    	{ 
    		Furniture.LootType furnitureLootType = interestingFurniture.getContent();
    		
    		System.out.println(furnitureLootType);
    		
    		if(furnitureLootType == Furniture.LootType.PUZZLE_PIECE) 
    			paintFurnitureInfo(interestingFurniture, StaticImage.getPuzzlePiece(interestingFurniture.getPuzzlePiece()).getImage(), g);
    		
    		else 
    			paintFurnitureInfo(interestingFurniture, StaticImage.getFurnitureLoot(furnitureLootType).getImage(), g);
    		
    		printFurnitureLoot = false;
    	}
    	
    	drawHUD(g);
    	g.dispose();
    } 
	
	private void drawHUD(Graphics g)
	{
		for(int i = 0; i < player.getLifes(); i++)
			g.drawImage(lifeIcon, i * (LIFEICON_SIZE + LIFEICON_PADDING), 0, LIFEICON_SIZE, LIFEICON_SIZE, null);
		
		List<BufferedImage> digitsList = ImageUtils.getNumberAsImagesList(player.getPoints(), numbersList);
		
		for(int i = 0; i < digitsList.size(); i++)
			g.drawImage(digitsList.get(i), this.getWidth() - (i + 1) * (DIGITICON_SIZE + DIGITICON_PADDING), 0, DIGITICON_SIZE, DIGITICON_SIZE, null);
	}
	
	private void paintImage(GameObject bindedGameObject, BufferedImage image, Graphics g)
	{
		Point gameObjectPosition = bindedGameObject.copyPosition();
		int paintX = (int)gameObjectPosition.getX();
		int paintY = (int)(gameObjectPosition.getY() - currentWindowY);
	
		int overflow = image.getHeight() - bindedGameObject.getHeight();
		
		g.drawImage(image, paintX + bindedGameObject.getWidth() / 2 - image.getWidth() / 2, paintY - overflow, null);
	}
	
	private void paintFurnitureInfo(Furniture furniture, BufferedImage image, Graphics g)
	{
		Point furniturePosition = interestingFurniture.copyPosition();
		int furnitureX = (int)furniturePosition.getX(), furnitureY = (int)furniturePosition.getY();
		g.drawImage(image,  furnitureX + furniture.getWidth() / 2 - image.getWidth() / 2, furnitureY - image.getHeight(), null);
	}
	
	public void setCurrentSpritesList()
	{
		Room room = context.getCurrentRoom();
		
		if(!spritesListsCache.containsKey(room))
		{
			List<Sprite> result = room.getGameObjectList().stream().map(g -> {
				if(g instanceof FixedObject || g instanceof Furniture)
					return SpriteFactory.produce(g, room.getColor());
				
				return SpriteFactory.produce(g);
			}).collect(Collectors.toList());
			
			spritesListsCache.put(room, result);
			currentSpritesList = result;
		}
		else 
			currentSpritesList = spritesListsCache.get(room);

		currentSpritesList.add(SpriteFactory.produce(player));
	}
	
	private void updateCache()
	{ 
		List<Sprite> spritesListToStore = 
				currentSpritesList.stream().filter(s -> !(s instanceof PlayerSprite)).collect(Collectors.toList());
		spritesListsCache.put(context.getCurrentRoom(), spritesListToStore);
	}
	
	private void addAttackSprite(AttackerRobot.Attack attack)
	{ currentSpritesList.add(SpriteFactory.produce(attack)); updateCache(); }
	
	private void removeAttackSprite(AttackerRobot.Attack attack)
	{ 
		Sprite spriteToRemove = currentSpritesList.stream().filter(s -> s.getGameObject() == attack).findFirst().get();
		currentSpritesList.remove(spriteToRemove);
		updateCache();
	}
	
	private void removeFurnitureSprite(Furniture furniture)
	{ 
		Sprite spriteToRemove = currentSpritesList.stream().filter(s -> s.getGameObject() == furniture).findFirst().get();
		currentSpritesList.remove(spriteToRemove);
		updateCache();
	}
	
	private void printFurnitureLoot(Furniture furniture)
	{ printFurnitureLoot = true; interestingFurniture = furniture; }
	
	private void printSearchingState(Furniture furniture)
	{ printSearchingState = true; interestingFurniture = furniture; }
	
	private void bindAllKey(Renderer renderer, GameContext context)
	{
		bindKey(renderer, context, "LEFT_PRESSED",  KeyEvent.VK_LEFT,  true);
		bindKey(renderer, context, "RIGHT_PRESSED", KeyEvent.VK_RIGHT, true);
		bindKey(renderer, context, "UP_PRESSED",    KeyEvent.VK_UP,    true);
		bindKey(renderer, context, "DOWN_PRESSED",  KeyEvent.VK_DOWN,  true);
		bindKey(renderer, context, "JUMP_PRESSED",  KeyEvent.VK_SPACE, true);
		
		bindKey(renderer, context, "LEFT_RELEASED",  KeyEvent.VK_LEFT,  false);
		bindKey(renderer, context, "RIGHT_RELEASED", KeyEvent.VK_RIGHT, false);
		bindKey(renderer, context, "UP_RELEASED",    KeyEvent.VK_UP,    false);
		bindKey(renderer, context, "DOWN_RELEASED",  KeyEvent.VK_DOWN,  false);
		bindKey(renderer, context, "JUMP_RELEASED",  KeyEvent.VK_SPACE, false);
	}
	
	private void bindKey(Renderer renderer, GameContext context, String keyName, int keyCode , boolean pressed)
	{
		InputMap inputMap = renderer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = renderer.getActionMap();
		
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
				}
			}	
		});
	}
	
	
	public List<Sprite> getCurrentSpritesList()
	{ return currentSpritesList; }
	
	@Override
	public Dimension getPreferredSize() 
	{ return new Dimension(RoomMap.PIXELS_MAP_WIDTH, RoomMap.PIXELS_MAP_HEIGHT); }
}
