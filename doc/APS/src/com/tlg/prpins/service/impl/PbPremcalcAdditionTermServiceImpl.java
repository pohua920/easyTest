package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PbPremcalcAdditionTermDao;
import com.tlg.prpins.entity.PbPremcalcAdditionTerm;
import com.tlg.prpins.service.PbPremcalcAdditionTermService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PbPremcalcAdditionTermServiceImpl implements PbPremcalcAdditionTermService{

	private PbPremcalcAdditionTermDao pbPremcalcAdditionTermDao;

	@Override
	public int countPbPremcalcAdditionTerm(Map params) throws SystemException, Exception {
		return pbPremcalcAdditionTermDao.count(params);
	}

	@Override
	public Result findPbPremcalcAdditionTermByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = pbPremcalcAdditionTermDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<PbPremcalcAdditionTerm> searchResult = pbPremcalcAdditionTermDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPbPremcalcAdditionTermByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<PbPremcalcAdditionTerm> searchResult = pbPremcalcAdditionTermDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPbPremcalcAdditionTermByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		PbPremcalcAdditionTerm persisted = pbPremcalcAdditionTermDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updatePbPremcalcAdditionTerm(PbPremcalcAdditionTerm pbPremcalcAdditionTerm) throws SystemException, Exception {

		if (pbPremcalcAdditionTerm == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = pbPremcalcAdditionTermDao.update(pbPremcalcAdditionTerm);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(pbPremcalcAdditionTerm);
		return result;
	}

	@Override
	public Result insertPbPremcalcAdditionTerm(PbPremcalcAdditionTerm pbPremcalcAdditionTerm) throws SystemException, Exception {

		if (pbPremcalcAdditionTerm == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = pbPremcalcAdditionTermDao.insert(pbPremcalcAdditionTerm);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(pbPremcalcAdditionTerm);
		return result;
	}

	@Override
	public Result removePbPremcalcAdditionTerm(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		PbPremcalcAdditionTerm persisted = pbPremcalcAdditionTermDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = pbPremcalcAdditionTermDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public PbPremcalcAdditionTermDao getPbPremcalcAdditionTermDao() {
		return pbPremcalcAdditionTermDao;
	}

	public void setPbPremcalcAdditionTermDao(PbPremcalcAdditionTermDao pbPremcalcAdditionTermDao) {
		this.pbPremcalcAdditionTermDao = pbPremcalcAdditionTermDao;
	}
}
