package com.tlg.msSqlAs400.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlAs400.dao.CtbcClmsmsRmDao;
import com.tlg.msSqlAs400.entity.CtbcClmsmsRm;
import com.tlg.msSqlAs400.service.CtbcClmsmsRmService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "msSqlAs400TransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class CtbcClmsmsRmServiceImpl implements CtbcClmsmsRmService{

	private CtbcClmsmsRmDao ctbcClmsmsRmDao;

	@Override
	public int countCtbcClmsmsRm(Map params) throws SystemException, Exception {
		return ctbcClmsmsRmDao.count(params);
	}

	@Override
	public Result findCtbcClmsmsRmByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = ctbcClmsmsRmDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<CtbcClmsmsRm> searchResult = ctbcClmsmsRmDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCtbcClmsmsRmByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<CtbcClmsmsRm> searchResult = ctbcClmsmsRmDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCtbcClmsmsRmByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		CtbcClmsmsRm persisted = ctbcClmsmsRmDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateCtbcClmsmsRm(CtbcClmsmsRm ctbcClmsmsRm) throws SystemException, Exception {

		if (ctbcClmsmsRm == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(ctbcClmsmsRmDao.isUnique(ctbcClmsmsRm)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = ctbcClmsmsRmDao.update(ctbcClmsmsRm);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(ctbcClmsmsRm);
		return result;
	}

	@Override
	public Result insertCtbcClmsmsRm(CtbcClmsmsRm ctbcClmsmsRm) throws SystemException, Exception {

		if (ctbcClmsmsRm == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!ctbcClmsmsRmDao.isUnique(ctbcClmsmsRm)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		ctbcClmsmsRmDao.insert(ctbcClmsmsRm);
		
		if(ctbcClmsmsRmDao.isUnique(ctbcClmsmsRm)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(ctbcClmsmsRm);
		return result;
	}

	@Override
	public Result removeCtbcClmsmsRm(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("transactionId", transactionId);
		CtbcClmsmsRm persisted = ctbcClmsmsRmDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = ctbcClmsmsRmDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public CtbcClmsmsRmDao getCtbcClmsmsRmDao() {
		return ctbcClmsmsRmDao;
	}

	public void setCtbcClmsmsRmDao(CtbcClmsmsRmDao ctbcClmsmsRmDao) {
		this.ctbcClmsmsRmDao = ctbcClmsmsRmDao;
	}
}
