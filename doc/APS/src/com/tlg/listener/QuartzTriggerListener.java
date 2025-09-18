/*
 * 在 2008/5/24 建立
 *
 * TODO 如果要變更這個產生的檔案的範本，請移至
 * 視窗 - 喜好設定 - Java - 程式碼樣式 - 程式碼範本
 */
package com.tlg.listener;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

import com.tlg.sys.entity.ScheduleLog;
import com.tlg.sys.entity.ScheduleStatus;
import com.tlg.sys.service.ScheduleLogService;
import com.tlg.sys.service.ScheduleStatusService;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/**
 * @author Kelvin.Liu
 * 
 *         TODO 如果要變更這個產生的類別註解的範本，請移至 視窗 - 喜好設定 - Java - 程式碼樣式 - 程式碼範本
 */
public class QuartzTriggerListener implements TriggerListener {

	private static ScheduleStatusService scheduleStatusService;
	private static ScheduleLogService scheduleLogService;
	private static boolean service;
	
	public String getName() {
		return "QuartzTriggerListener";
	}

	public void triggerFired(Trigger trigger, JobExecutionContext jeContext) {
	}

	@SuppressWarnings("unchecked")
	public void triggerComplete(Trigger trigger, JobExecutionContext jeContext,
			int arg2) {
//		Transaction t = null;
//		ResultSet rs = null;
		try {
			
			InetAddress addr = InetAddress.getLocalHost();
			String hostName = addr.getHostName() + ":" + addr.getAddress();
			String activeTime = String.valueOf(trigger.getNextFireTime().getTime());
			String triggerName = trigger.getName();
//			t = new Transaction();
			
			ScheduleStatus scheduleStatus = new ScheduleStatus();
			scheduleStatus.setActiveTime(activeTime);
			scheduleStatus.setActive("");
			scheduleStatus.setHostName("");
			scheduleStatus.setTriggerName(triggerName);
			
			
			Result result = scheduleStatusService.updateScheduleStatus(scheduleStatus, null);
			if(result.getResObject() == null){
				System.out.println(">>> 更新ScheduleStatus 失敗");
			}

			
			ScheduleLog scheduleLog = new ScheduleLog();
			scheduleLog.setTriggerName(triggerName);
			scheduleLog.setHostName(hostName);
			scheduleLog.setFinishTime(String.valueOf(System.currentTimeMillis()));
			scheduleLog.setCreater("TriggerListener");
			scheduleLog.setCreatetime(new Timestamp(new Date().getTime()));
			
			Map<String,String> params = new HashMap<String,String>();
			params.put("triggerName", triggerName);
			params.put("maxSerialNo", "Y");
			result = scheduleLogService.findScheduleLogByParams(params);
			if(result.getResObject() != null){
				ArrayList<ScheduleLog> list = (ArrayList<ScheduleLog>)result.getResObject();
				ScheduleLog scheduleLogTemp = (ScheduleLog)list.get(0);
				int nextNo = Integer.parseInt(scheduleLogTemp.getSerialNo()) + 1;
				scheduleLog.setSerialNo(String.valueOf(nextNo));
			}else{
				scheduleLog.setSerialNo("1");
			}
			
			result = scheduleLogService.insertScheduleLog(scheduleLog, null);
			if(result.getResObject() == null){
				System.out.println(">>> 新增ScheduleLog失敗");
			}
			
//			String updateSql = "UPDATE ScheduleStatus SET activeTime = '"
//					+ activeTime
//					+ "',active = '',hostName = '' WHERE triggerName = '"
//					+ triggerName + "' ";
//			int i = t.executeQuery(updateSql);
//			
//			String selectSql = "SELECT * FROM ScheduleLog WHERE triggerName = '"
//					+ triggerName
//					+ "' "
//					+ "AND serialNo = (SELECT MAX(TO_NUMBER(serialNo)) FROM ScheduleLog WHERE triggerName = '"
//					+ triggerName + "')";
//			Date finishTime = new Timestamp(new Date().getTime());
//			InetAddress addr = InetAddress.getLocalHost();
//			// String hostName = addr.getHostName();
//			String hostName = addr.getHostName() + ":" + addr.getAddress();
//			rs = t.select(selectSql);
//			if (rs.next()) {
//				String serialNo = rs.getString("serialNo");
//				int nextNo = Integer.parseInt(serialNo) + 1;
//				String insertSql = "INSERT INTO ScheduleLog(triggerName,serialNo,hostName,finishTime,creater,createtime)VALUES("
//						+ "'"
//						+ triggerName
//						+ "',"
//						+ nextNo
//						+ ",'"
//						+ hostName
//						+ "','"
//						+ finishTime
//						+ "','TriggerListener',TO_TIMESTAMP('"
//						+ finishTime
//						+ "','YYYY.MM-DD HH24:MI:SSxFF') " + ")";
//				i = t.executeQuery(insertSql);
//			} else {
//				String insertSql = "INSERT INTO ScheduleLog(triggerName,serialNo,hostName,finishTime,creater,createtime)VALUES("
//						+ "'"
//						+ triggerName
//						+ "',1,'"
//						+ hostName
//						+ "','"
//						+ finishTime
//						+ "','TriggerListener',TO_TIMESTAMP('"
//						+ finishTime + "','YYYY.MM-DD HH24:MI:SSxFF') " + ")";
//				i = t.executeQuery(insertSql);
//			}
//			t.commit();
		} catch (Exception e) {
			try {
//				t.rollback();
				e.printStackTrace();
				System.out.println("QuartzJobDetailListener:jobWasExecuted:"
						+ e);
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out.println("QuartzJobDetailListener:jobWasExecuted:"
						+ e1);
			}
		} 
//		finally {
//			try {
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//				if (t != null) {
//					t.close();
//					t = null;
//				}
//			} catch (Exception e2) {
//				e2.printStackTrace();
//				System.out.println("QuartzJobDetailListener:jobWasExecuted:"
//						+ e2);
//			}
//		}
	}

