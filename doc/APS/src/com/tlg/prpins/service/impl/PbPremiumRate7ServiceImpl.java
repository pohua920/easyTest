package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PbPremiumRate7Dao;
import com.tlg.prpins.entity.PbPremiumRate7;
import com.tlg.prpins.service.PbPremiumRate7Service;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PbPremiumRate7ServiceImpl implements PbPremiumRate7Service{

	private PbPremiumRate7Dao pbPremiumRate7Dao;

	@Override
	public int countPbPremiumRate7(Map params) throws SystemException, Exception {
		return pbPremiumRate7Dao.count(params);
	}

	@Override
	public Result findPbPremiumRate7ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = pbPremiumRate7Dao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<PbPremiumRate7> searchResult = pbPremiumRate7Dao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPbPremiumRate7ByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<PbPremiumRate7> searchResult = pbPremiumRate7Dao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPbPremiumRate7ByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		PbPremiumRate7 persisted = pbPremiumRate7Dao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updatePbPremiumRate7(PbPremiumRate7 pbPremiumRate7) throws SystemException, Exception {

		if (pbPremiumRate7 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = pbPremiumRate7Dao.update(pbPremiumRate7);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(pbPremiumRate7);
		return result;
	}

	@Override
	public Result insertPbPremiumRate7(PbPremiumRate7 pbPremiumRate7) throws SystemException, Exception {

		if (pbPremiumRate7 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = pbPremiumRate7Dao.insert(pbPremiumRate7);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(pbPremiumRate7);
		return result;
	}

	@Override
	public Result removePbPremiumRate7(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		PbPremiumRate7 persisted = pbPremiumRate7Dao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = pbPremiumRate7Dao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public PbPremiumRate7Dao getPbPremiumRate7Dao() {
		return pbPremiumRate7Dao;
	}

	public void setPbPremiumRate7Dao(PbPremiumRate7Dao pbPremiumRate7Dao) {
		this.pbPremiumRate7Dao = pbPremiumRate7Dao;
	}
}
