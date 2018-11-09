package no.hvl.dat159.dtf.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public abstract class MQTTPublisher implements Runnable {

	protected String topic;
	
	
	protected int qos = 1;
	private String clientId;
	private String broker = MQTTCredentials.BROKER;
	private String username = MQTTCredentials.USERNAME;
	private String password = MQTTCredentials.PASSWORD_TOKEN;

	protected MqttClient publisherClient;

	public MQTTPublisher(String clientId, String topic) {
		this.clientId = clientId;
		this.topic = topic;
	}

	protected abstract void publish() throws MqttPersistenceException, MqttException, InterruptedException;

	
	
	private void connect() {

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

	private void disconnect() throws MqttException {

		publisherClient.disconnect();

	}

	public void run() {

		try {

			System.out.println("Publisher to topic " + topic + " running");

			connect();

			publish();

			disconnect();

			System.out.println("Publisher to topic " + topic + " stopping");

		} catch (Exception ex) {
			System.out.println("Publisher to topic " + topic + ": " + ex.getMessage());
			ex.printStackTrace();
		}

	}
}