	public void triggerMisfired(Trigger arg0) {
	}

	@SuppressWarnings("unchecked")
	public boolean vetoJobExecution(Trigger trigger,
			JobExecutionContext jeContext) {
//		Transaction t = null;
//		ResultSet rs = null;
//		ResultSet logRs = null;
		boolean vetoJob = true;
//		SqlClient sqlClientBean = null;

		try {
			String triggerName = trigger.getName();
			if ("".equals(triggerName) || triggerName == null) {
				return true;
			}
			
			InetAddress addr = InetAddress.getLocalHost();
			String hostName = addr.getHostName();
			// String preActiveTime =
			// String.valueOf(trigger.getPreviousFireTime().getTime());

			Result result = scheduleStatusService.findScheduleStatusByTriggerNameJoinTimerLog(triggerName);
			if(result.getResObject() != null){
				ArrayList<Map> list = (ArrayList<Map>)result.getResObject();
				Map map = (HashMap)list.get(0);
				String activeTime = StringUtil.nullToSpace((String)map.get("activeTime"));
				// String NEXT_FIRE_TIME = rs.getString("NEXT_FIRE_TIME");
				BigDecimal PREV_FIRE_TIME = (BigDecimal)map.get("PREV_FIRE_TIME"); 
				String active = StringUtil.nullToSpace((String)map.get("active"));
				// String TRIGGER_STATE = rs.getString("TRIGGER_STATE");
				String adminMail = StringUtil.nullToSpace((String)map.get("adminMail"));
				String smtpServer = StringUtil.nullToSpace((String)map.get("smtpServer"));
				String triggerPath = StringUtil.nullToSpace((String)map.get("triggerPath"));
				String sendWarningMail = StringUtil.nullToSpace((String)map.get("sendWarningMail"));
				
//				ScheduleLog scheduleLog = new ScheduleLog();
//				scheduleLog.setTriggerName(triggerName);
//				scheduleLog.setHostName(hostName);
//				scheduleLog.setFinishTime(String.valueOf(new Timestamp(new Date().getTime())));
//				scheduleLog.setCreater("TriggerListener");
//				scheduleLog.setCreatetime(new Timestamp(new Date().getTime()));
				
				Map<String,String> params = new HashMap<String,String>();
				params.put("triggerName", triggerName);
				params.put("maxSerialNo", "Y");
				result = scheduleLogService.findScheduleLogByParams(params);
				if(result.getResObject() != null){
					ArrayList<ScheduleLog> logList = (ArrayList<ScheduleLog>)result.getResObject();
					// 判斷log的最後一筆完成時間是否大於目前要執行的時間，如果大於則表示已被執行過因此不再執行
					ScheduleLog scheduleLogTemp = (ScheduleLog)logList.get(0);
					long nowTime = new Date().getTime();
					if (Long.parseLong(scheduleLogTemp.getFinishTime()) > nowTime) {
						return true;
					}
					// active 若為Y，若兩者時間不同時，有可能是server突然停止造成，因此回傳true不繼續執行
					if ("Y".equals(active)) {
						if (!PREV_FIRE_TIME.equals(activeTime)) {
							if (!"Y".equals(sendWarningMail)) {
								String message = "系統發現此程式：" + triggerPath
										+ "未按時執行<br><br>" + "造成的原因可能是:<br><br>"
										+ "1.排程的程式執行中，Server異常停止或其他因素<br><br>"
										+ "2.程式執行的間隔時間太短<br><br>" + "請您重新設定排程時間！";
								sendMail(scheduleStatusService,smtpServer, adminMail, triggerName,message);
							}
							return true;
						}
					}
					// active 若為Y，且未發生上述問題時，則表示執行中，
					if ("Y".equals(active)) {
						return true;
					}
					ScheduleStatus scheduleStatus = new ScheduleStatus();
					scheduleStatus.setActive("Y");
					scheduleStatus.setActiveTime(PREV_FIRE_TIME.toString());
					scheduleStatus.setHostName(hostName);
					scheduleStatus.setTriggerName(triggerName);
					result = scheduleStatusService.updateScheduleStatus(scheduleStatus, null);
					if (result.getResObject() == null) {
						return true;
					}
					vetoJob = false;
				}
				
			} else {
				String nextFireTime = String.valueOf(trigger.getNextFireTime().getTime());
				ScheduleStatus scheduleStatus = new ScheduleStatus();
				scheduleStatus.setActive("Y");
				scheduleStatus.setActiveTime(nextFireTime);
				scheduleStatus.setHostName(hostName);
				scheduleStatus.setTriggerName(triggerName);
				result = scheduleStatusService.insertScheduleStatus(scheduleStatus, null);
				if(result.getResObject() == null){
					return true;
				}
				vetoJob = false;
			}
			// 檢查其他程式，TRIGGER_STATE是否有出現ERROR
			result = scheduleStatusService.findErrorStatus();
			if(result.getResObject() != null){
				ArrayList<Map> list = (ArrayList<Map>)result.getResObject();
				Iterator<Map> it = list.iterator();
				while(it.hasNext()){
					Map map = it.next();
					
					String adminMail = StringUtil.nullToSpace((String)map.get("adminMail"));
					String smtpServer = StringUtil.nullToSpace((String)map.get("smtpServer"));
					String triggerPath = StringUtil.nullToSpace((String)map.get("triggerPath"));
					String triggerName_ = StringUtil.nullToSpace((String)map.get("triggerName"));
					String sendWarningMail = StringUtil.nullToSpace((String)map.get("sendWarningMail"));
					if (!"Y".equals(sendWarningMail)) {
						String message = "系統發現此程式：" + triggerPath + "未按時執行<br><br>"
								+ "造成的原因可能是:<br><br>" + "此程式:" + triggerPath
								+ "在某台主機上並不存在，因此造成錯誤<br><br>" + "請檢查所有主機是否都有此程式："
								+ triggerPath + "<br><br>" + "檢查後並請您重新設定"
								+ triggerName_ + "的排程時間！";
						sendMail(scheduleStatusService,smtpServer, adminMail, triggerName_, message);
					}
				}
			}
			
//			t = new Transaction();
//			sqlClientBean = Transaction.getNonTsqlConnection();
//
//			String querySql = "SELECT s.triggerName,s.sendWarningMail,st.triggerPath,s.activeTime,s.active,st.adminMail,"
//					+ "st.smtpServer,q.TRIGGER_STATE,q.NEXT_FIRE_TIME,q.PREV_FIRE_TIME "
//					+ "FROM ScheduleStatus s,QRTZ_TRIGGERS q,ScheduleTimer st "
//					+ "WHERE s.triggerName = q.TRIGGER_NAME "
//					+ "AND st.triggerName = q.TRIGGER_NAME "
//					+ "AND q.TRIGGER_GROUP = 'DEFAULT' "
//					+ "AND q.TRIGGER_NAME = '" + triggerName + "' ";
//			rs = sqlClientBean.select(querySql);
//			if (rs.next()) {
//				String activeTime = rs.getString("activeTime");
//				// String NEXT_FIRE_TIME = rs.getString("NEXT_FIRE_TIME");
//				String PREV_FIRE_TIME = rs.getString("PREV_FIRE_TIME");
//				String active = rs.getString("active");
//				// String TRIGGER_STATE = rs.getString("TRIGGER_STATE");
//				String adminMail = rs.getString("adminMail");
//				String smtpServer = rs.getString("smtpServer");
//				String triggerPath = rs.getString("triggerPath");
//				String sendWarningMail = rs.getString("sendWarningMail");
//				// 判斷log的最後一筆完成時間是否大於目前要執行的時間，如果大於則表示已被執行過因此不再執行
//				String queryLogSql = "SELECT * FROM ScheduleLog WHERE triggerName = '"
//						+ triggerName
//						+ "' "
//						+ "AND serialNo = (SELECT MAX(TO_NUMBER(serialNo)) FROM ScheduleLog WHERE triggerName = '"
//						+ triggerName + "')";
//				logRs = sqlClientBean.select(queryLogSql);
//				if (logRs.next()) {
//					long finishTime = logRs.getTimestamp("finishTime").getTime();
//					long nowTime = new Date().getTime();
//					if (finishTime > nowTime) {
//						t.rollback();
//						return true;
//					}
//				}
//				// active 若為Y，若兩者時間不同時，有可能是server突然停止造成，因此回傳true不繼續執行
//				if ("Y".equals(active)) {
//					if (!PREV_FIRE_TIME.equals(activeTime)) {
//						if (!"Y".equals(sendWarningMail)) {
//							String message = "系統發現此程式：" + triggerPath
//									+ "未按時執行<br><br>" + "造成的原因可能是:<br><br>"
//									+ "1.排程的程式執行中，Server異常停止或其他因素<br><br>"
//									+ "2.程式執行的間隔時間太短<br><br>" + "請您重新設定排程時間！";
//							sendMail(smtpServer, adminMail, triggerName,
//									message, t);
//							t.commit();
//						}
//						return true;
//					}
//				}
//				// active 若為Y，且未發生上述問題時，則表示執行中，
//				if ("Y".equals(active)) {
//					return true;
//				}
//				String updateSql = "UPDATE ScheduleStatus SET active = 'Y',activeTime='"
//						+ PREV_FIRE_TIME
//						+ "',hostName = '"
//						+ hostName
//						+ "' WHERE triggerName = '" + triggerName + "' ";
//				int i = t.executeQuery(updateSql);
//				if (i == 0) {
//					t.rollback();
//					return true;
//				}
//				vetoJob = false;
//			} else {
//				String nextFireTime = String.valueOf(trigger.getNextFireTime()
//						.getTime());
//				// 若不存在，則表示是第一次執行，因此新增至ScheduleStatus
//				String insertSql = "INSERT INTO ScheduleStatus(triggerName,activeTime,active,hostName)VALUES("
//						+ "'"
//						+ triggerName
//						+ "','"
//						+ nextFireTime
//						+ "','Y','"
//						+ hostName + "')";
//				int i = t.executeQuery(insertSql);
//				if (i == 0) {
//					// System.out.println(">>>>>... vetoJobExecution true update fail 2"
//					// );
//					t.rollback();
//					return true;
//				}
//				vetoJob = false;
//			}
//			// 檢查其他程式，TRIGGER_STATE是否有出現ERROR
//			querySql = "SELECT s.triggerName,s.sendWarningMail,st.triggerPath,st.adminMail,st.smtpServer "
//					+ "FROM ScheduleStatus s,QRTZ_TRIGGERS q,ScheduleTimer st  "
//					+ "WHERE s.triggerName = q.TRIGGER_NAME "
//					+ "AND st.triggerName = q.TRIGGER_NAME "
//					+ "AND q.TRIGGER_GROUP = 'DEFAULT' "
//					+ "AND q.TRIGGER_STATE = 'ERROR' "
//					+ "AND s.sendWarningMail <> 'Y' ";
//			rs = sqlClientBean.select(querySql);
//			while (rs.next()) {
//				String adminMail = rs.getString("adminMail");
//				String smtpServer = rs.getString("smtpServer");
//				String triggerPath = rs.getString("triggerPath");
//				String triggerName_ = rs.getString("triggerName");
//				String sendWarningMail = rs.getString("sendWarningMail");
//				if (!"Y".equals(sendWarningMail)) {
//					String message = "系統發現此程式：" + triggerPath + "未按時執行<br><br>"
//							+ "造成的原因可能是:<br><br>" + "此程式:" + triggerPath
//							+ "在某台主機上並不存在，因此造成錯誤<br><br>" + "請檢查所有主機是否都有此程式："
//							+ triggerPath + "<br><br>" + "檢查後並請您重新設定"
//							+ triggerName_ + "的排程時間！";
//					sendMail(smtpServer, adminMail, triggerName_, message, t);
//				}
//			}
//			t.commit();
		} catch (Exception e) {
			try {
//				t.rollback();
				e.printStackTrace();
				System.out.println("QuartzTriggerListener:vetoJobExecution:" + e);
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out.println("QuartzTriggerListener:vetoJobExecution:" + e1);
			}

		}
//		finally {
//			try {
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//				if (logRs != null) {
//					logRs.close();
//					logRs = null;
//				}
//				if (t != null) {
//					t.close();
//					t = null;
//				}
//				if (sqlClientBean != null) {
//					sqlClientBean.close();
//					sqlClientBean = null;
//				}
//			} catch (Exception e2) {
//				e2.printStackTrace();
//				System.out.println("QuartzTriggerListener:vetoJobExecution:"
//						+ e2);
//			}
//		}
		return vetoJob;
	}

