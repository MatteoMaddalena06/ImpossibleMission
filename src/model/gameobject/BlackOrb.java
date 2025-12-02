package model.gameobject;

public class BlackOrb extends GameObject{

    public static final int TYPE_BLACKORB = 51;

    private static final int ORB_WIDTH = 24;
    private static final int ORB_HEIGHT = 24;

    private int speed = 3;
    private int direction = 1;

    public BlackOrb(int x, int y, int w, int h){ super(x, y, ORB_WIDTH, ORB_HEIGHT);}

    @Override
    public void update(){
        x += speed * direction;
    }

    public void reverseDirection(){this.direction *= -1;}

    public static int getTypeBlackorb() {
        return TYPE_BLACKORB;
    }

    public static int getOrbWidth() {
        return ORB_WIDTH;
    }

    public static int getOrbHeight() {
        return ORB_HEIGHT;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

}
