package Phase2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javafx.scene.text.Font;

import javax.swing.UIManager.LookAndFeelInfo;

import map.Timer;

public class GamePanel extends JPanel implements Runnable, MouseMotionListener, MouseListener, MouseWheelListener {

	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

	ImageIcon BG_img[][] = new ImageIcon[4][2]; // [season][day_night]
	ImageIcon LL_img[][][] = new ImageIcon[4][2][16]; // [season][day_night][land]
	ImageIcon HL_img[][][] = new ImageIcon[4][2][16]; // [season][day_night][land]
	ImageIcon ForGGold_img[] = new ImageIcon[2]; // [day_night]
	ImageIcon ForGIron_img[] = new ImageIcon[2]; // [day_night]
	ImageIcon ForGTree_img[][] = new ImageIcon[4][2]; // [day_night]
	ImageIcon ForGFarmLand_img[] = new ImageIcon[2]; // [day_night]
	ImageIcon ForGFish_img[] = new ImageIcon[2]; // [day_night]
	int cursor[] = new int[2];// where the cursor is now
	int r;
	int c;
	int miniblcsizex, miniblcsizey;
	int mapblcsizex, mapblcsizey;
	int startRow, startColumn;// start row, start column
	int rowCount, columnCount;// max number of rows and columns that are shown
	int screen1x;
	int screen1y;
	int screen2x;
	int screen2y;
	int sizex, sizey;
	Human clickedh = null;
	Ship clickedShip = null;
	 Port clickedPort = null;
	boolean isIn = true;
	boolean drawHumanMenu = false;
	boolean drawBuildingMenu = false;
	boolean wannabuildsoldier = false;
	boolean showPortMenu = false;
	int paint2a = 0;
	Human menuHuman;
	Barrack soldierBarrack;
	Timer t = new Timer(0, 0);

	Iterator<Human> ih;

	ImageIcon[] castleimg;
	ImageIcon[] HumanImg;
	ImageIcon[] PortImg;
	ImageIcon[] ShipImg;
	ImageIcon[] WoodImg;
	ImageIcon[] FoodImg;
	ImageIcon[] MineImg;
	ImageIcon[] BarrackImg;
	ImageIcon PortMenu;
	ImageIcon HumanMenu;
	ImageIcon BuildingMenu;
	ImageIcon soldierMenueimg;

	double nesbatx;
	double nesbaty;
	double nesbatBx;
	double nesbatBy;
	double nesbateCx;
	double nesbateCy;
	double npx, npy;

	int numberOfPlayers;
	Vector<Player> players;
	Khoone pmap[][];

	public GamePanel(int numberop, int bmap[][], Vector<int[]> putCastle, int r, int c) {

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

		addMouseMotionListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);

		nesbatx = d.width / 5;
		nesbatx /= 887;
		nesbaty = d.height / 5 * 3;
		nesbaty /= 1282;

		nesbatBx = d.width / 5;
		nesbatBx /= 925;
		nesbatBy = d.height / 5 * 3;
		nesbatBy /= 1102;

		nesbateCx = d.width / 5;
		nesbateCx /= 1994;
		nesbateCy = d.height / 5 * 3;
		nesbateCy /= 2371;
		
		npx = d.width/5;
	    npx /= 1994;
	    npy = d.height/5*3;
	    npy /= 2371;

		castleimg = new ImageIcon[4];
		HumanImg = new ImageIcon[4];
		PortImg = new ImageIcon[5];
		ShipImg = new ImageIcon[5];
		WoodImg = new ImageIcon[5];
		FoodImg = new ImageIcon[5];
		MineImg = new ImageIcon[5];
		BarrackImg = new ImageIcon[5];

		castleimg[0] = new ImageIcon("ghasre1.png");
		castleimg[1] = new ImageIcon("ghasre2.png");
		castleimg[2] = new ImageIcon("ghasre3.png");
		castleimg[3] = new ImageIcon("ghasre4.png");
		HumanImg[3] = new ImageIcon("human.worker.walking.1_8x1_100.png");
		HumanImg[1] = new ImageIcon("human.worker.walking.2_8x1_100.png");
		HumanImg[2] = new ImageIcon("human.worker.walking.1_8x1_100.png");
		HumanImg[0] = new ImageIcon("human.worker.walking.3_8x1_100.png");
		PortImg[0] = new ImageIcon("Port.PNG");
		PortImg[1] = new ImageIcon("Port.PNG");
		PortImg[2] = new ImageIcon("Port.PNG");
		PortImg[3] = new ImageIcon("Port.PNG");
		PortImg[4] = new ImageIcon("Port5.PNG");// nim sakhte
		ShipImg[0] = new ImageIcon("ship.png");
		ShipImg[1] = new ImageIcon("ship.png");
		ShipImg[2] = new ImageIcon("ship.png");
		ShipImg[3] = new ImageIcon("ship.png");
		HumanMenu = new ImageIcon("menu2.PNG");
		BuildingMenu = new ImageIcon("menu4.png");
		soldierMenueimg = new ImageIcon("soldiermenu.png");
		PortMenu = new ImageIcon("shipmenu.png");

		pmap = new Khoone[c][r];

		for (int i = 0; i < c; i++) {
			for (int j = 0; j < r; j++) {
				pmap[i][j] = new Khoone();
				pmap[i][j].bmapValue = bmap[i][j];
			}
		}

		players = new Vector<Player>();
		for (int i = 0; i < numberop; i++) {
			int casi, casj;
			casi = putCastle.elementAt(i)[0];
			casj = putCastle.elementAt(i)[1];
			Player p = new Player(i, casi, casj, pmap, r, c);
			pmap[casi][casj] = new Castle(p, casi, casj);
			pmap[casi][casj].bmapValue = 1;
			p.setCastle((Castle) pmap[casi][casj]);
			players.addElement(p);
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
		this.r = r;
		this.c = c;
		numberOfPlayers = numberop;
		rowCount = r / 2;
		columnCount = c / 2;
		mapblcsizex = d.width / columnCount;
		mapblcsizey = d.height / rowCount;
		miniblcsizex = d.width / 5 / c;
		miniblcsizey = d.height / 5 / r;
		startColumn = 0;
		startRow = 0;
		screen1x = 0;
		screen1y = 0;
		screen2x = d.width / 30;
		screen2y = d.height / 30;

		t.start();
		(new Thread(this)).start();
		this.setVisible(true);
	}

