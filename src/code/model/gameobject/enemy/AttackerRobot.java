package code.model.gameobject.enemy;

//inproject import
import code.model.gameobject.MovingObject;
import code.model.utils.Point;

public abstract class AttackerRobot extends Enemy
{
	private static final long serialVersionUID = 1L;
	
	private boolean isAttacking;
	
	public abstract class Attack extends MovingObject
	{	
		public Attack(Point position, int width, int height)
		{
			super(position, width, height);
			setDirection(AttackerRobot.this.getDirection());
		}
	}
	
	public AttackerRobot(Point position, int width, int height)
	{ 
		super(position, width, height);
		isAttacking = false;
	}
	
	protected abstract Attack produceAttack();
	
	protected void setAttackingState(boolean isAttacking)
	{ this.isAttacking = isAttacking; }
	
	public boolean isAttacking()
	{ return isAttacking; }
}
