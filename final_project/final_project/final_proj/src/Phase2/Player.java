package Phase2;

import java.util.Vector;

public class Player {

	int id;
	int castleI, castleJ;
	Khoone pmap[][];
	int r, c;
	int fish;
	Vector<Building> buildings;
	Vector<Human> humans;
	Vector<Ship>ships;
	Castle myCastle;

	public Player(int id, int castleI, int castleJ, Khoone pmap[][], int r, int c) {
		this.id = id;
		this.castleI = castleI;
		this.castleJ = castleJ;
		this.pmap = pmap;
		this.r = r;
		this.c = c;
		humans = new Vector<>();
		ships = new Vector<Ship>();
		for (int i = 0; i < 3; i++) {
			if (castleI + i < c && castleJ + 1 < r) {
				NormalHuman nh = new NormalHuman(castleI + i, castleJ + 1, this);
				pmap[nh.i][nh.j] = nh;
				pmap[nh.i][nh.j].bmapValue = 1;
				humans.addElement(nh);
			}
		}
		for (int i = 0; i < 3; i++) {
			if (castleI + i < c && castleJ + 2 < r) {
				NormalHuman nh = new NormalHuman(castleI + i, castleJ + 2, this);
				pmap[nh.i][nh.j] = nh;
				pmap[nh.i][nh.j].bmapValue = 1;
				humans.addElement(nh);
			}
		}
		for (int i = 0; i < 3; i++) {
			if (castleI + i < c && castleJ + 3 < r) {
				NormalHuman nh = new NormalHuman(castleI + i, castleJ + 3, this);
				pmap[nh.i][nh.j] = nh;
				pmap[nh.i][nh.j].bmapValue = 1;
				humans.addElement(nh);
			}
		}
		if (castleI < c && castleJ + 4 < r) {
			NormalHuman nh = new NormalHuman(castleI, castleJ + 4, this);
			pmap[nh.i][nh.j] = nh;
			pmap[nh.i][nh.j].bmapValue = 1;
			humans.addElement(nh);
		}
		int k=1;
		
		while (humans.size()<11) {
			if(castleI+k<c && castleJ-1>=0){
				NormalHuman nh = new NormalHuman(castleI+k, castleJ - 1, this);
				pmap[nh.i][nh.j] = nh;
				pmap[nh.i][nh.j].bmapValue = 1;
			    humans.addElement(nh);
			    k++;
			}
			else if(castleI-k>=0 && castleJ-1>=0){
				NormalHuman nh = new NormalHuman(castleI-k, castleJ - 1, this);
				pmap[nh.i][nh.j] = nh;
				pmap[nh.i][nh.j].bmapValue = 1;
			    humans.addElement(nh);
			    k++;
			}
			
		}
	}

	
	
	public void setCastle(Castle c) {
		myCastle = c;
	}

}
