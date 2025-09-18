package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirTmpDatacheckDao;
import com.tlg.prpins.entity.FirTmpDatacheck;
import com.tlg.prpins.service.FirTmpDatacheckService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirTmpDatacheckServiceImpl implements FirTmpDatacheckService{
	/* mantis：FIR0225，處理人員：BJ085，需求單編號：FIR0225 稽核議題調整-過往資料檢核 start */
	private FirTmpDatacheckDao firTmpDatacheckDao;

	@Override
	public int countFirTmpDatacheck(Map params) throws SystemException, Exception{
		return firTmpDatacheckDao.count(params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result findFirTmpDatacheckByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirTmpDatacheck> searchResult = firTmpDatacheckDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Result findBatchesFirTmpDatacheckByParams(Map params) throws SystemException, Exception {
		
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirTmpDatacheck> searchResult = firTmpDatacheckDao.findBatchesByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result updateFirTmpDatacheckByOid(FirTmpDatacheck firTmpDatacheck) throws SystemException, Exception {
		
		if (firTmpDatacheck == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firTmpDatacheckDao.update(firTmpDatacheck);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firTmpDatacheck);
		return result;
	}

	public FirTmpDatacheckDao getFirTmpDatacheckDao() {
		return firTmpDatacheckDao;
	}

	public void setFirTmpDatacheckDao(FirTmpDatacheckDao firTmpDatacheckDao) {
		this.firTmpDatacheckDao = firTmpDatacheckDao;
	}
}
