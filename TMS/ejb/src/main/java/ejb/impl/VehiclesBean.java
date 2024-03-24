package ejb.impl;

import ejb.remote.Vehicles;
import jakarta.ejb.Singleton;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Singleton
public class VehiclesBean implements Vehicles {
    public static HashMap<String,Object> vehicleSet = new HashMap<>();
    public static int totalSpeed;
    public static int averageSpeed;
    @Override
    public void addVehicle(String vehicle) {
        UUID uniqueId = UUID.randomUUID();
        long vehicleId = Math.abs(uniqueId.getMostSignificantBits());
        if(!vehicleSet.containsValue(vehicle.toString())){
            vehicleSet.put("Vehicle "+vehicleId,vehicle.toString());
            Pattern pattern = Pattern.compile("\\b\\d+(?=KPH\\b)");
            Matcher matcher = pattern.matcher(vehicle);
            if (matcher.find()) {
                String speed = matcher.group();
                totalSpeed += Integer.parseInt(speed);
                System.out.println(vehicle);
            } else {
                System.out.println("Pattern not found");
            }
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
    public String TotalSpeed() {
        return "Total Speed :"+totalSpeed;
    }

    @Override
    public int AverageSpeed() {
        if (vehicleSet.size()==0){
            return 0;
        }else {
            averageSpeed = totalSpeed / vehicleSet.size();
            return averageSpeed;
        }
    }


    @Override
    public HashMap<String, Object> getVehicles() {
        return vehicleSet;
    }
}
