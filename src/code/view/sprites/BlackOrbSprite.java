package code.view.sprites;

//model import
import code.model.gameobjects.enemy.BlackOrb;
//images import
import code.view.images.Animation;

/** Classe per la sprite del bombone */
public class BlackOrbSprite extends AnimatedSprite
{
	/** L'animazione del bombone */
	private static final Animation BLACK_ORB_ANIMATION = Animation.BLACKORB;
	/** La durata dei frame */
	private static final double    IMAGE_DURATION   = 0.12f; 
	
	/**
	 * Costruice la classe
	 * @param blackOrb
	 * il bombone a cui associare la sprite
	 */
	public BlackOrbSprite(BlackOrb blackOrb)
	{ super(blackOrb, BLACK_ORB_ANIMATION, IMAGE_DURATION); }
}