	public void paintMenu(Graphics g) {

		double tmpx = d.width / 5;
		tmpx /= 887;
		double tmpy = d.height / 5 * 3;
		tmpy /= 1282;

		if (drawHumanMenu) {
			g.drawImage(HumanMenu.getImage(), screen2x, screen2y + d.height / 5, d.width / 5, d.height / 5 * 3, null);
			g.setColor(Color.white);
			g.drawString("" + menuHuman.wood, screen2x + (int) (tmpx * 360),
					screen2y + d.height / 5 + (int) (tmpy * 632));
			g.drawString("" + menuHuman.fish, screen2x + (int) (tmpx * 360),
					screen2y + d.height / 5 + (int) (tmpy * 785));
			g.drawString("" + menuHuman.food, screen2x + (int) (tmpx * 360),
					screen2y + d.height / 5 + (int) (tmpy * 930));
			g.drawString("" + menuHuman.joon, screen2x + (int) (tmpx * 664),
					screen2y + d.height / 5 + (int) (tmpy * 632));
			g.drawString("" + menuHuman.gold, screen2x + (int) (tmpx * 664),
					screen2y + d.height / 5 + (int) (tmpy * 768));
			g.drawString("" + menuHuman.iron, screen2x + (int) (tmpx * 664),
					screen2y + d.height / 5 + (int) (tmpy * 932));
		}
		if (showPortMenu) {
		      //System.out.println("yes");
		      //System.out.println("mow");
		      g.drawImage(PortMenu.getImage(), screen2x, screen2y + d.height / 5, d.width / 5, d.height / 5 * 3, null);
		    }
	}

	public void paintBuildingMenu(Graphics g) {

		// double tmpx = d.width / 5;
		// tmpx /= 925;
		// double tmpy = d.height / 5 * 3;
		// tmpy /= 1102;

		if (drawBuildingMenu) {
			g.drawImage(BuildingMenu.getImage(), screen2x, screen2y + d.height / 5, d.width / 5, d.height / 5 * 3,
					null);
			g.setColor(Color.white);
		}
	}

	public void paintSoldierMenue(Graphics g) {
		// double tmpx = d.width / 5;
		// tmpx /= 1994;
		// double tmpy = d.width / 5 * 3;
		// tmpy /= 2370;
		if (soldierMenue) {
			g.drawImage(soldierMenueimg.getImage(), screen2x, screen2y + d.height / 5, d.width / 5, d.height / 5 * 3,
					null);
			g.setColor(Color.white);
		}
	}

