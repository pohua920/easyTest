package com.tlg.db2.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.db2.dao.Com890wbDao;
import com.tlg.db2.entity.Com890wb;
import com.tlg.db2.service.Com890wbService;
import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/**
 * mantis：OTH0132，處理人員：BI086，需求單編號：OTH0132  保單存摺AS400資料寫入核心中介Table
 *
 */
@Transactional(value = "db2TransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class Com890wbServiceImpl implements Com890wbService{

	private Com890wbDao com890wbDao;

	@Override
	public int countCom890wb(Map params) throws SystemException, Exception {
		return com890wbDao.count(params);
	}

	@Override
	public Result findCom890wbByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = com890wbDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Com890wb> searchResult = com890wbDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCom890wbByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Com890wb> searchResult = com890wbDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCom890wbByUK(String businessNo) throws SystemException,Exception {

		if (StringUtil.isSpace(businessNo)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map params = new HashMap();
		params.put("wa02", businessNo);
		Com890wb persisted = com890wbDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}


	public Com890wbDao getCom890wbDao() {
		return com890wbDao;
	}

	public void setCom890wbDao(Com890wbDao com890wbDao) {
		this.com890wbDao = com890wbDao;
	}
}
