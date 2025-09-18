package com.tlg.db2.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.db2.dao.Com051wcDao;
import com.tlg.db2.entity.Com051wc;
import com.tlg.db2.service.Com051wcService;
import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "db2TransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class Com051wcServiceImpl implements Com051wcService{

	private Com051wcDao com051wcDao;

	@Override
	public int countCom051wc(Map params) throws SystemException, Exception {
		return com051wcDao.count(params);
	}

	@Override
	public Result findCom051wcByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = com051wcDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Com051wc> searchResult = com051wcDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCom051wcByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Com051wc> searchResult = com051wcDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result updateCom051wc(Com051wc com051wc) throws SystemException, Exception {

		if (com051wc == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(com051wcDao.isUnique(com051wc)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = com051wcDao.update(com051wc);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(com051wc);
		return result;
	}

	@Override
	public Result insertCom051wc(Com051wc com051wc) throws SystemException, Exception {

		if (com051wc == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!com051wcDao.isUnique(com051wc)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		com051wcDao.insert(com051wc);
		
		if(com051wcDao.isUnique(com051wc)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(com051wc);
		return result;
	}

	public Com051wcDao getCom051wcDao() {
		return com051wcDao;
	}

	public void setCom051wcDao(Com051wcDao com051wcDao) {
		this.com051wcDao = com051wcDao;
	}
}
