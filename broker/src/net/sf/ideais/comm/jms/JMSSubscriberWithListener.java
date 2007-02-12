package net.sf.ideais.jms;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.JMSException;
import javax.jms.TextMessage;

public class JMSSubscriberWithListener extends JMSSubscriber implements MessageListener
{

	public void process()
	{
		try {
			consumer.setMessageListener(this);
		} catch (JMSException e) {
		}
	}
	
	public void onMessage(Message m)
	{
		try {
			if (m instanceof TextMessage)	{
				TextMessage message = (TextMessage)m;
				System.out.println(message.getText());
			}
		} catch (JMSException e) {
			
		} catch (Throwable t) {
			
		}
	
	}
	
	public static void main(String[] args)
	{
		JMSSubscriberWithListener sub = new JMSSubscriberWithListener();
		sub.start();
	}
}
