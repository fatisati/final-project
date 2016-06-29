package Phase2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public abstract class Human extends Khoone {

	public int joon;
	int food;
	String job;
	int i, j;
	int fish;
	int gandom;
	int gold;
	int iron;
	int wood;
	int di, dj;
	boolean koohnavard;
	String type;
	ImageIcon img;
	Player owner;
	Tile current;
	int dir =0;
	int imageId =0;
	int constructionLevel = 0;
	boolean isOnWay = false;
	boolean Waitplz = false;
	boolean isOnShip = false;
	boolean isOnFight = false;
	boolean isGoFight = false;
	boolean building = false;
	boolean wannaBuildp = false;
	boolean wannaBuildb = false;
	boolean wannaBuildw = false;
	boolean wannaBuildm = false;
	boolean wannaBuildf = false;
	boolean isAlive = true;
	boolean isOnDestroy = false;
	
	boolean isBuildingPort = false;
	boolean isBuildingBarrack = false;
	boolean isBuildingWoodQuary = false;
	boolean isBuildingFoodQuary = false;
	boolean isBuildingMineQuary = false;
	
	public Human fightingWith;
	public Building fightwithBuild;
	Ship ship;
	// rectangle
	static int portBuildSpeed = 5;
	static final int capacity = 300;

	public abstract void doWork();

	public abstract void showOptions();

	public Human(int i, int j, Player owner) {
		this.i = i;
		this.j = j;
		this.owner = owner;

	}

	public void setIJ(int ni, int nj) {
		int tmp = owner.pmap[i][j].bmapValue;
		owner.pmap[i][j] = new Khoone();
		owner.pmap[i][j].bmapValue = tmp;
		this.i = ni;
		this.j = nj;
		int tmp2 = owner.pmap[i][j].bmapValue;
		owner.pmap[i][j] = this;
		owner.pmap[i][j].bmapValue = tmp2;
	}

	public void getOnShip(ShipTransport ship) {

		// current = go(ship.i, ship.j);

		if (ship.addHuman(this)) {
			isOnShip = true;
			i = ship.i;
			j = ship.j;
		}

	}

	public void fight() {
		isOnFight = true;
		if (this instanceof NormalHuman) {
			if (fightingWith.joon > 0 && joon > 0) {
				fightingWith.joon -= 20;
			}
			if (fightingWith.joon < 1) {
				isOnFight = false;
				fightingWith.isOnFight = false;
				fightingWith.isAlive  =false;
				fightingWith = null;
			}
			if(joon<1){
				isAlive = false;
				isOnFight = false;
				fightingWith.isOnFight = false;
				fightingWith = null;
			}
		} else {
			if (fightingWith.joon > 0 && joon > 0) {
				fightingWith.joon -= 70;
			}
			if (fightingWith.joon < 1) {
				fightingWith.isAlive = false;
				isOnFight = false;
				fightingWith = null;
			}
			if(joon<1){
				isAlive = false;
				isOnFight = false;
				fightingWith.isOnFight = false;
				fightingWith = null;
			}
		}
	}
	
	public  void Destroy(){
		isOnDestroy = true;
		if(fightwithBuild.joon>0){
			System.out.println("hello");
			fightwithBuild.joon--;
		}else{
			isOnDestroy = false;
			
			fightwithBuild.isAlive = false;
			owner.pmap[fightwithBuild.i][fightwithBuild.j] = new Khoone();
			owner.pmap[fightwithBuild.i][fightwithBuild.j].bmapValue = 1;
			fightwithBuild =null;
			
		}
	}

	public Tile go(int dii, int djj) {
		PathFinder pf = new PathFinder(dii, djj, owner.c * owner.r, owner.pmap, owner.r, owner.c);
		Tile ans = pf.path(i, j, isOnShip);
		di = dii;
		dj = djj;
		if (ans != null) {
			isOnWay = true;
		}
		if(ans!=null){
			current = ans;
		}
		
		return ans;
	}
	
	public void setDir(){
	    if(current.parent == null){
	      return ;
	    }
	    
	    if(current.parent.i == i+1){
	      dir = 3;
	    }
	    
	    if(current.parent.i == i-1){
	      dir = 1;
	    }
	    
	    if(current.parent.j == j+1){
	      dir = 2;
	    }
	    
	    if(current.parent.j == j-1){
	      dir = 0;
	    }
	    imageId++;
	    imageId%=8;
	  }

}
