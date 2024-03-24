package ejb.impl;

import ejb.remote.LocationSensor;
import jakarta.ejb.Singleton;
import jakarta.ejb.Stateless;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Singleton
public class locationSensorBean implements LocationSensor {
    public static HashMap<String,Object> vehicleSet = new HashMap<>();

    @Override
    public void addVehicle(String vehicle) {
        UUID uniqueId = UUID.randomUUID();
        long vehicleId = Math.abs(uniqueId.getMostSignificantBits());
        if(!vehicleSet.containsValue(vehicle.toString())){
            vehicleSet.put("Vehicle "+vehicleId,vehicle.toString());
        } else{}
    }

    @Override
    public int getVehicleCount() {
        return vehicleSet.size();
    }

    @Override
    public void removeVehicleCount() {
        vehicleSet.clear();
    }

    @Override
    public HashMap<String, Object> getVehicles() {
        return vehicleSet;
    }
}
