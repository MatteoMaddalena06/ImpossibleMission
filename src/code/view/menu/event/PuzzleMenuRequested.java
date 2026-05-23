package code.view.menu.event;

//model import
import code.model.gameobjects.Player;

/** Record per l'evento PuzzleMenuRequested */
public record PuzzleMenuRequested(Player player) implements MenuEvent {}
