package code.model.context;

//model import
import code.model.gameobjects.Furniture;

public record PlayerFoundSomething(Furniture source) implements ModelEvent {}
