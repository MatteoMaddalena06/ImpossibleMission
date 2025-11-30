package model.gameobject;

public class GameObject 
{
	int x, y, w, h;
	int type;
	
	@Override
	public String toString()
	{ return type + ": (" + x + ", " + y + ")/(" + w + ", " + h + ")"; }
	
	public GameObject(int type, int x, int y, int w, int h)
	{
		this.type = type;
		this.x = x; this.y = y;
		this.w = w; this.h = h;
	}
}
