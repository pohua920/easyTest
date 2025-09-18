package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtrnAs400DataDao;
import com.tlg.prpins.entity.FirAgtrnAs400Data;
import com.tlg.prpins.service.FirAgtrnAs400DataService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/**mantis：FIR0388，處理人員：BJ085，需求單編號：FIR0388 AS400續保資料匯入 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtrnAs400DataServiceImpl implements FirAgtrnAs400DataService{
	private FirAgtrnAs400DataDao firAgtrnAs400DataDao;

	@Override
	public Result findFirAgtrnAs400DataByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtrnAs400DataDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirAgtrnAs400Data> searchResult = firAgtrnAs400DataDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtrnAs400DataByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtrnAs400Data> searchResult = firAgtrnAs400DataDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countFirAgtrnAs400Data(Map params) throws SystemException, Exception {
		return firAgtrnAs400DataDao.count(params);
	}

	@Override
	public Result insertFirAgtrnAs400Data(FirAgtrnAs400Data firAgtrnAs400Data) throws SystemException, Exception {
		if (firAgtrnAs400Data == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = firAgtrnAs400DataDao.insert(firAgtrnAs400Data);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		firAgtrnAs400Data.setOid(oid);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtrnAs400Data);
		return result;
	}
	
	@Override
	public Result updateFirAgtrnAs400Data(FirAgtrnAs400Data firAgtrnAs400Data) throws SystemException, Exception {

		if (firAgtrnAs400Data == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtrnAs400DataDao.update(firAgtrnAs400Data);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtrnAs400Data);
		return result;
	}
	
	@Override
	public Result removeFirAgtrnAs400Data(BigDecimal oid) throws Exception {
		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		FirAgtrnAs400Data persisted = firAgtrnAs400DataDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtrnAs400DataDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	
	public FirAgtrnAs400DataDao getFirAgtrnAs400DataDao() {
		return firAgtrnAs400DataDao;
	}

	public void setFirAgtrnAs400DataDao(FirAgtrnAs400DataDao firAgtrnAs400DataDao) {
		this.firAgtrnAs400DataDao = firAgtrnAs400DataDao;
	}
}
