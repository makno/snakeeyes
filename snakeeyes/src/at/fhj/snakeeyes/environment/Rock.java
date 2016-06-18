package at.fhj.snakeeyes.environment;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import at.fhj.snakeeyes.SnakeEyes;
import at.fhj.snakeeyes.environment.Mushroom.TYPE;

public class Rock extends JComponent{
	private ImageIcon imgCurrent;
	private ImageIcon[] rocks = new ImageIcon[]{
			new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/rock.png"),"A Felsn"),
			new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/rock2.png"),"A Felsn 2"),
			new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/rock3.png"),"A Felsn 3"),
	};
	public Point point;
	
	public Rock(Point point) {
		super();
		this.point = point;
		Random randomGenerator = new Random();
		this.imgCurrent = this.rocks[randomGenerator.nextInt(this.rocks.length-1)];
	}
	
	@Override
	public void paintComponent(Graphics g) {		
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(this.imgCurrent.getImage(),this.point.x,this.point.y,null);
	}
	
}
