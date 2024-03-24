package com.jiat.jms.client;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import jakarta.jms.*;

public class Execute {
    public static String msg = "";
    public static int step = 0;

    public static void main(String[] args) {
        try {
            InitialContext context = new InitialContext();
            TopicConnectionFactory factory = (TopicConnectionFactory) context.lookup("myTopicConnectionFactory");

            TopicConnection connection = factory.createTopicConnection();
            connection.start();

            TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            Topic topic = (Topic) context.lookup("myTopic");
            TopicSubscriber subscriber = session.createSubscriber(topic);

            subscriber.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {

                    try {
                        msg = message.getBody(String.class);
                        System.out.println(msg);
                        if(msg.equals("Data received") & step == 0){
                            StatusSender.main(args);
                            LocationSender.main(args);
                            DataSender.main(args);
                            step = step +1;
                        }else if(!msg.equals("Data received")){
                            System.out.println("Traffic Light not working.");
                        }else {
                            LocationSender.main(args);
                            DataSender.main(args);
                        }
                    } catch (JMSException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            while (true){

            }


        } catch (NamingException e) {
            throw new RuntimeException(e);
        }catch (JMSException e){
            throw new RuntimeException();
        }
    }
}
