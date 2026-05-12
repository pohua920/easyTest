/************************************************************************
 * Description: 销售系统报表配置静态常量类
 * Author     : FanML
 * CreateDate : 2009-6-1
 * UpdateLog  : Name           Date         Reason/Content
 *          ------------------------------------------------------------
 *
 ************************************************************************/
package com.sinosoft.app.common.util;

import java.util.Properties;

public final class PerfConstants {

	/** 最大线程数 */
	public static String emailSendURL;
	public static String emailUserName;
	public static String emailPassowrd;
	public static String localhostURL;

	/**
	 * 初始化报表调度器配置
	 * @param reportProperties
	 */
	public static void initPerfConfig(Properties perfProperties) {
		emailSendURL = perfProperties.getProperty("email.host", "");
		emailUserName = perfProperties.getProperty("email.username", "");
		emailPassowrd = perfProperties.getProperty("email.password", "");
		localhostURL = perfProperties.getProperty("email.localhost", "");
	}

}
