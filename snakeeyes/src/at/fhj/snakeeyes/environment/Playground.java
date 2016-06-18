package at.fhj.snakeeyes.environment;

import javax.swing.JPanel;

import at.fhj.snakeeyes.Controller;
import at.fhj.snakeeyes.snake.Snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;



public class Playground extends JPanel {	

	private Controller controller;
	
	private BufferedImage imageBuffer;
	private Graphics graphicsBuffer;
	
	private Color bgColor =new Color(225,230,150);
	
	private boolean isInitialized = false;
	
	public Playground(Controller controller){
		super();
		this.controller = controller;
		this.initialize();
	}	
	
	private void initialize(){
		this.setBackground(Color.ORANGE);
		this.setLayout(null);
		this.setDoubleBuffered(true);
		this.imageBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent ce) {
				resizeComponent();
			}
		});
		this.isInitialized = true;
	}
		
	/**
	 * Paint that odd stuff
	 */
	public void paint(Graphics g){
		this.drawBackground();
		this.drawMushrooms();
		this.drawRocks();
		this.drawRockwall();
		this.drawSnakes();
		this.update(g);
	}
	
	/**
	 * Update with buffered image for smooth animation
	 */
	public void update(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage( this.imageBuffer, 0, 0, this );
		
	}
	
	private void drawSnakes(){
		for(Snake snake : this.controller.getSnakes()){
			snake.paintComponent(this.graphicsBuffer);
		}
	}
	
	private void drawMushrooms(){
		for(Mushroom mush : this.controller.getMushrooms()){
			mush.paintComponent(this.graphicsBuffer);
		}
	}

	private void drawRocks(){
		for(Rock rock : this.controller.getRocks()){
			rock.paintComponent(this.graphicsBuffer);
		}
	}	
	
	private void drawRockwall(){
		for(Rock rock : this.controller.getRockwall()){
			rock.paintComponent(this.graphicsBuffer);
		}
	}
	
	/**
	 * Draw the background image
	 */
	private void drawBackground(){
	    Rectangle bounds = this.getBounds();
		if(this.graphicsBuffer != null){
			Graphics2D g2 = (Graphics2D)this.graphicsBuffer;
			g2.setColor( this.bgColor );
			g2.fillRect(0, 0, bounds.width, bounds.height);
		}else{
			Graphics2D g2 = (Graphics2D)getGraphics();
			g2.setColor( this.bgColor );
			g2.fillRect(0, 0, bounds.width, bounds.height);
		}
	}
	
	/**
	 * Resize component's double buffered area
	 */
	private void resizeComponent(){
		this.imageBuffer = new BufferedImage(
			this.getSize().width, 
			this.getSize().height,
			BufferedImage.TYPE_4BYTE_ABGR);
	    this.graphicsBuffer = this.imageBuffer.getGraphics();
	}
	
}
