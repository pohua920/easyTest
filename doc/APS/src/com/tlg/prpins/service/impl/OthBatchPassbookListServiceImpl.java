package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.OthBatchPassbookListDao;
import com.tlg.prpins.entity.OthBatchPassbookList;
import com.tlg.prpins.service.OthBatchPassbookListService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

/* mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class OthBatchPassbookListServiceImpl implements OthBatchPassbookListService{
	private OthBatchPassbookListDao othBatchPassbookListDao;

	@Override
	public Result findOthBatchPassbookListByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<OthBatchPassbookList> searchResult = othBatchPassbookListDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result updateOthBatchPassbookList(OthBatchPassbookList othBatchPassbookList)
			throws SystemException, Exception {
		if (othBatchPassbookList == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = othBatchPassbookListDao.update(othBatchPassbookList);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(othBatchPassbookList);
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result updateBatchNoByTmpBno(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Integer count = othBatchPassbookListDao.updateBatchNoByTmpBno(params);
		if (null == count || 0 == count) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(count);
		}
		return result;
	}
	
	
	/**
	 * mantis：OTH0132，處理人員：BI086，需求單編號：OTH0132  保單存摺AS400資料寫入核心中介Table
	 * 
	 * 新增保單存摺AS400資料寫入核心中介Table
	 */
	@Override
	public Result insertOthBatchPassbookList(OthBatchPassbookList othBatchPassbookList) throws SystemException, Exception {

		if (othBatchPassbookList == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = othBatchPassbookListDao.insert(othBatchPassbookList);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		othBatchPassbookList.setOid(oid);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(othBatchPassbookList);
		return result;
	}

	public OthBatchPassbookListDao getOthBatchPassbookListDao() {
		return othBatchPassbookListDao;
	}

	public void setOthBatchPassbookListDao(OthBatchPassbookListDao othBatchPassbookListDao) {
		this.othBatchPassbookListDao = othBatchPassbookListDao;
	}

}
