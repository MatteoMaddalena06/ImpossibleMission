package code.view.sprites;

//data structures import
import java.util.List;
//graphics import
import java.awt.image.BufferedImage;
//model import
import code.model.gameobjects.MovingObject;
//view import
import code.view.images.Animation;

public abstract class AnimatedSprite extends Sprite 
{
	 private Animation animation;
	 private Animation.State previousState;
	 private double imageDuration;
	 private double elapsedTime;
	 private int imageIndex;
	 
	 public AnimatedSprite(MovingObject gameObject, Animation animation, double imageDuration)
	 {
		 super(gameObject);
		 this.animation = animation;
		 this.imageDuration = imageDuration;
	 }
	 
	 @Override 
	 public void computeImage()
	 {
		 MovingObject bindedMovingObject = (MovingObject)getGameObject();
		 Animation.State currentState = Animation.State.getState(bindedMovingObject);
		 
		 if(previousState != currentState && !currentState.isMirrored(previousState))
			 imageIndex = 0;
		 
		 previousState = currentState;
		 
		 List<BufferedImage> animationList = animation.getAnimationLists().get(currentState);
		 
		 while(elapsedTime >= imageDuration)
		 {
			 elapsedTime -= imageDuration;
			 imageIndex = nextImageIndex(animationList);
		 }
		 
		 setImage(animationList.get(imageIndex));
	 }
	 
	 protected int nextImageIndex(List<BufferedImage> animationList)
	 { return (imageIndex + 1) % animationList.size(); }

	 public void updateElapsedTime(double deltaTime)
	 { elapsedTime += deltaTime; }
	 
	 protected int getImageIndex()
	 { return imageIndex; }
}
