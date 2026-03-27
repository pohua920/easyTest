package cn.com.sinosoft.ims.sync;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;

import cn.com.sinosoft.common.model.ExportBean;
import cn.com.sinosoft.ims.util.ReadProperties;

import com.sinosoft.sysframework.exception.BusinessException;

public class BCMessageResponse implements BCRawMessageRes {

	public void reciveMessage(ObjectMessage message) {
		System.out.println("------------BCMessageResponse-----------------");
		try {
			Destination d = message.getJMSDestination();
			ExportBean exportBean = new ExportBean();
			if (d instanceof Topic) {
				if (((Topic) d).getTopicName().equals("SystemModule!HD_Topic_F")) {
//					System.out.println("DestFlag"+ message.getStringProperty("DestFlag"));
//					System.out.println("SourFlag"+ message.getStringProperty("SourFlag"));
//					System.out.println("JS=="+ "JS".equals(message.getStringProperty("DestFlag")));
					if (ReadProperties.getString("deployCom").equals(message.getStringProperty("SourFlag"))) {
//						System.out.println("------接收总公司反馈信息------为------"+ message + "------");
						exportBean = (ExportBean) message.getObject();
						System.out.println("-----exportBean---BC---"+exportBean.getRequestType());
					}else{
						throw new BusinessException("3001", "同步源设置错误");
					}
				}
			}
		} catch (JMSException e) {
//			e.printStackTrace();
			new JMSException("2001","消息获取错误");
		}
	}
}