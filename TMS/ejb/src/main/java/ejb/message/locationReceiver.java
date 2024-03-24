package ejb.message;

import ejb.remote.LocationSensor;
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
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "locationSensor")

        }
)


public class locationReceiver implements MessageListener {

    public static String msg = "";
    @Override
    public void onMessage(Message message) {
        LocationSensor locationSensor;
        try {
            msg = message.getBody(String.class);
            InitialContext context = new InitialContext();
            locationSensor =(LocationSensor) context.lookup("java:global/ear/app/locationSensorBean");
            locationSensor.addVehicle(msg);

        } catch (JMSException | NamingException e) {
            throw new RuntimeException(e);
        }
    }

}
