package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirRepeatpolicyBatchMainDao;
import com.tlg.prpins.entity.FirRepeatpolicyBatchMain;
import com.tlg.prpins.service.FirRepeatpolicyBatchMainService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0565，處理人員：CC009，需求單編號：FIR0565 住火_複保險通知轉檔作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirRepeatpolicyBatchMainServiceImpl implements FirRepeatpolicyBatchMainService{
	
	private FirRepeatpolicyBatchMainDao firRepeatpolicyBatchMainDao;
	
	@Override
	public Result findByParams(Map params) throws SystemException,Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirRepeatpolicyBatchMain> searchResult = firRepeatpolicyBatchMainDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result removeFirRepeatpolicyBatchMain(FirRepeatpolicyBatchMain entity) throws SystemException,Exception {
		if (entity == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firRepeatpolicyBatchMainDao.remove(entity);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}

	@Override
	public Result insertFirRepeatpolicyBatchMain(FirRepeatpolicyBatchMain entity) throws SystemException,Exception {
		if (entity == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		firRepeatpolicyBatchMainDao.insert(entity);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(entity);
		return result;
	}
	
	@Override
	public Result findFirRepeatpolicyBatchMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firRepeatpolicyBatchMainDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirRepeatpolicyBatchMain> searchResult = firRepeatpolicyBatchMainDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result updateFirRepeatpolicyBatchMain(FirRepeatpolicyBatchMain entity) throws SystemException, Exception {
		if (entity == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firRepeatpolicyBatchMainDao.update(entity);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(entity);
		return result;
	}

	public FirRepeatpolicyBatchMainDao getFirRepeatpolicyBatchMainDao() {
		return firRepeatpolicyBatchMainDao;
	}

	public void setFirRepeatpolicyBatchMainDao(FirRepeatpolicyBatchMainDao firRepeatpolicyBatchMainDao) {
		this.firRepeatpolicyBatchMainDao = firRepeatpolicyBatchMainDao;
	}
}
