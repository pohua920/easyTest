package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirBatchLogDao;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.service.FirBatchLogService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirBatchLogServiceImpl implements FirBatchLogService{

	private FirBatchLogDao firBatchLogDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countFirBatchLog(Map params) throws SystemException, Exception {
		return firBatchLogDao.count(params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirBatchLogByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirBatchLog> searchResult = firBatchLogDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirBatchLog(FirBatchLog firBatchLog) throws SystemException, Exception {
		if (firBatchLog == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firBatchLogDao.update(firBatchLog);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firBatchLog);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result insertFirBatchLog(FirBatchLog firBatchLog) throws SystemException, Exception {
		if (firBatchLog == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Map params = new HashMap();
//		params.put("batchNo",firBatchLog.getBatchNo());
		params.put("batchNo","A");
		int count = firBatchLogDao.count(params);
		if(count > 0) {
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}

		BigDecimal oid = firBatchLogDao.insert(firBatchLog);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			firBatchLog.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firBatchLog);
		return result;
	}

	@Override
	public Result removeFirBatchLog(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getLastBatchNo() throws SystemException, Exception {
		String lastBatchNo = firBatchLogDao.selectLastBatchNo();
		return lastBatchNo;
	}

	public FirBatchLogDao getFirBatchLogDao() {
		return firBatchLogDao;
	}

	public void setFirBatchLogDao(FirBatchLogDao firBatchLogDao) {
		this.firBatchLogDao = firBatchLogDao;
	}

}
