package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirPremcalcTmpDao;
import com.tlg.prpins.entity.FirPremcalcTmp;
import com.tlg.prpins.service.FirPremcalcTmpService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirPremcalcTmpServiceImpl implements FirPremcalcTmpService{

	private FirPremcalcTmpDao firPremcalcTmpDao;

	@Override
	public int countFirPremcalcTmp(Map params) throws SystemException, Exception {
		return firPremcalcTmpDao.count(params);
	}

	@Override
	public Result findFirPremcalcTmpByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firPremcalcTmpDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirPremcalcTmp> searchResult = firPremcalcTmpDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFirPremcalcTmpByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirPremcalcTmp> searchResult = firPremcalcTmpDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFirPremcalcTmpByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirPremcalcTmp persisted = firPremcalcTmpDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateFirPremcalcTmp(FirPremcalcTmp firPremcalcTmp) throws SystemException, Exception {

		if (firPremcalcTmp == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		firPremcalcTmp.setDupdate(new Date());
		boolean status = firPremcalcTmpDao.update(firPremcalcTmp);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firPremcalcTmp);
		return result;
	}

	@Override
	public Result insertFirPremcalcTmp(FirPremcalcTmp firPremcalcTmp) throws SystemException, Exception {

		if (firPremcalcTmp == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		firPremcalcTmp.setDcreate(new Date());//加入建檔時間
		BigDecimal oid = firPremcalcTmpDao.insert(firPremcalcTmp);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		firPremcalcTmp.setOid(oid);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firPremcalcTmp);
		return result;
	}

	@Override
	public Result removeFirPremcalcTmp(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		FirPremcalcTmp persisted = firPremcalcTmpDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firPremcalcTmpDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public FirPremcalcTmpDao getFirPremcalcTmpDao() {
		return firPremcalcTmpDao;
	}

	public void setFirPremcalcTmpDao(FirPremcalcTmpDao firPremcalcTmpDao) {
		this.firPremcalcTmpDao = firPremcalcTmpDao;
	}
}
