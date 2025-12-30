package code.model;

//IO modules
import java.io.Serializable;

public class Point implements Serializable
{
    private static final long serialVersionUID = 1L;
    
	private double x, y;

    public Point(double x, double y)
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

    public void setX(double x) 
    { this.x = x; }
    
    public void setY(double y) 
    { this.y = y; }
    
    public double getX() 
    { return x; }

    public double getY() 
    { return y; }
}