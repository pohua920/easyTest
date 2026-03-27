package com.sinosoft.claim.regist.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.util.DAARegistViewHelper;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * 分发HTTP GET 报案查询
 * <p>
 * Title: 车险理赔报案查询信息
 * </p>
 * <p>
 * Description: 车险理赔报案报案查询信息系统样本程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
@SuppressWarnings("serial")
public class RegistQueryAction extends Struts2Action {
	/** 报案号 */
	private String RegistNo;
	/** 操作類型*/
	private String editType = "SHOW";
	/** 报案业务处理service */
	private RegistService registService;
	/** 报案数据收集*/
	private DAARegistViewHelper daaRegistViewHelper;
	/** 代码翻译*/
	private CodeService codeService;
    
	/**
	 * 报案查询
	 * @return 页面类型
	 * @throws Exception
	 */
	public String query() throws Exception {
		HttpServletRequest httpServletRequest = super.getRequest();
		String forward = "";
		// 尚未加入type异常处理{}、其它必须参数异常处理{}
		// 1。报案一般的查询，查询理赔节点状态信息,整理输入，用於初始界面显示
		if (editType.equals("ADD") || editType.equals("EDIT") || editType.equals("SHOW")) {
			try {
				// 查询定损信息,整理输入，用於初始界面显示
				Page page = this.setPrpLregistToView(httpServletRequest);
				for (Iterator<?> it = page.getResult().iterator(); it.hasNext();) {
					((PrpLregist) it.next()).setEditType(editType);
				}
				this.writeJSONData(page,"registNo","policyNo","riskCode","relatepolicyNo","licenseNo","status","cancelDate","insuredName", "operatorCode", "operatorName","operateDate","editType");
				return NONE;
			} catch (Exception ex) {
				ex.printStackTrace();
				this.writeJSONMsg(ex.getMessage());
			}
		}
		// 2。报案撤消的查询
		if (editType.equals("DELETE")) {
			daaRegistViewHelper.registCancelDtoToView(httpServletRequest, RegistNo);
			forward = "registCancel";
		}
		return forward;
	}

	/**
	 * 根据报案号和保单号,车牌号，操作时间，案件状态查询报案信息
	 * @param request 返回给页面的request
	 * @param registNo 报案号
	 * @param policyNo 保单号
	 * @throws Exception Reason:增加车牌号，案件状态，操作时间查询条件
	 */
	public Page setPrpLregistToView(HttpServletRequest request) throws Exception {
		//備案號碼
		String strRegistNo = request.getParameter("RegistNo");
		String strRegistNoSign = request.getParameter("RegistNoSign");
		//保單號碼
		String strPolicyNo = request.getParameter("PolicyNo");
		String strPolicyNoSign = request.getParameter("PolicyNoSign");
		//險種
		String strRiskCode = request.getParameter("RiskCode");
		String strRiskCodeSign = request.getParameter("PolicyNoSign");
		//牌照號碼
		String strLicenseNo = StringConvert.getParam(request, "LicenseNo", ConstantCodes.YUI_CHARSET);
		String strLicenseNoSign = request.getParameter("LicenseNoSign");
		//操作時間
		String strOperateDate = request.getParameter("OperateDate");
		String strOperateDateSign = request.getParameter("OperateDateSign");
		//要保人ID
		String strAppliIdentifyNumber = request.getParameter("AppliIdentifyNumber");
		String strAppliIdentifyNumberSign = request.getParameter("AppliIdentifyNumberSign");
		//被保險人名稱
		String strInsuredName = StringConvert.getParam(request, "InsuredName", ConstantCodes.YUI_CHARSET);
		String strInsuredNameSign = request.getParameter("InsuredNameSign");
		//被保險人ID
		String strInsuredIdentifyNumber = request.getParameter("InsuredIdentifyNumber");
		String strInsuredIdentifyNumberSign = request.getParameter("InsuredIdentifyNumberSign");
		//事故日期
		String strDamageStartDate = request.getParameter("damageStartDate");
		String strDamageEndDate = request.getParameter("damageEndDate");
		//是否註銷
		String cancelFlag = request.getParameter("cancelFlag");
		//案件狀態
		String[] status = request.getParameterValues("status");
		StringBuffer conditions = new StringBuffer("");
		conditions.append(StringConvert.convertString("a.registNo",strRegistNo,strRegistNoSign));
		conditions.append(StringConvert.convertString("a.policyNo",strPolicyNo,strPolicyNoSign));
		conditions.append(StringConvert.convertString("a.riskCode",strRiskCode,strRiskCodeSign));
		conditions.append(StringConvert.convertString("a.licenseNo",strLicenseNo,strLicenseNoSign));
		conditions.append(StringConvert.convertString("a.insuredName",strInsuredName,strInsuredNameSign));
		conditions.append(StringConvert.convertDate("a.damageStartDate",strDamageStartDate,">="));
		conditions.append(StringConvert.convertDate("a.damageStartDate",strDamageEndDate,"<="));
		// 判断是否报案注销
		if ("1".equals(cancelFlag)) {
			conditions.append(" AND a.cancelDate is not null" );
		} else {
			conditions.append(" AND a.cancelDate is null " );
		}
		// 被保险人名称、被保险人ID、要保人ID 参与检索
		if (DataUtils.emptyToNull(strAppliIdentifyNumber) != null) {// 检索了要保人
			conditions.append(" and exists (");
			conditions.append(" select 0 from prpcinsured where a.policyno = prpcinsured.policyno ");
			conditions.append(" and prpcinsured.insuredflag = '2' ");
			conditions.append(StringConvert.convertString("prpcinsured.identifynumber", strAppliIdentifyNumber, strAppliIdentifyNumberSign));
			conditions.append(" ) ");
		}
		// 检索了被保险人、或其身份证字号、统一编号
		if (DataUtils.emptyToNull(strInsuredIdentifyNumber) != null) {
			conditions.append(" and exists (");
			conditions.append(" select 0 from prpcinsured where a.policyno = prpcinsured.policyno ");
			conditions.append(" and prpcinsured.insuredflag = '1' ");
			conditions.append(StringConvert.convertString("prpcinsured.identifynumber", strInsuredIdentifyNumber, strInsuredIdentifyNumberSign));
			conditions.append(" ) ");
		}
		conditions.append(StringConvert.convertString("b.operateDate",strOperateDate,strOperateDateSign));
		StringConvert.convertDate("b.operateDate", strOperateDate, strOperateDateSign);
		conditions.append(CommonUtils.getSqlOr(status, "b.status"));
		// 查询报案信息
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
		}
		return registService.findRegistByConditions(conditions.toString(), pageNo, pageSize);
	}
	/**
	 * 根据同险编号查询保单对象
	 */
	public String sameAddress(){
		try {
			daaRegistViewHelper.findSameAddressPolicy(this.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String forward = "sameAddress";
		return forward;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public DAARegistViewHelper getDaaRegistViewHelper() {
		return daaRegistViewHelper;
	}

	public void setDaaRegistViewHelper(DAARegistViewHelper daaRegistViewHelper) {
		this.daaRegistViewHelper = daaRegistViewHelper;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public String getRegistNo() {
		return RegistNo;
	}

	public void setRegistNo(String registNo) {
		RegistNo = registNo;
	}
}