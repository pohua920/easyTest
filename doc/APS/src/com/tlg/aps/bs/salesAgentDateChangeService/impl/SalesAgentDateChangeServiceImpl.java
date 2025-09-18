package com.tlg.aps.bs.salesAgentDateChangeService.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.salesAgentDateChangeService.SalesAgentDateChangeService;
import com.tlg.aps.vo.SalesAgentDateAlertVo;
import com.tlg.aps.vo.SalesAgentDateReserveVo;
import com.tlg.dms.entity.PrpdNewCode;
import com.tlg.dms.service.PrpdNewCodeService;
import com.tlg.sales.entity.PrpdagentIdv;
import com.tlg.sales.entity.PrpdagentLoginDateReserve;
import com.tlg.sales.service.PrpdagentIdvService;
import com.tlg.sales.service.PrpdagentLoginDateReserveService;
import com.tlg.sales.service.PrpdagentService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;

/** mantis：SALES0033 ，處理人員： DP0713 ，需求單編號：銷管系統-業務員換證日期更新排程及通知 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class SalesAgentDateChangeServiceImpl implements SalesAgentDateChangeService {

	private static final Logger logger = Logger.getLogger(SalesAgentDateChangeServiceImpl.class);
	
	private ConfigUtil configUtil;
	private PrpdagentService prpdagentService;
	private PrpdagentIdvService prpdagentIdvService;
	private PrpdagentLoginDateReserveService prpdagentLoginDateReserveService;
	private PrpdNewCodeService prpdNewCodeService;

	@Override
	public Result excute() throws Exception {
		Date now = new Date();
		PrpdagentLoginDateReserve pldr = new PrpdagentLoginDateReserve();

		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String taiwanDateString = com.tlg.util.DateUtils.getTaiwanDateString(new Date(),com.tlg.util.DateUtils.ROC_DATE_PATTERN);
		Map<String, Object> params = new HashMap<>();
		Mailer mailer = new Mailer();
		String subject = "【測試通知】"+taiwanDateString+"啟動通知。"+new Date();
		String mailTo = "dp0713@ctbcins.com";//vo.getEmail();
		sb.append("<table>");
		sb.append("<tr>");
		sb.append("<td>測試中...</td>");
		mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
				"", "", "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		
		//發送給業務員的E-mail
		List<SalesAgentDateReserveVo> mailVo = this.sendMailToAgent();
		
		return this.getReturnResult("務員登錄證到期通知排程作業執行成功");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SalesAgentDateReserveVo> sendMailToAgent() throws Exception{
		List<SalesAgentDateReserveVo> list = new ArrayList<SalesAgentDateReserveVo>();
		Map<String, Object> params = new HashMap<>();
		Mailer mailer = new Mailer();
		Date sysdate = new Date();
		String sysdateStr = new SimpleDateFormat("yyyy-MM-dd").format(sysdate);
		params.put("sysdate", sysdateStr);
		params.put("email", "1");
		Result result = prpdagentLoginDateReserveService.selectForAgentLoginDateReserve(params);
		if (result.getResObject() == null) {//PrpdagentLoginDateReserve
			return list;
		}
		List<Map<String,String>> changeList = new ArrayList<Map<String,String>>();
		list = (List<SalesAgentDateReserveVo>) result.getResObject();
		for(SalesAgentDateReserveVo sadr:list) {
			Map<String,String> params2 = new HashMap<>();
			params2.put("agentCode", sadr.getAgentCode());
			
			Result result2 = prpdagentIdvService.findPrpdagentByUk(params2);
			if(null!=result2) {
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
				Map<String,String> changMap = new HashMap<>();
				PrpdagentIdv pdiv = (PrpdagentIdv)result2.getResObject();

				changMap.put("agentCode",pdiv.getAgentCode());
				changMap.put("orgLoginDate",sdf2.format(pdiv.getLoginDate()));
				changMap.put("orgLoginEndDate",sdf2.format(pdiv.getLoginEndDate()));
				
				pdiv.setLoginDate(sadr.getLoginDate());
				pdiv.setLoginEndDate(sadr.getLoginEndDate());
				
				changMap.put("newLoginDate",sdf2.format(sadr.getLoginDate()));
				changMap.put("newLoginEndDate",sdf2.format(sadr.getLoginEndDate()));
				prpdagentIdvService.updatePrpdagent(pdiv);
				
				PrpdagentLoginDateReserve pldr = new PrpdagentLoginDateReserve();
				pldr.setAgentCode(sadr.getAgentCode());//條件
				pldr.setCertiNo(sadr.getCertiNo());//條件
				pldr.setLoginCode(sadr.getLoginCode());//條件
//				pldr.setBusinessSource(sadr.getBusinessSource());//條件
				pldr.setCreateDate(sadr.getCreateDate());//條件
				
				pldr.setRunDate(new Date());//寫入
				prpdagentLoginDateReserveService.updatePrpdagentLoginDateReserve(pldr);
				
				changeList.add(changMap);
			}
		}
		if(changeList.size()>0) {
			
			StringBuffer sb = new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			String taiwanDateString = com.tlg.util.DateUtils.getTaiwanDateString(new Date(),com.tlg.util.DateUtils.ROC_DATE_PATTERN);
			String subject = taiwanDateString+" 業務員換證明細";
			String mailTo = "ba037@ctbcins.com,bl019@ctbcins.com,dp0713@ctbcins.com";//vo.getEmail();
//			String mailTo = "dp0713@ctbcins.com";//vo.getEmail();
			sb.append("<table>");
			sb.append("<tr>");
			sb.append("<td>中信託託產險</td>");
			sb.append("<td>於"+taiwanDateString+"執行排程-自動換證清單"+"</td>");
			sb.append("");
			sb.append("<table border=1 style='border-collapse: collapse;'>");
			sb.append("<tr bgcolor='#70bbd9'>");
			sb.append("<td>業務員代碼</td>");
			sb.append("<td>原_登錄日期</td>");
			sb.append("<td>原_登錄到期日</td>");
			sb.append("<td>新_登錄日期</td>");
			sb.append("<td>新_登錄到期日</td>");
			sb.append("</tr>");
			for (Map<String,String> mp : changeList) {
				sb.append("<tr>");
				sb.append("<td>" + mp.get("agentCode") + "</td>");
				sb.append("<td>" + mp.get("orgLoginDate") + "</td>");
				sb.append("<td>" + mp.get("orgLoginEndDate") + "</td>");
				sb.append("<td>" + mp.get("newLoginDate") + "</td>");
				sb.append("<td>" + mp.get("newLoginEndDate") + "</td>");
				sb.append("</tr>");
			}
			sb.append("</tr>");
			sb.append("</table>");
			sb.append("");
			sb.append("</table>");
			sb.append("\r\n");
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
					"", "", "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		}
		return list;
	}
	
	
	
	private Result getReturnResult(String msg) {
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}
	
	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public PrpdagentService getPrpdagentService() {
		return prpdagentService;
	}

	public void setPrpdagentService(PrpdagentService prpdagentService) {
		this.prpdagentService = prpdagentService;
	}

	public PrpdNewCodeService getPrpdNewCodeService() {
		return prpdNewCodeService;
	}

	public void setPrpdNewCodeService(PrpdNewCodeService prpdNewCodeService) {
		this.prpdNewCodeService = prpdNewCodeService;
	}

	public PrpdagentLoginDateReserveService getPrpdagentLoginDateReserveService() {
		return prpdagentLoginDateReserveService;
	}

	public void setPrpdagentLoginDateReserveService(PrpdagentLoginDateReserveService prpdagentLoginDateReserveService) {
		this.prpdagentLoginDateReserveService = prpdagentLoginDateReserveService;
	}

	public PrpdagentIdvService getPrpdagentIdvService() {
		return prpdagentIdvService;
	}

	public void setPrpdagentIdvService(PrpdagentIdvService prpdagentIdvService) {
		this.prpdagentIdvService = prpdagentIdvService;
	}
}
