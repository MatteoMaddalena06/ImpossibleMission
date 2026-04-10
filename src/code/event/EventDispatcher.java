package code.event;

//data structure import
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class EventDispatcher 
{
	private static final Map<Class<?>, List<EventListener<?>>> listeners = new HashMap<>();

	public static <T> void subscribe(Class<T> type, EventListener<T> listener)
	{ listeners.computeIfAbsent(type, x -> new ArrayList<>()).add(listener); }

	public static <T> void notify(T event)
	{ listeners.get(event.getClass()).forEach(l -> ((EventListener<T>)l).onEvent(event)); }
}
