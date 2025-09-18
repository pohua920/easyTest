package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirBatchTiiAddrDao;
import com.tlg.prpins.entity.FirBatchTiiAddr;
import com.tlg.prpins.service.FirBatchTiiAddrService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirBatchTiiAddrServiceImpl implements FirBatchTiiAddrService{

	private FirBatchTiiAddrDao firBatchTiiAddrDao;

	@Override
	public int countFirBatchTiiAddr(Map params) throws SystemException, Exception {
		return firBatchTiiAddrDao.count(params);
	}

	@Override
	public Result findFirBatchTiiAddrByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firBatchTiiAddrDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirBatchTiiAddr> searchResult = firBatchTiiAddrDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFirBatchTiiAddrByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirBatchTiiAddr> searchResult = firBatchTiiAddrDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result updateFirBatchTiiAddr(FirBatchTiiAddr firBatchTiiAddr) throws SystemException, Exception {
		
		if (firBatchTiiAddr == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		boolean status = firBatchTiiAddrDao.update(firBatchTiiAddr);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firBatchTiiAddr);
		return result;
	}

	@Override
	public Result insertFirBatchTiiAddr(FirBatchTiiAddr firBatchTiiAddr) throws SystemException, Exception {

		if (firBatchTiiAddr == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = firBatchTiiAddrDao.insert(firBatchTiiAddr);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firBatchTiiAddr);
		return result;
	}

	@Override
	public Result removeFirBatchTiiAddr(BigDecimal oid) throws SystemException, Exception {
		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		FirBatchTiiAddr persisted = firBatchTiiAddrDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firBatchTiiAddrDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	public FirBatchTiiAddrDao getFirBatchTiiAddrDao() {
		return firBatchTiiAddrDao;
	}

	public void setFirBatchTiiAddrDao(FirBatchTiiAddrDao firBatchTiiAddrDao) {
		this.firBatchTiiAddrDao = firBatchTiiAddrDao;
	}
}
