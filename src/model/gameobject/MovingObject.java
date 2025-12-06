package model.gameobject;

public abstract class MovingObject extends GameObject
{
	protected static final double GRAVITY = 900f; 
	
	protected transient double horizontalVelocity;
	protected transient double verticalVelocity;
	protected transient double horizontalSpeed;
	protected transient double verticalSpeed;
	private   transient double deltaTime;
	
	public MovingObject(Point position, int width, int height, double horizontalSpeed, double verticalSpeed)
	{
		super(position, width, height);
		this.horizontalSpeed = horizontalSpeed;
		this.verticalSpeed = verticalSpeed;
	}
	
	protected void applyGravity()
	{
		verticalVelocity += GRAVITY * deltaTime;
		applyVerticalForce();
	}
	
	protected void applyHorinztalForce()
	{ super.position.setX((int)(super.position.getX() + horizontalVelocity * deltaTime)); }
	
	protected void applyVerticalForce()
	{super.position.setY((int)(super.position.getY() + verticalVelocity * deltaTime)); }
	
	public void setDeltaTime(double deltaTime)
	{ this.deltaTime = deltaTime; } 
}
