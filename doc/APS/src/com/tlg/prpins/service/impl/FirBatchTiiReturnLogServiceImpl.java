package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirBatchTiiReturnLogDao;
import com.tlg.prpins.entity.FirBatchTiiReturnLog;
import com.tlg.prpins.service.FirBatchTiiReturnLogService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0580，處理人員：BJ085，需求單編號：FIR0580 保發中心-住火保批資料回饋檔回存資料庫 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirBatchTiiReturnLogServiceImpl implements FirBatchTiiReturnLogService{

	private FirBatchTiiReturnLogDao firBatchTiiReturnLogDao;

	@Override
	public int countFirBatchTiiReturnLog(Map params) throws SystemException, Exception {
		return firBatchTiiReturnLogDao.count(params);
	}

	@Override
	public Result findFirBatchTiiReturnLogByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firBatchTiiReturnLogDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirBatchTiiReturnLog> searchResult = firBatchTiiReturnLogDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFirBatchTiiReturnLogByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirBatchTiiReturnLog> searchResult = firBatchTiiReturnLogDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result updateFirBatchTiiReturnLog(FirBatchTiiReturnLog firBatchTiiReturnLog) throws SystemException, Exception {
		
		if (firBatchTiiReturnLog == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		boolean status = firBatchTiiReturnLogDao.update(firBatchTiiReturnLog);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firBatchTiiReturnLog);
		return result;
	}

	@Override
	public Result insertFirBatchTiiReturnLog(FirBatchTiiReturnLog firBatchTiiReturnLog) throws SystemException, Exception {

		if (firBatchTiiReturnLog == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = firBatchTiiReturnLogDao.insert(firBatchTiiReturnLog);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firBatchTiiReturnLog);
		return result;
	}

	@Override
	public Result removeFirBatchTiiReturnLog(BigDecimal oid) throws SystemException, Exception {
		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		FirBatchTiiReturnLog persisted = firBatchTiiReturnLogDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firBatchTiiReturnLogDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	public FirBatchTiiReturnLogDao getFirBatchTiiReturnLogDao() {
		return firBatchTiiReturnLogDao;
	}

	public void setFirBatchTiiReturnLogDao(FirBatchTiiReturnLogDao firBatchTiiReturnLogDao) {
		this.firBatchTiiReturnLogDao = firBatchTiiReturnLogDao;
	}
}
