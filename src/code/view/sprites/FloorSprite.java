package code.view.sprites;

//graphics import
import java.awt.image.BufferedImage;

import code.model.gameobjects.FixedObject;
import code.model.room.Room;
import code.view.images.ImageUtils;
import code.view.images.StaticImage;

/** Classe per la sprite di un paviemento */
public class FloorSprite extends Sprite
{
	/** L'immagine del pezzo di pavimento usato per la generazione dell'intera sprite */
	private BufferedImage pieceOfFloorImage;
	
	/**
	 * Costruisce la classe
	 * @param floor
	 * il pavimento a cui associare la sprite
	 * @param floorColor
	 * il colore del pavimento
	 */
	public FloorSprite(FixedObject floor, Room.Color floorColor)
	{ 
		super(floor); 
		pieceOfFloorImage = StaticImage.getFloor(floorColor).getImage();
		computeImage();
	}
	
	/** 
	 * Calcola e imposta l'immagine della sprite affiancando orizzontalmente più {@link pieceOfFloorImage}
	 * @see code.view.images.ImageUtils#imageStrip(BufferedImage, int, code.view.images.ImageUtils.Direction)
	 */
	@Override
	public void computeImage()
	{ setImage(ImageUtils.imageStrip(pieceOfFloorImage, getGameObject().getWidth() / pieceOfFloorImage.getWidth(), ImageUtils.Direction.HORIZONTAL)); }
}
