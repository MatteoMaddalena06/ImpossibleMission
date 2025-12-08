package model.gameobject;

//data structure modules
import java.util.List;


public abstract class MovingObject extends GameObject
{
	private static final double GRAVITY = 2000f; 
	private static final int    STANDING_TOLLERANCE = 2; 
	
	private transient double horizontalVelocity;
	private transient double verticalVelocity;
	
	private transient boolean onGround;
	private transient boolean wasHitboxModified;
	
	private static double deltaTime;
	
	public MovingObject(Point position, int width, int height)
	{
		super(position, width, height);
		horizontalVelocity = verticalVelocity = 0;
		onGround = true; wasHitboxModified = false;
	}
	
	protected void addGravity()
	{ verticalVelocity += GRAVITY * deltaTime; }
	
	protected void applyHorizontalForce()
	{ 
		Point thisPosition = getPosition();
		thisPosition.setX((int)(thisPosition.getX() + horizontalVelocity * deltaTime)); 
	}
	
	protected void applyVerticalForce()
	{ 
		Point thisPosition = getPosition();
		thisPosition.setY((int)(thisPosition.getY() + verticalVelocity * deltaTime)); 
	}
	
	protected void shrinkHitbox(int newWidth, int newHeight) 
	{
		Point thisPosition = getPosition();
		int thisWidth = getWidth(), thisHeight = getHeight();
	    thisPosition.setX(thisPosition.getX() + (thisWidth - newWidth) / 2);
	    thisPosition.setY(thisPosition.getY() + (thisHeight - newHeight));
	    setWidth(newWidth); setHeight(newHeight);
	    
	    wasHitboxModified = true;
	}
	
	protected void expandHitbox(int newWidth, int newHeight) 
	{
		Point thisPosition = getPosition();
		int thisWidth = getWidth(), thisHeight = getHeight();
	    thisPosition.setX(thisPosition.getX() - (thisWidth - newWidth) / 2);
	    thisPosition.setY(thisPosition.getY() - (thisHeight - newHeight));
	    setWidth(newWidth); setHeight(newHeight);
	    
	    wasHitboxModified = false;
	}
	
	protected void resolveHorizontalCollision(List<GameObject> gameObjectList)
	{
		Point thisPosition = getPosition();
	    int correctionX = thisPosition.getX(), minDistance = Integer.MAX_VALUE;
	    GameObject nearest = null;

	    for (GameObject fixed : gameObjectList) 
	    {
	        if(!isColliding(fixed))
	        	continue;

	        int fixedX = fixed.copyPosition().getX();
	        int newX = (horizontalVelocity > 0) ? fixedX - getWidth() : fixedX + fixed.getWidth();
	        int distance = Math.abs(newX - thisPosition.getX());

	        if(distance < minDistance) 
	        {
	            minDistance = distance;
	            nearest = fixed;
	            correctionX = newX;
	        }
	    }

	    if(nearest != null) 
	    { thisPosition.setX(correctionX); horizontalVelocity = 0; }
	}
	
	protected void resolveVerticalCollision(List<GameObject> gameObjectList)
	{
		Point thisPosition = getPosition();
	    int correctionY = thisPosition.getY(), minDistance = Integer.MAX_VALUE;
		GameObject nearest = null;

	    for (GameObject fixed : gameObjectList)
	    {
	        if(!isColliding(fixed)) 
	        	continue;

	        int fixedY = fixed.copyPosition().getY();
	        int newY = (verticalVelocity > 0) ? fixedY - getHeight() : fixedY + fixed.getHeight();
	        int distance = Math.abs(newY - thisPosition.getY());

	        if(distance < minDistance) 
	        {
	            minDistance = distance;
	            nearest = fixed;
	            correctionY = newY;
	        }
	    }

	    if(nearest != null) 
	    {
	        thisPosition.setY(correctionY);
	        if(verticalVelocity > 0) onGround = true;	
	        verticalVelocity = 0;
	        return;
	    }

	    onGround = gameObjectList.stream().anyMatch(g -> isStandingOn(g));
	    if(onGround == true) verticalVelocity = 0;
	}
	
	private boolean isStandingOn(GameObject other)
	{
		Point thisPosition = getPosition();
	    int x1 = thisPosition.getX(), y1 = thisPosition.getY();
	    int w1 = getWidth(), h1 = getHeight();
	    
	    Point otherPosition = other.copyPosition();
	    int x2 = otherPosition.getX(), y2 = otherPosition.getY();
	    int w2 = other.getWidth();

	    final int tolerance = STANDING_TOLLERANCE;

	    boolean horizontalOverlap = x1 + w1 > x2 && x1 < x2 + w2;
	    boolean verticalAlignment = (y1 + h1 >= y2 - tolerance) && (y1 + h1 <= y2 + tolerance);

	    return horizontalOverlap && verticalAlignment;
	}
	
	protected double getHorizontalVelocity()
	{ return horizontalVelocity; }
	
	protected double getVerticalVelocity()
	{ return verticalVelocity; }
	
	protected void setHorizontalVelocity(double velocity)
	{ horizontalVelocity = velocity; }
	
	protected void setVerticalVelocity(double velocity)
	{ verticalVelocity = velocity; }
	
	protected boolean isOnGround()
	{ return onGround; }
	
	protected boolean wasHitboxModified()
	{ return wasHitboxModified; }
	
	public static void setDeltaTime(double deltaTime)
	{ MovingObject.deltaTime = deltaTime; } 
}
