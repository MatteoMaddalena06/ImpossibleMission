package model.gameobject;

//muri e pavimenti
public class FixedObject extends GameObject{

    public static final int TYPE_WALL = 1;
    public static final int TYPE_FLOOR = 2;

    public FixedObject(int x, int y, int w, int h){
        super(x, y, w, h);
    }

    @Override
    public void update(){}
}
