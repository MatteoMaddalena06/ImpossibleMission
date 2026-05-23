package code.model.gameobjects.enemy;

import code.model.Point;
import code.model.gameobjects.MovingObject;

/** Classe che modella i robot che attaccano */
public abstract class AttackerRobot extends Enemy
{
	/** Indica se il robot sta attaccando */
	private boolean isAttacking;
	
	/** Classe per modellare gli attacchi dei {@link AttackerRobot} */
	public abstract class Attack extends MovingObject
	{	
		/** Robot che ha lanciate l'attacco */
		private AttackerRobot attacker;
		/** Tipo di attacco */
		private Type type;
		
		/** Enumerazione per i tipi di attacchi dei robot*/
		public enum Type 
		{ ISTANT, PROLONGED }
		
		/**
		 * Costruisce la classe
		 * @param position
		 * la posizione originale
		 * @param width
		 * la larghezza
		 * @param height
		 * l'altezza
		 */
		public Attack(Type type, Point position, int width, int height)
		{
			super(position, width, height);
			attacker = AttackerRobot.this;
			this.type = type;
			setDirection(AttackerRobot.this.getDirection());
		}
		
		/**
		 * Restituisce il robot che ha lanciato l'attacco
		 * @return
		 * il robot che ha lanciato l'attacco
		 */
		public AttackerRobot getAttacker()
		{ return attacker; }
		
		/**
		 * Restituisce il tipo di attacco
		 * @return
		 * il tipo di attacco
		 */
		public Type getType()
		{ return type; }
	}
	
	/**
	 * Costruisce la classe
	 * @param position
	 * la posizione originale
	 * @param width
	 * la larghezza
	 * @param height
	 * l'altezza
	 */
	public AttackerRobot(Point position, int width, int height)
	{ 
		super(position, width, height);
		isAttacking = false;
	}
	
	/** 
	 * Produce l'attacco 
	 * @return
	 * l'attacco
	 */
	protected abstract Attack produceAttack();
	
	/**
	 * Imposta lo stato di attaco del robot
	 * @param isAttacking
	 * lo stato di attacco
	 */
	protected void setAttackingState(boolean isAttacking)
	{ this.isAttacking = isAttacking; }
	
	/**
	 * Dice se il robot sta attaccando
	 * @return
	 * lo stato di {@link isAttacking}
	 */
	public boolean isAttacking()
	{ return isAttacking; }
}
