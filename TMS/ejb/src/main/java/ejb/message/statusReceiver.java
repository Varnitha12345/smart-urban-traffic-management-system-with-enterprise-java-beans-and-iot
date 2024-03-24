package ejb.message;

import ejb.remote.TLStatus;
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
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "statusSensor")

        }
)


public class statusReceiver implements MessageListener {

    public static String msg = "";
    @Override
    public void onMessage(Message message) {
        TLStatus tlStatus;
        try {
            msg = message.getBody(String.class);
            InitialContext context = new InitialContext();
            tlStatus =(TLStatus) context.lookup("java:global/ear/app/TLStatusBean");
            tlStatus.lightColor(msg);

        } catch (JMSException | NamingException e) {
            throw new RuntimeException(e);
        }
    }

}
