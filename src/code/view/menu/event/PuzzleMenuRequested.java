package code.view.menu.event;

//model import
import code.model.gameobjects.Player;

public record PuzzleMenuRequested(Player player) implements MenuEvent {}
