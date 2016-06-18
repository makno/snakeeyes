package at.fhj.snakeeyes;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import at.fhj.snakeeyes.environment.Mushroom;
import at.fhj.snakeeyes.environment.Playground;
import at.fhj.snakeeyes.environment.Rock;
import at.fhj.snakeeyes.snake.Snake;

public class Controller implements IController, Runnable, KeyListener {
	
	private Thread threadLoop;
	private int factorSpeed = 0;
	boolean isInitialized = false;
	boolean isFirstRun = true;
	
	private JFrame frame;
	private Playground playground;
	
	private Vector<Snake> snakes;
	private Vector<Mushroom> mushrooms;
	private Vector<Rock> rocks;
	private Vector<Rock> rockwall;
	
	private int numberPlayers = INITIAL_NUMBER_PLAYERS;
	
	private GAMEOVER gameovertype = GAMEOVER.NONE;

	public Controller(JFrame frame){
		this.frame = frame;
		this.frame.setTitle("SnakeEyes");
		this.playground = new Playground(this);
		this.playground.setBounds(new Rectangle(760,560));
		this.initGame();
	}
	
	private void initGame(){
		this.isInitialized = false;
		// common variables for game play
		this.gameovertype = GAMEOVER.NONE;
		this.factorSpeed=SPEED;
		// initializing object vectors for mushrooms, rocks and rocks building walls
		this.mushrooms = new Vector<Mushroom>();
		this.rockwall = new Vector<Rock>();
		this.rocks = new Vector<Rock>();
		this.snakes = new Vector<Snake>();
		// introducing snake(s) according to player number
		for(int i=0; i< this.numberPlayers;i++){
			this.snakes.add(new Snake(STARTINGPOINTS[i],KEYMAPS[i], COLORS[i]));
			this.snakes.get(i).setMovement(MOVE.NONE);
		}
		// place objects after snakes
		this.placeRockwall();
		this.placeMushrooms();
		this.placeRocks();
		this.isInitialized = true;
	}
	
	public Playground getPlayground(){
		return this.playground;
	}

	public Snake getSnake(int index){
		return this.snakes.get(index);
	}
	
	public Vector<Snake> getSnakes(){
		return this.snakes;
	}
	
	public Vector<Mushroom> getMushrooms(){
		return this.mushrooms;
	}
	
	public Vector<Rock> getRocks(){
		return this.rocks;
	}

	public Vector<Rock> getRockwall(){
		return this.rockwall;
	}
	
	private void moveSnakes(){
		for(Snake snake : this.snakes)
			snake.move();
	}
	
	private synchronized boolean checkCollission(){
		for(Snake snake : this.snakes){
			if(snake.checkSelfCollission()){
				this.gameovertype = GAMEOVER.SELFKILL;
				return true;
			}
			for(Snake snakeCol : this.snakes){
				if(snake!=snakeCol){
					if(snake.hasCollissionWithSnake(snakeCol)){
						this.gameovertype = GAMEOVER.SNAKE_VS_SNAKE;
						return true;
					}
				}
			}
		}
		for(int i=0; i < this.mushrooms.size(); i++){
			Mushroom mush = this.mushrooms.get(i);
			for(Snake snake :  this.snakes){
				if(snake.hasCollissionWithHead(mush.point)){
					this.mushrooms.remove(mush);
					snake.growMembers(mush.type.getGrowth());
					snake.addScore(mush.type.getScore());
				}
			}
		}
		for(int i=0; i < this.rocks.size(); i++){
			Rock rock = this.rocks.get(i);
			for(Snake snake :  this.snakes){
				if(snake.hasCollissionWithHead(rock.point)){
					snake.setDead(true);
					this.gameovertype = GAMEOVER.ROCK;
					return true;
				}
			}
		}
		for(int i=0; i < this.rockwall.size(); i++){
			Rock rock = this.rockwall.get(i);
			for(Snake snake :  this.snakes){
				if(snake.hasCollissionWithHead(rock.point)){
					snake.setDead(true);
					this.gameovertype = GAMEOVER.WALL;
					return true;
				}
			}
		}
		return false;
	}
	
	private void replaceMushroom(){
		if(this.mushrooms.size() < Controller.MAX_MUSHROOMS){
			this.mushrooms.add(new Mushroom(this.getPositionForObject()));
		}
	}
	
	private void placeMushrooms(){
		for(int i=0; i < Controller.MAX_MUSHROOMS; i++){
			this.mushrooms.add(new Mushroom(this.getPositionForObject()));
		}
	}
	
	private void placeRocks(){
		for(int i=0; i < Controller.MAX_ROCKS; i++){
			this.rocks.add(new Rock(this.getPositionForObject()));
		}
	}
	
