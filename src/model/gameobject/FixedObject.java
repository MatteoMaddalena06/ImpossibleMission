package model.gameobject;

//muri e pavimenti
public class FixedObject extends GameObject{

    public FixedObject(int type, int x, int y, int w, int h){
        super(new Point(x, y), w, h);
        this.type = type;
    }

    @Override
    public void update(){}
}
