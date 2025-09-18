package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.OthBatchPassbookReturnLogDao;
import com.tlg.prpins.entity.OthBatchPassbookReturnLog;
import com.tlg.prpins.service.OthBatchPassbookReturnLogService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：OTH0138，處理人員：CC009，需求單編號：OTH0138 保發中心_保單存摺回饋檔回存資料庫規格 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class OthBatchPassbookReturnLogServiceImpl implements OthBatchPassbookReturnLogService{
	private OthBatchPassbookReturnLogDao othBatchPassbookReturnLogDao;

	@Override
	public Result findOthBatchPassbookReturnLogByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = othBatchPassbookReturnLogDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<OthBatchPassbookReturnLog> searchResult = othBatchPassbookReturnLogDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public int countOthBatchPassbookReturnLog(Map params) throws SystemException, Exception {
		return othBatchPassbookReturnLogDao.count(params);
	}
	

	@Override
	public Result insertOthBatchPassbookReturnLog(OthBatchPassbookReturnLog othBatchPassbookReturnLog) throws SystemException, Exception {
		if (othBatchPassbookReturnLog == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		othBatchPassbookReturnLogDao.insert(othBatchPassbookReturnLog);
		
		if(othBatchPassbookReturnLogDao.isUnique(othBatchPassbookReturnLog)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(othBatchPassbookReturnLog);
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findOthBatchPassbookReturnLogByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<OthBatchPassbookReturnLog> searchResult = othBatchPassbookReturnLogDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateOthBatchPassbookReturnLog(OthBatchPassbookReturnLog othBatchPassbookReturnLog)
			throws SystemException, Exception {
		if (othBatchPassbookReturnLog == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = othBatchPassbookReturnLogDao.update(othBatchPassbookReturnLog);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(othBatchPassbookReturnLog);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result findOthBatchPassbookReturnLogByUK(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if(!params.containsKey("batchNo")) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		OthBatchPassbookReturnLog othBatchPassbookReturnLog = othBatchPassbookReturnLogDao.findByUK(params);
		if (null == othBatchPassbookReturnLog) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(othBatchPassbookReturnLog);
		}
		return result;
	}

	public OthBatchPassbookReturnLogDao getOthBatchPassbookReturnLogDao() {
		return othBatchPassbookReturnLogDao;
	}

	public void setOthBatchPassbookReturnLogDao(OthBatchPassbookReturnLogDao othBatchPassbookReturnLogDao) {
		this.othBatchPassbookReturnLogDao = othBatchPassbookReturnLogDao;
	}
	
}
