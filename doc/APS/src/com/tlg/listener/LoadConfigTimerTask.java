/*
 * 在 2006/12/7 建立
 *
 * TODO 如果要變更這個產生的檔案的範本，請移至
 * 視窗 - 喜好設定 - Java - 程式碼樣式 - 程式碼範本
 */
package com.tlg.listener;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import com.tlg.util.Constants;

/**
 * @author Kelvin.Liu
 * 
 *         TODO 如果要變更這個產生的類別註解的範本，請移至 視窗 - 喜好設定 - Java - 程式碼樣式 - 程式碼範本
 */
@SuppressWarnings("unchecked")
public class LoadConfigTimerTask extends TimerTask implements Constants {

	private ServletContext context = null;
	public LoadConfigTimerTask(ServletContext context) {
		this.context = context;
		setServletContextAttribute();
	}

	public void run() {
		try {
			setServletContextAttribute();
		} catch (Exception e) {
			System.out.println("LoadConfigTimerTask:run:error" + e);
		}
	}

	
	private void setServletContextAttribute() {
		FileInputStream fis = null;
		Properties p = null;
		try {
			if (p == null) {
				p = new Properties();
			}
			fis = new FileInputStream(Constants.conifgPath);
			p.load(fis);
			Enumeration em = p.keys();
			while (em.hasMoreElements()) {
				Object obj = em.nextElement();
				String propName = obj.toString();
				String propValue = p.getProperty(obj.toString());
				context.setAttribute(propName, propValue);
			}

			fis.close();
			fis = null;

		} catch (Exception e) {
			System.out.println("LoadConfigTimerTask:reSetServletContextAttribute:error"	+ e);
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
					fis = null;
				}
				if (p != null) {
					p = null;
				}

			} catch (Exception e) {
				System.out.println("LoadConfigTimerTask:reSetServletContextAttribute:error"	+ e);
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
}