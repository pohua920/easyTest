package com.sinosoft.app.common.util;

import ins.framework.common.DateTime;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.sinosig.servicebus.client.ServiceBusClientForTools;
import com.sinosig.servicebus.client.ServiceBusConstants;
import com.sinosoft.app.common.model.SendMesRemark;
import com.sinosoft.sys.platform.common.Contacts;
import com.sinosoft.sys.platform.power.model.SaaUser;

/**
 * 发送邮件
 * @param mailToUser 收件人
 * @param Title 主题
 * @param Content 邮件内容
 * @Company: sinosig
 * @author 中科软
 * @Date: 2011-09-07
 */
@SuppressWarnings("unchecked")
public class EmailUtil {

	public Map mail(String receiver, String title, String content) {
		Map map = new HashMap();
		ServiceBusClientForTools serviceBusClient = new ServiceBusClientForTools();
		// 发邮件
		serviceBusClient.setUrl(PerfConstants.emailSendURL);
		Map mailMap = new HashMap();
		mailMap.put(ServiceBusConstants.MAILTO, receiver);
		mailMap.put(ServiceBusConstants.TITLE, title);
		mailMap.put(ServiceBusConstants.CONTENT, content);
		mailMap.put(ServiceBusConstants.MAILFROM, PerfConstants.emailUserName);
		mailMap.put(ServiceBusConstants.MAILPASSWORD, PerfConstants.emailPassowrd);
		map = serviceBusClient.sendMail(mailMap, null);
		return map;
	}

