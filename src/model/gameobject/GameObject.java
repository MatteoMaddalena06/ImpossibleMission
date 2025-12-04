package model.gameobject;

//IO modules
import java.io.Serializable;

public abstract class GameObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	protected Point position;
	protected int width, height;
	
	public GameObject(Point position, int width, int height)
	{	
		this.position = position;
		this.width = width;
		this.height = height;
	}
	
	protected boolean isColliding(GameObject other)
	{		
		int x1 = position.getX(), y1 = position.getY();
		int w1 = width, h1 = height;
		
		int x2 = other.position.getX(), y2 = other.position.getY();
		int w2 = other.width, h2 = other.height;
		
		boolean firstCheck  = x1 < x2 + w2 && y1 < y2 + h2;
		boolean secondCheck = x2 < x1 + w1 && y2 < y1 + h1;
		
		return firstCheck && secondCheck;
	}

	public abstract void update(GameContext context);
	
	public Point getPosition()
	{ return new Point(position); }
	
	public int getWidth()
	{ return width; }
	
	public int getHeight()
	{ return height; }
}
