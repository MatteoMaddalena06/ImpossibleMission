package code.model.context;

//model import
import code.model.gameobjects.Furniture;

public record PlayerIsSearching(Furniture source) implements GameEvent{}
