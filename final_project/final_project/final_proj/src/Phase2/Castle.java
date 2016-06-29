package Phase2;

import javax.swing.ImageIcon;

import com.sun.javafx.geom.Rectangle;

public class Castle extends Building {
	Rectangle CastleRec;

	 public int iron, gold, gandom, wood,fish;

	public Castle(Player owner, int i, int j) {
		super(owner, i, j);
		joon = 100;
		this.gold = 20000;
		this.gandom = 20000;
		this.iron = 20000;
		this.wood = 20000;
	}
	public void addIron(int addamount) {
		iron += addamount;
	}
	public void addGold(int addamount) {
		gold += addamount;
	}
	public void addWood(int addamount) {
		wood += addamount;
	}
	public void addGandom(int addamount) {
		gandom += addamount;
	}
	
}
