package com.jiat.web.servlet;

import ejb.remote.LocationSensor;
import ejb.remote.TLStatus;
import ejb.remote.Vehicles;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.HashMap;


@WebServlet(name = "VehiclesServlet", value = "/vehicles")
public class VehiclesServlet extends HttpServlet {

    public static HashMap<String, Object> vehicleSet1,vehicleSet2;
    public static int totalVehicleCount = 0;
    Vehicles vehicles;
    LocationSensor locationSensor;
    TLStatus tlStatus;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            InitialContext context = new InitialContext();
            HttpSession session = request.getSession();
            if (session.getAttribute("vehicles-session") == null){
                vehicles =(Vehicles) context.lookup("java:global/ear/app/VehiclesBean");
                session.setAttribute("vehicles-session",vehicles);
            }else {
                vehicles =(Vehicles) session.getAttribute("vehicles-session");

            }
            locationSensor =(LocationSensor) context.lookup("java:global/ear/app/locationSensorBean");
            tlStatus =(TLStatus) context.lookup("java:global/ear/app/TLStatusBean");

            vehicleSet1 = (HashMap<String, Object>) locationSensor.getVehicles();
            vehicleSet2 = (HashMap<String, Object>) vehicles.getVehicles();

            totalVehicleCount = locationSensor.getVehicleCount()+vehicles.getVehicleCount();

            response.getWriter().println("Now Count : " + locationSensor.getVehicleCount());
            response.getWriter().println("Upcoming Vehicle Count : " + vehicles.getVehicleCount());
            response.getWriter().println("Total Vehicle Count : " + totalVehicleCount);
            response.getWriter().println("Upcoming Vehicle Average Speed : " + vehicles.AverageSpeed()+" KPH");
            response.getWriter().println("Color light Status : " + tlStatus.getColor());
            response.getWriter().println("Time Interval : (" + tlStatus.timeGap() + " seconds)");
            response.getWriter().println("");
            response.getWriter().println("");
            response.getWriter().println("Traffic Light Area Vehicle :");
            response.getWriter().println("");
            response.getWriter().println("   "+vehicleSet1.toString());
            response.getWriter().println("");
            response.getWriter().println("Upcoming Area Vehicle :");
            response.getWriter().println("");
            response.getWriter().println("   "+vehicleSet2.toString());



        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
