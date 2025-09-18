package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpdPropStructDao;
import com.tlg.prpins.entity.PrpdPropStruct;
import com.tlg.prpins.service.PrpdPropStructService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpdPropStructServiceImpl implements PrpdPropStructService{

	private PrpdPropStructDao prpdPropStructDao;

	@Override
	public int countPrpdPropStruct(Map params) throws SystemException, Exception {
		return prpdPropStructDao.count(params);
	}

	@Override
	public Result findPrpdPropStructByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = prpdPropStructDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<PrpdPropStruct> searchResult = prpdPropStructDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPrpdPropStructByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<PrpdPropStruct> searchResult = prpdPropStructDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}


	public PrpdPropStructDao getPrpdPropStructDao() {
		return prpdPropStructDao;
	}

	public void setPrpdPropStructDao(PrpdPropStructDao prpdPropStructDao) {
		this.prpdPropStructDao = prpdPropStructDao;
	}
}
