package code.view.menu;

//graphics import
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class MenuButton extends JButton
{
	private static final String normalIconID   = "normalIcon";
	private static final String selectedIconID = "selectedIcon";
	
	public MenuButton(BufferedImage normalImage, BufferedImage selectionImage)
	{
		ImageIcon normalImageIcon = new ImageIcon(normalImage);
		ImageIcon selectionImageIcon = new ImageIcon(selectionImage);
		
		this.setIcon(normalImageIcon);
		this.setPreferredSize(new Dimension(normalImage.getWidth(), normalImage.getHeight()));
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);
		
		this.putClientProperty(normalIconID, normalImageIcon);
		this.putClientProperty(selectedIconID, selectionImageIcon);
		
		MouseAdapter buttonListener = new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) 
		    {
		    	JButton button = (JButton)e.getSource();
		    	button.setIcon((Icon)button.getClientProperty(selectedIconID));
		    }
		
		    @Override
		    public void mouseExited(MouseEvent e) 
		    {
		    	JButton button = (JButton)e.getSource();
		    	button.setIcon((Icon)button.getClientProperty(normalIconID));
		    }
		    
		    @Override
		    public void mousePressed(MouseEvent e) 
		    {
		    	JButton button = (JButton)e.getSource();
		    	button.setBorder(BorderFactory.createEmptyBorder(3, 3, 0, 0));
		    }

		    @Override
		    public void mouseReleased(MouseEvent e) 
		    {
		    	JButton button = (JButton)e.getSource();
		    	button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		    }
		};
		
		this.addMouseListener(buttonListener);
	}
}
