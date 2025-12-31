package code.view.images;

//data strucutre import
import java.util.Map;
import java.util.Arrays;
import java.util.stream.Collectors;
//graphics import
import java.awt.image.BufferedImage;
//model import
import code.model.room.Room;
import code.model.gameobjects.Furniture;
import code.model.puzzle.PuzzlePiece;
import code.model.puzzle.PuzzlePiece.Position;

public enum StaticImage 
{
	GREEN_WALL_RIGHT		(ImageUtils.loadImage("/resources/FixedObjects/Walls/GreenWalls/GreenWall.png"), 	        Room.Color.GREEN, Type.MIDDLE_WALL_RIGHT),
	GREEN_WALL_LEFT		    (ImageUtils.loadFlipped("/resources/FixedObjects/Walls/GreenWalls/GreenWall.png"), 	        Room.Color.GREEN, Type.MIDDLE_WALL_LEFT),
	GREEN_UPPER_RIGHT_WALL  (ImageUtils.loadImage("/resources/FixedObjects/Walls/GreenWalls/GreenWallUpperRight.png"),  Room.Color.GREEN, Type.UPPER_RIGHT_WALL),
	GREEN_UPPER_LEFT_WALL   (ImageUtils.loadImage("/resources/FixedObjects/Walls/GreenWalls/GreenWallUpperLeft.png"),   Room.Color.GREEN, Type.UPPER_LEFT_WALL),
	GREEN_BOTTOM_RIGHT_WALL (ImageUtils.loadImage("/resources/FixedObjects/Walls/GreenWalls/GreenWallBottomRight.png"), Room.Color.GREEN, Type.BOTTOM_RIGHT_WALL),
	GREEN_BOTTOM_LEFT_WALL  (ImageUtils.loadImage("/resources/FixedObjects/Walls/GreenWalls/GreenWallBottomLeft.png"),  Room.Color.GREEN, Type.BOTTOM_LEFT_WALL),
	GREEN_FLOOR             (ImageUtils.loadImage("/resources/FixedObjects/Floors/GreenFloor.png"), 					Room.Color.GREEN, Type.FLOOR),
	GREEN_ARMOR_TYPE1		(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Armor1.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.ARMOR_TYPE1), 
	GREEN_ARMOR_TYPE2		(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Armor2.png"), 				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.ARMOR_TYPE2), 
	GREEN_BARREL			(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Barrel.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.BARREL), 
	GREEN_BIGMIRROR			(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/BigMirror.png"),				Room.Color.GREEN, Type.FURNITURE, Furniture.Type.BIGMIRROR), 
	GREEN_BIGTOTEM			(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/BigTotem.png"), 				Room.Color.GREEN, Type.FURNITURE, Furniture.Type.BIGTOTEM),
	GREEN_BOOKSHELF_TYPE1	(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Bookshelf1.png"),			    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.BOOKSHELF_TYPE1),
	GREEN_BOOKSHELFT_TYPE2	(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Bookshelf2.png"),			    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.BOOKSHELFT_TYPE2), 
	GREEN_CAVE				(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Cave.png"),					    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.CAVE), 
	GREEN_CHALICE			(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Chalice.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.CHALICE), 
	GREEN_CHEST				(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Chest.png"),					Room.Color.GREEN, Type.FURNITURE, Furniture.Type.CHEST), 
	GREEN_CLOCK				(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Clock.png"),					Room.Color.GREEN, Type.FURNITURE, Furniture.Type.CLOCK), 
	GREEN_COLUMN_TYPE1		(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Column1.png"),			        Room.Color.GREEN, Type.FURNITURE, Furniture.Type.COLUMN_TYPE1), 
	GREEN_COLUMN_TYPE2		(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Column2.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.COLUMN_TYPE2),
	GREEN_LANTERN			(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Lantern.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.LANTERN), 
	GREEN_LOG			    (ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Log.png"),					    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.LOG), 
	GREEN_LOGCHEST		    (ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/LogChest.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.LOGCHEST),
	GREEN_MEDIUMMIRROR_TYPE1(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/MediumMirror1.png"),			Room.Color.GREEN, Type.FURNITURE, Furniture.Type.MEDIUMMIRROR_TYPE1),
	GREEN_MEDIUMMIRROR_TYPE2(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/MediumMirror2.png"), 		    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.MEDIUMMIRROR_TYPE2), 
	GREEN_PYRAMIDOFBARRELS	(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/PyramidOfBarrels.png"),		    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.PYRAMIDOFBARRELS),
	GREEN_RUINS_TYPE1		(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Ruins1.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.RUINS_TYPE1),
	GREEN_RUINS_TYPE2		(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Ruins2.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.RUINS_TYPE2), 
	GREEN_SHIELD_TYPE1		(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Shield1.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.SHIELD_TYPE1), 
	GREEN_SHIEDL_TYPE2		(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Shield2.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.SHIEDL_TYPE2), 
	GREEN_STRANGEDOOR		(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/StrangeDoor.png"),			    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.STRANGEDOOR), 
	GREEN_TABLE_TYPE1		(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Table1.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.TABLE_TYPE1),
	GREEN_TABLE_TYP2		(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Table2.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.TABLE_TYP2	), 
	GREEN_TOTEM_TYPE1		(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Totem1.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.TOTEM_TYPE1), 
	GREEN_TOTEM_TYPE2		(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Totem2.png"),				    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.TOTEM_TYPE2), 
	GREEN_VASE				(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Vase.png"),					    Room.Color.GREEN, Type.FURNITURE, Furniture.Type.VASE),
	GREEN_WARDROBE_TYPE1	(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Wardrobe1.png"),				Room.Color.GREEN, Type.FURNITURE, Furniture.Type.WARDROBE_TYPE1), 
	GREEN_WARDOROBE_TYPE2	(ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/Wardrobe2.png"),				Room.Color.GREEN, Type.FURNITURE, Furniture.Type.WARDOROBE_TYPE2),
	GREEN_BROKEN_COLUMN     (ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/BrokenColumn.png"),             Room.Color.GREEN, Type.FURNITURE, Furniture.Type.BROKEN_COLUMN),
	GREEN_IDK               (ImageUtils.loadImage("/resources/Furnitures/GreenFurniture/IDK.png"),                      Room.Color.GREEN, Type.FURNITURE, Furniture.Type.IDK),
	
	RED_WALL_RIGHT		  (ImageUtils.loadImage("/resources/FixedObjects/Walls/RedWalls/RedWall.png"), 	          Room.Color.RED, Type.MIDDLE_WALL_RIGHT),
	RED_WALL_LEFT		  (ImageUtils.loadFlipped("/resources/FixedObjects/Walls/RedWalls/RedWall.png"), 	      Room.Color.RED, Type.MIDDLE_WALL_LEFT),
	RED_UPPER_RIGHT_WALL  (ImageUtils.loadImage("/resources/FixedObjects/Walls/RedWalls/RedWallUpperRight.png"),  Room.Color.RED, Type.UPPER_RIGHT_WALL),
	RED_UPPER_LEFT_WALL   (ImageUtils.loadImage("/resources/FixedObjects/Walls/RedWalls/RedWallUpperLeft.png"),   Room.Color.RED, Type.UPPER_LEFT_WALL),
	RED_BOTTOM_RIGHT_WALL (ImageUtils.loadImage("/resources/FixedObjects/Walls/RedWalls/RedWallBottomRight.png"), Room.Color.RED, Type.BOTTOM_RIGHT_WALL),
	RED_BOTTOM_LEFT_WALL  (ImageUtils.loadImage("/resources/FixedObjects/Walls/RedWalls/RedWallBottomLeft.png"),  Room.Color.RED, Type.BOTTOM_LEFT_WALL),
	RED_FLOOR             (ImageUtils.loadImage("/resources/FixedObjects/Floors/RedFloor.png"), 				  Room.Color.RED, Type.FLOOR),
	RED_ARMOR_TYPE1		  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Armor1.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.ARMOR_TYPE1), 
	RED_ARMOR_TYPE2		  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Armor2.png"), 				  Room.Color.RED, Type.FURNITURE, Furniture.Type.ARMOR_TYPE2), 
	RED_BARREL			  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Barrel.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.BARREL), 
	RED_BIGMIRROR		  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/BigMirror.png"),			  Room.Color.RED, Type.FURNITURE, Furniture.Type.BIGMIRROR), 
	RED_BIGTOTEM		  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/BigTotem.png"),	 		      Room.Color.RED, Type.FURNITURE, Furniture.Type.BIGTOTEM),
	RED_BOOKSHELF_TYPE1	  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Bookshelf1.png"),			  Room.Color.RED, Type.FURNITURE, Furniture.Type.BOOKSHELF_TYPE1),
	RED_BOOKSHELFT_TYPE2  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Bookshelf2.png"),		      Room.Color.RED, Type.FURNITURE, Furniture.Type.BOOKSHELFT_TYPE2), 
	RED_CAVE			  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Cave.png"),			          Room.Color.RED, Type.FURNITURE, Furniture.Type.CAVE), 
	RED_CHALICE			  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Chalice.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.CHALICE), 
	RED_CHEST			  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Chest.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.CHEST), 
	RED_CLOCK			  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Clock.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.CLOCK), 
	RED_COLUMN_TYPE1	  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Column1.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.COLUMN_TYPE1), 
	RED_COLUMN_TYPE2	  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Column2.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.COLUMN_TYPE2),
	RED_LANTERN			  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Lantern.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.LANTERN  ), 
	RED_LOG			      (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Log.png"),					  Room.Color.RED, Type.FURNITURE, Furniture.Type.LOG), 
	RED_LOGCHEST		  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/LogChest.png"),			      Room.Color.RED, Type.FURNITURE, Furniture.Type.LOGCHEST),
	RED_MEDIUMMIRROR_TYPE1(ImageUtils.loadImage("/resources/Furnitures/RedFurniture/MediumMirror1.png"),		  Room.Color.RED, Type.FURNITURE, Furniture.Type.MEDIUMMIRROR_TYPE1),
	RED_MEDIUMMIRROR_TYPE2(ImageUtils.loadImage("/resources/Furnitures/RedFurniture/MediumMirror2.png"), 		  Room.Color.RED, Type.FURNITURE, Furniture.Type.MEDIUMMIRROR_TYPE2), 
	RED_PYRAMIDOFBARRELS  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/PyramidOfBarrels.png"),	      Room.Color.RED, Type.FURNITURE, Furniture.Type.PYRAMIDOFBARRELS),
	RED_RUINS_TYPE1		  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Ruins1.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.RUINS_TYPE1),
	RED_RUINS_TYPE2		  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Ruins2.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.RUINS_TYPE2), 
	RED_SHIELD_TYPE1	  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Shield1.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.SHIELD_TYPE1), 
	RED_SHIEDL_TYPE2	  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Shield2.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.SHIEDL_TYPE2), 
	RED_STRANGEDOOR		  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/StrangeDoor.png"),			  Room.Color.RED, Type.FURNITURE, Furniture.Type.STRANGEDOOR), 
	RED_TABLE_TYPE1		  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Table1.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.TABLE_TYPE1),
	RED_TABLE_TYP2		  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Table2.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.TABLE_TYP2), 
	RED_TOTEM_TYPE1		  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Totem1.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.TOTEM_TYPE1), 
	RED_TOTEM_TYPE2		  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Totem2.png"),				  Room.Color.RED, Type.FURNITURE, Furniture.Type.TOTEM_TYPE2), 
	RED_VASE			  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Vase.png"),				      Room.Color.RED, Type.FURNITURE, Furniture.Type.VASE),
	RED_WARDROBE_TYPE1	  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Wardrobe1.png"),			  Room.Color.RED, Type.FURNITURE, Furniture.Type.WARDROBE_TYPE1), 
	RED_WARDOROBE_TYPE2	  (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/Wardrobe2.png"),			  Room.Color.RED, Type.FURNITURE, Furniture.Type.WARDOROBE_TYPE2),
	RED_BROKEN_COLUMN     (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/BrokenColumn.png"),           Room.Color.RED, Type.FURNITURE, Furniture.Type.BROKEN_COLUMN),
	RED_IDK               (ImageUtils.loadImage("/resources/Furnitures/RedFurniture/IDK.png"),                    Room.Color.RED, Type.FURNITURE, Furniture.Type.IDK),
	
	PURPLE_WALL_RIGHT		 (ImageUtils.loadImage("/resources/FixedObjects/Walls/PurpleWalls/PurpleWall.png"), 	       Room.Color.PURPLE, Type.MIDDLE_WALL_RIGHT),
	PURPLE_WALL_LEFT		 (ImageUtils.loadFlipped("/resources/FixedObjects/Walls/PurpleWalls/PurpleWall.png"), 	       Room.Color.PURPLE, Type.MIDDLE_WALL_LEFT),
	PURPLE_UPPER_RIGHT_WALL  (ImageUtils.loadImage("/resources/FixedObjects/Walls/PurpleWalls/PurpleWallUpperRight.png"),  Room.Color.PURPLE, Type.UPPER_RIGHT_WALL),
	PURPLE_UPPER_LEFT_WALL   (ImageUtils.loadImage("/resources/FixedObjects/Walls/PurpleWalls/PurpleWallUpperLeft.png"),   Room.Color.PURPLE, Type.UPPER_LEFT_WALL),
	PURPLE_BOTTOM_RIGHT_WALL (ImageUtils.loadImage("/resources/FixedObjects/Walls/PurpleWalls/PurpleWallBottomRight.png"), Room.Color.PURPLE, Type.BOTTOM_RIGHT_WALL),
	PURPLE_BOTTOM_LEFT_WALL  (ImageUtils.loadImage("/resources/FixedObjects/Walls/PurpleWalls/PurpleWallBottomLeft.png"),  Room.Color.PURPLE, Type.BOTTOM_LEFT_WALL),
	PURPLE_FLOOR             (ImageUtils.loadImage("/resources/FixedObjects/Floors/PurpleFloor.png"), 				       Room.Color.PURPLE, Type.FLOOR),
	PURPLE_ARMOR_TYPE1		 (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Armor1.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.ARMOR_TYPE1), 
	PURPLE_ARMOR_TYPE2		 (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Armor2.png"), 				   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.ARMOR_TYPE2), 
	PURPLE_BARREL			 (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Barrel.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.BARREL), 
	PURPLE_BIGMIRROR		 (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/BigMirror.png"),			       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.BIGMIRROR), 
	PURPLE_BIGTOTEM		     (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/BigTotem.png"),	 		       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.BIGTOTEM),
	PURPLE_BOOKSHELF_TYPE1	 (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Bookshelf1.png"),			       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.BOOKSHELF_TYPE1),
	PURPLE_BOOKSHELFT_TYPE2  (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Bookshelf2.png"),		           Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.BOOKSHELFT_TYPE2), 
	PURPLE_CAVE			     (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Cave.png"),			           Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.CAVE), 
	PURPLE_CHALICE			 (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Chalice.png"),				   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.CHALICE), 
	PURPLE_CHEST			 (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Chest.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.CHEST), 
	PURPLE_CLOCK			 (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Clock.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.CLOCK), 
	PURPLE_COLUMN_TYPE1	     (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Column1.png"),				   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.COLUMN_TYPE1), 
	PURPLE_COLUMN_TYPE2	     (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Column2.png"),				   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.COLUMN_TYPE2),
	PURPLE_LANTERN			 (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Lantern.png"),				   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.LANTERN), 
	PURPLE_LOG			     (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Log.png"),					   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.LOG ), 
	PURPLE_LOGCHEST		     (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/LogChest.png"),			       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.LOGCHEST),
	PURPLE_MEDIUMMIRROR_TYPE1(ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/MediumMirror1.png"),		       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.MEDIUMMIRROR_TYPE1),
	PURPLE_MEDIUMMIRROR_TYPE2(ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/MediumMirror2.png"), 		       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.MEDIUMMIRROR_TYPE2), 
	PURPLE_PYRAMIDOFBARRELS  (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/PyramidOfBarrels.png"),	       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.PYRAMIDOFBARRELS),
	PURPLE_RUINS_TYPE1		 (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Ruins1.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.RUINS_TYPE1),
	PURPLE_RUINS_TYPE2		 (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Ruins2.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.RUINS_TYPE2), 
	PURPLE_SHIELD_TYPE1	     (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Shield1.png"),				   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.SHIELD_TYPE1), 
	PURPLE_SHIEDL_TYPE2	     (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Shield2.png"),				   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.SHIEDL_TYPE2), 
	PURPLE_STRANGEDOOR		 (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/StrangeDoor.png"),			   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.STRANGEDOOR), 
	PURPLE_TABLE_TYPE1		 (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Table1.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.TABLE_TYPE1),
	PURPLE_TABLE_TYP2		 (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Table2.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.TABLE_TYP2), 
	PURPLE_TOTEM_TYPE1		 (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Totem1.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.TOTEM_TYPE1), 
	PURPLE_TOTEM_TYPE2		 (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Totem2.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.TOTEM_TYPE2), 
	PURPLE_VASE			     (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Vase.png"),				       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.VASE),
	PURPLE_WARDROBE_TYPE1	 (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Wardrobe1.png"),			       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.WARDROBE_TYPE1), 
	PURPLE_WARDOROBE_TYPE2	 (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/Wardrobe2.png"),			       Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.WARDOROBE_TYPE2),
	PURPLE_BROKEN_COLUMN     (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/BrokenColumn.png"),              Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.BROKEN_COLUMN),
	PURPLE_IDK               (ImageUtils.loadImage("/resources/Furnitures/PurpleFurniture/IDK.png"),                	   Room.Color.PURPLE, Type.FURNITURE, Furniture.Type.IDK),
	 
	PLATFORM_MID  (ImageUtils.loadImage("/resources/Platforms/PlatformMid.png"),   Type.PLATFORM),
	PLATFORM_END  (ImageUtils.loadImage("/resources/Platforms/PlatformEnd.png"),   Type.PLATFORM),
	PLATFORM_START(ImageUtils.loadFlipped("/resources/Platforms/PlatformEnd.png"), Type.PLATFORM),
	
	TERMINAL(ImageUtils.loadFlipped("/resources/Terminal.png"), Type.TERMINAL),
	
	EMPTY_LOOT(ImageUtils.loadImage("/resources/FurnitureLoots/EmptyLoot.png"), 		           Type.FURNITURE_LOOT, Furniture.LootType.EMPTY),
	ROBOT_PASSWORD_LOOT(ImageUtils.loadImage("/resources/FurnitureLoots/RobotPassword.png"),       Type.FURNITURE_LOOT, Furniture.LootType.ROBOT_PASSWORD),
	PLATFORM_PASSWORD_LOOT(ImageUtils.loadImage("/resources/FurnitureLoots/PlatformPassword.png"), Type.FURNITURE_LOOT, Furniture.LootType.PLATFORM_PASSWORD),
	
	A_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/a/a_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.A_UPPER_RIGHT),
	A_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/a/a_00.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.A_UPPER_LEFT ),  
	A_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/a/a_03.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.A_BOTTOM_RIGHT),
	A_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/a/a_02.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.A_BOTTOM_LEFT),
	
	B_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/b/b_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.B_UPPER_RIGHT),
	B_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/b/b_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.B_UPPER_LEFT ),  
	B_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/b/b_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.B_BOTTOM_RIGHT),
	B_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/b/b_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.B_BOTTOM_LEFT),
	
	C_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/c/c_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.C_UPPER_RIGHT),
	C_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/c/c_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.C_UPPER_LEFT ),  
	C_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/c/c_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.C_BOTTOM_RIGHT),
	C_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/c/c_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.C_BOTTOM_LEFT),
	
	D_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/d/d_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.D_UPPER_RIGHT),
	D_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/d/d_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.D_UPPER_LEFT ),  
	D_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/d/d_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.D_BOTTOM_RIGHT),
	D_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/d/d_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.D_BOTTOM_LEFT),
	
	E_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/e/e_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.E_UPPER_RIGHT),
	E_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/e/e_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.E_UPPER_LEFT ),  
	E_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/e/e_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.E_BOTTOM_RIGHT),
	E_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/e/e_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.E_BOTTOM_LEFT),
	
	F_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/f/f_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.F_UPPER_RIGHT),
	F_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/f/f_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.F_UPPER_LEFT ),  
	F_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/f/f_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.F_BOTTOM_RIGHT),
	F_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/f/f_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.F_BOTTOM_LEFT),
	
	G_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/g/g_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.G_UPPER_RIGHT),
	G_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/g/g_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.G_UPPER_LEFT ),  
	G_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/g/g_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.G_BOTTOM_RIGHT),
	G_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/g/g_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.G_BOTTOM_LEFT),
	
	H_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/h/h_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.H_UPPER_RIGHT),
	H_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/h/h_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.H_UPPER_LEFT ),  
	H_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/h/h_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.H_BOTTOM_RIGHT),
	H_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/h/h_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.H_BOTTOM_LEFT),
	
	I_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/i/i_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.I_UPPER_RIGHT),
	I_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/i/i_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.I_UPPER_LEFT ),  
	I_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/i/i_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.I_BOTTOM_RIGHT),
	I_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/i/i_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.I_BOTTOM_LEFT),
	
	J_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/j/j_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.J_UPPER_RIGHT),
	J_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/j/j_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.J_UPPER_LEFT ),  
	J_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/j/j_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.J_BOTTOM_RIGHT),
	J_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/j/j_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.J_BOTTOM_LEFT),
	
	K_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/k/k_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.K_UPPER_RIGHT),
	K_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/k/k_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.K_UPPER_LEFT ),  
	K_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/k/k_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.K_BOTTOM_RIGHT),
	K_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/k/k_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.K_BOTTOM_LEFT),
	
	L_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/l/l_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.L_UPPER_RIGHT),
	L_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/l/l_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.L_UPPER_LEFT ),  
	L_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/l/l_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.L_BOTTOM_RIGHT),
	L_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/l/l_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.L_BOTTOM_LEFT),
	
	M_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/m/m_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.M_UPPER_RIGHT),
	M_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/m/m_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.M_UPPER_LEFT ),  
	M_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/m/m_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.M_BOTTOM_RIGHT),
	M_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/m/m_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.M_BOTTOM_LEFT),
	
	N_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/n/n_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.N_UPPER_RIGHT),
	N_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/n/n_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.N_UPPER_LEFT ),  
	N_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/n/n_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.N_BOTTOM_RIGHT),
	N_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/n/n_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.N_BOTTOM_LEFT),
	
	O_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/o/o_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.O_UPPER_RIGHT),
	O_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/o/o_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.O_UPPER_LEFT ),  
	O_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/o/o_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.O_BOTTOM_RIGHT),
	O_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/o/o_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.O_BOTTOM_LEFT),
	
	P_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/p/p_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.P_UPPER_RIGHT),
	P_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/p/p_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.P_UPPER_LEFT ),  
	P_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/p/p_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.P_BOTTOM_RIGHT),
	P_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/p/p_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.P_BOTTOM_LEFT),
	
	Q_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/q/q_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.Q_UPPER_RIGHT),
	Q_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/q/q_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.Q_UPPER_LEFT ),  
	Q_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/q/q_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.Q_BOTTOM_RIGHT),
	Q_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/q/q_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.Q_BOTTOM_LEFT),

	R_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/r/r_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.R_UPPER_RIGHT),
	R_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/r/r_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.R_UPPER_LEFT ),  
	R_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/r/r_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.R_BOTTOM_RIGHT),
	R_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/r/r_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.R_BOTTOM_LEFT),

	S_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/s/s_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.S_UPPER_RIGHT),
	S_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/s/s_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.S_UPPER_LEFT ),  
	S_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/s/s_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.S_BOTTOM_RIGHT),
	S_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/s/s_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.S_BOTTOM_LEFT),

	T_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/t/t_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.T_UPPER_RIGHT),
	T_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/t/t_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.T_UPPER_LEFT ),  
	T_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/t/t_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.T_BOTTOM_RIGHT),
	T_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/t/t_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.T_BOTTOM_LEFT),

	U_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/u/u_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.U_UPPER_RIGHT),
	U_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/u/u_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.U_UPPER_LEFT ),  
	U_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/u/u_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.U_BOTTOM_RIGHT),
	U_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/u/u_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.U_BOTTOM_LEFT),

	W_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/w/w_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.W_UPPER_RIGHT),
	W_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/w/w_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.W_UPPER_LEFT ),  
	W_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/w/w_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.W_BOTTOM_RIGHT),
	W_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/w/w_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.W_BOTTOM_LEFT),
	
	X_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/x/x_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.X_UPPER_RIGHT),
	X_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/x/x_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.X_UPPER_LEFT ),  
	X_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/x/x_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.X_BOTTOM_RIGHT),
	X_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/x/x_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.X_BOTTOM_LEFT),

	Y_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/a/a_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.Y_UPPER_RIGHT),
	Y_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/a/a_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.Y_UPPER_LEFT ),  
	Y_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/a/a_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.Y_BOTTOM_RIGHT),
	Y_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/a/a_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.Y_BOTTOM_LEFT),

	Z_UPPER_RIGHT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/z/z_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.Z_UPPER_RIGHT),
	Z_UPPER_LEFT  (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/z/z_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.Z_UPPER_LEFT ),  
	Z_BOTTOM_RIGHT(ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/z/z_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.Z_BOTTOM_RIGHT),
	Z_BOTTOM_LEFT (ImageUtils.loadImage("/resources/FurnitureLoots/PuzzlePieces/z/z_01.png"),  Type.FURNITURE_LOOT, Furniture.LootType.PUZZLE_PIECE, PuzzlePiece.Z_BOTTOM_LEFT);

	//BACKGROUND(ImageUtils.loadImage("/resources/Background.png"), Type.BACKGROUND);
	
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
	
	private static final Map<PuzzlePiece, StaticImage> puzzlePieces = 
			Arrays.stream(StaticImage.values()).filter(i -> i.type == Type.FURNITURE_LOOT && i.furnitureLootType == Furniture.LootType.PUZZLE_PIECE).collect(Collectors.toMap(i -> i.puzzlePieceType, i -> i));
	
	private BufferedImage image;
	private Room.Color color;
	private Type type;
	private Furniture.Type furnitureType;
	private Furniture.LootType furnitureLootType;
	private PuzzlePiece puzzlePieceType;
	
	public enum Type 
	{ 
		TERMINAL, MIDDLE_WALL_RIGHT, MIDDLE_WALL_LEFT, UPPER_LEFT_WALL, 
		UPPER_RIGHT_WALL, BOTTOM_LEFT_WALL, BOTTOM_RIGHT_WALL, FURNITURE, FLOOR, PLATFORM,
		BACKGROUND, FURNITURE_LOOT
	}
		
	private StaticImage(BufferedImage image, Room.Color color, Type type, Furniture.Type furnitureType, Furniture.LootType furnitureLootType, PuzzlePiece puzzlePieceType)
	{
		this.image = image; 
		this.color = color;
		this.type = type;
		this.furnitureType = furnitureType;
		this.furnitureLootType = furnitureLootType;
		this.puzzlePieceType = puzzlePieceType;
	}
	
	private StaticImage(BufferedImage image, Type type, Furniture.LootType furnitureLootType, PuzzlePiece puzzlePieceType)
	{ this(image, null, type, null, furnitureLootType, puzzlePieceType); }
	
	private StaticImage(BufferedImage image, Room.Color color, Type type, Furniture.Type furnitureType)
	{ this(image, color, type, furnitureType, null, null); }
	
	private StaticImage(BufferedImage image, Type type, Furniture.LootType furnitureLootType)
	{ this(image, null, type, null, furnitureLootType, null); }
	
	private StaticImage(BufferedImage image, Type type)
	{ this(image, type, null); }
	
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
	
	public static StaticImage getPuzzlePiece(PuzzlePiece puzzlePiece)
	{ return puzzlePieces.get(puzzlePiece); }
	
	public static StaticImage getFurnitureLoot(Furniture.LootType furnitureLootType)
	{
		return switch(furnitureLootType) {
			case Furniture.LootType.EMPTY		      -> EMPTY_LOOT;
			case Furniture.LootType.ROBOT_PASSWORD    -> ROBOT_PASSWORD_LOOT;
			case Furniture.LootType.PLATFORM_PASSWORD -> PLATFORM_PASSWORD_LOOT;
			default -> throw new IllegalArgumentException("This method does not provide sprites for puzzle pieces");
		};
	}
}
