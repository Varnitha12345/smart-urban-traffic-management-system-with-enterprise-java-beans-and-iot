package ejb.impl;

import ejb.remote.LocationSensor;
import ejb.remote.TLStatus;
import ejb.remote.Vehicles;
import jakarta.ejb.Stateful;

import javax.naming.InitialContext;
import javax.naming.NamingException;

@Stateful
public class TLStatusBean implements TLStatus {
    public static String col;
    public static int time = 0;
    public static int timeGap = 0;

    public static int totalVehicleCount = 0;
    Vehicles vehicles;
    LocationSensor locationSensor;
    @Override
    public void lightColor(String color) {
        col = color;
        try {
            InitialContext context = new InitialContext();
            vehicles =(Vehicles) context.lookup("java:global/ear/app/VehiclesBean");
            locationSensor =(LocationSensor) context.lookup("java:global/ear/app/locationSensorBean");
            for (int i = 0; i < 100; i++) {
                totalVehicleCount = vehicles.getVehicleCount()+locationSensor.getVehicleCount();
                if (col.equals("Green")) {
                    col = "Yellow";
                    timeGap = 3;
                    Thread.sleep(3000);
                    col = "Red";
                    timeGap = 54;
                    Thread.sleep(54000);
                } else if (col.equals("Red") & totalVehicleCount<=30) {
                    col = "Yellow";
                    timeGap = 3;
                    Thread.sleep(3000);
                    col = "Green";
                    timeGap = 15;
                    Thread.sleep(15000);
                    locationSensor.removeVehicleCount();
                    vehicles.removeVehicleCount();
                }else if (col.equals("Red") & totalVehicleCount>30) {
                    col = "Yellow";
                    timeGap = 3;
                    Thread.sleep(3000);
                    col = "Green";
                    timeGap = 25;
                    Thread.sleep(25000);
                    locationSensor.removeVehicleCount();
                    vehicles.removeVehicleCount();
                } else if (col.equals("Red") & totalVehicleCount>50) {
                    col = "Yellow";
                    timeGap = 3;
                    Thread.sleep(3000);
                    col = "Green";
                    timeGap = 35;
                    Thread.sleep(35000);
                    locationSensor.removeVehicleCount();
                    vehicles.removeVehicleCount();
                }else {

                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getColor() {
        return col;
    }

    @Override
    public int timeGap() {
        return timeGap;
    }
}
