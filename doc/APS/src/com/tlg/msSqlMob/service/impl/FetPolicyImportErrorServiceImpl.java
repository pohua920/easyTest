package com.tlg.msSqlMob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.FetPolicyImportErrorDao;
import com.tlg.msSqlMob.entity.FetPolicyImportError;
import com.tlg.msSqlMob.service.FetPolicyImportErrorService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FetPolicyImportErrorServiceImpl implements FetPolicyImportErrorService{

	private FetPolicyImportErrorDao fetPolicyImportErrorDao;

	@Override
	public int countFetPolicyImportError(Map params) throws SystemException, Exception {
		return fetPolicyImportErrorDao.count(params);
	}

	@Override
	public Result findFetPolicyImportErrorByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = fetPolicyImportErrorDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FetPolicyImportError> searchResult = fetPolicyImportErrorDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFetPolicyImportErrorByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FetPolicyImportError> searchResult = fetPolicyImportErrorDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFetPolicyImportErrorByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		FetPolicyImportError persisted = fetPolicyImportErrorDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateFetPolicyImportError(FetPolicyImportError fetPolicyImportError) throws SystemException, Exception {

		if (fetPolicyImportError == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(fetPolicyImportErrorDao.isUnique(fetPolicyImportError)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = fetPolicyImportErrorDao.update(fetPolicyImportError);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(fetPolicyImportError);
		return result;
	}

	@Override
	public Result insertFetPolicyImportError(FetPolicyImportError fetPolicyImportError) throws SystemException, Exception {

		if (fetPolicyImportError == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!fetPolicyImportErrorDao.isUnique(fetPolicyImportError)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		fetPolicyImportErrorDao.insert(fetPolicyImportError);
		
		if(fetPolicyImportErrorDao.isUnique(fetPolicyImportError)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(fetPolicyImportError);
		return result;
	}

	@Override
	public Result removeFetPolicyImportError(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("transactionId", transactionId);
		FetPolicyImportError persisted = fetPolicyImportErrorDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = fetPolicyImportErrorDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public FetPolicyImportErrorDao getFetPolicyImportErrorDao() {
		return fetPolicyImportErrorDao;
	}

	public void setFetPolicyImportErrorDao(FetPolicyImportErrorDao fetPolicyImportErrorDao) {
		this.fetPolicyImportErrorDao = fetPolicyImportErrorDao;
	}
}
