package code.model.context;

//model import
import code.model.gameobjects.Furniture;

/** Modella l'evento PlayerIsSearching*/
public record PlayerIsSearching(Furniture source) implements ModelEvent{}
