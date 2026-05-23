package code.model.gameobjects;

//model import
import code.model.Point;
import code.model.context.GameContext;
import code.model.context.TerminalOpened;
//event import
import code.event.EventDispatcher;

/** Classe che modella i terminali del gioco */
public class Terminal extends GameObject
{
	/**
	 * Costruisce la classe
	 * @param position
	 * la posizione del terminale
	 * @param width
	 * la dimensione orizzontale del terminale
	 * @param height
	 * la dimensione verticale del terminale 
	 */
	public Terminal(Point position, int width, int height)
	{ super(position, width, height); }
	
	/**
	 * Aggiorna lo stato del terminale controllando se l'utente lo sta usando 
	 * @param context 
	 * il contesto di gioco su cui operare
	 */
	@Override
	public void update(GameContext context) 
	{
		Player player = context.getPlayer();
		
		if(context.getUserInput(GameContext.UserInput.UP) && isColliding(player))
			EventDispatcher.notify(new TerminalOpened());
	}
}
