package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcRewMatchnameDao;
import com.tlg.prpins.entity.FirCtbcRewMatchname;
import com.tlg.prpins.service.FirCtbcRewMatchnameService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcRewMatchnameServiceImpl implements FirCtbcRewMatchnameService{

	private FirCtbcRewMatchnameDao firCtbcRewMatchnameDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcRewMatchnameByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcRewMatchname> searchResult = firCtbcRewMatchnameDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirCtbcRewMatchname(FirCtbcRewMatchname firCtbcRewMatchname) throws SystemException, Exception {
		if (firCtbcRewMatchname == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firCtbcRewMatchnameDao.update(firCtbcRewMatchname);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firCtbcRewMatchname);
		return result;
	}

	@Override
	public Result insertFirCtbcRewMatchname(FirCtbcRewMatchname firCtbcRewMatchname) throws SystemException, Exception {
		if (firCtbcRewMatchname == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		firCtbcRewMatchnameDao.insert(firCtbcRewMatchname);

		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcRewMatchname);
		return result;
	}

	@Override
	public Result removeFirCtbcRewMatchname(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public FirCtbcRewMatchnameDao getFirCtbcRewMatchnameDao() {
		return firCtbcRewMatchnameDao;
	}

	public void setFirCtbcRewMatchnameDao(FirCtbcRewMatchnameDao firCtbcRewMatchnameDao) {
		this.firCtbcRewMatchnameDao = firCtbcRewMatchnameDao;
	}

}
