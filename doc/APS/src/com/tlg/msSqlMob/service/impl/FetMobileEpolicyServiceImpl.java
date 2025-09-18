package com.tlg.msSqlMob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.FetMobileEpolicyDao;
import com.tlg.msSqlMob.entity.FetMobileEpolicy;
import com.tlg.msSqlMob.service.FetMobileEpolicyService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FetMobileEpolicyServiceImpl implements FetMobileEpolicyService{

	private FetMobileEpolicyDao fetMobileEpolicyDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countFetMobileEpolicy(Map params) throws SystemException, Exception {
		return fetMobileEpolicyDao.count(params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFetMobileEpolicyByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FetMobileEpolicy> searchResult = fetMobileEpolicyDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFetMobileEpolicyByUK(String oid) throws SystemException,Exception {

		if (StringUtil.isSpace(oid)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("oid", oid);
		FetMobileEpolicy persisted = fetMobileEpolicyDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateFetMobileEpolicy(FetMobileEpolicy fetMobileEpolicy) throws SystemException, Exception {

		if (fetMobileEpolicy == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(fetMobileEpolicyDao.isUnique(fetMobileEpolicy)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = fetMobileEpolicyDao.update(fetMobileEpolicy);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(fetMobileEpolicy);
		return result;
	}

	@Override
	public Result insertFetMobileEpolicy(FetMobileEpolicy fetMobileEpolicy) throws SystemException, Exception {

		if (fetMobileEpolicy == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!fetMobileEpolicyDao.isUnique(fetMobileEpolicy)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		fetMobileEpolicyDao.insert(fetMobileEpolicy);
		
		if(fetMobileEpolicyDao.isUnique(fetMobileEpolicy)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(fetMobileEpolicy);
		return result;
	}

	@Override
	public Result removeFetMobileEpolicy(String oid) throws SystemException, Exception {

		if (StringUtil.isSpace(oid)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("oid", oid);
		FetMobileEpolicy persisted = fetMobileEpolicyDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = fetMobileEpolicyDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public FetMobileEpolicyDao getFetMobileEpolicyDao() {
		return fetMobileEpolicyDao;
	}

	public void setFetMobileEpolicyDao(FetMobileEpolicyDao fetMobileEpolicyDao) {
		this.fetMobileEpolicyDao = fetMobileEpolicyDao;
	}

}
