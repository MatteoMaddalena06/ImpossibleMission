package code.model.context;

//model import
import code.model.gameobjects.Furniture;

/** Modella l'evento PlayerFoundSomething*/
public record PlayerFoundSomething(Furniture source) implements ModelEvent {}
