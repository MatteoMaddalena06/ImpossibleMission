package code.model;

/** Classe che modella il concetto di punto */
public class Point
{
	/** Coordinata x del punto */
	private double x;
	/** Coordinata y del punto */
	private double y;

	/**
	 * Costruisce il punto
	 * @param x
	 * coordinata x
	 * @param y
	 * coordainta y
	 */
    public Point(double x, double y)
    { this.x = x; this.y = y; }
    
    /**
     * Costruttore di copia
     * @param source
     * il punto da copiare
     */
  	public Point(Point source)
  	{ this(source.x, source.y); }
  	
  	/**
  	 * Confronta due punti
  	 * @return true se e solo se i due punti hanno le stesse coordinate
  	 */
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

    /**
     * Imposta la coordinata x del punto
     * @param x
     * coordinata x
     */
    public void setX(double x) 
    { this.x = x; }
    
    /**
     * Imposta la coordinata y del punto
     * @param y
     * coordinata y
     */
    public void setY(double y) 
    { this.y = y; }
    
    /**
     * Restituisce la coordinata x del punto
     * @return 
     * coordinata x del punto
     */
    public double getX() 
    { return x; }

    /**
     * Restituisce la coorindata y del punto
     * @return
     * coordinata y del punto
     */
    public double getY() 
    { return y; }
}