package cn.com.sinosoft.ims.sync;

import ins.framework.common.ServiceFactory;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

import cn.com.sinosoft.common.model.InputBean;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;

public class HDMessageResponse implements HDRawMessageRes {

	public void reciveMessage(ObjectMessage message) {
		// TODO Auto-generated method stub
		System.out.println("-------------HDMessageResponse--------1--------");
		try {
			Destination d = message.getJMSDestination();
			System.out.println("-------------HDMessageResponse--------2--------");
			if (d instanceof Queue) {
				if (((Queue) d).getQueueName().equals("SystemModule!BC_Queue_F")) {
					System.out.println("-------------HDMessageResponse------3----------");
					InputBean inputBean = (InputBean) message.getObject();
					System.out.println("-------------HDMessageResponse-------4---------");
					UtiISyncLog utiISyncLog = inputBean.getUtiISyncLog();
					System.out.println("-------------HDMessageResponse-------5---------");
					UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
					     .getService("utiISyncLogService");// 获得Spring管理的bean
					System.out.println("-------------HDMessageResponse--------6--------");
					utiISyncLogService.insertMethod(utiISyncLog);
				}
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			new JMSException("2001","消息获取错误");
		}
	}
}