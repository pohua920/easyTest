package com.sinosoft.app.common.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AutoSettleConstants {
	private static final Log logger = LogFactory.getLog(AutoSettleConstants.class);

	// 提前一天工作提醒
	public static final String SENDMES_JOBNAME = "AUTOSENDMES";
	public static final String SENDMES_JOBDES = "提前一天工作提醒";
	// 周五导出周工作，並发送邮件
	public static final String SENDMESFRIDAY_JOBNAME = "AUTOSENDMESFRIDAY";
	public static final String SENDMESFRIDAY_JOBDES = "提前一天工作提醒";

	public static Map jobNameMap = new HashMap();
	static {
		// 提前一天工作提醒
		jobNameMap.put(AutoSettleConstants.SENDMES_JOBNAME, AutoSettleConstants.SENDMES_JOBDES);
		// 周五导出周工作，並发送邮件
		jobNameMap.put(AutoSettleConstants.SENDMESFRIDAY_JOBNAME, AutoSettleConstants.SENDMESFRIDAY_JOBDES);
	}

}
