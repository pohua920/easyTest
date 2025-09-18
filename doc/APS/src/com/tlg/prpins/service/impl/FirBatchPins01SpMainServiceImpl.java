package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.Aps031ExcelVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirBatchPins01SpMainDao;
import com.tlg.prpins.entity.FirBatchPins01SpMain;
import com.tlg.prpins.service.FirBatchPins01SpMainService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirBatchPins01SpMainServiceImpl implements FirBatchPins01SpMainService{

	private FirBatchPins01SpMainDao firBatchPins01SpMainDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countFirBatchPins01SpMain(Map params) throws SystemException, Exception {
		return firBatchPins01SpMainDao.count(params);
	}
	
	@Override
	public Result findFirBatchPins01SpMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firBatchPins01SpMainDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirBatchPins01SpMain> searchResult = firBatchPins01SpMainDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirBatchPins01SpMainByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirBatchPins01SpMain> searchResult = firBatchPins01SpMainDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirBatchPins01SpMain(FirBatchPins01SpMain firBatchPins01SpMain) throws SystemException, Exception {
		if (firBatchPins01SpMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firBatchPins01SpMainDao.update(firBatchPins01SpMain);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firBatchPins01SpMain);
		return result;
	}

	@Override
	public Result insertFirBatchPins01SpMain(FirBatchPins01SpMain firBatchPins01SpMain) throws SystemException, Exception {
		if (firBatchPins01SpMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = firBatchPins01SpMainDao.insert(firBatchPins01SpMain);
		
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		
		firBatchPins01SpMain.setOid(oid);
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firBatchPins01SpMain);
		return result;
	}

	@Override
	public Result findFirBatchPins01SpMainByUK(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		FirBatchPins01SpMain searchResult = firBatchPins01SpMainDao.findByUK(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result removeFirBatchPins01SpMain(BigDecimal oid) throws SystemException, Exception {
		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		FirBatchPins01SpMain persisted = firBatchPins01SpMainDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firBatchPins01SpMainDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	@Override
	public Result findForAps031ExcelByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Aps031ExcelVo> searchResult = firBatchPins01SpMainDao.selectForAps031Excel(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countForAps031Excel(Map params) throws SystemException, Exception {
		return firBatchPins01SpMainDao.countForAps031Excel(params);
	}
	
	@Override
	public void truncateFirBatchPins01SpMain() throws SystemException, Exception {
		firBatchPins01SpMainDao.truncate();
	}

	public FirBatchPins01SpMainDao getFirBatchPins01SpMainDao() {
		return firBatchPins01SpMainDao;
	}

	public void setFirBatchPins01SpMainDao(FirBatchPins01SpMainDao firBatchPins01SpMainDao) {
		this.firBatchPins01SpMainDao = firBatchPins01SpMainDao;
	}
}
