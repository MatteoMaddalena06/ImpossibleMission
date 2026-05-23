package code.view.images;

//data structure import
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.IntStream;
import java.util.ArrayList;
//graphics import
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Image;
//IO import
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/** Classe di utilità generale per la manipolazione delle immagine */
public abstract class ImageUtils 
{
	/** Cache per il caricamento delle immagine dal disco */
	private static final Map<String, BufferedImage> IMAGE_CACHE = new HashMap<String, BufferedImage>();
	
	/** 
	 * Enumerazione per le direzione possibili in cui fare lo strip 
	 * @see imageStrip
	 */
	public enum Direction
	{ VERTICAL, HORIZONTAL }

	/** 
	 * Carica un'immagine dal disco
	 * @param pathname
	 * il percorso dell'immagine
	 * @param crop
	 * true se e solo se bisogna fare il crop
	 * @return
	 * l'immagine 
	 */
	public static BufferedImage loadImage(String pathname, boolean crop)
	{ return IMAGE_CACHE.computeIfAbsent(pathname, f -> (crop) ? crop(loadRaw(pathname)) : loadRaw(pathname)); }
	
	/** 
	 * Carica un'immagine dal disco e la specchia
	 * @param pathname
	 * il percorso dell'immagine
	 * @param crop
	 * true se e solo se bisogna fare il crop
	 * @return
	 * l'immagine 
	 * @see flipHorizontally
	 */
	public static BufferedImage loadFlipped(String pathname, boolean crop)
	{ return flipHorizontally(loadImage(pathname, crop)); }
	
	/**
	 * Carica un'animazione dal disco
	 * @param pattern
	 * pattern che specifica il percors di tutti i file che compongono l'animazione
	 * @param start
	 * id della prima immagine che compone l'animazione
	 * @param end
	 * id dell'ultima immagine che compone l'animazione
	 * @param crop
	 * true se e solo se bisogna fare il crop
	 * @return
	 * la lista di immagini che compongono l'animazione
	 */
	public static List<BufferedImage> loadAnimation(String pattern, int start, int end, boolean crop)
	{ return IntStream.rangeClosed(start, end).mapToObj(i -> loadImage(String.format(pattern, i), crop)).toList(); }
	
	/**
	 * Carica un'animazione dal disco e la specchia
	 * @param pattern
	 * pattern che specifica il percors di tutti i file che compongono l'animazione
	 * @param start
	 * id della prima immagine che compone l'animazione
	 * @param end
	 * id dell'ultima immagine che compone l'animazione
	 * @param crop
	 * true se e solo se bisogna fare il crop
	 * @return
	 * la lista di immagini che compongono l'animazione
	 * @see loadFlipped
	 */
	public static List<BufferedImage> loadFlippedAnimation(String pattern, int start, int end, boolean crop)
	{ return IntStream.rangeClosed(start, end).mapToObj(i -> loadFlipped(String.format(pattern, i), crop)).toList(); }
	
	/**
	 * Carica un'immagine dal disco senza memorizzarla in cache
	 * @param pathname
	 * il percorso dell'immagine
	 * @return
	 * l'immagine
	 */
	private static BufferedImage loadRaw(String pathname)
	{
		try(InputStream input = ImageUtils.class.getResourceAsStream(pathname))
		{
			if(input == null)
				throw new IllegalStateException("Unable to load the sprite: " + pathname + " not found.");
			
			return ImageIO.read(input);
		}
		catch(IOException exp)
		{ throw new IllegalStateException("Unable to load the sprite " + pathname); }
	}
	
	/**
	 * Specchia orizzontalmente un'immagine
	 * @param image
	 * l'immagine
	 * @return
	 * la versione specchiata dell'immagine
	 */
	private static BufferedImage flipHorizontally(BufferedImage image)
	{
		int imageWidth = image.getWidth(), imageHeight = image.getHeight();
		
		BufferedImage flippedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = flippedImage.createGraphics();
		graphics.drawImage(image, imageWidth, 0, -imageWidth, imageHeight, null);
		graphics.dispose();
		
		return flippedImage;
	}
	
	/**
	 * Effettua il crop di un'immagine
	 * @param image
	 * l'immagine
	 * @return
	 * l'immaggine risultante
	 */
	private static BufferedImage crop(BufferedImage image)
	{
		int imageWidth = image.getWidth(), imageHeight = image.getHeight();
		int minX = imageWidth, maxX = 0, minY = imageHeight, maxY = 0;
		
		for(int y = 0; y < imageHeight; y++)
		{
			for(int x = 0; x < imageWidth; x++)
			{
				int currentAlpha = (image.getRGB(x, y) >> 24) & 0xff;
				
				if(currentAlpha <= 0)
					continue;
				
				minX = Math.min(minX, x); maxX = Math.max(maxX, x);
				minY = Math.min(minY, y); maxY = Math.max(maxY, y);
			}
		}
		
		return image.getSubimage(minX, minY, maxX - minX + 1, maxY - minY + 1);
	}
	
	/**
	 * Concatena un'immagine a se stessa
	 * @param image
	 * l'immagine
	 * @param times
	 * la lunghezza della concatenazione
	 * @param direction
	 * la direzione che deve seguire la concatenazione
	 * @return
	 * l'immagine risultante
	 */
	public static BufferedImage imageStrip(BufferedImage image, int times, Direction direction)
	{
		int imageWidth = image.getWidth(), imageHeight = image.getHeight();
		int resultImageWidth = imageWidth * ((direction == Direction.HORIZONTAL) ? times : 1);
		int resultImageHeight = imageHeight * ((direction == Direction.VERTICAL) ? times : 1);
				
		BufferedImage resultImage = new BufferedImage(resultImageWidth, resultImageHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = resultImage.createGraphics();
		
		int currentX = 0, currentY = 0;
		
		while(times-- >= 0)
		{
			graphics.drawImage(image, currentX, currentY, null);
			currentX += (direction == Direction.HORIZONTAL) ? imageWidth : 0;
			currentY += (direction == Direction.VERTICAL) ? imageHeight : 0;
		}
		
		graphics.dispose();
		return resultImage;
	}
	
	/** 
	 * Scala la dimensione di un'immagine
	 * @param image
	 * l'immagine
	 * @param width
	 * la nuova lunghezza
	 * @param height
	 * la nuova altezza
	 * @return
	 * l'immagine ridimensionata
	 */
	public static BufferedImage scaleImage(BufferedImage image, int width, int height)
	{
	    Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	    BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    
	    Graphics2D graphics = resultImage.createGraphics();
	    
	    graphics.drawImage(scaledImage, 0, 0, null);
	    graphics.dispose();
	    
	    return resultImage;
	}
	
	/**
	 * Converte un numero in una lista di immagini di cifre
	 * @param number
	 * il numero
	 * @param symbolsList
	 * i simboli da usare (dove symbolList[i] è il simbolo della cifra i)
	 * @return
	 * la lista di immagini
	 */
	public static List<BufferedImage> getNumberAsImagesList(int number, StaticImage[] symbolsList)
	{
		List<BufferedImage> numbersList = new ArrayList<BufferedImage>();
		
		while(number != 0)
		{
			numbersList.add(symbolsList[number % 10].getImage());
			number /= 10;
		}
		
		return numbersList;
	}
}
