package ejb.remote;

import java.util.Map;

public interface LocationSensor {
    public void addVehicle(String vehicle);
    public int getVehicleCount();
    public void removeVehicleCount();
    public Map<String,Object> getVehicles();

}
