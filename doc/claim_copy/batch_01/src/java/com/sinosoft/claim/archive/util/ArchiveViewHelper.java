package com.sinosoft.claim.archive.util;

import ins.framework.common.Page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.archive.service.facade.ArchiveService;
import com.sinosoft.claim.archive.vo.ArchiveDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLDocArchive;
import com.sinosoft.claim.schema.model.PrpLDocArchiveLog;
import com.sinosoft.claim.schema.model.PrpLDocArchiveLogId;
import com.sinosoft.claim.schema.model.UtiUwLevel;
import com.sinosoft.claim.schema.service.facade.PrpLDocArchiveLogService;
import com.sinosoft.claim.schema.service.facade.PrpLDocArchiveService;
import com.sinosoft.claim.schema.service.facade.UtiUwLevelService;
import com.sinosoft.claim.util.StringConvert;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * <p>
 * Title: ArchiveViewHelper
 * </p>
 * <p>
 * Description:资料归档ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2013
 * </p>
 * @author 中科软
 * @version 1.0 <br>
 */
public class ArchiveViewHelper {
	/** 资料归档调阅信息服务 */
	private PrpLDocArchiveService prpLDocArchiveService;
	/** 资料归档调阅服务 */
	private ArchiveService archiveService;
	/** 人员级别设置信息服务 */
	private UtiUwLevelService utiUwLevelService;
	/** 资料归档调阅日志信息服务 */
	private PrpLDocArchiveLogService prpLDocArchiveLogService;

	/**
	 * 默认构造方法
	 */
	public ArchiveViewHelper() {
	}

