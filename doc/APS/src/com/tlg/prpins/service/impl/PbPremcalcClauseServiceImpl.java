package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PbPremcalcClauseDao;
import com.tlg.prpins.entity.PbPremcalcClause;
import com.tlg.prpins.service.PbPremcalcClauseService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PbPremcalcClauseServiceImpl implements PbPremcalcClauseService{

	private PbPremcalcClauseDao pbPremcalcClauseDao;

	@Override
	public int countPbPremcalcClause(Map params) throws SystemException, Exception {
		return pbPremcalcClauseDao.count(params);
	}

	@Override
	public Result findPbPremcalcClauseByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = pbPremcalcClauseDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<PbPremcalcClause> searchResult = pbPremcalcClauseDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPbPremcalcClauseByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<PbPremcalcClause> searchResult = pbPremcalcClauseDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPbPremcalcClauseByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		PbPremcalcClause persisted = pbPremcalcClauseDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updatePbPremcalcClause(PbPremcalcClause pbPremcalcClause) throws SystemException, Exception {

		if (pbPremcalcClause == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = pbPremcalcClauseDao.update(pbPremcalcClause);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(pbPremcalcClause);
		return result;
	}

	@Override
	public Result insertPbPremcalcClause(PbPremcalcClause pbPremcalcClause) throws SystemException, Exception {

		if (pbPremcalcClause == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = pbPremcalcClauseDao.insert(pbPremcalcClause);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(pbPremcalcClause);
		return result;
	}

	@Override
	public Result removePbPremcalcClause(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		PbPremcalcClause persisted = pbPremcalcClauseDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = pbPremcalcClauseDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public PbPremcalcClauseDao getPbPremcalcClauseDao() {
		return pbPremcalcClauseDao;
	}

	public void setPbPremcalcClauseDao(PbPremcalcClauseDao pbPremcalcClauseDao) {
		this.pbPremcalcClauseDao = pbPremcalcClauseDao;
	}
}
