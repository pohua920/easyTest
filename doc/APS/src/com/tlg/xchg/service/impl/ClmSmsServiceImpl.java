package com.tlg.xchg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.xchg.dao.ClmSmsDao;
import com.tlg.xchg.entity.ClmSms;
import com.tlg.xchg.service.ClmSmsService;

@Transactional(value = "xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ClmSmsServiceImpl implements ClmSmsService{

	private ClmSmsDao clmSmsDao;

	@Override
	public int countClmSms(Map params) throws SystemException, Exception {
		return clmSmsDao.count(params);
	}

	@Override
	public Result findClmSmsByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = clmSmsDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<ClmSms> searchResult = clmSmsDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findClmSmsByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<ClmSms> searchResult = clmSmsDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findClmSmsByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		ClmSms persisted = clmSmsDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateClmSms(ClmSms clmSms) throws SystemException, Exception {

		if (clmSms == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(clmSmsDao.isUnique(clmSms)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = clmSmsDao.update(clmSms);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(clmSms);
		return result;
	}

	@Override
	public Result insertClmSms(ClmSms clmSms) throws SystemException, Exception {

		if (clmSms == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!clmSmsDao.isUnique(clmSms)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		clmSmsDao.insert(clmSms);
		
		if(clmSmsDao.isUnique(clmSms)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}

		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(clmSms);
		return result;
	}

	@Override
	public Result removeClmSms(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("transactionId", transactionId);
		ClmSms persisted = clmSmsDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = clmSmsDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}



	
	public ClmSmsDao getClmSmsDao() {
		return clmSmsDao;
	}

	public void setClmSmsDao(ClmSmsDao clmSmsDao) {
		this.clmSmsDao = clmSmsDao;
	}
}