	/**
	 * 查询资料归档数据
	 * @param httpServletRequest 返回给页面的request
	 * @return 资料归档对象
	 * @throws Exception
	 */
	public PrpLDocArchive viewToDto(HttpServletRequest request) throws Exception {
		String claimNo = StringUtils.rightTrim(request.getParameter("claimNo"));
		PrpLDocArchive prpLDocArchive = prpLDocArchiveService.findPrpLDocArchive(claimNo);
		return prpLDocArchive;
	}
	/**
	 * 查询资料归档数据
	 * @param request
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public ArchiveDto viewToDto(HttpServletRequest request, String claimNo) throws Exception {
		UserDto userDto = (UserDto) request.getSession().getAttribute("user");
		ArchiveDto archiveDto = new ArchiveDto();
		// 获取归档调阅主表信息
		PrpLDocArchive prpLDocArchive = prpLDocArchiveService.findPrpLDocArchive(claimNo);
		// 组织归档调阅日志信息
		PrpLDocArchiveLog prpLDocArchiveLog = new PrpLDocArchiveLog();
		prpLDocArchiveLog.getId().setClaimNo(prpLDocArchive.getClaimNo());
		prpLDocArchiveLog.getId().setSerialNo(1);
		prpLDocArchiveLog.setRegistNo(prpLDocArchive.getRegistno());
		prpLDocArchiveLog.setPolicyNo(prpLDocArchive.getPolicyNo());
		prpLDocArchiveLog.setComcode(prpLDocArchive.getComCode());
		prpLDocArchiveLog.setInsuredCode(prpLDocArchive.getInsuredCode());
		prpLDocArchiveLog.setInsuredName(prpLDocArchive.getInsuredName());
		prpLDocArchiveLog.setEndCaseDate((DateTime) prpLDocArchive.getEndCaseDate());
		prpLDocArchiveLog.setSumDutyPaid(prpLDocArchive.getSumDutyPaid());
		prpLDocArchiveLog.setStatus("1");
		prpLDocArchiveLog.setOperatorCode(userDto.getUserCode());
		prpLDocArchiveLog.setOperatorName(userDto.getUserName());
		prpLDocArchiveLog.setOperatorDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		archiveDto.setPrpLDocArchive(prpLDocArchive);
		archiveDto.setPrpLDocArchiveLog(prpLDocArchiveLog);
		return archiveDto;
	}

	public Page overtimeDtoToView(HttpServletRequest request, int pageNo, int recordPerPage) throws Exception {
		String claimNo = StringUtils.rightTrim(request.getParameter("claimNo"));
		String claimNoSign = StringUtils.rightTrim(request.getParameter("claimNoSign"));
		String policyNo = StringUtils.rightTrim(request.getParameter("policyNo"));
		String policyNoSign = StringUtils.rightTrim(request.getParameter("policyNoSign"));
		String insuredName = StringUtils.rightTrim(request.getParameter("insuredName"));
		String insuredNameSign = StringUtils.rightTrim(request.getParameter("insuredNameSign"));
		String applicantName = StringUtils.rightTrim(request.getParameter("applicantName"));
		String applicantNameSign = StringUtils.rightTrim(request.getParameter("applicantNameSign"));
		String startReviewDate = StringUtils.rightTrim(request.getParameter("startReviewDate"));
		String startReviewDateSign = StringUtils.rightTrim(request.getParameter("startReviewDateSign"));
		String estimateReturnDate = StringUtils.rightTrim(request.getParameter("estimateReturnDate"));
		String estimateReturnDateSign = StringUtils.rightTrim(request.getParameter("estimateReturnDateSign"));
		// 根据页面输入信息生成SQL where子句
		StringBuffer conditions = new StringBuffer(200);
		conditions.append("Status=4");
		if (claimNo.length() > 0) {
			conditions.append(StringConvert.convertString("claimNo", claimNo, claimNoSign));
		}

		if (policyNo.length() > 0) {
			conditions.append(StringConvert.convertString("policyNo", policyNo, policyNoSign));
		}

		if (insuredName.length() > 0) {
			conditions.append(StringConvert.convertString("insuredName", insuredName, insuredNameSign));
		}

		if (applicantName.length() > 0) {
			conditions.append(StringConvert.convertString("applicantName", applicantName, applicantNameSign));
		}

		if (startReviewDate.length() > 0) {
			conditions.append(StringConvert.convertDate("startReviewDate", startReviewDate, startReviewDateSign));
		}

		if (estimateReturnDate.length() > 0) {
			conditions.append(StringConvert.convertDate("estimateReturnDate", estimateReturnDate, estimateReturnDateSign));
		}

		UserDto userDto = (UserDto) request.getSession().getAttribute("user");
		String comCode = userDto.getComCode();
		// 拼接机构条件
		String strConditionCom = this.addPowerCom(comCode, "PrpLDocArchive", "ComCode");
		conditions.append(" and ");
		conditions.append(strConditionCom);

		Page page = this.archiveService.findPageByConditions(conditions.toString(), pageNo, recordPerPage);
		return page;
	}

	public PrpLDocArchive extensionDtoToView(HttpServletRequest request) throws Exception {
		String claimNo = StringUtils.rightTrim(request.getParameter("claimNo"));
		UserDto userDto = (UserDto) request.getSession().getAttribute("user");
		String conditions = "claimNo='" + claimNo + "' AND ApplicantCode='" + userDto.getUserCode() + "' AND Status='4'";
		List<PrpLDocArchive> collection = this.archiveService.findByConditions(conditions);
		PrpLDocArchive prpLDocArchive = null;
		if (collection == null || collection.size() == 0) {
			return null;
		}
		Iterator<PrpLDocArchive> it = collection.iterator();
		while (it.hasNext()) {
			prpLDocArchive = it.next();
		}
		return prpLDocArchive;
	}

	public PrpLDocArchiveLog retrivalDtoToView(HttpServletRequest request) throws Exception {
		String claimNo = StringUtils.rightTrim(request.getParameter("claimNo"));
		int serialNo = this.archiveService.getCount(claimNo);
		UserDto userDto = (UserDto) request.getSession().getAttribute("user");
		String comCode = userDto.getComCode();
		// 拼接机构条件
		String strConditionCom = this.addPowerCom(comCode, "Prpldocarchivelog", "ComCode");
		StringBuffer conditions = new StringBuffer(200);
		conditions.append("ClaimNo='");
		conditions.append(claimNo);
		conditions.append("' and Status=");
		conditions.append(3);
		conditions.append(" and SerialNo=");
		conditions.append(serialNo);
		conditions.append(" and ");
		conditions.append(strConditionCom);
		PrpLDocArchiveLog prpLDocArchiveLog = this.archiveService.findPrpLDocArchiveLogByConditions(conditions.toString());
		return prpLDocArchiveLog;
	}

	public PrpLDocArchiveLog toarchiveDtoToView(HttpServletRequest request) throws Exception {
		String claimNo = StringUtils.rightTrim(request.getParameter("claimNo"));
		int serialNo = this.archiveService.getCount(claimNo);
		UserDto userDto = (UserDto) request.getSession().getAttribute("user");
		String comCode = userDto.getComCode();
		// 拼接机构条件
		String strConditionCom = this.addPowerCom(comCode, "Prpldocarchivelog", "ComCode");
		StringBuffer conditions = new StringBuffer(200);
		conditions.append("ClaimNo='");
		conditions.append(claimNo);
		conditions.append("' and Status=");
		conditions.append(4);
		conditions.append(" and SerialNo=");
		conditions.append(serialNo);
		conditions.append(" and ");
		conditions.append(strConditionCom);
		PrpLDocArchiveLog prpLDocArchiveLog = this.archiveService.findPrpLDocArchiveLogByConditions(conditions.toString());
		return prpLDocArchiveLog;
	}

	/**
	 * 查询资料归档数据
	 * @param request 返回给页面的request
	 * @param claimNo 赔案号
	 * @param pageNo 页码
	 * @param recordPerPage 每页显示的行数
	 * @throws Exception
	 */
	public Page setPrpLDocArchiveDtoToView(HttpServletRequest request, int pageNo, int recordPerPage) throws Exception {
		String editType = StringUtils.rightTrim(request.getParameter("editType"));
		String claimNo = StringUtils.rightTrim(request.getParameter("claimNo"));
		String claimNoSign = StringUtils.rightTrim(request.getParameter("claimNoSign"));
		String policyNo = StringUtils.rightTrim(request.getParameter("policyNo"));
		String policyNoSign = StringUtils.rightTrim(request.getParameter("policyNoSign"));
		String insuredName = StringUtils.rightTrim(request.getParameter("insuredName"));
		String insuredNameSign = StringUtils.rightTrim(request.getParameter("insuredNameSign"));
		String startDate = StringUtils.rightTrim(request.getParameter("startDate"));
		String endDate = StringUtils.rightTrim(request.getParameter("endDate"));
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		String classCodes = "";
		String strClassCodes = "";
		String strSql = "";
		String[] classCode = null;
		StringBuffer sb = new StringBuffer("");
		if (request.getParameter("strClassCode") != null) {
			classCodes = StringUtils.rightTrim(request.getParameter("strClassCode"));
		}
		if (classCodes.length() > 0) {
			classCode = classCodes.split(",");
			sb.append(" claimNo in ( select claimNo from prpLclaim where classCode in (");
			for (int m = 0; m < classCode.length; m++) {
				sb.append("'"+classCode[m]).append("',");
			}
			strClassCodes = sb.toString();
			strSql = strClassCodes.substring(0, strClassCodes.length() - 1) + "))";
		} else {
			strSql = " 1=1";
		}
		UserDto userDto = (UserDto) request.getSession().getAttribute("user");
		String comCode = userDto.getComCode();
		// 拼接机构条件
		String strConditionCom = this.addPowerCom(comCode, "PrpLDocArchive", "ComCode");
		// 根据页面输入信息生成SQL where子句
		StringBuffer conditions = new StringBuffer(200);
		conditions.append("1=1");
		if (claimNo.length() > 0) {
			conditions.append(StringConvert.convertString("claimNo", claimNo, claimNoSign));
		}
		if (policyNo.length() > 0) {
			conditions.append(StringConvert.convertString("policyNo", policyNo, policyNoSign));
		}
		if (insuredName.length() > 0) {
			conditions.append(StringConvert.convertString("insuredName", insuredName, insuredNameSign));
		}
		if ("archiveBefore".equals(editType)) {// 未归档资料查询
			conditions.append(" and status=0");
		} else if ("query".equals(editType)) {// 资料调阅查询
			conditions.append(" and status<>0 and endcaseDate between to_date('" + startDate + "','yyyy/MM/dd') and to_date('" + endDate + "','yyyy/MM/dd') and " + strSql);
		} else {// 资料调阅申请前查询
			conditions.append(" and status=1");
		}
		conditions.append(" and ");
		conditions.append(strConditionCom);
		Page page = prpLDocArchiveService.findByConditions(conditions.toString(), pageNo, recordPerPage);
		return page;
	}