	public void sendNoticeEmail(String mailAddress, String... condition) {
		String emailTitle = "";
		StringBuffer emailInfo = new StringBuffer(300);
		String operateName = condition[0];// 下发的部门
		String operateType = condition[1];// 下发的类型：新增、修改、审阅
		String userName = condition[2];// 下发部门的负责人
		String startDate = condition[3];
		String endDate = condition[4];
		String operateList = null;// 审阅类型：计划、总结
		String currentName = null;// 当前审阅人：只有在审阅时，得到该当前用户
		String reviewType = null;
		String review = null;
		if (condition.length > 8) {
			operateList = condition[5];
			currentName = condition[6];
			reviewType = condition[7];
			review = condition[8];
		}
		try {
			if (Contacts.OperateADD.equals(operateType)) {
				emailTitle = "【請閱處】【新增】周工作計劃有新增（" + startDate + "至" + endDate + ") 時間為計劃區間";
				emailInfo.append(userName + "  您好:");
				emailInfo.append("\n\r     活動量及關鍵工作管理平台提示，" + operateName + "本周工作計劃已下發，請及時安排處理。");
				emailInfo.append("\n\r     請點選鏈接查看：" + PerfConstants.localhostURL);
				emailInfo.append("\n\r                                                                " + TimeUtil.getDate());
			} else if (Contacts.OperateUPDATE.equals(operateType)) {
				emailTitle = "【請閱處】【修訂】周工作計劃有更改（" + startDate + "至" + endDate + ")";
				emailInfo.append(userName + "  您好:");
				emailInfo.append("\n\r     活動量及關鍵工作管理平台提示，" + operateName + "本周工作計劃有修改，請及時安排處理。");
				emailInfo.append("\n\r     請點選鏈接查看：" + PerfConstants.localhostURL);
				emailInfo.append("\n\r                                                                " + TimeUtil.getDate());
			} else if (Contacts.OperateCHECK.equals(operateType)) {
				if (Contacts.ReportPLAN.equals(operateList)) {
					emailTitle = "【請閱處】【領導批示】公司領導對周工作計劃有批示（" + startDate + "至" + endDate + ")";
					emailInfo.append(userName + "  您好:");
					emailInfo.append("\n\r     活動量及關鍵工作管理平台提示，" + currentName + "總對部門本周工作計劃有批示，請及時安排處理。");
					emailInfo.append("\n\r     " + reviewType + ":");
					emailInfo.append("\n\r     " + review);
					emailInfo.append("\n\r     請點選鏈接查看：" + PerfConstants.localhostURL);
					emailInfo.append("\n\r                                                                " + TimeUtil.getDate());
				} else {
					emailTitle = "【請閱處】【領導批示】公司領導對周工作總結有批示（" + startDate + "至" + endDate + ")";
					emailInfo.append(userName + "  您好:");
					emailInfo.append("\n\r     活動量及關鍵工作管理平台提示，" + currentName + "總對部門本周工作總結有批示，請及時安排處理。");
					emailInfo.append("\n\r     " + reviewType + ":");
					emailInfo.append("\n\r     " + review);
					emailInfo.append("\n\r     請點選鏈接查看：" + PerfConstants.localhostURL);
					emailInfo.append("\n\r                                                                " + TimeUtil.getDate());
				}
			} else if (Contacts.OperateDELETE.equals(operateType)) {// 無效
				emailTitle = "【請閱處】【修訂】周工作計劃有更改（" + startDate + "至" + endDate + ")";
				emailInfo.append(userName + "  您好:");
				emailInfo.append("\n\r     活動量及關鍵工作管理平台提示，" + operateName + "本周工作計劃有修改，請及時安排處理。");
				emailInfo.append("\n\r     請點選鏈接查看：" + PerfConstants.localhostURL);
				emailInfo.append("\n\r                                                                " + TimeUtil.getDate());
			}
			EmailUtil emailUtil = new EmailUtil();
			Map map = emailUtil.mail(mailAddress, emailTitle, emailInfo.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取邮件配置文件，获取相关配置信息（from、subject和velocityPath）
	 * @param tagName
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static String getConfigInfo(String tagName) throws ParserConfigurationException, SAXException, IOException {
		String tagValue = "";
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(EmailUtil.class.getResource("resource.xml").getPath());
			Element element = (Element) doc.getElementsByTagName(tagName).item(0);
			tagValue = element.getFirstChild().getNodeValue();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			throw e;
		} catch (SAXException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return tagValue;
	}

	/**
	 * 邮箱地址可靠性判断
	 * @param addr 邮件地址
	 * @return
	 */
	public static boolean isAddressValidate(String addr) {

		return true;
	}

	// add by luonan 定时发邮件
	public void sendEmailClik(String mailAddress, String... condition) {

	}

	// add by luonan 组装邮件内容
	public void sendEmailClik(List list, SaaUser saaUser) {
		if (list.size() > 0 && saaUser != null && !"".equals(saaUser.getEmail()) && saaUser.getEmail() != null) {
			StringBuffer emailInfo = new StringBuffer(300);
			int i = 1;
			SendMesRemark sendMesRemark = null;
			DateTime time = null;
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				sendMesRemark = (SendMesRemark) iter.next();
				if (i == 1) {
					time = new DateTime(sendMesRemark.getStartDate());
					emailInfo.append(sendMesRemark.getRemark() + ",您好:");
					emailInfo.append("\n\r     活動量與關鍵工作系統提醒您，您計劃於" + time.getYear() + "年" + time.getMonth() + "月" + time.getDay() + "日開展以下工作事項，請登錄系統查看詳情：");
				}
				emailInfo.append("\n\r     " + i + ". 工作事項：" + sendMesRemark.getTaskName() + "。");
				if (!"".equals(sendMesRemark.getJobdeMand()) && sendMesRemark.getJobdeMand() != null) {
					emailInfo.append("\n\r        工作要求：" + sendMesRemark.getJobdeMand() + "。");
				} else {
					emailInfo.append("\n\r        工作要求：無。");
				}
				i++;
			}
			emailInfo.append("\n\r     請點選鏈接查看：" + PerfConstants.localhostURL);
			emailInfo.append("\n\r                                                                " + TimeUtil.getDate());
			String weekInfo = TimeUtil.getWeekString(2, sendMesRemark.getStartDate());
			String emailTitle = "【請處理】 " + time.getMonth() + "月" + time.getDay() + "日（" + weekInfo + "）計劃工作事項即將到達開始時間";
			EmailUtil emailUtil = new EmailUtil();
			Map map = emailUtil.mail(saaUser.getEmail(), emailTitle, emailInfo.toString());
		}

	}

	// 定时周五发邮件模板
	public String[] sendMesFriday(String start, String end) {
		StringBuffer emailInfo = new StringBuffer(300);
		String[] listMail = new String[2];
		String[] starD = start.split("-");
		String[] endD = end.split("-");
		listMail[0] = "【請查收】" + starD[0] + "年" + starD[1] + "月" + starD[2] + "日至" + endD[0] + "年" + endD[1] + "月" + endD[2] + "日的各部門周工作事項總結報表";
		emailInfo.append("管理員，您好：");
		emailInfo.append("\n\r     活動量與關鍵工作系統提醒您，請注意查收附件內容：《" + starD[0] + "年" + starD[1] + "月" + starD[2] + "日至" + endD[0] + "年" + endD[1] + "月" + endD[2] + "日的各部門周工作事項總結報表》");
		emailInfo.append("\n\r                                                                " + TimeUtil.getDate());
		listMail[1] = emailInfo.toString();
		return listMail;

	}

}
