package com.jiat.jms.client;

import jakarta.jms.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.UUID;


public class DataSender {

    public static int x = 1000;
    public static void main(String[] args) {
        try {
            InitialContext context = new InitialContext();
            TopicConnectionFactory factory = (TopicConnectionFactory) context.lookup("vehicleSensorConnectionFactory");

            TopicConnection connection = factory.createTopicConnection();
            TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            Topic topic = (Topic) context.lookup("vehicleSensor");

            TopicPublisher publisher = session.createPublisher(topic);

            TextMessage message = session.createTextMessage();

            for (int i = 0; i < 10; i++){
                Thread.sleep(3000);
                UUID uniqueId = UUID.randomUUID();
                long uniqueNo = Math.abs(uniqueId.getMostSignificantBits());
                String vehicleNoPlate = String.format("%0" + 4 + "d", uniqueNo % (long)Math.pow(10, 4));

                message.setText("CDE "+vehicleNoPlate+","+"Vehicle"+x+","+"60KPH");
                x = x+1;
                publisher.send(message);

            }

        } catch (NamingException e) {
            throw new RuntimeException(e);
        }catch (JMSException e){
            throw new RuntimeException();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
