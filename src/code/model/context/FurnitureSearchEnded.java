package code.model.context;

import code.model.gameobjects.Furniture;

/** Modella l'evento  FurnitureSearchEnded */
public record FurnitureSearchEnded(Furniture source) implements ModelEvent {}