	/**
	 * 查询资料归档数据
	 * @param httpServletRequest 返回给页面的request
	 * @throws Exception
	 */
	public void setPrpLDocArchiveDtoToView(HttpServletRequest request) throws Exception {
		String claimNo = StringUtils.rightTrim(request.getParameter("claimNo"));
		PrpLDocArchive prpLDocArchive = prpLDocArchiveService.findPrpLDocArchive(claimNo);
		int serialNo = this.archiveService.getCount(request.getParameter("claimNo"));
		request.setAttribute("serialNo", serialNo);
		request.setAttribute("prpLDocArchive", prpLDocArchive);
	}

	/**
	 * 查询资料归档调阅日志数据
	 * @param request 返回给页面的request
	 * @param pageNo 页码
	 * @param recordPerPage 每页显示的行数
	 * @throws Exception
	 */
	public void setPrpLDocArchiveLogDtoToView(HttpServletRequest request, String pageNo, String recordPerPage) throws Exception {
		int pageno = Integer.parseInt(DataUtils.nullToZero(pageNo));
		int pagesize = Integer.parseInt(DataUtils.nullToZero(recordPerPage));
		PrpLDocArchiveLog prpLDocArchiveLog = new PrpLDocArchiveLog();
		String statement = this.prepareStatement(request);
		Page page = this.prpLDocArchiveLogService.findPrpLDocArchiveLog(statement, pageno, pagesize);
		List<?> list = page.getResult();
		List<PrpLDocArchiveLog> archiveList = new ArrayList<PrpLDocArchiveLog>();
		for (int i = 0; i < list.size(); i++) {
			PrpLDocArchiveLog prpLDocArchiveLog1 = (PrpLDocArchiveLog) list.get(i);
			archiveList.add(prpLDocArchiveLog1);
		}
		prpLDocArchiveLog.setArchiveList(archiveList);
		request.setAttribute("prpLDocArchiveLogDto", prpLDocArchiveLog);
	}

