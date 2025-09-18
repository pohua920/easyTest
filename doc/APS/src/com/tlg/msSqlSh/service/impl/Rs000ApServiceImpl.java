package com.tlg.msSqlSh.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlSh.dao.Rs000ApDao;
import com.tlg.msSqlSh.entity.Rs000Ap;
import com.tlg.msSqlSh.service.Rs000ApService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "msSqlShTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class Rs000ApServiceImpl implements Rs000ApService{

	private Rs000ApDao rs000ApDao;

	@Override
	public int countRs000Ap(Map params) throws SystemException, Exception {
		return rs000ApDao.count(params);
	}

	@Override
	public Result findRs000ApByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = rs000ApDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Rs000Ap> searchResult = rs000ApDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findRs000ApByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Rs000Ap> searchResult = rs000ApDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findRs000ApByUK(String ap01, String ap00) throws SystemException,Exception {

		if (StringUtil.isSpace(ap01) || StringUtil.isSpace(ap00)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map params = new HashMap();
		params.put("ap00", ap00);
		params.put("ap01", ap01);
		Rs000Ap persisted = rs000ApDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateRs000Ap(Rs000Ap rs000Ap) throws SystemException, Exception {

		if (rs000Ap == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(rs000ApDao.isUnique(rs000Ap)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = rs000ApDao.update(rs000Ap);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(rs000Ap);
		return result;
	}

	@Override
	public Result insertRs000Ap(Rs000Ap rs000Ap) throws SystemException, Exception {

		if (rs000Ap == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!rs000ApDao.isUnique(rs000Ap)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		rs000ApDao.insert(rs000Ap);
		
		if(rs000ApDao.isUnique(rs000Ap)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(rs000Ap);
		return result;
	}

	@Override
	public Result removeRs000Ap(String ap00, String ap01) throws SystemException, Exception {

		if (StringUtil.isSpace(ap01) || StringUtil.isSpace(ap00)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("ap00", ap00);
		params.put("ap01", ap01);
		Rs000Ap persisted = rs000ApDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = rs000ApDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public Rs000ApDao getRs000ApDao() {
		return rs000ApDao;
	}

	public void setRs000ApDao(Rs000ApDao rs000ApDao) {
		this.rs000ApDao = rs000ApDao;
	}
}
