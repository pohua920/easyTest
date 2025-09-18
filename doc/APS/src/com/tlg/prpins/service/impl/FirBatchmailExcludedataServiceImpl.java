package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirBatchmailExcludedataDao;
import com.tlg.prpins.entity.FirBatchmailExcludedata;
import com.tlg.prpins.service.FirBatchmailExcludedataService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0369，處理人員：BJ085，需求單編號：FIR0369 增加電子保單不寄送單位業務來源設定 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirBatchmailExcludedataServiceImpl implements FirBatchmailExcludedataService{
	private FirBatchmailExcludedataDao firBatchmailExcludedataDao;

	@Override
	public Result findFirBatchmailExcludedataByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firBatchmailExcludedataDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirBatchmailExcludedata> searchResult = firBatchmailExcludedataDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirBatchmailExcludedataByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirBatchmailExcludedata> searchResult = firBatchmailExcludedataDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirBatchmailExcludedataByUk(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirBatchmailExcludedata firBatchmailExcludedata = firBatchmailExcludedataDao.findByUK(params);
		if (null == firBatchmailExcludedata) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(firBatchmailExcludedata);
		}
		return result;
	}
	
	@Override
	public Result insertFirBatchmailExcludedata(FirBatchmailExcludedata firBatchmailExcludedata) throws SystemException, Exception {

		if (firBatchmailExcludedata == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		if(!firBatchmailExcludedataDao.isUnique(firBatchmailExcludedata)) {
			throw new SystemException("資料已存在資料庫中");
		}
		firBatchmailExcludedataDao.insert(firBatchmailExcludedata);
		
		if(firBatchmailExcludedataDao.isUnique(firBatchmailExcludedata)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firBatchmailExcludedata);
		return result;
	}
	
	@Override
	public Result updateFirBatchmailExcludedata(FirBatchmailExcludedata firBatchmailExcludedata) throws SystemException, Exception {

		if (firBatchmailExcludedata == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firBatchmailExcludedataDao.update(firBatchmailExcludedata);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firBatchmailExcludedata);
		return result;
	}
	
	public FirBatchmailExcludedataDao getFirBatchmailExcludedataDao() {
		return firBatchmailExcludedataDao;
	}

	public void setFirBatchmailExcludedataDao(FirBatchmailExcludedataDao firBatchmailExcludedataDao) {
		this.firBatchmailExcludedataDao = firBatchmailExcludedataDao;
	}
}
