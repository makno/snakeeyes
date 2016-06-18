package at.fhj.snakeeyes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.util.ArrayList;

public class SnakeEyes extends JFrame {

	private static final long serialVersionUID = 7590948179066172576L;

	private JPanel contentPane;
	
	private Controller controller;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SnakeEyes frame = new SnakeEyes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SnakeEyes() {
		setResizable(false);
		this.controller = new Controller(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.contentPane = new JPanel();
		contentPane.setBackground(Color.GREEN);
		this.contentPane.setLayout(new BorderLayout(0, 0));
		this.setContentPane(contentPane);
		this.getContentPane().add(this.controller.getPlayground(),BorderLayout.CENTER);	
		this.controller.startLoop();
		this.addKeyListener(this.controller);
		this.setPreferredSize(new Dimension(805,605));
		ImageIcon imgIcon1 = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/16x16.gif"),"16x16");
		ImageIcon imgIcon2 = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/24x24.gif"),"24x24");
		ImageIcon imgIcon3 = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/32x32.gif"),"32x32");
		ImageIcon imgIcon4 = new ImageIcon(SnakeEyes.class.getResource("/at/fhj/snakeeyes/img/16x16.gif"),"48x48");
		ArrayList<Image> arlImages = new ArrayList<Image>();
		arlImages.add(imgIcon1.getImage());
		arlImages.add(imgIcon2.getImage());
		arlImages.add(imgIcon3.getImage());
		arlImages.add(imgIcon4.getImage());
		this.setIconImages(arlImages);
		this.pack();
	}

}
