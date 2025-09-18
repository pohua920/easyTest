package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpcaddressDao;
import com.tlg.prpins.entity.Prpcaddress;
import com.tlg.prpins.service.PrpcaddressService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpcaddressServiceImpl implements PrpcaddressService{

	private PrpcaddressDao prpcaddressDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findPrpcaddressByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Prpcaddress> searchResult = prpcaddressDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public PrpcaddressDao getPrpcaddressDao() {
		return prpcaddressDao;
	}

	public void setPrpcaddressDao(PrpcaddressDao prpcaddressDao) {
		this.prpcaddressDao = prpcaddressDao;
	}

}
