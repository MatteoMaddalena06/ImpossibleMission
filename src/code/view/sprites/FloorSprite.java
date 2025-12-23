package code.view.sprites;

//graphics import
import java.awt.image.BufferedImage;

import code.model.gameobjects.FixedObject;
import code.model.room.Room;
import code.view.images.ImageUtils;
import code.view.images.StaticImage;

public class FloorSprite extends Sprite
{
	private BufferedImage pieceOfFloorImage;
	
	public FloorSprite(FixedObject floor, Room.Color floorColor)
	{ 
		super(floor); 
		pieceOfFloorImage = StaticImage.getFloor(floorColor).getImage();
		computeImage();
	}
	
	@Override
	public void computeImage()
	{ setImage(ImageUtils.imageStrip(pieceOfFloorImage, getGameObject().getWidth() / pieceOfFloorImage.getWidth(), ImageUtils.Direction.HORIZONTAL)); }
}
