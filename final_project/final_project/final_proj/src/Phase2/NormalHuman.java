package Phase2;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.ImageIcon;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class NormalHuman extends Human {

	ImageIcon[] img;
	boolean isCollectingWood = false;
	boolean isCollectingFood = false;
	boolean isCollectingFish = false;
	boolean isCollectingIron = false;
	boolean isCollectingGold = false;
    int constructingShipType = 0;
	
	

	public NormalHuman(int i, int j, Player owner) {
		super(i, j, owner);

		current = new Tile(i, j, 0, 0, null, null, 0, 0, owner.r, owner.c);
		img = new ImageIcon[4];
		wood = 0;
		super.joon = 500;
		// TODO Auto-generated constructor stub
	}

	static int woodCollectSpeed0 = 2;
	static int woodCollectSpeed1 = 3;// faster

	public int getResorces() {
		return (3 * wood + 5 * gold + 4 * iron + fish + gandom);
	}

	public void goToCastle() {
		this.current = this.go(owner.castleI, owner.castleJ);
	}

	/**
	 * get i and j of the jungle which collecting food from
	 * 
	 * @param i
	 * @param j
	 */
	public void collectWood(int i, int j) {

		int speed;// 1 wood per second
		if (true) {// if there is a wood query on the i, j jungle
			speed = woodCollectSpeed1;// decreasing speed by 50 percent
		} else {
			speed = woodCollectSpeed0;
		}
		if (this.getResorces() + 3 * speed <= capacity) {
			wood += speed;
		}
	}

	public void collectFood(int i, int j) {

		int speed;// 1 wood per second
		if (true) {// if there is a wood query on the i, j jungle
			speed = woodCollectSpeed1;// decreasing speed by 50 percent
		} else {
			speed = woodCollectSpeed0;
		}
		if (this.getResorces() + 3 * speed <= capacity) {
			gandom += speed;
		}
	}

	public void collectFish(int i, int j) {

		int speed;// 1 wood per second
		if (true) {// if there is a wood query on the i, j j
			speed = woodCollectSpeed1;// decreasing speed by 50 percent
		} else {
			speed = woodCollectSpeed0;
		}
		if (this.getResorces() + 3 * speed <= capacity) {
			fish += speed;
		}
	}

	public void collectIron(int i, int j) {

		int speed;// 1 wood per second
		if (true) {// if there is a wood query on the i, j
			speed = woodCollectSpeed1;// decreasing speed by 50 percent
		} else {
			speed = woodCollectSpeed0;
		}
		if (this.getResorces() + 3 * speed <= capacity) {
			iron += speed;
		}
	}

	public void collectGold(int i, int j) {

		int speed;// 1 wood per second
		if (true) {// if there is a wood query on the i, j jungle
			speed = woodCollectSpeed1;// decreasing speed by 50 percent
		} else {
			speed = woodCollectSpeed0;
		}
		if (this.getResorces() + 3 * speed <= capacity) {
			gold += speed;
		}
	}

	public boolean fullCapacity() {
		if (getResorces() >= capacity) {
			return true;
		}
		return false;
	}
	
	

	public void buildPort(int i, int j) {// bandar besaze
		boolean isNearWater = false;
		if (i + 1 < owner.c && owner.pmap[i + 1][j].bmapValue == 0) {
			isNearWater = true;
		}

		if (j + 1 < owner.r && owner.pmap[i][j + 1].bmapValue == 0) {
			isNearWater = true;
		}

		if (i - 1 > -1 && owner.pmap[i - 1][j].bmapValue == 0) {
			isNearWater = true;
		}

		if (j - 1 > -1 && owner.pmap[i][j - 1].bmapValue == 0) {
			isNearWater = true;
		}
		if (isNearWater) {
			if (owner.pmap[i][j] instanceof Port) {
				if (((Port) owner.pmap[i][j]).constructionLevel < 100) {
					((Port) owner.pmap[i][j]).constructionLevel+=10;
				}
				else{
					owner.pmap[i][j].builder = new Vector<NormalHuman>();
					wannaBuildp = false;
					isBuildingPort = false;
				}
			} else {
				owner.pmap[i][j] = new Port(owner, i, j);
				owner.pmap[i][j].bmapValue = 1;
			}
		}
	}

	public void buildBarrack(int i, int j) {// bandar besaze

		if (owner.pmap[i][j] instanceof Barrack) {
			if (((Barrack) owner.pmap[i][j]).constructionLevel < 100) {
				((Barrack) owner.pmap[i][j]).constructionLevel++;
//				System.out.println("barrack"+((Barrack) owner.pmap[i][j]).constructionLevel);
			}else{
				owner.pmap[i][j].builder = new Vector<NormalHuman>();;
				wannaBuildb = false;
				isBuildingBarrack = false;
			}
		} else {
			owner.pmap[i][j] = new Barrack(owner, i, j);
			owner.pmap[i][j].bmapValue = 1;
		}
	}

	public void buildFoodQuery(int i, int j) {// bandar besaze

		if (owner.pmap[i][j] instanceof FoodQuery) {
			if (((FoodQuery) owner.pmap[i][j]).constructionLevel < 100) {
				((FoodQuery) owner.pmap[i][j]).constructionLevel++;
			}else{
				owner.pmap[i][j].builder = new Vector<NormalHuman>();;
				wannaBuildf = false;
				isBuildingFoodQuary= false;
			}
		} else {
			owner.pmap[i][j] = new FoodQuery(owner, i, j);
			owner.pmap[i][j].bmapValue = 1;
		}
	}

	public void buildMineQuery(int i, int j) {// bandar besaze

		if (owner.pmap[i][j] instanceof MineQuery) {
			if (((MineQuery) owner.pmap[i][j]).constructionLevel < 100) {
				((MineQuery) owner.pmap[i][j]).constructionLevel++;
			}else{
				owner.pmap[i][j].builder = new Vector<NormalHuman>();;
				wannaBuildm = false;
				isBuildingMineQuary = false;
			}
		} else {
			owner.pmap[i][j] = new MineQuery(owner, i, j);
			owner.pmap[i][j].bmapValue = 1;
		}
	}

	public void buildWoodQuery(int i, int j) {// bandar besaze

		if (owner.pmap[i][j] instanceof WoodQuery) {
			if (((WoodQuery) owner.pmap[i][j]).constructionLevel < 100) {
				((WoodQuery) owner.pmap[i][j]).constructionLevel++;
			}else{
				owner.pmap[i][j].builder = new Vector<NormalHuman>();;
				wannaBuildw = false;
				isBuildingWoodQuary = false;
			}
		} else {
			owner.pmap[i][j] = new WoodQuery(owner, i, j);
			owner.pmap[i][j].bmapValue = 1;
		}
	}

	public void buildCastle(Castle c) {
		if (c.constructionLevel < 100) {
			c.constructionLevel++;
		}
	}

	public boolean buildShip(Port p, int type) {
	    int ii, jj;
	    ii = p.i;
	    jj = p.j;
	    if(type==0){
	      if (ii + 1 < owner.c && owner.pmap[ii + 1][j].bmapValue == 0 && !(owner.pmap[ii + 1][jj] instanceof Ship)) {
	        owner.pmap[ii + 1][jj] = new FishingVessel(owner, ii + 1, jj);
	        owner.pmap[ii + 1][jj].bmapValue = 0;
	        return true;
	      }

	      if (j + 1 < owner.r && owner.pmap[ii][jj + 1].bmapValue == 0 && !(owner.pmap[ii][jj + 1] instanceof Ship)) {
	        owner.pmap[ii][jj + 1] = new FishingVessel(owner, ii, jj + 1);
	        owner.pmap[ii][jj + 1].bmapValue = 0;
	        return true;
	      }

	      if (i - 1 > -1 && owner.pmap[ii - 1][jj].bmapValue == 0 && !(owner.pmap[ii - 1][jj] instanceof Ship)) {
	        owner.pmap[ii - 1][jj] = new FishingVessel(owner, ii - 1, jj);
	        owner.pmap[ii - 1][jj].bmapValue = 0;
	        return true;
	      }

	      if (j - 1 > -1 && owner.pmap[ii][jj - 1].bmapValue == 0 && !(owner.pmap[ii][jj - 1] instanceof Ship)) {
	        owner.pmap[ii][jj - 1] = new FishingVessel(owner, ii, jj - 1);
	        owner.pmap[ii][jj - 1].bmapValue = 0;
	        return true;
	      }
	      return false;
	    }
	    else{
	      if (ii + 1 < owner.c && owner.pmap[ii + 1][j].bmapValue == 0 && !(owner.pmap[ii + 1][jj] instanceof Ship)) {
	        owner.pmap[ii + 1][jj] = new ShipTransport(owner, ii + 1, jj);
	        owner.pmap[ii + 1][jj].bmapValue = 0;
	        return true;
	      }

	      if (j + 1 < owner.r && owner.pmap[ii][jj + 1].bmapValue == 0 && !(owner.pmap[ii][jj + 1] instanceof Ship)) {
	        owner.pmap[ii][jj + 1] = new ShipTransport(owner, ii, jj + 1);
	        owner.pmap[ii][jj + 1].bmapValue = 0;
	        return true;
	      }

	      if (i - 1 > -1 && owner.pmap[ii - 1][jj].bmapValue == 0 && !(owner.pmap[ii - 1][jj] instanceof Ship)) {
	        owner.pmap[ii - 1][jj] = new ShipTransport(owner, ii - 1, jj);
	        owner.pmap[ii - 1][jj].bmapValue = 0;
	        return true;
	      }

	      if (j - 1 > -1 && owner.pmap[ii][jj - 1].bmapValue == 0 && !(owner.pmap[ii][jj - 1] instanceof Ship)) {
	        owner.pmap[ii][jj - 1] = new ShipTransport(owner, ii, jj - 1);
	        owner.pmap[ii][jj - 1].bmapValue = 0;
	        return true;
	      }
	      return false;
	    }
	    
	  }
	@Override
	public void doWork() {
		// TODO Auto-generated method stub
		if (fullCapacity()) {
			// gotToCastle();
			doWork();
		}

	}

	@Override
	public void showOptions() {
		// TODO Auto-generated method stub

	}

}
