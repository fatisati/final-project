package map;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class BackClass extends JLabel {

	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(new ImageIcon("frame1.jpg").getImage(),0,0,d.width,d.height,null);
	}

}
