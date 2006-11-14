import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSPublisher
{
	private ConnectionFactory connectionFactory;

	private Connection connection = null;
	
	private Topic topic;
	
	public JMSPublisher()
	{
		/*
		 If you are not using the @Resource annotation, you must get the
		 resource through the current context:
		*/
		try {
			Context messaging = new InitialContext();
			connectionFactory = (ConnectionFactory) messaging.lookup("ConnectionFactory");
	 		topic = (Topic) messaging.lookup("topic/testTopic");
		} catch (NamingException e) {
		}

		try {
			connection = connectionFactory.createConnection();
		} catch (JMSException e) {
		}
	}
	
	public void start()
	{
		try {
			connection.start();
		} catch (JMSException e) {
		}
	}
	
	public void pause()
	{
		try {
			connection.stop();
		} catch (JMSException e) {
		}
	}
	
	public void stop()
	{
		try {
			connection.stop();
			connection.close();
		} catch (JMSException e) {
		}
	}
	
	public void process()
	{
		Session session = null;
		MessageProducer producer = null;
		try {
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			producer = session.createProducer((Destination)topic);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			producer.setPriority(4); // value between 0 and 9, 9 being the highest priority
			producer.setTimeToLive(0);
			TextMessage message = session.createTextMessage();
			message.setText("Hello World");
			producer.send(message);
		} catch (JMSException e) {
		} finally {
			try {
				producer.close();
				session.close();
			} catch (JMSException e) {
			}
		}

	}
	
	public static void main(String[] args)
	{
		JMSPublisher pub = new JMSPublisher();
		pub.start();
		pub.process();
		pub.stop();
	}
}
