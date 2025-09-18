package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcTmpStlDao;
import com.tlg.prpins.entity.FirCtbcTmpStl;
import com.tlg.prpins.service.FirCtbcTmpStlService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcTmpStlServiceImpl implements FirCtbcTmpStlService{

	private FirCtbcTmpStlDao firCtbcTmpStlDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcTmpStlByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcTmpStl> searchResult = firCtbcTmpStlDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirCtbcTmpStl(FirCtbcTmpStl firCtbcTmpStl) throws SystemException, Exception {
		if (firCtbcTmpStl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firCtbcTmpStlDao.update(firCtbcTmpStl);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firCtbcTmpStl);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result insertFirCtbcTmpStl(FirCtbcTmpStl firCtbcTmpStl) throws SystemException, Exception {
		if (firCtbcTmpStl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Map params = new HashMap();
		params.put("batchNo",firCtbcTmpStl.getBatchNo());
		params.put("batchSeq",firCtbcTmpStl.getBatchSeq());
		params.put("filename",firCtbcTmpStl.getFilename());
		params.put("fkOrderSeq",firCtbcTmpStl.getFkOrderSeq());
		int count = firCtbcTmpStlDao.count(params);
		if(count > 0) {
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}

		BigDecimal oid = firCtbcTmpStlDao.insert(firCtbcTmpStl);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			firCtbcTmpStl.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcTmpStl);
		return result;
	}

	@Override
	public Result removeFirCtbcTmpStl(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public FirCtbcTmpStlDao getFirCtbcTmpStlDao() {
		return firCtbcTmpStlDao;
	}

	public void setFirCtbcTmpStlDao(FirCtbcTmpStlDao firCtbcTmpStlDao) {
		this.firCtbcTmpStlDao = firCtbcTmpStlDao;
	}

}
