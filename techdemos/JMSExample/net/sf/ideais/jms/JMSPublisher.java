package net.sf.ideais.jms;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class JMSPublisher extends JMSAgent
{
	private Topic topic;
	
	public JMSPublisher()
	{
		this("topic/testTopic", false, null);
	}

	public JMSPublisher(String topicName, String subscriptionName)
	{
		this(topicName, true, subscriptionName);
	}

	
	public JMSPublisher(String topicName, boolean durableTopic, String subscriptionName)
	{
		super(topicName, durableTopic, subscriptionName);
	}
	
	protected void startSession()
	{
		MessageProducer producer = null;
		try {
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
		pub.stop();
	}
}
