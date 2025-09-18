package com.tlg.db2.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.db2.dao.Com051wbDao;
import com.tlg.db2.entity.Com051wb;
import com.tlg.db2.service.Com051wbService;
import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "db2TransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class Com051wbServiceImpl implements Com051wbService{

	private Com051wbDao com051wbDao;

	@Override
	public Result findUnsendCom051wbData() throws SystemException, Exception {
		Result result = new Result();
		List<Com051wb> searchResult = com051wbDao.selectByDistinctIdno();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countCom051wb(Map params) throws SystemException, Exception {
		return com051wbDao.count(params);
	}

	@Override
	public Result findCom051wbByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = com051wbDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Com051wb> searchResult = com051wbDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCom051wbByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Com051wb> searchResult = com051wbDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result updateCom051wb(Com051wb com051wb) throws SystemException, Exception {

		if (com051wb == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(com051wbDao.isUnique(com051wb)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = com051wbDao.update(com051wb);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(com051wb);
		return result;
	}
	
	@Override
	public Result updateCom051wbForWb60(Com051wb com051wb) throws SystemException, Exception {

		if (com051wb == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		boolean status = com051wbDao.updateForWB60(com051wb);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(com051wb);
		return result;
	}
	
	/**
	 * 此方法專門清空某時間下的WB60為空白
	 */
	@Override
	public Result updateCom051wbForWb60(Map params) throws SystemException, Exception {

		if (params == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if (params.size() == 0) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		
		boolean status = com051wbDao.clearForWB60(params);
//		if (!status) {
//			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
//		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}

	@Override
	public Result insertCom051wb(Com051wb com051wb) throws SystemException, Exception {

		if (com051wb == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!com051wbDao.isUnique(com051wb)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		com051wbDao.insert(com051wb);
		
		if(com051wbDao.isUnique(com051wb)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(com051wb);
		return result;
	}

	public Com051wbDao getCom051wbDao() {
		return com051wbDao;
	}

	public void setCom051wbDao(Com051wbDao com051wbDao) {
		this.com051wbDao = com051wbDao;
	}
}
