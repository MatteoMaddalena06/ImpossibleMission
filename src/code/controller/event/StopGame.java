package code.controller.event;

//graphics import
import javax.swing.JPanel;

public record StopGame(JPanel from) implements GameLoopEvent {}
