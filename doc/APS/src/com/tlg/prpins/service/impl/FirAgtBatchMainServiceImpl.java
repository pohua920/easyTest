package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtBatchMainDao;
import com.tlg.prpins.entity.FirAgtBatchMain;
import com.tlg.prpins.service.FirAgtBatchMainService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtBatchMainServiceImpl implements FirAgtBatchMainService{
	/* mantis：FIR0265，處理人員：BJ085，需求單編號：FIR0265 板信受理檔產生排程 start */
	private FirAgtBatchMainDao firAgtBatchMainDao;

	@Override
	public Result findFirAgtBatchMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtBatchMainDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirAgtBatchMain> searchResult = firAgtBatchMainDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtBatchMainByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtBatchMain> searchResult = firAgtBatchMainDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtBatchMainByUk(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirAgtBatchMain firAgtBatchMain = firAgtBatchMainDao.findByUK(params);
		if (null == firAgtBatchMain) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(firAgtBatchMain);
		}
		return result;
	}
	
	@Override
	public Result updateFirAgtBatchMain(FirAgtBatchMain firAgtBatchMain) throws SystemException, Exception {

		if (firAgtBatchMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtBatchMainDao.update(firAgtBatchMain);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtBatchMain);
		return result;
	}
	
	/* mantis：FIR0314，處理人員：BJ085，需求單編號：FIR0314 台銀保經-APS新件要保檔產生排程
	   mantis：FIR0315，處理人員：BJ085，需求單編號：FIR0315 台銀保經-APS保單檔產生排程 */
	@Override
	public Result findBotMaxFilenameByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		String filename = firAgtBatchMainDao.findBotMaxFilenameByParams(params);
		if (null == filename) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(filename);
		}
		return result;
	}
	
	/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 start */
	@SuppressWarnings("unchecked")
	@Override
	public Result findAPS041ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtBatchMainDao.countForAps041(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirAgtBatchMain> searchResult = firAgtBatchMainDao.selectForAps041(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 end */

	/*mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程*/
	@Override
	public Result insertFirAgtBatchMain(FirAgtBatchMain firAgtBatchMain) throws SystemException, Exception {
		if (firAgtBatchMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
	
		firAgtBatchMainDao.insert(firAgtBatchMain);
	
		if(firAgtBatchMainDao.isUnique(firAgtBatchMain)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtBatchMain);
		return result;
	}

	public FirAgtBatchMainDao getFirAgtBatchMainDao() {
		return firAgtBatchMainDao;
	}

	public void setFirAgtBatchMainDao(FirAgtBatchMainDao firAgtBatchMainDao) {
		this.firAgtBatchMainDao = firAgtBatchMainDao;
	}
}
