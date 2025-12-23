package code.view.sprites;

//data structure import
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
//graphics import
import java.awt.image.BufferedImage;

import code.model.gameobjects.Furniture;
import code.model.room.Room;
import code.view.images.StaticImage;

public class FurnitureSprite extends Sprite
{	
	private static final double OVERFLOW_PENALITY = 2f;
	
	private BufferedImage chosenFurnitureImage;
	
	public FurnitureSprite(Furniture furniture, Room.Color color)
	{ 
		super(furniture); 
		
		if(furniture.getType() == Furniture.Type.RANDOM)
			chosenFurnitureImage = choseBestImage(furniture, StaticImage.getFurnitures(color));
		
		else 
			chosenFurnitureImage = StaticImage.getFurniture(furniture.getType(), color).getImage();
		
		computeImage();
	}
	
	private BufferedImage choseBestImage(Furniture furniture, StaticImage[] furnitureImages)
	{
		int furnitureWidth = furniture.getWidth(), furnitureHeight = furniture.getHeight();
		
		List<StaticImage> furnitureImagesList = Arrays.asList(furnitureImages);
		Collections.shuffle(furnitureImagesList);
		
		return furnitureImagesList.stream().min(Comparator.comparingDouble(i -> {
			BufferedImage image = i.getImage();
			int imageWidth = image.getWidth(), imageHeight = image.getHeight();
			
			return cost(furnitureWidth, furnitureHeight, imageWidth, imageHeight);
		})).get().getImage();
	}
	
	private double cost(double furnitureWidth, double furnitureHeight, double imageWidth, double imageHeight)
	{
		double horizontalError = 
				(imageWidth <= furnitureWidth) ? 1 - imageWidth / furnitureWidth : OVERFLOW_PENALITY * (imageWidth / furnitureWidth - 1);
		double verticalError = 
				(imageHeight <= furnitureHeight) ? 1 - imageHeight / furnitureHeight : OVERFLOW_PENALITY * (imageHeight / furnitureHeight - 1);
		
		return horizontalError * horizontalError + verticalError * verticalError;
	}
	
	@Override
	public void computeImage()
	{ setImage(chosenFurnitureImage); }
}
