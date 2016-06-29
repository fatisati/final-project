package Phase2;

import java.io.Serializable;
import java.util.Vector;

public class Khoone implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean isBmap;//az map faze 1 hast ya na
	int bmapValue;
	Vector<NormalHuman> builder = new Vector<NormalHuman>();
	Vector<Human> destroyer = new Vector<Human>();
	
	public boolean isWalkable(boolean isOnShip){
		
		
		if(isOnShip){
			if(bmapValue==0){
				return true;
			}
			else{
				return false;
			}
		}
		
		if(this instanceof Building){
			return false;
		}
		if(this instanceof NormalHuman){
			return false;
		}
		
		
		if(bmapValue==1 || bmapValue==5 || bmapValue==3){
			return true;
		}
		return false;
	}
}
