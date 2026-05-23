package code.view.sprites;

//graphics import
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import code.model.gameobjects.Platform;
import code.view.images.ImageUtils;
import code.view.images.StaticImage;

/** Classe per la sprite delle piattaforme */
public class PlatformSprite extends Sprite
{
	/** Immagine del primo segmento di piattaforma */
	private static final BufferedImage platformStartImage = StaticImage.PLATFORM_START.getImage();
	/** Immagine dei segmenti intermedi di piattaforma */
	private static final BufferedImage platformMidImage   = StaticImage.PLATFORM_MID.getImage();
	/** Immagine per l'ultimo segmento di piattaforma */
	private static final BufferedImage platformEndImage   = StaticImage.PLATFORM_END.getImage();
	
	/** Larghezza del segmento di piattaforma */
	private static final int SPRITE_WIDTH  = platformStartImage.getWidth();
	
	/**
	 * Costruice la classe
	 * @param platform
	 * la piattaforma a cui associare la sprite
	 */
	public PlatformSprite(Platform platform)
	{ 
		super(platform); 
		computeImage();
	}
	
	/**
	 * Calcola e imposta l'immagine per la sprite concatenando i segmenti di piattaforma
	 * @see code.view.images.ImageUtils#imageStrip(BufferedImage, int, code.view.images.ImageUtils.Direction)
	 * @see platformStartImage
	 * @see platformMidImage
	 * @see platformEndImage
	 * @see code.view.images.ImageUtils#imageStrip(BufferedImage, int, code.view.images.ImageUtils.Direction)
	 */
	@Override
	public void computeImage()
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
		setImage(resultImage);
	}
}
