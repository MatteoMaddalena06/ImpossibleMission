package code.model.context;

//model import
import code.model.gameobjects.enemy.AttackerRobot;

/** Modella l'evento AttackLaunched */
public record AttackLaunched(AttackerRobot.Attack source) implements ModelEvent {}
