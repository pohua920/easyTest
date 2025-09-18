/*
 * 在 2006/12/7 建立
 *
 * TODO 如果要變更這個產生的檔案的範本，請移至
 * 視窗 - 喜好設定 - Java - 程式碼樣式 - 程式碼範本
 */
package com.tlg.listener;

import java.io.File;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.tlg.util.Constants;
import com.tlg.util.Schedule;

/**
 * @author Kelvin.Liu
 * 
 *         TODO 如果要變更這個產生的類別註解的範本，請移至 視窗 - 喜好設定 - Java - 程式碼樣式 - 程式碼範本
 */
public class LoadResouceListener implements ServletContextListener, Constants {

	public void contextInitialized(ServletContextEvent event) {
    	
		try {
			ServletContext servletContext = event.getServletContext();
			// 取得系統設定，並設定時間，每隔一段時間重新取得系統設定參數
			Timer loadResource = new Timer();
			LoadConfigTimerTask loadConfigTimerTask = new LoadConfigTimerTask(servletContext);
			loadResource.schedule(loadConfigTimerTask, 0, DELAY_TIME);
			
			// 設定網路磁碟機路徑
			servletContext.setAttribute("nfsPath", Constants.nfsPath);
			servletContext.setAttribute("apPath", servletContext.getRealPath("/"));
			servletContext.setAttribute("separate", File.separator);
			
			System.out.println("SET " + servletContext.getContextPath().replaceAll("/", "") + " config.property Parameter to ServletContext");
			

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {

		}
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		try {
//			Schedule.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}