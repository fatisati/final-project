package map;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MapFrame extends JFrame {

	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

	public MapFrame(int row,int column, int castlenum) {
		// TODO Auto-generated constructor stub
		
		this.setIconImage(Toolkit.getDefaultToolkit().createImage("myback.jpg"));
        
		setLayout(null);
		setSize(d);
		setLocation(0, 0);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		MapPanel mappanel = new MapPanel(row,column, castlenum, this);
		mappanel.setSize(d);
		mappanel.setLocation(0,0);
		Sound song1= new Sound(2);
		song1.start();
		
		

		
		getContentPane().add(mappanel);
		setVisible(true);
	} 

}
