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
//IO import
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public abstract class ImageUtils 
{
	private static final Map<String, BufferedImage> IMAGE_CACHE = new HashMap<String, BufferedImage>();
	
	public enum Direction
	{ VERTICAL, HORIZONTAL }

	public static BufferedImage loadImage(String pathname)
	{ return IMAGE_CACHE.computeIfAbsent(pathname, f -> crop(loadRaw(pathname))); }
	
	public static BufferedImage loadFlipped(String pathname)
	{ return flipHorizontally(loadImage(pathname)); }
	
	public static List<BufferedImage> loadAnimation(String pattern, int start, int end)
	{ return IntStream.rangeClosed(start, end).mapToObj(i -> loadImage(String.format(pattern, i))).toList(); }
	
	public static List<BufferedImage> loadFlippedAnimation(String pattern, int start, int end)
	{ return IntStream.rangeClosed(start, end).mapToObj(i -> loadFlipped(String.format(pattern, i))).toList(); }
	
	private static BufferedImage loadRaw(String pathname)
	{
		try(InputStream input = StaticImage.class.getResourceAsStream(pathname))
		{
			if(input == null)
				throw new IllegalStateException("Unable to load the sprite: " + pathname + " not found.");
			
			return ImageIO.read(input);
		}
		catch(IOException exp)
		{ throw new IllegalStateException("Unable to load the sprite " + pathname); }
	}
	
	private static BufferedImage flipHorizontally(BufferedImage image)
	{
		int imageWidth = image.getWidth(), imageHeight = image.getHeight();
		
		BufferedImage flippedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = flippedImage.createGraphics();
		graphics.drawImage(image, imageWidth, 0, -imageWidth, imageHeight, null);
		graphics.dispose();
		
		return flippedImage;
	}
	
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
