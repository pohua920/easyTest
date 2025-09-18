package com.tlg.aps.schedule;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.tlg.util.AppContext;

public class ClmSendSmsScheduleRunner {
    private static Logger logger = Logger.getLogger(ClmSendSmsScheduleRunner.class);
    
    public static void main(String[] args) {
        logger.info("Starting ClmSendSmsSchedule Runner...");
        
        ApplicationContext springContext = null;
        try {
            // 1. 載入 Spring 配置
            springContext = new ClassPathXmlApplicationContext(
                //"classpath:applicationContext.xml"  // 根據您的實際配置文件路徑
            	new String[]{"classpath:applicationContext.xml"}
            );

//        	springContext = new FileSystemXmlApplicationContext(
//    		    "file:D:/workspaceAPS/workspaceApsDEV/APS/WebContent/WEB-INF/applicationContext/aps/*.xml"
//    		);
        	// 或指定具體檔案
        	springContext = new FileSystemXmlApplicationContext(//\APS\conf\config.properties
        			//"/conf/config.properties",
        			"classpath*:applicationContext-sys-jar/applicationContext.xml"
        			//"WebContent/WEB-INF/applicationContext/msSqlSms/applicationContext.xml"
        	);
            
            // 2. 設置 AppContext
            AppContext.setApplicationContext(springContext);
            
            // 3. 獲取並執行 Schedule
            ClmSendSmsSchedule schedule = springContext.getBean(ClmSendSmsSchedule.class);
            if (schedule == null) {
                schedule = new ClmSendSmsSchedule();
            }
            
            // 4. 執行業務邏輯
            //schedule.executeBusinessLogic();
            
            logger.info("ClmSendSmsSchedule Runner completed successfully.");
            
        } catch (Exception e) {
            logger.error("ClmSendSmsSchedule Runner failed", e);
            System.exit(1);
        } finally {
            if (springContext instanceof ClassPathXmlApplicationContext) {
                ((ClassPathXmlApplicationContext) springContext).close();
            }
        }
    }
}
