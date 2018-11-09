package no.hvl.dat159.dtf.actuators;

import no.hvl.dat159.dtf.Room;

public class HeatingActuator {
	
	public void activate() {
		Room.state = true;
	}
	
	public void disable() {
		Room.state = false;
	}

}
