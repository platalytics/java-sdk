/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.platalytics.iotsdk.javasdk.iothub;

/**
 *
 * @author umer
 */

import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONException;
import org.json.JSONObject;

import com.platalytics.iotsdk.javasdk.protocols.MQTT;
import com.platalytics.iotsdk.javasdk.protocols.Protocol;
import java.util.HashMap;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HubClient {
    
    public String hub_id;
    private Protocol protocol;
    
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    
    public boolean connectToHub(String uname, String pswd, String hub_id, String client_name)
            throws IOException, MqttException {
        
        Map<String,String> vals = new HashMap<String,String>();
        vals.put("hub_id",hub_id);
        vals.put("username", uname);
        vals.put("password", pswd);
        JSONObject values_json = new JSONObject(vals);
        RequestBody body = RequestBody.create(JSON, values_json.toString());
        Request request = new Request.Builder()
                .url("http://122.129.79.68:3005/api/iot/hubs/connect")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        JSONObject obj = new JSONObject(response.body().string());
        System.out.println(obj.toString());
        Boolean status = obj.getBoolean("status");
        if (status == true) {
            JSONObject data = obj.getJSONObject("data");
            if (data.getString("protocol").toLowerCase().equals("mqtt")) {
                protocol = new MQTT(data.getString("broker"), client_name
                        + hub_id, hub_id);
                this.hub_id = hub_id;
            }
            
        }
        
        return status;
    }
    
    public boolean sendMessage(String message) {
        return protocol.sendMessage(message, hub_id);
    }
    
    public String fetchMessage() {
        return protocol.fetchReceivedMessage();
    }
}

