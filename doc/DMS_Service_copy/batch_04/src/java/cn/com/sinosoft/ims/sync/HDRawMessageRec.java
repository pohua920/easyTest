package cn.com.sinosoft.ims.sync;

import javax.jms.ObjectMessage;

public interface HDRawMessageRec {

	public void reciveMessage(ObjectMessage message);

	// public ExportBean processReviMsg(InputBean inputBean);
}
