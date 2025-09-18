package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcTmpPblDao;
import com.tlg.prpins.entity.FirCtbcTmpPbl;
import com.tlg.prpins.service.FirCtbcTmpPblService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcTmpPblServiceImpl implements FirCtbcTmpPblService{

	private FirCtbcTmpPblDao firCtbcTmpPblDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcTmpPblByParams(Map params) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Result updateFirCtbcTmpPbl(FirCtbcTmpPbl firCtbcTmpPbl) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result insertFirCtbcTmpPbl(FirCtbcTmpPbl firCtbcTmpPbl) throws SystemException, Exception {
		if (firCtbcTmpPbl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Map params = new HashMap();
		params.put("batchNo",firCtbcTmpPbl.getBatchNo());
		params.put("batchSeq",firCtbcTmpPbl.getBatchSeq());
		params.put("filename",firCtbcTmpPbl.getFilename());
		params.put("fkOrderSeq",firCtbcTmpPbl.getFkOrderSeq());
		int count = firCtbcTmpPblDao.count(params);
		if(count > 0) {
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}

		BigDecimal oid = firCtbcTmpPblDao.insert(firCtbcTmpPbl);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			firCtbcTmpPbl.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcTmpPbl);
		return result;
	}

	@Override
	public Result removeFirCtbcTmpPbl(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public FirCtbcTmpPblDao getFirCtbcTmpPblDao() {
		return firCtbcTmpPblDao;
	}

	public void setFirCtbcTmpPblDao(FirCtbcTmpPblDao firCtbcTmpPblDao) {
		this.firCtbcTmpPblDao = firCtbcTmpPblDao;
	}

}