	/**
	 * 查询资料归档调阅日志数据
	 * @param request 返回给页面的request
	 * @throws Exception
	 */
	public void setPrpLDocArchiveLogDtoToView(HttpServletRequest request) throws Exception {
		String claimNo = StringUtils.rightTrim(request.getParameter("claimNo"));
		int serialNo = this.archiveService.getCount(claimNo);
		PrpLDocArchiveLogId prpLDocArchiveLogId = new PrpLDocArchiveLogId();
		prpLDocArchiveLogId.setClaimNo(claimNo);
		prpLDocArchiveLogId.setSerialNo(serialNo);
		PrpLDocArchiveLog prpLDocArchiveLog = this.archiveService.findByPrimaryKey(prpLDocArchiveLogId);
		PrpLDocArchive prpLDocArchive = prpLDocArchiveService.findPrpLDocArchive(claimNo);
		request.setAttribute("prpLDocArchiveLogDto", prpLDocArchiveLog);
		request.setAttribute("prpLDocArchiveDto", prpLDocArchive);
	}

	/**
	 * 自定义查询条件
	 * @param request
	 * @return 查询条件
	 * @throws Exception
	 */
	private String prepareStatement(HttpServletRequest request) throws Exception {
		String claimNo = StringUtils.rightTrim(request.getParameter("claimNo"));
		UserDto userDto = (UserDto) request.getSession().getAttribute("user");
		String userCode = userDto.getUserCode();
		String comCodeTemp = "";
		String classCode = "";
		String riskCode = "";
		int nodeNo = 0;
		boolean result = true;// 没有查到双核权限数据则result = true,返回1=0
		String strConditionCom = "";
		String strConditionClassRisk = "";
		String strConditionAll = "";
		String statementTemp = "";
		UtiUwLevel utiUwLevel = new UtiUwLevel();
		String conditions1 = "UWTYPE='C' AND VALIDSTATUS='1' AND USERCODE='" + userCode + "'";
		List<UtiUwLevel> list = this.utiUwLevelService.findByConditions(conditions1);
		for (Iterator<UtiUwLevel> iter = list.iterator(); iter.hasNext();) {
			result = false;
			utiUwLevel = iter.next();
			comCodeTemp = utiUwLevel.getId().getComCode();
			classCode = utiUwLevel.getClassCode();
			riskCode = utiUwLevel.getId().getRiskCode();
			nodeNo = utiUwLevel.getId().getNodeNo();
			// 拼接机构条件
			strConditionCom = this.addPowerCom(comCodeTemp, "Prpldocarchivelog", "ComCode");
			// 拼接险种条件
			strConditionClassRisk = this.addPowerClassRisk(classCode, riskCode, "Prplclaim");
			strConditionAll = strConditionCom + " AND " + strConditionClassRisk;
			if (iter.hasNext()) {
				statementTemp = statementTemp + " SELECT DISTINCT Prpldocarchivelog.* FROM Prpldocarchivelog,Prplclaim WHERE Prpldocarchivelog.claimno=Prplclaim.claimno AND Prpldocarchivelog.nodeno='" + nodeNo + "' AND " + strConditionAll
						+ " UNION ";
			} else {
				statementTemp = statementTemp + " SELECT DISTINCT Prpldocarchivelog.* FROM Prpldocarchivelog,Prplclaim WHERE Prpldocarchivelog.claimno=Prplclaim.claimno AND Prpldocarchivelog.nodeno='" + nodeNo + "' AND " + strConditionAll;
			}
		}

		if (result) {
			throw new UserException(1, 3, "賠案歸檔調閱", "人員沒有調閱審核權限!");
		}

		String strReturn = "SELECT t1.* FROM (" + statementTemp + ") t1,(SELECT MAX(serialno) serialno,claimno FROM Prpldocarchivelog GROUP BY claimno) t2 WHERE t1.claimno=t2.claimno AND t1.serialno=t2.serialno";
		if (claimNo.length() > 0) {
			strReturn = strReturn + StringConvert.convertString("t1.claimNo", claimNo, "=");
		}

		return strReturn;
	}

