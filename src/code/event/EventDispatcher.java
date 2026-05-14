package code.event;

//data structure import
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class EventDispatcher 
{
	private static Map<Class<?>, List<EventListener<?>>> staticListeners = new HashMap<>();
	private static Map<Class<?>, List<EventListener<?>>> listeners = new HashMap<>();

	public static <T> void subscribeAsStatic(Class<T> type, EventListener<T> listener)
	{ staticListeners.computeIfAbsent(type, x -> new ArrayList<>()).add(listener); }
	
	public static <T> void subscribe(Class<T> type, EventListener<T> listener)
	{ listeners.computeIfAbsent(type, x -> new ArrayList<>()).add(listener); }

	public static <T> void notify(T event)
	{ 
		 List<EventListener<?>> listenersList;
		 
		if((listenersList = staticListeners.get(event.getClass())) != null)
			listenersList.forEach(l -> ((EventListener<T>)l).onEvent(event));
		
		if((listenersList = listeners.get(event.getClass())) != null)
			listenersList.forEach(l -> ((EventListener<T>)l).onEvent(event));
	}
	
	public static void disposeListeners()
	{ listeners.clear(); }
}
