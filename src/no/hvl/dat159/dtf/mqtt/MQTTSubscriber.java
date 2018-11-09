package no.hvl.dat159.dtf.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public abstract class MQTTSubscriber  implements MqttCallback {

	private String message;

	public MQTTSubscriber(String clientId, String topic) throws MqttException {

		int qos = 1; // 1 - This client will acknowledge to the Device Gateway that messages are
						// received
		String broker = MQTTCredentials.BROKER;
		String username = MQTTCredentials.USERNAME;
		String password = MQTTCredentials.PASSWORD_TOKEN;

		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
		connOpts.setUserName(username);
		connOpts.setPassword(password.toCharArray());

		System.out.println("Connecting to broker: " + broker);

		MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
		client.setCallback(this);
		client.connect(connOpts);
		System.out.println("Connected");

		client.subscribe(topic, qos);
		System.out.println("Subscribed to message");

	}

	/**
	 * @see MqttCallback#connectionLost(Throwable)
	 */
	public void connectionLost(Throwable cause) {
		System.out.println("Connection lost because: " + cause);
		System.exit(1);

	}

	/**
	 * @see MqttCallback#messageArrived(String, MqttMessage)
	 */
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String stringMessage = new String(message.getPayload());
		this.setMessage(stringMessage);
		
		messageArrived(stringMessage);
	}
	
	public abstract void messageArrived(String message);
	

	/**
	 * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
	 */
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}