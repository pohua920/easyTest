package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.OthBatchPassbookDao;
import com.tlg.prpins.entity.OthBatchPassbook;
import com.tlg.prpins.service.OthBatchPassbookService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class OthBatchPassbookServiceImpl implements OthBatchPassbookService{
	private OthBatchPassbookDao othBatchPassbookDao;

	@Override
	public Result findOthBatchPassbookByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = othBatchPassbookDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<OthBatchPassbook> searchResult = othBatchPassbookDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public int countOthBatchPassbook(Map params) throws SystemException, Exception {
		return othBatchPassbookDao.count(params);
	}
	

	@Override
	public Result insertOthBatchPassbook(OthBatchPassbook othBatchPassbook) throws SystemException, Exception {
		if (othBatchPassbook == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		othBatchPassbookDao.insert(othBatchPassbook);
		
		if(othBatchPassbookDao.isUnique(othBatchPassbook)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(othBatchPassbook);
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findOthBatchPassbookByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<OthBatchPassbook> searchResult = othBatchPassbookDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateOthBatchPassbook(OthBatchPassbook othBatchPassbook)
			throws SystemException, Exception {
		if (othBatchPassbook == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = othBatchPassbookDao.update(othBatchPassbook);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(othBatchPassbook);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result findOthBatchPassbookByUK(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if(!params.containsKey("batchNo")) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		OthBatchPassbook othBatchPassbook = othBatchPassbookDao.findByUK(params);
		if (null == othBatchPassbook) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(othBatchPassbook);
		}
		return result;
	}

	public OthBatchPassbookDao getOthBatchPassbookDao() {
		return othBatchPassbookDao;
	}

	public void setOthBatchPassbookDao(OthBatchPassbookDao othBatchPassbookDao) {
		this.othBatchPassbookDao = othBatchPassbookDao;
	}
	
}
