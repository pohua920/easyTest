package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtrnTmpFbDao;
import com.tlg.prpins.entity.FirAgtrnTmpFb;
import com.tlg.prpins.service.FirAgtrnTmpFbService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業  **/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtrnTmpFbServiceImpl implements FirAgtrnTmpFbService{
	
	private FirAgtrnTmpFbDao firAgtrnTmpFbDao;

	@Override
	public Result findFirAgtrnTmpFbByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtrnTmpFbDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirAgtrnTmpFb> searchResult = firAgtrnTmpFbDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtrnTmpFbByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtrnTmpFb> searchResult = firAgtrnTmpFbDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countFirAgtrnTmpFb(Map params) throws SystemException, Exception {
		return firAgtrnTmpFbDao.count(params);
	}

	@Override
	public Result insertFirAgtrnTmpFb(FirAgtrnTmpFb firAgtrnTmpFb) throws SystemException, Exception {

		if (firAgtrnTmpFb == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = firAgtrnTmpFbDao.insert(firAgtrnTmpFb);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			firAgtrnTmpFb.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtrnTmpFb);
		return result;
	}
	
	@Override
	public Result updateFirAgtrnTmpFb(FirAgtrnTmpFb firAgtrnTmpFb) throws SystemException, Exception {

		if (firAgtrnTmpFb == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtrnTmpFbDao.update(firAgtrnTmpFb);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtrnTmpFb);
		return result;
	}
	
	@Override
	public Result findFbDiffFile(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		 List<FirAgtrnTmpFb> searchResult = firAgtrnTmpFbDao.findForFbDiffFile(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findFbProcessCenter(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtrnTmpFb> searchResult = firAgtrnTmpFbDao.findForFbProcessCenter(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	public FirAgtrnTmpFbDao getFirAgtrnTmpFbDao() {
		return firAgtrnTmpFbDao;
	}

	public void setFirAgtrnTmpFbDao(FirAgtrnTmpFbDao firAgtrnTmpFbDao) {
		this.firAgtrnTmpFbDao = firAgtrnTmpFbDao;
	}
}
