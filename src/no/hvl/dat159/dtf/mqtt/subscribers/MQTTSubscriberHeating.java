package no.hvl.dat159.dtf.mqtt.subscribers;

import org.eclipse.paho.client.mqttv3.MqttException;

import no.hvl.dat159.dtf.actuators.HeatingActuator;
import no.hvl.dat159.dtf.mqtt.MQTTCredentials;
import no.hvl.dat159.dtf.mqtt.MQTTSubscriber;

public class MQTTSubscriberHeating extends MQTTSubscriber {

	
	private HeatingActuator heatingActuator;
	
	public MQTTSubscriberHeating(HeatingActuator heatingActuator) throws MqttException {
		super(MQTTCredentials.HEAT_SUB_CLIENT_ID, MQTTCredentials.HEAT_TOPIC);
		this.heatingActuator = heatingActuator;
	}

	@Override
	public void messageArrived(String message) {
		boolean activate = Boolean.valueOf(message);
		if (activate)
			heatingActuator.activate();
		else 
			heatingActuator.disable();
	}
	
	

}
