package Structural;

import Common.SmartDevice;
import Structural.Composite.DeviceGroup;
import java.util.Map;

public interface SmartHomeInterface {
    void addDevice(String type, String roomName, boolean withDimmer); 
    void removeDevice(String roomName, SmartDevice device);
    void addDimmerFeature(String roomName, SmartDevice device); 

    Map<String, DeviceGroup> getRooms();
    
    void triggerSimulationTick();           
    String getSimulationInfo(String roomName); 
    void setRoomTargetTemp(String roomName, int temp);
    int getRoomTargetTemp(String roomName);
}