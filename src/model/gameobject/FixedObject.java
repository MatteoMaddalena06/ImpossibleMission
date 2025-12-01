package model.gameobject;

//muri e pavimenti
public class FixedObject extends GameObject{

    public static final int TYPE_SOLID = 1;

    public FixedObject(int type, int x, int y, int w, int h){
        super(type, x, y, w, h);
    }

    @Override
    public void update(){}
}
