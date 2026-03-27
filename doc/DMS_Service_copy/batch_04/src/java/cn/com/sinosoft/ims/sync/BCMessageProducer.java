package cn.com.sinosoft.ims.sync;

import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import cn.com.sinosoft.common.model.InputBean;

public class BCMessageProducer {

	private Map tempMap;

	private Map destMap;

	public void send(final InputBean message) {
		JmsTemplate template = (JmsTemplate) tempMap.get("BC_Temp_F");
		Destination destination = (Destination) destMap.get("BC_Queue_F");
		template.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage objMsg = session.createObjectMessage();
				objMsg.setObject(message);
				return objMsg;
			}
		});
	}
	
	public Map getTempMap() {
		return tempMap;
	}

	public void setTempMap(Map tempMap) {
		this.tempMap = tempMap;
	}

	public Map getDestMap() {
		return destMap;
	}

	public void setDestMap(Map destMap) {
		this.destMap = destMap;
	}

}