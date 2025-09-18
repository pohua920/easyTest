package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcRewNoticeBatchDao;
import com.tlg.prpins.entity.FirCtbcBatchMain;
import com.tlg.prpins.entity.FirCtbcRewNoticeBatch;
import com.tlg.prpins.service.FirCtbcRewNoticeBatchService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcRewNoticeBatchServiceImpl implements FirCtbcRewNoticeBatchService{

	private FirCtbcRewNoticeBatchDao firCtbcRewNoticeBatchDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countFirCtbcRewNoticeBatch(Map params) throws SystemException, Exception {
		return firCtbcRewNoticeBatchDao.count(params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcRewNoticeBatchByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcRewNoticeBatch> searchResult = firCtbcRewNoticeBatchDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirCtbcRewNoticeBatch(FirCtbcRewNoticeBatch firCtbcRewNoticeBatch) throws SystemException, Exception {
		if (firCtbcRewNoticeBatch == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firCtbcRewNoticeBatchDao.update(firCtbcRewNoticeBatch);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firCtbcRewNoticeBatch);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result insertFirCtbcRewNoticeBatch(FirCtbcRewNoticeBatch firCtbcRewNoticeBatch) throws SystemException, Exception {
		if (firCtbcRewNoticeBatch == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
//		Map params = new HashMap();
//		params.put("batchNo",firCtbcRewNoticeBatch.getBatchNo());
//		params.put("batchSeq",firCtbcRewNoticeBatch.getBatchSeq());
//		params.put("filename",firCtbcRewNoticeBatch.getFilename());
//		params.put("fkOrderSeq",firCtbcRewNoticeBatch.getFkOrderSeq());
//		int count = firCtbcRewNoticeBatchDao.count(params);
//		if(count > 0) {
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}

		BigDecimal oid = firCtbcRewNoticeBatchDao.insert(firCtbcRewNoticeBatch);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			firCtbcRewNoticeBatch.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcRewNoticeBatch);
		return result;
	}

	@Override
	public Result removeFirCtbcRewNoticeBatch(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Result findFirCtbcRewNoticeBatchByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firCtbcRewNoticeBatchDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirCtbcRewNoticeBatch> searchResult = firCtbcRewNoticeBatchDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public FirCtbcRewNoticeBatchDao getFirCtbcRewNoticeBatchDao() {
		return firCtbcRewNoticeBatchDao;
	}

	public void setFirCtbcRewNoticeBatchDao(FirCtbcRewNoticeBatchDao firCtbcRewNoticeBatchDao) {
		this.firCtbcRewNoticeBatchDao = firCtbcRewNoticeBatchDao;
	}

}
