package com.tlg.xchg.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.NewepolicyVo;
import com.tlg.exception.SystemException;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Constants;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.xchg.dao.NewepolicyresultDao;
import com.tlg.xchg.entity.Newepolicyresult;
import com.tlg.xchg.service.NewepolicyresultService;
/** mantis：OTH0159，處理人員：CD094，需求單編號：OTH0159 電子保單系統條款檢核不通過資料通知(APS)  **/
@Transactional(value = "xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class NewepolicyresultServiceImpl implements NewepolicyresultService {

	private NewepolicyresultDao newepolicyresultDao;
	private ConfigUtil configUtil;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findNewepolicyresultByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Newepolicyresult> searchResult = newepolicyresultDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findNewepolicyresultByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = newepolicyresultDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Newepolicyresult> searchResult = newepolicyresultDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findPdfUrl(String  policyno) throws SystemException, Exception {
		Result result = new Result();
		if (null == policyno) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params=new HashMap();
		params.put("policyno", policyno);
		params.put("pattern", "\\d$");
		String searchResult = newepolicyresultDao.selectForUrl(params);
		if (StringUtil.nullToSpace(searchResult).isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findNewepolicyVo(PageInfo pageInfo) throws SystemException, Exception {
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		int rowCount = newepolicyresultDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		List<NewepolicyVo> searchResult = newepolicyresultDao.selectForErr(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findYdayNewepolicyVo() throws SystemException, Exception {

		Result result = new Result();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, -1);
		Date dataDate = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String formatDataDate = sdf.format(dataDate);
		String startDate = formatDataDate + " 00:00:00";
		String endDate = formatDataDate + " 23:59:59";

		Map<String, String> params = new HashMap<>();
		params.put("startCreateDate", startDate);
		params.put("endCreateDate", endDate);

		List<NewepolicyVo> searchResult = newepolicyresultDao.selectForYdayErr(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));

		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public String sendEmail(List<NewepolicyVo> newepolicyresultErrList) throws SystemException, Exception {
		Mailer mailer = new Mailer();
		StringBuilder tmpMsg = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String subject = configUtil.getString("epolicyResultCheckSubject") + "-" + sdf.format(new Date());
		String mailTo = configUtil.getString("epolicyResultCheckMailTo");
		String mailCc = configUtil.getString("epolicyResultCheckMailCc");
//		String mailTo = "cd094@ctbcins.com";
//		String mailCc = "";

		StringBuilder sb = new StringBuilder();
		sb.append("<p>電子保單報送檢核機制-檢核不符明細表</p>");
		sb.append("<table border=1 style='border-collapse: collapse;'>");
		sb.append("<tr bgcolor='#70bbd9'>");
		sb.append("<td>序號</td>");
		sb.append("<td>保單號碼</td>");
		sb.append("<td>批單號碼</td>");
		sb.append("<td>險種代碼</td>");
		sb.append("<td>傳輸日期</td>");
		sb.append("</tr>");
		int serial = 1;
		for (NewepolicyVo newepolicyVo : newepolicyresultErrList) {
			sb.append("<tr>");
			sb.append("<td>" + serial + "</td>");
			sb.append("<td>" + StringUtil.nullToSpace(newepolicyVo.getPolicyno()) + "</td>");
			sb.append("<td>" + StringUtil.nullToSpace(newepolicyVo.getEndorseno()) + "</td>");
			sb.append("<td>" + newepolicyVo.getRiskcode() + "</td>");
			sb.append("<td>" + sdf.format(newepolicyVo.getDcreate()) + "</td>");
			sb.append("</tr>");
			serial++;
		}
		sb.append("</table>");
		mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo, "",
				mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims");

		return tmpMsg.toString();
	}

	public NewepolicyresultDao getNewepolicyresultDao() {
		return newepolicyresultDao;
	}

	public void setNewepolicyresultDao(NewepolicyresultDao newepolicyresultDao) {
		this.newepolicyresultDao = newepolicyresultDao;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

}