	/**
	 * 拼接机构条件
	 * @param comCode
	 * @param tableName
	 * @param tableCol
	 * @return
	 * @throws Exception
	 */
	private String addPowerCom(String comCode, String tableName, String tableCol) throws Exception {
		String strCondition = "";
		if (("00").equals(comCode)) {
			strCondition = "1=1";
		} else {
			strCondition = tableName+"."+tableCol + " in" + " (Select ComCode from prpdCompany Start With ComCode  = '" + comCode + "' Connect By Prior comCode = uppercomCode  and  " + "" + " prior ComCode != ComCode  and validstatus='1')";
		}
		return strCondition;
	}

	/**
	 * 拼接险种条件
	 * @param classCode
	 * @param riskCode
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	private String addPowerClassRisk(String classCode, String riskCode, String tableName) throws Exception {
		String strCondition = "";
		String strConditionClass = "";
		String strConditionRisk = "";
		String strClassCode = "";
		String strRiskCode = "";
		if (("*").equals(classCode) || ("*").equals(riskCode)) {
			strCondition = "1=1";
		} else {
			String[] classCodeFieldsArray = StringUtils.split(classCode, ",");
			String[] riskCodeFieldsArray = StringUtils.split(riskCode, ",");
			for (int i = 0; i < classCodeFieldsArray.length; i++) {
				if (i < (classCodeFieldsArray.length - 1)) {
					strClassCode += "'" + classCodeFieldsArray[i] + "',";
				} else if (i == (classCodeFieldsArray.length - 1)) {
					strClassCode += "'" + classCodeFieldsArray[i] + "'";
				}
			}

			if (("").equals(strClassCode) || strClassCode == null) {
				strConditionClass = "1=0";
			} else {
				strConditionClass = tableName + ".CLASSCODE IN (" + strClassCode + ")";
			}

			for (int j = 0; j < riskCodeFieldsArray.length; j++) {
				if (j < (riskCodeFieldsArray.length - 1)) {
					strRiskCode += "'" + riskCodeFieldsArray[j] + "',";
				} else if (j == (riskCodeFieldsArray.length - 1)) {
					strRiskCode += "'" + riskCodeFieldsArray[j] + "'";
				}
			}
			if (("").equals(strRiskCode) || strRiskCode == null) {
				strConditionRisk = "1=0";
			} else {
				strConditionRisk = tableName + ".RISKCODE IN (" + strRiskCode + ")";
			}

			strCondition = "(" + strConditionClass + " OR " + strConditionRisk + ")";
		}
		return strCondition;
	}

	public PrpLDocArchiveService getPrpLDocArchiveService() {
		return prpLDocArchiveService;
	}

	public void setPrpLDocArchiveService(PrpLDocArchiveService prpLDocArchiveService) {
		this.prpLDocArchiveService = prpLDocArchiveService;
	}

	public ArchiveService getArchiveService() {
		return archiveService;
	}

	public void setArchiveService(ArchiveService archiveService) {
		this.archiveService = archiveService;
	}

	public UtiUwLevelService getUtiUwLevelService() {
		return utiUwLevelService;
	}

	public void setUtiUwLevelService(UtiUwLevelService utiUwLevelService) {
		this.utiUwLevelService = utiUwLevelService;
	}

	public PrpLDocArchiveLogService getPrpLDocArchiveLogService() {
		return prpLDocArchiveLogService;
	}

	public void setPrpLDocArchiveLogService(PrpLDocArchiveLogService prpLDocArchiveLogService) {
		this.prpLDocArchiveLogService = prpLDocArchiveLogService;
	}

}
