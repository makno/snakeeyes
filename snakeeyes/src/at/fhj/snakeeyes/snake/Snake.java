package at.fhj.snakeeyes.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.ShortLookupTable;
import java.util.Vector;
import javax.swing.JComponent;

import at.fhj.snakeeyes.IController.MOVE;
import at.fhj.snakeeyes.KeyMap;

public class Snake extends JComponent{
	
	public static final int SIARTSIZE = 3; // Initial amount of snake members upon start
	public static final int STEPSIZE = 1; // Pixels to move in one animation frame
	
	public static final int SPEED = 20;
	public static final int SPEEDSTEP = 1;
	
	public Point point;	
	
	private Vector<TurningPoint> turningpoints;
	
	private Head head;
	private Vector<Member> members;
	private Member growingmember = null;
	private MOVE moveSoFar = MOVE.NONE;
	private int growmembers = 0;
	private int score = 0;
	private int speed = 0;
	private boolean isDead = false;
	
	private KeyMap keymap = null;
	private MOVE movement = MOVE.UP;
	private Color color = Color.green;
	
	boolean isInitialized;
	
	private int sizeAmount = 0; // movement temporary helper variable
	
	public Snake(Point point,KeyMap keymap, Color color){
		this(point.x,point.y, keymap, color);
	}
	
	public Snake(int x, int y, KeyMap keymap, Color color){
		this.point = new Point(x, y);
		this.keymap = keymap;
		this.color = color;
		this.initialize();
	}		
	private void initialize(){
		this.turningpoints = new Vector<TurningPoint>();
		this.head = new Head(this.point);
		this.head.heading = MOVE.UP;
		this.head.origin = MOVE.UP.getOpposite();
		this.add(this.head);
		this.members = new Vector<Member>();
		for(int i=1; i<= Snake.SIARTSIZE; i++){	
			Member m = new Member(new Point(this.point.x,this.point.y + i * Member.TILESIZE));
			m.heading = MOVE.UP;
			m.origin = MOVE.UP.getOpposite();
			this.members.add(m);
			this.add(m);
		}
		this.speed = 0;
		this.score = 0;
		this.isInitialized = true;
	}
	
	/**
	 * Returns the head of the snake
	 * @return
	 */
	public Head getHead(){
		return this.head;
	}
	
	/**
	 * Members
	 * @return
	 */
	public Vector<Member> getMembers(){
		return this.members;
	}
	
	/**
	 * Move snake according to movement
	 */
	public void move(){
		this.move(this.movement);
	}
	
	/**
	 * Set color of snake
	 * @param color
	 */
	public void setColor(Color color){
		this.color = color;
	}
	
	/**
	 * Set direction of movement
	 * @param movement
	 */
	public void setMovement(MOVE movement) {
		this.movement = movement;
	}
	
	/**
	 * Adds a certain score for the snake
	 * @param score
	 */
	public void addScore(int score){
		this.score += score;
	}
	
	/**
	 * Says how many members should grow after a bite of mushroom
	 * @param growmembers
	 */
	public void growMembers(int growmembers){
		this.growmembers += growmembers;
	}
	
