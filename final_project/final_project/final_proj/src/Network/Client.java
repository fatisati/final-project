package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Phase2.Khoone;

public class Client {
	private Socket socket = null;
	private ObjectInputStream inputStream = null;//
	private ObjectOutputStream outputStream = null;
	private boolean isConnected = false;
	
	public void communicate(Khoone kh){
		while(!isConnected){
			try {
				socket = new Socket("localhost", 4445);
				isConnected = true;
				outputStream = new ObjectOutputStream(socket.getOutputStream());
				outputStream.writeObject(kh);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
