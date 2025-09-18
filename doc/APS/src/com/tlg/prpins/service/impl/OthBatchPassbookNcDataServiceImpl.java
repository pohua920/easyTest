package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.OthBatchPassbookNcDataDao;
import com.tlg.prpins.entity.OthBatchPassbookNcData;
import com.tlg.prpins.service.OthBatchPassbookNcDataService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：OTH0131，處理人員：BJ085，需求單編號：OTH0131 保發中心-保單存摺各險寫入中介Table作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class OthBatchPassbookNcDataServiceImpl implements OthBatchPassbookNcDataService{

	private OthBatchPassbookNcDataDao othBatchPassbookNcDataDao;
	
	@Override
	public Result findOthBatchPassbookNcDataByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = othBatchPassbookNcDataDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<OthBatchPassbookNcData> searchResult = othBatchPassbookNcDataDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findOthBatchPassbookNcDataByParams(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		List<OthBatchPassbookNcData> searchResult = othBatchPassbookNcDataDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result findOthBatchPassbookNcDataByUK(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		OthBatchPassbookNcData searchResult = othBatchPassbookNcDataDao.findByUK(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result insertOthBatchPassbookNcData(OthBatchPassbookNcData othBatchPassbookNcData) throws SystemException, Exception {
		if (othBatchPassbookNcData == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		othBatchPassbookNcDataDao.insert(othBatchPassbookNcData);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(othBatchPassbookNcData);
		return result;
	}
	
	@Override
	public Result updateOthBatchPassbookNcData(OthBatchPassbookNcData othBatchPassbookNcData) throws SystemException, Exception {
		if (othBatchPassbookNcData == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = othBatchPassbookNcDataDao.update(othBatchPassbookNcData);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(othBatchPassbookNcData);
		return result;
	}

	@Override
	public Result removeOthBatchPassbookNcData(OthBatchPassbookNcData othBatchPassbookNcData) throws SystemException, Exception {
		if (othBatchPassbookNcData == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = othBatchPassbookNcDataDao.remove(othBatchPassbookNcData);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	public OthBatchPassbookNcDataDao getOthBatchPassbookNcDataDao() {
		return othBatchPassbookNcDataDao;
	}

	public void setOthBatchPassbookNcDataDao(OthBatchPassbookNcDataDao othBatchPassbookNcDataDao) {
		this.othBatchPassbookNcDataDao = othBatchPassbookNcDataDao;
	}
}
