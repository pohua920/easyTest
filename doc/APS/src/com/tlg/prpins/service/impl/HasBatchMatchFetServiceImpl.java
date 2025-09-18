package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.HasBatchMatchFetDao;
import com.tlg.prpins.entity.HasBatchMatchFet;
import com.tlg.prpins.service.HasBatchMatchFetService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

/** mantis：HAS0284，處理人員：DP0706，需求單編號：HAS0284_遠傳優化需求-比對同要保人但生日或姓名不同*/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
@SuppressWarnings("rawtypes")
public class HasBatchMatchFetServiceImpl implements HasBatchMatchFetService{
	
	private HasBatchMatchFetDao hasBatchMatchFetDao;
	
	@Override
	public Result findHasBatchMatchFetByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<HasBatchMatchFet> searchResult = hasBatchMatchFetDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public HasBatchMatchFetDao getHasBatchMatchFetDao() {
		return hasBatchMatchFetDao;
	}

	public void setHasBatchMatchFetDao(HasBatchMatchFetDao hasBatchMatchFetDao) {
		this.hasBatchMatchFetDao = hasBatchMatchFetDao;
	}
	

}
