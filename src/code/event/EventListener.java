package code.event;

public interface EventListener<T>
{
	void onEvent(T event);
}
