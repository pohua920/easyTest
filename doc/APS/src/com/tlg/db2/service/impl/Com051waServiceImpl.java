package com.tlg.db2.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.db2.dao.Com051waDao;
import com.tlg.db2.entity.Com051wa;
import com.tlg.db2.service.Com051waService;
import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "db2TransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class Com051waServiceImpl implements Com051waService{

	private Com051waDao com051waDao;

	@Override
	public Result findUnsendCom051waData() throws SystemException, Exception {
		Result result = new Result();
		List<Com051wa> searchResult = com051waDao.selectByDistinctIdno();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countCom051wa(Map params) throws SystemException, Exception {
		return com051waDao.count(params);
	}

	@Override
	public Result findCom051waByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = com051waDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Com051wa> searchResult = com051waDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCom051waByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Com051wa> searchResult = com051waDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result updateCom051wa(Com051wa com051wa) throws SystemException, Exception {

		if (com051wa == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(com051waDao.isUnique(com051wa)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = com051waDao.update(com051wa);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(com051wa);
		return result;
	}
	
	@Override
	public Result updateCom051waForWa60(Com051wa com051wa) throws SystemException, Exception {

		if (com051wa == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		boolean status = com051waDao.updateForWA60(com051wa);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(com051wa);
		return result;
	}
	/**
	 * 此方法專門清空某時間下的WB60為空白
	 */
	@Override
	public Result updateCom051waForWa60(Map params) throws SystemException, Exception {

		if (params == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if (params.size() == 0) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		boolean status = com051waDao.clearForWA60(params);
//		if (!status) {
//			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
//		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}

	@Override
	public Result insertCom051wa(Com051wa com051wa) throws SystemException, Exception {

		if (com051wa == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!com051waDao.isUnique(com051wa)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		com051waDao.insert(com051wa);
		
		if(com051waDao.isUnique(com051wa)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(com051wa);
		return result;
	}

	public Com051waDao getCom051waDao() {
		return com051waDao;
	}

	public void setCom051waDao(Com051waDao com051waDao) {
		this.com051waDao = com051waDao;
	}
}
