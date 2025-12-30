package code.model.context;

import code.model.gameobjects.Furniture;

public record FurnitureSearchEnded(Furniture source) implements GameEvent {}
