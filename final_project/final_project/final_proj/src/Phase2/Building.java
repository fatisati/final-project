package Phase2;

import javax.swing.ImageIcon;

public abstract class Building extends Khoone{
	
	Player owner;
	int joon = 100;
	int i, j;
	boolean isAlive = true;
	String name;
	ImageIcon img;
	Khoone kh;
	int constructionLevel;//alan cheghadesh sakhte shode
	public Building(Player owner, int i, int j){
		this.owner = owner;
		this.i = i;
		this.j = j;
	}
}

class Port extends Building{
	public Port(Player owner, int i, int j){
		
		super(owner, i, j);
		constructionLevel = 0;
	}
	
}

class Barrack extends Building{
	public Barrack(Player owner, int i, int j){
		
		super(owner, i, j);
		constructionLevel = 0;
	}
	
}

class WoodQuery extends Building{
	public WoodQuery(Player owner, int i, int j){
		
		super(owner, i, j);
		constructionLevel = 0;
	}
	
}

class MineQuery extends Building{
	public MineQuery(Player owner, int i, int j){
		
		super(owner, i, j);
		constructionLevel = 0;
	}
	
}

class FoodQuery extends Building{
	public FoodQuery(Player owner, int i, int j){
		
		super(owner, i, j);
		constructionLevel = 0;
	}
	
}