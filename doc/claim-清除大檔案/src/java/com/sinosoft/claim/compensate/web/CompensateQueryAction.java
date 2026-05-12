package com.sinosoft.claim.compensate.web;

import ins.framework.common.Page;
import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

//mantis：CLM0155，處理人員：DP0713，車體險自負額有責任時卡控自負額發票號碼必輸 START
import net.sf.json.JSONObject;
import java.util.Map;
import java.util.HashMap;
//mantis：CLM0155，處理人員：DP0713，車體險自負額有責任時卡控自負額發票號碼必輸 END
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.compensate.util.DAACompensateViewHelper;
import com.sinosoft.claim.schema.model.PrpLcompensate;
//mantis：CLM0155，處理人員：DP0713，車體險自負額有責任時卡控自負額發票號碼必輸
import com.sinosoft.claim.schema.service.facade.PrpLCitemKindService;
import com.sinosoft.claim.workflow.vo.WorkFlowQueryDto;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * 理算查询
 * @Description 
 * @author 中科软
 * @date Mar 1, 2013 11:14:37 AM
 */
public class CompensateQueryAction extends Struts2Action {
	/**
	 * @Fields serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;
	/** 赔款计算书号查询匹配 */
	private String CompensateNoSign;
	/** 赔案号 */
	private String CompensateNo;
	/** 赔案号查询匹配 */
	private String ClaimNoSign;
	/** 赔案号 */
	private String ClaimNo;
	/** 保单号查询匹配 */
	private String PolicyNoSign;
	/** 保单号 */
	private String PolicyNo;
	/** 操作时间查询匹配 */
	private String OperateDateSign;
	/** 操作时间 */
	private String OperateDate;
	/** 车牌号码查询匹配 */
	private String LicenseNoSign;
	/** 车牌号码 */
	private String LicenseNo;
	/** 被保险人名称查询匹配 */
	private String InsuredNameSign;
	/** 被保险人名称 */
	private String InsuredName;
	/** 案件状态 */
	private String status;
	/** 核赔标志 */
	private String UnderWriteFlag;
	/** 报案号查询匹配 */
	private String RegistNoSign;
	/** 报案号 */
	private String RegistNo;
	/**理算数据收集*/
	private DAACompensateViewHelper daaCompensateViewHelper;
	//mantis：CLM0155，處理人員：DP0713，車體險自負額有責任時卡控自負額發票號碼必輸
	private PrpLCitemKindService prpLCitemKindService;
	/**
	 * 理算查询
	 * @throws Exception
	 */
	public String compensateQuery() throws Exception {
		HttpServletRequest request = super.getRequest();
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		String editType = request.getParameter("editType");// 类型
		WorkFlowQueryDto workFlowQueryDto = new WorkFlowQueryDto();
		workFlowQueryDto.setUnderWriteFlag(UnderWriteFlag);
		workFlowQueryDto.setCompensateNo(CompensateNo);
		workFlowQueryDto.setCompensateNoSign(CompensateNoSign);
		workFlowQueryDto.setClaimNo(ClaimNo);
		workFlowQueryDto.setClaimNoSign(ClaimNoSign);
		workFlowQueryDto.setPolicyNo(PolicyNo);
		workFlowQueryDto.setRegistNo(RegistNo);
		workFlowQueryDto.setLicenseNo(StringConvert.getParam(super.getRequest(), "LicenseNo", ConstantCodes.YUI_CHARSET));
		workFlowQueryDto.setStatus(status);
		workFlowQueryDto.setOperateDate(OperateDate);
		workFlowQueryDto.setInsuredName(StringConvert.getParam(super.getRequest(), "InsuredName", ConstantCodes.YUI_CHARSET));
		workFlowQueryDto.setRegistNoSign(RegistNoSign);
		workFlowQueryDto.setPolicyNoSign(PolicyNoSign);
		workFlowQueryDto.setLicenseNoSign(LicenseNoSign);
		workFlowQueryDto.setOperateDateSign(OperateDateSign);
		workFlowQueryDto.setInsuredNameSign(InsuredNameSign);

		String forward = ""; // 向前
		// 尚未加入type异常处理{}、其它必须参数异常处理{}
		if ("ADD".equals(editType) || "EDIT".equals(editType) || "SHOW".equals(editType)) {
			// 查询实赔r信息,整理输入，用於初始界面显示
			String recordPerPage = AppConfig.get("sysconst.ROWS_PERPAGE");
			String pageNo = request.getParameter("pageNo");
			if (DataUtils.emptyToNull(pageNo) == null) {
				pageNo = "1";
			}
			try {
				Page page = this.daaCompensateViewHelper.setPrpLcompensateDtoToView(request, workFlowQueryDto, pageNo, recordPerPage);
				for (Iterator<PrpLcompensate> it = page.getResult().iterator(); it.hasNext();) {
					((PrpLcompensate) it.next()).setEditType(editType);
				}
				this.writeJSONData(page, "status", "compensateNo", "claimNo", "policyNo", "sumPaid", "underWriteFlag", "riskCode", "editType");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return NONE;
		}
		if (editType.equals("PRINT")) {
			String printType = request.getParameter("PrintType");// 赔案号
			request.setAttribute("printType",printType);
			this.daaCompensateViewHelper.setPrpLcompensateDtoToPrint(request,CompensateNo,ClaimNo,RegistNo);
			forward = "PRINT";
		}
		return forward;
	}
	
	/**
	 * mantis：CLM0155，處理人員：DP0713，車體險自負額有責任時卡控自負額發票號碼必輸
	 * AJAX進入點
	 * @return
	 * @throws Exception
	 */
	public String checkPrpLlossDeductibletype() throws Exception {
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("prpLcompensateClaimNo");
		String kindCode =  request.getParameter("prpLlossKindCode");
		String deductibletype = null;
		deductibletype = "";//checkPayuserCode();

		String conditions = " claimNo='" + claimNo + "' and KIND.KINDCODE = '"+ kindCode + "' ";
//		conditions = " CLAIMNO = AND KIND.KINDCODE= "
		deductibletype = this.getPrpLCitemKindService().findDeductibleTypeByConditions(conditions);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("deductibletype", deductibletype);
		super.getResponse().setContentType("text/html; charset=UTF-8");
		super.getResponse().getWriter().write(JSONObject.fromObject(jsonMap).toString());
		return NONE;
	}

	/**
	 * mantis：CLM0216，處理人員：DP0714，新核心-新增車險醫詢費用提示檢核
	 * 檢查 理算書號、牌照號碼、出險日期、出險小時 是否存在
	 */
	public String checkLicenceNoAndDamageStartDate() throws Exception {
		HttpServletRequest request = super.getRequest();
		String licenseNo = request.getParameter("licenseNo"); //牌照號碼
		String damageDate = request.getParameter("damageDate"); //出險日期
		String damageHour = request.getParameter("damageHour"); //出險小時
		String compensateNo = request.getParameter("compensateNo"); //計算書號碼

		String sum = this.getPrpLCitemKindService().checkLicenceNoAndDamageStartDate(licenseNo, damageDate, damageHour, compensateNo);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("sumChargeAmount", sum);
		super.getResponse().setContentType("text/html; charset=UTF-8");
		super.getResponse().getWriter().write(JSONObject.fromObject(jsonMap).toString());
		return NONE;
	}

	public String getCompensateNoSign() {
		return CompensateNoSign;
	}

	public void setCompensateNoSign(String compensateNoSign) {
		CompensateNoSign = compensateNoSign;
	}

	public String getCompensateNo() {
		return CompensateNo;
	}

	public void setCompensateNo(String compensateNo) {
		CompensateNo = compensateNo;
	}

	public String getClaimNoSign() {
		return ClaimNoSign;
	}

	public void setClaimNoSign(String claimNoSign) {
		ClaimNoSign = claimNoSign;
	}

	public String getClaimNo() {
		return ClaimNo;
	}

	public void setClaimNo(String claimNo) {
		ClaimNo = claimNo;
	}

	public String getPolicyNoSign() {
		return PolicyNoSign;
	}

	public void setPolicyNoSign(String policyNoSign) {
		PolicyNoSign = policyNoSign;
	}

	public String getPolicyNo() {
		return PolicyNo;
	}

	public void setPolicyNo(String policyNo) {
		PolicyNo = policyNo;
	}

	public String getOperateDateSign() {
		return OperateDateSign;
	}

	public void setOperateDateSign(String operateDateSign) {
		OperateDateSign = operateDateSign;
	}

	public String getOperateDate() {
		return OperateDate;
	}

	public void setOperateDate(String operateDate) {
		OperateDate = operateDate;
	}

	public String getLicenseNoSign() {
		return LicenseNoSign;
	}

	public void setLicenseNoSign(String licenseNoSign) {
		LicenseNoSign = licenseNoSign;
	}

	public String getLicenseNo() {
		return LicenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		LicenseNo = licenseNo;
	}

	public String getInsuredNameSign() {
		return InsuredNameSign;
	}

	public void setInsuredNameSign(String insuredNameSign) {
		InsuredNameSign = insuredNameSign;
	}

	public String getInsuredName() {
		return InsuredName;
	}

	public void setInsuredName(String insuredName) {
		InsuredName = insuredName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUnderWriteFlag() {
		return UnderWriteFlag;
	}

	public void setUnderWriteFlag(String underWriteFlag) {
		UnderWriteFlag = underWriteFlag;
	}

	public String getRegistNoSign() {
		return RegistNoSign;
	}

	public void setRegistNoSign(String registNoSign) {
		RegistNoSign = registNoSign;
	}

	public String getRegistNo() {
		return RegistNo;
	}

	public void setRegistNo(String registNo) {
		RegistNo = registNo;
	}

	public DAACompensateViewHelper getDaaCompensateViewHelper() {
		return daaCompensateViewHelper;
	}

	public void setDaaCompensateViewHelper(DAACompensateViewHelper daaCompensateViewHelper) {
		this.daaCompensateViewHelper = daaCompensateViewHelper;
	}
	//mantis：CLM0155，處理人員：DP0713，車體險自負額有責任時卡控自負額發票號碼必輸 START
	public PrpLCitemKindService getPrpLCitemKindService() {
		return prpLCitemKindService;
	}

	public void setPrpLCitemKindService(PrpLCitemKindService prpLCitemKindService) {
		this.prpLCitemKindService = prpLCitemKindService;
	}
	//mantis：CLM0155，處理人員：DP0713，車體險自負額有責任時卡控自負額發票號碼必輸 END
}
