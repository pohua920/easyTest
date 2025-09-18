package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtrnAs400DataErrDao;
import com.tlg.prpins.entity.FirAgtrnAs400DataErr;
import com.tlg.prpins.service.FirAgtrnAs400DataErrService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/**mantis：FIR0388，處理人員：BJ085，需求單編號：FIR0388 AS400續保資料匯入 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtrnAs400DataErrServiceImpl implements FirAgtrnAs400DataErrService{
	private FirAgtrnAs400DataErrDao firAgtrnAs400DataErrDao;

	@Override
	public Result findFirAgtrnAs400DataErrByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtrnAs400DataErrDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirAgtrnAs400DataErr> searchResult = firAgtrnAs400DataErrDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtrnAs400DataErrByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtrnAs400DataErr> searchResult = firAgtrnAs400DataErrDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countFirAgtrnAs400DataErr(Map params) throws SystemException, Exception {
		return firAgtrnAs400DataErrDao.count(params);
	}

	@Override
	public Result insertFirAgtrnAs400DataErr(FirAgtrnAs400DataErr firAgtrnAs400DataErr) throws SystemException, Exception {
		if (firAgtrnAs400DataErr == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = firAgtrnAs400DataErrDao.insert(firAgtrnAs400DataErr);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		firAgtrnAs400DataErr.setOid(oid);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtrnAs400DataErr);
		return result;
	}
	
	@Override
	public Result updateFirAgtrnAs400DataErr(FirAgtrnAs400DataErr firAgtrnAs400DataErr) throws SystemException, Exception {

		if (firAgtrnAs400DataErr == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtrnAs400DataErrDao.update(firAgtrnAs400DataErr);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtrnAs400DataErr);
		return result;
	}
	
	@Override
	public Result removeFirAgtrnAs400DataErr(BigDecimal oid) throws Exception {
		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		FirAgtrnAs400DataErr persisted = firAgtrnAs400DataErrDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtrnAs400DataErrDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	public FirAgtrnAs400DataErrDao getFirAgtrnAs400DataErrDao() {
		return firAgtrnAs400DataErrDao;
	}

	public void setFirAgtrnAs400DataErrDao(FirAgtrnAs400DataErrDao firAgtrnAs400DataErrDao) {
		this.firAgtrnAs400DataErrDao = firAgtrnAs400DataErrDao;
	}

}
