package com.tlg.db2.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.db2.dao.Com704wbDao;
import com.tlg.db2.entity.Com704wb;
import com.tlg.db2.service.Com704wbService;
import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "db2TransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class Com704wbServiceImpl implements Com704wbService{

	private Com704wbDao com704wbDao;

	@Override
	public int countCom704wb(Map params) throws SystemException, Exception {
		return com704wbDao.count(params);
	}

	@Override
	public Result findCom704wbByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = com704wbDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Com704wb> searchResult = com704wbDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCom704wbByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Com704wb> searchResult = com704wbDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCom704wbByUK(String businessNo, String serialNo) throws SystemException,Exception {

		if (StringUtil.isSpace(businessNo) || StringUtil.isSpace(serialNo)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map params = new HashMap();
		params.put("wb01", businessNo);
		params.put("wb02", serialNo);
		Com704wb persisted = com704wbDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateCom704wb(Com704wb com704wb) throws SystemException, Exception {

		if (com704wb == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(com704wbDao.isUnique(com704wb)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = com704wbDao.update(com704wb);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(com704wb);
		return result;
	}

	@Override
	public Result insertCom704wb(Com704wb com704wb) throws SystemException, Exception {

		if (com704wb == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!com704wbDao.isUnique(com704wb)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		com704wbDao.insert(com704wb);
		
		if(com704wbDao.isUnique(com704wb)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(com704wb);
		return result;
	}

	public Com704wbDao getCom704wbDao() {
		return com704wbDao;
	}

	public void setCom704wbDao(Com704wbDao com704wbDao) {
		this.com704wbDao = com704wbDao;
	}
}
