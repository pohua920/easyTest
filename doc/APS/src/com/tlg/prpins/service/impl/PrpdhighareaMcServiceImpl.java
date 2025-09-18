package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpdhighareaMcDao;
import com.tlg.prpins.entity.PrpdhighareaMc;
import com.tlg.prpins.service.PrpdhighareaMcService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpdhighareaMcServiceImpl implements PrpdhighareaMcService{
	/* mantis：MAR0037，處理人員：BJ085，需求單編號：MAR0037 高風險地區新增維護檔 start*/

	private PrpdhighareaMcDao prpdhighareaMcDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countPrpdhighareaMc(Map params) throws SystemException, Exception {
		return prpdhighareaMcDao.count(params);
	}
	
	@Override
	public Result findPrpdhighareaMcByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = prpdhighareaMcDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<PrpdhighareaMc> searchResult = prpdhighareaMcDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findPrpdhighareaMcByParams(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		List<PrpdhighareaMc> searchResult = prpdhighareaMcDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updatePrpdhighareaMc(PrpdhighareaMc prpdhighareaMc) throws SystemException, Exception {
		if (prpdhighareaMc == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = prpdhighareaMcDao.update(prpdhighareaMc);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(prpdhighareaMc);
		return result;
	}

	@Override
	public Result insertPrpdhighareaMc(PrpdhighareaMc prpdhighareaMc) throws SystemException, Exception {
		if (prpdhighareaMc == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		prpdhighareaMcDao.insert(prpdhighareaMc);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(prpdhighareaMc);
		return result;
	}

	@Override
	public Result removePrpdhighareaMc(PrpdhighareaMc prpdhighareaMc) throws SystemException, Exception {
		if (prpdhighareaMc == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = prpdhighareaMcDao.remove(prpdhighareaMc);
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
	public Result findPrpdhighareaMcByUK(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		PrpdhighareaMc searchResult = prpdhighareaMcDao.findByUK(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public PrpdhighareaMcDao getPrpdhighareaMcDao() {
		return prpdhighareaMcDao;
	}

	public void setPrpdhighareaMcDao(PrpdhighareaMcDao prpdhighareaMcDao) {
		this.prpdhighareaMcDao = prpdhighareaMcDao;
	}
}
