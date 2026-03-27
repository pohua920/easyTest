/*
 * @(#)AccountCodeViewHelper.java	Mar 13, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.common.util;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CommonService;
import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpDpaymentAccount;
import com.sinosoft.claim.schema.model.PrpLaccount;
import com.sinosoft.claim.schema.service.facade.PrpDpaymentAccountService;
import com.sinosoft.claim.schema.service.facade.PrpLaccountService;
import com.sinosoft.sysframework.common.datatype.DateTime;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class AccountCodeViewHelper {
	/** 银行帐号与赔案信息服务 */
	private PrpLaccountService prpLaccountService;
	/** 机构信息服务 */
	private PrpDcompanyService prpDcompanyService;
	/** 支付帳户信息服务 */
	private PrpDpaymentAccountService prpDpaymentAccountService;

	private CommonService commonService;

	/**
	 * 查询用户
	 * @param request
	 * @throws Exception
	 */
	public void queryUser(HttpServletRequest request) throws Exception {
		String registNo = request.getParameter("registNo");
		String serialNo = request.getParameter("serialNo");
		String riskCode = request.getParameter("riskCode");
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo);
		Page page = prpLaccountService.findPrpLaccount(queryRule, 1, 15);// 如果查过15条，可能会产生性能问题，直接让其去新增
		List<?> list = page.getResult();
		List<PrpDpaymentAccount> paymentAccounList = new ArrayList<PrpDpaymentAccount>();
		PrpDpaymentAccount prpDpaymentAccount = null;
		PrpLaccount prpLaccount = null;
		Iterator<?> it = list.iterator();
		while (it.hasNext()) {
			prpLaccount = (PrpLaccount) it.next();
			prpDpaymentAccount = this.prpDpaymentAccountService.findByPrimaryKey(prpLaccount.getAccountCode());
			// add by zmy begin
			// 利用反射实现将判断对象prpDpaymentAccount中类型为String的参数（serialVersionUID除外），如果为null或者"null",则赋值为"".
			Field[] fs = PrpDpaymentAccount.class.getDeclaredFields();
			for (int j = 1; j < fs.length; j++) {
				String methodname = "get" + fs[j].getName().substring(0, 1).toUpperCase() + fs[j].getName().substring(1);
				Method m = PrpDpaymentAccount.class.getMethod(methodname);
				Class<?> clazz = fs[j].getType();
				if (clazz.equals(String.class)) {
					if (null == (m.invoke(prpDpaymentAccount)) || ("null").equals(m.invoke(prpDpaymentAccount))) {
						String methodname2 = "set" + fs[j].getName().substring(0, 1).toUpperCase() + fs[j].getName().substring(1);
						Method n = PrpDpaymentAccount.class.getMethod(methodname2, fs[j].getType());
						n.invoke(prpDpaymentAccount, "");
					}
				};
			}// end
			if (prpDpaymentAccount != null) {
				paymentAccounList.add(prpDpaymentAccount);
			}
		}
		request.setAttribute("serialNo", serialNo);
		request.setAttribute("registNo", registNo);
		// request.setAttribute("bankList", ConstantsCollection.bankList);
		request.setAttribute("PaymentAccounList", paymentAccounList);
		request.setAttribute("Propflag", ConstantCodes.carClassMap.get(riskCode));// 火险标志
	}

	/**
	 * 查询账号信息
	 * @param request
	 * @throws Exception
	 */
	public void oldQueryUserCom(HttpServletRequest request) throws Exception {
		String registNo = request.getParameter("registNo");
		String ownerName = request.getParameter("ownerName");
		List<PrpDpaymentAccount> PaymentAccounList = new ArrayList<PrpDpaymentAccount>();
		if (ownerName == null || "".equals(ownerName)) {
			// String conditions = "registNo = '"+registNo+"' and rownum<16";//
			// 如果查过15条，可能会产生性能问题，直接让其去新增
			// BLPrplaccountFacade blPrplaccountFacade = new
			// BLPrplaccountFacade();
			// Collection col =
			// blPrplaccountFacade.findByConditions(conditions);
			// Iterator itaccount = col.iterator();
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.registNo", registNo);
			Page page = prpLaccountService.findPrpLaccount(queryRule, 1, 15);// 如果查过15条，可能会产生性能问题，直接让其去新增
			List<?> list = page.getResult();
			PrpLaccount prpLaccount = null;
			PrpDpaymentAccount prpDpaymentAccount = null;
			Iterator<?> it = list.iterator();
			while (it.hasNext()) {
				prpLaccount = (PrpLaccount) it.next();
				prpDpaymentAccount = this.prpDpaymentAccountService.findByPrimaryKey(prpLaccount.getAccountCode());
				if (prpDpaymentAccount != null) {
					PaymentAccounList.add(prpDpaymentAccount);
				}
			}
		} else {
			//mantis：CLM0075 ，處理人員：BK007  蘇哲，需求單編號：CLM0075.理賠系統-修改或刪除已失效匯款帳戶
			String conditions = "validStatus = '1' abd ownerName='" + ownerName + "' and rownum<16";
			PaymentAccounList = (List<PrpDpaymentAccount>) this.prpDpaymentAccountService.findByConditions(conditions);
		}

		request.setAttribute("registNo", registNo);
		request.setAttribute("ownerName", ownerName);
		request.setAttribute("PaymentAccounList", PaymentAccounList);

	}

	/**
	 * 查询账号信息
	 * @param request
	 * @throws Exception
	 */
	public void queryUserCom(HttpServletRequest request) throws Exception {
		String riskCode = request.getParameter("riskCode");
		String registNo = request.getParameter("registNo");
		String certificateCode = request.getParameter("certificateCode");
		String uniformNo = request.getParameter("uniformNo");
		String accountCode = request.getParameter("accountCode");
		// 優先檢索本案已收錄的賬戶資料
		//mantis：CLM0075 ，處理人員：BK007  蘇哲，需求單編號：CLM0075.理賠系統-修改或刪除已失效匯款帳戶
		String statements = " select d.* from PrpLaccount t , PrpDpaymentAccount d where d.validStatus = '1' and t.accountCode = d.accountCode and t.registNo = '" + registNo + "' order by t.serialNo asc ";
		List<PrpDpaymentAccount> paymentAccounList = this.commonService.findByStatements(statements, PrpDpaymentAccount.class);
		if (CommonUtils.isEmpty(accountCode)) {// 如果賬號存在，則本案肯定已收錄，
			boolean exists = false;
			if (!CommonUtils.isEmpty(uniformNo)) {
				for (PrpDpaymentAccount d : paymentAccounList) {
					if (d.getCertificateCode().equals(uniformNo) && d.getCertificateType().equals(certificateCode)) {
						exists = true;
						break;
					}
				}
				if (!exists) {
					//mantis：CLM0075 ，處理人員：BK007  蘇哲，需求單編號：CLM0075.理賠系統-修改或刪除已失效匯款帳戶
					String conditions = " validStatus = '1' and certificateCode = '" + uniformNo + "' and certificateType = '" + certificateCode + "' order by updateDate desc , operateDate desc ";
					paymentAccounList = this.prpDpaymentAccountService.findByConditions(conditions);
				}
			}
		}
		request.setAttribute("Propflag", ConstantCodes.carClassMap.get(riskCode));// 火险标志
		request.setAttribute("registNo", registNo);
		// request.setAttribute("bankList", ConstantsCollection.bankList);
		// request.setAttribute("ownerName", certificateCode);
		request.setAttribute("PaymentAccounList", paymentAccounList);

	}

	/**
	 * 添加银行账号
	 * @param request
	 * @throws Exception
	 */
	public void AccountAdd(HttpServletRequest request) throws Exception {
		String registNo = request.getParameter("registNo");
		String serialNo = request.getParameter("serialNo");
		String riskCode = request.getParameter("riskCode");
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) (session.getAttribute("user"));
//		String accountCode = request.getParameter("accountCode");
		PrpDpaymentAccount prpDpaymentAccount = null;