	public void moveItems() {
		if (paint2a == 0) {
			for (Player p : players) {
				ih = p.humans.iterator();
				while (ih.hasNext()) {
					Human h = ih.next();
					if (h.isAlive == false) {

						pmap[h.i][h.j] = new Khoone();
						pmap[h.i][h.j].bmapValue = 1;
						ih.remove();
						if (ih.hasNext()) {
							h = ih.next();
						} else {
							continue;
						}

					}

					if (h instanceof NormalHuman && ((NormalHuman) h).getResorces() == 300) {
						((NormalHuman) h).goToCastle();
					}

					if (h.isOnFight) {
						h.fight();
					}
					if (h.isOnDestroy) {
						h.Destroy();
					}

					// collect woood kamel nist
					if (h instanceof NormalHuman) {
						if (((NormalHuman) h).isCollectingWood) {
							((NormalHuman) h).collectWood(h.i, h.j);
						}
						if (((NormalHuman) h).isCollectingFood) {
							((NormalHuman) h).collectFood(h.i, h.j);
						}
						if (((NormalHuman) h).isCollectingFish) {
							((NormalHuman) h).collectFish(h.i, h.j);
						}
						if (((NormalHuman) h).isCollectingIron) {
							((NormalHuman) h).collectIron(h.i, h.j);
						}
						if (((NormalHuman) h).isCollectingGold) {
							((NormalHuman) h).collectGold(h.i, h.j);
						}
					}
					if (h.isBuildingPort) {
						((NormalHuman) h).buildPort(h.di, h.dj);
					} else if (h.isBuildingBarrack) {
						((NormalHuman) h).buildBarrack(h.di, h.dj);
					} else if (h.isBuildingFoodQuary) {
						((NormalHuman) h).buildFoodQuery(h.di, h.dj);
					} else if (h.isBuildingMineQuary) {
						((NormalHuman) h).buildMineQuery(h.di, h.dj);
					} else if (h.isBuildingWoodQuary) {
						((NormalHuman) h).buildWoodQuery(h.di, h.dj);
					}

					if (!h.isOnWay) {
						continue;
					}

					// fekr konam aslan be in nemikhorim
					if (h.current == null) {
						continue;
					}

					if (h.current.parent == null) {
						h.isOnWay = false;
						if (pmap[h.i][h.j].bmapValue == 3) {
							((NormalHuman) h).isCollectingWood = true;
						} else if (pmap[h.i][h.j].bmapValue == 9) {
							((NormalHuman) h).isCollectingIron = true;
						} else if (pmap[h.i][h.j].bmapValue == 11) {
							((NormalHuman) h).isCollectingGold = true;
						} else if (pmap[h.i][h.j].bmapValue == 5) {
							((NormalHuman) h).isCollectingFood = true;
						} else if (pmap[h.i][h.j].bmapValue == 9) {
							((NormalHuman) h).isCollectingFish = true;
						}
						continue;
					}

					/**
					 * age jolot jaiie ke bayad building besazi dige naro
					 */
					if (pmap[h.current.parent.i][h.current.parent.j].builder != null
							&& pmap[h.current.parent.i][h.current.parent.j].builder.contains(h)) {
						h.isOnWay = false;
						if (h.wannaBuildp) {
							h.isBuildingPort = true;
						} else if (h.wannaBuildb) {
							h.isBuildingBarrack = true;
						} else if (h.wannaBuildf) {
							h.isBuildingFoodQuary = true;
						} else if (h.wannaBuildm) {
							h.isBuildingMineQuary = true;
						} else if (h.wannaBuildw) {
							h.isBuildingWoodQuary = true;
						}
						continue;
					}

					if (pmap[h.current.parent.i][h.current.parent.j].builder != null
							&& pmap[h.current.parent.i][h.current.parent.j].destroyer.contains(h)) {
						h.isOnWay = false;
						h.Destroy();
						continue;
					}
					if (pmap[h.current.parent.i][h.current.parent.j] instanceof Port) {
						h.isOnWay = false;
						Port port = (Port) pmap[h.current.parent.i][h.current.parent.j];
						NormalHuman nh = (NormalHuman) h;
						nh.buildShip(port, nh.constructingShipType);
						System.out.println("keshtisazi");
						continue;
					}

					/**
					 * agar jolt keshti bood savaresh sho
					 */

					if (pmap[h.current.parent.i][h.current.parent.j] instanceof ShipTransport) {
						h.isOnWay = false;
						ShipTransport ship = (ShipTransport) pmap[h.current.parent.i][h.current.parent.j];
						h.getOnShip(ship);
						// h.setIJ(h.current.i, h.current.j);
						int tmp = pmap[h.current.i][h.current.j].bmapValue;
						pmap[h.current.i][h.current.j] = new Khoone();
						pmap[h.current.i][h.current.j].bmapValue = tmp;
						continue;
					}

					if (pmap[h.current.parent.i][h.current.parent.j] instanceof Human
							&& ((Human) pmap[h.current.parent.i][h.current.parent.j]).owner != h.owner) {
						h.fightingWith = (Human) pmap[h.current.parent.i][h.current.parent.j];
						h.fight();
						((Human) pmap[h.current.parent.i][h.current.parent.j]).fightingWith = h;
						((Human) pmap[h.current.parent.i][h.current.parent.j]).fight();
						h.isOnWay = false;
						continue;
					}

					/**
					 * age mane nabood ke boro age bood rahe jadid peyda kon
					 */
					if (pmap[h.current.parent.i][h.current.parent.j].isWalkable(h.isOnShip)) {
						h.setDir();
						h.current = h.current.parent;
						h.setIJ(h.current.i, h.current.j);
					} else if (h.isOnWay) {
						h.setDir();
						h.current = ((NormalHuman) h).go(h.di, h.dj).parent;
						h.setIJ(h.current.i, h.current.j);
					}

				}

				for (Ship s : p.ships) {

			          if (s instanceof FishingVessel) {
			            if (((FishingVessel) s).isFishing) {
			              ((FishingVessel) s).goFishing();
			            }
			          }

			          if (s.isOnWay) {
			            if (s.current == null) {
			              s.isOnWay = false;
			              continue;
			            }
			            if (s.current.parent == null) {
			              s.isOnWay = false;
			              continue;
			            } else {
			              Khoone tmp = pmap[s.current.parent.i][s.current.parent.j];
			              if (tmp.bmapValue == 2) {
			                if (s instanceof FishingVessel) {
			                  s.isOnWay = false;
			                  ((FishingVessel) s).isFishing = true;
			                  continue;
			                }
			              }

			              if (tmp instanceof Castle) {
			                if (s instanceof FishingVessel) {
			                  s.isOnWay = false;
			                  //s.current = null;
			                  ((FishingVessel) s).khaliKon();
			                  continue;
			                }
			              }

			              else {
			                s.current = s.current.parent;
			                s.setIJ(s.current.i, s.current.j);
			              }
			            }
			          }
			        }
			      
			
				
			}
		}
	}

