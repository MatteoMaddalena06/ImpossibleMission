package model.gameobject.enemy;

//inproject import
import model.gameobject.GameObject;
import model.utils.Point;

public abstract class AttackerRobot extends Enemy
{
	private static final long serialVersionUID = 1L;
	
	private boolean isAttacking;
	
	public AttackerRobot(Point position, int width, int height, double actionDelay)
	{ 
		super(position, width, height, actionDelay);
		isAttacking = false;
	}
	
	protected abstract GameObject produceAttack();
	
	protected void setAttackingState(boolean isAttacking)
	{ this.isAttacking = isAttacking; }
	
	public boolean isAttacking()
	{ return isAttacking; }
}
