/*
 * 在 2008/5/24 建立
 *
 * TODO 如果要變更這個產生的檔案的範本，請移至
 * 視窗 - 喜好設定 - Java - 程式碼樣式 - 程式碼範本
 */
package com.tlg.listener;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import com.tlg.exception.SystemException;
import com.tlg.sys.service.ScheduleLogService;
import com.tlg.sys.service.ScheduleStatusService;

/**
 * @author Kelvin.Liu
 * 
 *         TODO 如果要變更這個產生的類別註解的範本，請移至 視窗 - 喜好設定 - Java - 程式碼樣式 - 程式碼範本
 */
public class QuartzJobDetailListener implements JobListener {
	private static ScheduleStatusService scheduleStatusService;
	private static ScheduleLogService scheduleLogService;
	private static boolean service;

	public String getName() {
		return "QuartzJobDetailListener";
	}

	public void jobExecutionVetoed(JobExecutionContext jeContext) {
	}

	public void jobToBeExecuted(JobExecutionContext jeContext) {
	}

	@SuppressWarnings("unchecked")
	public void jobWasExecuted(JobExecutionContext jeContext,
			JobExecutionException jeException) {
		
		String jobName = jeContext.getJobDetail().getName();
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String hostName = addr.getHostName() + ":" + addr.getAddress();

//			ScheduleLog scheduleLog = new ScheduleLog();
//			scheduleLog.setTriggerName(jobName);
//			scheduleLog.setHostName(hostName);
//			scheduleLog.setFinishTime(String.valueOf(System.currentTimeMillis()));
//			scheduleLog.setCreater("JobListener");
//			scheduleLog.setCreatetime(new Timestamp(new Date().getTime()));
//
//			Map<String, String> params = new HashMap<String, String>();
//			params.put("triggerName", jobName);
//			params.put("maxSerialNo", "Y");
//			Result result = scheduleLogService.findScheduleLogByParams(params);
//			if (result.getResObject() != null) {
//				ArrayList<ScheduleLog> list = (ArrayList<ScheduleLog>) result.getResObject();
//				ScheduleLog scheduleLogTemp = (ScheduleLog) list.get(0);
//				int nextNo = Integer.parseInt(scheduleLogTemp.getSerialNo()) + 1;
//				scheduleLog.setSerialNo(String.valueOf(nextNo));
//			} else {
//				scheduleLog.setSerialNo("1");
//			}
//
//			result = scheduleLogService.insertScheduleLog(scheduleLog, null);
//			if (result.getResObject() == null) {
//				System.out.println(">>> 新增ScheduleLog失敗");
//			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static ScheduleStatusService getScheduleStatusService() {
		return scheduleStatusService;
	}

	public static boolean  setScheduleStatusService(ScheduleStatusService scheduleStatusService) {
		QuartzJobDetailListener.scheduleStatusService = scheduleStatusService;
		return true;
	}

	public static ScheduleLogService getScheduleLogService() {
		return scheduleLogService;
	}

	public static boolean setScheduleLogService(ScheduleLogService scheduleLogService) {
		QuartzJobDetailListener.scheduleLogService = scheduleLogService;
		return true;
	}

	public static boolean isService() {
		return service;
	}

	public static boolean setService(ScheduleStatusService scheduleStatusService,ScheduleLogService scheduleLogService) {
		QuartzJobDetailListener.scheduleStatusService = scheduleStatusService;
		QuartzJobDetailListener.scheduleLogService = scheduleLogService;
		return true;
	}


}
