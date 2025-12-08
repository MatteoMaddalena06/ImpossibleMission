package model.gameobject;

import model.utils.GameContext;
import model.utils.Point;

public class FixedObject extends GameObject
{
	private static final long serialVersionUID = 1L;
	private Type type;
	
	public enum Type 
	{ WALL, FLOOR }
	
    public FixedObject(Type type, Point point, int w, int h)
    {
        super(point, w, h);
        this.type = type;
    }
    
	public boolean containsPoint(Point point)
	{ 
		int fx = getPosition().getX(), fy = getPosition().getY();
		int fw = getWidth(), fh = getHeight();
		
		int px = point.getX(), py = point.getY();
		
		return px > fx && px < fx + fw && py > fy && py < fy + fh;
	}
    
    public Type getType()
    { return type; }

    @Override
    public void update(GameContext context)
    { /*do nothing*/}
}
