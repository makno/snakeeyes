package at.fhj.snakeeyes;

import java.util.HashMap;

public class KeyMap implements IController{
	
	private HashMap<MOVE, Integer> movements;
	
	public boolean isInitialized = false;
	
	public KeyMap(){
		this.initialize();
	}
	
	public KeyMap(int keyevent_up, int keyevent_down, int keyevent_left, int keyevent_right){
		this.initialize();
		this.setDownKey(keyevent_down);
		this.setUpKey(keyevent_up);
		this.setLeftKey(keyevent_left);
		this.setRightKey(keyevent_right);
		this.checkInitialized();
	}

	private void initialize(){
		this.movements =  new HashMap<MOVE,Integer>();
		this.movements.put(MOVE.UP, -1);
		this.movements.put(MOVE.DOWN, -1);
		this.movements.put(MOVE.LEFT, -1);
		this.movements.put(MOVE.RIGHT, -1);
	}
	
	public void setUpKey(int key){
		this.movements.put(MOVE.UP, key);
	}
	
	public void setDownKey(int key){
		this.movements.put(MOVE.DOWN, key);
	}
	
	public void setLeftKey(int key){
		this.movements.put(MOVE.LEFT, key);
	}
	
	public void setRightKey(int key){
		this.movements.put(MOVE.RIGHT, key);
	}
	
	public int getUpKey(){
		return this.movements.get(MOVE.UP);
	}
	
	public int getDownKey(){
		return this.movements.get(MOVE.DOWN);
	}
	
	public int getLeftKey(){
		return this.movements.get(MOVE.LEFT);
	}
	public int getRightKey(){
		return this.movements.get(MOVE.RIGHT);
	}
	
	private boolean checkInitialized(){
		if(this.movements==null || this.movements.get(MOVE.UP)<0 || this.movements.get(MOVE.DOWN)<0 || this.movements.get(MOVE.LEFT)<0 || this.movements.get(MOVE.RIGHT)<0){
			this.isInitialized = false;
			return false;
		}
		this.isInitialized = true;
		return true;
	}
}
