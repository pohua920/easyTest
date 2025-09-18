package com.tlg.prpins.service.impl;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpdclausereportDao;
import com.tlg.prpins.service.PrpdclausereportService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpdclausereportServiceImpl implements PrpdclausereportService{

	private PrpdclausereportDao prpdclausereportDao;

	@Override
	public Result selectForEpolicy(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		String searchResult = prpdclausereportDao.selectForEpolicy(params);
		if (null == searchResult || 0 == searchResult.length()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public PrpdclausereportDao getPrpdclausereportDao() {
		return prpdclausereportDao;
	}

	public void setPrpdclausereportDao(PrpdclausereportDao prpdclausereportDao) {
		this.prpdclausereportDao = prpdclausereportDao;
	}

}
