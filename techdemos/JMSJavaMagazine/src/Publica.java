import javax.jms.*;
import javax.naming.*;

public class Publica {

    public static void main(String[] args) throws NamingException, JMSException {

        // obtém referências ao QueueConnectionFactory e à fila via JNDI        
        Context jndiContext = new InitialContext();
        TopicConnectionFactory connectionFactory =
            (TopicConnectionFactory)jndiContext.lookup("ConnectionFactory");
        Topic topic = (Topic)jndiContext.lookup("topic/testTopic");

        // conecta ao tópico e inicia uma sessão
        TopicConnection connection =
            connectionFactory.createTopicConnection();
        TopicSession session = connection.createTopicSession(false, 
            Session.AUTO_ACKNOWLEDGE);
            
        // publica uma única mensagem
        TopicPublisher publisher = session.createPublisher(topic);
        TextMessage message = session.createTextMessage();
        message.setText("Oi via JMS");
        publisher.send(message);
        //publisher.publish(message);

        // libera recursos
        publisher.close();
        session.close();        
        connection.close();
    }
}
