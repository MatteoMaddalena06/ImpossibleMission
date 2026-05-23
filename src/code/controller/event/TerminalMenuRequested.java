package code.controller.event;

//model import
import code.model.gameobjects.Player;

/** Tipizza l'evento TerminalMenuRequested */
public record TerminalMenuRequested(Player player) implements ControllerEvent {}
