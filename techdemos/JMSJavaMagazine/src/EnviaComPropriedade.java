import javax.jms.*;
import javax.naming.*;

public class EnviaComPropriedade {

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
            
        QueueSender sender = session.createSender(queue);
        TextMessage message = null;
        
        // envia mensagem para o suporte
        message = session.createTextMessage();
        message.setText("Mensagem 1");
        message.setStringProperty("Depto", "Suporte");
        sender.send(message);
        
        // envia mensagem para o marketing
        message = session.createTextMessage();
        message.setText("Mensagem 2");
        message.setStringProperty("Depto", "Marketing");
        sender.send(message);

        // libera recursos
        sender.close();
        session.close();        
        connection.close();
    }
}
