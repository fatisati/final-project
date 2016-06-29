package map;

import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Sound extends Thread {

	private int num;

	public Sound(int num) {
		// TODO Auto-generated constructor stub
		this.num = num;
	}

	@Override
	public void run() {
		if (num == 2) {
			// TODO Auto-generated method stub
			try {
				FileInputStream file = new FileInputStream("Northern_mountains.mp3");
				Player playMP3 = new Player(file);
				playMP3.play();
			} catch (Exception e) {
				System.out.println("not   sounddddddddddddddddddddddddddddddddddddddddddddddd");
			}
		} else if (num == 1) {
			try {
				FileInputStream file = new FileInputStream("05 - It's a Game (Reprise).mp3");
				Player playMP3 = new Player(file);
				playMP3.play();
			} catch (Exception e) {
				System.out.println("not 1  sounddddddddddddddddddddddddddddddddddddddddddddddd");
			}
		}
	}

}
