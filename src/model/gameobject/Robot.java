package model.gameobject;

import model.room.RoomMap;

public class Robot extends GameObject 
{
    public static final int TYPE_ROBOT = RoomMap.ROBOT_ID;
    private static final int ROBOT_WIDTH = 30;//sempre numeri da rivedere
    private static final int ROBOT_HEIGHT = 50;


    public enum Direction {LEFT, RIGHT}
    public enum State {WALKING, SHOOTING}
    private int speed = 2; 

    private Direction currentDirection = Direction.RIGHT;
    private State currentState = State.WALKING;
    
    public Robot(Point position, int w, int h){ 
        super(position, w, h);
     }

    public void reverseDirection(){
        if(currentDirection == Direction.RIGHT) currentDirection = Direction.LEFT;
        else currentDirection = Direction.RIGHT;
    }

    public static int getTypeRobot() {
        return TYPE_ROBOT;
    }

    public static int getRobotWidth() {
        return ROBOT_WIDTH;
    }

    public static int getRobotHeight() {
        return ROBOT_HEIGHT;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

	@Override
	public void update(GameContext context) {
		// TODO Auto-generated method stub
		
	}


}
