package model.gameobject;

import java.io.Serializable;

public abstract class GameObject implements Serializable
{
	protected int x, y, w, h;
	protected int type;
	
	@Override
	public String toString()
	{ return type + ": (" + x + ", " + y + ")/(" + w + ", " + h + ")"; }
	
	public GameObject(Point point, int w, int h)
	{
		this.x = point.getX();
		this.y = point.getY();
		this.w = w; this.h = h;
	}

	public abstract void update();

	public boolean intersects(GameObject other){
		return this.x < other.x +other.w &&
			this.x + this.w > other.x &&
			this.y < other.y + other.h &&
			this.y + this.h > other.y;
	}

	public boolean intersects(int otherX, int otherY, int otherW, int otherH){
		return this.x < otherX +otherW &&
			this.x + this.w > otherX &&
			this.y < otherY + otherH &&
			this.y + this.h > otherY;
	}

	public int getX(){return x;}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
//fix git
}
