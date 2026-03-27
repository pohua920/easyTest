package cn.com.sinosoft.common.web;

import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class MessageProducer {

	private JmsTemplate template;

	private Map destinationMap;

	public void send(final String message) {
		Destination destination = (Destination) destinationMap.get("HD_Queue");
		template.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				Message m = session.createTextMessage(message);
				return m;
			}
		});
	}

	public void setDestinationMap(Map destinationMap) {
		this.destinationMap = destinationMap;
	}

	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

}