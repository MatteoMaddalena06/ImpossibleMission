package code.view;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import javax.swing.*;

import code.model.gameobject.*;
import code.model.room.*;
import code.model.utils.GameContext;
import code.model.utils.Point;
import code.model.world.GameWorld;
import code.model.room.RoomMap;

import java.util.List;

public class Test 
{
	private static GameWorld world = GameWorld.randomGeneration();
	private static int i = 0;
	private static List<Room> rooms = Arrays.stream(world.getWorldMatrix()).flatMap(e -> Arrays.stream(e)).filter(r -> r != null).toList();
	
	public static void main(String[] args)
	{
		GameWorld.randomGeneration();
		GameContext context = new GameContext(new Player(new Point(1*32, 2*32)), rooms.get(0));
		
		JFrame frame = new JFrame("Test");
		Renderer renderer = new Renderer(context, 40*RoomMap.TILE_SIZE, 25*RoomMap.TILE_SIZE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(renderer);
		frame.pack();
		frame.setVisible(true);
		
		bindKey(renderer, context, "pressed LEFT", KeyEvent.VK_LEFT, true);
        bindKey(renderer, context, "pressed RIGHT", KeyEvent.VK_RIGHT, true);
        bindKey(renderer, context, "pressed SPACE", KeyEvent.VK_SPACE, true);
        bindKey(renderer, context, "pressed UP", KeyEvent.VK_UP, true);
        bindKey(renderer, context, "pressed DOWN", KeyEvent.VK_DOWN, true);
        bindKey(renderer, context, "A DOWN", KeyEvent.VK_A, true);
        bindKey(renderer, context, "B DOWN", KeyEvent.VK_B, true);
        bindKey(renderer, context, "E DOWN", KeyEvent.VK_E, true);
        bindKey(renderer, context, "N DOWN", KeyEvent.VK_N, true);

        bindKey(renderer, context, "released LEFT", KeyEvent.VK_LEFT, false);
        bindKey(renderer, context, "released RIGHT", KeyEvent.VK_RIGHT, false);
        bindKey(renderer, context, "released SPACE", KeyEvent.VK_SPACE, false);
        bindKey(renderer, context, "released  UP", KeyEvent.VK_UP, false);
        bindKey(renderer, context, "released DOWN", KeyEvent.VK_DOWN, false);
        bindKey(renderer, context, "A UP", KeyEvent.VK_A, false);
        bindKey(renderer, context, "B UP", KeyEvent.VK_B, false);
        bindKey(renderer, context, "E UP", KeyEvent.VK_E, false);
        
        new Thread(new GameLoop(context, renderer)).start();
	}
	
	private static void bindKey(Renderer tester, GameContext context, String name, int keyCode, boolean pressed) 
	{
        InputMap im = tester.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = tester.getActionMap();

        String actionKey = name;

        if (pressed)
            im.put(KeyStroke.getKeyStroke(keyCode, 0, false), actionKey);
        else
            im.put(KeyStroke.getKeyStroke(keyCode, 0, true), actionKey);

        am.put(actionKey, new AbstractAction() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {

                switch (keyCode) {
                    case KeyEvent.VK_LEFT  -> context.setUserInput(GameContext.UserInput.LEFT, pressed);
                    case KeyEvent.VK_RIGHT -> context.setUserInput(GameContext.UserInput.RIGHT, pressed);
                    case KeyEvent.VK_SPACE -> context.setUserInput(GameContext.UserInput.JUMP, pressed);
                    case KeyEvent.VK_UP    -> context.setUserInput(GameContext.UserInput.UP, pressed); 
                    case KeyEvent.VK_DOWN  -> context.setUserInput(GameContext.UserInput.DOWN, pressed);
                    case KeyEvent.VK_A     -> context.setUserInput(GameContext.UserInput.A_KEY, pressed);
                    case KeyEvent.VK_B     -> context.setUserInput(GameContext.UserInput.B_KEY, pressed);
                    case KeyEvent.VK_E     -> context.setUserInput(GameContext.UserInput.E_KEY, pressed);
                    case KeyEvent.VK_N     -> context.setCurrentRoom(rooms.get(++i % 30));
                }
            }
        });
	}
}
