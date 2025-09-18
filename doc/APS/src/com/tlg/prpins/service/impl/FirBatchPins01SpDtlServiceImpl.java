package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirBatchPins01SpDtlDao;
import com.tlg.prpins.entity.FirBatchPins01SpDtl;
import com.tlg.prpins.service.FirBatchPins01SpDtlService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirBatchPins01SpDtlServiceImpl implements FirBatchPins01SpDtlService{

	private FirBatchPins01SpDtlDao firBatchPins01SpDtlDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countFirBatchPins01SpDtl(Map params) throws SystemException, Exception {
		return firBatchPins01SpDtlDao.count(params);
	}
	
	@Override
	public Result findFirBatchPins01SpDtlByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firBatchPins01SpDtlDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirBatchPins01SpDtl> searchResult = firBatchPins01SpDtlDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirBatchPins01SpDtlByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirBatchPins01SpDtl> searchResult = firBatchPins01SpDtlDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirBatchPins01SpDtl(FirBatchPins01SpDtl firBatchPins01SpDtl) throws SystemException, Exception {
		if (firBatchPins01SpDtl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firBatchPins01SpDtlDao.update(firBatchPins01SpDtl);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firBatchPins01SpDtl);
		return result;
	}

	@Override
	public Result insertFirBatchPins01SpDtl(FirBatchPins01SpDtl firBatchPins01SpDtl) throws SystemException, Exception {
		if (firBatchPins01SpDtl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = firBatchPins01SpDtlDao.insert(firBatchPins01SpDtl);
		
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		
		firBatchPins01SpDtl.setOid(oid);
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firBatchPins01SpDtl);
		return result;
	}

	@Override
	public Result findFirBatchPins01SpDtlByUK(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		FirBatchPins01SpDtl searchResult = firBatchPins01SpDtlDao.findByUK(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result removeFirBatchPins01SpDtl(BigDecimal oid) throws SystemException, Exception {
		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		FirBatchPins01SpDtl persisted = firBatchPins01SpDtlDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firBatchPins01SpDtlDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	@Override
	public void truncateFirBatchPins01SpDtl() throws SystemException, Exception {
		firBatchPins01SpDtlDao.truncate();
	}

	public FirBatchPins01SpDtlDao getFirBatchPins01SpDtlDao() {
		return firBatchPins01SpDtlDao;
	}

	public void setFirBatchPins01SpDtlDao(FirBatchPins01SpDtlDao firBatchPins01SpDtlDao) {
		this.firBatchPins01SpDtlDao = firBatchPins01SpDtlDao;
	}
}
