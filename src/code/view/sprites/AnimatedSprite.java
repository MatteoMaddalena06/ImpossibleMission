package code.view.sprites;

//model import
import code.model.gameobject.GameObject;
import code.view.images.Animation;

public abstract class AnimatedSprite extends Sprite 
{
	 private Animation animation;
	 private Animation.State state;
	 
	 private long spriteDuration;
	 private long elapsedTime;
	 private int spriteIndex;
	 
	 public AnimatedSprite(GameObject gameObject, Animation animation, long spriteDuration)
	 {
		 super(gameObject);
		 this.animation = animation;
		 this.spriteDuration = spriteDuration;
		 elapsedTime = spriteIndex = 0;
	 }
	  
	 protected void setState(Animation.State state)
	 { this.state = state; }
	 
	 protected long getSpriteDuration()
	 { return spriteDuration; }
	 
	 protected long getElapsedTime()
	 { return elapsedTime; }
	 
	 protected void updateElapsedTime(long deltaTime)
	 { elapsedTime += deltaTime; }
	 
	 protected int getSpriteIndex()
	 { return spriteIndex; }
	 
	 protected void setSpriteIndex(int spriteIndex)
	 { this.spriteIndex = spriteIndex; }
}
