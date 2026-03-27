package cn.com.sinosoft.ims.sync;

import javax.jms.MapMessage;
import javax.jms.ObjectMessage;

public interface HDRawMessageRes {

	public void reciveMessage(ObjectMessage message);

}
