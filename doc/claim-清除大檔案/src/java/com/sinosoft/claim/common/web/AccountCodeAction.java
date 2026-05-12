package com.sinosoft.claim.common.web;

import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.util.AccountCodeViewHelper;

/**
 * 银行帳户管理
 * @Description 
 * @author 中科软
 */
public class AccountCodeAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	/** 帐户信息数据收集 */
	private AccountCodeViewHelper accountCodeViewHelper;

	/**
	 * 银行帳户管理
	 * @return
	 * @throws Exception
	 */
	public String accountCode() throws Exception {
		HttpServletRequest request = super.getRequest();
		String actionType = request.getParameter("actionType");
//		String riskCode = request.getParameter("riskCode");
		Map<String,String> CertificateTypeList = ConstantsCollection.prpdpaymentaccountCertificateTypeList;
		//支付对象 帳號歸屬人證件類型
//		if(DataUtils.emptyToNull(riskCode)!=null && ("E".equals(ConstantCodes.carClassMap.get(riskCode)) || "Q".equals(ConstantCodes.carClassMap.get(riskCode)))){//意健险的证件类型没有"统一编号"
//			CertificateTypeList.remove("02");
//		}
		request.setAttribute("prpdpaymentaccountCertificateTypeList",CertificateTypeList );
		try {
			if ("AccountAdd".equals(DataUtils.dbNullToEmpty(actionType))) {
				accountCodeViewHelper.AccountAdd(request);
			} else if ("queryUserCom".equals(DataUtils.dbNullToEmpty(actionType)) 
					|| "queryUser".equals(DataUtils.dbNullToEmpty(actionType))) {
				accountCodeViewHelper.queryUserCom(request);
			} else if ("AccountAddCompensate".equals(DataUtils.dbNullToEmpty(actionType))) {
				accountCodeViewHelper.AccountAddCompensate(request);
			} else if ("SearchWithOwnerName".equals(DataUtils.dbNullToEmpty(actionType))) {
				accountCodeViewHelper.SearchWithOwnerName(request);
				// 按费用支付对象姓名查询银行帳号信息
			} else if ("SearchWithPayObjectName".equals(DataUtils.dbNullToEmpty(actionType))) {
				accountCodeViewHelper.SearchWithPayObjectName(request);
			}else if("oldQueryUserCom".equals(DataUtils.dbNullToEmpty(actionType))) {
				accountCodeViewHelper.oldQueryUserCom(request);
			}
			//mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 START
			else if("BankEdit".equals(DataUtils.dbNullToEmpty(actionType))) {
				accountCodeViewHelper.BankEdit(request);
			}
			//mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 END
			else {
				return NONE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return actionType;
	}

	public AccountCodeViewHelper getAccountCodeViewHelper() {
		return accountCodeViewHelper;
	}

	public void setAccountCodeViewHelper(AccountCodeViewHelper accountCodeViewHelper) {
		this.accountCodeViewHelper = accountCodeViewHelper;
	}

}
