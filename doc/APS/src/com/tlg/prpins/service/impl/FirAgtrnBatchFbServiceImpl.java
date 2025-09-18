package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtrnBatchFbDao;
import com.tlg.prpins.entity.FirAgtrnBatchFb;
import com.tlg.prpins.service.FirAgtrnBatchFbService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程  **/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtrnBatchFbServiceImpl implements FirAgtrnBatchFbService{
	
	private FirAgtrnBatchFbDao firAgtrnBatchFbDao;

	@Override
	public Result findFirAgtrnBatchFbByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtrnBatchFbDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirAgtrnBatchFb> searchResult = firAgtrnBatchFbDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtrnBatchFbByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtrnBatchFb> searchResult = firAgtrnBatchFbDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countFirAgtrnBatchFb(Map params) throws SystemException, Exception {
		return firAgtrnBatchFbDao.count(params);
	}

	@Override
	public Result insertFirAgtrnBatchFb(FirAgtrnBatchFb firAgtrnBatchFb) throws SystemException, Exception {

		if (firAgtrnBatchFb == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		firAgtrnBatchFbDao.insert(firAgtrnBatchFb);

		if(firAgtrnBatchFbDao.isUnique(firAgtrnBatchFb)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtrnBatchFb);
		return result;
	}
	
	@Override
	public Result updateFirAgtrnBatchFb(FirAgtrnBatchFb firAgtrnBatchFb) throws SystemException, Exception {

		if (firAgtrnBatchFb == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtrnBatchFbDao.update(firAgtrnBatchFb);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtrnBatchFb);
		return result;
	}
	
	public FirAgtrnBatchFbDao getFirAgtrnBatchFbDao() {
		return firAgtrnBatchFbDao;
	}

	public void setFirAgtrnBatchFbDao(FirAgtrnBatchFbDao firAgtrnBatchFbDao) {
		this.firAgtrnBatchFbDao = firAgtrnBatchFbDao;
	}
}
