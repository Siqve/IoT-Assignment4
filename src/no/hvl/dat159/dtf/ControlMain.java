package no.hvl.dat159.dtf;

import org.eclipse.paho.client.mqttv3.MqttException;

import no.hvl.dat159.dtf.actuators.DisplayActuator;
import no.hvl.dat159.dtf.mqtt.subscribers.MQTTSubscriberController;
import no.hvl.dat159.dtf.mqtt.subscribers.MQTTSubscriberDisplay;

public class ControlMain {

	public static void main(String[] args) {
		double minTemp = 18;
		double maxTemp = 23;
		
		//Controller
		Controller controller = new Controller(minTemp, maxTemp);
		Thread controllerThread = new Thread(controller);
		controllerThread.start();
		try {
			new MQTTSubscriberController(controller);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
		DisplayActuator display = new DisplayActuator();
		try {
			new MQTTSubscriberDisplay(display);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
	}
	
}
