package code.controller.event;

//model import
import code.model.gameobjects.Player;

public record TerminalMenuRequested(Player player) implements ControllerEvent {}
