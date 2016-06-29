package map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.tools.Tool;
import javax.swing.UIManager.LookAndFeelInfo;

import Phase2.GamePanel;

public class Panel1 extends JPanel implements MouseListener,ItemListener {

	JTextField t1, t2;
	JLabel j1, j2,j3;
	JButton submit,map,exit,aboutUs,play;
	Sound song1;
	JRadioButton single,multi,multi2,multi3,multi4;
	int castlenum;
	Vector<int[]> cplaces;
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	Frame1 frame;

	public Panel1(Frame1 f) {

		this.frame = f;
		//this.setIconImage(Toolkit.getDefaultToolkit().createImage("myback.jpg"));
		
		for (LookAndFeelInfo a : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(a.getName()))
				try {
					UIManager.setLookAndFeel(a.getClassName());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		setLayout(null);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(d);
		setLocation(0, 0);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);

		castlenum = 2;
		
		single = new JRadioButton("single Play");
		single.isSelected();
		single.setSize(600,50);
		single.setLocation(100,d.height/2);
		single.setForeground(Color.orange);
		single.setFont(new Font("Courier", Font.BOLD, 30));
		single.setVisible(true);
		single.addItemListener(this);
		add(single);
		
		multi = new JRadioButton("Multi  Play");
		multi.isSelected();
		multi.setSize(600,50);
		multi.setLocation(100,getHeight() / 2+100);
		multi.setFont(new Font("Courier", Font.BOLD, 30));
		multi.setForeground(Color.orange);
		multi.setVisible(true);
		multi.addItemListener(this);
		add(multi);
		
		multi2 = new JRadioButton("2 Pleyer");
		multi2.isSelected();
		multi2.setSize(200,50);
		multi2.setLocation((int) (d.getWidth()/4),getHeight() / 2+100);
		multi2.setForeground(Color.orange);
		multi2.setFont(new Font("Courier", Font.BOLD, 30));
		multi2.setVisible(true);
		multi2.addItemListener(this);
		add(multi2);
		
		multi3 = new JRadioButton("3 Player");
		multi3.isSelected();
		multi3.setSize(200,50);
		multi3.setLocation(getWidth() / 4 +300,getHeight() / 2+100);
		multi3.setForeground(Color.orange);
		multi3.setFont(new Font("Courier", Font.BOLD, 30));
		multi3.setVisible(true);
		multi3.addItemListener(this);
		add(multi3);
		
		multi4 = new JRadioButton("4 Player");
		multi4.isSelected();
		multi4.setSize(200,50);
		multi4.setLocation(getWidth() / 4+600,getHeight() / 2+100);
		multi4.setForeground(Color.orange);
		multi4.setFont(new Font("Courier", Font.BOLD, 30));
		multi4.setVisible(true);
		multi4.addItemListener(this);
		add(multi4);
		
		ButtonGroup br = new ButtonGroup();
		br.add(single);
		br.add(multi);
		single.doClick();
		
		ButtonGroup br2 = new ButtonGroup();
		br2.add(multi2);
		br2.add(multi3);
		br2.add(multi4);
		multi2.doClick();
		multi2.setEnabled(false);
		multi3.setEnabled(false);
		multi4.setEnabled(false);
		
		exit = new JButton("Exit");
		exit.setSize(200,100);
		exit.setLocation(getWidth() / 2, (getHeight()*3 / 4) +200);
		exit.addMouseListener(this);
		add(exit);
		
		map = new JButton("Map");
        map.setSize(200,100);
		map.setLocation(getWidth()/4, (getHeight()*3 / 4) +200);
		map.addMouseListener(this);
		add(map);
		
		play = new JButton("Play");
        play.setSize(200,100);
		play.setLocation(getWidth()/4*3, (getHeight()*3 / 4) +200);
		play.addMouseListener(this);
		add(play);
		
		aboutUs = new JButton("About Us");
		aboutUs.setSize(200,100);
		aboutUs.setLocation(getWidth()-250, (getHeight()*3 / 4) +200);
		aboutUs.addMouseListener(this);
		add(aboutUs);
		
		j1 = new JLabel("number of rows");
		j1.setSize(getWidth() / 10, getHeight() / 10);
		j1.setFont(new Font("Courier", Font.BOLD, 30));
		j1.setLocation(100, d.height * 2 / 3-50);
		j1.setForeground(Color.orange);
		j1.setVisible(true);
		add(j1);
		
		

		j2 = new JLabel("number of columns");
		j2.setSize(getWidth() / 10, 300);
		j2.setFont(new Font("Courier", Font.BOLD, 30));
		j2.setLocation(100, d.height * 3 / 4-50);
		j2.setForeground(Color.orange);
		j2.setVisible(true);
		add(j2);

		t1 = new JTextField();
		t1.setSize(200, 100);
		t1.setLocation(getWidth() / 4, getHeight() * 2 / 3);
		t1.addMouseListener(this);
		add(t1);

		t2 = new JTextField();
		t2.setSize(200, 100);
		t2.setLocation(getWidth() / 4, getHeight() * 3 / 4);
		t2.addMouseListener(this);
		add(t2);

		

		BackClass b = new BackClass();
		b.setSize(d);
		b.setLocation(0, 0);
		b.setVisible(true);
		this.add(b);

		song1 = new Sound(1);
		song1.start();

		setVisible(true);
	}

	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == map) {
			int row, column;
			try {
				row = Integer.parseInt(t1.getText());
				column = Integer.parseInt(t2.getText());
			} catch (NumberFormatException w) {
				row = 30;
				column = 30;
			}
			this.setVisible(false);
			song1.stop();
			new MapFrame(row, column, castlenum);
		}
		else if(e.getSource()==exit){
			System.exit(0);
		}else if(e.getSource()==play){
			
			int r=30;int c=30;
			FileInputStream input=null;
			int [][] bmap = new int[c][r];
			java.io.File file = new File("playmap");
			try {
				input = new FileInputStream(file);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				System.out.println("no filllle");
				e1.printStackTrace();
			}

			byte[] b = new byte[r * c];
			for (int i = 0; i < r; i++) {
				try {
					input.read(b);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("break r=" + r);
					break;
				}
				for (int j = 0; j < c; j++) {
					bmap[i][j] = b[i * c + j];
				}

			}
			boolean[][] castlemap = new boolean[c][r];
			for (int i = 0; i < c; i++) {
				for (int j = 0; j < r; j++) {
	
					if (bmap[i][j] == 1) {// iland
						if ((j > 0 && bmap[i][j - 1]==0) ||(i + 1 < c && bmap[i + 1][j]==0) || (j + 1 < r && bmap[i][j + 1]==0)||(i > 0 && bmap[i - 1][j]==0 ) ){					
							castlemap[i][j] = true;
						}
						
	
					}
				}
			}
			int N = 0;
			for (int i = 0; i < c; i++) {
				for (int j = 0; j < r; j++) {
					if (castlemap[i][j] == true) {
						N++;
					}
				}
			}
			// N is the number of proper place for castle
						int[][] castlearr = new int[N][2];
						int k = 0;
						for (int i = 0; i < c; i++) {
							for (int j = 0; j < r; j++) {
								if (castlemap[i][j] == true) {
									castlearr[k][0] = i;
									castlearr[k][1] = j;
									k++;
								}
							}
						}

			this.setVisible(false);
			 cplaces = new PutCastle(castlenum).putCastle(castlearr);	
			 GamePanel g = new GamePanel(castlenum,bmap,cplaces,r,c);
			 g.setSize(d);
			 g.setLocation(0, 0);
			 g.setVisible(true);
			 frame.getContentPane().add(g);
		}
		else if(e.getSource()==aboutUs){
			
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==multi){

			multi2.setEnabled(true);
			multi3.setEnabled(true);
			multi4.setEnabled(true);
			multi2.doClick();
			castlenum = 2;
			
		}else if(e.getSource()==single){

			multi2.setEnabled(false);
			multi3.setEnabled(false);
			multi4.setEnabled(false);
			
			castlenum = 2;
		}
		if(e.getSource()==multi2){
			castlenum = 2;
		}else if(e.getSource()==multi3){
			castlenum = 3;
		}else if(e.getSource()==multi4){
			castlenum = 4;
		}
		
	}

}
