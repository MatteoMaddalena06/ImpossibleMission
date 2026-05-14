package code.model.context;

//model import
import code.model.gameobjects.enemy.RunnerRobot;

public record RunnerStopRunning(RunnerRobot robot) implements ModelEvent {}
