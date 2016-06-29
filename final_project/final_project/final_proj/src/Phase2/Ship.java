package Phase2;

import java.security.acl.Owner;
import java.util.Vector;

import com.sun.org.apache.bcel.internal.classfile.PMGClass;

public class Ship extends Khoone {

	Player owner;
	int i, j;

	boolean isOnWay;
	Tile current;
	NormalHuman nakhoda;

	public Ship(Player owner, int i, int j) {
		this.owner = owner;
		this.i = i;
		this.j = j;
		current = null;
		nakhoda = new NormalHuman(i, j, owner);
		nakhoda.isOnShip = true;
		owner.ships.addElement(this);
	}

	public void go(int di, int dj) {
		isOnWay = true;
		current = nakhoda.go(di, dj);
	}

	public void setIJ(int ni, int nj) {

		int tmp = owner.pmap[ni][nj].bmapValue;
		owner.pmap[ni][nj] = this;
		int tmp2 = this.bmapValue;
		this.bmapValue = tmp;
		owner.pmap[i][j] = new Khoone();
		owner.pmap[i][j].bmapValue = tmp2;
		i = ni;
		j = nj;

		nakhoda.i = i;
		nakhoda.j = j;
	}

}

class ShipTransport extends Ship {

	Vector<Human> humans;

	public ShipTransport(Player owner, int i, int j) {
		super(owner, i, j);
		humans = new Vector<>();
		// TODO Auto-generated constructor stub
	}

	public boolean addHuman(Human h) {
		if (humans.size() < 3) {
			humans.addElement(h);
			System.out.println(humans.size() + " are in:)");
			return true;
		}
		return false;
	}
	
	@Override
	public void setIJ(int ni, int nj) {
	
		super.setIJ(ni, nj);
		for (Human h : humans) {
			h.i = i;
			h.j = j;
		}
		
	}

}

class FishingVessel extends Ship{
	
	int fish;
	boolean isFishing;
	int fi, fj;//fishing area i and fishing area j

	public FishingVessel(Player owner, int i, int j) {
		super(owner, i, j);
		fish = 0;
		isFishing = false;
		// TODO Auto-generated constructor stub
	}
	
	public void goFishing(){
		if(fish<100){
			fish+=10;
		}
		else if(i!=owner.castleI){
			go(owner.castleI, owner.castleJ);
		}
		
	}
	
	public void setfifj(int fi, int fj){
		this.fi = fi;
		this.fj = fj;
	}
	
	public void khaliKon(){
		owner.fish += fish;
		fish = 0;
		if(owner.pmap[fi][fj].bmapValue == 2){
			go(fi, fj);
		}
	}
}