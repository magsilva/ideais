import javax.jms.*;
import javax.naming.*;

public class AssinaAssincrono {

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

    public AssinaAssincrono() throws NamingException, JMSException {
                
        // obtém referências ao QueueConnectionFactory e à fila via JNDI        
        Context jndiContext = new InitialContext();
        TopicConnectionFactory connectionFactory = 
            (TopicConnectionFactory)jndiContext.lookup("ConnectionFactory"); 
        Topic topic = (Topic)jndiContext.lookup("topic/testTopic");

        // conecta à fila e inicia uma sessão
        TopicConnection connection = 
            connectionFactory.createTopicConnection();
        TopicSession session = connection.createTopicSession(false, 
            Session.AUTO_ACKNOWLEDGE);
        
        // recebe mensagens
        TopicSubscriber subscriber = session.createSubscriber(topic);
        subscriber.setMessageListener(new RecebeUmaMensagem());
        connection.start();

        // libera recursos
        //subscriber.close();
        //session.close();        
        //connection.close();
    }

    public static void main(String[] args) throws
            NamingException, JMSException, InterruptedException {
            
        new AssinaAssincrono();
        while (true)
            Thread.sleep(10000);
    }
}
