package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirBatchSendmailDao;
import com.tlg.prpins.entity.FirBatchSendmail;
import com.tlg.prpins.service.FirBatchSendmailService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/*mantis：CAR0507，處理人員：BJ085，需求單編號：CAR0507 承保系統新增電子保單產生追蹤機制(車險&旅平險)*/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirBatchSendmailServiceImpl implements FirBatchSendmailService{

	private FirBatchSendmailDao firBatchSendmailDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countFirBatchSendmail(Map params) throws SystemException, Exception {
		return firBatchSendmailDao.count(params);
	}
	
	@Override
	public Result findFirBatchSendmailByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firBatchSendmailDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirBatchSendmail> searchResult = firBatchSendmailDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirBatchSendmailByParams(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		List<FirBatchSendmail> searchResult = firBatchSendmailDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirBatchSendmail(FirBatchSendmail firBatchSendmail) throws SystemException, Exception {
		if (firBatchSendmail == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firBatchSendmailDao.update(firBatchSendmail);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firBatchSendmail);
		return result;
	}

	@Override
	public Result insertFirBatchSendmail(FirBatchSendmail firBatchSendmail) throws SystemException, Exception {
		if (firBatchSendmail == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		firBatchSendmailDao.insert(firBatchSendmail);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firBatchSendmail);
		return result;
	}

	@Override
	public Result removeFirBatchSendmail(FirBatchSendmail firBatchSendmail) throws SystemException, Exception {
		if (firBatchSendmail == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firBatchSendmailDao.remove(firBatchSendmail);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}

	public FirBatchSendmailDao getFirBatchSendmailDao() {
		return firBatchSendmailDao;
	}

	public void setFirBatchSendmailDao(FirBatchSendmailDao firBatchSendmailDao) {
		this.firBatchSendmailDao = firBatchSendmailDao;
	}

}
