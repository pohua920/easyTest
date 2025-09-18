package com.tlg.msSqlMob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.BatchRepairDetailDao;
import com.tlg.msSqlMob.entity.BatchRepairDetail;
import com.tlg.msSqlMob.service.BatchRepairDetailService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class BatchRepairDetailServiceImpl implements BatchRepairDetailService{

	private BatchRepairDetailDao batchRepairDetailDao;

	@SuppressWarnings("rawtypes")
	@Override
	public int countBatchRepairDetail(Map params) throws SystemException, Exception {
		return batchRepairDetailDao.count(params);
	}

	@Override
	public Result findBatchRepairDetailByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = batchRepairDetailDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<BatchRepairDetail> searchResult = batchRepairDetailDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findBatchRepairDetailByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<BatchRepairDetail> searchResult = batchRepairDetailDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findBatchRepairDetailByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		BatchRepairDetail persisted = batchRepairDetailDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateBatchRepairDetail(BatchRepairDetail batchRepairDetail) throws SystemException, Exception {

		if (batchRepairDetail == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(batchRepairDetailDao.isUnique(batchRepairDetail)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = batchRepairDetailDao.update(batchRepairDetail);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(batchRepairDetail);
		return result;
	}

	@Override
	public Result insertBatchRepairDetail(BatchRepairDetail batchRepairDetail) throws SystemException, Exception {

		if (batchRepairDetail == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!batchRepairDetailDao.isUnique(batchRepairDetail)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		batchRepairDetailDao.insert(batchRepairDetail);
		
		if(batchRepairDetailDao.isUnique(batchRepairDetail)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(batchRepairDetail);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result removeBatchRepairDetail(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("transactionId", transactionId);
		BatchRepairDetail persisted = batchRepairDetailDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = batchRepairDetailDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public BatchRepairDetailDao getBatchRepairDetailDao() {
		return batchRepairDetailDao;
	}

	public void setBatchRepairDetailDao(BatchRepairDetailDao batchRepairDetailDao) {
		this.batchRepairDetailDao = batchRepairDetailDao;
	}
}
