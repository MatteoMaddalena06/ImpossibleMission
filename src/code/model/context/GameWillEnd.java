package code.model.context;

/** Modella l'evento  GameWillEnd */
public record GameWillEnd(long nanos) implements ModelState {
	/** Costante per indicare la fine adesso */
	public static final int NOW = 0;
}
