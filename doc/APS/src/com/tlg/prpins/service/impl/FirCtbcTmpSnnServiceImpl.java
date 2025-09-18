package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcTmpSnnDao;
import com.tlg.prpins.entity.FirCtbcTmpSnn;
import com.tlg.prpins.service.FirCtbcTmpSnnService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcTmpSnnServiceImpl implements FirCtbcTmpSnnService{

	private FirCtbcTmpSnnDao firCtbcTmpSnnDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcTmpSnnByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcTmpSnn> searchResult = firCtbcTmpSnnDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirCtbcTmpSnn(FirCtbcTmpSnn firCtbcTmpSnn) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result insertFirCtbcTmpSnn(FirCtbcTmpSnn firCtbcTmpSnn) throws SystemException, Exception {
		if (firCtbcTmpSnn == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
//		Map params = new HashMap();
//		params.put("batchNo",firCtbcTmpSnn.getBatchNo());
//		params.put("batchSeq",firCtbcTmpSnn.getBatchSeq());
//		params.put("filename",firCtbcTmpSnn.getFilename());
//		params.put("fkOrderSeq",firCtbcTmpSnn.getFkOrderSeq());
//		int count = firCtbcTmpSnnDao.count(params);
//		if(count > 0) {
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}

		BigDecimal oid = firCtbcTmpSnnDao.insert(firCtbcTmpSnn);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			firCtbcTmpSnn.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcTmpSnn);
		return result;
	}

	@Override
	public Result removeFirCtbcTmpSnn(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public FirCtbcTmpSnnDao getFirCtbcTmpSnnDao() {
		return firCtbcTmpSnnDao;
	}

	public void setFirCtbcTmpSnnDao(FirCtbcTmpSnnDao firCtbcTmpSnnDao) {
		this.firCtbcTmpSnnDao = firCtbcTmpSnnDao;
	}

}
