package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PbPremcalcTmpdtlDao;
import com.tlg.prpins.entity.PbPremcalcTmpdtl;
import com.tlg.prpins.service.PbPremcalcTmpdtlService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PbPremcalcTmpdtlServiceImpl implements PbPremcalcTmpdtlService{

	private PbPremcalcTmpdtlDao pbPremcalcTmpdtlDao;

	@Override
	public int countPbPremcalcTmpdtl(Map params) throws SystemException, Exception {
		return pbPremcalcTmpdtlDao.count(params);
	}

	@Override
	public Result findPbPremcalcTmpdtlByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = pbPremcalcTmpdtlDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<PbPremcalcTmpdtl> searchResult = pbPremcalcTmpdtlDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPbPremcalcTmpdtlByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<PbPremcalcTmpdtl> searchResult = pbPremcalcTmpdtlDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPbPremcalcTmpdtlByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		PbPremcalcTmpdtl persisted = pbPremcalcTmpdtlDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updatePbPremcalcTmpdtl(PbPremcalcTmpdtl pbPremcalcTmpdtl) throws SystemException, Exception {

		if (pbPremcalcTmpdtl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = pbPremcalcTmpdtlDao.update(pbPremcalcTmpdtl);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(pbPremcalcTmpdtl);
		return result;
	}

	@Override
	public Result insertPbPremcalcTmpdtl(PbPremcalcTmpdtl pbPremcalcTmpdtl) throws SystemException, Exception {

		if (pbPremcalcTmpdtl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = pbPremcalcTmpdtlDao.insert(pbPremcalcTmpdtl);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(pbPremcalcTmpdtl);
		return result;
	}

	@Override
	public Result removePbPremcalcTmpdtl(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		PbPremcalcTmpdtl persisted = pbPremcalcTmpdtlDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = pbPremcalcTmpdtlDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public PbPremcalcTmpdtlDao getPbPremcalcTmpdtlDao() {
		return pbPremcalcTmpdtlDao;
	}

	public void setPbPremcalcTmpdtlDao(PbPremcalcTmpdtlDao pbPremcalcTmpdtlDao) {
		this.pbPremcalcTmpdtlDao = pbPremcalcTmpdtlDao;
	}
}
