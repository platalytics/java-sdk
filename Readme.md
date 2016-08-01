#JAVA SDK for development with IoT Platalytics Platform

You need to first have a device group created in the Platalytics IoT platform before you can start developing java based apps using this SDK


#### To add a new Device in the device group
```
RegistryManager registry_mmanager = new RegistryManager(<platform-url>);
registery_manager.registerDeviceById(<device-group-id>, <device-name>, <device-info>);
```

#### To remove an existing device from a device group
```
RegistryManager registry_mmanager = new RegistryManager(<platform-url>);
registery_manager.registerDeviceById(<device-id>);
```


#### To send a message from device
```
HubClient hub_client = new HubClient();
hub_client.connectToHub(<userename>,<password>,<device-group-id>,<client-name>);
hub_client.sendMessage(<device-id>,<message>);
```

#### To fetch a message
```
HubClient hub_client = new HubClient();
hub_client.connectToHub(<userename>,<password>,<device-group-id>,<client-name>);
String message = hub_client.fetchMessage();
```
