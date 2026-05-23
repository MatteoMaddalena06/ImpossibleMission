package code.model.context;

import code.model.gameobjects.enemy.AttackerRobot;

/** Modella l'evento AttackEnded */
public record AttackEnded(AttackerRobot.Attack source) implements ModelEvent {}
