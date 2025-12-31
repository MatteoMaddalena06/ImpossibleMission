package code.view.sprites;

//data structures import
import java.util.List;
//graphics
import java.awt.image.BufferedImage;
//model import
import code.model.gameobjects.Furniture;
//images import
import code.view.images.Animation;

public abstract class SearchingWindow
{
	private static final Animation SEARCHING_WINDOW_ANIMATION = Animation.SEARCHING_WINDOW;
	private static final int MAX_PROGRESSION_BLOCK = 11;
	
	public static BufferedImage getSearchingWindow(Furniture furniture)
	{ 
		List<BufferedImage> windowsSearchingList = SEARCHING_WINDOW_ANIMATION.getAnimationLists().get(Animation.State.SEARCHING); 
		int maxProgressionBlock = MAX_PROGRESSION_BLOCK;
		double blockValue = furniture.getTimeForSearch() / maxProgressionBlock;
		return windowsSearchingList.get(Math.min(maxProgressionBlock - 1, (int)(maxProgressionBlock - furniture.getRemainingTimeForSearch() / blockValue)));
	}
}
