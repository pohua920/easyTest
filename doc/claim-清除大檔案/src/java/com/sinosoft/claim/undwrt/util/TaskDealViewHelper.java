/*
 * @(#)TaskDealViewHelper.java	Feb 19, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.service.facade.PrpDriskService;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.common.util.UIQueryAction;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.PrpDriskConfig;
import com.sinosoft.claim.schema.model.UtiUwLevel;
import com.sinosoft.claim.schema.service.facade.UtiUwLevelService;
import com.sinosoft.platform.bl.action.domain.BLUtiUwLevelAction;
import com.sinosoft.platform.bl.facade.BLUtiUwLevelFacade;
import com.sinosoft.platform.dto.domain.UtiUwLevelDto;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.dto.domain.UwNotionDto;
import com.sinosoft.undwrt.dto.domain.WfLogDto;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class TaskDealViewHelper {
	/** 机构信息服务 */
	private PrpDcompanyService prpDcompanyService;
	/** 代码服务 */
	private CodeService codeService;
	/** 险种配置信息服务 */
	private PrpDriskConfigService prpDriskConfigService;
	/** 险种信息服务 */
	private PrpDriskService prpDriskService;
	/** 人员级别设置信息服务 */
	private UtiUwLevelService utiUwLevelService;

	public TaskDealViewHelper() {
	}

	/**
	 * 获取核赔任务查询条件中node和comcode的条件
	 * @param userCode
	 * @param includeJunior 是否包含下级
	 * @param tableName
	 * @param selectLowNode
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getPermissionControlStatement(String userCode, boolean includeJunior, String tableName, String selectLowNode) throws SQLException, Exception {
		BLUtiUwLevelFacade blUtiUwLevelFacade = new BLUtiUwLevelFacade();
		String strCondition = null;
		String statement = null;

		// 下面的函数参数分别为
		// 机构代码，现在不用
		// userCode:双核人员代码
		// tableName:双核任务查询表;wflog/view_wflogall
		// "MAKECOM":按照表中的操作机构进行查询
		// "C":写死，核赔为C
		// includeJunior:是否允许察看下级任务标志
		strCondition = blUtiUwLevelFacade.addPower("", userCode, tableName, "COMCODE", "C", includeJunior);

		if (!"".equals(selectLowNode)) {
			com.sinosoft.undwrt.bl.facade.BLUtiUwLevelFacade blUtiUwLevelFacadeForCom = new com.sinosoft.undwrt.bl.facade.BLUtiUwLevelFacade();
			BLUtiUwLevelAction blUtiUwLevelAction = new BLUtiUwLevelAction();
			String strSQL = "userCode ='" + userCode + "' and nodeno>" + selectLowNode + " and uwtype='C' and validstatus='1'";
			Collection<UtiUwLevelDto> col = blUtiUwLevelFacade.findByConditions(strSQL);
			Iterator<UtiUwLevelDto> it = col.iterator();
			UtiUwLevelDto utiUwLevelDto = null;
			if (it.hasNext()) {
				utiUwLevelDto = (UtiUwLevelDto) it.next();
			}
			if (utiUwLevelDto != null) {
				String comPower = blUtiUwLevelFacadeForCom.addPowerCom(utiUwLevelDto.getComCode(), tableName, "COMCODE");
				String strConditionClassRisk = blUtiUwLevelAction.addPowerClassRisk(utiUwLevelDto.getClassCode(), utiUwLevelDto.getRiskCode(), tableName);
				strCondition = strCondition + " OR (" + strConditionClassRisk + " and " + comPower + " and " + tableName + ".NODENO='" + selectLowNode + "')";
			}
		}
		statement = " SELECT * from " + tableName + " where  " + tableName + ".LogNo <> 1 AND " + tableName + ".NodeNo <> 1 AND (" + tableName + ".BusinessType = 'C' OR  " + tableName + ".BusinessType = 'Y') AND (" + strCondition + ")";

		return statement;
	}

	/**
	 * 拼接页面录入的查询条件
	 * @param req
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	private String getQueryConditionStatement(HttpServletRequest req, String tableName) throws Exception {
		String riskCategoryTag = "=";
		String riskCategoryVal = StringUtils.trimToEmpty(req.getParameter("riskCategory"));
		String[] riskCodeVal = req.getParameterValues("riskCode");
		String businessNoTag = req.getParameter("businessNoTag");
		String businessNoVal = StringUtils.trimToEmpty(req.getParameter("businessNo"));
		String contractNoTag = req.getParameter("contractNoTag");
		String contractNoVal = StringUtils.trimToEmpty(req.getParameter("contractNo"));
		String comCodeTag = req.getParameter("comCodeTag");
		String comCodeVal = StringUtils.trimToEmpty(req.getParameter("comCode"));
		// add by huguoning 20071009 start
		String registNoVal = req.getParameter("RegistNo");
		String registNoTag = req.getParameter("RegistNoTag");
		// add by huguoning 20071009 end
		String[] nodeStatusVal = req.getParameterValues("nodeStatus");
		String flowInTime1Tag = ">=";
		String flowInTime1Val = StringUtils.trimToEmpty(req.getParameter("flowInTime1"));
		String flowInTime2Tag = "<=";
		String flowInTime2Val = StringUtils.trimToEmpty(req.getParameter("flowInTime2"));//
		if (flowInTime1Val.length() > 0) {
			flowInTime1Val = flowInTime1Val + " 00:00:00";
		}
		if (flowInTime2Val.length() > 0) {
			flowInTime2Val = flowInTime2Val + " 23:59:59";
		}
		if (businessNoVal == null || businessNoVal.length()<1) {
			businessNoVal = req.getParameter("businessNoVal");
		}
		if (comCodeVal == null || comCodeVal.length()<1) {
			comCodeVal = req.getParameter("comCodeVal");
		}
		String licenseNoTag = req.getParameter("licenseNoTag");
		String licenseNoVal = StringUtils.trimToEmpty(req.getParameter("licenseNo"));
		String identifyTypeTag = "=";
		String identifyTypeVal = req.getParameter("identifyType");
		String identifyNumberTag = req.getParameter("identifyNumberTag");
		String identifyNumberVal = StringUtils.trimToEmpty(req.getParameter("identifyNumber"));
		String[] relateContractNoYesNoVal = req.getParameterValues("relateContractNoYesNo");
		String relateContractNoTag = req.getParameter("relateContractNoTag");
		String relateContractNoVal = StringUtils.trimToEmpty(req.getParameter("relateContractNo"));
		String policyNoTag = StringUtils.trimToEmpty(req.getParameter("policyNoTag"));
		String policyNo = StringUtils.trimToEmpty(req.getParameter("policyNo"));
		String claimNoTag = StringUtils.trimToEmpty(req.getParameter("claimNoTag"));
		String claimNo = StringUtils.trimToEmpty(req.getParameter("claimNo"));

		if (policyNo == null || policyNo.length()<1) {
			policyNo = req.getParameter("policyNoVal");
		}
		if (claimNo == null || claimNo.length()<1) {
			claimNo = req.getParameter("claimNoVal");
		}
		req.setAttribute("businessNoTag", businessNoTag);
		req.setAttribute("businessNoVal", businessNoVal);
		req.setAttribute("comCodeTag", comCodeTag);
		req.setAttribute("comCodeVal", comCodeVal);
		req.setAttribute("RegistNoTag", registNoTag);
		req.setAttribute("RegistNo", registNoVal);
		req.setAttribute("flowInTime1", flowInTime1Val.length()>10?flowInTime1Val.substring(0, 11):"");
		req.setAttribute("flowInTime2", flowInTime2Val.length()>10?flowInTime2Val.substring(0, 11):"");
		req.setAttribute("policyNoTag", policyNoTag);
		req.setAttribute("policyNoVal", policyNo);
		req.setAttribute("claimNoTag", claimNoTag);
		req.setAttribute("claimNoVal", claimNo);
		StringBuffer statement = new StringBuffer("");
		if (riskCodeVal == null || riskCodeVal.length<1) {
			riskCodeVal = (String[]) req.getSession().getAttribute("riskCodeVal");
		}
		if (relateContractNoYesNoVal == null || relateContractNoYesNoVal.length<1) {
			relateContractNoYesNoVal = (String[]) req.getSession().getAttribute("relateContractNoYesNoVal");
		}
		if (nodeStatusVal == null || nodeStatusVal.length<1) {
			nodeStatusVal = (String[]) req.getSession().getAttribute("nodeStatusVal");
		}

		UIQueryAction uiQueryAction = new UIQueryAction();
		statement.append(uiQueryAction.getCharConditions("RiskCategory", riskCategoryTag, riskCategoryVal));
		statement.append(uiQueryAction.getCharInConditions("RiskCode", riskCodeVal));
		statement.append(uiQueryAction.getCharConditions("BusinessNo", businessNoTag, businessNoVal));
		statement.append(uiQueryAction.getCharConditions("ContractNo", contractNoTag, contractNoVal));
		statement.append(uiQueryAction.getCharConditions("ComCode", comCodeTag, comCodeVal));
		statement.append(uiQueryAction.getCharInConditions("NodeStatus", nodeStatusVal));
		statement.append(uiQueryAction.getCharConditions("FlowInTime", flowInTime1Tag, flowInTime1Val));
		statement.append(uiQueryAction.getCharConditions("FlowInTime", flowInTime2Tag, flowInTime2Val));
		statement.append(uiQueryAction.getCharConditions("PolicyNo", policyNoTag, policyNo));
		statement.append(uiQueryAction.getCharConditions("ClaimNo", claimNoTag, claimNo));

		// 报案号查询
		if (registNoVal != null && !registNoVal.trim().equals("")) {
			String[] claimNoVals = codeService.translateBusinessCodes(registNoVal.trim(), true);
			if (claimNoVals.length < 1) {
				statement.append(" And 1 = 0 ");
			} else {
				statement.append(uiQueryAction.getCharInConditions("ClaimNo", claimNoVals));
			}
		}

		if (riskCategoryVal.equals("1")) {// 车险
			statement.append(uiQueryAction.getCharConditions("LicenseNo", licenseNoTag, licenseNoVal));
		} else if (riskCategoryVal.equals("4")) {// 意健
			statement.append(uiQueryAction.getCharConditions("IdentifyType", identifyTypeTag, identifyTypeVal));
			statement.append(uiQueryAction.getCharConditions("IdentifyNumber", identifyNumberTag, identifyNumberVal));
		} else if (riskCategoryVal.equals("2")) {// 水险（货运险）
			if (relateContractNoYesNoVal != null && relateContractNoYesNoVal.length == 1) {
				if (relateContractNoYesNoVal[0].equals("Yes")) {
					if (relateContractNoVal.length() > 0) {
						statement.append(uiQueryAction.getCharConditions("RelateContractNo", relateContractNoTag, relateContractNoVal));
					} else {
						statement.append(" AND RelateContractNo is not null");
					}
				} else {
					statement.append(" AND RelateContractNo is null");
				}
			} else if (relateContractNoYesNoVal != null && relateContractNoYesNoVal.length == 2) {
				if (relateContractNoVal.length() > 0) {
					statement.append(uiQueryAction.getCharConditions("RelateContractNo", relateContractNoTag, relateContractNoVal));
				}
			}
		}
		//要保人ID
		String strAppliIdentifyNumber = StringUtils.trimToEmpty(req.getParameter("AppliIdentifyNumber"));
		String strAppliIdentifyNumberSign = StringUtils.trimToEmpty(req.getParameter("AppliIdentifyNumberSign"));
		//被保險人ID
		String strInsuredIdentifyNumber = StringUtils.trimToEmpty(req.getParameter("InsuredIdentifyNumber"));
		String strInsuredIdentifyNumberSign = StringUtils.trimToEmpty(req.getParameter("InsuredIdentifyNumberSign"));
		//事故日期
		String strDamageStartDate = StringUtils.trimToEmpty(req.getParameter("damageStartDate"));
		String strDamageEndDate = StringUtils.trimToEmpty(req.getParameter("damageEndDate"));
		// 要保人ID 参与检索
		if (DataUtils.emptyToNull(strAppliIdentifyNumber) != null) {// 检索了要保人
			statement.append(" and exists (");
			statement.append(" select 0 from prpcinsured where "+tableName+".policyno = prpcinsured.policyno ");
			statement.append(" and prpcinsured.insuredflag = '2' ");
			statement.append(StringConvert.convertString("prpcinsured.identifynumber", strAppliIdentifyNumber, strAppliIdentifyNumberSign));
			statement.append(" ) ");
		}
		// 检索了被保险人、或其身份证字号、统一编号
		if (DataUtils.emptyToNull(strInsuredIdentifyNumber) != null) {
			statement.append(" and exists (");
			statement.append(" select 0 from prpcinsured where "+tableName+".policyno = prpcinsured.policyno ");
			statement.append(" and prpcinsured.insuredflag = '1' ");
			statement.append(StringConvert.convertString("prpcinsured.identifynumber", strInsuredIdentifyNumber, strInsuredIdentifyNumberSign));
			statement.append(" ) ");
		}
		//事故日期参与查询
		if(DataUtils.emptyToNull(strDamageStartDate) != null
				|| DataUtils.emptyToNull(strDamageEndDate) != null){
			statement.append(" and exists (");
			statement.append(" select 0 from prplclaim where prplclaim.claimno = "+tableName+".claimno");
			statement.append(StringConvert.convertDate("prplclaim.damagestartdate", strDamageStartDate, ">="));
			statement.append(StringConvert.convertDate("prplclaim.damagestartdate", strDamageEndDate, "<="));
			statement.append(" ) ");
		}
		return statement.toString();
	}

	/**
	 * 获取核赔任务查询sql
	 * @param request
	 * @param tableName
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getHepeiTaskQueryStatement(HttpServletRequest request, String tableName) throws SQLException, Exception {
		String strReturn = "";
		HttpSession session = request.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String userCode = StringUtils.trimToEmpty(user.getUserCode());
		String underling = StringUtils.trimToEmpty(request.getParameter("underling"));
		String editType = StringUtils.trimToEmpty(request.getParameter("EditType"));
		/*
		 * 增加包含下级具体到哪一级
		 */
		String selectUnderling = StringUtils.trimToEmpty(request.getParameter("selectUnderling"));
		String selectLowNode = "";
		if ("A".equals(selectUnderling)) {
			underling = "N";
		} else if ("B".equals(selectUnderling)) {
			underling = "Y";
		} else {
			underling = "N";
			selectLowNode = selectUnderling;
		}
		boolean includeJunior = false;
		if ("Y".equals(underling)) {
			includeJunior = true;
		}
		request.setAttribute("underling", underling);
		request.setAttribute("selectUnderling", selectUnderling);

		// 获取核赔任务查询sql
		String strConditionAll = "";
		String strConditionCom = "";
		String strConditionClassRisk = "";
		String strConditionNode = "";
		String conditions = "";
		String classCode = "";
		String riskCode = "";
		String comCodeTemp = "";
		int nodeNo = 0;
		boolean result = true;// 没有查到双核权限数据则result = true,返回1=0
		String statementTemp = "";
		String statementCommon = " ) And " + tableName + ".LogNo <> 1 AND " + tableName + ".NodeNo <> 1 AND (" + tableName + ".BusinessType = 'C' OR  " + tableName + ".BusinessType = 'Y') " + this.getQueryConditionStatement(request, tableName);

		UtiUwLevel utiUwLevel = new UtiUwLevel();
		conditions = "UWTYPE = 'C' AND VALIDSTATUS  = '1' AND USERCODE = '" + userCode + "'";
		List<UtiUwLevel> utiUwLevelList = this.getUtiUwLevelService().findByConditions(conditions);
		// add by zhangyurui 增加核赔初审岗，当没有核赔初审岗的省市，默认包含nodeno=4 begin
		PrpDriskConfig prpdRiskConfig = null;
		String userComCode = user.getComCode();
		if (ConstantCodes.MAINCOMPANYCOMCODE.equals(userComCode)||ConstantCodes.MAINCOMPANYCOMCODE.equals(userComCode)) {
			prpdRiskConfig = prpDriskConfigService.findByPrimaryKey(userComCode, "0000", "FIRST_UNDWRT_FLAG");
		} else {
			PrpDcompany prpDcompany = null;
			List<PrpDcompany> collect = prpDcompanyService.findByConditions("comcode = (Select comcode From (Select Comcode,comlevel From Prpdcompany Start With Comcode = '" + userComCode
					+ "' Connect By  Comcode = Prior Uppercomcode And  Uppercomcode != Prior Comcode And Validstatus = '1') Where comlevel = '1')");
			Iterator<PrpDcompany> itera = collect.iterator();
			if (itera.hasNext()) {
				prpDcompany = (PrpDcompany) itera.next();
			} else {
				throw new Exception("該人員沒有對應的二級機構，請與管理員聯系！");
			}
			prpdRiskConfig = prpDriskConfigService.findByPrimaryKey(prpDcompany.getComCode(), "0000", "FIRST_UNDWRT_FLAG");
		}
		if (prpdRiskConfig == null) {
			throw new Exception("請在prpdriskconfig中維護該機構的二級機構是否含有核賠初審崗配置項，請與管理員聯系！");
		}
		// add by zhangyurui 增加核赔初审岗，当没有核赔初审岗的省市，默认包含nodeno=4 end
		for (int i=0;i<utiUwLevelList.size();i++) {
			result = false;
			utiUwLevel = utiUwLevelList.get(i);
			comCodeTemp = utiUwLevel.getId().getComCode();
			classCode = utiUwLevel.getClassCode();
			riskCode = utiUwLevel.getId().getRiskCode();
			nodeNo = utiUwLevel.getId().getNodeNo();
			// modify by zhangyurui 修改逻辑，当不包含核赔初审岗的省市，当选择或本身是核赔二级C时，自动包含核赔初审岗
			// begin
			if ((nodeNo == 5 || "5".equals(selectLowNode)) && "0".equals(prpdRiskConfig.getConfigValue())) {
				if (includeJunior) {
					strConditionNode = tableName + ".NODENO <=" + nodeNo;
				} else {
					if (!"".equals(selectLowNode)) {
						strConditionNode = tableName + ".NODENO in ('" + nodeNo + "','" + selectLowNode + "','4')";
					} else {
						strConditionNode = tableName + ".NODENO in ('" + nodeNo + "','4')";
					}
				}
			} else {
				if (includeJunior) {
					strConditionNode = tableName + ".NODENO <=" + nodeNo;
				} else {
					if (!"".equals(selectLowNode)) {
						strConditionNode = tableName + ".NODENO in ('" + nodeNo + "','" + selectLowNode + "')";
					} else {
						strConditionNode = tableName + ".NODENO =" + nodeNo;
					}
				}
			}
			// 拼接机构条件
			if("deal".equals(editType)){
				strConditionCom = this.addPowerCom(comCodeTemp, tableName, "ComCode");
			}else {
				strConditionCom = " 1=1 ";
			}
			// 拼接险种条件
			strConditionClassRisk = this.addPowerClassRisk(classCode, riskCode, tableName);
			strConditionAll = "(" + strConditionNode + " AND (" + strConditionCom + " AND " + strConditionClassRisk + "))";

//			if (!"".equals(selectLowNode)) {
//			}

			if (i<utiUwLevelList.size()-1) {
				statementTemp = statementTemp + " SELECT DISTINCT " + tableName + ".* FROM " + tableName + " WHERE (" + strConditionAll + statementCommon + "UNION";
			} else {
				statementTemp = statementTemp + " SELECT DISTINCT " + tableName + ".* FROM " + tableName + " WHERE (" + strConditionAll + statementCommon;
			}
		}

		if (result) {
			throw new UserException(0, 0, "核賠", "人員沒有核賠的權限!");
		} else {
			strReturn = statementTemp;
		}

		strReturn = "select * from (" + strReturn + ") order by  flowintime desc";

		return strReturn;
	}
	/**
	 * 设置节点隐藏
	 * @param nodeStatus
	 * @return
	 */
	public String[] getBatchButtonEnabledByNodeStatus(String nodeStatus) {
		String[] enabled = new String[3];
		if (nodeStatus.equals("0")) {
			enabled[0] = enabled[1] = enabled[2] = "disabled";
		} else if (nodeStatus.equals("4")) {
			enabled[0] = enabled[1] = "disabled";
			enabled[2] = "";
		} else {
			enabled[0] = enabled[1] = "";
			enabled[2] = "disabled";
		}
		return enabled;
	}

	public List<WfLogDto> getCheckboxSelectTaskCollection(HttpServletRequest request) {
		List<WfLogDto> collection = new ArrayList<WfLogDto>();
		String[] checkbox = request.getParameterValues("checkboxSelect");
		String[] businessNo = new String[checkbox.length];
		String[] comCode = new String[checkbox.length];
		String[] riskCode = new String[checkbox.length];
		String[] modelNo = new String[checkbox.length];
		String[] nodeNo = new String[checkbox.length];
		String[] flowId = new String[checkbox.length];
		String[] logNo = new String[checkbox.length];
		String[] nodeStatus = new String[checkbox.length];
		WfLogDto wfLogDto = null;
		for (int i = 1; i < checkbox.length; i++) {
			businessNo[i] = request.getParameterValues("BusinessNo")[Integer.parseInt(checkbox[i])];
			comCode[i] = request.getParameterValues("ComCode")[Integer.parseInt(checkbox[i])];
			riskCode[i] = request.getParameterValues("RiskCode")[Integer.parseInt(checkbox[i])];
			modelNo[i] = request.getParameterValues("ModelNo")[Integer.parseInt(checkbox[i])];
			nodeNo[i] = request.getParameterValues("NodeNo")[Integer.parseInt(checkbox[i])];
			flowId[i] = request.getParameterValues("FlowID")[Integer.parseInt(checkbox[i])];
			logNo[i] = request.getParameterValues("LogNo")[Integer.parseInt(checkbox[i])];
			nodeStatus[i] = request.getParameterValues("NodeStatus")[Integer.parseInt(checkbox[i])];
			wfLogDto = new WfLogDto();
			wfLogDto.setBusinessNo(businessNo[i]);
			wfLogDto.setComCode(comCode[i]);
			wfLogDto.setRiskCode(riskCode[i]);
			wfLogDto.setModelNo(Integer.parseInt(modelNo[i]));
			wfLogDto.setNodeNo(Integer.parseInt(nodeNo[i]));
			wfLogDto.setFlowID(flowId[i]);
			wfLogDto.setLogNo(Integer.parseInt(logNo[i]));
			wfLogDto.setNodeStatus(nodeStatus[i]);
			collection.add(wfLogDto);
		}
		return collection;
	}

	public List<WfLogDto> getBatchTaskCollection(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String userCode = user.getUserCode();
		String userName = user.getUserName();
		String deptCode = user.getComCode();
		String deptName = user.getComName();
		List<WfLogDto> collection = new ArrayList<WfLogDto>();
		String[] businessNo = request.getParameterValues("businessNo");
		String[] comCode = request.getParameterValues("comCode");
		String[] modelNo = request.getParameterValues("modelNo");
		String[] nodeNo = request.getParameterValues("nodeNo");
		String[] flowId = request.getParameterValues("flowId");
		String[] logNo = request.getParameterValues("logNo");
		String[] nodeStatus = request.getParameterValues("nodeStatus");
		String[] nextNodeNo = request.getParameterValues("nextNodeNo");
		String[] nextNodeName = request.getParameterValues("nextNodeName");
		WfLogDto wfLogDto = null;
		for (int i = 0; i < businessNo.length; i++) {
			wfLogDto = new WfLogDto();
			wfLogDto.setBusinessNo(businessNo[i]);
			wfLogDto.setComCode(comCode[i]);
			wfLogDto.setModelNo(Integer.parseInt(modelNo[i]));
			wfLogDto.setNodeNo(Integer.parseInt(nodeNo[i]));
			wfLogDto.setFlowID(flowId[i]);
			wfLogDto.setLogNo(Integer.parseInt(logNo[i]));
			wfLogDto.setNodeStatus(nodeStatus[i]);
			wfLogDto.setNextNodeNo(Integer.parseInt(nextNodeNo[i]));
			wfLogDto.setNextNodeName(nextNodeName[i]);
			wfLogDto.setOperatorCode(userCode);
			wfLogDto.setOperatorName(userName);
			wfLogDto.setDeptCode(deptCode);
			wfLogDto.setDeptName(deptName);
			collection.add(wfLogDto);
		}
		return collection;
	}

	public List<UwNotionDto> getBatchNotionCollection(HttpServletRequest request) {
		String[] flowId = request.getParameterValues("flowId");
		String[] logNo = request.getParameterValues("logNo");
		String handleText = StringUtils.trimToEmpty(request.getParameter("HandleText"));
		handleText = StringUtils.replace(handleText, "'", "''");
		List<UwNotionDto> notionCollection = new ArrayList<UwNotionDto>();
		UwNotionDto uwNotionDto = null;
		for (int i = 0; i < flowId.length; i++) {
			uwNotionDto = new UwNotionDto();
			uwNotionDto.setFlowID(flowId[i]);
			uwNotionDto.setLogNo(Integer.parseInt(logNo[i]));
			uwNotionDto.setHandleText(handleText);
			notionCollection.add(uwNotionDto);
		}
		return notionCollection;
	}

	/**
	 * 拼接机构条件
	 * @param comCode
	 * @param tableName
	 * @param tableCol
	 * @return
	 * @throws Exception
	 */
	public String addPowerCom(String comCode, String tableName, String tableCol) throws Exception {
		String strCondition = "";
		if (("00").equals(comCode)) {
			strCondition = "1=1";
		} else {

			strCondition = tableName + "." + tableCol + " in" + " (Select ComCode from prpdCompany Start With ComCode  = '" + comCode + "' Connect By Prior comCode = uppercomCode  and  " + "" + " prior ComCode != ComCode  and validstatus='1')";
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
	public String addPowerClassRisk(String classCode, String riskCode, String tableName) throws Exception {
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

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public PrpDriskConfigService getPrpDriskConfigService() {
		return prpDriskConfigService;
	}

	public void setPrpDriskConfigService(PrpDriskConfigService prpDriskConfigService) {
		this.prpDriskConfigService = prpDriskConfigService;
	}

	public PrpDriskService getPrpDriskService() {
		return prpDriskService;
	}

	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}

	public UtiUwLevelService getUtiUwLevelService() {
		return utiUwLevelService;
	}

	public void setUtiUwLevelService(UtiUwLevelService utiUwLevelService) {
		this.utiUwLevelService = utiUwLevelService;
	}

}
