package code.model.gameobjects;

import code.model.Point;
import code.model.context.GameContext;

public class FixedObject extends GameObject
{
	private Type type;
	
	public enum Type 
	{ WALL, FLOOR }
	
    public FixedObject(Type type, Point point, int width, int height)
    {
        super(point, width, height);
        this.type = type;
    }
    
    public FixedObject(FixedObject source, Point position)
    { this(source.type, position, source.getWidth(), source.getHeight()); }
    
    public Type getType()
    { return type; }

    @Override
    public void update(GameContext context)
    { /*do nothing*/}
}
