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

/** Classe per la sprite di un mobile */
public class FurnitureSprite extends Sprite
{	
	/** 
	 * Metrica usata dall'algoritmo per la scelta dell'immagine che meglio entra in un gameobject di un mobile 
	 * @see choseBestImage
	 */
	private static final double OVERFLOW_PENALITY = 2f;
	
	/** L'immagine della sprite */
	private BufferedImage chosenFurnitureImage;
	
	/**
	 * Costruice la classe 
	 * @param furniture
	 * il mobile associato alla sprite
	 * @param color
	 * il colore del mobile
	 */
	public FurnitureSprite(Furniture furniture, Room.Color color)
	{ 
		super(furniture); 
		
		if(furniture.getType() == Furniture.Type.RANDOM)
			chosenFurnitureImage = choseBestImage(furniture, StaticImage.getFurnitures(color));
		
		else 
			chosenFurnitureImage = StaticImage.getFurniture(furniture.getType(), color).getImage();
		
		computeImage();
	}
	
	/**
	 * Sceglie fra tutte le possibile immagini per un mobile quella che meglio entra nella sua hitbox
	 * @param furniture
	 * il mobile in questione
	 * @param furnitureImages
	 * la lista delle immagine possibili per un mobile
	 * @return
	 * l'immagine scelta
	 * @see cost
	 */
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
	
	/**
	 * Calcola il costo (cioè quanto bene l'immagine entra nella hitbox) per un'immagine e un mobile
	 * @param furnitureWidth
	 * la larghezza del mobile
	 * @param furnitureHeight
	 * l'altezza del mobile
	 * @param imageWidth
	 * la larghezza dell'immagine
	 * @param imageHeight
	 * l'altezza dell'immagine
	 * @return
	 * il costo
	 */
	private double cost(double furnitureWidth, double furnitureHeight, double imageWidth, double imageHeight)
	{
		double horizontalError = 
				(imageWidth <= furnitureWidth) ? 1 - imageWidth / furnitureWidth : OVERFLOW_PENALITY * (imageWidth / furnitureWidth - 1);
		double verticalError = 
				(imageHeight <= furnitureHeight) ? 1 - imageHeight / furnitureHeight : OVERFLOW_PENALITY * (imageHeight / furnitureHeight - 1);
		
		return horizontalError * horizontalError + verticalError * verticalError;
	}
	
	/** 
	 * Clacola e imposta l'immaggine della sprite 
	 * @see setImage
	 */
	@Override
	public void computeImage()
	{ setImage(chosenFurnitureImage); }
}
