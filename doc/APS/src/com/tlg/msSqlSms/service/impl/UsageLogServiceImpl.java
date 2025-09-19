package com.tlg.msSqlSms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlSms.dao.UsageLogDao;
import com.tlg.msSqlSms.entity.UsageLog;
import com.tlg.msSqlSms.service.UsageLogService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "msSqlSmsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class UsageLogServiceImpl implements UsageLogService{

	private UsageLogDao usageLogDao;

	@Override
	public int countUsageLog(Map params) throws SystemException, Exception {
		return usageLogDao.count(params);
	}

	@Override
	public Result findUsageLogByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = usageLogDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<UsageLog> searchResult = usageLogDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findUsageLogByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<UsageLog> searchResult = usageLogDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findUsageLogBySubmitHour(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<UsageLog> searchResult = usageLogDao.selectBySubmitHour(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findUsageLogByUK(String serial) throws SystemException,Exception {

		if (StringUtil.isSpace(serial)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("serial", serial);
		UsageLog persisted = usageLogDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateUsageLog(UsageLog usageLog) throws SystemException, Exception {

		if (usageLog == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(usageLogDao.isUnique(usageLog)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = usageLogDao.update(usageLog);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(usageLog);
		return result;
	}
	
	@Override
	public Result updateUsageLogResp(UsageLog usageLog) throws SystemException, Exception {

		if (usageLog == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(usageLogDao.isUnique(usageLog)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = usageLogDao.updateForResp(usageLog);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(usageLog);
		return result;
	}

	@Override
	public Result insertUsageLog(UsageLog usageLog) throws SystemException, Exception {

		if (usageLog == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
//		if(!usageLogDao.isUnique(usageLog)) {
//    		throw new SystemException("資料已存在資料庫中");    		
//    	}
		usageLogDao.insert(usageLog);
		
		if(usageLogDao.isUnique(usageLog)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(usageLog);
		return result;
	}
	
	@Override
	public Result removeUsageLog(String serial) throws SystemException, Exception {

		if (StringUtil.isSpace(serial)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("serial", serial);
		UsageLog persisted = usageLogDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = usageLogDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}

	public UsageLogDao getUsageLogDao() {
		return usageLogDao;
	}

	public void setUsageLogDao(UsageLogDao usageLogDao) {
		this.usageLogDao = usageLogDao;
	}
}
