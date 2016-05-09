/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.platalytics.iotsdk.javasdk.protocols;

/**
 *
 * @author umer
 */
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTT implements MqttCallback, Protocol {
    
    MqttClient client;
    private static String received_message = "";
    private boolean consumed;
    
    public MQTT(String broker, String client_name, String topic) throws MqttException {
        MemoryPersistence persistence = new MemoryPersistence();
        client = new MqttClient("tcp://" + broker + ":1883", client_name, persistence);
        client.connect();
        client.setCallback(this);
        client.subscribe(topic);
    }
    
    @Override
    public boolean sendMessage(String message, String topic) {
        MqttMessage mqttmessage = new MqttMessage();
        mqttmessage.setPayload(message.getBytes());
        try {
            System.out.println("TOPIC =========== " + topic);
            client.publish(topic, mqttmessage);
            return true;
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
            return false;
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void doDemo() {
        try {
            client = new MqttClient("tcp://192.168.23.101:1883", "Sending");
            client.connect();
            client.setCallback(this);
            client.subscribe("foo");
            MqttMessage message = new MqttMessage();
            message.setPayload("A single message from my computer fff"
                    .getBytes());
            client.publish("foo", message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String fetchReceivedMessage() {
        if (received_message != null && consumed == false) {
            consumed = true;
            return received_message;
        }
        return null;
    }
    
    @Override
    public void connectionLost(Throwable cause) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void messageArrived(String topic, MqttMessage message)
            throws Exception {
        received_message = message.toString();
        consumed = false;
    }
    
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub
        
    }
    
}

