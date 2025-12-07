package model.gameobject;

//data structure modulesd
import java.util.List;

public abstract class MovingObject extends GameObject
{
	protected static final double GRAVITY = 2000f; 
	
	protected transient double horizontalVelocity;
	protected transient double verticalVelocity;
	protected transient double horizontalSpeed;
	protected transient double verticalSpeed;
	protected transient boolean onGround;
	
	private static double deltaTime;
	
	public MovingObject(Point position, int width, int height, double horizontalSpeed, double verticalSpeed)
	{
		super(position, width, height);
		this.horizontalSpeed = horizontalSpeed;
		this.verticalSpeed = verticalSpeed;
		horizontalVelocity = verticalVelocity = 0;
		onGround = true;
	}
	
	protected void addGravity()
	{ verticalVelocity += GRAVITY * deltaTime; }
	
	protected void applyHorizontalForce()
	{ position.setX((int)(position.getX() + horizontalVelocity * deltaTime)); }
	
	protected void applyVerticalForce()
	{ position.setY((int)(position.getY() + verticalVelocity * deltaTime)); }
	
	protected void shrinkHitbox(int newWidth, int newHeight) 
	{
	    position.setX(position.getX() + (width - newWidth) / 2);
	    position.setY(position.getY() + (height - newHeight));
	    width = newWidth; height = newHeight;
	}
	
	protected void expandHitbox(int newWidth, int newHeight) 
	{
	    position.setX(position.getX() - (newWidth - width) / 2);
	    position.setY(position.getY() - (newHeight - height));
	    width = newWidth; height = newHeight;
	}
	
	protected void resolveHorizontalCollision(List<GameObject> gameObjectList)
	{
		GameObject nearest = null;
	    int correctionX = position.getX(); 
	    int minDistance = Integer.MAX_VALUE;

	    for (GameObject fixed : gameObjectList) 
	    {
	        if(!isColliding(fixed))
	        	continue;

	        int gameObjectX = fixed.getPosition().getX();
	        int gameObjectWidth = fixed.getWidth();
	        int newX = (horizontalVelocity > 0) ? gameObjectX - width : gameObjectX + gameObjectWidth;
	        int distance = Math.abs(newX - position.getX());

	        if(distance < minDistance) 
	        {
	            minDistance = distance;
	            nearest = fixed;
	            correctionX = newX;
	        }
	    }

	    if(nearest != null) 
	    { position.setX(correctionX); horizontalVelocity = 0; }
	}
	
	protected void resolveVerticalCollision(List<GameObject> gameObjectList)
	{
		GameObject nearest = null;
	    int correctionY = position.getY();
	    int minDistance = Integer.MAX_VALUE;

	    for (GameObject fixed : gameObjectList) {

	        if(!isColliding(fixed)) 
	        	continue;

	        int gameObjectY = fixed.getPosition().getY();
	        int gameObjectHeight = fixed.getHeight();
	        int newY = (verticalVelocity > 0) ? gameObjectY - height : gameObjectY + gameObjectHeight;
	        int distance = Math.abs(newY - position.getY());

	        if(distance < minDistance) 
	        {
	            minDistance = distance;
	            nearest = fixed;
	            correctionY = newY;
	        }
	    }

	    if(nearest != null) 
	    {
	        position.setY(correctionY);
	        if(verticalVelocity > 0) onGround = true;	
	        verticalVelocity = 0;
	        return;
	    }

	    onGround = gameObjectList.stream().anyMatch(g -> isStandingOn(g));
	    if(onGround == true) verticalVelocity = 0;
	}
	
	private boolean isStandingOn(GameObject other)
	{
	    int x1 = position.getX(), y1 = position.getY();
	    int w1 = width, h1 = height;
	    
	    int x2 = other.getPosition().getX(), y2 = other.getPosition().getY();
	    int w2 = other.getWidth();

	    final int tolerance = 2;

	    boolean horizontalOverlap = x1 + w1 > x2 && x1 < x2 + w2;
	    boolean verticalAlignment = (y1 + h1 >= y2 - tolerance) && (y1 + h1 <= y2 + tolerance);

	    return horizontalOverlap && verticalAlignment;
	}
	
	public static void setDeltaTime(double deltaTime)
	{ MovingObject.deltaTime = deltaTime; } 
}
