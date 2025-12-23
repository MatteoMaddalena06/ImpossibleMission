package code.view.sprites;

//graphics import
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import code.model.gameobjects.FixedObject;
import code.model.room.Room;
import code.model.utils.Point;
import code.view.images.ImageUtils;
import code.view.images.StaticImage;

public class WallSprite extends Sprite
{
	private BufferedImage rightMiddleWallImage;
	private BufferedImage leftMiddleWallImage;
	private BufferedImage wallUpperLeftImage;
	private BufferedImage wallUpperRightImage;
	private BufferedImage wallBottomLeftImage;
	private BufferedImage wallBottomRightImage;;
	
	public WallSprite(FixedObject wall, Room.Color wallColor)
	{ 
		super(wall); 
		
		rightMiddleWallImage = StaticImage.getWall(wallColor, StaticImage.Type.MIDDLE_WALL_RIGHT).getImage();
		leftMiddleWallImage = StaticImage.getWall(wallColor, StaticImage.Type.MIDDLE_WALL_LEFT).getImage();
		wallUpperLeftImage = StaticImage.getWall(wallColor, StaticImage.Type.UPPER_LEFT_WALL).getImage();
		wallUpperRightImage = StaticImage.getWall(wallColor, StaticImage.Type.UPPER_RIGHT_WALL).getImage();
		wallBottomLeftImage = StaticImage.getWall(wallColor, StaticImage.Type.BOTTOM_LEFT_WALL).getImage();
		wallBottomRightImage = StaticImage.getWall(wallColor, StaticImage.Type.BOTTOM_RIGHT_WALL).getImage();

		computeImage();
	}
	
	@Override
	public void computeImage()
	{ 
		FixedObject bindedWall = (FixedObject)getGameObject();
		Point bindedWallPosition = bindedWall.copyPosition();
		int bindedWallX = (int)bindedWallPosition.getX();
		int bindedWallWidth = bindedWall.getWidth();
		int bindedWallHeight = bindedWall.getHeight();
		int wallImageHeight = rightMiddleWallImage.getHeight();
		int numberOfImages = bindedWallHeight / wallImageHeight;
		
		BufferedImage resultImage = new BufferedImage(bindedWallWidth, bindedWallHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = resultImage.createGraphics();

		graphics.drawImage(ImageUtils.imageStrip((bindedWallX == 0) ? rightMiddleWallImage : leftMiddleWallImage, numberOfImages, ImageUtils.Direction.VERTICAL), 0, 0, null);
		
		if(numberOfImages > 1)
		{
			graphics.drawImage((bindedWallX == 0) ? wallUpperLeftImage : wallUpperRightImage, 0, 0, null);
			graphics.drawImage((bindedWallX == 0) ? wallBottomLeftImage : wallBottomRightImage, 0, bindedWallHeight -  wallImageHeight, null);
		}
		
		graphics.dispose();
		setImage(resultImage);
	}
}
