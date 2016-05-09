/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.platalytics.iotsdk.javasdk.device;

/**
 *
 * @author umer
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.*;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistryManager {
    
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    
    public Device registerDeviceById(String hub_id, String name, String info)
            throws IOException {
        
        Device new_created_device = null;
        
        Map<String,String> vals = new HashMap<String,String>();
        vals.put("hub_id",hub_id);
        vals.put("name", name);
        vals.put("info", info);
        JSONObject values_json = new JSONObject(vals);
        RequestBody body = RequestBody.create(JSON, values_json.toString());
        Request request = new Request.Builder()
                .url("http://122.129.79.68:3005/api/iot/devices")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        JSONObject obj = new JSONObject(response.body().string());
        Boolean status = obj.getBoolean("status");
        System.out.println(status);
        if (status == true) {
            JSONObject data = obj.getJSONObject("data");
            new_created_device = new Device(data.getString("name"),
                    data.getString("key"), data.getString("info"),
                    data.getString("created"), hub_id);
        }
        
        return new_created_device;
    }
    
    public void registerDevice(Device device) throws IOException {
        
        Device new_created_device = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(
                    "http://122.129.79.68:3005/api/iot/devices");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("name", device.device_name));
            nvps.add(new BasicNameValuePair("hub_id", device.hub_id));
            nvps.add(new BasicNameValuePair("info", device.info));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            
            try {
                HttpEntity entity = response.getEntity();
                String reponse_result = EntityUtils.toString(entity);
                JSONObject obj = new JSONObject(reponse_result);
                Boolean status = obj.getBoolean("status");
                System.out.println(status);
                if (status == true) {
                    JSONObject data = obj.getJSONObject("data");
                    new_created_device = new Device(data.getString("name"),
                            data.getString("key"), data.getString("info"),
                            data.getString("created"), device.hub_id);
                    device = new_created_device;
                }
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
    
    public boolean removeDevice(String device_id) throws IOException {
        Request request = new Request.Builder()
                .url("http://122.129.79.68:3005/api/iot/devices/" + device_id + "/remove")
                .build();
        
        Response response = client.newCall(request).execute();
        
        JSONObject obj = new JSONObject(response.body().string());
        Boolean status = obj.getBoolean("status");
        return status;
    }
    
    public Device getDevice(String device_id) throws IOException {
        Device new_created_device = null;
        Request request = new Request.Builder()
                .url("http://122.129.79.68:3005/api/iot/devices/" + device_id)
                .addHeader("Origin", "http://122.129.79.68:3005")
                .build();
        
        Response response = client.newCall(request).execute();
        
        JSONObject obj = new JSONObject(response.body().string());
        Boolean status = obj.getBoolean("status");
        if (status == true) {
            JSONObject data = obj.getJSONObject("data");
            new_created_device = new Device(data.getString("name"),
                    data.getString("key"), data.getString("info"),
                    data.getString("created"));
            new_created_device.device_id = data.getString("key");
            new_created_device.device_name = data.getString("name");
            new_created_device.info = data.getString("info");
            new_created_device.hub_id = "";
        }
        return new_created_device;
    }
    
    public ArrayList<Device> getAllDevices(String hub_id) throws IOException {
        
        ArrayList<Device> all_devices = new ArrayList<Device>();
        Request request = new Request.Builder()
                .url("http://122.129.79.68:3005/api/iot/hubs/" + hub_id + "/devices")
                .build();
        
        Response response = client.newCall(request).execute();
        
        JSONObject obj = new JSONObject(response.body().string());
        Boolean status = obj.getBoolean("status");
        if (status == true) {
            JSONArray data = obj.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                all_devices.add(new Device(data.getJSONObject(i)
                        .getString("name"), data.getJSONObject(i)
                                .getString("key"), data.getJSONObject(i)
                                        .getString("info"), data.getJSONObject(i)
                                                .getString("created"), hub_id));
            }
        }
        
        return all_devices;
    }
    
}

