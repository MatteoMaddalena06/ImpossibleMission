package code.view.sprites;

//graphics import
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import code.model.gameobjects.Platform;
import code.view.images.ImageUtils;
import code.view.images.StaticImage;

public class PlatformSprite extends Sprite
{
	private static final BufferedImage platformStartImage = StaticImage.PLATFORM_START.getImage();
	private static final BufferedImage platformMidImage   = StaticImage.PLATFORM_MID.getImage();
	private static final BufferedImage platformEndImage   = StaticImage.PLATFORM_END.getImage();
	
	private static final int SPRITE_WIDTH  = platformStartImage.getWidth();
	
	public PlatformSprite(Platform platform)
	{ 
		super(platform); 
		setImage(computeImage());
	}
	
	@Override
	public BufferedImage computeImage()
	{
		Platform bindedPlatform = (Platform)getGameObject();
		int bindedPlatformWidth = bindedPlatform.getWidth();
		int bindedPlatformHeight = bindedPlatform.getHeight();
		int numberOfImages = bindedPlatformWidth / SPRITE_WIDTH;
		
		BufferedImage resultImage = new BufferedImage(bindedPlatformWidth, bindedPlatformHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = resultImage.createGraphics();
		
		graphics.drawImage(platformStartImage, 0, 0, null);
		
		if(numberOfImages - 2 > 0)
			graphics.drawImage(ImageUtils.imageStrip(platformMidImage, numberOfImages - 2, ImageUtils.Direction.HORIZONTAL), SPRITE_WIDTH, 0, null);
		
		graphics.drawImage(platformEndImage, bindedPlatformWidth - SPRITE_WIDTH, 0, null);
		
		graphics.dispose();
		return resultImage;
	}
}
