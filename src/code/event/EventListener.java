package code.event;

/** Tipizza i listener */
public interface EventListener<T>
{
	/**
	 * L'handler dell'evento 
	 * @param event 
	 * l'evento
	 */
	void onEvent(T event);
}
