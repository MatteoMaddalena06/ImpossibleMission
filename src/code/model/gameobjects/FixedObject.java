package code.model.gameobjects;

import code.model.Point;
import code.model.context.GameContext;

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
    
    public Type getType()
    { return type; }

    @Override
    public void update(GameContext context)
    { /*do nothing*/}
}
