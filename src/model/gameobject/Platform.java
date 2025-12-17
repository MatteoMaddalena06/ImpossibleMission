package model.gameobject;

//inproejct import
import model.utils.GameContext;
import model.utils.Point;

public class Platform extends MovingObject
{
	private Point originalPosition;
	
	public Platform(Point position, int width, int height)
	{ 
		super(position, width, height);
		originalPosition = position;
	}
	
	@Override
	public void update(GameContext context)
	{
		if(context.getPlatformsToReset() == 0)
			return;
		
		setPosition(originalPosition);
		context.resetOnePlatform();		
	}
}
