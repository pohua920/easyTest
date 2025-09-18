package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtrnBatchBotDao;
import com.tlg.prpins.entity.FirAgtrnBatchBot;
import com.tlg.prpins.service.FirAgtrnBatchBotService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業  **/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtrnBatchBotServiceImpl implements FirAgtrnBatchBotService{
	
	private FirAgtrnBatchBotDao firAgtrnBatchBotDao;

	@Override
	public Result findFirAgtrnBatchBotByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtrnBatchBotDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirAgtrnBatchBot> searchResult = firAgtrnBatchBotDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtrnBatchBotByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtrnBatchBot> searchResult = firAgtrnBatchBotDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countFirAgtrnBatchBot(Map params) throws SystemException, Exception {
		return firAgtrnBatchBotDao.count(params);
	}

	@Override
	public Result insertFirAgtrnBatchBot(FirAgtrnBatchBot firAgtrnBatchBot) throws SystemException, Exception {

		if (firAgtrnBatchBot == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		firAgtrnBatchBotDao.insert(firAgtrnBatchBot);

		if(firAgtrnBatchBotDao.isUnique(firAgtrnBatchBot)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtrnBatchBot);
		return result;
	}
	
	@Override
	public Result updateFirAgtrnBatchBot(FirAgtrnBatchBot firAgtrnBatchBot) throws SystemException, Exception {

		if (firAgtrnBatchBot == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtrnBatchBotDao.update(firAgtrnBatchBot);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtrnBatchBot);
		return result;
	}
	
	@Override
	public Result removeFirAgtrnBatchBot(FirAgtrnBatchBot firAgtrnBatchBot) throws SystemException, Exception {
		if (firAgtrnBatchBot == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtrnBatchBotDao.remove(firAgtrnBatchBot);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	public FirAgtrnBatchBotDao getFirAgtrnBatchBotDao() {
		return firAgtrnBatchBotDao;
	}

	public void setFirAgtrnBatchBotDao(FirAgtrnBatchBotDao firAgtrnBatchBotDao) {
		this.firAgtrnBatchBotDao = firAgtrnBatchBotDao;
	}
}
