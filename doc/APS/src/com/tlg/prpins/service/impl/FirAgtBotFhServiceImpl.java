package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtBotFhDao;
import com.tlg.prpins.entity.FirAgtBotFh;
import com.tlg.prpins.service.FirAgtBotFhService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0315，處理人員：BJ085，需求單編號：FIR0315 台銀保經-APS保單檔產生排程 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtBotFhServiceImpl implements FirAgtBotFhService{
	private FirAgtBotFhDao firAgtBotFhDao;

	@Override
	public Result findFirAgtBotFhByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtBotFhDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirAgtBotFh> searchResult = firAgtBotFhDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtBotFhByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtBotFh> searchResult = firAgtBotFhDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countFirAgtBotFh(Map params) throws SystemException, Exception {
		return firAgtBotFhDao.count(params);
	}

	@Override
	public Result insertFirAgtBotFh(FirAgtBotFh firAgtBotFh) throws SystemException, Exception {

		if (firAgtBotFh == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		firAgtBotFhDao.insert(firAgtBotFh);

		if(firAgtBotFhDao.isUnique(firAgtBotFh)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtBotFh);
		return result;
	}
	
	@Override
	public Result updateFirAgtBotFh(FirAgtBotFh firAgtBotFh) throws SystemException, Exception {

		if (firAgtBotFh == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtBotFhDao.update(firAgtBotFh);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtBotFh);
		return result;
	}

	public FirAgtBotFhDao getFirAgtBotFhDao() {
		return firAgtBotFhDao;
	}

	public void setFirAgtBotFhDao(FirAgtBotFhDao firAgtBotFhDao) {
		this.firAgtBotFhDao = firAgtBotFhDao;
	}
	
}
