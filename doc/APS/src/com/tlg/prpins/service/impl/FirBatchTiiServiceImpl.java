package com.tlg.prpins.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirBatchTiiDao;
import com.tlg.prpins.entity.FirBatchTii;
import com.tlg.prpins.service.FirBatchTiiService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirBatchTiiServiceImpl implements FirBatchTiiService{

	private FirBatchTiiDao firBatchTiiDao;

	@Override
	public int countFirBatchTii(Map params) throws SystemException, Exception {
		return firBatchTiiDao.count(params);
	}

	@Override
	public Result findFirBatchTiiByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firBatchTiiDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirBatchTii> searchResult = firBatchTiiDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFirBatchTiiByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirBatchTii> searchResult = firBatchTiiDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findFirBatchTiiByUk(String batchNo) throws SystemException,Exception {

		if (StringUtil.isSpace(batchNo)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("batchNo", batchNo);
		FirBatchTii persisted = firBatchTiiDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateFirBatchTii(FirBatchTii firBatchTii) throws SystemException, Exception {
		
		if (firBatchTii == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		boolean status = firBatchTiiDao.update(firBatchTii);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firBatchTii);
		return result;
	}

	@Override
	public Result insertFirBatchTii(FirBatchTii firBatchTii) throws SystemException, Exception {

		if (firBatchTii == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		firBatchTiiDao.insert(firBatchTii);

		if(firBatchTiiDao.isUnique(firBatchTii)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firBatchTii);
		return result;
	}

	@Override
	public Result removeFirBatchTii(FirBatchTii firBatchTii) throws SystemException, Exception {
		if (firBatchTii == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firBatchTiiDao.remove(firBatchTii);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	public FirBatchTiiDao getFirBatchTiiDao() {
		return firBatchTiiDao;
	}

	public void setFirBatchTiiDao(FirBatchTiiDao firBatchTiiDao) {
		this.firBatchTiiDao = firBatchTiiDao;
	}
}
