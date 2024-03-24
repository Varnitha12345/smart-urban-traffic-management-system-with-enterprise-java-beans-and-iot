package com.jiat.web.servlet;

import jakarta.annotation.Resource;
import jakarta.jms.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "DataSender",value = "/sender")
public class DataSender extends HttpServlet {

    @Resource(lookup = "myTopicConnectionFactory")
    private TopicConnectionFactory factory;

    @Resource(lookup = "myTopic")
    private Topic topic;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            TopicConnection connection = factory.createTopicConnection();
            TopicSession session = connection.createTopicSession(false, Session.DUPS_OK_ACKNOWLEDGE);

            TopicPublisher sender = session.createPublisher(topic);
            TextMessage message = session.createTextMessage();
            message.setText("Data received");

            sender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.getWriter().write("Vehicle deployed");
    }
}
