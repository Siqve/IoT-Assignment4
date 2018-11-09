package no.hvl.dat159.dtf.sensors;

import no.hvl.dat159.dtf.Room;

public class TemperatureSensor {

	private long lastSense = System.currentTimeMillis();
	
	
	public double sense() {
		long now = System.currentTimeMillis();
		
		long timenow = System.currentTimeMillis();
		long timeinterval = timenow - lastSense;

		Room.temperature = Room.temperature + (0.0002 * (Room.state ? 1 : -1) * timeinterval);
		
		lastSense = now;
		return Room.temperature;
	}
	
	
}
