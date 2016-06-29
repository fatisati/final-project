package map;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


import Phase2.GamePanel;

import javax.swing.UIManager.LookAndFeelInfo;

public class MapPanel extends JPanel implements MouseListener, Runnable, MouseMotionListener {

	ImageIcon BG_img[][] = new ImageIcon[4][2]; // [season][day_night]
	ImageIcon LL_img[][][] = new ImageIcon[4][2][16]; // [season][day_night][land]
	ImageIcon HL_img[][][] = new ImageIcon[4][2][16]; // [season][day_night][land]
	ImageIcon ForGGold_img[] = new ImageIcon[2]; // [day_night]
	ImageIcon ForGIron_img[] = new ImageIcon[2]; // [day_night]
	ImageIcon ForGTree_img[][] = new ImageIcon[4][2]; // [day_night]
	ImageIcon ForGFarmLand_img[] = new ImageIcon[2]; // [day_night]
	ImageIcon ForGFish_img[] = new ImageIcon[2]; // [day_night]
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	FileOutputStream output;
	FileInputStream input;
	Stack<int[][]> redo;
	Stack<int[][]> undo;
	Vector<int[]> cplaces;
	int r, c;
	int bmap[][];
	boolean castlemap[][];
	int blcsizex;
	int blcsizey;
	int miniblcsizex;
	int miniblcsizey;
	int startRow, startColumn;// start row, start column
	int rowCount, columnCount;// max number of rows and columns that are shown
	int cursor[] = new int[2];// where the cursor is now
	int cursor2[] = new int[2];// for b part
	int cursortype;
	int screen1x;
	int screen1y;
	int screen2x;
	int screen2y;
	int X;
	int sizex, sizey;
	int mapblcsizex, mapblcsizey;
	int castlenumber;
	boolean isIn = true;
	//boolean putcastle=false;
	JFileChooser savefile;
	JButton iland;
	JButton deepsea;
	JButton sea;
	JButton fish;
	JButton bigfish;
	JButton land;
	JButton tree;
	JButton farmland;
	JButton mount;
	JButton gold;
	JButton iron;
	JButton save;
	JButton load;
	JButton zoomin;
	JButton zoomout;
	JButton right;
	JButton up;
	JButton down;
	JButton left;
	JButton simul;
	JButton addRow;
	JButton addCol;
	JButton redobut;
	JButton undobut;
	JButton Play;
	JLabel back;
	FileInputStream fi;
	Timer t = new Timer(0, 0);
	JFrame frame;
	

