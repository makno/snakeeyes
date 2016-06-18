package at.fhj.snakeeyes.environment;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import at.fhj.snakeeyes.SnakeEyes;

public class Mushroom extends JComponent{
	
	private ImageIcon imgCurrrent;
	public Point point;
	public TYPE type;
	
	public enum TYPE{
		NORMAL,
		MEDIUM,
		SUPERSIZE;
		
		ImageIcon imgNormal = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/mushroom.png"),"A anfoches Schwammerl");
		ImageIcon imgMedium = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/mushroom_medium.png"),"A medium Schwammerl");
		ImageIcon imgSupersize = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/mushroom_supersize.png"),"A Schwamm mit Pommes");
		
		public Image getImage(){
			switch(this){
				case MEDIUM: return imgMedium.getImage();
				case SUPERSIZE: return imgSupersize.getImage();
				case NORMAL: 
				default: return imgNormal.getImage();
			}
		}
		
		public int getGrowth(){
			switch(this){
				case MEDIUM: return 2;
				case SUPERSIZE: return 3;
				case NORMAL: 
				default: return 1;
			}
		}
		
		public int getScore(){
			switch(this){
				case MEDIUM: return 3;
				case SUPERSIZE: return 6;
				case NORMAL: 
				default: return 1;
			}
		}
		
	}
	
	public Mushroom(Point point) {
		super();
		this.point = point;
		Random randomGenerator = new Random();
		this.type = TYPE.values()[randomGenerator.nextInt(TYPE.values().length)];
	}
	
	@Override
	public void paintComponent(Graphics g) {		
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(this.type.getImage(),this.point.x,this.point.y,null);
	}
	
}
