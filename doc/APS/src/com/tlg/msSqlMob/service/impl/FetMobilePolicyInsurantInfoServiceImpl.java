package com.tlg.msSqlMob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.FetMobilePolicyInsurantInfoDao;
import com.tlg.msSqlMob.entity.FetMobilePolicyInsurantInfo;
import com.tlg.msSqlMob.service.FetMobilePolicyInsurantInfoService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FetMobilePolicyInsurantInfoServiceImpl implements FetMobilePolicyInsurantInfoService{

	private FetMobilePolicyInsurantInfoDao fetMobilePolicyInsurantInfoDao;

	@Override
	public int countFetMobilePolicyInsurantInfo(Map params) throws SystemException, Exception {
		return fetMobilePolicyInsurantInfoDao.count(params);
	}

	@Override
	public Result findFetMobilePolicyInsurantInfoByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = fetMobilePolicyInsurantInfoDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FetMobilePolicyInsurantInfo> searchResult = fetMobilePolicyInsurantInfoDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFetMobilePolicyInsurantInfoByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FetMobilePolicyInsurantInfo> searchResult = fetMobilePolicyInsurantInfoDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFetMobilePolicyInsurantInfoByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		FetMobilePolicyInsurantInfo persisted = fetMobilePolicyInsurantInfoDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateFetMobilePolicyInsurantInfo(FetMobilePolicyInsurantInfo fetMobilePolicyInsurantInfo) throws SystemException, Exception {

		if (fetMobilePolicyInsurantInfo == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(fetMobilePolicyInsurantInfoDao.isUnique(fetMobilePolicyInsurantInfo)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = fetMobilePolicyInsurantInfoDao.update(fetMobilePolicyInsurantInfo);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(fetMobilePolicyInsurantInfo);
		return result;
	}

	@Override
	public Result insertFetMobilePolicyInsurantInfo(FetMobilePolicyInsurantInfo fetMobilePolicyInsurantInfo) throws SystemException, Exception {

		if (fetMobilePolicyInsurantInfo == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!fetMobilePolicyInsurantInfoDao.isUnique(fetMobilePolicyInsurantInfo)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		fetMobilePolicyInsurantInfoDao.insert(fetMobilePolicyInsurantInfo);
		
		if(fetMobilePolicyInsurantInfoDao.isUnique(fetMobilePolicyInsurantInfo)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(fetMobilePolicyInsurantInfo);
		return result;
	}

	@Override
	public Result removeFetMobilePolicyInsurantInfo(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("transactionId", transactionId);
		FetMobilePolicyInsurantInfo persisted = fetMobilePolicyInsurantInfoDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = fetMobilePolicyInsurantInfoDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public FetMobilePolicyInsurantInfoDao getFetMobilePolicyInsurantInfoDao() {
		return fetMobilePolicyInsurantInfoDao;
	}

	public void setFetMobilePolicyInsurantInfoDao(FetMobilePolicyInsurantInfoDao fetMobilePolicyInsurantInfoDao) {
		this.fetMobilePolicyInsurantInfoDao = fetMobilePolicyInsurantInfoDao;
	}
}