//		if (DataUtils.emptyToNull(accountCode) != null) {
//			prpDpaymentAccount = this.prpDpaymentAccountService.findByPrimaryKey(accountCode);
//		}
		if (prpDpaymentAccount == null) {
			prpDpaymentAccount = new PrpDpaymentAccount();
			prpDpaymentAccount.setAccountCurrency(ConstantCodes.LOCAL_CURRENCY);
			prpDpaymentAccount.setOwnerName(request.getParameter("insuredName"));
			prpDpaymentAccount.setOperatorCode(userDto.getUserCode());
			prpDpaymentAccount.setOperatorComCode(prpDcompanyService.getComName(userDto.getComCode(), true));
			prpDpaymentAccount.setOperatorName(userDto.getUserName());

			prpDpaymentAccount.setOperateDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
			prpDpaymentAccount.setUpdateDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
			prpDpaymentAccount.setOperateSys("LOL");
			prpDpaymentAccount.setUsedOrNot("1");// 是
			prpDpaymentAccount.setValidStatus("1");// 有效
		}
		prpDpaymentAccount.setRegistNo(registNo);
		request.setAttribute("serialNo", serialNo);
		request.setAttribute("prpdpaymentaccountDto", prpDpaymentAccount);
		request.setAttribute("Propflag", ConstantCodes.carClassMap.get(riskCode));// 火险标志
		// request.setAttribute("bankList", ConstantsCollection.bankList);

	}
	
	/**
	 * mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位
	 * 添加银行账号
	 * @param request
	 * @throws Exception
	 */
	public void BankEdit(HttpServletRequest request) throws Exception {
//		String serialNo = request.getParameter("serialNo");
//		request.setAttribute("serialNo", serialNo);
//		 request.setAttribute("bankList", ConstantsCollection.bankList);
	}
	/**
	 * 查询支付账户信息
	 * @param accountNo 账号
	 * @param serialNo 序号
	 * @param registNo 报案号
	 * @return
	 * @throws Exception
	 */
	public PrpDpaymentAccount checkAccountNo(String accountNo, String serialNo, String registNo) throws Exception {
		PrpDpaymentAccount prpDpaymentAccount = this.prpDpaymentAccountService.findByPrimaryKey(accountNo);
		if (prpDpaymentAccount != null) {
			prpDpaymentAccount.setSerialNo(serialNo);
			prpDpaymentAccount.setRegistNo(registNo);
		}
		return prpDpaymentAccount;
	}

	/**
	 * 保存支付账户信息
	 * @param hashMap 支付账号信息
	 * @return
	 * @throws Exception
	 */
	public PrpDpaymentAccount saveAccount(HashMap<?, ?> hashMap) throws Exception {
		PrpDpaymentAccount prpDpaymentAccount = this.prpDpaymentAccountService.saveAccount(hashMap);
		return prpDpaymentAccount;
	}

	/**
	 * 查询支付账户信息
	 * @param request
	 * @throws Exception
	 */
	public void AccountAddCompensate(HttpServletRequest request) throws Exception {
		String riskCode = request.getParameter("riskCode");
		String registNo = request.getParameter("registNo");
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) (session.getAttribute("user"));
		// String accountCode = request.getParameter("accountCode");
		PrpDpaymentAccount prpDpaymentAccount = null;
		// if (DataUtils.emptyToNull(accountCode) != null) {
		// prpDpaymentAccount =
		// this.prpDpaymentAccountService.findByPrimaryKey(accountCode);
		// }

		if (prpDpaymentAccount == null) {
			prpDpaymentAccount = new PrpDpaymentAccount();
			prpDpaymentAccount.setAccountCurrency(ConstantCodes.LOCAL_CURRENCY);
			prpDpaymentAccount.setOwnerName(request.getParameter("ownerName"));
			prpDpaymentAccount.setOperatorCode(userDto.getUserCode());
			prpDpaymentAccount.setOperatorComCode(prpDcompanyService.getComName(userDto.getComCode(), true));
			prpDpaymentAccount.setOperatorName(userDto.getUserName());

			prpDpaymentAccount.setOperateDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
			prpDpaymentAccount.setUpdateDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
			prpDpaymentAccount.setOperateSys("LOL");
			prpDpaymentAccount.setUsedOrNot("1");// 是
			prpDpaymentAccount.setValidStatus("1");// 有效
		}
		prpDpaymentAccount.setRegistNo(registNo);
		// request.setAttribute("bankList", ConstantsCollection.bankList);
		request.setAttribute("prpdpaymentaccountDto", prpDpaymentAccount);
		request.setAttribute("Propflag", ConstantCodes.carClassMap.get(riskCode));// 火险标志
	}

	/**
	 * 根据名称查询账户信息
	 * @param request
	 * @throws Exception
	 */
	public void SearchWithOwnerName(HttpServletRequest request) throws Exception {
		String certificateType = request.getParameter("certificateType");
		String certificateCode = request.getParameter("certificateCode");
		if (!CommonUtils.isEmpty(certificateType) && !CommonUtils.isEmpty(certificateCode)) {
			//mantis：CLM0075 ，處理人員：BK007  蘇哲，需求單編號：CLM0075.理賠系統-修改或刪除已失效匯款帳戶
			String conditions = " validStatus = '1' and certificateType = '" + certificateType + "' and certificateCode = '" + certificateCode + "'";
			List<PrpDpaymentAccount> iterator = (List<PrpDpaymentAccount>) this.prpDpaymentAccountService.findByConditions(conditions);
			List<PrpDpaymentAccount> prpDpaymentAccountList = new ArrayList<PrpDpaymentAccount>();
			PrpDpaymentAccount prpDpaymentAccount = null;
			for (int i = 0; i < iterator.size(); i++) {
				prpDpaymentAccount = iterator.get(i);
				prpDpaymentAccountList.add(prpDpaymentAccount);
			}
			request.setAttribute("PrpdPaymentAccountDtoList", prpDpaymentAccountList);
		}
	}

	/**
	 * 按费用支付对象姓名查询银行帳号信息
	 * @param request
	 * @throws Exception
	 */
	public void SearchWithPayObjectName(HttpServletRequest request) throws Exception {
		String ownerName = request.getParameter("ownerName");
		String serialNo = request.getParameter("serialNo");
		if (ownerName != null && !"".equals(ownerName)) {
			//mantis：CLM0075 ，處理人員：BK007  蘇哲，需求單編號：CLM0075.理賠系統-修改或刪除已失效匯款帳戶
			String conditions = " validStatus = '1' and ownername like '" + ownerName + "%'";

			List<PrpDpaymentAccount> iterator = (List<PrpDpaymentAccount>) this.prpDpaymentAccountService.findByConditions(conditions);
			List<PrpDpaymentAccount> prpDpaymentAccountList = new ArrayList<PrpDpaymentAccount>();
			PrpDpaymentAccount prpDpaymentAccount = null;
			for (int i = 0; i < iterator.size(); i++) {
				prpDpaymentAccount = iterator.get(i);
				prpDpaymentAccountList.add(prpDpaymentAccount);
			}
			request.setAttribute("PrpdPaymentAccountDtoList", prpDpaymentAccountList);
			request.setAttribute("serialNo", serialNo);
		}
	}

	public PrpLaccountService getPrpLaccountService() {
		return prpLaccountService;
	}

	public void setPrpLaccountService(PrpLaccountService prpLaccountService) {
		this.prpLaccountService = prpLaccountService;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	public PrpDpaymentAccountService getPrpDpaymentAccountService() {
		return prpDpaymentAccountService;
	}

	public void setPrpDpaymentAccountService(PrpDpaymentAccountService prpDpaymentAccountService) {
		this.prpDpaymentAccountService = prpDpaymentAccountService;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

}
