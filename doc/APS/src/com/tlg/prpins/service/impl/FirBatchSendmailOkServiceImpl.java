package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirBatchSendmailOkDao;
import com.tlg.prpins.entity.FirBatchSendmailOk;
import com.tlg.prpins.service.FirBatchSendmailOkService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirBatchSendmailOkServiceImpl implements FirBatchSendmailOkService{

	private FirBatchSendmailOkDao firBatchSendmailOkDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countFirBatchSendmailOk(Map params) throws SystemException, Exception {
		return firBatchSendmailOkDao.count(params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Result findFirBatchSendmailOkByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firBatchSendmailOkDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirBatchSendmailOk> searchResult = firBatchSendmailOkDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirBatchSendmailOkByParams(Map params) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Result updateFirBatchSendmailOk(FirBatchSendmailOk firBatchSendmailOk) throws SystemException, Exception {
		if (firBatchSendmailOk == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firBatchSendmailOkDao.update(firBatchSendmailOk);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firBatchSendmailOk);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result insertFirBatchSendmailOk(FirBatchSendmailOk firBatchSendmailOk) throws SystemException, Exception {
		if (firBatchSendmailOk == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		firBatchSendmailOkDao.insert(firBatchSendmailOk);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firBatchSendmailOk);
		return result;
	}

	@Override
	public Result removeFirBatchSendmailOk(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Result findFirBatchSendmailOkByOid(BigDecimal oid) throws SystemException, Exception {
		Result result = new Result();
		if (null == oid) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		FirBatchSendmailOk searchResult = firBatchSendmailOkDao.findByOid(oid);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public FirBatchSendmailOkDao getFirBatchSendmailOkDao() {
		return firBatchSendmailOkDao;
	}

	public void setFirBatchSendmailOkDao(FirBatchSendmailOkDao firBatchSendmailOkDao) {
		this.firBatchSendmailOkDao = firBatchSendmailOkDao;
	}

}
