package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrptmainDao;
import com.tlg.prpins.entity.Prptmain;
import com.tlg.prpins.service.PrptmainService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrptmainServiceImpl implements PrptmainService{

	private PrptmainDao prptmainDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countPrptmain(Map params) throws SystemException, Exception {
		return prptmainDao.count(params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findPrptmainByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Prptmain> searchResult = prptmainDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public PrptmainDao getPrptmainDao() {
		return prptmainDao;
	}

	public void setPrptmainDao(PrptmainDao prptmainDao) {
		this.prptmainDao = prptmainDao;
	}

}
