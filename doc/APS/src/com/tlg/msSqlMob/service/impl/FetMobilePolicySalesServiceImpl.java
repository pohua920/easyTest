package com.tlg.msSqlMob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.FetMobilePolicySalesDao;
import com.tlg.msSqlMob.entity.FetMobilePolicySales;
import com.tlg.msSqlMob.service.FetMobilePolicySalesService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FetMobilePolicySalesServiceImpl implements FetMobilePolicySalesService{

	private FetMobilePolicySalesDao fetMobilePolicySalesDao;
	
	@Override
	public int countFetMobilePolicySales(Map params) throws SystemException, Exception {
		return fetMobilePolicySalesDao.count(params);
	}

	@Override
	public Result findFetMobilePolicySalesByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = fetMobilePolicySalesDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FetMobilePolicySales> searchResult = fetMobilePolicySalesDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFetMobilePolicySalesByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FetMobilePolicySales> searchResult = fetMobilePolicySalesDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFetMobilePolicySalesByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		FetMobilePolicySales persisted = fetMobilePolicySalesDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateFetMobilePolicySales(FetMobilePolicySales fetMobilePolicySales) throws SystemException, Exception {

		if (fetMobilePolicySales == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(fetMobilePolicySalesDao.isUnique(fetMobilePolicySales)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = fetMobilePolicySalesDao.update(fetMobilePolicySales);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(fetMobilePolicySales);
		return result;
	}

	@Override
	public Result insertFetMobilePolicySales(FetMobilePolicySales fetMobilePolicySales) throws SystemException, Exception {

		if (fetMobilePolicySales == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!fetMobilePolicySalesDao.isUnique(fetMobilePolicySales)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		fetMobilePolicySalesDao.insert(fetMobilePolicySales);
		
		if(fetMobilePolicySalesDao.isUnique(fetMobilePolicySales)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(fetMobilePolicySales);
		return result;
	}

	@Override
	public Result removeFetMobilePolicySales(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		FetMobilePolicySales persisted = fetMobilePolicySalesDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = fetMobilePolicySalesDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	@Override
	public boolean isUnique(FetMobilePolicySales fetMobilePolicySales) throws SystemException, Exception {
		return fetMobilePolicySalesDao.isUnique(fetMobilePolicySales);
	}

	public FetMobilePolicySalesDao getFetMobilePolicySalesDao() {
		return fetMobilePolicySalesDao;
	}

	public void setFetMobilePolicySalesDao(FetMobilePolicySalesDao fetMobilePolicySalesDao) {
		this.fetMobilePolicySalesDao = fetMobilePolicySalesDao;
	}

}
