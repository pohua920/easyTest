package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpccommissionDao;
import com.tlg.prpins.entity.Prpccommission;
import com.tlg.prpins.service.PrpccommissionService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpccommissionServiceImpl implements PrpccommissionService{

	private PrpccommissionDao prpccommissionDao;

	
	@SuppressWarnings("rawtypes")
	@Override
	public Result findPrpccommissionByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Prpccommission> searchResult = prpccommissionDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}


	public PrpccommissionDao getPrpccommissionDao() {
		return prpccommissionDao;
	}

	public void setPrpccommissionDao(PrpccommissionDao prpccommissionDao) {
		this.prpccommissionDao = prpccommissionDao;
	}

}
