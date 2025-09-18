package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcRewDontnoticeDao;
import com.tlg.prpins.entity.FirCtbcRewDontnotice;
import com.tlg.prpins.service.FirCtbcRewDontnoticeService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcRewDontnoticeServiceImpl implements FirCtbcRewDontnoticeService{

	private FirCtbcRewDontnoticeDao firCtbcRewDontnoticeDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcRewDontnoticeByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcRewDontnotice> searchResult = firCtbcRewDontnoticeDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirCtbcRewDontnotice(FirCtbcRewDontnotice firCtbcRewDontnotice) throws SystemException, Exception {
		if (firCtbcRewDontnotice == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firCtbcRewDontnoticeDao.update(firCtbcRewDontnotice);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firCtbcRewDontnotice);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result insertFirCtbcRewDontnotice(FirCtbcRewDontnotice firCtbcRewDontnotice) throws SystemException, Exception {
		if (firCtbcRewDontnotice == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
//		Map params = new HashMap();
//		params.put("batchNo",firCtbcRewDontnotice.getBatchNo());
//		params.put("batchSeq",firCtbcRewDontnotice.getBatchSeq());
//		params.put("filename",firCtbcRewDontnotice.getFilename());
//		params.put("fkOrderSeq",firCtbcRewDontnotice.getFkOrderSeq());
//		int count = firCtbcRewDontnoticeDao.count(params);
//		if(count > 0) {
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}

		firCtbcRewDontnoticeDao.insert(firCtbcRewDontnotice);
//		if(null == oid  || 0L ==oid.longValue()){
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}else {
//			firCtbcRewDontnotice.setBatchOid(oid);
//		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcRewDontnotice);
		return result;
	}

	@Override
	public Result removeFirCtbcRewDontnotice(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public FirCtbcRewDontnoticeDao getFirCtbcRewDontnoticeDao() {
		return firCtbcRewDontnoticeDao;
	}

	public void setFirCtbcRewDontnoticeDao(FirCtbcRewDontnoticeDao firCtbcRewDontnoticeDao) {
		this.firCtbcRewDontnoticeDao = firCtbcRewDontnoticeDao;
	}

}
