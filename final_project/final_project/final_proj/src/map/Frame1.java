package map;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Frame1  extends JFrame{
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

	public Frame1() {
		// TODO Auto-generated constructor stub
		
		this.setIconImage(Toolkit.getDefaultToolkit().createImage("myback.jpg"));
        
		setLayout(null);
		setSize(d);
		setLocation(0, 0);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Panel1 mappanel = new Panel1(this);
		mappanel.setSize(d);
		mappanel.setLocation(0,0);
		Sound song1= new Sound(2);
		song1.start();
		
		

		
		getContentPane().add(mappanel);
		setVisible(true);
	} 

	public static void main(String[] args) {
		new Frame1();
	}
}

