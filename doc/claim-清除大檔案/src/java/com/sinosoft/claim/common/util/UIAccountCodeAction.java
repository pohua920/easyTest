package com.sinosoft.claim.common.util;

import ins.framework.common.ServiceFactory;
import ins.framework.utils.DataUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpDpaymentAccount;
import com.sinosoft.claim.schema.model.PrpLaccount;
import com.sinosoft.claim.schema.service.facade.PrpDpaymentAccountService;
import com.sinosoft.claim.schema.service.facade.PrpLaccountService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.ParamUtils;

public class UIAccountCodeAction {
	PrpLaccountService prpLaccountService;
	PrpDpaymentAccountService prpDpaymentAccountService;

	public void queryUser(HttpServletRequest request) throws Exception {
		String registNo = request.getParameter("registNo");
		String serialNo = request.getParameter("serialNo");
		String conditions = "registNo = '" + registNo + "' and rownum<16";// 如果查过15条，可能会产生性能问题，直接让其去新增
		Collection<PrpLaccount> col = this.getPrpLaccountService().findByConditions(conditions);
		List<PrpDpaymentAccount> PaymentAccounList = new ArrayList<PrpDpaymentAccount>();
		if (col != null && !col.isEmpty()) {
			PrpLaccount prplaccountDto = null;
			Iterator<PrpLaccount> itaccount = col.iterator();
			while (itaccount.hasNext()) {
				prplaccountDto = (PrpLaccount) itaccount.next();
				PrpDpaymentAccount prpdPaymentAccountDto = this.getPrpDpaymentAccountService().findByPrimaryKey(prplaccountDto.getAccountCode());
				if (prpdPaymentAccountDto != null) {
					PaymentAccounList.add(prpdPaymentAccountDto);
				}
			}
		}
		request.setAttribute("serialNo", serialNo);
		request.setAttribute("registNo", registNo);
		request.setAttribute("PaymentAccounList", PaymentAccounList);

	}

	public void queryUserCom(HttpServletRequest request) throws Exception {
		String registNo = request.getParameter("registNo");
		String ownerName = request.getParameter("ownerName");
		String serialNo = request.getParameter("serialNo");
		ArrayList<PrpDpaymentAccount> paymentAccounList = new ArrayList<PrpDpaymentAccount>();
		if (ownerName == null || "".equals(ownerName)) {
			String conditions = "registNo = '" + registNo + "' and rownum<16";// 如果查过15条，可能会产生性能问题，直接让其去新增
			Collection<PrpLaccount> col = this.getPrpLaccountService().findByConditions(conditions);
			Iterator<PrpLaccount> itaccount = col.iterator();
			PrpLaccount prplaccountDto = null;
			while (itaccount.hasNext()) {
				prplaccountDto = (PrpLaccount) itaccount.next();
				PrpDpaymentAccount prpdPaymentAccountDto = this.getPrpDpaymentAccountService().findByPrimaryKey(prplaccountDto.getAccountCode());
				if (prpdPaymentAccountDto != null) {
					paymentAccounList.add(prpdPaymentAccountDto);
				}
			}
		} else {
			String conditions = "ownerName='" + ownerName + "' and rownum<16";
			paymentAccounList = (ArrayList<PrpDpaymentAccount>) this.getPrpDpaymentAccountService().findByConditions(conditions);
		}

		request.setAttribute("registNo", registNo);
		request.setAttribute("serialNo", serialNo);
		request.setAttribute("ownerName", ownerName);
		request.setAttribute("PaymentAccounList", paymentAccounList);

	}