	/**
	 * Main movement logic!
	 * @param moveTo Direction where to move next
	 */
	public void move(MOVE moveTo){
		
		// Stop moving if none direction given
		if(moveTo == MOVE.NONE)
			return;
		
		// Initialized necessary
		if(!this.isInitialized)
			return;
		
		//////////
		// Head //
		//////////
		// If snake can turn ...
		if(this.sizeAmount%Member.TILESIZE == 0){
			this.sizeAmount = 0;
			this.moveSoFar = moveTo;
			this.turningpoints.add(0,new TurningPoint(new Point(this.head.point.x, this.head.point.y),moveTo,this.head.origin));
		// If movement path is less than size of snake member - avoid turning!
		}else{
			moveTo = this.moveSoFar;
		}
		
		/////////////////////
		// Growing Members //
		/////////////////////
		
		if(this.growmembers>0 && this.growingmember==null){
			this.growingmember = new Member(new Point(this.members.get(0).point.x,this.members.get(0).point.y));
			this.growingmember.heading = this.members.get(0).heading;
			this.growingmember.origin = this.members.get(0).origin;
			this.growingmember.setPointTurning(new TurningPoint(new Point(this.head.point.x,this.head.point.y), this.head.heading, this.head.origin));
		}
		
		if(this.growingmember!=null){
			for(int j=0; j < this.turningpoints.size(); j++){
				TurningPoint tp = this.turningpoints.get(j);
				if(this.growingmember.point.x == tp.point.x && this.growingmember.point.y == tp.point.y){
					this.growingmember.heading = tp.heading;
					this.growingmember.origin = tp.origin;
				}
			}
			
			switch(this.growingmember.heading){
				case UP: this.growingmember.moveUp(STEPSIZE); break;
				case DOWN: this.growingmember.moveDown(STEPSIZE); break;
				case LEFT: this.growingmember.moveLeft(STEPSIZE); break;
				case RIGHT: this.growingmember.moveRight(STEPSIZE); break;
			}
			
			if(this.growingmember.point.x == this.growingmember.getPointTurning().point.x && this.growingmember.point.y == this.growingmember.getPointTurning().point.y){
				this.members.add(0,this.growingmember);
				this.growingmember=null;
				this.growmembers--;
			}
			
		}else{
			for(int i=0;i<this.members.size();i++){
				
				Member member = this.members.get(i);
				
				for(int j=0; j < this.turningpoints.size(); j++){
					TurningPoint tp = this.turningpoints.get(j);
					if(member.point.x == tp.point.x && member.point.y == tp.point.y){
						member.heading = tp.heading;
						member.origin = tp.origin;
						if(i==this.members.size()-1){
							this.turningpoints.remove(tp);
						}
					}
				}
				
				switch(member.heading){
					case UP: member.moveUp(STEPSIZE); break;
					case DOWN: member.moveDown(STEPSIZE); break;
					case LEFT: member.moveLeft(STEPSIZE); break;
					case RIGHT: member.moveRight(STEPSIZE); break;
				}
			
			}
		}
			
		this.head.origin = this.head.heading.getOpposite();
		
		// Head movement
		int dX = 0; // Delta X
		int dY = 0; // Delta Y
		switch(moveTo){
			case UP:
				this.head.moveUp(STEPSIZE);
				this.head.heading = MOVE.UP; 
				break;
			case DOWN:
				this.head.moveDown(STEPSIZE);
				this.head.heading = MOVE.DOWN;
				break;
			case LEFT:
				this.head.moveLeft(STEPSIZE);
				this.head.heading = MOVE.LEFT;
				break;
			case RIGHT:
				this.head.moveRight(STEPSIZE);
				this.head.heading = MOVE.RIGHT;
				break;
			default:
		}
			
		this.sizeAmount += Snake.STEPSIZE;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Check if initialized properly
		if(!isInitialized)
			return;
		
		// Body
		if(this.growingmember!=null){
			this.growingmember.paintComponent(g);
		}
		boolean isTail = false;
		for(int i=0; i < this.members.size(); i++){
			// Last one is tail!
			if(i==this.members.size()-1){
				isTail=true;
			}
			this.members.get(i).isTail = isTail;
			this.members.get(i).paintComponent(g);
		}
		
		for(int j=0; j < this.turningpoints.size(); j++){
			this.turningpoints.get(j).paintComponent(g);
		}
		
		// Head
		this.head.paintComponent(g);
		
//		Color col = g.getColor();
//		g.setColor(Color.blue);
//		g.drawOval(this.pnt.x-2,this.pnt.y-2, 4, 4);
//		g.setColor(col);
	}
	
	public KeyMap getKeyMap(){
		return this.keymap;
	}
	
	public boolean checkSelfCollission(){
		for(Member member : this.members){
			if(member.point.x == this.head.point.x && member.point.y == this.head.point.y) {
				this.setDead(true);
				return true;
			}
		}
		this.setDead(false);
		return false;
	}
	
	public boolean hasCollissionWithHead(Point point){
		if(this.head.point.x == point.x && this.head.point.y == point.y)
			return true;
		else
			return false;
	}
	
	public boolean hasCollissionWithSnake(Point point){
		if(this.head.point.x == point.x && this.head.point.y == point.y)
			return true;
		for(Member member : this.members){
			if(member.point.x == point.x && member.point.y == point.y) {
				return true;
			}
		}
		return false;
	}
	
	Point pnt = new Point(0,0);
	public boolean hasCollissionWithSnake(Snake snake){
		this.pnt = new Point(this.head.point.x + (int)Member.TILESIZE/2, this.head.point.y + (int)Member.TILESIZE/2 );
		if(snake.head.getBounds().contains(pnt))
			return true;
		for(Member member : snake.getMembers()){
			if(member.getBounds().contains(pnt))
				return true;
		}
		return false;
	}
	
	public boolean hasCollissionWithObject(Point point){
		if(this.head.contains(point))
			return true;
		for(Member member : this.members){
			if(member.contains(point)) {
				return true;
			}
		}
		return false;
	}
	
	public void setDead(boolean isDead){
		this.isDead = isDead;
		this.head.isDead = isDead;
	}
	
}





