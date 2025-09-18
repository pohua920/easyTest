package com.tlg.db2.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.db2.dao.Com704waDao;
import com.tlg.db2.entity.Com704wa;
import com.tlg.db2.service.Com704waService;
import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "db2TransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class Com704waServiceImpl implements Com704waService{

	private Com704waDao com704waDao;

	@Override
	public int countCom704wa(Map params) throws SystemException, Exception {
		return com704waDao.count(params);
	}

	@Override
	public Result findCom704waByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = com704waDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Com704wa> searchResult = com704waDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCom704waByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Com704wa> searchResult = com704waDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCom704waByUK(String businessNo) throws SystemException,Exception {

		if (StringUtil.isSpace(businessNo)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map params = new HashMap();
		params.put("wa02", businessNo);
		Com704wa persisted = com704waDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateCom704wa(Com704wa com704wa) throws SystemException, Exception {

		if (com704wa == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(com704waDao.isUnique(com704wa)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = com704waDao.update(com704wa);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(com704wa);
		return result;
	}

	@Override
	public Result insertCom704wa(Com704wa com704wa) throws SystemException, Exception {

		if (com704wa == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!com704waDao.isUnique(com704wa)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		com704waDao.insert(com704wa);
		
		if(com704waDao.isUnique(com704wa)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(com704wa);
		return result;
	}

	public Com704waDao getCom704waDao() {
		return com704waDao;
	}

	public void setCom704waDao(Com704waDao com704waDao) {
		this.com704waDao = com704waDao;
	}
}
