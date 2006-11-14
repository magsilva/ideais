package net.sf.ideais.jms;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class JMSSubscriber extends JMSAgent
{
	protected Connection connection = null;
	
	protected Topic topic;
	
	private boolean durableTopic = false;
	
	private String subscriptionName;

	protected MessageConsumer consumer;
	
	
	public JMSSubscriber()
	{
		this("topic/testTopic", false, null);
	}

	public JMSSubscriber(String topicName, String subscriptionName)
	{
		this(topicName, true, subscriptionName);
	}

	
	public JMSSubscriber(String topicName, boolean durableTopic, String subscriptionName)
	{
		super(topicName, durableTopic, subscriptionName);
	}
		
	protected void startSession()
	{
		try {
			consumer = null;
			if (durableTopic) {
				consumer = session.createDurableSubscriber(topic, subscriptionName);
			} else {
				consumer = session.createConsumer(topic);
			}

			// Ignore messages without an XML file as content (type = TextMessage)
			// String selector = "JMSType =  'TextMessage'"; 
			// MessageConsumer consumer = session.createConsumer(topic, selector);
		} catch (JMSException e) {
		}
	}
	
	
	public void process()
	{
		try {
			// Message m = consumer.receiveNoWait();
			// Message m = consumer.receive(1000); //timeout
			Message m = consumer.receive();
			processMessage(m);
			// session.unsubscribe(subname);
		} catch (JMSException e) {
			
		}
	}
	
	protected void processMessage(Message m) throws JMSException
	{
		if (m instanceof TextMessage)	{
			TextMessage message = (TextMessage)m;
			System.out.println(message.getText());
		}
	}

	
	public static void main(String[] args)
	{
		JMSSubscriber sub = new JMSSubscriber();
		sub.start();
		sub.process();
		sub.stop();
	}
}
