package com.tlg.xchg.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.xchg.dao.CtbcPetWs10704Dao;
import com.tlg.xchg.entity.CtbcPetWs10704;
import com.tlg.xchg.service.CtbcPetWs10704Service;

@Transactional(value="xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class CtbcPetWs10704ServiceImpl implements CtbcPetWs10704Service{

	private CtbcPetWs10704Dao ctbcPetWs10704Dao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findCtbcPetWs10704ByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<CtbcPetWs10704> searchResult = ctbcPetWs10704Dao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int countCtbcPetWs10704(Map params) throws SystemException, Exception {
		return ctbcPetWs10704Dao.count(params);
	}

	@Override
	public Result updateCtbcPetWs10704(CtbcPetWs10704 ctbcPetWs10704) throws SystemException, Exception {
		if (ctbcPetWs10704 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = ctbcPetWs10704Dao.update(ctbcPetWs10704);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(ctbcPetWs10704);
		return result;
	}

	public CtbcPetWs10704Dao getCtbcPetWs10704Dao() {
		return ctbcPetWs10704Dao;
	}

	public void setCtbcPetWs10704Dao(CtbcPetWs10704Dao ctbcPetWs10704Dao) {
		this.ctbcPetWs10704Dao = ctbcPetWs10704Dao;
	}


}