	private void sendMail(ScheduleStatusService scheduleStatusService,String smtpServer, String mailTo, String triggerName,
			String message) {
		try {
//			Mailer mailer = new Mailer();
//			mailer.setSmtpServer(smtpServer);
//			String contentType = "text/html;charset=UTF-8";
//			String subject = "排程程式執行訊息通知";
//			String from = smtpServer;
//			String personalFrom = "public-mail-service";
//			String to = mailTo;
//			String personalTo = "";
//			String CC = "";
//			String personalCC = "";
//			String BCC = "";
//			String personalBCC = "";
//			String mailBody = "<html><head></head><body>" + message
//					+ "<br></body></html>";
//			String auth = "";
//			String userName = "";
//			String password = "";
//			mailer.sendmail(smtpServer, contentType, subject, from,
//					personalFrom, to, personalTo, CC, personalCC, BCC,
//					personalBCC, mailBody, auth, userName, password);
//
//			Result result = scheduleStatusService.findScheduleStatusByUK(triggerName);
//			if(result.getResObject() != null){
//				ScheduleStatus scheduleStatus = (ScheduleStatus)result.getResObject();
//				scheduleStatus.setSendWarningMail("Y");
//				result = scheduleStatusService.updateScheduleStatus(scheduleStatus, null);
//				if(result.getResObject() == null){
//					System.out.println("無法更新ScheduleStatus");
//				}
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ScheduleStatusService getScheduleStatusService() {
		return scheduleStatusService;
	}

	public static boolean  setScheduleStatusService(ScheduleStatusService scheduleStatusService) {
		QuartzTriggerListener.scheduleStatusService = scheduleStatusService;
		return true;
	}

	public static ScheduleLogService getScheduleLogService() {
		return scheduleLogService;
	}

	public static boolean setScheduleLogService(ScheduleLogService scheduleLogService) {
		QuartzTriggerListener.scheduleLogService = scheduleLogService;
		return true;
	}

	public static boolean isService() {
		return service;
	}

	public static boolean setService(ScheduleStatusService scheduleStatusService,ScheduleLogService scheduleLogService) {
		QuartzTriggerListener.scheduleStatusService = scheduleStatusService;
		QuartzTriggerListener.scheduleLogService = scheduleLogService;
//		QuartzTriggerListener.service = service;
		return true;
	}

}
