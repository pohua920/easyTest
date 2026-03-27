package cn.com.sinosoft.ims.sync;


import ins.framework.common.ServiceFactory;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

import cn.com.sinosoft.common.model.InputBean;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;
public class HDMessageReceive implements HDRawMessageRec {

	public void reciveMessage(ObjectMessage message) {
		try {
			Destination d = message.getJMSDestination();
			if (d instanceof Queue) {
				if (((Queue) d).getQueueName().equals("SystemModule!BC_Queue_F")) {
					InputBean inputBean = (InputBean) message.getObject();
					UtiISyncLog utiISyncLog = inputBean.getUtiISyncLog();
					 UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
			         	.getService("utiISyncLogService");// 获得Spring管理的bean
					 utiISyncLogService.insertMethod(utiISyncLog);
				}
			}
		} catch (JMSException e) {
			new JMSException("2001","消息获取错误");
		}
	}

}