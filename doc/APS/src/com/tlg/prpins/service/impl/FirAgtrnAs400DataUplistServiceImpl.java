package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtrnAs400DataUplistDao;
import com.tlg.prpins.entity.FirAgtrnAs400DataUplist;
import com.tlg.prpins.service.FirAgtrnAs400DataUplistService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/**mantis：FIR0388，處理人員：BJ085，需求單編號：FIR0388 AS400續保資料匯入 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtrnAs400DataUplistServiceImpl implements FirAgtrnAs400DataUplistService{
	private FirAgtrnAs400DataUplistDao firAgtrnAs400DataUplistDao;

	@Override
	public Result findFirAgtrnAs400DataUplistByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtrnAs400DataUplistDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirAgtrnAs400DataUplist> searchResult = firAgtrnAs400DataUplistDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtrnAs400DataUplistByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtrnAs400DataUplist> searchResult = firAgtrnAs400DataUplistDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countFirAgtrnAs400DataUplist(Map params) throws SystemException, Exception {
		return firAgtrnAs400DataUplistDao.count(params);
	}

	@Override
	public Result insertFirAgtrnAs400DataUplist(FirAgtrnAs400DataUplist firAgtrnAs400DataUplist) throws SystemException, Exception {
		if (firAgtrnAs400DataUplist == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = firAgtrnAs400DataUplistDao.insert(firAgtrnAs400DataUplist);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		firAgtrnAs400DataUplist.setOid(oid);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtrnAs400DataUplist);
		return result;
	}
	
	@Override
	public Result updateFirAgtrnAs400DataUplist(FirAgtrnAs400DataUplist firAgtrnAs400DataUplist) throws SystemException, Exception {

		if (firAgtrnAs400DataUplist == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtrnAs400DataUplistDao.update(firAgtrnAs400DataUplist);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtrnAs400DataUplist);
		return result;
	}
	
	@Override
	public Result findFirAgtrnAs400DataUplistByUk(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		FirAgtrnAs400DataUplist searchResult = firAgtrnAs400DataUplistDao.findByUK(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	public FirAgtrnAs400DataUplistDao getFirAgtrnAs400DataUplistDao() {
		return firAgtrnAs400DataUplistDao;
	}

	public void setFirAgtrnAs400DataUplistDao(FirAgtrnAs400DataUplistDao firAgtrnAs400DataUplistDao) {
		this.firAgtrnAs400DataUplistDao = firAgtrnAs400DataUplistDao;
	}
}
