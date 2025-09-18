package com.tlg.db2.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.db2.dao.Com910wbDao;
import com.tlg.db2.entity.Com910wb;
import com.tlg.db2.service.Com910wbService;
import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/**
 *
 */
@Transactional(value = "db2TransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class Com910wbServiceImpl implements Com910wbService{

	private Com910wbDao com910wbDao;

	@Override
	public int countCom910wb(Map params) throws SystemException, Exception {
		return com910wbDao.count(params);
	}

	@Override
	public Result findCom910wbByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = com910wbDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Com910wb> searchResult = com910wbDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCom910wbByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Com910wb> searchResult = com910wbDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCom910wbByUK(String businessNo) throws SystemException,Exception {

		if (StringUtil.isSpace(businessNo)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map params = new HashMap();
		params.put("wa02", businessNo);
		Com910wb persisted = com910wbDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}


	public Com910wbDao getCom910wbDao() {
		return com910wbDao;
	}

	public void setCom910wbDao(Com910wbDao com910wbDao) {
		this.com910wbDao = com910wbDao;
	}
}
