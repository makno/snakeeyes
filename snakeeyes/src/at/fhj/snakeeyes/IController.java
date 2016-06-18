package at.fhj.snakeeyes;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;

public interface IController {
	
	public static final int SPEED = 20;
	public static final int SCORE_SPEED_CHANGE =  50;
	public static final int INITIAL_NUMBER_PLAYERS = 2;
	public static final Point[] STARTINGPOINTS =  new Point[]{ new Point(80,260), new Point(720,260), new Point(400,260)};
	public static final KeyMap[] KEYMAPS = new KeyMap[]{new KeyMap(KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT),new KeyMap(KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_A,KeyEvent.VK_D), new KeyMap(KeyEvent.VK_Z,KeyEvent.VK_H,KeyEvent.VK_G,KeyEvent.VK_J)};
	public static final Color[] COLORS = new Color[]{Color.green, Color.blue, Color.yellow};
	public static final int MAX_MUSHROOMS = 10;
	public static final int MAX_ROCKS = 10;

	
	public enum GAMEOVER{
		SELFKILL,
		ROCK,
		WALL,
		SNAKE_VS_SNAKE,
		NONE;
		
		public String getMessage(){
			switch(this){
				case SELFKILL: return "You have bitten yourself! Bummer!";
				case ROCK: return "You can't eat rocks- you know that. Don't you?";
				case WALL: return "Hitting the wall causes brain damage!";
				case SNAKE_VS_SNAKE: return "Snake %s has munched into snake %s. Snakes are poisonous- Therefore %s fails!";
				case NONE: return "No - this does not mean \"Game Over\"!";
				default: return "You died somehow. There is obviously no case for your death....";
			}
		}
	}
	
	public enum MOVE{
		LEFT,
		RIGHT,
		UP,
		DOWN,
		NONE;
		
		public int getKeyEvent(){
			switch (this){
				case LEFT: return KeyEvent.VK_LEFT;	
				case RIGHT: return KeyEvent.VK_RIGHT;		
				case UP: return KeyEvent.VK_UP;		
				case DOWN: return KeyEvent.VK_DOWN;		
				default: return -1;
			}
		}
		
		public MOVE getOpposite(){
			switch (this){
				case LEFT: return MOVE.RIGHT;	
				case RIGHT: return MOVE.LEFT;		
				case UP: return MOVE.DOWN;		
				case DOWN: return MOVE.UP;		
				default: return MOVE.NONE;
			}
		}
	}
	
}
