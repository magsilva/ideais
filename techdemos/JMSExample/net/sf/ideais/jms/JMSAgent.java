package net.sf.ideais.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public abstract class JMSAgent
{
	private ConnectionFactory connectionFactory;

	protected Connection connection = null;
	
	protected Topic topic;
	
	protected Session session;
	
	protected boolean durableTopic = false;
	
	protected String subscriptionName;

	public JMSAgent()
	{
		this("topic/testTopic", false, null);
	}

	public JMSAgent(String topicName, String subscriptionName)
	{
		this(topicName, true, subscriptionName);
	}

	
	public JMSAgent(String topicName, boolean durableTopic, String subscriptionName)
	{
		try {
			Context messaging = new InitialContext();
			connectionFactory = (ConnectionFactory) messaging.lookup("ConnectionFactory");
			topic = (Topic) messaging.lookup(topicName);
			this.durableTopic = durableTopic;
			this.subscriptionName = subscriptionName;
		} catch (NamingException e) {
		}
		
		try {
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
		}
		
		establishSession();
		startSession();
	}
	
	protected void establishSession()
	{
		try {
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
		}
	}
	
	abstract protected void startSession();
	
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
}
