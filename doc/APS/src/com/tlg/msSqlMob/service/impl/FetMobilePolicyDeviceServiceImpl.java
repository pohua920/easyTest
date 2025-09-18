package com.tlg.msSqlMob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.FetMobilePolicyDeviceDao;
import com.tlg.msSqlMob.entity.FetMobilePolicyDevice;
import com.tlg.msSqlMob.service.FetMobilePolicyDeviceService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FetMobilePolicyDeviceServiceImpl implements FetMobilePolicyDeviceService{

	private FetMobilePolicyDeviceDao fetMobilePolicyDeviceDao;

	@Override
	public int countFetMobilePolicyDevice(Map params) throws SystemException, Exception {
		return fetMobilePolicyDeviceDao.count(params);
	}

	@Override
	public Result findFetMobilePolicyDeviceByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = fetMobilePolicyDeviceDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FetMobilePolicyDevice> searchResult = fetMobilePolicyDeviceDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFetMobilePolicyDeviceByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FetMobilePolicyDevice> searchResult = fetMobilePolicyDeviceDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFetMobilePolicyDeviceByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		FetMobilePolicyDevice persisted = fetMobilePolicyDeviceDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateFetMobilePolicyDevice(FetMobilePolicyDevice fetMobilePolicyDevice) throws SystemException, Exception {

		if (fetMobilePolicyDevice == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(fetMobilePolicyDeviceDao.isUnique(fetMobilePolicyDevice)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = fetMobilePolicyDeviceDao.update(fetMobilePolicyDevice);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(fetMobilePolicyDevice);
		return result;
	}

	@Override
	public Result insertFetMobilePolicyDevice(FetMobilePolicyDevice fetMobilePolicyDevice) throws SystemException, Exception {

		if (fetMobilePolicyDevice == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!fetMobilePolicyDeviceDao.isUnique(fetMobilePolicyDevice)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		fetMobilePolicyDeviceDao.insert(fetMobilePolicyDevice);
		
		if(fetMobilePolicyDeviceDao.isUnique(fetMobilePolicyDevice)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(fetMobilePolicyDevice);
		return result;
	}

	@Override
	public Result removeFetMobilePolicyDevice(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("transactionId", transactionId);
		FetMobilePolicyDevice persisted = fetMobilePolicyDeviceDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = fetMobilePolicyDeviceDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public FetMobilePolicyDeviceDao getFetMobilePolicyDeviceDao() {
		return fetMobilePolicyDeviceDao;
	}

	public void setFetMobilePolicyDeviceDao(FetMobilePolicyDeviceDao fetMobilePolicyDeviceDao) {
		this.fetMobilePolicyDeviceDao = fetMobilePolicyDeviceDao;
	}
}
