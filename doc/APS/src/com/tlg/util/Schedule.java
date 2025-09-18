/*
 * 在 2008/5/23 建立
 *
 * TODO 如果要變更這個產生的檔案的範本，請移至
 * 視窗 - 喜好設定 - Java - 程式碼樣式 - 程式碼範本
 */
package com.tlg.util;

import java.io.FileInputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.tlg.listener.QuartzJobDetailListener;
import com.tlg.listener.QuartzTriggerListener;

/**
 * @author Kelvin.Liu
 * 
 * TODO 如果要變更這個產生的類別註解的範本，請移至 視窗 - 喜好設定 - Java - 程式碼樣式 - 程式碼範本
 */
public class Schedule  extends SqlMapClientDaoSupport {
  
  
  private static QuartzTriggerListener triggerListener = new QuartzTriggerListener();
  private static QuartzJobDetailListener jobDetailListener = new QuartzJobDetailListener();
  private static Scheduler sched;
  
  private void getDs(){
	  try{
		  DataSource ds = getSqlMapClientTemplate().getDataSource();
		  
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	 
  }

  public static void run() throws Exception {
    FileInputStream fis = null;
    StdSchedulerFactory factory = null;
    Properties props = null;
    Properties configProperties = null;
    try{
      factory = new StdSchedulerFactory();
      //Create the properties to configure the factory   
      props = new Properties();
      
      
      props.put("org.quartz.threadPool.class","org.quartz.simpl.SimpleThreadPool");
      props.put("org.quartz.threadPool.threadCount","20");
      props.put("org.quartz.threadPool.threadPriority","5");
      props.put("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread","true");
      props.put("org.quartz.jobStore.class","org.quartz.impl.jdbcjobstore.JobStoreTX");
      props.put("org.quartz.jobStore.tablePrefix","QRTZ_");
//      props.put("org.quartz.jobStore.driverDelegateClass","org.quartz.impl.jdbcjobstore.oracle.weblogic.WebLogicOracleDelegate");
      props.put("org.quartz.jobStore.driverDelegateClass","org.quartz.impl.jdbcjobstore.oracle.OracleDelegate");
      props.put("org.quartz.jobStore.dataSource","myDS");
      
      props.put("org.quartz.scheduler.instanceId","AUTO");
      props.put("org.quartz.plugin.triggHistory.class","org.quartz.plugins.history.LoggingJobHistoryPlugin");
      props.put("org.quartz.jobStore.isClustered","true");
      props.put("org.quartz.jobStore.misfireThreshold","120000");
      props.put("org.quartz.jobStore.clusterCheckinInterval","15000");
      props.put("org.quartz.jobStore.maxMisfiresToHandleAtATime","10");
      props.put("org.quartz.jobStore.selectWithLockSQL","SELECT * FROM {0}LOCKS UPDLOCK WHERE LOCK_NAME = ?");
      
      
      configProperties = new Properties();
      fis = new FileInputStream(Constants.conifgPath);
      configProperties.load(fis);
      String jndiName = configProperties.getProperty("jndiName");
//      props.put("org.quartz.dataSource.myDS.jndiURL",jndiName);
      props.put("org.quartz.dataSource.myDS.driver","oracle.jdbc.driver.OracleDriver");
//      props.put("org.quartz.dataSource.myDS.URL","jdbc:oracle:thin:@10.224.16.7:1521:CORETEST");
      props.put("org.quartz.dataSource.myDS.URL","jdbc:oracle:thin:@coredb01:1521:COREDB");
//      props.put("org.quartz.dataSource.myDS.user","CORE_DEV");
      props.put("org.quartz.dataSource.myDS.user","WCSAP");
      props.put("org.quartz.dataSource.myDS.password","03110001");
      props.put("org.quartz.dataSource.myDS.maxConnections","10");
      props.put("org.quartz.jobStore.selectWithLockSQL","SELECT * FROM {0}LOCKS UPDLOCK WHERE LOCK_NAME = ?");
      
      factory.initialize(props);
      sched = factory.getScheduler();
      
      
      sched.addTriggerListener(triggerListener);
      sched.addJobListener(jobDetailListener);
      sched.start();
      System.out.println(" APS Schedule is Runing ... ");
    }catch (Exception e) {
      e.printStackTrace();
    }finally{
//      fis.close();
//      fis = null;
//      props = null;
//      configProperties = null;
    }
  }

  public static void stop() throws Exception {
	  if(sched != null){
		  sched.shutdown();
	  }
  }
  
  /**
   * @param jobDetail
   * @param simTrigger
   * @throws Exception
   */
  public static boolean addJob(JobDetail jobDetail,Trigger trigger) throws Exception {
	  boolean success = false;
    try{
      trigger.addTriggerListener(triggerListener.getName());
      jobDetail.addJobListener(jobDetailListener.getName());
      sched.scheduleJob(jobDetail, trigger);
      success = true;
    }catch (Exception e) {
      e.printStackTrace();
    }
    return success;
  }
  public static boolean deleteJob(String jobName, String triggerName, String groupName) throws Exception {
    boolean success = false;
    try{
      if("".equals(groupName)){
        groupName = Scheduler.DEFAULT_GROUP;
      }
      success = sched.deleteJob(jobName,groupName);

    }catch (Exception e) {
      try{
        e.printStackTrace();
        System.out.println("Schedule:deleteJob:" + e);
      }catch (Exception e1){
        e1.printStackTrace();
        System.out.println("Schedule:deleteJob:" + e1);
      }
    }
    return success;
  }
}
