package Phase2;

import java.awt.Component;
import java.awt.event.ComponentEvent;

public class MyEvent extends ComponentEvent {

	Human h1,h2;
	public MyEvent(Component arg0, int arg1,Human h1,Human h2) {
		super(arg0, arg1);
		this.h1 = h1;
		this.h2 = h2;
	}
	public Human  geth1(){
		return h1;
	}
	public Human  geth2(){
		return h2;
	}

}
