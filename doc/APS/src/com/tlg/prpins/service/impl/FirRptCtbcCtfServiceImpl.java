package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirRptCtbcCtfDao;
import com.tlg.prpins.entity.FirRptCtbcCtf;
import com.tlg.prpins.service.FirRptCtbcCtfService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirRptCtbcCtfServiceImpl implements FirRptCtbcCtfService{

	private FirRptCtbcCtfDao firRptCtbcCtfDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirRptCtbcCtfByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirRptCtbcCtf> searchResult = firRptCtbcCtfDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirRptCtbcCtf(FirRptCtbcCtf firRptCtbcCtf) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result insertFirRptCtbcCtf(FirRptCtbcCtf firRptCtbcCtf) throws SystemException, Exception {
		if (firRptCtbcCtf == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
//		Map params = new HashMap();
//		params.put("batchNo",FirRptCtbcCtf.getBatchNo());
//		params.put("oBatchNo",FirRptCtbcCtf.getoBatchNo());
//		params.put("oBatchSeq",FirRptCtbcCtf.getoBatchSeq());
//		params.put("fkOrderSeq",FirRptCtbcCtf.getFkOrderSeq());
//		int count = firRptCtbcCtfDao.count(params);
//		if(count > 0) {
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}

		BigDecimal oid = firRptCtbcCtfDao.insert(firRptCtbcCtf);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			firRptCtbcCtf.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firRptCtbcCtf);
		return result;
	}

	@Override
	public Result removeFirRptCtbcCtf(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業 start*/
	@Override
	public Result findFirRptCtbcCtfByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirRptCtbcCtf persisted = firRptCtbcCtfDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}
	/* mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業 end*/

	public FirRptCtbcCtfDao getFirRptCtbcCtfDao() {
		return firRptCtbcCtfDao;
	}

	public void setFirRptCtbcCtfDao(FirRptCtbcCtfDao firRptCtbcCtfDao) {
		this.firRptCtbcCtfDao = firRptCtbcCtfDao;
	}

}
