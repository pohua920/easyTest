package com.tlg.msSqlMob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.FetMobilePolicyDao;
import com.tlg.msSqlMob.entity.FetMobilePolicy;
import com.tlg.msSqlMob.service.FetMobilePolicyService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FetMobilePolicyServiceImpl implements FetMobilePolicyService{

	private FetMobilePolicyDao fetMobilePolicyDao;

	@Override
	public Result findPolicyNoByWait() throws SystemException,
			Exception {
		
		Result result = new Result();

		List<FetMobilePolicy> searchResult = fetMobilePolicyDao.findPolicyByStatusWait(null);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countFetMobilePolicy(Map params) throws SystemException, Exception {
		return fetMobilePolicyDao.count(params);
	}
	
	@Override
	public int countPolicyByStatusWait() throws SystemException, Exception {
		return fetMobilePolicyDao.countPolicyByStatusWait(null);
	}

	@Override
	public Result findFetMobilePolicyByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = fetMobilePolicyDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FetMobilePolicy> searchResult = fetMobilePolicyDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFetMobilePolicyByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FetMobilePolicy> searchResult = fetMobilePolicyDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFetMobilePolicyByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		FetMobilePolicy persisted = fetMobilePolicyDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateFetMobilePolicy(FetMobilePolicy fetMobilePolicy) throws SystemException, Exception {

		if (fetMobilePolicy == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(fetMobilePolicyDao.isUnique(fetMobilePolicy)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = fetMobilePolicyDao.update(fetMobilePolicy);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(fetMobilePolicy);
		return result;
	}

	@Override
	public Result insertFetMobilePolicy(FetMobilePolicy fetMobilePolicy) throws SystemException, Exception {

		if (fetMobilePolicy == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!fetMobilePolicyDao.isUnique(fetMobilePolicy)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		fetMobilePolicyDao.insert(fetMobilePolicy);
		
		if(fetMobilePolicyDao.isUnique(fetMobilePolicy)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(fetMobilePolicy);
		return result;
	}

	@Override
	public Result removeFetMobilePolicy(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		FetMobilePolicy persisted = fetMobilePolicyDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = fetMobilePolicyDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public FetMobilePolicyDao getFetMobilePolicyDao() {
		return fetMobilePolicyDao;
	}

	public void setFetMobilePolicyDao(FetMobilePolicyDao fetMobilePolicyDao) {
		this.fetMobilePolicyDao = fetMobilePolicyDao;
	}

}
