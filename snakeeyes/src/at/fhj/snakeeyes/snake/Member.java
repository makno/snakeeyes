package at.fhj.snakeeyes.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import at.fhj.snakeeyes.IController.MOVE;
import at.fhj.snakeeyes.SnakeEyes;

public class Member extends JComponent{
	
	public static final int TILESIZE = 20; // in px
	
	ImageIcon imgVertical;
	ImageIcon imgHorizontal;
	
	ImageIcon imgUp_tail;
	ImageIcon imgDown_tail;
	ImageIcon imgLeft_tail;
	ImageIcon imgRight_tail;
    
	Point point;
	MOVE heading;
	MOVE origin;
	
	TurningPoint pointTurning;
	
	MOVE moveHeadingBefore = MOVE.NONE;
	
	boolean isTail = false;
	
	public Image imgCurrent;
	
	public Member(Point point){
		this.point = point;
		this.setBounds(new Rectangle(Member.TILESIZE,Member.TILESIZE));
		this.heading = MOVE.UP;
		this.origin = MOVE.NONE;
		
		imgVertical = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/worm_ver.png"),"Vertical tile");
		imgHorizontal = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/worm_hor.png"),"Horizontal tile");
			
		imgUp_tail = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/worm_tail_up.png"),"Tail connection to upper tile");
		imgDown_tail = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/worm_tail_down.png"),"Tail connection to tile below");
		imgLeft_tail = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/worm_tail_left.png"),"Tail connection to left tile");
		imgRight_tail = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/worm_tail_right.png"),"Tail connection to right tile");
	}
	
	public void moveUp(int step){
		this.point.y -= step; 
		this.setLocation(this.point);
	}
	
	public void moveDown(int step){
		this.point.y += step; 
		this.setLocation(this.point);
	}
	
	public void moveLeft(int step){
		this.point.x -= step; 
		this.setLocation(this.point);
	}
	
	public void moveRight(int step){
		this.point.x += step; 
		this.setLocation(this.point);
	}
	
	public Point getPoint(){
		return this.point;
	}
	
	public TurningPoint getPointTurning() {
		return pointTurning;
	}

	public void setPointTurning(TurningPoint pointTurning) {
		this.pointTurning = pointTurning;
	}

	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;
		
		this.imgCurrent = null;
		
		if(this.isTail){
			switch(this.heading){
				case UP: this.imgCurrent = this.imgUp_tail.getImage(); break;
				case DOWN: this.imgCurrent = this.imgDown_tail.getImage(); break;
				case LEFT: this.imgCurrent = this.imgLeft_tail.getImage(); break;
				case RIGHT: this.imgCurrent = this.imgRight_tail.getImage(); break;
			}
		}
		
		if(this.imgCurrent==null){
			switch(this.heading){
				case UP:
				case DOWN:
					this.imgCurrent = this.imgVertical.getImage();
					break;
				case LEFT:
				case RIGHT:
					this.imgCurrent = this.imgHorizontal.getImage();
					break;
				default:
			}
		}
		
		g2.drawImage(this.imgCurrent,this.point.x,this.point.y,null);
//		Color colCur = g2.getColor();
//		g2.setColor(Color.blue);
//		g2.drawRect(this.getBounds().getLocation().x,this.getBounds().getLocation().y,this.getBounds().width,this.getBounds().height);
//		g2.setColor(colCur);

	}
	
}
