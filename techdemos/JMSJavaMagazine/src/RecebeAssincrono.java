import javax.jms.*;
import javax.naming.*;

public class RecebeAssincrono {

    private class RecebeUmaMensagem implements MessageListener {
        public void onMessage(Message msg) {
           try {
               TextMessage message = (TextMessage)msg;
               if (message != null)
                   System.out.println("Recebi: " + message.getText());
           }
           catch (JMSException e) {
               System.out.println("Erro ao receber mensagem:" + e);
               e.printStackTrace();
           }
        }
    };

    public RecebeAssincrono() throws NamingException, JMSException {
                
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
        QueueReceiver receiver = session.createReceiver(queue);
        receiver.setMessageListener(new RecebeUmaMensagem());
        connection.start();

        // libera recursos
        //sender.close();
        //session.close();        
        //connection.close();
    }

    public static void main(String[] args) throws
            NamingException, JMSException, InterruptedException {
            
        new RecebeAssincrono();
        while (true)
            Thread.sleep(10000);
    }
}
