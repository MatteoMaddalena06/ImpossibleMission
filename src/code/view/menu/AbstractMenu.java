package code.view.menu;

//graphics import
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
//view import
import code.view.images.StaticImage;

public abstract class AbstractMenu extends JPanel
{
	private static final BufferedImage backgroundImage = StaticImage.MENU_BACKGROUND.getImage();
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	    g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	protected abstract void buildMenu();
}
