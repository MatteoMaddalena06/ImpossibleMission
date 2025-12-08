package model.utils;

//IO modules
import java.io.Serializable;

public class Point implements Serializable
{
    private static final long serialVersionUID = 1L;
    
	private int x, y;

    public Point(int x, int y)
    { this.x = x; this.y = y; }
    
    @Override
    public boolean equals(Object obj)
    {
        if(this == obj) 
        	return true;
        
        if(obj == null || getClass() != obj.getClass()) 
        	return false;
        
        Point other = (Point) obj;
        return x == other.x && y == other.y;
    }
    
    //for a copy of the point
  	public Point(Point source)
  	{ this(source.x, source.y); }

    public void setX(int x) 
    { this.x = x; }
    
    public void setY(int y) 
    { this.y = y; }
    
    public int getX() 
    { return x; }

    public int getY() 
    { return y; }
}