	public void AccountAdd(HttpServletRequest request) throws Exception {
		String registNo = request.getParameter("registNo");
		String serialNo = request.getParameter("serialNo");
		ParamUtils paramUtils = new ParamUtils(request);
		HttpSession session = request.getSession();
		UserDto user = (UserDto) (session.getAttribute("user"));
		String accountCode = paramUtils.getParameter("accountCode");
		PrpDpaymentAccount prpdPaymentAccountDto = this.getPrpDpaymentAccountService().findByPrimaryKey(accountCode);

		if (prpdPaymentAccountDto == null) {
			prpdPaymentAccountDto = new PrpDpaymentAccount();
			prpdPaymentAccountDto.setAccountCurrency(ConstantCodes.LOCAL_CURRENCY);
			prpdPaymentAccountDto.setOwnerName(paramUtils.getParameter("insuredName"));
			prpdPaymentAccountDto.setOperatorCode(user.getUserCode());
			prpdPaymentAccountDto.setOperatorComCode(user.getComName());
			prpdPaymentAccountDto.setOperatorName(user.getUserName());

			prpdPaymentAccountDto.setOperateDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
			prpdPaymentAccountDto.setUpdateDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
			prpdPaymentAccountDto.setOperateSys("LOL");
			prpdPaymentAccountDto.setUsedOrNot("1");// 是
			prpdPaymentAccountDto.setValidStatus("1");// 有效
			System.out.println("------自动取得被保险人名称 不能修改-------");
		}
		prpdPaymentAccountDto.setRegistNo(registNo);
		request.setAttribute("serialNo", serialNo);
		request.setAttribute("prpdpaymentaccountDto", prpdPaymentAccountDto);

	}

