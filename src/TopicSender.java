import javax.annotation.Resource;
import javax.jms.*;

public class TopicSender {

    @Resource(mappedName = "jms/GlassFishBookDurableConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/GlassFishBookTopic")
    private static Topic topic;

    public void produceMessages() {

        MessageProducer messageProducer;
        TextMessage textMessage;

        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            messageProducer = session.createProducer(topic);
            textMessage = session.createTextMessage();
            connection.start();

            textMessage.setText("Testing, 1, 2, 3. Can you hear me?");
            System.out.println("Sending the following message: " + textMessage.getText());
            messageProducer.send(textMessage);

            textMessage.setText("Do you copy?");
            System.out.println("Sending the following message: " + textMessage.getText());
            messageProducer.send(textMessage);

            textMessage.setText("Goodbye");
            System.out.println("Sending the following message: " + textMessage.getText());
            messageProducer.send(textMessage);

            messageProducer.close();
            session.close();
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TopicSender().produceMessages();
    }
}