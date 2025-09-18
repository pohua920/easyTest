package com.tlg.aps.bs.othBatchPassbookServerce.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.othBatchPassbookServerce.OthBatchPassbookTranService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.OthBatchPassbookKind;
import com.tlg.prpins.entity.OthBatchPassbookList;
import com.tlg.prpins.service.OthBatchPassbookKindService;
import com.tlg.prpins.service.OthBatchPassbookListService;
import com.tlg.util.Result;
/**
 * mantis：OTH0132，處理人員：BI086，需求單編號：OTH0132  保單存摺AS400資料寫入核心中介Table
 * 
 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class OthBatchPassbookTranServiceImpl implements
		OthBatchPassbookTranService {

	private OthBatchPassbookListService othBatchPassbookListService;
	private OthBatchPassbookKindService othBatchPassbookKindService;
	

	@Override
	public Result insertOthBatchPassbookList(OthBatchPassbookList othBatchPassbookList) throws SystemException, Exception {
		return othBatchPassbookListService.insertOthBatchPassbookList(othBatchPassbookList);
	}

	@Override
	public Result insertOthBatchPassbookKind(
			OthBatchPassbookKind othBatchPassbookKind) throws SystemException,
			Exception {
		return othBatchPassbookKindService.insertOthBatchPassbookKind(othBatchPassbookKind);
	}

	public OthBatchPassbookListService getOthBatchPassbookListService() {
		return othBatchPassbookListService;
	}

	public void setOthBatchPassbookListService(
			OthBatchPassbookListService othBatchPassbookListService) {
		this.othBatchPassbookListService = othBatchPassbookListService;
	}

	public OthBatchPassbookKindService getOthBatchPassbookKindService() {
		return othBatchPassbookKindService;
	}

	public void setOthBatchPassbookKindService(
			OthBatchPassbookKindService othBatchPassbookKindService) {
		this.othBatchPassbookKindService = othBatchPassbookKindService;
	}

}
