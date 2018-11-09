package no.hvl.dat159.dtf.mqtt.publishers;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import no.hvl.dat159.dtf.mqtt.MQTTCredentials;

public class MQTTPublisherController {

	protected String topic = MQTTCredentials.HEAT_TOPIC;
	
	protected int qos = 1;
	private String clientId = MQTTCredentials.HEAT_CLIENT_ID;
	private String broker = MQTTCredentials.BROKER;
	private String username = MQTTCredentials.USERNAME;
	private String password = MQTTCredentials.PASSWORD_TOKEN;

	protected MqttClient publisherClient;
	
	public MQTTPublisherController() {
		System.out.println("[CONTROLLER PUB] Thread id: " + Thread.currentThread().getId());
	}

	public void publish(boolean activate) throws MqttPersistenceException, MqttException, InterruptedException {
		System.out.println("Publishing to " + topic + ": " + activate);
		
		MqttMessage message = new MqttMessage(("" + activate).getBytes());
		message.setQos(qos);

		publisherClient.publish(topic, message);
	}
	
	public void connect() {
		MemoryPersistence persistence = new MemoryPersistence();

		try {
			publisherClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			connOpts.setUserName(username);
			connOpts.setPassword(password.toCharArray());
			System.out.println("Connecting to broker: " + broker);
			publisherClient.connect(connOpts);
			System.out.println("Connected");

		} catch (MqttException e) {
			System.out.println("reason " + e.getReasonCode());
			System.out.println("msg " + e.getMessage());
			System.out.println("loc " + e.getLocalizedMessage());
			System.out.println("cause " + e.getCause());
			System.out.println("excep " + e);
			e.printStackTrace();
		}
	}

	public void disconnect() throws MqttException {
		publisherClient.disconnect();
	}

}
