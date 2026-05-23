package code.view.sprites;

//data structures import
import java.util.List;
//graphics
import java.awt.image.BufferedImage;
//model import
import code.model.gameobjects.Furniture;
//images import
import code.view.images.Animation;

/** Classe per la sprite della finestra che indica il progresso della ricerca in un mobile */
public abstract class SearchingWindow
{
	/** Animazione della sprite */
	private static final Animation SEARCHING_WINDOW_ANIMATION = Animation.SEARCHING_WINDOW;
	/** Numero massimo di blocchi di progressione */
	private static final int MAX_PROGRESSION_BLOCK = 11;
	
	/**
	 * Restiuisce l'immagine con il numero giusto di blochi di progressione
	 * @param furniture
	 * il mobile in questione
	 * @return
	 * l'immagine della finestra
	 */
	public static BufferedImage getSearchingWindow(Furniture furniture)
	{ 
		List<BufferedImage> windowsSearchingList = SEARCHING_WINDOW_ANIMATION.getAnimationLists().get(Animation.State.SEARCHING); 
		int maxProgressionBlock = MAX_PROGRESSION_BLOCK;
		double blockValue = furniture.getTimeForSearch() / maxProgressionBlock;
		return windowsSearchingList.get(Math.min(maxProgressionBlock - 1, (int)(maxProgressionBlock - furniture.getRemainingTimeForSearch() / blockValue)));
	}
}
