package no.hvl.dat159.dtf;

import org.eclipse.paho.client.mqttv3.MqttException;

import no.hvl.dat159.dtf.actuators.HeatingActuator;
import no.hvl.dat159.dtf.mqtt.publishers.MQTTPublisherTemperature;
import no.hvl.dat159.dtf.mqtt.subscribers.MQTTSubscriberHeating;
import no.hvl.dat159.dtf.sensors.TemperatureSensor;

public class RoomMain {

	public static void main(String[] args) {
		//HeatingActuator
		HeatingActuator heatingActuator = new HeatingActuator();
		try {
			new MQTTSubscriberHeating(heatingActuator);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
		//TemperatureSensor
		TemperatureSensor tempSensor = new TemperatureSensor();
		Thread threadBob = new Thread(new MQTTPublisherTemperature(tempSensor));
		threadBob.start();
		
	}
	
}
