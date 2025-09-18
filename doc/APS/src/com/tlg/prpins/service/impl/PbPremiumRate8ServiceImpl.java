package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PbPremiumRate8Dao;
import com.tlg.prpins.entity.PbPremiumRate8;
import com.tlg.prpins.service.PbPremiumRate8Service;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PbPremiumRate8ServiceImpl implements PbPremiumRate8Service{

	private PbPremiumRate8Dao pbPremiumRate8Dao;

	@Override
	public int countPbPremiumRate8(Map params) throws SystemException, Exception {
		return pbPremiumRate8Dao.count(params);
	}

	@Override
	public Result findPbPremiumRate8ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = pbPremiumRate8Dao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<PbPremiumRate8> searchResult = pbPremiumRate8Dao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPbPremiumRate8ByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<PbPremiumRate8> searchResult = pbPremiumRate8Dao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPbPremiumRate8ByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		PbPremiumRate8 persisted = pbPremiumRate8Dao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updatePbPremiumRate8(PbPremiumRate8 pbPremiumRate8) throws SystemException, Exception {

		if (pbPremiumRate8 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = pbPremiumRate8Dao.update(pbPremiumRate8);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(pbPremiumRate8);
		return result;
	}

	@Override
	public Result insertPbPremiumRate8(PbPremiumRate8 pbPremiumRate8) throws SystemException, Exception {

		if (pbPremiumRate8 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = pbPremiumRate8Dao.insert(pbPremiumRate8);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(pbPremiumRate8);
		return result;
	}

	@Override
	public Result removePbPremiumRate8(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		PbPremiumRate8 persisted = pbPremiumRate8Dao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = pbPremiumRate8Dao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public PbPremiumRate8Dao getPbPremiumRate8Dao() {
		return pbPremiumRate8Dao;
	}

	public void setPbPremiumRate8Dao(PbPremiumRate8Dao pbPremiumRate8Dao) {
		this.pbPremiumRate8Dao = pbPremiumRate8Dao;
	}
}
