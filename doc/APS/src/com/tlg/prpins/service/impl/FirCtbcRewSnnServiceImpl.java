package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcRewSnnDao;
import com.tlg.prpins.entity.FirCtbcRewSnn;
import com.tlg.prpins.service.FirCtbcRewSnnService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcRewSnnServiceImpl implements FirCtbcRewSnnService{

	private FirCtbcRewSnnDao firCtbcRewSnnDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcRewSnnByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcRewSnn> searchResult = firCtbcRewSnnDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
	@SuppressWarnings("rawtypes")
	@Override
	public Result findCtbcRewSnnForSameId(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcRewSnn> searchResult = firCtbcRewSnnDao.findCtbcRewSnnForSameId(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result findCtbcRewSnnForSameName(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcRewSnn> searchResult = firCtbcRewSnnDao.findCtbcRewSnnForSameName(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
	
	@Override
	public Result updateFirCtbcRewSnn(FirCtbcRewSnn firCtbcRewSnn) throws SystemException, Exception {
		if (firCtbcRewSnn == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firCtbcRewSnnDao.update(firCtbcRewSnn);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firCtbcRewSnn);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result insertFirCtbcRewSnn(FirCtbcRewSnn firCtbcRewSnn) throws SystemException, Exception {
		if (firCtbcRewSnn == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
//		Map params = new HashMap();
//		params.put("batchNo",firCtbcRewSnn.getBatchNo());
//		params.put("batchSeq",firCtbcRewSnn.getBatchSeq());
//		params.put("filename",firCtbcRewSnn.getFilename());
//		params.put("fkOrderSeq",firCtbcRewSnn.getFkOrderSeq());
//		int count = firCtbcRewSnnDao.count(params);
//		if(count > 0) {
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}

		BigDecimal oid = firCtbcRewSnnDao.insert(firCtbcRewSnn);

		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcRewSnn);
		return result;
	}

	@Override
	public Result removeFirCtbcRewSnn(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public FirCtbcRewSnnDao getFirCtbcRewSnnDao() {
		return firCtbcRewSnnDao;
	}

	public void setFirCtbcRewSnnDao(FirCtbcRewSnnDao firCtbcRewSnnDao) {
		this.firCtbcRewSnnDao = firCtbcRewSnnDao;
	}

}