	public MapPanel(int r, int c,int castlenum,JFrame frame) {

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
		// ----------------------------------------------------------castle
		castlemap = new boolean[c][r];
		castlenumber = castlenum;
		// ------------------------------------------------------------

		// dafult map
		bmap = new int[1000][1000];
		for (int i = 0; i < c; i++) {
			for (int j = 0; j < r; j++) {
				bmap[i][j] = 0;
			}
		}
		// -----------------------------------------------------------------------------------------
		for (int i = 0; i < 4; i++) {
			String seasonStr = "";
			switch (i) {
			case 0: // Spring
				seasonStr = "SP-";
				break;
			case 1: // Summer
				seasonStr = "SU-";
				break;
			case 2: // Fall
				seasonStr = "FA-";
				break;
			case 3: // Winter
				seasonStr = "WI-";
				break;
			}
			for (int j = 0; j < 2; j++) {
				String dayStr = "";
				if (j == 0)
					dayStr = "D-";
				else
					dayStr = "N-";
				BG_img[i][j] = new ImageIcon(seasonStr + dayStr + "SEA.png");
				for (int k = 0; k < 16; k++) {
					LL_img[i][j][k] = new ImageIcon(seasonStr + dayStr + "LL-" + String.valueOf(k) + ".png");
				}
			}
		}
		for (int i = 0; i < 4; i++) {
			String seasonStr = "";
			switch (i) {
			case 0: // Spring
				seasonStr = "SP-";
				break;
			case 1: // Summer
				seasonStr = "SU-";
				break;
			case 2: // Fall
				seasonStr = "FA-";
				break;
			case 3: // Winter
				seasonStr = "WI-";
				break;
			}
			for (int j = 0; j < 2; j++) {
				String dayStr = "";
				if (j == 0)
					dayStr = "D-";
				else
					dayStr = "N-";
				for (int k = 0; k < 16; k++) {
					HL_img[i][j][k] = new ImageIcon(seasonStr + dayStr + "HL-" + String.valueOf(k) + ".png");
				}
			}
		}

		for (int j = 0; j < 2; j++) {
			String dayStr = "";
			if (j == 0)
				dayStr = "D_";
			else
				dayStr = "N_";
			ForGFish_img[j] = new ImageIcon(dayStr + "Fish.png");

		}

		for (int j = 0; j < 2; j++) {
			String dayStr = "";
			if (j == 0)
				dayStr = "D_";
			else
				dayStr = "N_";

			ForGIron_img[j] = new ImageIcon(dayStr + "Iron.png");

		}

		for (int j = 0; j < 2; j++) {
			String dayStr = "";
			if (j == 0)
				dayStr = "D_";
			else
				dayStr = "N_";
			ForGGold_img[j] = new ImageIcon(dayStr + "Gold.png");

		}

		for (int j = 0; j < 2; j++) {
			String dayStr = "";
			if (j == 0)
				dayStr = "D_";
			else
				dayStr = "N_";
			ForGFarmLand_img[j] = new ImageIcon(dayStr + "FarmLand.png");

		}

		for (int i = 0; i < 4; i++) {
			String seasonStr = "";
			switch (i) {
			case 0: // Spring
				seasonStr = "SP_";
				break;
			case 1: // Summer
				seasonStr = "SU_";
				break;
			case 2: // Fall
				seasonStr = "FA_";
				break;
			case 3: // Winter
				seasonStr = "WI_";
				break;
			}
			for (int j = 0; j < 2; j++) {
				String dayStr = "";
				if (j == 0)
					dayStr = "D_";
				else
					dayStr = "N_";
				ForGTree_img[i][j] = new ImageIcon(seasonStr + dayStr + "Tree.png");

			}
		}
		
		this.frame = frame;
		cursortype = -1;
		startRow = 0;
		startColumn = 0;

		this.r = r;
		this.c = c;
		rowCount = r;
		columnCount = c;
		X = (int) (d.getWidth() * 3 / 4);
		screen1x = d.width;
		screen1y = (int) (d.getHeight() * 3 / 5) + 50;
		screen2x = (int) (d.getWidth() * 1 / 5);
		screen2y = (int) (d.getHeight() * 2 / 7);
		blcsizex = (int) (screen1x / c);
		blcsizey = (int) (screen1y / r);
		mapblcsizex = screen1x / columnCount;
		mapblcsizey = screen1y / rowCount;
		miniblcsizex = (int) (screen2x / c);
		miniblcsizey = (int) (screen2y / r);
		cursor2[0] = screen1x + 20;
		cursor2[1] = screen1y + 20;
		undo = new Stack<int[][]>();
		redo = new Stack<int[][]>();
		blcsizex = 80;
		blcsizey = 36;

		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		// primaryStage.getIcons().add(new ImageIcon("myback.jpg"));

		// panel
		setLayout(null);
		setSize(d);
		setLocation(0, 0);

		int xbutton = (int) (d.getWidth() * 3 / 4 / 8);

		save = new JButton();
		save.setSize(2 * blcsizex, 2 * blcsizey);
		save.setLocation(2 * xbutton, screen1y + screen2y / 2);
		save.setVisible(true);
		save.addMouseListener(this);
		save.setIcon(new ImageIcon("saveicon.PNG"));
		save.setBackground(new Color(8, 134, 83));
		this.add(save);

		load = new JButton();
		load.setSize(2 * blcsizex, 2 * blcsizey);
		load.setLocation(3 * xbutton, screen1y + screen2y / 2);
		load.setVisible(true);
		load.addMouseListener(this);
		load.setIcon(new ImageIcon("loadicon.PNG"));
		load.setBackground(new Color(8, 134, 83));
		this.add(load);

		zoomin = new JButton("zoomin");
		zoomin.setSize(2 * blcsizex, 2 * blcsizey);
		zoomin.setLocation(4 * xbutton, screen1y + screen2y / 2);
		zoomin.setVisible(true);
		zoomin.addMouseListener(this);
		zoomin.setIcon(new ImageIcon(""));
		zoomin.setBackground(new Color(8, 134, 83));
		this.add(zoomin);

		zoomout = new JButton("zoomout");
		zoomout.setSize(2 * blcsizex, 2 * blcsizey);
		zoomout.setLocation(5 * xbutton, screen1y + screen2y / 2);
		zoomout.setVisible(true);
		zoomout.addMouseListener(this);
		zoomout.setIcon(new ImageIcon(""));
		zoomout.setBackground(new Color(8, 134, 83));
		this.add(zoomout);

		up = new JButton("up");
		up.setSize(2 * blcsizex, 2 * blcsizey);
		up.setLocation(1 * xbutton, screen1y + screen2y / 2);
		up.setVisible(true);
		up.addMouseListener(this);
		up.setIcon(new ImageIcon(".PNG"));
		up.setBackground(new Color(8, 134, 83));
		this.add(up);

		right = new JButton("right");
		right.setSize(2 * blcsizex, 2 * blcsizey);
		right.setLocation(2 * xbutton, screen1y + screen2y * 3 / 4);
		right.setVisible(true);
		right.addMouseListener(this);
		right.setIcon(new ImageIcon(".PNG"));
		right.setBackground(new Color(8, 134, 83));
		this.add(right);

		left = new JButton("left");
		left.setSize(2 * blcsizex, 2 * blcsizey);
		left.setLocation(30, screen1y + screen2y * 3 / 4);
		left.setVisible(true);
		left.addMouseListener(this);
		left.setIcon(new ImageIcon(".PNG"));
		left.setBackground(new Color(8, 134, 83));
		this.add(left);

		down = new JButton("down");
		down.setSize(2 * blcsizex, 2 * blcsizey);
		down.setLocation(1 * xbutton, screen1y + screen2y * 3 / 4 + 2 * blcsizey);
		down.setVisible(true);
		down.addMouseListener(this);
		down.setIcon(new ImageIcon(".PNG"));
		down.setBackground(new Color(8, 134, 83));
		this.add(down);

		simul = new JButton("simul");
		simul.setSize(2 * blcsizex, 2 * blcsizey);
		simul.setLocation(3 * xbutton + 2 * blcsizex, screen1y + screen2y * 3 / 4);
		simul.setVisible(true);
		simul.addMouseListener(this);
		simul.setBackground(new Color(8, 134, 83));
		this.add(simul);

		addRow = new JButton("addRow");
		addRow.setSize(2 * blcsizex, 2 * blcsizey);
		addRow.setLocation(6 * xbutton, screen1y + screen2y / 2);
		addRow.setVisible(true);
		addRow.addMouseListener(this);
		addRow.setBackground(new Color(8, 134, 83));
		this.add(addRow);

		addCol = new JButton("addCol");
		addCol.setSize(2 * blcsizex, 2 * blcsizey);
		addCol.setLocation(6 * xbutton, screen1y + screen2y * 3 / 4 + 2 * blcsizey);
		addCol.setVisible(true);
		addCol.addMouseListener(this);
		addCol.setBackground(new Color(8, 134, 83));
		this.add(addCol);

		undobut = new JButton("undo");
		undobut.setSize(2 * blcsizex, 2 * blcsizey);
		undobut.setLocation(7 * xbutton, screen1y + screen2y * 3 / 4);
		undobut.setVisible(true);
		undobut.addMouseListener(this);
		undobut.setIcon(new ImageIcon(""));
		undobut.setBackground(new Color(8, 134, 83));
		this.add(undobut);

		redobut = new JButton("redo");
		redobut.setSize(2 * blcsizex, 2 * blcsizey);
		redobut.setLocation(5 * xbutton, screen1y + screen2y * 3 / 4);
		redobut.setVisible(true);
		redobut.addMouseListener(this);
		redobut.setIcon(new ImageIcon(""));
		redobut.setBackground(new Color(8, 134, 83));
		this.add(redobut);

		Play = new JButton("Play");
		Play.setSize(2 * blcsizex, 2 * blcsizey);
		Play.setLocation(7 * xbutton, screen1y + screen2y / 2);
		Play.setVisible(true);
		Play.addMouseListener(this);
		Play.setIcon(new ImageIcon(""));
		Play.setBackground(new Color(8, 134, 83));
		this.add(Play);

		iland = new JButton();// 0
		iland.setSize(2 * blcsizex, 2 * blcsizey);
		iland.setLocation(1 * xbutton, screen1y + screen2y / 10);
		iland.setVisible(true);
		iland.addMouseListener(this);
		iland.setIcon(new ImageIcon("ilandicon.png"));
		iland.setBackground(new Color(8, 134, 83));
		this.add(iland);

		sea = new JButton();// 1
		sea.setSize(2 * blcsizex, 2 * blcsizey);
		sea.setLocation(2 * xbutton, screen1y + screen2y / 10);
		sea.setVisible(true);
		sea.addMouseListener(this);
		sea.setBackground(new Color(8, 134, 83));
		sea.setIcon(new ImageIcon("sea.png"));
		this.add(sea);

		fish = new JButton();// 2
		fish.setSize(2 * blcsizex, 2 * blcsizey);
		fish.setLocation(3 * xbutton, screen1y + screen2y / 10);
		fish.setVisible(true);
		fish.addMouseListener(this);
		fish.setIcon(new ImageIcon("fish1icon.PNG"));
		fish.setBackground(new Color(8, 134, 83));
		this.add(fish);

		tree = new JButton();// 3
		tree.setSize(2 * blcsizex, 2 * blcsizey);
		tree.setLocation(4 * xbutton, screen1y + screen2y / 10);
		tree.setVisible(true);
		tree.addMouseListener(this);
		tree.setIcon(new ImageIcon("treeicon.PNG"));
		tree.setBackground(new Color(8, 134, 83));
		this.add(tree);

		farmland = new JButton();// 4
		farmland.setSize(2 * blcsizex, 2 * blcsizey);
		farmland.setLocation(5 * xbutton, screen1y + screen2y / 10);
		farmland.setVisible(true);
		farmland.addMouseListener(this);
		farmland.setIcon(new ImageIcon("farmlandicon.PNG"));
		farmland.setBackground(new Color(8, 134, 83));
		this.add(farmland);

		mount = new JButton();// 5
		mount.setSize(2 * blcsizex, 2 * blcsizey);
		mount.setLocation(6 * xbutton, screen1y + screen2y / 10);
		mount.setVisible(true);
		mount.addMouseListener(this);
		mount.setIcon(new ImageIcon("mounticon.PNG"));
		mount.setBackground(new Color(8, 134, 83));
		this.add(mount);

		iron = new JButton();// 6
		iron.setSize(2 * blcsizex, 2 * blcsizey);
		iron.setLocation(7 * xbutton, screen1y + screen2y / 10);
		iron.setVisible(true);
		iron.addMouseListener(this);
		iron.setIcon(new ImageIcon("ironicon.PNG"));
		iron.setBackground(new Color(8, 134, 83));
		this.add(iron);

		gold = new JButton();// 7
		gold.setSize(2 * blcsizex, 2 * blcsizey);
		gold.setLocation(30, screen1y + screen2y / 10);
		gold.setVisible(true);
		gold.addMouseListener(this);
		gold.setIcon(new ImageIcon("goldicon.PNG"));
		gold.setBackground(new Color(8, 134, 83));
		this.add(gold);

		BackClass b = new BackClass();
		b.setSize(d);
		b.setLocation(0, 0);
		b.setVisible(true);
		this.add(b);

		t.start();
		(new Thread(this)).start();
		this.setVisible(true);

	}

