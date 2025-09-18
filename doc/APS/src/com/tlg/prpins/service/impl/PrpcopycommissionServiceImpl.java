package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpcopycommissionDao;
import com.tlg.prpins.entity.Prpcopycommission;
import com.tlg.prpins.service.PrpcopycommissionService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpcopycommissionServiceImpl implements PrpcopycommissionService{

	private PrpcopycommissionDao prpcopycommissionDao;

	
	@SuppressWarnings("rawtypes")
	@Override
	public Result findPrpcopycommissionByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Prpcopycommission> searchResult = prpcopycommissionDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}


	public PrpcopycommissionDao getPrpcopycommissionDao() {
		return prpcopycommissionDao;
	}

	public void setPrpcopycommissionDao(PrpcopycommissionDao prpcopycommissionDao) {
		this.prpcopycommissionDao = prpcopycommissionDao;
	}

}