	private void placeRockwall(){
		for(int i=0; i<this.playground.getWidth()+21;i+=20){
			this.rockwall.add(new Rock(new Point(i,0)));
			this.rockwall.add(new Rock(new Point(i,560)));
		}
		for(int j=0; j<this.playground.getHeight();j+=20){
			this.rockwall.add(new Rock(new Point(0,j)));
			this.rockwall.add(new Rock(new Point(780,j)));
		}
	}
	
	private void writeStatus(){
		this.frame.setTitle("SnakeEyes Score:");
	}
	
	public void startLoop(){
		this.threadLoop = new Thread(this);
		this.threadLoop.start();
	}
	
	private void loop(){
		this.moveSnakes();
		this.checkCollission();
		this.replaceMushroom();
		this.writeStatus();
		this.playground.repaint();
		if(this.gameovertype != GAMEOVER.NONE){
			this.initGameOver();
		}
	}

	@Override
	public void run() {
		if(this.threadLoop==null)
			return;
		
			for(;;){
		
				this.loop();
				
				if(isFirstRun){
					this.setPause(true);
					isFirstRun = false;
				}
			
				try {
					Thread.sleep(this.getSpeed());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
	}

	@Override
	public void keyPressed(KeyEvent ke) {
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		
		for(Snake snake : this.snakes){
			if(ke.getKeyCode() == snake.getKeyMap().getUpKey()){
				snake.setMovement(MOVE.UP);
			}
			if(ke.getKeyCode() == snake.getKeyMap().getDownKey()){
				snake.setMovement(MOVE.DOWN);
			}
			if(ke.getKeyCode() == snake.getKeyMap().getLeftKey()){
				snake.setMovement(MOVE.LEFT);
			}
			if(ke.getKeyCode() == snake.getKeyMap().getRightKey()){
				snake.setMovement(MOVE.RIGHT); 
			}
		}
		
		// Common keys
		switch(ke.getKeyCode()){
			case KeyEvent.VK_ESCAPE:
				this.setPause(true);
				break;
			case KeyEvent.VK_1:
				this.numberPlayers=1;
				this.isFirstRun = true;
				this.initGame();
				break;
			case KeyEvent.VK_2:
				this.numberPlayers=2;
				this.isFirstRun = true;
				this.initGame();
				break;
			case KeyEvent.VK_3:
				this.numberPlayers=3;
				this.isFirstRun = true;
				this.initGame();
				break;
			default:
		}
	}

	@Override
	public void keyTyped(KeyEvent ke) {
	}
	

	
	public int getSpeed(){
		return this.factorSpeed;
	}
	
	private synchronized Point getPositionForObject(){
		Point point;
		Random randomGenerator = new Random();	
		boolean hasCollissionCurrently = false;
		int iCollissionCounter = 0;
		
		do{
		 hasCollissionCurrently = false;
		 int randomX = randomGenerator.nextInt((int)this.playground.getWidth()/20);
		 int randomY = randomGenerator.nextInt((int)this.playground.getHeight()/20);
		 point = new Point(randomX*20,randomY*20);
		 
		 for(Snake snake : this.snakes){
			 if(snake.hasCollissionWithSnake(point))
				 hasCollissionCurrently = true;
		 }
			 
		 if(this.hasCollissionWithMushrooms(point) || this.hasCollissionWithRocks(point) || this.hasCollissionWithRockwall(point)){
			 hasCollissionCurrently = true;
		 }
		 
		 if(hasCollissionCurrently)
			 iCollissionCounter++;
		 
		}while(hasCollissionCurrently);
		
		return point;
	}
	
	private boolean hasCollissionWithMushrooms(Point point){
		for(Mushroom mush : this.mushrooms)
			if(mush.point.x == point.x && mush.point.y == point.y)
				return true;
		return false;
	}
	
	private boolean hasCollissionWithRocks(Point point){
		for(Rock rock : this.rocks)
			if(rock.point.x == point.x && rock.point.y == point.y)
				return true;
		return false;
	}
	
	private boolean hasCollissionWithRockwall(Point point){
		for(Rock rock : this.rockwall)
			if(rock.point.x == point.x && rock.point.y == point.y)
				return true;
		return false;
	}
	
	private ImageIcon icon = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/about.png"),"Info");;
	private void setPause(boolean hasPause){
		if(hasPause){
			for(Snake snake : this.snakes){
				snake.setMovement(MOVE.NONE);
			}
			JOptionPane.showMessageDialog(
					this.frame,
					null, null,
					JOptionPane.INFORMATION_MESSAGE,
					this.icon);
		}
	}
	
	private void initGameOver(){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(Snake snake : this.snakes){
			snake.setMovement(MOVE.NONE);
		}
		int iRet = JOptionPane.showConfirmDialog(this.frame, gameovertype.getMessage() + " Do you want to quit or try again?", "Game Over", JOptionPane.YES_NO_OPTION);
		if(iRet==JOptionPane.YES_OPTION){
			this.initGame();
		}else{
			System.exit(0);
		}
	}	
}
