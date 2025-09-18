package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.HasBatchLogDao;
import com.tlg.prpins.entity.HasBatchLog;
import com.tlg.prpins.service.HasBatchLogService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
@SuppressWarnings("rawtypes")
public class HasBatchLogServiceImpl implements HasBatchLogService{

	private HasBatchLogDao hasBatchLogDao;
	
	
	@Override
	public int countHasBatchLog(Map params) throws SystemException, Exception {
		return hasBatchLogDao.count(params);
	}

	@Override
	public Result findHasBatchLogByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<HasBatchLog> searchResult = hasBatchLogDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateHasBatchLog(HasBatchLog hasBatchLog) throws SystemException, Exception {
		if (hasBatchLog == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = hasBatchLogDao.update(hasBatchLog);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(hasBatchLog);
		return result;
	}

	@SuppressWarnings( "unchecked")
	@Override
	public Result insertHasBatchLog(HasBatchLog hasBatchLog) throws SystemException, Exception {
		if (hasBatchLog == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Map params = new HashMap();
		params.put("batchNo",hasBatchLog.getBatchNo());
		int count = hasBatchLogDao.count(params);
		if(count > 0) {
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}

		BigDecimal oid = hasBatchLogDao.insert(hasBatchLog);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			hasBatchLog.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(hasBatchLog);
		return result;
	}

	@Override
	public Result removeHasBatchLog(BigDecimal oid) throws SystemException, Exception {
		return null;
	}


	public HasBatchLogDao getHasBatchLogDao() {
		return hasBatchLogDao;
	}

	public void setHasBatchLogDao(HasBatchLogDao hasBatchLogDao) {
		this.hasBatchLogDao = hasBatchLogDao;
	}

}
