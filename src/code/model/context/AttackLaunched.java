package code.model.context;

//model import
import code.model.gameobjects.enemy.AttackerRobot;

public record AttackLaunched(AttackerRobot.Attack source) implements ModelEvent {}
