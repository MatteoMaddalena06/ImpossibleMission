package code.view.sprites;

//data structure import
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
//graphics import
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
//model import
import code.model.gameobject.Furniture;
import code.model.room.Room;
import code.view.images.StaticImage;

public class FurnitureSprite extends Sprite
{	
	private static final double OVERFLOW_PENALITY = 3f;
	
	private BufferedImage chosenFurnitureImage;
	
	public FurnitureSprite(Furniture furniture, Room.Color color)
	{ 
		super(furniture); 
		
		if(furniture.getType() == Furniture.Type.RANDOM)
			chosenFurnitureImage = choseBestImage(furniture, StaticImage.getFurnitures(color));
		
		else 
			chosenFurnitureImage = StaticImage.getFurniture(furniture.getType(), color).getImage();
		
		setImage(computeImage());
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
	public BufferedImage computeImage()
	{
		Furniture bindedFurniture = (Furniture)getGameObject();
		int furnitureWidth = bindedFurniture.getWidth();
		int furnitureHeight = bindedFurniture.getHeight();
		int furnitureImageWidth = chosenFurnitureImage.getWidth();
		int furnitureImageHeight = chosenFurnitureImage.getHeight();
		
		int widthToAdd = Math.max(0, furnitureImageWidth - furnitureWidth);
		int heightToAdd = Math.max(0, furnitureImageHeight - furnitureHeight);
		
		BufferedImage finalImage = new BufferedImage(furnitureWidth + widthToAdd, furnitureHeight + heightToAdd, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = finalImage.createGraphics();
		
		graphics.drawImage(chosenFurnitureImage, 0, furnitureHeight - furnitureImageHeight, null);
		
		graphics.dispose();
		return finalImage;
	}
}
