package code.controller;

//graphics import
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
//model import
import code.model.context.GameContext;
import code.model.context.GameContext.UserInput;
import code.model.gameobjects.Player;
import code.model.room.PresettedRoom;
import code.model.room.Room;
import code.model.Point;
//view import
import code.view.Renderer;

public class JImpossibleMission 
{
	private static String WINDOW_TITLE = "Impossible mission";
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame(WINDOW_TITLE);
		
		Player player = new Player(new Point(64, 64));
		Renderer renderer = new Renderer();
		GameContext context = new GameContext(player, PresettedRoom.getRoom(Room.ExitLayout.ONLEFTANDRIGHT, 1));
		
		bindAllKey(renderer, context);
		
		frame.add(renderer);
		renderer.setBackground(Color.black);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		context.setListener(renderer);
		new GameLoop(context, renderer).start();
	}
	
	private static void bindAllKey(Renderer renderer, GameContext context)
	{
		bindKey(renderer, context, "LEFT_PRESSED",  KeyEvent.VK_LEFT,  true);
		bindKey(renderer, context, "RIGHT_PRESSED", KeyEvent.VK_RIGHT, true);
		bindKey(renderer, context, "UP_PRESSED",    KeyEvent.VK_UP,    true);
		bindKey(renderer, context, "DOWN_PRESSED",  KeyEvent.VK_DOWN,  true);
		bindKey(renderer, context, "JUMP_PRESSED",  KeyEvent.VK_SPACE, true);
		
		bindKey(renderer, context, "LEFT_RELEASED",  KeyEvent.VK_LEFT,  false);
		bindKey(renderer, context, "RIGHT_RELEASED", KeyEvent.VK_RIGHT, false);
		bindKey(renderer, context, "UP_RELEASED",    KeyEvent.VK_UP,    false);
		bindKey(renderer, context, "DOWN_RELEASED",  KeyEvent.VK_DOWN,  false);
		bindKey(renderer, context, "JUMP_RELEASED",  KeyEvent.VK_SPACE,  false);
	}
	
	private static void bindKey(Renderer renderer, GameContext context, String keyName, int keyCode , boolean pressed)
	{
		InputMap inputMap = renderer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = renderer.getActionMap();
		
		inputMap.put(KeyStroke.getKeyStroke(keyCode, 0, !pressed), keyName);
		actionMap.put(keyName, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				switch(keyCode)
				{
					case KeyEvent.VK_LEFT  -> context.setUserInput(UserInput.LEFT,  pressed);
					case KeyEvent.VK_RIGHT -> context.setUserInput(UserInput.RIGHT, pressed);
					case KeyEvent.VK_UP    -> context.setUserInput(UserInput.UP,    pressed);
					case KeyEvent.VK_DOWN  -> context.setUserInput(UserInput.DOWN,  pressed);
					case KeyEvent.VK_SPACE -> context.setUserInput(UserInput.JUMP,  pressed);
				}
			}	
		});
	}
}
