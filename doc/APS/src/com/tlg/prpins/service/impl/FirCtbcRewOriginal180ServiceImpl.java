package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcRewOriginal180Dao;
import com.tlg.prpins.entity.FirCtbcRewOriginal180;
import com.tlg.prpins.service.FirCtbcRewOriginal180Service;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcRewOriginal180ServiceImpl implements FirCtbcRewOriginal180Service{

	private FirCtbcRewOriginal180Dao firCtbcRewOriginal180Dao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcRewOriginal180ByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcRewOriginal180> searchResult = firCtbcRewOriginal180Dao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirCtbcRewOriginal180(FirCtbcRewOriginal180 firCtbcRewOriginal180) throws SystemException, Exception {
		if (firCtbcRewOriginal180 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firCtbcRewOriginal180Dao.update(firCtbcRewOriginal180);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firCtbcRewOriginal180);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result insertFirCtbcRewOriginal180(FirCtbcRewOriginal180 firCtbcRewOriginal180) throws SystemException, Exception {
		if (firCtbcRewOriginal180 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
//		Map params = new HashMap();
//		params.put("batchNo",firCtbcRewOriginal180.getBatchNo());
//		params.put("batchSeq",firCtbcRewOriginal180.getBatchSeq());
//		params.put("filename",firCtbcRewOriginal180.getFilename());
//		params.put("fkOrderSeq",firCtbcRewOriginal180.getFkOrderSeq());
//		int count = firCtbcRewOriginal180Dao.count(params);
//		if(count > 0) {
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}

		firCtbcRewOriginal180Dao.insert(firCtbcRewOriginal180);
//		if(null == oid  || 0L ==oid.longValue()){
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}else {
//			firCtbcRewOriginal180.setBatchOid(oid);
//		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcRewOriginal180);
		return result;
	}

	@Override
	public Result removeFirCtbcRewOriginal180(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public FirCtbcRewOriginal180Dao getFirCtbcRewOriginal180Dao() {
		return firCtbcRewOriginal180Dao;
	}

	public void setFirCtbcRewOriginal180Dao(FirCtbcRewOriginal180Dao firCtbcRewOriginal180Dao) {
		this.firCtbcRewOriginal180Dao = firCtbcRewOriginal180Dao;
	}

}
