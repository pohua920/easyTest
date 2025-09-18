package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirPremiumRate2Dao;
import com.tlg.prpins.entity.FirPremiumRate2;
import com.tlg.prpins.service.FirPremiumRate2Service;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirPremiumRate2ServiceImpl implements FirPremiumRate2Service{

	private FirPremiumRate2Dao firPremiumRate2Dao;

	@Override
	public int countFirPremiumRate2(Map params) throws SystemException, Exception {
		return firPremiumRate2Dao.count(params);
	}

	@Override
	public Result findFirPremiumRate2ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firPremiumRate2Dao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirPremiumRate2> searchResult = firPremiumRate2Dao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFirPremiumRate2ByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirPremiumRate2> searchResult = firPremiumRate2Dao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFirPremiumRate2ByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirPremiumRate2 persisted = firPremiumRate2Dao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateFirPremiumRate2(FirPremiumRate2 firPremiumRate2) throws SystemException, Exception {

		if (firPremiumRate2 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firPremiumRate2Dao.update(firPremiumRate2);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firPremiumRate2);
		return result;
	}

	@Override
	public Result insertFirPremiumRate2(FirPremiumRate2 firPremiumRate2) throws SystemException, Exception {

		if (firPremiumRate2 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = firPremiumRate2Dao.insert(firPremiumRate2);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firPremiumRate2);
		return result;
	}

	@Override
	public Result removeFirPremiumRate2(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		FirPremiumRate2 persisted = firPremiumRate2Dao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firPremiumRate2Dao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public FirPremiumRate2Dao getFirPremiumRate2Dao() {
		return firPremiumRate2Dao;
	}

	public void setFirPremiumRate2Dao(FirPremiumRate2Dao firPremiumRate2Dao) {
		this.firPremiumRate2Dao = firPremiumRate2Dao;
	}
}
