package model.gameobject;

import java.util.Random;

import model.room.RoomMap;

public class Terminal extends GameObject
{

	public static final int TYPE = RoomMap.TERMINAL_ID;
	
	private TerminalEffect effect;
	private boolean isHacked;

	public Terminal(int x, int y, int w, int h) {
		super(new Point(x, y), w, h);
		this.isHacked = false;
		this.type = TYPE;
		assignRandomEffect();
	}

	private void assignRandomEffect(){
		Random rand = new Random();
		if(rand.nextBoolean()) this.effect = TerminalEffect.ROBOT_STOP;
		else this.effect = TerminalEffect.PLATFORM_RESET;
	}

	public enum TerminalEffect{
		NONE, ROBOT_STOP, PLATFORM_RESET
	}

	public TerminalEffect interact(){
		if(isHacked) return TerminalEffect.NONE;
		isHacked = true;
		return effect;
	}

	@Override
	public void update() {
	}

	public int getType() {
		return TYPE;
	}

	public TerminalEffect getEffect() {
		return effect;
	}

	public void setEffect(TerminalEffect effect) {
		this.effect = effect;
	}

	public boolean isHacked() {
		return isHacked;
	}

	public void setHacked(boolean isHacked) {
		this.isHacked = isHacked;
	}
	
}
