package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtrnBatchYcbDao;
import com.tlg.prpins.entity.FirAgtrnBatchYcb;
import com.tlg.prpins.service.FirAgtrnBatchYcbService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業  **/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtrnBatchYcbServiceImpl implements FirAgtrnBatchYcbService{
	
	private FirAgtrnBatchYcbDao firAgtrnBatchYcbDao;

	@Override
	public Result findFirAgtrnBatchYcbByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtrnBatchYcbDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirAgtrnBatchYcb> searchResult = firAgtrnBatchYcbDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtrnBatchYcbByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtrnBatchYcb> searchResult = firAgtrnBatchYcbDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countFirAgtrnBatchYcb(Map params) throws SystemException, Exception {
		return firAgtrnBatchYcbDao.count(params);
	}

	@Override
	public Result insertFirAgtrnBatchYcb(FirAgtrnBatchYcb firAgtrnBatchYcb) throws SystemException, Exception {

		if (firAgtrnBatchYcb == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		firAgtrnBatchYcbDao.insert(firAgtrnBatchYcb);

		if(firAgtrnBatchYcbDao.isUnique(firAgtrnBatchYcb)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtrnBatchYcb);
		return result;
	}
	
	@Override
	public Result updateFirAgtrnBatchYcb(FirAgtrnBatchYcb firAgtrnBatchYcb) throws SystemException, Exception {

		if (firAgtrnBatchYcb == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = false;
		try{
		 status = firAgtrnBatchYcbDao.update(firAgtrnBatchYcb);
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtrnBatchYcb);
		return result;
	}
	
	@Override
	public Result removeFirAgtrnBatchYcb(FirAgtrnBatchYcb firAgtrnBatchYcb) throws SystemException, Exception {
		if (firAgtrnBatchYcb == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtrnBatchYcbDao.remove(firAgtrnBatchYcb);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	public FirAgtrnBatchYcbDao getFirAgtrnBatchYcbDao() {
		return firAgtrnBatchYcbDao;
	}

	public void setFirAgtrnBatchYcbDao(FirAgtrnBatchYcbDao firAgtrnBatchYcbDao) {
		this.firAgtrnBatchYcbDao = firAgtrnBatchYcbDao;
	}
}
