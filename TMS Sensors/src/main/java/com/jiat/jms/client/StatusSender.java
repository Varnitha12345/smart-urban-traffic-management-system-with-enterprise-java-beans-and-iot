package com.jiat.jms.client;

import jakarta.jms.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.UUID;


public class StatusSender {
    public static String color = "Green";
    public static void main(String[] args) {
        try {
            InitialContext context = new InitialContext();
            TopicConnectionFactory factory = (TopicConnectionFactory) context.lookup("statusSensorConnectionFactory");

            TopicConnection connection = factory.createTopicConnection();
            TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            Topic topic = (Topic) context.lookup("statusSensor");

            TopicPublisher publisher = session.createPublisher(topic);

            TextMessage message = session.createTextMessage();

            message.setText(color);
            publisher.send(message);

        } catch (NamingException e) {
            throw new RuntimeException(e);
        }catch (JMSException e){
            throw new RuntimeException();
        }
    }

}
