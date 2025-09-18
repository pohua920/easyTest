package com.tlg.db2.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.db2.dao.Com051weDao;
import com.tlg.db2.entity.Com051we;
import com.tlg.db2.service.Com051weService;
import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "db2TransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class Com051weServiceImpl implements Com051weService{

	private Com051weDao com051weDao;

	@Override
	public Result findUnsendCom051weData() throws SystemException, Exception {
		Result result = new Result();
		List<Com051we> searchResult = com051weDao.selectByDistinctIdno();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countCom051we(Map params) throws SystemException, Exception {
		return com051weDao.count(params);
	}

	@Override
	public Result findCom051weByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = com051weDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Com051we> searchResult = com051weDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCom051weByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Com051we> searchResult = com051weDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result updateCom051we(Com051we com051we) throws SystemException, Exception {

		if (com051we == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(com051weDao.isUnique(com051we)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = com051weDao.update(com051we);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(com051we);
		return result;
	}
	
	@Override
	public Result updateCom051weForWe20(Com051we com051we) throws SystemException, Exception {

		if (com051we == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		boolean status = com051weDao.updateForWE20(com051we);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(com051we);
		return result;
	}
	/**
	 * 此方法專門清空某時間下的WB20為空白
	 */
	@Override
	public Result updateCom051weForWe20(Map params) throws SystemException, Exception {

		if (params == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if (params.size() == 0) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		boolean status = com051weDao.clearForWE20(params);
//		if (!status) {
//			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
//		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}

	@Override
	public Result insertCom051we(Com051we com051we) throws SystemException, Exception {

		if (com051we == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!com051weDao.isUnique(com051we)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		com051weDao.insert(com051we);
		
		if(com051weDao.isUnique(com051we)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(com051we);
		return result;
	}

	public Com051weDao getCom051weDao() {
		return com051weDao;
	}

	public void setCom051weDao(Com051weDao com051weDao) {
		this.com051weDao = com051weDao;
	}
}
