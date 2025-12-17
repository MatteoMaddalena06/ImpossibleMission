package model.gameobject;

//IO modules
import java.io.Serializable;

import model.utils.GameContext;
import model.utils.Point;

public abstract class GameObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Point position;
	private int width, height;
	
	public GameObject(Point position, int width, int height)
	{	
		this.position = position;
		this.width = width;
		this.height = height;
	}
	
	public boolean isColliding(GameObject other)
	{		
		int x1 = position.getX(), y1 = position.getY();
		int w1 = width, h1 = height;
		
		int x2 = other.position.getX(), y2 = other.position.getY();
		int w2 = other.width, h2 = other.height;
		
		boolean firstCheck  = x1 < x2 + w2 && y1 < y2 + h2;
		boolean secondCheck = x2 < x1 + w1 && y2 < y1 + h1;
		
		return firstCheck && secondCheck;
	}
	
	public boolean containsPoint(Point point)
	{ 
		int fx = getPosition().getX(), fy = getPosition().getY();
		int fw = getWidth(), fh = getHeight();
		
		int px = point.getX(), py = point.getY();
		
		return px > fx && px < fx + fw && py > fy && py < fy + fh;
	}

	public abstract void update(GameContext context);
	
	protected Point getPosition()
	{ return position; }
	
	protected void setPosition(Point position)
	{ this.position = position; }
	
	public Point copyPosition()
	{ return new Point(position); }
	
	public int getWidth()
	{ return width; }
	
	public int getHeight()
	{ return height; }
	
	protected void setWidth(int width)
	{ this.width = width; }
	
	protected void setHeight(int height)
	{ this.height = height; }
}
