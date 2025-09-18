package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcRnproposalMainDao;
import com.tlg.prpins.entity.FirCtbcRnproposalMain;
import com.tlg.prpins.service.FirCtbcRnproposalMainService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0459，處理人員：CC009，需求單編號：FIR0459 住火-APS中信續件要保書-排程查詢作業 
    mantis：FIR0458，處理人員：CC009，需求單編號：FIR0458 住火-APS中信續件要保書-資料接收排程 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcRnproposalMainServiceImpl implements FirCtbcRnproposalMainService{
	private FirCtbcRnproposalMainDao firCtbcRnproposalMainDao;

	@Override
	public Result findFirCtbcRnproposalMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firCtbcRnproposalMainDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirCtbcRnproposalMain> searchResult = firCtbcRnproposalMainDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public int countFirCtbcRnproposalMain(Map params) throws SystemException, Exception {
		return firCtbcRnproposalMainDao.count(params);
	}

	@Override
	public Result insertFirCtbcRnproposalMain(FirCtbcRnproposalMain firCtbcRnproposalMain)
			throws SystemException, Exception {
		if (firCtbcRnproposalMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		firCtbcRnproposalMainDao.insert(firCtbcRnproposalMain);

		if(firCtbcRnproposalMainDao.isUnique(firCtbcRnproposalMain)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcRnproposalMain);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcRnproposalMainByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcRnproposalMain> searchResult = firCtbcRnproposalMainDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirCtbcRnproposalMain(FirCtbcRnproposalMain firCtbcRnproposalMain)
			throws SystemException, Exception {
		if (firCtbcRnproposalMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firCtbcRnproposalMainDao.update(firCtbcRnproposalMain);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firCtbcRnproposalMain);
		return result;
	}

	public FirCtbcRnproposalMainDao getFirCtbcRnproposalMainDao() {
		return firCtbcRnproposalMainDao;
	}

	public void setFirCtbcRnproposalMainDao(FirCtbcRnproposalMainDao firCtbcRnproposalMainDao) {
		this.firCtbcRnproposalMainDao = firCtbcRnproposalMainDao;
	}
}
