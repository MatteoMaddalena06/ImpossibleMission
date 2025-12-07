package model.gameobject;

import model.room.RoomMap;

public class BlackOrb extends Robot{

    public static final int TYPE_BLACKORB = RoomMap.BLACK_ORB_ID;

    private static final int ORB_WIDTH = 24;
    private static final int ORB_HEIGHT = 24;

    private int speed = 3;
    private int direction = 1;

    public BlackOrb(Point position, int w, int h){
    	super(position, w, h);
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

	@Override
	public void update(GameContext context) {
		// TODO Auto-generated method stub
		
	}

}
