package map;

public class Timer extends Thread{
	public int season = 0;
	public int day;//1 if night 0 if not
	public long d = System.currentTimeMillis();
	
	public Timer(int day, int season){
		this.day = day;
		this.season = season;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			d = System.currentTimeMillis();
			while (d + 10000 > System.currentTimeMillis())
				;
			day++;
			day%=2;
			d = System.currentTimeMillis();
			while (d + 10000 > System.currentTimeMillis())
				;
			season++;
			season%=4;
			day++;
			day%=2;
		}
	}
}
