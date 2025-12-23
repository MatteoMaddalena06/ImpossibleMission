package code.view.sprites;

//graphics import
import java.awt.image.BufferedImage;
//model import
import code.model.gameobject.Terminal;
//image import
import code.view.images.StaticImage;

public class TerminalSprite extends Sprite
{
	private static final BufferedImage TERMINAL_IMAGE = StaticImage.TERMINAL.getImage();
	
	public TerminalSprite(Terminal terminal)
	{
		super(terminal); 
		setImage(computeImage());
	}
	
	@Override
	public BufferedImage computeImage()
	{ return TERMINAL_IMAGE; }
}
