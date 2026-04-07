package code.view.sprites;

//data structures import
import java.util.List;
//graphics import
import java.awt.image.BufferedImage;
//model import
import code.model.gameobjects.Player;
import code.model.gameobjects.MovingObject;
//images import
import code.view.images.Animation;

public class PlayerSprite extends AnimatedSprite
{
	private static final Animation PLAYER_ANIMATION = Animation.PLAYER;
	private static final double    IMAGE_DURATION   = 0.12f;
	
	public PlayerSprite(Player player)
	{ super(player, PLAYER_ANIMATION, IMAGE_DURATION); }

	@Override
	protected int nextImageIndex(List<BufferedImage> animationList)
	{
		Player bindedPlayer = (Player)getGameObject();
		MovingObject.PhysicsState state = bindedPlayer.getPhysicsState();
		
		int animationSize = animationList.size();
		int nextIndex = getImageIndex() + 1;
		
		if((state == MovingObject.PhysicsState.JUMPING || bindedPlayer.isSearching() || bindedPlayer.isDead()) && nextIndex == animationSize)
			nextIndex--;
		
		else if(state == MovingObject.PhysicsState.FALLING && nextIndex == animationSize)
			nextIndex -= 2;
		
		else
			nextIndex %= animationSize;
		
		return nextIndex;
	}
}