	public void paint2a(Graphics g) {

		for (int i = startColumn; i < startColumn + columnCount && i < c; i++) {
			for (int j = startRow; j < startRow + rowCount && j < r; j++) {
				int y = j - startRow;
				int x = i - startColumn;
				if (pmap[i][j] instanceof Castle) {
					g.drawImage(castleimg[((Castle) pmap[i][j]).owner.id].getImage(), x * mapblcsizex, y * mapblcsizey,
							mapblcsizex, mapblcsizey, null);
				}

				if (pmap[i][j] instanceof NormalHuman && !(((NormalHuman) pmap[i][j]).isOnShip)) {

					NormalHuman nh = (NormalHuman) pmap[i][j];
					int dstx0, dsty0;
					int dstx1, dsty1;
					dstx0 = x * mapblcsizex;
					dstx1 = mapblcsizex / 2 + (x * mapblcsizex);
					dsty0 = y * mapblcsizey;
					dsty1 = mapblcsizey / 4 * 3 + (y * mapblcsizey);

					if (paint2a == 0 && nh.isOnWay) {
						if (nh.dir == 2) {
							dsty0 -= mapblcsizey / 2;
							dsty1 -= mapblcsizey / 2;
						}
						if (nh.dir == 3) {
							dstx0 -= mapblcsizex / 2;
							dstx1 -= mapblcsizex / 2;
						}
						if (nh.dir == 0) {
							dsty0 += mapblcsizey / 2;
							dsty1 += mapblcsizey / 2;
						}
						if (nh.dir == 1) {
							dstx0 += mapblcsizex / 2;
							dstx1 += mapblcsizex / 2;
						}
					}

					g.drawImage(HumanImg[nh.dir].getImage(), dstx0, dsty0, dstx1, dsty1, nh.imageId * 35, 0,
							nh.imageId * 35 + 35, 50, null);

				}

				if (pmap[i][j] instanceof Port) {
					// System.out.println("port " + ((Port)
					// pmap[i][j]).constructionLevel);
					if (((Port) pmap[i][j]).constructionLevel < 100) {
						g.drawImage(PortImg[4].getImage(), x * mapblcsizex, y * mapblcsizey, mapblcsizex, mapblcsizey,
								null);
					} else {
						g.drawImage(PortImg[((Port) pmap[i][j]).owner.id].getImage(), x * mapblcsizex, y * mapblcsizey,
								mapblcsizex, mapblcsizey, null);
					}
				}
				if (pmap[i][j] instanceof FoodQuery) {
					if (((FoodQuery) pmap[i][j]).constructionLevel < 100) {
						g.drawImage(FoodImg[4].getImage(), x * mapblcsizex, y * mapblcsizey, mapblcsizex, mapblcsizey,
								null);
					} else {
						g.drawImage(FoodImg[((FoodQuery) pmap[i][j]).owner.id].getImage(), x * mapblcsizex,
								y * mapblcsizey, mapblcsizex, mapblcsizey, null);
					}
				}
				if (pmap[i][j] instanceof WoodQuery) {
					if (((WoodQuery) pmap[i][j]).constructionLevel < 100) {
						g.drawImage(WoodImg[4].getImage(), x * mapblcsizex, y * mapblcsizey, mapblcsizex, mapblcsizey,
								null);
					} else {
						g.drawImage(FoodImg[((WoodQuery) pmap[i][j]).owner.id].getImage(), x * mapblcsizex,
								y * mapblcsizey, mapblcsizex, mapblcsizey, null);
					}
				}
				if (pmap[i][j] instanceof MineQuery) {
					if (((MineQuery) pmap[i][j]).constructionLevel < 100) {
						g.drawImage(MineImg[4].getImage(), x * mapblcsizex, y * mapblcsizey, mapblcsizex, mapblcsizey,
								null);
					} else {
						g.drawImage(MineImg[((MineQuery) pmap[i][j]).owner.id].getImage(), x * mapblcsizex,
								y * mapblcsizey, mapblcsizex, mapblcsizey, null);
					}
				}
				if (pmap[i][j] instanceof Barrack) {
					System.out.println(((Barrack) pmap[i][j]).constructionLevel);
					if (((Barrack) pmap[i][j]).constructionLevel < 100) {
						g.drawImage(PortImg[4].getImage(), x * mapblcsizex, y * mapblcsizey, mapblcsizex, mapblcsizey,
								null);
					} else {
						g.drawImage(PortImg[((Barrack) pmap[i][j]).owner.id].getImage(), x * mapblcsizex,
								y * mapblcsizey, mapblcsizex, mapblcsizey, null);
					}
				}
				if (pmap[i][j] instanceof Soldier) {
					g.drawImage(HumanImg[0].getImage(), x * mapblcsizex, y * mapblcsizey, mapblcsizex, mapblcsizey,
							null);
				}

				if (pmap[i][j] instanceof Ship) {
					g.drawImage(ShipImg[((Ship) pmap[i][j]).owner.id].getImage(), x * mapblcsizex, y * mapblcsizey,
							mapblcsizex, mapblcsizey, null);
				}
			}
		}

	}

