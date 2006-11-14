import javax.jms.*;
import javax.naming.*;

public class RecebeComSeletor {

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
        
        // recebe mensagens
        QueueReceiver receiver = session.createReceiver(queue,
            "Depto = 'Marketing'");
        connection.start();
        while (true) {
            TextMessage message = (TextMessage)receiver.receive(1);
            if (message != null)
                System.out.println("Recebi: " + message.getText());
            }

        // libera recursos
        //sender.close();
        //session.close();        
        //connection.close();
    }
}
