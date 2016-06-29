package Phase2;

import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

public class PathFinder {
	
	int i, j;//khoone mabda
	Vector<Tile> openList;
	Vector<Tile> closedList;
	Comparator<Tile> comp;
	Khoone pmap[][];
	int maxSize;//bishtarin tool momken baraye yek masir
	int r, c;
	
	
	public PathFinder(int i, int j, int maxS, Khoone pmap[][], int r, int c){
		
		this.i = i;
		this.j = j;
		this.maxSize = maxS;
		closedList = new Vector<>();
		openList = new Vector<>();
		
		comp = new Comparator<Tile>() {

			@Override
			public int compare(Tile arg0, Tile arg1) {
				// TODO Auto-generated method stub
				return arg0.g + arg0.h > arg1.g + arg1.h ? 1 : arg0.g + arg0.h == arg1.g + arg1.h ? 0 : -1;
			}
		};
		
		this.pmap = pmap;
		this.r = r;
		this.c = c;
		
	}
	
	public int ghadr(int x) {
		return x > 0 ? x : -1 * x;
	}


	public Tile path(int di, int dj, boolean isOnShip) {
		
		int counter = 0;
		openList.clear();
		closedList.clear();
		Tile nt = new Tile(i, j, 0, this.ghadr(di-i)+this.ghadr(dj-j),pmap, null, di, dj, r, c);
		openList.addElement(nt);
		boolean end = false;
		Tile ans = null;
		while (!end && counter<maxSize && openList.size()>0) {
			java.util.Collections.sort(openList, comp);
			Tile s = openList.firstElement();
			closedList.add(s);
			openList.remove(s);
			counter++;
			
			for (Tile t : s.walkableAdj(isOnShip)) {
				
				if(t.i == di && t.j==dj){
					end = true;
					ans = t;
				}
//				if(closedList.isEmpty()){
//					end = true;
//				}
				/**
				 * if t is in closedList ignore it
				 */
				boolean a1 = false;
				for (Tile t1 : closedList) {
					if (t.i == t1.i && t.j == t1.j) {
						a1 = true;
						break;
					}
				}
				if (a1) {
					continue;
				}
				
				/**
				 * if t is in openlist update it
				 */
				a1 = false;
				for(Tile t1: openList){
					if(t1.i == t.i && t1.j==t.j){
						if(t1.score()>t.score()){
							t1.g = t.g;
							t1.h = t.h;
							t1.parent = s;
						}
						a1= true;
					}
				}
				
				/**
				 * if t is not in openList add it
				 */
				if(!a1){
					openList.add(t);
				}

			}
		}
		//System.out.println(ans);
		return ans;

	}
	
	
}
