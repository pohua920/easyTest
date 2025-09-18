package com.tlg.msSqlMob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.BatchRepairMainDao;
import com.tlg.msSqlMob.entity.BatchRepairMain;
import com.tlg.msSqlMob.service.BatchRepairMainService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class BatchRepairMainServiceImpl implements BatchRepairMainService{

	private BatchRepairMainDao batchRepairMainDao;

	@SuppressWarnings("rawtypes")
	@Override
	public int countBatchRepairMain(Map params) throws SystemException, Exception {
		return batchRepairMainDao.count(params);
	}

	@Override
	public Result findBatchRepairMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = batchRepairMainDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<BatchRepairMain> searchResult = batchRepairMainDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findBatchRepairMainByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<BatchRepairMain> searchResult = batchRepairMainDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findBatchRepairMainByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		BatchRepairMain persisted = batchRepairMainDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateBatchRepairMain(BatchRepairMain batchRepairMain) throws SystemException, Exception {

		if (batchRepairMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(batchRepairMainDao.isUnique(batchRepairMain)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = batchRepairMainDao.update(batchRepairMain);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(batchRepairMain);
		return result;
	}

	@Override
	public Result insertBatchRepairMain(BatchRepairMain batchRepairMain) throws SystemException, Exception {

		if (batchRepairMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!batchRepairMainDao.isUnique(batchRepairMain)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		batchRepairMainDao.insert(batchRepairMain);
		
		if(batchRepairMainDao.isUnique(batchRepairMain)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(batchRepairMain);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result removeBatchRepairMain(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("transactionId", transactionId);
		BatchRepairMain persisted = batchRepairMainDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = batchRepairMainDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}

	public BatchRepairMainDao getBatchRepairMainDao() {
		return batchRepairMainDao;
	}

	public void setBatchRepairMainDao(BatchRepairMainDao batchRepairMainDao) {
		this.batchRepairMainDao = batchRepairMainDao;
	}
}
