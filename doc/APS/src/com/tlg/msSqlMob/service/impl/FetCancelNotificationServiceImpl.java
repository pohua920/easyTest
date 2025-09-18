package com.tlg.msSqlMob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.FetCancelNotificationDao;
import com.tlg.msSqlMob.entity.FetCancelNotification;
import com.tlg.msSqlMob.service.FetCancelNotificationService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FetCancelNotificationServiceImpl implements FetCancelNotificationService{

	private FetCancelNotificationDao fetCancelNotificationDao;

	@Override
	public int countFetCancelNotification(Map params) throws SystemException, Exception {
		return fetCancelNotificationDao.count(params);
	}

	@Override
	public Result findFetCancelNotificationByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = fetCancelNotificationDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FetCancelNotification> searchResult = fetCancelNotificationDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFetCancelNotificationByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FetCancelNotification> searchResult = fetCancelNotificationDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFetCancelNotificationByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		FetCancelNotification persisted = fetCancelNotificationDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateFetCancelNotification(FetCancelNotification fetCancelNotification) throws SystemException, Exception {

		if (fetCancelNotification == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(fetCancelNotificationDao.isUnique(fetCancelNotification)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = fetCancelNotificationDao.update(fetCancelNotification);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(fetCancelNotification);
		return result;
	}

	@Override
	public Result insertFetCancelNotification(FetCancelNotification fetCancelNotification) throws SystemException, Exception {

		if (fetCancelNotification == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!fetCancelNotificationDao.isUnique(fetCancelNotification)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		fetCancelNotificationDao.insert(fetCancelNotification);
		
		if(fetCancelNotificationDao.isUnique(fetCancelNotification)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(fetCancelNotification);
		return result;
	}

	@Override
	public Result removeFetCancelNotification(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("transactionId", transactionId);
		FetCancelNotification persisted = fetCancelNotificationDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = fetCancelNotificationDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}

	public FetCancelNotificationDao getFetCancelNotificationDao() {
		return fetCancelNotificationDao;
	}

	public void setFetCancelNotificationDao(FetCancelNotificationDao fetCancelNotificationDao) {
		this.fetCancelNotificationDao = fetCancelNotificationDao;
	}
}
