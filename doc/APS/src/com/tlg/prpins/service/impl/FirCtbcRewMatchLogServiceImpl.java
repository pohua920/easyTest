package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcRewMatchLogDao;
import com.tlg.prpins.entity.FirCtbcRewMatchLog;
import com.tlg.prpins.service.FirCtbcRewMatchLogService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcRewMatchLogServiceImpl implements FirCtbcRewMatchLogService{

	private FirCtbcRewMatchLogDao firCtbcRewMatchLogDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcRewMatchLogByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcRewMatchLog> searchResult = firCtbcRewMatchLogDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirCtbcRewMatchLog(FirCtbcRewMatchLog firCtbcRewMatchLog) throws SystemException, Exception {
		if (firCtbcRewMatchLog == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firCtbcRewMatchLogDao.update(firCtbcRewMatchLog);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firCtbcRewMatchLog);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result insertFirCtbcRewMatchLog(FirCtbcRewMatchLog firCtbcRewMatchLog) throws SystemException, Exception {
		if (firCtbcRewMatchLog == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
//		Map params = new HashMap();
//		params.put("batchNo",firCtbcRewMatchLog.getBatchNo());
//		params.put("batchSeq",firCtbcRewMatchLog.getBatchSeq());
//		params.put("filename",firCtbcRewMatchLog.getFilename());
//		params.put("fkOrderSeq",firCtbcRewMatchLog.getFkOrderSeq());
//		int count = firCtbcRewMatchLogDao.count(params);
//		if(count > 0) {
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}

		BigDecimal oid = firCtbcRewMatchLogDao.insert(firCtbcRewMatchLog);
//		if(null == oid  || 0L ==oid.longValue()){
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}else {
//			firCtbcRewMatchLog.setMatchOid(oid);
//		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcRewMatchLog);
		return result;
	}

	@Override
	public Result removeFirCtbcRewMatchLog(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public BigDecimal getOid() throws SystemException, Exception {
		return firCtbcRewMatchLogDao.getOid();
	}

	public FirCtbcRewMatchLogDao getFirCtbcRewMatchLogDao() {
		return firCtbcRewMatchLogDao;
	}

	public void setFirCtbcRewMatchLogDao(FirCtbcRewMatchLogDao firCtbcRewMatchLogDao) {
		this.firCtbcRewMatchLogDao = firCtbcRewMatchLogDao;
	}

}
