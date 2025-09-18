package com.tlg.aps.bs.salesAgentDateAlertService.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
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

import com.tlg.aps.bs.salesAgentDateAlertService.SalesAgentDateAlertService;
import com.tlg.aps.vo.SalesAgentDateAlertVo;
import com.tlg.dms.entity.PrpdNewCode;
import com.tlg.dms.service.PrpdNewCodeService;
import com.tlg.sales.entity.Prpdagent;
import com.tlg.sales.service.PrpdagentService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.DateUtils;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;

import freemarker.template.Configuration;
import freemarker.template.Template;

/** mantis：Sales0024，處理人員：CC009，需求單編號：Sales0024 銷管系統-業務員登錄證到期提醒Mail與簡訊通知排程 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class SalesAgentDateAlertServiceImpl implements SalesAgentDateAlertService {

	private static final Logger logger = Logger.getLogger(SalesAgentDateAlertServiceImpl.class);
	
	private ConfigUtil configUtil;
	private PrpdagentService prpdagentService;
	private PrpdNewCodeService prpdNewCodeService;

	@Override
	public Result excute() throws Exception {
		Date now = new Date();
		//發送給業務員的E-mail
		List<SalesAgentDateAlertVo> mailVo = this.sendMailToAgent(now);
		
		//產簡訊發送檔
		File file = this.genTxtFile(now);
		
		//發送E-mail
		String msg = "";
		if(mailVo.size() > 0 || file != null) {
			msg = sendEmail(file,mailVo);
		}else {
			return this.getReturnResult("務員登錄證到期通知排程作業執行成功，本次查無資料作業。");
		}
		
		if(msg.equals("")) {
			logger.info(msg);
			return this.getReturnResult(msg);
		}
		
		return this.getReturnResult("務員登錄證到期通知排程作業執行成功");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SalesAgentDateAlertVo> sendMailToAgent(Date sysdate) throws Exception{
		List<SalesAgentDateAlertVo> list = new ArrayList<SalesAgentDateAlertVo>();
		Map<String, Object> params = new HashMap<>();
		Mailer mailer = new Mailer();
		params.put("sysdate", new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.addMonth(sysdate, 3)));
		params.put("email", "1");
		Result result = prpdagentService.selectForSalesAgentDateAlert(params);
		if (result.getResObject() == null) {
			return list;
		}
		list = (List<SalesAgentDateAlertVo>) result.getResObject();
		for (SalesAgentDateAlertVo vo : list) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			String subject = "【中國信託產險通知】財產保險業務員5年到期換證通知，未如期完成者將予以停招或撤銷登錄。";
			String mailTo = vo.getEmail();

			Configuration config = new Configuration();
			config.setClassForTemplateLoading(this.getClass(), "/");
			Template template = config.getTemplate("ftl/salesAgentDateAlertMail.ftl", "UTF-8");
			Map<String, Object> map = new HashMap<>();
			map.put("agentname", vo.getAgentname());
			map.put("logincode", vo.getLogincode());
			map.put("loginenddate", sdf.format(vo.getLoginenddate()));
			StringWriter stringWriter = new StringWriter();
			template.process(map, stringWriter);

			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
					"", "", "", "", "", stringWriter.toString(), "smtp", "newims", "2012newims");
			vo.setMaillog(sysdate);
			//發送時間紀錄回寫
			Prpdagent prpdagent = new Prpdagent();
			prpdagent.setAgentcode(vo.getAgentcode());
			prpdagent.setMaillog(sysdate);
			prpdagentService.updatePrpdagent(prpdagent);
		}
			
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public File genTxtFile(Date sysdate) throws Exception {
		List<SalesAgentDateAlertVo> list = new ArrayList<SalesAgentDateAlertVo>();
		Map<String, Object> params = new HashMap<>();
		sysdate = DateUtils.addMonth(sysdate, 3);
		params.put("sysdate", new SimpleDateFormat("yyyy-MM-dd").format(sysdate));
		params.put("mobile", "1");
		Result result = prpdagentService.selectForSalesAgentDateAlert(params);
		if (result.getResObject() == null) {
			return null;
		}
		list = (List<SalesAgentDateAlertVo>) result.getResObject();
		String content = genContent(list);
		String folder = configUtil.getString("tempFolder") + "SALESAGENT" + File.separator;
		String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".txt";
		File folderPath = new File(folder);
		if (!folderPath.exists()) {
			folderPath.mkdirs();
		}
		File file = new File(folder+filename);
		try(BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8"))){
			bufWriter.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	@SuppressWarnings("unused")
	private String genContent(List<SalesAgentDateAlertVo> list) throws Exception {
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for (SalesAgentDateAlertVo vo : list) {
			sb.append("'");
			sb.append(vo.getMobile());
			sb.append("','");
			sb.append("中信託託產險-");
			sb.append(vo.getAgentname());
			sb.append(" 先生/小姐您好,您登錄於本公司之財產保險業務員(登錄證字號");
			sb.append(vo.getLogincode());
			sb.append(")有效日期至");
			sb.append(sdf.format(vo.getLoginenddate()));
			sb.append("，提醒您應於有效日期前完成年度教育訓練及登錄證換發。如未完成換證手續，");
			sb.append("依據「保險業務員管理規則」第九條及中華民國產物保險商業同業公會「財產保險業務員教育訓練通報暨換證作業規範」第12條規定");
			sb.append("，本公司將予以三個月以上一年以下停止招攬行為或撤銷業務員登錄，如有疑問請洽詢您的輔導專員，謝謝。中國信託產險 敬啟'");
			sb.append("\r\n");
		}
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String sendEmail(File file,List<SalesAgentDateAlertVo> mailVoList) throws Exception {
		Mailer mailer = new Mailer();
		Map<String, Object> params = new HashMap<>();
		params.put("codetype", "Salemailjob");
		params.put("codecode", "user");
		params.put("validstatus", "1");
		Result result = this.prpdNewCodeService.findPrpdNewCodeByParams(params);
		if (result.getResObject() == null) {
			return "無法取得PRPDNEWCODE資料，無法寄送MAIL";
		}

		List<PrpdNewCode> list = (List<PrpdNewCode>) result.getResObject();
		PrpdNewCode prpdNewCode = list.get(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String subject = "換證通知已發送Email明細及簡訊發送檔";
		String mailTo = prpdNewCode.getCodecname();
		if (StringUtils.isBlank(mailTo)) {
			return "無法取得CODECNAME資料，無法寄送MAIL";
		}

		StringBuilder sb = new StringBuilder();
		if (mailVoList.size() > 0) {
			sb.append("<table border=1 style='border-collapse: collapse;'>");
			sb.append("<tr bgcolor='#70bbd9'>");
			sb.append("<td>發送日期</td>");
			sb.append("<td>登錄證字號</td>");
			sb.append("<td>姓名</td>");
			sb.append("<td>登錄到期日</td>");
			sb.append("</tr>");
			for (SalesAgentDateAlertVo vo : mailVoList) {
				sb.append("<tr>");
				sb.append("<td>" + sdf.format(vo.getMaillog()) + "</td>");
				sb.append("<td>" + vo.getLogincode() + "</td>");
				sb.append("<td>" + vo.getAgentname() + "</td>");
				sb.append("<td>" + sdf.format(vo.getLoginenddate()) + "</td>");
				sb.append("</tr>");
			}
			sb.append("</table>");
		}

		if (file != null) {
			String[] filePath = { file.getPath() };
			String[] fileName = { file.getName() };
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
					"", "", "", "", "", sb.toString(), "smtp", "newims", "2012newims", filePath, fileName);
		} else {
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
					"", "", "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		}
		return "";
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
}
