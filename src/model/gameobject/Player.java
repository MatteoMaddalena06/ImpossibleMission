package model.gameobject;

public class Player extends GameObject  
{
    private static final int PLAYER_WIDTH = 32;//non so quanto sia di preciso, da modificare
    private static final int PLAYER_HEIGHT = 64;//uguale 

    public static final int TYPE_PLAYER = 999;

    //per le animazioni 
    public enum Direction {LEFT, RIGHT};
    public enum State {IDLE, WALKING, SEARCHING, JUMPING, FALLING};

    private double velX;
    private double velY;
    private double speed = 3.0; 

    private Direction currentDirection;
    private State currentState;

    public Player(int startX, int startY){
        super(TYPE_PLAYER, startX, startY, PLAYER_WIDTH, PLAYER_HEIGHT);

        this.velX = 0;
        this.velY = 0;
        this.currentDirection= Direction.RIGHT;
        this.currentState = State.IDLE;

    }    

    @Override
    public void update(){
        x+=(int)velX;
        y+=(int)velY;

        updateAnimationsState();
    }

    private void updateAnimationsState(){
        if (velX != 0) {
        currentDirection = (velX > 0) ? Direction.RIGHT : Direction.LEFT;
        }

        if(velY != 0){
            currentState = (velY > 0) ? State.FALLING : State.JUMPING;
            return;            
        }

        if (velX != 0){currentState = State.WALKING;}
        else{currentState = State.IDLE;}
    }

    public void setVelX(double velX){this.velX = velX;}

    public void setVelY(double velY){this.velY = velY;}

    public double getVelX(){ return velX; }
    public double getVelY(){ return velY; }

    public double getSpeed(){return speed;}
    public void setSpeed(double speed){this.speed = speed;}

    public Direction getDirection(){ return currentDirection; }
    public State getState() {return currentState;}

    public void setState(State s) {this.currentState = s;}
    public void setDirection(Direction d) {this.currentDirection = d;}
//fix git
}
