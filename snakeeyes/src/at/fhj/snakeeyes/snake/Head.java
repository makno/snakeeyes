package at.fhj.snakeeyes.snake;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import at.fhj.snakeeyes.SnakeEyes;

public class Head extends Member{
	ImageIcon imgUp;
	ImageIcon imgDown;
	ImageIcon imgLeft;
	ImageIcon imgRight;
	ImageIcon imgUp_dead;
	ImageIcon imgDown_dead;
	ImageIcon imgLeft_dead;
	ImageIcon imgRight_dead;
	boolean isDead = false;
	
	public Head(Point point) {
		super(point);
		imgUp = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/worm_head_up.png"),"Tail connection to upper tile");
		imgDown = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/worm_head_down.png"),"Tail connection to tile below");
		imgLeft = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/worm_head_left.png"),"Tail connection to left tile");
		imgRight = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/worm_head_right.png"),"Tail connection to right tile");
		imgUp_dead = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/worm_headdead_up.png"),"Tail connection to upper tile");
		imgDown_dead = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/worm_headdead_down.png"),"Tail connection to tile below");
		imgLeft_dead = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/worm_headdead_left.png"),"Tail connection to left tile");
		imgRight_dead = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/worm_headdead_right.png"),"Tail connection to right tile");
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;
		
		// Head
		Image imgHead = null;
		switch(this.heading){
			case UP:
				this.imgCurrent = (!this.isDead)?this.imgUp.getImage():this.imgUp_dead.getImage();
				break;
			case DOWN:
				this.imgCurrent = (!this.isDead)?this.imgDown.getImage():this.imgDown_dead.getImage();
				break;
			case LEFT:
				this.imgCurrent = (!this.isDead)?this.imgLeft.getImage():this.imgLeft_dead.getImage();
				break;
			case RIGHT:
				this.imgCurrent = (!this.isDead)?this.imgRight.getImage():this.imgRight_dead.getImage();
				break;
			default:
		}
		g2.drawImage(this.imgCurrent,this.point.x,this.point.y,null);
	}
	
}
