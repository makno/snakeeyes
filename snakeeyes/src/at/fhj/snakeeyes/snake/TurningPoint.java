package at.fhj.snakeeyes.snake;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import at.fhj.snakeeyes.IController.MOVE;
import at.fhj.snakeeyes.SnakeEyes;

public class TurningPoint extends JComponent{
	Point point;
	MOVE heading;
	MOVE origin;
	
	Image imgCurrent = null;
	
	ImageIcon imgLeftUp = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/worm_leftup.png"),"Connection to left and up tile");
	ImageIcon imgRightUp = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/worm_rightup.png"),"Connection to right and up tile");
	ImageIcon imgDownLeft = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/worm_downleft.png"),"Connection to left and down  tile");
	ImageIcon imgDownRight = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/worm_downright.png"),"Connection to right and down tile");
	
	public TurningPoint(Point point, MOVE heading, MOVE origin){
		this.point = point;
		this.heading = heading;
		this.origin = origin;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		switch(this.heading){
			case UP:
				switch(this.origin){
					case LEFT:
						this.imgCurrent = imgLeftUp.getImage();
						break;
					case RIGHT:
						this.imgCurrent = this.imgRightUp.getImage();
						break;
					case DOWN:
					case UP:
					case NONE:
					default:
				}
				break;
			case DOWN:
				switch(this.origin){
					case LEFT:
						this.imgCurrent = this.imgDownLeft.getImage();
						break;
					case RIGHT:
						this.imgCurrent = this.imgDownRight.getImage();
						break;
					case DOWN:
					case UP:
					case NONE:
					default:
				}
				break;
			case LEFT:
				switch(this.origin){
					case UP:
						this.imgCurrent = this.imgLeftUp.getImage();
						break;
					case DOWN:
						this.imgCurrent = this.imgDownLeft.getImage();
						break;
					case LEFT:
					case RIGHT:
					case NONE:
					default:
				}
				break;
			case RIGHT:
				switch(this.origin){
					case UP:
						this.imgCurrent = this.imgRightUp.getImage();
						break;
					case DOWN:
						this.imgCurrent = this.imgDownRight.getImage();
						break;
					case LEFT:
					case RIGHT:
					case NONE:
					default:
				}
				break;
			default:
		}
		
		if(this.imgCurrent!=null)
			g2.drawImage(this.imgCurrent,this.point.x,this.point.y,null);
		
	}
	
	
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	public MOVE getHeading() {
		return heading;
	}
	public void setHeading(MOVE heading) {
		this.heading = heading;
	}
	public MOVE getOrigin() {
		return origin;
	}
	public void setOrigin(MOVE origin) {
		this.origin = origin;
	}
}