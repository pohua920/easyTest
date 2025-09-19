package com.tlg.dms.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.dms.dao.PrpdexchDao;
import com.tlg.dms.entity.Prpdexch;
import com.tlg.dms.service.PrpdexchService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "dmsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpdexchServiceImpl implements PrpdexchService{

	private PrpdexchDao prpdexchDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countPrpdexch(Map params) throws SystemException, Exception {
		return prpdexchDao.count(params);
	}
	
	@Override
	public Result findPrpdexchByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = prpdexchDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Prpdexch> searchResult = prpdexchDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findPrpdexchByParams(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		List<Prpdexch> searchResult = prpdexchDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updatePrpdexch(Prpdexch prpdexch) throws SystemException, Exception {
		if (prpdexch == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = prpdexchDao.update(prpdexch);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(prpdexch);
		return result;
	}

	@Override
	public Result insertPrpdexch(Prpdexch prpdexch) throws SystemException, Exception {
		if (prpdexch == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		prpdexchDao.insert(prpdexch);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(prpdexch);
		return result;
	}

	@Override
	public Result removePrpdexch(Prpdexch prpdexch) throws SystemException, Exception {
		if (prpdexch == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = prpdexchDao.remove(prpdexch);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result findPrpdexchByUK(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Prpdexch searchResult = prpdexchDao.findByUK(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public PrpdexchDao getPrpdexchDao() {
		return prpdexchDao;
	}

	public void setPrpdexchDao(PrpdexchDao prpdexchDao) {
		this.prpdexchDao = prpdexchDao;
	}

}
