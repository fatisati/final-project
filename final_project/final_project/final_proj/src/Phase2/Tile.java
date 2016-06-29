package Phase2;

import java.util.Vector;

public class Tile {
	int i, j;
	int g, h;
	Khoone pmap[][];
	int r, c;
	Tile parent;
	int di, dj;

	public Tile(int i, int j, int g, int h, Khoone pmap[][], Tile par, int di, int dj, int r, int c) {
		this.i = i;
		this.j = j;
		this.g = g;
		this.h = h;
		this.pmap = pmap;
		this.parent = par;
		this.di = di;
		this.dj = dj;
		this.r = r;
		 this.c = c;
	}
	
	


	public Vector<Tile> walkableAdj(boolean isOnShip) {
		Vector<Tile> ans = new Vector<>();
		
		if (i+1<c && pmap[i + 1][j].isWalkable(isOnShip) || (i+1==di && j==dj)) {
			int nh = ghadr(di - i - 1) + ghadr(dj - j);
			ans.add(new Tile(i + 1, j, this.g + 1, nh, pmap, this, di, dj, r, c));
		}
		if (i-1>-1 && pmap[i - 1][j].isWalkable(isOnShip) || (i-1==di && j==dj)) {
			int nh = ghadr(di - i + 1) + ghadr(dj - j);
			ans.add(new Tile(i - 1, j, this.g + 1, nh, pmap, this, di, dj, r, c));
		}
		if (j+1<r && pmap[i][j + 1].isWalkable(isOnShip) || (i==di && j+1==dj)) {
			int nh = ghadr(di - i) + ghadr(dj - j - 1);
			ans.add(new Tile(i, j + 1, this.g + 1, nh, pmap, this,di, dj, r, c));
		}
		if (j-1>-1 && pmap[i][j - 1].isWalkable(isOnShip) || (i==di && j-1==dj)) {
			int nh = ghadr(di - i) + ghadr(dj - j + 1);
			ans.add(new Tile(i, j - 1, this.g + 1, nh, pmap, this, di, dj, r, c));
		}
		
		
		return ans;
	}

	public int ghadr(int x) {
		return x > 0 ? x : -1 * x;
	}
	
	public int score(){
		return g+h;
	}
}