	public PrpDpaymentAccount checkAccountNo(String accountNo, String serialNo, String registNo) throws Exception {
		PrpDpaymentAccount prpdPaymentAccountDto = null;
		try {
			prpdPaymentAccountDto = this.getPrpDpaymentAccountService().findByPrimaryKey(accountNo);
			if (prpdPaymentAccountDto != null) {
				prpdPaymentAccountDto.setSerialNo(serialNo);
				prpdPaymentAccountDto.setRegistNo(registNo);
				prpdPaymentAccountDto.setCustomBankName(DataUtils.dbNullToEmpty(prpdPaymentAccountDto.getCustomBankName()));
				prpdPaymentAccountDto.setCustomBankCode(DataUtils.dbNullToEmpty(prpdPaymentAccountDto.getCustomBankCode()));
				prpdPaymentAccountDto.setAccountName(DataUtils.dbNullToEmpty(prpdPaymentAccountDto.getAccountName()));
				prpdPaymentAccountDto.setCustomerCode(DataUtils.dbNullToEmpty(prpdPaymentAccountDto.getCustomerCode()));
				prpdPaymentAccountDto.setCompensateOwnerName(DataUtils.dbNullToEmpty(prpdPaymentAccountDto.getCompensateOwnerName()));
				prpdPaymentAccountDto.setUniformNo(DataUtils.dbNullToEmpty(prpdPaymentAccountDto.getUniformNo()));
				prpdPaymentAccountDto.setAreaCode(DataUtils.dbNullToEmpty(prpdPaymentAccountDto.getAreaCode()));
				prpdPaymentAccountDto.setCourierAddress(DataUtils.dbNullToEmpty(prpdPaymentAccountDto.getCourierAddress()));
				prpdPaymentAccountDto.setRemark(DataUtils.dbNullToEmpty(prpdPaymentAccountDto.getRemark()));
			} else {

			}
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return prpdPaymentAccountDto;
	}

	public PrpDpaymentAccount saveAccount(HashMap<?,?> hashMap) throws Exception {
		PrpDpaymentAccount prpdPaymentAccountDto = null;
		try {
			prpdPaymentAccountDto = this.getPrpDpaymentAccountService().saveAccount(hashMap);
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return prpdPaymentAccountDto;
	}

	public void AccountAddCompensate(HttpServletRequest request) throws Exception {
		String registNo = request.getParameter("registNo");
		ParamUtils paramUtils = new ParamUtils(request);
		HttpSession session = request.getSession();
		UserDto user = (UserDto) (session.getAttribute("user"));
		String accountCode = paramUtils.getParameter("accountCode");

		PrpDpaymentAccount prpdPaymentAccountDto = this.getPrpDpaymentAccountService().findByPrimaryKey(accountCode);

		if (prpdPaymentAccountDto == null) {
			prpdPaymentAccountDto = new PrpDpaymentAccount();
			prpdPaymentAccountDto.setAccountCurrency(ConstantCodes.LOCAL_CURRENCY);
			prpdPaymentAccountDto.setOwnerName(paramUtils.getParameter("ownerName"));
			prpdPaymentAccountDto.setOperatorCode(user.getUserCode());
			prpdPaymentAccountDto.setOperatorComCode(user.getComName());
			prpdPaymentAccountDto.setOperatorName(user.getUserName());

			prpdPaymentAccountDto.setOperateDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
			prpdPaymentAccountDto.setUpdateDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
			prpdPaymentAccountDto.setOperateSys("LOL");
			prpdPaymentAccountDto.setUsedOrNot("1");// 是
			prpdPaymentAccountDto.setValidStatus("1");// 有效
		}
		prpdPaymentAccountDto.setRegistNo(registNo);
		request.setAttribute("prpdpaymentaccountDto", prpdPaymentAccountDto);
	}

	public void SearchWithOwnerName(HttpServletRequest request) throws Exception {
		String ownerName = request.getParameter("ownerName");
		if (DataUtils.emptyToNull(DataUtils.dbNullToEmpty(ownerName)) != null) {
			String conditions = "ownername like '" + ownerName + "%'";

			Iterator<PrpDpaymentAccount> iterator = this.getPrpDpaymentAccountService().findByConditions(conditions).iterator();
			ArrayList<PrpDpaymentAccount> PrpdPaymentAccountDtoList = new ArrayList<PrpDpaymentAccount>();

			while (iterator.hasNext()) {
				PrpDpaymentAccount prpdPaymentAccountDto = (PrpDpaymentAccount) iterator.next();
				PrpdPaymentAccountDtoList.add(prpdPaymentAccountDto);
			}
			request.setAttribute("PrpdPaymentAccountDtoList", PrpdPaymentAccountDtoList);
		}
	}

	// add by liuwei at 2010-12-21 按费用支付对象姓名查询银行帳号信息 begin
	public void SearchWithPayObjectName(HttpServletRequest request) throws Exception {
		String ownerName = request.getParameter("ownerName");
		String serialNo = request.getParameter("serialNo");
		if (DataUtils.emptyToNull(DataUtils.dbNullToEmpty(ownerName)) != null) {
			String conditions = "ownername like '" + ownerName + "%'";

			Iterator<PrpDpaymentAccount> iterator = this.getPrpDpaymentAccountService().findByConditions(conditions).iterator();
			ArrayList<PrpDpaymentAccount> PrpdPaymentAccountDtoList = new ArrayList<PrpDpaymentAccount>();

			while (iterator.hasNext()) {
				PrpDpaymentAccount prpdPaymentAccountDto = (PrpDpaymentAccount) iterator.next();
				PrpdPaymentAccountDtoList.add(prpdPaymentAccountDto);
			}
			request.setAttribute("PrpdPaymentAccountDtoList", PrpdPaymentAccountDtoList);
			request.setAttribute("serialNo", serialNo);
		}
	}

	public PrpLaccountService getPrpLaccountService() {
		if (prpLaccountService == null) {
			return (PrpLaccountService) ServiceFactory.getService("prpLaccountService");
		}
		return prpLaccountService;
	}

	public void setPrpLaccountService(PrpLaccountService prpLaccountService) {
		this.prpLaccountService = prpLaccountService;
	}

	public PrpDpaymentAccountService getPrpDpaymentAccountService() {
		if (prpDpaymentAccountService == null) {
			return (PrpDpaymentAccountService) ServiceFactory.getService("prpDpaymentAccountService");
		}
		return prpDpaymentAccountService;
	}

	public void setPrpDpaymentAccountService(PrpDpaymentAccountService prpDpaymentAccountService) {
		this.prpDpaymentAccountService = prpDpaymentAccountService;
	}

}
