import javax.jms.*;
import javax.naming.*;

public class EnviaFila {

    public static void main(String[] args) throws NamingException, JMSException {

        // obtém referências ao QueueConnectionFactory e à fila via JNDI        
        Context jndiContext = new InitialContext();
        QueueConnectionFactory connectionFactory =
            (QueueConnectionFactory)jndiContext.lookup("ConnectionFactory");
        Queue queue = (Queue)jndiContext.lookup("queue/testQueue");

        // conecta à fila e inicia uma sessão
        QueueConnection connection =
            connectionFactory.createQueueConnection();
        QueueSession session = connection.createQueueSession(false, 
            Session.AUTO_ACKNOWLEDGE);
            
        // envia uma única mensagem
        QueueSender sender = session.createSender(queue);
        TextMessage message = session.createTextMessage();
        message.setText("Oi via JMS");
        sender.send(message);

        // libera recursos
        sender.close();
        session.close();        
        connection.close();
    }
}
