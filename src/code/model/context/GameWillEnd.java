package code.model.context;

public record GameWillEnd(long nanos) implements ModelState {
	public static final int NOW = 0;
}
