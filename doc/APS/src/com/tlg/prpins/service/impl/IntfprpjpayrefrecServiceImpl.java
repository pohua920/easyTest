package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.IntfprpjpayrefrecDao;
import com.tlg.prpins.entity.Intfprpjpayrefrec;
import com.tlg.prpins.service.IntfprpjpayrefrecService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class IntfprpjpayrefrecServiceImpl implements IntfprpjpayrefrecService{

	private IntfprpjpayrefrecDao intfprpjpayrefrecDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countIntfprpjpayrefrec(Map params) throws SystemException, Exception {
		return intfprpjpayrefrecDao.count(params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findIntfprpjpayrefrecByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Intfprpjpayrefrec> searchResult = intfprpjpayrefrecDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public IntfprpjpayrefrecDao getIntfprpjpayrefrecDao() {
		return intfprpjpayrefrecDao;
	}

	public void setIntfprpjpayrefrecDao(IntfprpjpayrefrecDao intfprpjpayrefrecDao) {
		this.intfprpjpayrefrecDao = intfprpjpayrefrecDao;
	}

}
