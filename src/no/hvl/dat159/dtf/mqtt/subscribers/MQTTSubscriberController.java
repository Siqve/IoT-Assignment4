package no.hvl.dat159.dtf.mqtt.subscribers;

import org.eclipse.paho.client.mqttv3.MqttException;

import no.hvl.dat159.dtf.Controller;
import no.hvl.dat159.dtf.mqtt.MQTTCredentials;
import no.hvl.dat159.dtf.mqtt.MQTTSubscriber;

public class MQTTSubscriberController extends MQTTSubscriber {

	
	private Controller controller;
	
	public MQTTSubscriberController(Controller controller) throws MqttException {
		super(MQTTCredentials.TEMPERATURE_SUB_CLIENT_ID, MQTTCredentials.TEMPERATURE_TOPIC);
		this.controller = controller;
	}

	@Override
	public void messageArrived(String message) {
		double temp;
		try {
			temp = Double.parseDouble(message);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		controller.checkTemperature(temp);
	}
	
	

}
