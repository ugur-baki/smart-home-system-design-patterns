package Structural.Facade;

import Common.DeviceType;
import Common.SmartDevice;
import Creational.SmartHomeHub;
import Creational.Factory.DeviceFactory;
import Simulation.SimulationContext; 
import Simulation.SmartRoom;         
import Structural.SmartHomeInterface;
import Structural.Composite.DeviceGroup;
import Structural.Decorator.AutoDimmerDecorator;

import java.util.HashMap;
import java.util.Map;

public class SmartHomeFacade implements SmartHomeInterface {
    
    private SmartHomeHub hub;
    private Map<String, DeviceGroup> roomMap; 
    private Map<String, SmartRoom> simulationRooms;

    public SmartHomeFacade() {
        this.hub = SmartHomeHub.getInstance();
        this.roomMap = new HashMap<>();
        this.simulationRooms = new HashMap<>();
    }

    @Override
    public void addDevice(String type, String roomName, boolean withDimmer) {
        DeviceType typeEnum = DeviceType.valueOf(type.toUpperCase());
        SmartDevice device = DeviceFactory.createDevice(typeEnum);

        //Sadece Lamba ise ve Dimmer isteniyorsa ekle
        if (withDimmer && typeEnum == DeviceType.LIGHT) {
            device = new AutoDimmerDecorator(device);
        }

        if (!roomMap.containsKey(roomName)) {
            DeviceGroup newGroup = new DeviceGroup(roomName);
            roomMap.put(roomName, newGroup);
            SmartRoom smartRoom = new SmartRoom(roomName, newGroup);
            simulationRooms.put(roomName, smartRoom);
            hub.registerRoom(roomName, smartRoom);
        }
        
        DeviceGroup targetRoom = roomMap.get(roomName);
        targetRoom.add(device);
    }

    //Yükseltme Metodu
    @Override
    public void addDimmerFeature(String roomName, SmartDevice oldDevice) {
        DeviceGroup room = roomMap.get(roomName);
        if (room != null) {
            // Sadece Lamba ise yükselt
            if (!(oldDevice instanceof Structural.Decorator.AutoDimmerDecorator)) {
                SmartDevice newDev = new AutoDimmerDecorator(oldDevice);
                room.replaceDevice(oldDevice, newDev);
            }
        }
    }

    @Override
    public void triggerSimulationTick() {
        SimulationContext.getInstance().tick();
    }
    
    @Override
    public String getSimulationInfo(String roomName) {
        if (simulationRooms.containsKey(roomName)) {
            double temp = simulationRooms.get(roomName).getTemperature();
            return String.format("%.1f°C", temp);
        }
        return "N/A";
    }

    @Override
    public void setRoomTargetTemp(String roomName, int temp) {
        if (simulationRooms.containsKey(roomName)) {
            simulationRooms.get(roomName).setTargetTemperature(temp);
        }
    }

    @Override
    public int getRoomTargetTemp(String roomName) {
        if (simulationRooms.containsKey(roomName)) {
            return simulationRooms.get(roomName).getTargetTemperature();
        }
        return 22; 
    }

    @Override public Map<String, DeviceGroup> getRooms() { return roomMap; }
    @Override
    public void removeDevice(String roomName, SmartDevice device) {
        DeviceGroup room = roomMap.get(roomName);
        if (room != null) room.remove(device);
    }
    
}