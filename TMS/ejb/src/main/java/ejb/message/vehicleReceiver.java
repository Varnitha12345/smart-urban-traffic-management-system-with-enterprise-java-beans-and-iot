package ejb.message;

import ejb.remote.Vehicles;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;

import javax.naming.InitialContext;
import javax.naming.NamingException;


@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "vehicleSensor")

        }
)


public class vehicleReceiver implements MessageListener {

    public static String msg = "";
    @Override
    public void onMessage(Message message) {
        Vehicles vehicles;
        try {
            msg = message.getBody(String.class);
            InitialContext context = new InitialContext();
            vehicles =(Vehicles) context.lookup("java:global/ear/app/VehiclesBean");
            vehicles.addVehicle(msg);

        } catch (JMSException | NamingException e) {
            throw new RuntimeException(e);
        }
    }

}
