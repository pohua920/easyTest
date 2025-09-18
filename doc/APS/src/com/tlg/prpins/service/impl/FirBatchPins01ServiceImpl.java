package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirBatchPins01Dao;
import com.tlg.prpins.entity.FirBatchPins01;
import com.tlg.prpins.service.FirBatchPins01Service;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0427，處理人員：BJ085，需求單編號：FIR0427 個人險-保單通訊資料產生及下載作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirBatchPins01ServiceImpl implements FirBatchPins01Service{

	private FirBatchPins01Dao firBatchPins01Dao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countFirBatchPins01(Map params) throws SystemException, Exception {
		return firBatchPins01Dao.count(params);
	}
	
	@Override
	public Result findFirBatchPins01ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firBatchPins01Dao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirBatchPins01> searchResult = firBatchPins01Dao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirBatchPins01ByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirBatchPins01> searchResult = firBatchPins01Dao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirBatchPins01(FirBatchPins01 firBatchPins01) throws SystemException, Exception {
		if (firBatchPins01 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firBatchPins01Dao.update(firBatchPins01);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firBatchPins01);
		return result;
	}

	@Override
	public Result insertFirBatchPins01(FirBatchPins01 firBatchPins01) throws SystemException, Exception {
		if (firBatchPins01 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		firBatchPins01Dao.insert(firBatchPins01);
//		if(null == oid  || 0L ==oid.longValue()){
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firBatchPins01);
		return result;
	}

	@Override
	public Result findFirBatchPins01ByUK(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		FirBatchPins01 searchResult = firBatchPins01Dao.findByUK(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public FirBatchPins01Dao getFirBatchPins01Dao() {
		return firBatchPins01Dao;
	}

	public void setFirBatchPins01Dao(FirBatchPins01Dao firBatchPins01Dao) {
		this.firBatchPins01Dao = firBatchPins01Dao;
	}

}
