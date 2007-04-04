package net.sf.ideais.jms;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.JMSException;
import javax.jms.TextMessage;

public class JMSPublisher extends JMSAgent
{
	protected MessageProducer producer = null;
	
	/**
	 * Start a JMS publisher for a temporary topic.
	 * 
	 * @param topicName The topic the agent will publish to.
	 */
	public JMSPublisher(String topicName)
	{
		this(topicName, null);
	}

	/**
	 * Start a JMS publisher for a durable topic.
	 * 
	 * @param topicName The topic the agent will subscribe or publish to.
	 * @param subscriptionName The subscription name.
	 */
	public JMSPublisher(String topicName, String subscriptionName)
	{
		super(topicName, subscriptionName);
	}
	
	protected void startSession()
	{
		producer = null;
		try {
			producer = session.createProducer((Destination)topic);

			/*
			 * NON_PERSISTENT: The lowest-overhead delivery mode because it does not require that
			 * the message be logged to stable storage.
			 * PERSISTENT: Instructs the JMS provider to log the message to stable storage as part
			 * of the client's send operation.
			 */
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			producer.setPriority(4); // value between 0 and 9, 9 being the highest priority
			producer.setTimeToLive(0);
		} catch (JMSException e) {
		} finally {
		}
	}

	protected void stopSession()
	{
		try {
			producer.close();
			session.close();
		} catch (JMSException e) {
		}

	}
	
	protected void sendMessage(Object o)
	{
		Message message = null;

		if (o instanceof String) {
			try {
				TextMessage tm = session.createTextMessage();	
				tm.setText((String) o);
				message = tm;
			} catch (JMSException e) {
			}
		} else {
			// BytesMessage bm = session.createBytesMessage();
			// bm.writeBytes(o);
		}
		
		try {
			producer.send(message);
		} catch (JMSException e) {
		}		
	}
}
