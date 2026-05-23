package code.view.sprites;

//graphics import
import java.awt.image.BufferedImage;

import code.model.gameobjects.Terminal;
//image import
import code.view.images.StaticImage;

/** Classe per la sprite del terminake */
public class TerminalSprite extends Sprite
{
	/** Immagine della sprite */
	private static final BufferedImage TERMINAL_IMAGE = StaticImage.TERMINAL.getImage();
	
	/**
	 * Costruisce la classe
	 * @param terminal
	 * il terminale a cui associare la sprite
	 */
	public TerminalSprite(Terminal terminal)
	{
		super(terminal); 
		computeImage();
	}
	
	/** Imposta l'immagine della sprite con {@link TERMINAL_IMAGE} */
	@Override
	public void computeImage()
	{ setImage(TERMINAL_IMAGE); }
}
