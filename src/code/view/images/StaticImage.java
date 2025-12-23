package code.view.images;

//data strucutre import
import java.util.Map;
import java.util.Arrays;
import java.util.stream.Collectors;
//graphics import
import java.awt.image.BufferedImage;

import code.model.gameobjects.Furniture;
//model import
import code.model.room.Room;

public enum StaticImage 
{
	GREEN_WALL_RIGHT		(ImageUtils.loadImage("/resoruces/FixedObjects/Walls/GreenWalls/GreenWall.png"), 	        Room.Color.GREEN, Type.MIDDLE_WALL_RIGHT),
	GREEN_WALL_LEFT		    (ImageUtils.loadFlipped("/resoruces/FixedObjects/Walls/GreenWalls/GreenWall.png"), 	        Room.Color.GREEN, Type.MIDDLE_WALL_LEFT),
	GREEN_UPPER_RIGHT_WALL  (ImageUtils.loadImage("/resoruces/FixedObjects/Walls/GreenWalls/GreenWallUpperRight.png"),  Room.Color.GREEN, Type.UPPER_RIGHT_WALL),
	GREEN_UPPER_LEFT_WALL   (ImageUtils.loadImage("/resoruces/FixedObjects/Walls/GreenWalls/GreenWallUpperLeft.png"),   Room.Color.GREEN, Type.UPPER_LEFT_WALL),
	GREEN_BOTTOM_RIGHT_WALL (ImageUtils.loadImage("/resoruces/FixedObjects/Walls/GreenWalls/GreenWallBottomRight.png"), Room.Color.GREEN, Type.BOTTOM_RIGHT_WALL),
	GREEN_BOTTOM_LEFT_WALL  (ImageUtils.loadImage("/resoruces/FixedObjects/Walls/GreenWalls/GreenWallBottomLeft.png"),  Room.Color.GREEN, Type.BOTTOM_LEFT_WALL),
	GREEN_FLOOR             (ImageUtils.loadImage("/resoruces/FixedObjects/Floors/GreenFloor.png"), 					Room.Color.GREEN, Type.FLOOR),
	GREEN_ARMOR_TYPE1		(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Armor1.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.ARMOR_TYPE1), 
	GREEN_ARMOR_TYPE2		(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Armor2.png"), 				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.ARMOR_TYPE2), 
	GREEN_BARREL			(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Barrel.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.BARREL), 
	GREEN_BIGMIRROR			(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/BigMirror.png"),				Room.Color.GREEN, Type.FURNITURE, Furniture.Type.BIGMIRROR), 
	GREEN_BIGTOTEM			(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/BigTotem.png"), 				Room.Color.GREEN, Type.FURNITURE, Furniture.Type.BIGTOTEM),
	GREEN_BOOKSHELF_TYPE1	(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Bookshelf1.png"),			    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.BOOKSHELF_TYPE1),
	GREEN_BOOKSHELFT_TYPE2	(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Bookshelf2.png"),			    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.BOOKSHELFT_TYPE2), 
	GREEN_CAVE				(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Cave.png"),					    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.CAVE), 
	GREEN_CHALICE			(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Chalice.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.CHALICE), 
	GREEN_CHEST				(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Chest.png"),					Room.Color.GREEN, Type.FURNITURE, Furniture.Type.CHEST), 
	GREEN_CLOCK				(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Clock.png"),					Room.Color.GREEN, Type.FURNITURE, Furniture.Type.CLOCK), 
	GREEN_COLUMN_TYPE1		(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Column1.png"),			        Room.Color.GREEN, Type.FURNITURE, Furniture.Type.COLUMN_TYPE1), 
	GREEN_COLUMN_TYPE2		(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Column2.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.COLUMN_TYPE2),
	GREEN_LANTERN			(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Lantern.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.LANTERN), 
	GREEN_LOG			    (ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Log.png"),					    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.LOG), 
	GREEN_LOGCHEST		    (ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/LogChest.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.LOGCHEST),
	GREEN_MEDIUMMIRROR_TYPE1(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/MediumMirror1.png"),			Room.Color.GREEN, Type.FURNITURE, Furniture.Type.MEDIUMMIRROR_TYPE1),
	GREEN_MEDIUMMIRROR_TYPE2(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/MediumMirror2.png"), 		    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.MEDIUMMIRROR_TYPE2), 
	GREEN_PYRAMIDOFBARRELS	(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/PyramidOfBarrels.png"),		    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.PYRAMIDOFBARRELS),
	GREEN_RUINS_TYPE1		(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Ruins1.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.RUINS_TYPE1),
	GREEN_RUINS_TYPE2		(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Ruins2.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.RUINS_TYPE2), 
	GREEN_SHIELD_TYPE1		(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Shield1.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.SHIELD_TYPE1), 
	GREEN_SHIEDL_TYPE2		(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Shield2.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.SHIEDL_TYPE2), 
	GREEN_STRANGEDOOR		(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/StrangeDoor.png"),			    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.STRANGEDOOR), 
	GREEN_TABLE_TYPE1		(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Table1.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.TABLE_TYPE1),
	GREEN_TABLE_TYP2		(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Table2.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.TABLE_TYP2	), 
	GREEN_TOTEM_TYPE1		(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Totem1.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.TOTEM_TYPE1), 
	GREEN_TOTEM_TYPE2		(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Totem2.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.TOTEM_TYPE2), 
	GREEN_VASE				(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Vase.png"),					    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.VASE),
	GREEN_WARDROBE_TYPE1	(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Wardrobe1.png"),				Room.Color.GREEN, Type.FURNITURE, Furniture.Type.WARDROBE_TYPE1), 
	GREEN_WARDOROBE_TYPE2	(ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/Wardrobe2.png"),				Room.Color.GREEN, Type.FURNITURE, Furniture.Type.WARDOROBE_TYPE2),
	GREEN_BROKEN_COLUMN     (ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/BrokenColumn.png"),             Room.Color.GREEN, Type.FURNITURE, Furniture.Type.BROKEN_COLUMN),
	GREEN_IDK               (ImageUtils.loadImage("/resoruces/Furnitures/GreenFurniture/IDK.png"),                      Room.Color.GREEN, Type.FURNITURE, Furniture.Type.IDK),
	
	RED_WALL_RIGHT		  (ImageUtils.loadImage("/resoruces/FixedObjects/Walls/RedWalls/RedWall.png"), 	          Room.Color.RED, Type.MIDDLE_WALL_RIGHT),
	RED_WALL_LEFT		  (ImageUtils.loadFlipped("/resoruces/FixedObjects/Walls/RedWalls/RedWall.png"), 	      Room.Color.RED, Type.MIDDLE_WALL_LEFT),
	RED_UPPER_RIGHT_WALL  (ImageUtils.loadImage("/resoruces/FixedObjects/Walls/RedWalls/RedWallUpperRight.png"),  Room.Color.RED, Type.UPPER_RIGHT_WALL),
	RED_UPPER_LEFT_WALL   (ImageUtils.loadImage("/resoruces/FixedObjects/Walls/RedWalls/RedWallUpperLeft.png"),   Room.Color.RED, Type.UPPER_LEFT_WALL),
	RED_BOTTOM_RIGHT_WALL (ImageUtils.loadImage("/resoruces/FixedObjects/Walls/RedWalls/RedWallBottomRight.png"), Room.Color.RED, Type.BOTTOM_RIGHT_WALL),
	RED_BOTTOM_LEFT_WALL  (ImageUtils.loadImage("/resoruces/FixedObjects/Walls/RedWalls/RedWallBottomLeft.png"),  Room.Color.RED, Type.BOTTOM_LEFT_WALL),
	RED_FLOOR             (ImageUtils.loadImage("/resoruces/FixedObjects/Floors/RedFloor.png"), 				  Room.Color.RED, Type.FLOOR),
	RED_ARMOR_TYPE1		  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Armor1.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.ARMOR_TYPE1), 
	RED_ARMOR_TYPE2		  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Armor2.png"), 				  Room.Color.RED, Type.FURNITURE, Furniture.Type.ARMOR_TYPE2), 
	RED_BARREL			  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Barrel.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.BARREL), 
	RED_BIGMIRROR		  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/BigMirror.png"),			  Room.Color.RED, Type.FURNITURE, Furniture.Type.BIGMIRROR), 
	RED_BIGTOTEM		  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/BigTotem.png"),	 		      Room.Color.RED, Type.FURNITURE, Furniture.Type.BIGTOTEM),
	RED_BOOKSHELF_TYPE1	  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Bookshelf1.png"),			  Room.Color.RED, Type.FURNITURE, Furniture.Type.BOOKSHELF_TYPE1),
	RED_BOOKSHELFT_TYPE2  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Bookshelf2.png"),		      Room.Color.RED, Type.FURNITURE, Furniture.Type.BOOKSHELFT_TYPE2), 
	RED_CAVE			  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Cave.png"),			          Room.Color.RED, Type.FURNITURE, Furniture.Type.CAVE), 
	RED_CHALICE			  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Chalice.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.CHALICE), 
	RED_CHEST			  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Chest.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.CHEST), 
	RED_CLOCK			  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Clock.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.CLOCK), 
	RED_COLUMN_TYPE1	  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Column1.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.COLUMN_TYPE1), 
	RED_COLUMN_TYPE2	  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Column2.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.COLUMN_TYPE2),
	RED_LANTERN			  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Lantern.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.LANTERN  ), 
	RED_LOG			      (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Log.png"),					  Room.Color.RED, Type.FURNITURE, Furniture.Type.LOG), 
	RED_LOGCHEST		  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/LogChest.png"),			      Room.Color.RED, Type.FURNITURE, Furniture.Type.LOGCHEST),
	RED_MEDIUMMIRROR_TYPE1(ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/MediumMirror1.png"),		  Room.Color.RED, Type.FURNITURE, Furniture.Type.MEDIUMMIRROR_TYPE1),
	RED_MEDIUMMIRROR_TYPE2(ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/MediumMirror2.png"), 		  Room.Color.RED, Type.FURNITURE, Furniture.Type.MEDIUMMIRROR_TYPE2), 
	RED_PYRAMIDOFBARRELS  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/PyramidOfBarrels.png"),	      Room.Color.RED, Type.FURNITURE, Furniture.Type.PYRAMIDOFBARRELS),
	RED_RUINS_TYPE1		  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Ruins1.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.RUINS_TYPE1),
	RED_RUINS_TYPE2		  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Ruins2.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.RUINS_TYPE2), 
	RED_SHIELD_TYPE1	  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Shield1.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.SHIELD_TYPE1), 
	RED_SHIEDL_TYPE2	  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Shield2.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.SHIEDL_TYPE2), 
	RED_STRANGEDOOR		  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/StrangeDoor.png"),			  Room.Color.RED, Type.FURNITURE, Furniture.Type.STRANGEDOOR), 
	RED_TABLE_TYPE1		  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Table1.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.TABLE_TYPE1),
	RED_TABLE_TYP2		  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Table2.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.TABLE_TYP2), 
	RED_TOTEM_TYPE1		  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Totem1.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.TOTEM_TYPE1), 
	RED_TOTEM_TYPE2		  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Totem2.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.TOTEM_TYPE2), 
	RED_VASE			  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Vase.png"),				      Room.Color.RED, Type.FURNITURE, Furniture.Type.VASE),
	RED_WARDROBE_TYPE1	  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Wardrobe1.png"),			  Room.Color.RED, Type.FURNITURE, Furniture.Type.WARDROBE_TYPE1), 
	RED_WARDOROBE_TYPE2	  (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/Wardrobe2.png"),			  Room.Color.RED, Type.FURNITURE, Furniture.Type.WARDOROBE_TYPE2),
	RED_BROKEN_COLUMN     (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/BrokenColumn.png"),           Room.Color.RED, Type.FURNITURE, Furniture.Type.BROKEN_COLUMN),
	RED_IDK               (ImageUtils.loadImage("/resoruces/Furnitures/RedFurniture/IDK.png"),                    Room.Color.RED, Type.FURNITURE, Furniture.Type.IDK),
	
	PURPLE_WALL_RIGHT		 (ImageUtils.loadImage("/resoruces/FixedObjects/Walls/PurpleWalls/PurpleWall.png"), 	       Room.Color.PURPLE, Type.MIDDLE_WALL_RIGHT),
	PURPLE_WALL_LEFT		 (ImageUtils.loadFlipped("/resoruces/FixedObjects/Walls/PurpleWalls/PurpleWall.png"), 	       Room.Color.PURPLE, Type.MIDDLE_WALL_LEFT),
	PURPLE_UPPER_RIGHT_WALL  (ImageUtils.loadImage("/resoruces/FixedObjects/Walls/PurpleWalls/PurpleWallUpperRight.png"),  Room.Color.PURPLE, Type.UPPER_RIGHT_WALL),
	PURPLE_UPPER_LEFT_WALL   (ImageUtils.loadImage("/resoruces/FixedObjects/Walls/PurpleWalls/PurpleWallUpperLeft.png"),   Room.Color.PURPLE, Type.UPPER_LEFT_WALL),
	PURPLE_BOTTOM_RIGHT_WALL (ImageUtils.loadImage("/resoruces/FixedObjects/Walls/PurpleWalls/PurpleWallBottomRight.png"), Room.Color.PURPLE, Type.BOTTOM_RIGHT_WALL),
	PURPLE_BOTTOM_LEFT_WALL  (ImageUtils.loadImage("/resoruces/FixedObjects/Walls/PurpleWalls/PurpleWallBottomLeft.png"),  Room.Color.PURPLE, Type.BOTTOM_LEFT_WALL),
	PURPLE_FLOOR             (ImageUtils.loadImage("/resoruces/FixedObjects/Floors/PurpleFloor.png"), 				       Room.Color.PURPLE, Type.FLOOR),
	PURPLE_ARMOR_TYPE1		 (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Armor1.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.ARMOR_TYPE1), 
	PURPLE_ARMOR_TYPE2		 (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Armor2.png"), 				   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.ARMOR_TYPE2), 
	PURPLE_BARREL			 (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Barrel.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.BARREL), 
	PURPLE_BIGMIRROR		 (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/BigMirror.png"),			       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.BIGMIRROR), 
	PURPLE_BIGTOTEM		     (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/BigTotem.png"),	 		       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.BIGTOTEM),
	PURPLE_BOOKSHELF_TYPE1	 (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Bookshelf1.png"),			       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.BOOKSHELF_TYPE1),
	PURPLE_BOOKSHELFT_TYPE2  (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Bookshelf2.png"),		           Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.BOOKSHELFT_TYPE2), 
	PURPLE_CAVE			     (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Cave.png"),			           Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.CAVE), 
	PURPLE_CHALICE			 (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Chalice.png"),				   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.CHALICE), 
	PURPLE_CHEST			 (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Chest.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.CHEST), 
	PURPLE_CLOCK			 (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Clock.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.CLOCK), 
	PURPLE_COLUMN_TYPE1	     (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Column1.png"),				   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.COLUMN_TYPE1), 
	PURPLE_COLUMN_TYPE2	     (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Column2.png"),				   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.COLUMN_TYPE2),
	PURPLE_LANTERN			 (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Lantern.png"),				   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.LANTERN), 
	PURPLE_LOG			     (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Log.png"),					   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.LOG ), 
	PURPLE_LOGCHEST		     (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/LogChest.png"),			       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.LOGCHEST),
	PURPLE_MEDIUMMIRROR_TYPE1(ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/MediumMirror1.png"),		       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.MEDIUMMIRROR_TYPE1),
	PURPLE_MEDIUMMIRROR_TYPE2(ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/MediumMirror2.png"), 		       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.MEDIUMMIRROR_TYPE2), 
	PURPLE_PYRAMIDOFBARRELS  (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/PyramidOfBarrels.png"),	       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.PYRAMIDOFBARRELS),
	PURPLE_RUINS_TYPE1		 (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Ruins1.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.RUINS_TYPE1),
	PURPLE_RUINS_TYPE2		 (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Ruins2.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.RUINS_TYPE2), 
	PURPLE_SHIELD_TYPE1	     (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Shield1.png"),				   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.SHIELD_TYPE1), 
	PURPLE_SHIEDL_TYPE2	     (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Shield2.png"),				   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.SHIEDL_TYPE2), 
	PURPLE_STRANGEDOOR		 (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/StrangeDoor.png"),			   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.STRANGEDOOR), 
	PURPLE_TABLE_TYPE1		 (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Table1.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.TABLE_TYPE1),
	PURPLE_TABLE_TYP2		 (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Table2.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.TABLE_TYP2), 
	PURPLE_TOTEM_TYPE1		 (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Totem1.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.TOTEM_TYPE1), 
	PURPLE_TOTEM_TYPE2		 (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Totem2.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.TOTEM_TYPE2), 
	PURPLE_VASE			     (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Vase.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.VASE),
	PURPLE_WARDROBE_TYPE1	 (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Wardrobe1.png"),			       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.WARDROBE_TYPE1), 
	PURPLE_WARDOROBE_TYPE2	 (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/Wardrobe2.png"),			       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.WARDOROBE_TYPE2),
	PURPLE_BROKEN_COLUMN     (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/BrokenColumn.png"),              Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.BROKEN_COLUMN),
	PURPLE_IDK               (ImageUtils.loadImage("/resoruces/Furnitures/PurpleFurniture/IDK.png"),                	   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.IDK),
	 
	PLATFORM_MID  (ImageUtils.loadImage("/resoruces/Platforms/PlatformMid.png"),   Type.PLATFORM),
	PLATFORM_END  (ImageUtils.loadImage("/resoruces/Platforms/PlatformEnd.png"),   Type.PLATFORM),
	PLATFORM_START(ImageUtils.loadFlipped("/resoruces/Platforms/PlatformEnd.png"), Type.PLATFORM),
	
	TERMINAL(ImageUtils.loadFlipped("/resoruces/Terminal.png"), Type.TERMINAL);
	
	private static final Map<Room.Color, Map<Furniture.Type, StaticImage>> furnitureMap =  
			Arrays.stream(StaticImage.values()).filter(i -> i.type == Type.FURNITURE).collect(Collectors.groupingBy(i -> i.color, Collectors.toMap(i -> i.furnitureType, i -> i)));
	
	private static final StaticImage[] greenFurnitures  = Arrays.stream(StaticImage.values()).filter(i -> i.color == Room.Color.GREEN && i.type == Type.FURNITURE).toArray(StaticImage[]::new);
	private static final StaticImage[] redFurnitures    = Arrays.stream(StaticImage.values()).filter(i -> i.color == Room.Color.RED && i.type == Type.FURNITURE).toArray(StaticImage[]::new);
	private static final StaticImage[] purpleFurnitures = Arrays.stream(StaticImage.values()).filter(i -> i.color == Room.Color.PURPLE && i.type == Type.FURNITURE).toArray(StaticImage[]::new);
	
	private static final Map<Room.Color, StaticImage> rightMiddleWalls = 
			Arrays.stream(StaticImage.values()).filter(i -> i.type == Type.MIDDLE_WALL_RIGHT).collect(Collectors.toMap(i -> i.color, i -> i));
	private static final Map<Room.Color, StaticImage> leftMiddleWalls = 
			Arrays.stream(StaticImage.values()).filter(i -> i.type == Type.MIDDLE_WALL_LEFT).collect(Collectors.toMap(i -> i.color, i -> i));
	private static final Map<Room.Color, StaticImage> upperLeftWalls = 
			Arrays.stream(StaticImage.values()).filter(i -> i.type == Type.UPPER_LEFT_WALL).collect(Collectors.toMap(i -> i.color, i -> i));
	private static final Map<Room.Color, StaticImage> upperRightWalls = 
			Arrays.stream(StaticImage.values()).filter(i -> i.type == Type.UPPER_RIGHT_WALL).collect(Collectors.toMap(i -> i.color, i -> i));
	private static final Map<Room.Color, StaticImage> bottomLeftWalls = 
			Arrays.stream(StaticImage.values()).filter(i -> i.type == Type.BOTTOM_LEFT_WALL).collect(Collectors.toMap(i -> i.color, i -> i));
	private static final Map<Room.Color, StaticImage> bottomRightWalls = 
			Arrays.stream(StaticImage.values()).filter(i -> i.type == Type.BOTTOM_RIGHT_WALL).collect(Collectors.toMap(i -> i.color, i -> i));
	
	private BufferedImage image;
	private Room.Color color;
	private Type type;
	private Furniture.Type furnitureType;
	
	public enum Type 
	{ 
		TERMINAL, MIDDLE_WALL_RIGHT, MIDDLE_WALL_LEFT, UPPER_LEFT_WALL, 
		UPPER_RIGHT_WALL, BOTTOM_LEFT_WALL, BOTTOM_RIGHT_WALL, FURNITURE, FLOOR, PLATFORM 
	}
		
	private StaticImage(BufferedImage image, Room.Color color, Type type, Furniture.Type furnitureType)
	{
		this.image = image; 
		this.color = color;
		this.type = type;
		this.furnitureType = furnitureType;
	}
	
	private StaticImage(BufferedImage image, Type type)
	{ this(image, null, type, null); }
	
	private StaticImage(BufferedImage image, Room.Color color, Type type)
	{ this(image, color, type, null); }

	public BufferedImage getImage()
	{ return image; }	
	
	public static StaticImage getFurniture(Furniture.Type type, Room.Color color)
	{ return furnitureMap.get(color).get(type); }
	
	public static StaticImage[] getFurnitures(Room.Color color)
	{
		return switch(color) {
			case Room.Color.GREEN  -> greenFurnitures.clone();
			case Room.Color.RED    -> redFurnitures.clone();
			case Room.Color.PURPLE -> purpleFurnitures.clone();
		};
	}
	
	public static StaticImage getFloor(Room.Color color)
	{
		return switch(color) {
			case Room.Color.GREEN  -> GREEN_FLOOR;
			case Room.Color.RED    -> RED_FLOOR;
			case Room.Color.PURPLE -> PURPLE_FLOOR;
		};
	}
	
	public static StaticImage getWall(Room.Color color, Type wallType)
	{ 
		return switch(wallType) {
			case Type.MIDDLE_WALL_LEFT  -> leftMiddleWalls.get(color);
			case Type.MIDDLE_WALL_RIGHT -> rightMiddleWalls.get(color);
			case Type.UPPER_LEFT_WALL   -> upperLeftWalls.get(color);
			case Type.UPPER_RIGHT_WALL  -> upperRightWalls.get(color);
			case Type.BOTTOM_LEFT_WALL  -> bottomLeftWalls.get(color);
			case Type.BOTTOM_RIGHT_WALL -> bottomRightWalls.get(color);
			default -> throw new IllegalArgumentException(wallType + "is not a valid wall type.");
		};
	}
}
