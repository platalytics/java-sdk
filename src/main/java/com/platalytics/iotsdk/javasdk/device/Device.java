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
public class Device {
    
    public String device_id;
    public String device_name;
    public String info;
    public String created;
    public String hub_id;
    
    public Device(String device_name, String device_id, String info, String created, String hub_id){
        this.device_id = device_id;
        this.device_name = device_name;
        this.info = info;
        this.created = created;
        this.hub_id = hub_id;
    }
    
    public Device(String device_name, String info, String created, String hub_id){
        this.device_name = device_name;
        this.info = info;
        this.created = created;
        this.hub_id = hub_id;
    }
    
}
