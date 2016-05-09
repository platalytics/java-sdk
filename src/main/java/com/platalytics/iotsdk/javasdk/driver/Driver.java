/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.platalytics.iotsdk.javasdk.driver;

import com.platalytics.iotsdk.javasdk.device.RegistryManager;
import com.platalytics.iotsdk.javasdk.iothub.HubClient;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONException;

/**
 *
 * @author umer
 */
public class Driver {
    
    public static void main(String[] args) throws IOException, JSONException, MqttException, InterruptedException {
        
        RegistryManager rm = new RegistryManager();
        
        rm.registerDeviceById("5715dafecd7dff161d9890d6", "Hello OK", "");

        rm.removeDevice("5715db0ccd7dff161d9890d7");

//        rm.testokhttp();

        HubClient hc = new HubClient();
        hc.connectToHub("consultant@programmer.com","123456","5715dafecd7dff161d9890d6","5715dafecd7dff161d9890d6");
        while(true){
            hc.sendMessage("Hellp OK HTTP");
        }
    }
    
}
