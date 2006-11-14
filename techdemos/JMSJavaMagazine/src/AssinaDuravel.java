import javax.jms.*;
import javax.naming.*;

public class AssinaDuravel {

    public static void main(String[] args) throws NamingException, JMSException {
                
        // obtém referências ao TopicConnectionFactory e à fila via JNDI        
        Context jndiContext = new InitialContext();
        TopicConnectionFactory connectionFactory = 
            (TopicConnectionFactory)jndiContext.lookup("ConnectionFactory"); 
        Topic topic = (Topic)jndiContext.lookup("topic/testDurableTopic");

        // conecta à fila e inicia uma sessão
        TopicConnection connection = 
            connectionFactory.createTopicConnection("john", "needle");
        TopicSession session = connection.createTopicSession(false, 
            Session.AUTO_ACKNOWLEDGE);
        
        // recebe mensagens
        TopicSubscriber subscriber = session.createDurableSubscriber(
            topic, "eu");
        connection.start();
        while (true) {
            TextMessage message = (TextMessage)subscriber.receive(1);
            if (message != null)
                System.out.println("Recebi: " + message.getText());
            }

        // libera recursos
        //session.unsubscribe("eu");
        //subscriber.close();
        //session.close();        
        //connection.close();
    }
}
