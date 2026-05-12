package com.ctbcins.commons.action;

import java.util.Map;

//mantis：CLM0151，處理人員：DP0713，需求單編號：新核心理賠-賠款帳戶維護查詢錯誤問題排除 START
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//mantis：CLM0151，處理人員：DP0713，需求單編號：新核心理賠-賠款帳戶維護查詢錯誤問題排除 END

import org.springframework.beans.factory.annotation.Autowired;

import ins.framework.web.Struts2Action;

import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpDpaymentAccount;
import com.sinosoft.claim.schema.service.facade.PrpDpaymentAccountService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * mantis： CLM0075，處理人員：BK007 蘇哲，需求單編號：CLM0075.理賠系統-修改或刪除已失效匯款帳戶
 * @author bk007
 *
 */
public class PaymentAccountAction extends Struts2Action {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5372914996756799896L;
	
	@Autowired
	private PrpDpaymentAccountService paymentAccountService;
	private PrpDpaymentAccount paymentAccount;
	
	private Map<String, String> certificateTypeList = ConstantsCollection.prpdpaymentaccountCertificateTypeList;
	
	private String accountCode;
	private String accountCodeSign;
	private String accountName;
	private String accountNameSign;
	
	/**
	 * query 查詢過濾功能
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception {
		//mantis：CLM0151，處理人員：DP0713，需求單編號：新核心理賠-賠款帳戶維護查詢錯誤問題排除 START
		HttpServletRequest request = this.getRequest();
		request.setCharacterEncoding("UTF-8");
		accountName = request.getParameter("accountName");
		//mantis：CLM0151，處理人員：DP0713，需求單編號：新核心理賠-賠款帳戶維護查詢錯誤問題排除 END
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
		}
		this.writeJSONData(paymentAccountService.findByConditionsForPage(queryConditions(), pageNo, pageSize), "accountCode", "certificateCode","bankName","customBankName","ownerName","operatorCode","validStatus");
		return NONE;
	}

	private String queryConditions() {
		StringBuilder conditions = new StringBuilder(" 1=1 ");
		conditions.append(StringConvert.convertString("accountCode", accountCode, accountCodeSign));
		conditions.append(StringConvert.convertString("accountName", accountName, accountNameSign));
		return conditions.toString();
	}

	/**
	 * view 讀取功能
	 * @return
	 * @throws Exception
	 */
	public String view()throws Exception {
		paymentAccount = paymentAccountService.findByPrimaryKey(paymentAccount.getAccountCode());
		return SUCCESS;
	}
	
	/**
	 * edit 修改
	 * @return
	 * @throws Exception
	 */
	public String edit()throws Exception {
		paymentAccount = paymentAccountService.findByPrimaryKey(paymentAccount.getAccountCode());
		return SUCCESS;
	}
	
	/**
	 * save 儲存
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		//paymentAccount = paymentAccountService.findByPrimaryKey(paymentAccount.getAccountCode());
		UserDto user = (UserDto) getSession().getAttribute("user");
		paymentAccount.setUpdateDate(new DateTime());
		paymentAccount.setOperatorCode(user.getUserCode());
		paymentAccount.setOperatorName(user.getUserName());
		paymentAccount.setOperatorComCode(user.getComCode());
		paymentAccountService.update(paymentAccount);
		return SUCCESS;
	}

	public PrpDpaymentAccount getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(PrpDpaymentAccount paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	public Map<String, String> getCertificateTypeList() {
		return certificateTypeList;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAccountCodeSign() {
		return accountCodeSign;
	}

	public void setAccountCodeSign(String accountCodeSign) {
		this.accountCodeSign = accountCodeSign;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNameSign() {
		return accountNameSign;
	}

	public void setAccountNameSign(String accountNameSign) {
		this.accountNameSign = accountNameSign;
	}
}