	public void paint2b(Graphics g) {

		for (int i = 0; i < c; i++) {
			for (int j = 0; j < r; j++) {
				if (pmap[i][j] instanceof Castle) {// fish
					g.drawImage(castleimg[0].getImage(), screen2x + i * miniblcsizex, screen2y + j * miniblcsizey,
							miniblcsizex, miniblcsizey, null);
				}
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		int pic = 0;

		// drawing island and sea of A part
		for (int i = startColumn; i < startColumn + columnCount && i < c; i++) {
			for (int j = startRow; j < startRow + rowCount && j < r; j++) {
				int y = j - startRow;
				int x = i - startColumn;
				g.drawImage(BG_img[t.season][t.day].getImage(), x * mapblcsizex, y * mapblcsizey, mapblcsizex,
						mapblcsizey, null);
				pic = 0;
				if ((pmap[i][j].bmapValue % 2) != 0) {
					if (j > 0)
						pic = pic + (pmap[i][j - 1].bmapValue % 2);
					if (i + 1 < c)
						pic = pic + 2 * (pmap[i + 1][j].bmapValue % 2);
					if (j + 1 < r)
						pic = pic + 4 * (pmap[i][j + 1].bmapValue % 2);
					if (i > 0)
						pic = pic + 8 * (pmap[i - 1][j].bmapValue % 2);
					g.drawImage(LL_img[t.season][t.day][pic].getImage(), x * mapblcsizex, y * mapblcsizey, mapblcsizex,
							mapblcsizey, null);
				}
			}
		}

		// drawing mountain of A part
		for (int i = startColumn; i < startColumn + columnCount && i < c; i++) {
			for (int j = startRow; j < startRow + rowCount && j < r; j++) {
				int y = j - startRow;
				int x = i - startColumn;
				pic = 0;
				if (pmap[i][j].bmapValue == 7 || pmap[i][j].bmapValue == 9 || pmap[i][j].bmapValue == 11) {// mount
					if (j > 0)
						if (pmap[i][j - 1].bmapValue == 7 || pmap[i][j - 1].bmapValue == 9
								|| pmap[i][j - 1].bmapValue == 11)
							pic = pic + (1);
					if (i + 1 < c)
						if (pmap[i + 1][j].bmapValue == 7 || pmap[i + 1][j].bmapValue == 9
								|| pmap[i + 1][j].bmapValue == 11)
							pic = pic + 2 * (1);
					if (j + 1 < r)
						if (pmap[i][j + 1].bmapValue == 7 || pmap[i][j + 1].bmapValue == 9
								|| pmap[i][j + 1].bmapValue == 11)
							pic = pic + 4 * (1);
					if (i > 0)
						if (pmap[i - 1][j].bmapValue == 7 || pmap[i - 1][j].bmapValue == 9
								|| pmap[i - 1][j].bmapValue == 11)
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
				if (pmap[i][j].bmapValue == 2) {// fish
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
				if (pmap[i][j].bmapValue == 9) {// Iron
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
				if (pmap[i][j].bmapValue == 11) {// gold
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
				if (pmap[i][j].bmapValue == 5) {// FarmLand
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
				if (pmap[i][j].bmapValue == 3) {// tree
					g.drawImage(ForGTree_img[t.season][t.day].getImage(), x * mapblcsizex, y * mapblcsizey,
							mapblcsizex / 2, mapblcsizey / 2, null);
				}
			}
		}

		paint2a(g);

		// drawing sea and island if B part
		for (int i = 0; i < c; i++) {
			for (int j = 0; j < r; j++) {
				g.drawImage(BG_img[t.season][t.day].getImage(), screen2x + i * miniblcsizex,
						screen2y + j * miniblcsizey, miniblcsizex, miniblcsizey, null);
				pic = 0;

				if ((pmap[i][j].bmapValue % 2) != 0) {
					if (j > 0)
						pic = pic + (pmap[i][j - 1].bmapValue % 2);
					if (i + 1 < c)
						pic = pic + 2 * (pmap[i + 1][j].bmapValue % 2);
					if (j + 1 < r)
						pic = pic + 4 * (pmap[i][j + 1].bmapValue % 2);
					if (i > 0)
						pic = pic + 8 * (pmap[i - 1][j].bmapValue % 2);
					g.drawImage(LL_img[t.season][t.day][pic].getImage(), screen2x + i * miniblcsizex,
							screen2y + j * miniblcsizey, miniblcsizex, miniblcsizey, null);
				}
			}
		}

		// drawing mountain of B part
		for (int i = 0; i < c; i++) {
			for (int j = 0; j < r; j++) {

				pic = 0;

				if (pmap[i][j].bmapValue == 7 || pmap[i][j].bmapValue == 9 || pmap[i][j].bmapValue == 11) {// mount
					if (j > 0)
						if (pmap[i][j - 1].bmapValue == 7 || pmap[i][j - 1].bmapValue == 9
								|| pmap[i][j - 1].bmapValue == 11)
							pic = pic + (1);
					if (i + 1 < c)
						if (pmap[i + 1][j].bmapValue == 7 || pmap[i + 1][j].bmapValue == 9
								|| pmap[i + 1][j].bmapValue == 11)
							pic = pic + 2 * (1);
					if (j + 1 < r)
						if (pmap[i][j + 1].bmapValue == 7 || pmap[i][j + 1].bmapValue == 9
								|| pmap[i][j + 1].bmapValue == 11)
							pic = pic + 4 * (1);
					if (i > 0)
						if (pmap[i - 1][j].bmapValue == 7 || pmap[i - 1][j].bmapValue == 9
								|| pmap[i - 1][j].bmapValue == 11)
							pic = pic + 8 * (1);
					g.drawImage(HL_img[t.season][t.day][pic].getImage(), screen2x + i * miniblcsizex,
							screen2y + j * miniblcsizey, miniblcsizex, miniblcsizey, null);
				}
			}
		}

		// fish of B part
		for (int i = 0; i < c; i++) {
			for (int j = 0; j < r; j++) {
				if (pmap[i][j].bmapValue == 2) {// fish
					g.drawImage(ForGFish_img[t.day].getImage(), screen2x + i * miniblcsizex,
							screen2y + j * miniblcsizey, miniblcsizex / 2, miniblcsizey / 2, null);
				}
			}
		}

		// Gold B part
		for (int i = 0; i < c; i++) {
			for (int j = 0; j < r; j++) {
				if (pmap[i][j].bmapValue == 11) {// Gold
					g.drawImage(ForGGold_img[t.day].getImage(), screen2x + i * miniblcsizex,
							screen2y + j * miniblcsizey, miniblcsizex / 2, miniblcsizey / 2, null);
				}
			}
		}

		// Iron of B part
		for (int i = 0; i < c; i++) {
			for (int j = 0; j < r; j++) {
				if (pmap[i][j].bmapValue == 9) {// Iron
					g.drawImage(ForGIron_img[t.day].getImage(), screen2x + i * miniblcsizex,
							screen2y + j * miniblcsizey, miniblcsizex / 2, miniblcsizey / 2, null);
				}
			}
		}

		// Tree of B part
		for (int i = 0; i < c; i++) {
			for (int j = 0; j < r; j++) {
				if (pmap[i][j].bmapValue == 3) {// tree
					g.drawImage(ForGTree_img[t.season][t.day].getImage(), screen2x + i * miniblcsizex,
							screen2y + j * miniblcsizey, miniblcsizex / 2, miniblcsizey / 2, null);
				}
			}
		}

		// farmland of B part
		for (int i = 0; i < c; i++) {
			for (int j = 0; j < r; j++) {
				if (pmap[i][j].bmapValue == 5) {// farmland
					g.drawImage(ForGFarmLand_img[t.day].getImage(), screen2x + i * miniblcsizex,
							screen2y + j * miniblcsizey, miniblcsizex / 2, miniblcsizey / 2, null);
				}
			}
		}

		paint2b(g);

		g.draw3DRect(screen2x, screen2y, miniblcsizex * c, miniblcsizey * r, true);

		// minimap rec
		sizex = columnCount * miniblcsizex;
		sizey = rowCount * miniblcsizey;
		g.drawRect(screen2x + startColumn * miniblcsizex, screen2y + startRow * miniblcsizey, sizex, sizey);
		paintMenu(g);
		paintBuildingMenu(g);
		paintSoldierMenue(g);

	}

	boolean b = true;

	private boolean soldierMenue;

	public void checkFight() {
		for (Player p : players) {
			for (Human h : p.humans) {
				if (h instanceof Soldier && !h.isOnFight) {
					for (int i = 1; i < 26 && h.i + i < c && b; i++) {
						if (pmap[h.i + i][h.j] instanceof Human && ((Human) pmap[h.i + i][h.j]).owner != h.owner) {
							this.dispatchEvent(new MyEvent(this, Messages.FIGHT, h, (Human) pmap[h.i + i][h.j]));
							b = false;
						}
					}
					for (int i = 1; i < 26 && h.j + i < r && b; i++) {
						if (pmap[h.i][h.j + i] instanceof Human && ((Human) pmap[h.i][h.j + i]).owner != h.owner) {
							this.dispatchEvent(new MyEvent(this, Messages.FIGHT, h, (Human) pmap[h.i][h.j + i]));
							b = false;
						}
					}
					for (int i = -1; i > -26 && h.i + i >= 0 && b; i--) {
						if (pmap[h.i + i][h.j] instanceof Human && ((Human) pmap[h.i + i][h.j]).owner != h.owner) {
							this.dispatchEvent(new MyEvent(this, Messages.FIGHT, h, (Human) pmap[h.i + i][h.j]));
							b = false;
						}
					}
					for (int i = -1; i > -26 && h.j + i >= 0 && b; i--) {
						if (pmap[h.i][h.j + i] instanceof Human && ((Human) pmap[h.i][h.j + i]).owner != h.owner) {
							this.dispatchEvent(new MyEvent(this, Messages.FIGHT, h, (Human) pmap[h.i][h.j + i]));
							b = false;
						}
					}
					b = true;
				} else if (h instanceof NormalHuman && !h.isOnFight) {// normalhuman
					for (int i = 1; i < 16 && h.i + i < c && b; i++) {
						if (pmap[h.i + i][h.j] instanceof Human && ((Human) pmap[h.i + i][h.j]).owner != h.owner) {
							this.dispatchEvent(new MyEvent(this, Messages.FIGHT, h, (Human) pmap[h.i + i][h.j]));
							b = false;
						}
					}
					for (int i = 1; i < 16 && h.j + i < r && b; i++) {
						if (pmap[h.i][h.j + i] instanceof Human && ((Human) pmap[h.i][h.j + i]).owner != h.owner) {
							this.dispatchEvent(new MyEvent(this, Messages.FIGHT, h, (Human) pmap[h.i][h.j + i]));
							b = false;
						}
					}
					for (int i = -1; i > -16 && h.i + i >= 0 && b; i--) {
						if (pmap[h.i + i][h.j] instanceof Human && ((Human) pmap[h.i + i][h.j]).owner != h.owner) {
							this.dispatchEvent(new MyEvent(this, Messages.FIGHT, h, (Human) pmap[h.i + i][h.j]));
							b = false;
						}
					}
					for (int i = -1; i > -16 && h.j + i >= 0 && b; i--) {
						if (pmap[h.i][h.j + i] instanceof Human && ((Human) pmap[h.i][h.j + i]).owner != h.owner) {
							this.dispatchEvent(new MyEvent(this, Messages.FIGHT, h, (Human) pmap[h.i][h.j + i]));
							b = false;
						}
					}
					b = true;
				}
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int eat = 0;
		while (true) {

			if (cursor[0] < mapblcsizex && startColumn > 0) {
				startColumn--;
			}
			if (cursor[0] > mapblcsizex * rowCount - mapblcsizex && startColumn + columnCount < c) {
				startColumn++;
			}
			if (cursor[1] < mapblcsizey && startRow > 0) {
				startRow--;
			}
			if (cursor[1] > mapblcsizey * rowCount - mapblcsizey && startRow + rowCount < r) {
				startRow++;
			}

			paint2a++;
			paint2a %= 3;
			eat++;
			eat %= 10;
			if (eat == 0) {
				for (Player p : players) {
					for (Human h : p.humans) {
						if (h instanceof NormalHuman) {
							h.owner.myCastle.gandom -= 1;
						} else if (h instanceof Soldier) {
							h.owner.myCastle.gandom -= 2;
						} // mahigir
					}
				}
			}

			// checkFight();
			moveItems();
			repaint();
			long d = System.currentTimeMillis();
			while (d + 100 > System.currentTimeMillis())
				;

		}

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		cursor[0] = arg0.getX();
		cursor[1] = arg0.getY();
		if (cursor[0] > screen2x && cursor[1] > screen2y && cursor[0] + sizex < screen2x + miniblcsizex * c
				&& cursor[1] + sizey < screen2y + miniblcsizey * r) {

			startColumn = (arg0.getX() - screen2x) / miniblcsizex;
			startRow = (arg0.getY() - screen2y) / miniblcsizey;
		}

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		cursor[0] = arg0.getX();
		cursor[1] = arg0.getY();

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		int ci, cj;
		ci = e.getX() / mapblcsizex;
		cj = e.getY() / mapblcsizey;

		ci = ci + startColumn;
		cj = cj + startRow;

		// agar kharej az mahdude menu bood
		if (!(e.getX() > screen2x && e.getX() < screen2x + d.width / 5 && e.getY() > screen2y + d.width / 5
				&& e.getY() < screen2y + d.width / 5 * 3)) {
			drawHumanMenu = false;
			drawBuildingMenu = false;
			soldierMenue = false;
			showPortMenu = false;

			if (wannabuildsoldier && soldierBarrack != null) {
				wannabuildsoldier = false;
				int i = 0, j = 0;
				if (soldierBarrack.i + 1 < c) {
					i = soldierBarrack.i + 1;
				}
				if (soldierBarrack.i - 1 >= 0) {
					i = soldierBarrack.i - 1;
				}
				if (soldierBarrack.j + 1 < r) {
					j = soldierBarrack.j + 1;
				}
				if (soldierBarrack.i - 1 >= 0) {
					j = soldierBarrack.j - 1;
				}
				if (!(i == 0 && j == 0)) {
					Soldier s = new Soldier(soldierBarrack.owner, i, j);
					soldierBarrack.owner.humans.addElement(s);
					s.go(ci, cj);
				}
			}
			if (pmap[ci][cj].isWalkable(false)) {
				if (menuHuman != null) {
					pmap[ci][cj].builder.addElement((NormalHuman) menuHuman);
					menuHuman.go(ci, cj);
					menuHuman = null;
				}
			}

		}
		if (pmap[ci][cj] instanceof Ship) {
		      clickedShip = (Ship) pmap[ci][cj];
		    } else if (clickedShip != null) {
		      if (pmap[ci][cj].isWalkable(true)) {
		        clickedShip.go(ci, cj);
		        if (clickedShip instanceof FishingVessel) {
		          ((FishingVessel) clickedShip).isFishing = false;
		        }
		      }

		      if (pmap[ci][cj].bmapValue == 2) {
		        if (clickedShip instanceof FishingVessel) {
		          ((FishingVessel) clickedShip).go(ci, cj);
		          ((FishingVessel) clickedShip).setfifj(ci, cj);
		        }
		      }
		    }

		if (clickedh != null) {

			clickedh.isOnFight = false;
			if (pmap[ci][cj] instanceof Port) {
		        showPortMenu = true;
		        clickedPort = (Port) pmap[ci][cj];
		      }
			// not complete
			if (clickedh instanceof NormalHuman)
				((NormalHuman) clickedh).isCollectingWood = false;

			if (pmap[ci][cj].isWalkable(clickedh.isOnShip)) {
				clickedh.go(ci, cj);
				clickedh.isOnWay = true;

			}
			if (clickedh instanceof NormalHuman && pmap[ci][cj] instanceof Port) {
				if (clickedh.owner == ((Port) pmap[ci][cj]).owner && ((Port) pmap[ci][cj]).constructionLevel<100   ) {
					clickedh.wannaBuildp = true;
					pmap[ci][cj].builder.addElement((NormalHuman) clickedh);
					clickedh.go(ci, cj);
				} else if(clickedh.owner != ((Port) pmap[ci][cj]).owner){
					clickedh.fightwithBuild = (Building) pmap[ci][cj];
					clickedh.Destroy();
				}
				
			} else if (clickedh instanceof NormalHuman && pmap[ci][cj] instanceof WoodQuery) {
				if (clickedh.owner == ((WoodQuery) pmap[ci][cj]).owner&& ((WoodQuery) pmap[ci][cj]).constructionLevel<100) {
					clickedh.wannaBuildw = true;
					pmap[ci][cj].builder.addElement((NormalHuman) clickedh);
					clickedh.go(ci, cj);
				} else if(clickedh.owner != ((WoodQuery) pmap[ci][cj]).owner){
					clickedh.fightwithBuild = (Building) pmap[ci][cj];
					clickedh.Destroy();
				}
				//clickedh = null;
			} else if (clickedh instanceof NormalHuman && pmap[ci][cj] instanceof FoodQuery) {
				if (clickedh.owner == ((FoodQuery) pmap[ci][cj]).owner && ((FoodQuery) pmap[ci][cj]).constructionLevel<100) {
					clickedh.wannaBuildf = true;
					pmap[ci][cj].builder.addElement((NormalHuman) clickedh);
					clickedh.go(ci, cj);
				} else if(clickedh.owner != ((FoodQuery) pmap[ci][cj]).owner){
					clickedh.fightwithBuild = (Building) pmap[ci][cj];
					clickedh.Destroy();
				}
				//clickedh = null;
			} else if (clickedh instanceof NormalHuman && pmap[ci][cj] instanceof MineQuery) {
				if (clickedh.owner == ((MineQuery) pmap[ci][cj]).owner  && ((MineQuery) pmap[ci][cj]).constructionLevel<100) {
					clickedh.wannaBuildm = true;
					pmap[ci][cj].builder.addElement((NormalHuman) clickedh);
					clickedh.go(ci, cj);
				} else if(clickedh.owner != ((MineQuery) pmap[ci][cj]).owner){
					System.out.println("destroy");
					clickedh.fightwithBuild = (Building) pmap[ci][cj];
					clickedh.Destroy();
				}
				clickedh = null;
			} else if (clickedh instanceof NormalHuman && pmap[ci][cj] instanceof Barrack) {
				if (clickedh.owner == ((Barrack) pmap[ci][cj]).owner && ((Barrack) pmap[ci][cj]).constructionLevel<100) {
					clickedh.wannaBuildb = true;
					pmap[ci][cj].builder.addElement((NormalHuman) clickedh);
					clickedh.go(ci, cj);
				} else if(clickedh.owner != ((Barrack) pmap[ci][cj]).owner) {
					System.out.println("destroooy");
					clickedh.fightwithBuild = (Building) pmap[ci][cj];
					pmap[ci][cj].destroyer.addElement(clickedh);
					clickedh.go(ci, cj);

				}
				
				clickedh = null;
			}

			if (pmap[ci][cj] instanceof Ship && !clickedh.isOnShip) {
				clickedh.go(ci, cj);
				clickedh.isOnWay = true;
			}

			if (pmap[ci][cj] instanceof Human && ((Human) pmap[ci][cj]).owner != clickedh.owner) {
				clickedh.go(ci, cj);
				clickedh.isOnWay = true;
				clickedh.isGoFight = true;
			}

			if(!showPortMenu){
		        clickedh = null;
		     }

		} else if (pmap[ci][cj] instanceof Human && e.getButton() == MouseEvent.BUTTON1) {
			clickedh = (Human) pmap[ci][cj];
		}

		if (e.getButton() == MouseEvent.BUTTON3 && pmap[ci][cj] instanceof Human) {
			drawHumanMenu = true;
			menuHuman = (Human) pmap[ci][cj];
		}
		if (e.getButton() == MouseEvent.BUTTON3 && pmap[ci][cj] instanceof Barrack
				&& ((Barrack) pmap[ci][cj]).owner.myCastle.gandom > 2000
				&& ((Barrack) pmap[ci][cj]).owner.myCastle.iron > 500
				&& ((Barrack) pmap[ci][cj]).owner.myCastle.gold > 250
				&& ((Barrack) pmap[ci][cj]).owner.myCastle.wood > 600
				&& ((Barrack) pmap[ci][cj]).constructionLevel == 100) {
			((Barrack) pmap[ci][cj]).owner.myCastle.gandom -= 2000;
			((Barrack) pmap[ci][cj]).owner.myCastle.iron -= 500;
			((Barrack) pmap[ci][cj]).owner.myCastle.gold -= 250;
			((Barrack) pmap[ci][cj]).owner.myCastle.wood -= 600;
			soldierMenue = true;
			soldierBarrack = (Barrack) pmap[ci][cj];
		}

		int bx, by;
		int bx1, by1;
		bx = screen2x + (int) (636 * nesbateCx);
		bx1 = screen2x + (int) (1216 * nesbateCx);
		by1 = screen2y + d.height / 5 + (int) (1754 * nesbateCy);
		by = screen2y + d.height / 5 + (int) (1546 * nesbateCy);

		if (soldierMenue && e.getX() > bx && e.getX() < bx1 && e.getY() > by && e.getY() < by1) { // soldier
			wannabuildsoldier = true;
		}

		int tx0, ty0;
	    int tx1, ty1;
	    
	    tx0 = screen2x + (int) (646 * npx);
	    tx1 = screen2x + (int) (1255 * npx);
	    ty1 = screen2y + d.height / 5 + (int) (1598 * npy);
	    ty0 = screen2y + d.height / 5 + (int) (1381 * npy);
	    
	    System.out.println(ty0+" "+e.getY()+" "+ty1);
	    System.out.println(tx0+" "+e.getX()+" "+tx1);
	        
	    if (showPortMenu && e.getX()<tx1 && e.getX()>tx0 && e.getY()>ty0 && e.getY()<ty1 && clickedh!=null) {// va in ke rooye button ship click kard
	      if(clickedh instanceof NormalHuman){
	        clickedh.go(clickedPort.i, clickedPort.j);
	        ((NormalHuman) clickedh).constructingShipType = 0;
	        showPortMenu = false;
	        clickedh = null;
	        clickedPort = null;
	        System.out.println("hoo");
	      }
	      
	    }
	    
	    ty1 = screen2y + d.height / 5 + (int) (1907 * npy);
	    ty0 = screen2y + d.height / 5 + (int) (1721 * npy);
	    if (showPortMenu && e.getX()<tx1 && e.getX()>tx0 && e.getY()>ty0 && e.getY()<ty1 && clickedh!=null) {// va in ke rooye button ship click kard
	      if(clickedh instanceof NormalHuman){
	        clickedh.go(clickedPort.i, clickedPort.j);
	        ((NormalHuman) clickedh).constructingShipType = 1;
	        showPortMenu = false;
	        clickedh = null;
	        clickedPort = null;
	      }
	      
	    }
	    
		int bxx, byy;
		int bxx1, byy1;
		bxx = screen2x + (int) (258 * nesbatx);
		bxx1 = screen2x + (int) (627 * nesbatx);
		byy1 = screen2y + d.height / 5 + (int) (1129 * nesbaty);
		byy = screen2y + d.height / 5 + (int) (1033 * nesbaty);

		if (drawHumanMenu && e.getX() > bxx && e.getX() < bxx1 && e.getY() > byy && e.getY() < byy1) {

			drawHumanMenu = false;
			drawBuildingMenu = true;
		}

		int px, py, pxx, pyy;
		px = screen2x + (int) (315 * nesbatBx);
		pxx = screen2x + (int) (562 * nesbatBx);
		py = screen2y + d.height / 5 + (int) (421 * nesbatBy);
		pyy = screen2y + d.height / 5 + (int) (496 * nesbatBy);

		if (drawBuildingMenu && e.getX() > px && e.getX() < pxx && e.getY() > py && e.getY() < pyy) {
			// port
			menuHuman.wannaBuildp = true;

		}
		int Bx, Bxx, By, Byy;
		Bx = screen2x + (int) (315 * nesbatBx);
		Bxx = screen2x + (int) (562 * nesbatBx);
		By = screen2y + d.height / 5 + (int) (539 * nesbatBy);
		Byy = screen2y + d.height / 5 + (int) (635 * nesbatBy);

		if (drawBuildingMenu && e.getX() > Bx && e.getX() < Bxx && e.getY() > By && e.getY() < Byy) {
			menuHuman.wannaBuildb = true;
		}
		int wx, wxx, wy, wyy;
		wx = screen2x + (int) (315 * nesbatBx);
		wxx = screen2x + (int) (562 * nesbatBx);
		wy = screen2y + d.height / 5 + (int) (659 * nesbatBy);
		wyy = screen2y + d.height / 5 + (int) (755 * nesbatBy);

		if (drawBuildingMenu && e.getX() > wx && e.getX() < wxx && e.getY() > wy && e.getY() < wyy) {
			menuHuman.wannaBuildw = true;
		}
		int fx, fxx, fy, fyy;
		fx = screen2x + (int) (315 * nesbatBx);
		fxx = screen2x + (int) (562 * nesbatBx);
		fy = screen2y + d.height / 5 + (int) (767 * nesbatBy);
		fyy = screen2y + d.height / 5 + (int) (863 * nesbatBy);

		if (drawBuildingMenu && e.getX() > fx && e.getX() < fxx && e.getY() > fy && e.getY() < fyy) {
			menuHuman.wannaBuildf = true;
		}
		int mx, mxx, my, myy;
		mx = screen2x + (int) (315 * nesbatBx);
		mxx = screen2x + (int) (562 * nesbatBx);
		my = screen2y + d.height / 5 + (int) (894 * nesbatBy);
		myy = screen2y + d.height / 5 + (int) (990 * nesbatBy);

		if (drawBuildingMenu && e.getX() > mx && e.getX() < mxx && e.getY() > my && e.getY() < myy) {
			menuHuman.wannaBuildm = true;
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void processComponentEvent(ComponentEvent e) {

		if (e.getID() == Messages.FIGHT) {

			Human h1 = ((MyEvent) e).geth1();
			Human h2 = ((MyEvent) e).geth2();
			if (h1.go(h2.i, h2.j) != null) {
				h1.go(h2.i, h2.j);
				h1.isOnWay = true;
			}

		}
		super.processComponentEvent(e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0 && rowCount > 2 && columnCount > 2) {
			rowCount--;
			columnCount--;
			mapblcsizey = d.height / rowCount;
			mapblcsizex = d.width / columnCount;
		} else {
			if (startColumn > 0) {
				startColumn--;
			}
			if (startRow > 0) {
				startRow--;
			}
			if (rowCount < r) {
				rowCount++;
			}
			if (columnCount < c) {
				columnCount++;
			}
			mapblcsizey = d.height / rowCount;
			mapblcsizex = d.width / columnCount;
		}

	}

}
