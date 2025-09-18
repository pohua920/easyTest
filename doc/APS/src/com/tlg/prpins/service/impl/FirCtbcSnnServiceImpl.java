package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcSnnDao;
import com.tlg.prpins.entity.FirCtbcSnn;
import com.tlg.prpins.service.FirCtbcSnnService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcSnnServiceImpl implements FirCtbcSnnService{

	private FirCtbcSnnDao firCtbcSnnDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcSnnByParams(Map params) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Result updateFirCtbcSnn(FirCtbcSnn firCtbcSnn) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result insertFirCtbcSnn(FirCtbcSnn firCtbcSnn) throws SystemException, Exception {
		if (firCtbcSnn == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
//		Map params = new HashMap();
//		params.put("batchNo",firCtbcSnn.getBatchNo());
//		params.put("batchSeq",firCtbcSnn.getBatchSeq());
//		params.put("filename",firCtbcSnn.getFilename());
//		params.put("fkOrderSeq",firCtbcSnn.getFkOrderSeq());
//		int count = firCtbcSnnDao.count(params);
//		if(count > 0) {
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}

		BigDecimal oid = firCtbcSnnDao.insert(firCtbcSnn);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			firCtbcSnn.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcSnn);
		return result;
	}

	@Override
	public Result removeFirCtbcSnn(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public FirCtbcSnnDao getFirCtbcSnnDao() {
		return firCtbcSnnDao;
	}

	public void setFirCtbcSnnDao(FirCtbcSnnDao firCtbcSnnDao) {
		this.firCtbcSnnDao = firCtbcSnnDao;
	}

}
