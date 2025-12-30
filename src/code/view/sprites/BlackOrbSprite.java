package code.view.sprites;

//model import
import code.model.gameobjects.enemy.BlackOrb;
//images import
import code.view.images.Animation;

public class BlackOrbSprite extends AnimatedSprite
{
	private static final Animation BLACK_ORB_ANIMATION = Animation.BLACKORB;
	private static final double    IMAGE_DURATION   = 0.12f; 
	
	public BlackOrbSprite(BlackOrb blackOrb)
	{ super(blackOrb, BLACK_ORB_ANIMATION, IMAGE_DURATION); }
}