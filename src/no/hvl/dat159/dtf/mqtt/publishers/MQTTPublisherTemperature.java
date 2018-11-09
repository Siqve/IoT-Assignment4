package no.hvl.dat159.dtf.mqtt.publishers;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import no.hvl.dat159.dtf.mqtt.MQTTCredentials;
import no.hvl.dat159.dtf.mqtt.MQTTPublisher;
import no.hvl.dat159.dtf.sensors.TemperatureSensor;

public class MQTTPublisherTemperature extends MQTTPublisher {

	private TemperatureSensor sensor;
	
	public MQTTPublisherTemperature(TemperatureSensor sensor) {
		super(MQTTCredentials.TEMPERATURE_CLIENT_ID, MQTTCredentials.TEMPERATURE_TOPIC);
		this.sensor = sensor;
	}

	@Override
	protected void publish() throws MqttPersistenceException, MqttException, InterruptedException {
		for (int i = 0; i < 10; i++) {

			String temp = String.valueOf(sensor.sense());

			System.out.println("Publishing temperature: " + temp);

			MqttMessage message = new MqttMessage(temp.getBytes());
			message.setQos(qos);

			publisherClient.publish(topic, message);

			Thread.sleep(10000);
		}

	}

}
