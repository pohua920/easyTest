package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpcplanDao;
import com.tlg.prpins.entity.Prpcplan;
import com.tlg.prpins.service.PrpcplanService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpcplanServiceImpl implements PrpcplanService{

	private PrpcplanDao prpcplanDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countPrpcplan(Map params) throws SystemException, Exception {
		return prpcplanDao.count(params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findPrpcplanByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Prpcplan> searchResult = prpcplanDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public PrpcplanDao getPrpcplanDao() {
		return prpcplanDao;
	}

	public void setPrpcplanDao(PrpcplanDao prpcplanDao) {
		this.prpcplanDao = prpcplanDao;
	}

}
