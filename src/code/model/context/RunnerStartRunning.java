package code.model.context;

//model import
import code.model.gameobjects.enemy.RunnerRobot;

/** Modella l'evento RunnerStartRunning*/
public record RunnerStartRunning(RunnerRobot robot) implements ModelEvent {}
