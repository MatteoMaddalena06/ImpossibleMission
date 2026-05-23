package code.event;

//data structure import
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** La classe che funge da hub centrale per lo smistamento degli eventi */
public abstract class EventDispatcher 
{
	/** La lista di listener statici (cioè quelli che persistenti fra le partite)  */
	private static Map<Class<?>, List<EventListener<?>>> staticListeners = new HashMap<>();
	/** La lista dei listener normali (cioè quelli che devono essere ricreati per ogni partita)*/
	private static Map<Class<?>, List<EventListener<?>>> listeners = new HashMap<>();

	/**
	 * Registra un listener come statico
	 * @param type
	 * il tipo di eventi 
	 * @param listener
	 * il listener da associare al tipo di evento
	 */
	public static <T> void subscribeAsStatic(Class<T> type, EventListener<T> listener)
	{ staticListeners.computeIfAbsent(type, x -> new ArrayList<>()).add(listener); }
	
	/**
	 * Registra un listener come normale
	  * @param type
	 * il tipo di eventi 
	 * @param listener
	 * il listener da associare al tipo di evento
	 */
	public static <T> void subscribe(Class<T> type, EventListener<T> listener)
	{ listeners.computeIfAbsent(type, x -> new ArrayList<>()).add(listener); }

	/**
	 * Notifica tutti i listner associati all'evento event
	 * @param event
	 * l'evento 
	 */
	public static <T> void notify(T event)
	{ 
		 List<EventListener<?>> listenersList;
		 
		if((listenersList = staticListeners.get(event.getClass())) != null)
			listenersList.forEach(l -> ((EventListener<T>)l).onEvent(event));
		
		if((listenersList = listeners.get(event.getClass())) != null)
			listenersList.forEach(l -> ((EventListener<T>)l).onEvent(event));
	}
	
	/** Pulisce la lista in modo da permette al GC di deallocare i listener che ormai non servono più */
	public static void disposeListeners()
	{ listeners.clear(); }
}
