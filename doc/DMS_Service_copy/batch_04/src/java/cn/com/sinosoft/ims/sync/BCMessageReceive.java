package cn.com.sinosoft.ims.sync;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.sinosoft.common.model.InputBean;

public class BCMessageReceive implements BCRawMessageRec {
	private static Log logger = LogFactory
	.getLog(BCMessageReceive.class);
	public void reciveMessage(ObjectMessage message) {
		 System.out.println("------------BCMessageReceive-----------------");
		try {
			Destination d = message.getJMSDestination();
			if (d instanceof Topic) {
				if (((Topic) d).getTopicName().equals("SystemModule!BC_Topic")) {
					System.out.println("&&&&&&&&&&&&&&&&&&&&");
					InputBean inputBean = (InputBean) message.getObject();
					JmsMessageProcessor jmsMessageProcessor = new JmsMessageProcessor();
					jmsMessageProcessor.processMessage(inputBean);
				}else {
					System.out.println("不是bc_topic??");
				}
			} else {
				logger
				.debug("***********************分公司收到总公司的信息了************************************");
			}
		} catch (Exception e) {
			// e.printStackTrace();
			new JMSException("2001", "消息获取错误");
		}

	}
}