package com.tlg.xchg.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.xchg.dao.RfrcodeDao;
import com.tlg.xchg.entity.Rfrcode;
import com.tlg.xchg.service.RfrcodeService;

@Transactional(value="xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class RfrcodeServiceImpl implements RfrcodeService{

	private RfrcodeDao rfrcodeDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findRfrcodeByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Rfrcode> searchResult = rfrcodeDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public RfrcodeDao getRfrcodeDao() {
		return rfrcodeDao;
	}

	public void setRfrcodeDao(RfrcodeDao rfrcodeDao) {
		this.rfrcodeDao = rfrcodeDao;
	}

}
