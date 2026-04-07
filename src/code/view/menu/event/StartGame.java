package code.view.menu.event;

//graphics import
import javax.swing.JPanel;

public record StartGame(String playerName, JPanel from) implements MenuEvent {}
