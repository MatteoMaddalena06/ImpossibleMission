package code.model.context;

//model import
import code.model.gameobjects.enemy.RunnerRobot;

public record RunnerStartRunning(RunnerRobot robot) implements ModelEvent {}
