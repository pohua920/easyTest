package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirDoubleQuakeQlistDao;
import com.tlg.prpins.entity.FirDoubleQuakeQlist;
import com.tlg.prpins.service.FirDoubleQuakeQlistService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirDoubleQuakeQlistServiceImpl implements FirDoubleQuakeQlistService{

	private FirDoubleQuakeQlistDao firDoubleQuakeQlistDao;

	@Override
	public int countFirDoubleQuakeQlist(Map params) throws SystemException, Exception {
		return firDoubleQuakeQlistDao.count(params);
	}

	@Override
	public Result findFirDoubleQuakeQlistByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firDoubleQuakeQlistDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirDoubleQuakeQlist> searchResult = firDoubleQuakeQlistDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result findFirDoubleQuakeQlistByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirDoubleQuakeQlist> searchResult = firDoubleQuakeQlistDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFirDoubleQuakeQlistByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirDoubleQuakeQlist persisted = firDoubleQuakeQlistDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateFirDoubleQuakeQlist(FirDoubleQuakeQlist firDoubleQuakeQlist) throws SystemException, Exception {

		if (firDoubleQuakeQlist == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firDoubleQuakeQlistDao.update(firDoubleQuakeQlist);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firDoubleQuakeQlist);
		return result;
	}

	@Override
	public Result insertFirDoubleQuakeQlist(FirDoubleQuakeQlist firDoubleQuakeQlist) throws SystemException, Exception {

		if (firDoubleQuakeQlist == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		firDoubleQuakeQlist.setDcreate(new Date());
		BigDecimal oid = firDoubleQuakeQlistDao.insert(firDoubleQuakeQlist);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firDoubleQuakeQlist);
		return result;
	}

	@Override
	public Result removeFirDoubleQuakeQlist(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		FirDoubleQuakeQlist persisted = firDoubleQuakeQlistDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firDoubleQuakeQlistDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public FirDoubleQuakeQlistDao getFirDoubleQuakeQlistDao() {
		return firDoubleQuakeQlistDao;
	}

	public void setFirDoubleQuakeQlistDao(FirDoubleQuakeQlistDao firDoubleQuakeQlistDao) {
		this.firDoubleQuakeQlistDao = firDoubleQuakeQlistDao;
	}
}
