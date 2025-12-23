package code.view.sprites;

//graphics import
import java.awt.image.BufferedImage;

import code.model.gameobject.FixedObject;
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
		setImage(computeImage());
	}
	
	@Override
	public BufferedImage computeImage()
	{ return ImageUtils.imageStrip(pieceOfFloorImage, getGameObject().getWidth() / pieceOfFloorImage.getWidth(), ImageUtils.Direction.HORIZONTAL); }
}
