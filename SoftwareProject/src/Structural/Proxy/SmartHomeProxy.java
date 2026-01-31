package Structural.Proxy;

import Common.SmartDevice;
import Simulation.SmartRoom;
import Structural.SmartHomeInterface;
import Structural.Composite.DeviceGroup;
import Structural.Facade.SmartHomeFacade;
import Creational.SmartHomeHub; 
import java.util.Map;

public class SmartHomeProxy implements SmartHomeInterface {
    
    private SmartHomeFacade realSystem;
    private String userRole; 

    public SmartHomeProxy(String role) {
        this.userRole = role;
        this.realSystem = new SmartHomeFacade(); 
    }

    @Override
    public void addDevice(String t, String r, boolean w) { 
        if("ADMIN".equalsIgnoreCase(userRole)) realSystem.addDevice(t,r,w); 
    }
    
    @Override
    public void removeDevice(String r, SmartDevice d) { 
        if("ADMIN".equalsIgnoreCase(userRole)) realSystem.removeDevice(r,d); 
    }
    
  
    @Override
    public void addDimmerFeature(String r, SmartDevice d) { 
        if("ADMIN".equalsIgnoreCase(userRole)) realSystem.addDimmerFeature(r,d); 
    }

    @Override
    public Map<String, DeviceGroup> getRooms() { return realSystem.getRooms(); }

    @Override
    public void triggerSimulationTick() { realSystem.triggerSimulationTick(); }

    @Override
    public String getSimulationInfo(String roomName) { return realSystem.getSimulationInfo(roomName); }
    
    @Override
    public void setRoomTargetTemp(String roomName, int temp) {
        SmartRoom room = SmartHomeHub.getInstance().getRoom(roomName);
        if (room != null) room.setTargetTemperature(temp);
    }

    @Override
    public int getRoomTargetTemp(String roomName) {
        SmartRoom room = SmartHomeHub.getInstance().getRoom(roomName);
        if (room != null) return room.getTargetTemperature();
        return 22;
    }

    public void setUserRole(String role) {
        this.userRole = role;
    }
}