package com.sinosoft.claim.regist.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PowerService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLcallCenter;
import com.sinosoft.claim.schema.model.PrpLcallCenterId;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.service.facade.PrpLcallCenterService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.UtiUserGradeTaskService;
import com.sinosoft.claim.ui.control.action.UICallCenterAction;
import com.sinosoft.claim.ui.control.action.UIPowerInterface;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * @author 中科軟
 * @description 呼叫中心
 */

public class CallCenterPerfectAction extends Struts2Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 備案服務 */
	private PrpLregistService prpLregistService = null;
	/** 呼叫中心服務 */
	private PrpLcallCenterService prpLcallCenterService = null;
	/** 立案服務 */
	private PrpLclaimService prpLclaimService = null;
	/** 機構員工崗位權限服務 */
	private UtiUserGradeTaskService utiUserGradeTaskService = null;
	/** 代码服务 */
	private CodeService codeService;
	/** 权限服务 */
	private PowerService powerService;

	/**
	 * 呼叫中心
	 * @return NONE
	 * @throws Exception
	 */
	public String callcenterPerfect() throws Exception {
		HttpServletRequest httpServletRequest = super.getRequest();
		String actionType = "";
		actionType = httpServletRequest.getParameter("actionType");
		// 查询
		if (actionType.equals("query")) {
			try {
				List<?> prpLregistList = null;
				String pageSize = httpServletRequest.getParameter("pageSize");
				if (pageSize == null || "".equals(pageSize)) {
					pageSize = AppConfig.get("sysconst.ROWS_PERPAGE");
				}
				String pageNo = httpServletRequest.getParameter("pageNo");
				if (pageNo == null || pageNo.trim().equals("")) {
					pageNo = "1";
				}
				int intRecordPerPage = Integer.parseInt(pageSize);
				int intPageNo = Integer.parseInt(pageNo);
				String conditions = "";
				// 翻页查询条件
				String condition = httpServletRequest.getParameter("condition");
				if (condition != null && condition.trim().length() > 0) {
					conditions = condition;
				} else {
					conditions = this.generateConditions(httpServletRequest);
				}
				Page page = prpLregistService.findByConditions(conditions, intPageNo, intRecordPerPage);

				prpLregistList = page.getResult();
				// 判断该报案是否已立案，是否允许修改,並取得该报案的服务单号
				if (!CommonUtils.isEmpty(prpLregistList)) {
					HttpSession session = httpServletRequest.getSession();
					UserDto user = (UserDto) session.getAttribute("user"); // 用户信息
					int power = this.checkModifyPower(user);
					for (int i = 0; i < prpLregistList.size(); i++) {
						PrpLregist prpLregist = (PrpLregist) prpLregistList.get(i);
						String strRiskType = codeService.translateRiskCodetoRiskType(prpLregist.getRiskCode());
						// 取得服务单号
						int maxSerialNo = 0;
						try {
							maxSerialNo = new UICallCenterAction().getMaxSerialNo(prpLregist.getRegistNo());
						} catch (Exception ex) {
							ex.printStackTrace();
							throw ex;
						}
						PrpLcallCenter prpLcallCenter = null;
						PrpLcallCenterId prpLcallCenterId = new PrpLcallCenterId();
						prpLcallCenterId.setRegistNo(prpLregist.getRegistNo());
						prpLcallCenterId.setSerialNo(maxSerialNo);
						prpLcallCenter = prpLcallCenterService.findPrpLcallCenter(prpLcallCenterId);
						if (prpLcallCenter != null) {
							prpLregist.setServiceNo(prpLcallCenter.getServiceNo());
						} else {
							prpLregist.setServiceNo("CX400");
						}
						//modifyFlag  00-已註銷，不可修改；01-未立案，可修改；10：已立案，不可修改；11-已立案，可修改；20-已結案，不可修改；21-已結案，可修改
						if (prpLregist.getCancelDate() != null) {
							prpLregist.setModifyFlag("00");//已註銷，不可修改
							// 已注销，不能修改
							continue;
						}
						List<PrpLclaim> prpLclaimList = prpLclaimService.findByRegistNo(prpLregist.getRegistNo());
						if (!CommonUtils.isEmpty(prpLclaimList)) {// 已立案，不能修改
							prpLregist.setModifyFlag("10");//已立案，不可修改
							if (ConstantCodes.CLASSCODE_D.equals(strRiskType) ) {//車險特殊權限
								boolean end = true;//是否已全部結案
								for (int j = 0; j < prpLclaimList.size(); j++) {
									if (prpLclaimList.get(j).getEndCaseDate() == null) {// 立案后结案前，有修改权限
										end = false;
										break;
									}
								}
								if(end){//已結案
									prpLregist.setModifyFlag("2" + (power == 2 ? "1" : "0" ));
								} else {//已立案，未結案
									prpLregist.setModifyFlag("1" + (power > 0 ? "1" : "0" ));
								}
							}
							continue;
						}
						prpLregist.setModifyFlag("01");//未立案，可修改
					}
				}
				this.writeJSONData(page, "registNo", "policyNo", "riskCode", "insuredName", "reportDate", "serviceNo", "modifyFlag");
			} catch (Exception ex) {
				ex.printStackTrace();
				this.writeJSONMsg(ex.getMessage());
			}
		}
		return NONE;
	}

	/**
	 * 修改权限校验
	 * @param userDto
	 * @return 0-无权限，1-立案后可修改，2-结案后仍可修改
	 * @throws Exception
	 */
	//mantis： CLM0067 ，處理人員：  BK007 蘇哲 ，需求單編號： CLM0067.理賠系統-備案修改權限修正   開始
	public int checkModifyPower(UserDto user) throws Exception {
//		QueryRule queryRule = QueryRule.getInstance();
//		queryRule.addEqual("id.userCode", user.getUserCode());
//		queryRule.addEqual("id.taskCode", ConstantCodes.MODIFYENDCA);// 结案后可修改
//		// 结案后可修改
//		List<UtiUserGradeTask> utiUserGradeTaskList = utiUserGradeTaskService.findUtiUserGradeTask(queryRule);
//		if (!CommonUtils.isEmpty(utiUserGradeTaskList)) {// 有結案後可修改權限
//			return 2;
//		} else {
//			queryRule = QueryRule.getInstance();
//			queryRule.addEqual("id.userCode", user.getUserCode());
//			queryRule.addEqual("id.taskCode", ConstantCodes.MODIFYCLAIM);// 立案后可修改
//			utiUserGradeTaskList = utiUserGradeTaskService.findUtiUserGradeTask(queryRule);
//			if (!CommonUtils.isEmpty(utiUserGradeTaskList)) {//立案後 未結案可修改權限
//				return 1;
//			}
//		}
//		return 0;
		if(utiUserGradeTaskService.checkPower(user, ConstantCodes.MODIFYENDCA)){ // 结案后可修改
			return 2;
		}else if(utiUserGradeTaskService.checkPower(user, ConstantCodes.MODIFYCLAIM)){ // 立案后可修改
			return 1;
		}else{
			return 0;
		}
	//mantis： CLM0067 ，處理人員：  BK007 蘇哲 ，需求單編號： CLM0067.理賠系統-備案修改權限修正   結束
	}

	/**
	 * 拼装sql
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	public String generateConditions(HttpServletRequest httpServletRequest) throws Exception {
		String registNoSign = httpServletRequest.getParameter("RegistNoSign");
		String policyNoSign = httpServletRequest.getParameter("PolicyNoSign");
		String riskCodeSign = httpServletRequest.getParameter("RiskCodeSign");
		String reportDateSign = httpServletRequest.getParameter("ReportDateSign");
		String insuredNameSign = httpServletRequest.getParameter("InsuredNameSign");
		String engineNoSign = httpServletRequest.getParameter("EngineNoSign");
		String licenseNoSign = httpServletRequest.getParameter("LicenseNoSign");
		String insuredCodeSign = httpServletRequest.getParameter("InsuredCodeSign");

		String registNo = StringUtils.rightTrim(httpServletRequest.getParameter("RegistNo"));
		String policyNo = StringUtils.rightTrim(httpServletRequest.getParameter("PolicyNo"));
		String riskCode = StringUtils.rightTrim(httpServletRequest.getParameter("RiskCode"));
		String reportDate = StringUtils.rightTrim(httpServletRequest.getParameter("ReportDate"));
		String insuredName = StringConvert.getParam(httpServletRequest, "InsuredName", ConstantCodes.YUI_CHARSET);

		String engineNo = StringConvert.getParam(httpServletRequest, "EngineNo", ConstantCodes.YUI_CHARSET);
		String licenseNo = StringConvert.getParam(httpServletRequest, "LicenseNo", ConstantCodes.YUI_CHARSET);
		String insuredCode = StringConvert.getParam(httpServletRequest, "InsuredCode", ConstantCodes.YUI_CHARSET);

		String conditions = " 1=1 ";
		conditions = conditions + StringConvert.convertString("REGISTNO", registNo, registNoSign);
		conditions = conditions + StringConvert.convertString("POLICYNO", policyNo, policyNoSign);
		conditions = conditions + StringConvert.convertString("RISKCODE", riskCode, riskCodeSign);
		conditions = conditions + StringConvert.convertString("ENGINENO", engineNo, engineNoSign);
		conditions = conditions + StringConvert.convertString("LICENSENO", licenseNo, licenseNoSign);
		conditions = conditions + StringConvert.convertString("INSUREDCODE", insuredCode, insuredCodeSign);
		conditions = conditions + StringConvert.convertString("INSUREDNAME", insuredName, insuredNameSign);
		if (reportDate != null && !reportDate.trim().equals("")) {
			conditions = conditions + StringConvert.convertDate("REPORTDATE", reportDate, reportDateSign);
		}
		//檢索流程未結束，且備案已提交的案件
		conditions = conditions + " and exists ( SELECT 0 FROM swflog WHERE nodetype = 'regis' and nodeno = 1 and nodestatus = '4' and registno = prplregist.registno ) ";
		// 拼权限
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		conditions = conditions + uiPowerInterface.addPower(userDto, "PrpLregist", "", "MakeCom");
		conditions = conditions + powerService.addRiskPower(userDto, "PrpLregist","claim");
		conditions += " ORDER BY REPORTDATE DESC";
		return conditions;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpLcallCenterService getPrpLcallCenterService() {
		return prpLcallCenterService;
	}

	public void setPrpLcallCenterService(PrpLcallCenterService prpLcallCenterService) {
		this.prpLcallCenterService = prpLcallCenterService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public UtiUserGradeTaskService getUtiUserGradeTaskService() {
		return utiUserGradeTaskService;
	}

	public void setUtiUserGradeTaskService(UtiUserGradeTaskService utiUserGradeTaskService) {
		this.utiUserGradeTaskService = utiUserGradeTaskService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public PowerService getPowerService() {
		return powerService;
	}

	public void setPowerService(PowerService powerService) {
		this.powerService = powerService;
	}
}
