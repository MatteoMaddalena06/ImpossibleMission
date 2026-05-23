package code.model;

//data structure import
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.ArrayList;
//IO import
import java.io.Serializable;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/** Classe che modella la classifica */
public class Leaderboard implements Serializable
{
	private static final long serialVersionUID = 1L;
	/**Percorso del file della classficia*/
	private static final String leaderboardFilename = "leaderboard.dat";
	
	/**Errore nella lettura del file della classifica*/
	private static final String READ_ERROR  = "Unable to read the leaderboard file";
	/**Errore nella scrittura del file della classifica*/
	private static final String WRITE_ERROR = "Unable to save the leaderboard data";
	
	/**Istanza della classe per il pattern singleton*/
	private static Leaderboard instance;
	
	/** Contenuto della classifica */
	private List<Entry> content;
	
	/** Classe che modella una riga della classifica*/
	public static class Entry implements Serializable 
	{
		private static final long serialVersionUID = 1L;
		
		/**Nome del giocatore**/
		private String name;
		/**Punti del giocatore*/
		private int points;
		/**Posizione del giocatore*/
		private int rank;
		
		/**Costruisce la classe*/
		public Entry(String name, int points)
		{ 
			this.name = name;
			this.points = points;
		}
		
		/**
		 * Restituisce il nome del player
		 * @return
		 * nome del player
		 */
		public String getName()
		{ return name; }
		
		/**
		 * Restitusce i punti del player
		 * @return
		 * punti del player
		 */
		public int getPoints()
		{ return points; }
		
		/**
		 * Restituisce la posizione in classifica del player
		 * @return
		 * posizione in classifica del player 
		 */
		public int getRank()
		{ return rank; }
		
		/**
		 * Imposta la posizione in classifica del player 
		 * @param rank
		 * posizione in classifica
		 */
		public void setRank(int rank)
		{ this.rank = rank; }
	}
	
	/**Costruisce la classe*/
	private Leaderboard()
	{ content = new ArrayList<Entry>(); }
		
	/**Memorizza la classfica sul disco*/
	public void store() 
	{
		try 
		{
			Path storePath = Path.of(
					Leaderboard.class.getProtectionDomain().getCodeSource().getLocation().toURI()
			).resolve(leaderboardFilename);
			
			ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(storePath));
		    output.writeObject(this);
		    output.close();
		}
		catch (URISyntaxException | IOException e) 
		{ throw new RuntimeException(WRITE_ERROR); }
	}

	/**
	 * Carica la classifica dal disco
	 * @return
	 * l'istanza della classifica caricata
	 */
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
			input.close();
		}
		catch(IOException | ClassNotFoundException | URISyntaxException e)
		{ instance = new Leaderboard(); }	
		
		return instance;
	}
	
	/**
	 * Aggiunge una riga alla classifica
	 * @param entry
	 * la riga
	 */
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
	
	/**
	 * Cerca un player in classifica
	 * @param name
	 * il nome del player
	 * @return
	 * la sua posizione in classifica se c'è, altrimenti -1
	 */
	private int findPlayer(String name)
	{ 
		OptionalInt index = IntStream.range(0, content.size()).filter(i -> content.get(i).getName().equals(name)).findFirst();
		return (index.isPresent()) ? index.getAsInt() : -1;
	}
	
	/**
	 * Aggiunge una riga alla classifica
	 * @param entry
	 * la riga
	 */
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
	
	/**
	 * Rimuove una riga dalla classifica
	 * @param index
	 * la posizione della riga
	 */
	private void removeEntry(int index)
	{ content.remove(index); }
	
	/** 
	 * Restituisce il contenuto della classifica
	 * @return
	 * il contenuto della classifica
	 */
	public List<Entry> getContent()
	{ return content; }
}
