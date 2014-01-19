package com.frengky.mobile.messaging.app;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Client {
    
    public Client() {
    }
    
    public static void main(String[] args) {
    	String host = "127.0.0.1";
    	int port = 1883;
    	String userName = "frengky";
    	String password = "frengky";
    	String topic = "test-topic";
    	
    	MessagingDebugger test = new MessagingDebugger();
    	test.Connect(host, port, userName, password);
    	test.Subscribe(topic);
    	test.Listen();
    }
}

class MessagingDebugger implements MqttCallback {
	private MqttClient mqtt;
	private MqttConnectOptions mqttConnectOptions;
	private boolean _isConnected = false;
	
	private static Logger log = Logger.getLogger(MessagingDebugger.class);
	
	public MessagingDebugger() {
	}
	
	public void Connect(String host, int port, String userName, String password) {
		try {
			mqttConnectOptions = new MqttConnectOptions();
			mqttConnectOptions.setUserName(userName);
			mqttConnectOptions.setPassword(password.toCharArray());
			
			mqtt = new MqttClient("tcp://"+host+":"+port, "mobile-messaging-test");
			
			log.info("Connecting.. with user=" + userName + ", pass=" + password);
			mqtt.setCallback(this);
			mqtt.connect(mqttConnectOptions);
			
			_isConnected = mqtt.isConnected();
		}
		catch(Exception ex) {
			log.error(ex.getMessage());
			_isConnected = false;
		}
		
		if(_isConnected) {
			log.info("OK connected");
		} else {
			log.info("FAIL not connected");
		}
	}
	
	public void Subscribe(String topic) {
		log.info("SUBC topic=" + topic);
		
		try {
			mqtt.subscribe(topic);
			log.info("SUBC OK ");
		} catch(Exception ex) {
			log.error("SUBC ERR " + ex.getMessage());
		}
	}
	
	public void Listen() {
		while(true) {
			log.info("Waiting for next message...");
			try {
				Thread.sleep(5000);
			} catch(Exception ex) {
				log.error("ERR " + ex.getMessage());
			}
		}
	}
	
	public void connectionLost(Throwable cause) {
		log.error(cause.getMessage());
	}
	
	public void deliveryComplete(IMqttDeliveryToken token) {
	}
	
	public void messageArrived(String topic, MqttMessage message) {
		String msgTxt = new String(message.getPayload());
		log.info("RECV MSG=" + msgTxt);
	}
	
	public boolean isConnected() {
		return _isConnected;
	}
}