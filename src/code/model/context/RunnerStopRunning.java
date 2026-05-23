package code.model.context;

//model import
import code.model.gameobjects.enemy.RunnerRobot;

/** Modella l'evento RunnerStopRunning*/
public record RunnerStopRunning(RunnerRobot robot) implements ModelEvent {}
