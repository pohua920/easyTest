package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PbPremiumRate0Dao;
import com.tlg.prpins.entity.PbPremiumRate0;
import com.tlg.prpins.service.PbPremiumRate0Service;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PbPremiumRate0ServiceImpl implements PbPremiumRate0Service{

	private PbPremiumRate0Dao pbPremiumRate0Dao;

	@Override
	public int countPbPremiumRate0(Map params) throws SystemException, Exception {
		return pbPremiumRate0Dao.count(params);
	}

	@Override
	public Result findPbPremiumRate0ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = pbPremiumRate0Dao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<PbPremiumRate0> searchResult = pbPremiumRate0Dao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPbPremiumRate0ByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<PbPremiumRate0> searchResult = pbPremiumRate0Dao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPbPremiumRate0ByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		PbPremiumRate0 persisted = pbPremiumRate0Dao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updatePbPremiumRate0(PbPremiumRate0 pbPremiumRate0) throws SystemException, Exception {

		if (pbPremiumRate0 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = pbPremiumRate0Dao.update(pbPremiumRate0);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(pbPremiumRate0);
		return result;
	}

	@Override
	public Result insertPbPremiumRate0(PbPremiumRate0 pbPremiumRate0) throws SystemException, Exception {

		if (pbPremiumRate0 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = pbPremiumRate0Dao.insert(pbPremiumRate0);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(pbPremiumRate0);
		return result;
	}

	@Override
	public Result removePbPremiumRate0(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		PbPremiumRate0 persisted = pbPremiumRate0Dao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = pbPremiumRate0Dao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public PbPremiumRate0Dao getPbPremiumRate0Dao() {
		return pbPremiumRate0Dao;
	}

	public void setPbPremiumRate0Dao(PbPremiumRate0Dao pbPremiumRate0Dao) {
		this.pbPremiumRate0Dao = pbPremiumRate0Dao;
	}
}
