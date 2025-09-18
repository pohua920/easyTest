package com.tlg.msSqlSms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlSms.dao.SMSRequestDao;
import com.tlg.msSqlSms.entity.SMSRequest;
import com.tlg.msSqlSms.service.SMSRequestService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "msSqlSmsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class SMSRequestServiceImpl implements SMSRequestService{

	private SMSRequestDao smsRequestDao;

	@Override
	public int countSMSRequest(Map params) throws SystemException, Exception {
		return smsRequestDao.count(params);
	}

	@Override
	public Result findSMSRequestByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = smsRequestDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<SMSRequest> searchResult = smsRequestDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findTopSMSRequestByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<SMSRequest> searchResult = smsRequestDao.findTopByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findSMSRequestByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<SMSRequest> searchResult = smsRequestDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findSMSRequestByUK(String serial) throws SystemException,Exception {

		if (StringUtil.isSpace(serial)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("serial", serial);
		SMSRequest persisted = smsRequestDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateSMSRequest(SMSRequest smsRequest) throws SystemException, Exception {

		if (smsRequest == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(smsRequestDao.isUnique(smsRequest)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = smsRequestDao.update(smsRequest);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(smsRequest);
		return result;
	}

	@Override
	public Result insertSMSRequest(SMSRequest smsRequest) throws SystemException, Exception {

		if (smsRequest == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!smsRequestDao.isUnique(smsRequest)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		smsRequestDao.insert(smsRequest);
		
		if(smsRequestDao.isUnique(smsRequest)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(smsRequest);
		return result;
	}

	@Override
	public Result removeSMSRequest(String serial) throws SystemException, Exception {

		if (StringUtil.isSpace(serial)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("serial", serial);
		SMSRequest persisted = smsRequestDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = smsRequestDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	@Override
	public void removeSMSRequestNullData() throws SystemException, Exception {
		smsRequestDao.removeNullData();
	}

	public SMSRequestDao getSmsRequestDao() {
		return smsRequestDao;
	}

	public void setSmsRequestDao(SMSRequestDao smsRequestDao) {
		this.smsRequestDao = smsRequestDao;
	}
}
