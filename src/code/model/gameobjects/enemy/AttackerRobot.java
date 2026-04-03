package code.model.gameobjects.enemy;

import code.model.Point;
import code.model.gameobjects.MovingObject;

public abstract class AttackerRobot extends Enemy
{
	private boolean isAttacking;
	
	public abstract class Attack extends MovingObject
	{	
		private AttackerRobot attacker;
		
		public Attack(Point position, int width, int height)
		{
			super(position, width, height);
			attacker = AttackerRobot.this;
			setDirection(AttackerRobot.this.getDirection());
		}
		
		public AttackerRobot getAttacker()
		{ return attacker; }
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
