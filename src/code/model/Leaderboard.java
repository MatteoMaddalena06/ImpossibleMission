package code.model;

//data structure import
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Arrays;
//IO import
import java.io.Serializable;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Leaderboard implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static final String leaderboardFilename = "leaderboard.dat";
	
	private static final String READ_ERROR  = "Unable to read the leaderboard file";
	private static final String WRITE_ERROR = "Unable to save the leaderboard data";
	
	private static Leaderboard instance;
	
	private List<Entry> content;
	
	public static class Entry implements Serializable 
	{
		private static final long serialVersionUID = 1L;
		
		private String name;
		private int points;
		private int rank;
		
		public Entry(String name, int points)
		{ 
			this.name = name;
			this.points = points;
		}
		
		public String getName()
		{ return name; }
		
		public int getPoints()
		{ return points; }
		
		public int getRank()
		{ return rank; }
		
		public void setRank(int rank)
		{ this.rank = rank; }
	}
	
	private Leaderboard()
	{ content = new ArrayList<Entry>(); }
		
	public void store() 
	{
		try 
		{
			Path storePath = Path.of(
					Leaderboard.class.getProtectionDomain().getCodeSource().getLocation().toURI()
			).resolve(leaderboardFilename);
			
			ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(storePath));
		    output.writeObject(this);
		}
		catch (URISyntaxException | IOException e) 
		{ throw new RuntimeException(WRITE_ERROR); }
	}

	public static Leaderboard load()
	{
		if(instance != null)
			return instance;
		
		try
		{
			Path loadPath = Path.of(
			   Leaderboard.class.getProtectionDomain().getCodeSource().getLocation().toURI()
			).resolve(leaderboardFilename);
			
			ObjectInputStream input = new ObjectInputStream(Files.newInputStream(loadPath));
			instance = (Leaderboard)input.readObject();
		}
		catch(IOException | ClassNotFoundException | URISyntaxException e)
		{ instance = new Leaderboard(); }	
		
		return instance;
	}
	
	public void addEntry(Entry entry)
	{
		int index = findPlayer(entry.getName());
		
		if(index >= 0 && entry.getPoints() > content.get(index).getPoints())
		{
			removeEntry(index);
			insertEntry(entry);
		}
		else if(index < 0)
			insertEntry(entry);
	}
	
	private int findPlayer(String name)
	{ 
		OptionalInt index = IntStream.range(0, content.size()).filter(i -> content.get(i).getName().equals(name)).findFirst();
		return (index.isPresent()) ? index.getAsInt() : -1;
	}
	
	private void insertEntry(Entry entry)
	{
		int points = entry.getPoints(), i;
		
		for(i = 0; i < content.size(); i++)
		{
			if(points > content.get(i).getPoints())
			{ content.add(i, entry); break; }	
		}
		
		if(i == content.size())
			content.add(entry);
		
		if(i == 0 || entry.getPoints() != content.get(i - 1).getPoints())
		{
			entry.setRank((i != 0 ) ? content.get(i - 1).getRank() + 1 : 0);
			
			for(i = i + 1; i < content.size(); i++)
			{
				Entry currEntry = content.get(i);
				currEntry.setRank(currEntry.getRank() + 1);
			}
		}
		else
			entry.setRank(content.get(i - 1).getRank());
	}
	
	private void removeEntry(int index)
	{ content.remove(index); }
	
	public List<Entry> getContent()
	{ return content; }
}
