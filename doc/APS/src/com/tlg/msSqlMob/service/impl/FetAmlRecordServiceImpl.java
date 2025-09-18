package com.tlg.msSqlMob.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.FetAmlRecordDao;
import com.tlg.msSqlMob.entity.FetAmlRecord;
import com.tlg.msSqlMob.service.FetAmlRecordService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：MOB0022，處理人員：CE035，需求單編號：MOB0022 洗錢檢核條件記錄到FET_AML_RECORD資料表 */
@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FetAmlRecordServiceImpl implements FetAmlRecordService {

	private FetAmlRecordDao fetAmlRecordDao;
	
	@Override
	public Result findFetAmlRecordByContractId(String contractId) throws SystemException, Exception {
		if (StringUtil.isSpace(contractId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("contractId", contractId);
		List<FetAmlRecord> persisted = fetAmlRecordDao.selectByContractId(params);
		if (null == persisted || 0 == persisted.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public int countFetAmlRecord(Map params) throws SystemException, Exception {
		return fetAmlRecordDao.count(params);
	}

	@Override
	public Result findFetAmlRecordByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FetAmlRecord> searchResult = fetAmlRecordDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFetAmlRecordByUK(String transactionId) throws SystemException, Exception {
		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		FetAmlRecord persisted = fetAmlRecordDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateFetAmlRecord(FetAmlRecord entity) throws SystemException, Exception {
		if (entity == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(fetAmlRecordDao.isUnique(entity)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		entity.setModifiedBy("system");
		entity.setModifiedTime(new Date());
		boolean status = fetAmlRecordDao.update(entity);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(entity);
		return result;
	}

	@Override
	public Result insertFetAmlRecord(FetAmlRecord entity) throws SystemException, Exception {
		if (entity == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!fetAmlRecordDao.isUnique(entity)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		entity.setCreatedBy("system");
		entity.setCreatedTime(new Date());
		fetAmlRecordDao.insert(entity);
		
		if(fetAmlRecordDao.isUnique(entity)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(entity);
		return result;
	}

	@Override
	public Result removeFetAmlRecord(String transactionId) throws SystemException, Exception {
		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		FetAmlRecord persisted = fetAmlRecordDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = fetAmlRecordDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}

	public FetAmlRecordDao getFetAmlRecordDao() {
		return fetAmlRecordDao;
	}

	public void setFetAmlRecordDao(FetAmlRecordDao fetAmlRecordDao) {
		this.fetAmlRecordDao = fetAmlRecordDao;
	}

}
