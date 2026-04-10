package code.model.context;

import code.model.gameobjects.enemy.AttackerRobot;

public record AttackEnded(AttackerRobot.Attack source) implements ModelEvent {}
