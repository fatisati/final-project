package map;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class BackClass1 extends JLabel {

	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(new ImageIcon("photo_2016-05-17_14-15-18.jpg").getImage(),0,0,d.width/3,d.height/3,null);
	}

}