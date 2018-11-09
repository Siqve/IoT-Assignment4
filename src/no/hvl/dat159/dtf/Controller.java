package no.hvl.dat159.dtf;

import org.eclipse.paho.client.mqttv3.MqttException;

import no.hvl.dat159.dtf.mqtt.publishers.MQTTPublisherController;

public class Controller implements Runnable {
	
	private MQTTPublisherController publisher;
	
	private double minTemp;
	private double maxTemp;
	
	
	public Controller(double minTemp, double maxTemp) {
		System.out.println("[CONTROLLER] Thread id: " + Thread.currentThread().getId());
		this.minTemp = minTemp;
		this.maxTemp = maxTemp;
	}
	
	public synchronized void checkTemperature(double temperature) {
		//Publish to heat topic
		if (temperature < minTemp) {
			publishToHeater(true);
		} else if (temperature > maxTemp) {
			publishToHeater(false);
		}
	}
	
	private synchronized void publishToHeater(boolean activate) {
		try {
			publisher.connect();
			publisher.publish(activate);
			publisher.disconnect();
		} catch (MqttException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println("runnyrun");
		publisher = new MQTTPublisherController();
	}
	
}
