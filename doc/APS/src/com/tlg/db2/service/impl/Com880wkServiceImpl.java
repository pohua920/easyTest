package com.tlg.db2.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.db2.dao.Com880wkDao;
import com.tlg.db2.entity.Com880wk;
import com.tlg.db2.service.Com880wkService;
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
@Transactional(value = "db2TransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class Com880wkServiceImpl implements Com880wkService{

	private Com880wkDao com880wkDao;

	@Override
	public int countCom880wk(Map params) throws SystemException, Exception {
		return com880wkDao.count(params);
	}

	@Override
	public Result findCom880wkByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = com880wkDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Com880wk> searchResult = com880wkDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCom880wkByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Com880wk> searchResult = com880wkDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCom880wkByUK(String businessNo) throws SystemException,Exception {

		if (StringUtil.isSpace(businessNo)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map params = new HashMap();
		params.put("wa02", businessNo);
		Com880wk persisted = com880wkDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateCom880wkForBatch(Com880wk com880wk) throws SystemException, Exception {

		if (com880wk == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
//		if(com880wkDao.isUnique(com880wk)) {
//			throw new SystemException(Constants.DATA_NOT_EXIST);  
//		}
		
		boolean status = com880wkDao.updateForBatch(com880wk);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(com880wk);
		return result;
	}
	
	@Override
	public Result findUnsendCom880wkData() throws SystemException, Exception {
		Result result = new Result();
		List<Com880wk> searchResult = com880wkDao.selectByUnsend();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}


	public Com880wkDao getCom880wkDao() {
		return com880wkDao;
	}

	public void setCom880wkDao(Com880wkDao com880wkDao) {
		this.com880wkDao = com880wkDao;
	}
}
