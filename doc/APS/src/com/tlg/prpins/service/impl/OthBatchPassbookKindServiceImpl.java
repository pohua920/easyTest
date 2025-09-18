package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.OthBatchPassbookKindDao;
import com.tlg.prpins.entity.OthBatchPassbookKind;
import com.tlg.prpins.service.OthBatchPassbookKindService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

/* mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class OthBatchPassbookKindServiceImpl implements OthBatchPassbookKindService{
	private OthBatchPassbookKindDao othBatchPassbookKindDao;

	@Override
	public Result findOthBatchPassbookKindByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<OthBatchPassbookKind> searchResult = othBatchPassbookKindDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result updateOthBatchPassbookKind(OthBatchPassbookKind othBatchPassbookKind)
			throws SystemException, Exception {
		if (othBatchPassbookKind == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = othBatchPassbookKindDao.update(othBatchPassbookKind);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(othBatchPassbookKind);
		return result;
	}

	@Override
	public Result insertOthBatchPassbookKind(OthBatchPassbookKind othBatchPassbookKind) throws SystemException, Exception {

		if (othBatchPassbookKind == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = othBatchPassbookKindDao.insert(othBatchPassbookKind);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(othBatchPassbookKind);
		return result;
	}

	public OthBatchPassbookKindDao getOthBatchPassbookKindDao() {
		return othBatchPassbookKindDao;
	}

	public void setOthBatchPassbookKindDao(OthBatchPassbookKindDao othBatchPassbookKindDao) {
		this.othBatchPassbookKindDao = othBatchPassbookKindDao;
	}

}
