package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtBotApDao;
import com.tlg.prpins.entity.FirAgtBotAp;
import com.tlg.prpins.service.FirAgtBotApService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0314，處理人員：BJ085，需求單編號：FIR0314 台銀保經-APS新件要保檔產生排程 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtBotApServiceImpl implements FirAgtBotApService{
	private FirAgtBotApDao firAgtBotApDao;

	@Override
	public Result findFirAgtBotApByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtBotApDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirAgtBotAp> searchResult = firAgtBotApDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtBotApByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtBotAp> searchResult = firAgtBotApDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countFirAgtBotAp(Map params) throws SystemException, Exception {
		return firAgtBotApDao.count(params);
	}

	@Override
	public Result insertFirAgtBotAp(FirAgtBotAp firAgtBotAp) throws SystemException, Exception {

		if (firAgtBotAp == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		firAgtBotApDao.insert(firAgtBotAp);

		if(firAgtBotApDao.isUnique(firAgtBotAp)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtBotAp);
		return result;
	}
	
	@Override
	public Result updateFirAgtBotAp(FirAgtBotAp firAgtBotAp) throws SystemException, Exception {

		if (firAgtBotAp == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtBotApDao.update(firAgtBotAp);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtBotAp);
		return result;
	}

	public FirAgtBotApDao getFirAgtBotApDao() {
		return firAgtBotApDao;
	}

	public void setFirAgtBotApDao(FirAgtBotApDao firAgtBotApDao) {
		this.firAgtBotApDao = firAgtBotApDao;
	}
	
}
