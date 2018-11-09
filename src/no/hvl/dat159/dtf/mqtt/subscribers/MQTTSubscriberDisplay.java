package no.hvl.dat159.dtf.mqtt.subscribers;

import org.eclipse.paho.client.mqttv3.MqttException;

import no.hvl.dat159.dtf.actuators.DisplayActuator;
import no.hvl.dat159.dtf.mqtt.MQTTCredentials;
import no.hvl.dat159.dtf.mqtt.MQTTSubscriber;

public class MQTTSubscriberDisplay extends MQTTSubscriber {

	
	private DisplayActuator display;
	
	public MQTTSubscriberDisplay(DisplayActuator display) throws MqttException {
		super(MQTTCredentials.TEMPERATURE_SUB_CLIENT_ID2, MQTTCredentials.TEMPERATURE_TOPIC);
		this.display = display;
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
		
		display.display("Temp is: " + temp);
	}
	
	

}
