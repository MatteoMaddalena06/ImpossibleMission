package code.view;

//data structure import
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.LinkedList;
//graphics import
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
//view import
import code.view.sprites.Sprite;
import code.view.sprites.SpriteFactory;
import code.view.images.StaticImage;
import code.view.sprites.PlayerSprite;
import code.view.sprites.SearchingWindow;
//model import
import code.model.context.GameContext;
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
import code.model.Point;
//model event import
import code.model.context.GameEvent;
import code.model.context.AttackEnded;
import code.model.context.AttackLaunched;
import code.model.context.FurnitureSearchEnded;
import code.model.context.PlayerDied;
import code.model.context.PlayerFoundSomething;
import code.model.context.PlayerIsSearching;

public class Renderer extends JPanel implements GameContext.EventListener
{
	private Room currentRoom;
	private List<Sprite> currentSpritesList; 
	private Map<Room, List<Sprite>> spritesListsCache;
	
	private boolean printSearchingState;
	private boolean printFurnitureLoot;
	private Furniture interestingFurniture;
	
	public Renderer()
	{
		currentSpritesList = new LinkedList<Sprite>();
		spritesListsCache = new HashMap<Room, List<Sprite>>();
		printSearchingState = printFurnitureLoot = false;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
    	super.paintComponent(g);
    	
    	this.setBackground(Color.BLACK);
    	
    	List<Sprite> firstLayerSprites = currentSpritesList.stream().filter(s -> {
    		GameObject go = s.getGameObject();
    		return go instanceof FixedObject || go instanceof Furniture || go instanceof Terminal || go instanceof Platform;
    	}).toList();
    	
    	List<Sprite> secondLayerSprites = currentSpritesList.stream().filter(s -> {
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
    	
    	g.dispose();
    } 
	
	private void paintImage(GameObject bindedGameObject, BufferedImage image, Graphics g)
	{
		Point gameObjectPosition = bindedGameObject.copyPosition();
		int gameObjectX = (int)gameObjectPosition.getX(), gameObjectY = (int)gameObjectPosition.getY();
		
		int overflow = image.getHeight() - bindedGameObject.getHeight();
		
		g.drawImage(image, gameObjectX + bindedGameObject.getWidth() / 2 - image.getWidth() / 2, gameObjectY - overflow, null);
	}
	
	private void paintFurnitureInfo(Furniture furniture, BufferedImage image, Graphics g)
	{
		Point furniturePosition = interestingFurniture.copyPosition();
		int furnitureX = (int)furniturePosition.getX(), furnitureY = (int)furniturePosition.getY();
		g.drawImage(image,  furnitureX + furniture.getWidth() / 2 - image.getWidth() / 2, furnitureY - image.getHeight(), null);
	}

	@Override 
	public void notifyEvent(GameEvent event)
	{
		if(event instanceof AttackLaunched)
			addAttackSprite(((AttackLaunched)event).source());
		
		else if(event instanceof AttackEnded)
			removeAttackSprite(((AttackEnded)event).source());
		
		else if(event instanceof FurnitureSearchEnded)
			removeFurnitureSprite(((FurnitureSearchEnded)event).source());
		
		else if(event instanceof PlayerDied)
			playPlayerDeadAnimation(((PlayerDied)event).source());
		
		else if(event instanceof PlayerFoundSomething)
			printFurnitureLoot(((PlayerFoundSomething)event).source());
	
		else 
			printSearchingState(((PlayerIsSearching)event).source());
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
	
	private void playPlayerDeadAnimation(Player player)
	{ /*TODO*/ }
	
	private void printFurnitureLoot(Furniture furniture)
	{ printFurnitureLoot = true; interestingFurniture = furniture; }
	
	private void printSearchingState(Furniture furniture)
	{ printSearchingState = true; interestingFurniture = furniture; }
	
	public void setCurrentSpritesList(Player player, Room room)
	{
		currentRoom = room;
		
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
		spritesListsCache.put(currentRoom, spritesListToStore);
	}
	
	public List<Sprite> getCurrentSpritesList()
	{ return currentSpritesList; }
	
	@Override
	public Dimension getPreferredSize() 
	{ return new Dimension(RoomMap.MAP_WIDTH * RoomMap.TILE_SIZE, RoomMap.MAP_HEIGHT * RoomMap.TILE_SIZE); }
}
