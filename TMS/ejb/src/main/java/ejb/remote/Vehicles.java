package ejb.remote;

import jakarta.ejb.Remote;

import java.util.Map;
@Remote
public interface Vehicles {
    public void addVehicle(String vehicle);
    public int getVehicleCount();
    public void removeVehicleCount();
    public String TotalSpeed();
    public int AverageSpeed();
    public Map<String,Object> getVehicles();
}
