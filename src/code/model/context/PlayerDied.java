package code.model.context;

import code.model.gameobjects.Player;

public record PlayerDied(Player source) implements GameEvent {}
