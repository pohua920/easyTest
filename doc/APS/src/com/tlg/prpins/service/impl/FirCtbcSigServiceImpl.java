package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcSigDao;
import com.tlg.prpins.entity.FirCtbcSig;
import com.tlg.prpins.service.FirCtbcSigService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcSigServiceImpl implements FirCtbcSigService{

	private FirCtbcSigDao firCtbcSigDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcSigByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcSig> searchResult = firCtbcSigDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirCtbcSig(FirCtbcSig firCtbcSig) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result insertFirCtbcSig(FirCtbcSig firCtbcSig) throws SystemException, Exception {
		if (firCtbcSig == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
//		Map params = new HashMap();
//		params.put("batchNo",firCtbcSig.getBatchNo());
//		params.put("batchSeq",firCtbcSig.getBatchSeq());
//		params.put("fkOrderSeq",firCtbcSig.getFkOrderSeq());
//		int count = firCtbcSigDao.count(params);
//		if(count > 0) {
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}

		BigDecimal oid = firCtbcSigDao.insert(firCtbcSig);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			firCtbcSig.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcSig);
		return result;
	}
	
	@Override
	public int countFirCtbcSig(Map params) throws SystemException, Exception {
		return firCtbcSigDao.count(params);
	}

	@Override
	public Result removeFirCtbcSig(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public FirCtbcSigDao getFirCtbcSigDao() {
		return firCtbcSigDao;
	}

	public void setFirCtbcSigDao(FirCtbcSigDao firCtbcSigDao) {
		this.firCtbcSigDao = firCtbcSigDao;
	}

}