	public void changeCursor(boolean is) {
		if (is) {
			if (cursortype == -1) {
				this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else if (cursortype == 1) {
				this.setCursor(getToolkit().createCustomCursor(new ImageIcon("ilandicon.png").getImage(),
						new Point(this.getX(), this.getY()), "mouse imge"));
			} else if (cursortype == 0) {
				this.setCursor(getToolkit().createCustomCursor(new ImageIcon("sea.png").getImage(),
						new Point(this.getX(), this.getY()), "mouse imge"));
			} else if (cursortype == 2) {
				this.setCursor(getToolkit().createCustomCursor(new ImageIcon("fish1icon.png").getImage(),
						new Point(this.getX(), this.getY()), "mouse imge"));
			} else if (cursortype == 3) {
				this.setCursor(getToolkit().createCustomCursor(new ImageIcon("treeicon.png").getImage(),
						new Point(this.getX(), this.getY()), "mouse imge"));
			} else if (cursortype == 5) {
				this.setCursor(getToolkit().createCustomCursor(new ImageIcon("farmlandicon.png").getImage(),
						new Point(this.getX(), this.getY()), "mouse imge"));
			} else if (cursortype == 7) {
				this.setCursor(getToolkit().createCustomCursor(new ImageIcon("mounticon.png").getImage(),
						new Point(this.getX(), this.getY()), "mouse imge"));
			} else if (cursortype == 9) {
				this.setCursor(getToolkit().createCustomCursor(new ImageIcon("ironicon.png").getImage(),
						new Point(this.getX(), this.getY()), "mouse imge"));
			} else if (cursortype == 11) {
				this.setCursor(getToolkit().createCustomCursor(new ImageIcon("goldicon.png").getImage(),
						new Point(this.getX(), this.getY()), "mouse imge"));
			}

		} else {
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

	}

	@Override
	public void paint(Graphics g) {

		super.paint(g);
		// changes the cursor if in the scr1
		changeCursor(isIn);

		int pic = 0;

		// ---------------------------------------------------------
		// for know that where is iland inside of water
				for (int i = 0; i < c; i++) {
					for (int j = 0; j < r; j++) {
		
						if (bmap[i][j] == 1) {// iland
							if ((j > 0 && bmap[i][j - 1]==0) ||(i + 1 < c && bmap[i + 1][j]==0) || (j + 1 < r && bmap[i][j + 1]==0)||(i > 0 && bmap[i - 1][j]==0 ) ){					
								castlemap[i][j] = true;
							}
							
		
						}
					}
				}
//				
		// ---------------------------------------------------------

		
		
		
		
		// drawing island and sea of A part
		for (int i = startColumn; i < startColumn + columnCount && i < c; i++) {
			for (int j = startRow; j < startRow + rowCount && j < r; j++) {
				int y = j - startRow;
				int x = i - startColumn;
				g.drawImage(BG_img[t.season][t.day].getImage(), x * mapblcsizex, y * mapblcsizey, mapblcsizex,
						mapblcsizey, null);
				pic = 0;
				if ((bmap[i][j] % 2) != 0) {
					if (j > 0)
						pic = pic + (bmap[i][j - 1] % 2);
					if (i + 1 < c)
						pic = pic + 2 * (bmap[i + 1][j] % 2);
					if (j + 1 < r)
						pic = pic + 4 * (bmap[i][j + 1] % 2);
					if (i > 0)
						pic = pic + 8 * (bmap[i - 1][j] % 2);
					g.drawImage(LL_img[t.season][t.day][pic].getImage(), x * mapblcsizex, y * mapblcsizey, mapblcsizex,
							mapblcsizey, null);
				}
			}
		}

		// drawing sea and island if B part
		for (int i = 0; i < c; i++) {
			for (int j = 0; j < r; j++) {
				g.drawImage(BG_img[t.season][t.day].getImage(), X + i * miniblcsizex, screen1y + j * miniblcsizey,
						miniblcsizex, miniblcsizey, null);
				pic = 0;

				if ((bmap[i][j] % 2) != 0) {
					if (j > 0)
						pic = pic + (bmap[i][j - 1] % 2);
					if (i + 1 < c)
						pic = pic + 2 * (bmap[i + 1][j] % 2);
					if (j + 1 < r)
						pic = pic + 4 * (bmap[i][j + 1] % 2);
					if (i > 0)
						pic = pic + 8 * (bmap[i - 1][j] % 2);
					g.drawImage(LL_img[t.season][t.day][pic].getImage(), X + i * miniblcsizex,
							screen1y + j * miniblcsizey, miniblcsizex, miniblcsizey, null);
				}
			}
		}

		// drawing mountain of B part
		for (int i = 0; i < c; i++) {
			for (int j = 0; j < r; j++) {

				pic = 0;

				if (bmap[i][j] == 7 || bmap[i][j] == 9 || bmap[i][j] == 11) {// mount
					if (j > 0)
						if (bmap[i][j - 1] == 7 || bmap[i][j - 1] == 9 || bmap[i][j - 1] == 11)
							pic = pic + (1);
					if (i + 1 < c)
						if (bmap[i + 1][j] == 7 || bmap[i + 1][j] == 9 || bmap[i + 1][j] == 11)
							pic = pic + 2 * (1);
					if (j + 1 < r)
						if (bmap[i][j + 1] == 7 || bmap[i][j + 1] == 9 || bmap[i][j + 1] == 11)
							pic = pic + 4 * (1);
					if (i > 0)
						if (bmap[i - 1][j] == 7 || bmap[i - 1][j] == 9 || bmap[i - 1][j] == 11)
							pic = pic + 8 * (1);
					g.drawImage(HL_img[t.season][t.day][pic].getImage(), X + i * miniblcsizex,
							screen1y + j * miniblcsizey, miniblcsizex, miniblcsizey, null);
				}
			}
		}

		// drawing mountain of A part
		for (int i = startColumn; i < startColumn + columnCount && i < c; i++) {
			for (int j = startRow; j < startRow + rowCount && j < r; j++) {
				int y = j - startRow;
				int x = i - startColumn;
				pic = 0;
				if (bmap[i][j] == 7 || bmap[i][j] == 9 || bmap[i][j] == 11) {// mount
					if (j > 0)
						if (bmap[i][j - 1] == 7 || bmap[i][j - 1] == 9 || bmap[i][j - 1] == 11)
							pic = pic + (1);
					if (i + 1 < c)
						if (bmap[i + 1][j] == 7 || bmap[i + 1][j] == 9 || bmap[i + 1][j] == 11)
							pic = pic + 2 * (1);
					if (j + 1 < r)
						if (bmap[i][j + 1] == 7 || bmap[i][j + 1] == 9 || bmap[i][j + 1] == 11)
							pic = pic + 4 * (1);
					if (i > 0)
						if (bmap[i - 1][j] == 7 || bmap[i - 1][j] == 9 || bmap[i - 1][j] == 11)
							pic = pic + 8 * (1);
					g.drawImage(HL_img[t.season][t.day][pic].getImage(), x * mapblcsizex, y * mapblcsizey, mapblcsizex,
							mapblcsizey, null);
				}
			}
		}

		// fish of A part
		for (int i = startColumn; i < startColumn + columnCount && i < c; i++) {
			for (int j = startRow; j < startRow + rowCount && j < r; j++) {
				int y = j - startRow;
				int x = i - startColumn;
				if (bmap[i][j] == 2) {// fish
					g.drawImage(ForGFish_img[t.day].getImage(), x * mapblcsizex, y * mapblcsizey, mapblcsizex / 2,
							mapblcsizey / 2, null);
				}
			}
		}

		// Iron of of A part
		for (int i = startColumn; i < startColumn + columnCount && i < c; i++) {
			for (int j = startRow; j < startRow + rowCount && j < r; j++) {
				int y = j - startRow;
				int x = i - startColumn;
				if (bmap[i][j] == 9) {// Iron
					g.drawImage(ForGIron_img[t.day].getImage(), x * mapblcsizex, y * mapblcsizey, mapblcsizex / 2,
							mapblcsizey / 2, null);
				}
			}
		}

		// Gold of A part
		for (int i = startColumn; i < startColumn + columnCount && i < c; i++) {
			for (int j = startRow; j < startRow + rowCount && j < r; j++) {
				int y = j - startRow;
				int x = i - startColumn;
				if (bmap[i][j] == 11) {// gold
					g.drawImage(ForGGold_img[t.day].getImage(), x * mapblcsizex, y * mapblcsizey, mapblcsizex / 2,
							mapblcsizey / 2, null);
				}
			}
		}

		// farm of A part
		for (int i = startColumn; i < startColumn + columnCount && i < c; i++) {
			for (int j = startRow; j < startRow + rowCount && j < r; j++) {
				int y = j - startRow;
				int x = i - startColumn;
				if (bmap[i][j] == 5) {// FarmLand
					g.drawImage(ForGFarmLand_img[t.day].getImage(), x * mapblcsizex, y * mapblcsizey, mapblcsizex / 2,
							mapblcsizey / 2, null);
				}
			}
		}

		// Tree of A part
		for (int i = startColumn; i < startColumn + columnCount && i < c; i++) {
			for (int j = startRow; j < startRow + rowCount && j < r; j++) {
				int y = j - startRow;
				int x = i - startColumn;
				if (bmap[i][j] == 3) {// tree
					g.drawImage(ForGTree_img[t.season][t.day].getImage(), x * mapblcsizex, y * mapblcsizey,
							mapblcsizex / 2, mapblcsizey / 2, null);
				}
			}
		}

		// fish of B part
		for (int i = 0; i < c; i++) {
			for (int j = 0; j < r; j++) {
				if (bmap[i][j] == 2) {// fish
					g.drawImage(ForGFish_img[t.day].getImage(), X + i * miniblcsizex, screen1y + j * miniblcsizey,
							miniblcsizex / 2, miniblcsizey / 2, null);
				}
			}
		}

		// Gold B part
		for (int i = 0; i < c; i++) {
			for (int j = 0; j < r; j++) {
				if (bmap[i][j] == 11) {// Gold
					g.drawImage(ForGGold_img[t.day].getImage(), X + i * miniblcsizex, screen1y + j * miniblcsizey,
							miniblcsizex / 2, miniblcsizey / 2, null);
				}
			}
		}

		// Iron of B part
		for (int i = 0; i < c; i++) {
			for (int j = 0; j < r; j++) {
				if (bmap[i][j] == 9) {// Iron
					g.drawImage(ForGIron_img[t.day].getImage(), X + i * miniblcsizex, screen1y + j * miniblcsizey,
							miniblcsizex / 2, miniblcsizey / 2, null);
				}
			}
		}

		// Tree of B part
		for (int i = 0; i < c; i++) {
			for (int j = 0; j < r; j++) {
				if (bmap[i][j] == 3) {// tree
					g.drawImage(ForGTree_img[t.season][t.day].getImage(), X + i * miniblcsizex,
							screen1y + j * miniblcsizey, miniblcsizex / 2, miniblcsizey / 2, null);
				}
			}
		}

		// farmland of B part
		for (int i = 0; i < c; i++) {
			for (int j = 0; j < r; j++) {
				if (bmap[i][j] == 5) {// farmland
					g.drawImage(ForGFarmLand_img[t.day].getImage(), X + i * miniblcsizex, screen1y + j * miniblcsizey,
							miniblcsizex / 2, miniblcsizey / 2, null);
				}
			}
		}

		// oon mostatil siah oon paiin
		sizex = columnCount * miniblcsizex;
		sizey = rowCount * miniblcsizey;
		g.drawRect(X + startColumn * miniblcsizex, screen1y + startRow * miniblcsizey, sizex, sizey);

		
	}

	public void myUndo() {
		try {
			undo.pop();
			for (int q = 0; q < c; q++) {
				for (int w = 0; w < r; w++) {
					bmap[q][w] = undo.peek()[q][w];
				}
			}
			this.repaint();
			int[][] redomap = new int[c][r];

			for (int q = 0; q < c; q++) {
				for (int w = 0; w < r; w++) {
					redomap[q][w] = undo.peek()[q][w];
				}
			}
			undo.pop();
			redo.push(redomap);
		} catch (EmptyStackException w) {
			System.out.println("empty undo");
		}

	}

	public void myRedo() {
		try {
			for (int q = 0; q < c; q++) {
				for (int w = 0; w < r; w++) {
					bmap[q][w] = redo.peek()[q][w];
				}
			}
			redo.pop();
			this.repaint();
		} catch (EmptyStackException w) {
			System.out.println("empty redo");
		}
	}

	public void save() {
		savefile = new JFileChooser();
		int returnVal = savefile.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			java.io.File file = savefile.getSelectedFile();
			try {

				output = new FileOutputStream(file);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (int i = 0; i < r; i++) {
				for (int j = 0; j < c; j++) {
					try {
						output.write(bmap[i][j]);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}

		}

	}

	public void load() {
		savefile = new JFileChooser();
		savefile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = savefile.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			java.io.File file = savefile.getSelectedFile();
			try {

				input = new FileInputStream(file);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			byte[] b = new byte[r * c];
			for (int i = 0; i < r; i++) {
				try {
					input.read(b);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("break r=" + r);
					break;
				}
				for (int j = 0; j < c; j++) {
					bmap[i][j] = b[i * c + j];
				}

			}

		}
	}

	public void zoom(int t) {
		int mr = rowCount;
		int mc = columnCount;
		if (t == 0 && rowCount > 2 && columnCount > 2) {// zoom in
			mr--;
			mc--;
			mapblcsizey = screen1y / mr;
			mapblcsizex = d.width / mc;
		} else {
			if (startColumn > 0) {
				startColumn--;
			}
			if (startRow > 0) {
				startRow--;
			}
			if (mr < r) {
				mr++;
			}
			if (mc < c) {
				mc++;
			}
			mapblcsizey = screen1y / mr;
			mapblcsizex = d.width / mc;
		}
		rowCount = mr;
		columnCount = mc;
	}

	@Override
	public void run() {
		while (true) {
			//
			if (cursor[0] < mapblcsizex && startColumn > 0 && isIn) {
				startColumn--;
			}
			if (cursor[0] > mapblcsizex * rowCount - mapblcsizex && startColumn + columnCount < c && isIn) {
				startColumn++;
			}
			if (cursor[1] < mapblcsizey && startRow > 0 && isIn) {
				startRow--;
			}
			if (cursor[1] > mapblcsizey * rowCount - mapblcsizey && startRow + rowCount < r && isIn) {
				startRow++;
			}

			repaint();

			long d = System.currentTimeMillis();
			while (d + 100 > System.currentTimeMillis())
				;

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getSource() == Play) {
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

			 cplaces = new PutCastle(castlenumber).putCastle(castlearr);
			 this.setVisible(false);
			 GamePanel g = new GamePanel(castlenumber,bmap,cplaces,r,c);
			 g.setSize(d);
			 g.setLocation(0, 0);
			 frame.getContentPane().add(g);
			 

		}
		if (e.getSource() == addCol) {
			c++;
			miniblcsizex = (int) (screen2x / c);
		}
		if (e.getSource() == addRow) {
			r++;
			miniblcsizey = (int) (screen2y / r);
		}
		if (e.getSource() == up && startRow > 0) {
			startRow--;
		}
		if (e.getSource() == right && startColumn + columnCount < c) {
			startColumn++;
		}
		if (e.getSource() == left && startColumn > 0) {
			startColumn--;

		}
		if (e.getSource() == down && startRow + rowCount < r) {
			startRow++;
		}
		if (e.getSource() == zoomin) {
			zoom(0);
			cursortype = -1;
		}
		if (e.getSource() == zoomout) {
			zoom(1);
			cursortype = -1;
		}
		if (e.getSource() == redobut) {
			myRedo();
			cursortype = -1;
		}
		if (e.getSource() == undobut) {
			myUndo();
			cursortype = -1;
		}
		if (e.getSource() == save) {
			this.save();
			cursortype = -1;
		} else if (e.getSource() == load) {
			this.load();
			cursortype = -1;
		} else if (e.getSource() == iland) {
			cursortype = 1;
		} else if (e.getSource() == sea) {
			cursortype = 0;
		} else if (e.getSource() == fish) {
			cursortype = 2;
		} else if (e.getSource() == tree) {
			cursortype = 3;
		} else if (e.getSource() == farmland) {
			cursortype = 5;
		} else if (e.getSource() == mount) {
			cursortype = 7;
		} else if (e.getSource() == iron) {
			cursortype = 9;
		} else if (e.getSource() == gold) {
			cursortype = 11;
		} else {

		}

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		cursor[0] = arg0.getX();
		cursor[1] = arg0.getY();
		if (cursor[0] > X && cursor[1] > screen1y && cursor[0] + sizex < d.width * 19 / 20
				&& cursor[1] + sizey < d.height * 31 / 35) {
			cursor2[0] = arg0.getX();
			cursor2[1] = arg0.getY();
			startColumn = (cursor2[0] - X) / miniblcsizex;
			startRow = (cursor2[1] - screen1y) / miniblcsizey;
		}

		if (arg0.getXOnScreen() > 0 && arg0.getXOnScreen() < screen1x && arg0.getYOnScreen() > 0
				&& arg0.getYOnScreen() < screen1y) {

			int x = arg0.getXOnScreen();
			int y = arg0.getYOnScreen();
			int i = startColumn + x / mapblcsizex;
			int j = startRow + y / mapblcsizey;

			if (cursortype == 2 && bmap[i][j] != 0)// tree not on land
				return;
			if (cursortype == 3 && bmap[i][j] != 1)// tree not on land
				return;
			if (cursortype == 5 && bmap[i][j] != 1)// farmland not on land
				return;
			if (cursortype == 9 && bmap[i][j] != 7)// iron not on mount
				return;
			if (cursortype == 11 && bmap[i][j] != 7)// iron not on mount
				return;
			if (cursortype == -1) {
				return;
			}

			boolean b1 = false;
			int[][] map = new int[c][r];
			for (int q = 0; q < c; q++) {
				for (int w = 0; w < r; w++) {
					map[q][w] = bmap[q][w];
				}
			}

			try {
				bmap[i][j] = cursortype;
			} catch (ArrayIndexOutOfBoundsException e) {
				return;
			}

			for (int q = 0; q < c; q++) {
				for (int w = 0; w < r; w++) {
					if (map[q][w] != bmap[q][w]) {
						b1 = true;
					}
				}
			}

			if (b1) {
				int[][] undomap = new int[c][r];

				for (int q = 0; q < c; q++) {
					for (int w = 0; w < r; w++) {
						undomap[q][w] = bmap[q][w];
					}
				}

				undo.add(undomap);
			}
			this.repaint();
		}

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		isIn = true;
		cursor[0] = arg0.getX();
		cursor[1] = arg0.getY();
		if (cursor[0] > screen1x) {
			isIn = false;
			changeCursor(isIn);
		}
		if (cursor[1] > screen1y) {
			isIn = false;
			changeCursor(isIn);
		}
	}


	

}
