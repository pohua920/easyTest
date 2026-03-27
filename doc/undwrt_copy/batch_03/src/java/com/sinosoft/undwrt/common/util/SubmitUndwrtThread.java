package com.sinosoft.undwrt.common.util;

import ins.framework.common.ServiceFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.sinosoft.common.schema.model.PrpQmain;

import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.undwrtInterface.service.facade.TaskService;
import com.sinosoft.undwrt.undwrtInterface.service.spring.TaskServiceSpringImpl;

/**
 * The Class MsgSenderThread.
 */
public class SubmitUndwrtThread extends Thread {
	private List<PrpQmain> list;
	private CountDownLatch endSigle;
	private Logger loggerRenewal = Logger.getLogger(SubmitUndwrtThread.class); 
	private TaskServiceSpringImpl taskService = (TaskServiceSpringImpl) ServiceFactory.getService("taskService");
	private SessionFactory sessionFactory= (SessionFactory)ServiceFactory.getService("sessionFactory");
	public SubmitUndwrtThread(List<PrpQmain> list,CountDownLatch endSigle){
		this.endSigle=endSigle;
		this.list=list;
	}
	
	public void run() {
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	prepare();
        	System.out.println("批次提交核保开始------------------"+System.currentTimeMillis());
        	loggerRenewal.error(sdf.format(new Date())+"提交核保開始");
        	long begin4= System.currentTimeMillis();
        	taskService.checkDataForRenewal(list);	
        	System.out.println("批次提交核保结束------------------"+System.currentTimeMillis());
        	long end4 = System.currentTimeMillis();
        	loggerRenewal.error(sdf.format(new Date())+"提交核保結束");
        	loggerRenewal.error("提交核保所用時間差:----------"+(begin4-end4));
        } catch(Exception e) {
        } finally {
           cleanup();
           endSigle.countDown();
        }
    }
 
     private void cleanup() {
    	 try {
    	    SessionHolder sessionHolder = (SessionHolder)TransactionSynchronizationManager.unbindResource(sessionFactory);
    	    SessionFactoryUtils.closeSession(sessionHolder.getSession());
         } catch(Exception e) {
             e.printStackTrace();
         }
    }
    private void prepare(){
    	Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
    }
}
