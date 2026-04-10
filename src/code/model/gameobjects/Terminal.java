package code.model.gameobjects;

//model import
import code.model.Point;
import code.model.context.GameContext;
import code.model.context.PlayerOpenTerminal;
//event import
import code.event.EventDispatcher;

public class Terminal extends GameObject
{
	public Terminal(Point position, int width, int height)
	{ super(position, width, height); }
	
	@Override
	public void update(GameContext context) 
	{
		Player player = context.getPlayer();
		
		if(context.getUserInput(GameContext.UserInput.UP) && isColliding(player))
			EventDispatcher.notify(new PlayerOpenTerminal());
	}
}
