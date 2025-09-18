package com.tlg.aps.bs.smsService.impl;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.smsService.ReceiptOtService;
import com.tlg.exception.SystemException;
import com.tlg.msSqlSms.entity.Receipt;
import com.tlg.msSqlSms.service.ReceiptService;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "msSqlSmsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class ReceiptOtServiceImpl implements ReceiptOtService {

	private ReceiptService receiptService;

	@Override
	public int countReceipt(Map params) throws SystemException, Exception {
		return receiptService.countReceipt(params);
	}

	@Override
	public Result findReceiptByPageInfo(PageInfo pageInfo)
			throws SystemException, Exception {

		return receiptService.findReceiptByPageInfo(pageInfo);
	}

	@Override
	public Result findTopReceiptByParams(Map params) throws SystemException,
			Exception {

		return receiptService.findReceiptByParams(params);
	}

	@Override
	public Result findReceiptByParams(Map params) throws SystemException,
			Exception {

		return receiptService.findReceiptByParams(params);
	}

	@Override
	public Result findReceiptByUK(String messagId, String destAddress,
			String seq) throws SystemException, Exception {
		return receiptService.findReceiptByUK(messagId, destAddress, seq);
	}

	@Override
	public Result updateReceipt(Receipt receipt) throws SystemException,
			Exception {

		return receiptService.updateReceipt(receipt);
	}

	@Override
	public Result insertReceipt(Receipt receipt) throws SystemException,
			Exception {
		return receiptService.insertReceipt(receipt);
	}

	public ReceiptService getReceiptService() {
		return receiptService;
	}

	public void setReceiptService(ReceiptService receiptService) {
		this.receiptService = receiptService;
	}
}
