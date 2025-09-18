package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PbPremcalcTmpDao;
import com.tlg.prpins.entity.PbPremcalcTmp;
import com.tlg.prpins.service.PbPremcalcTmpService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PbPremcalcTmpServiceImpl implements PbPremcalcTmpService{

	private PbPremcalcTmpDao pbPremcalcTmpDao;

	@Override
	public int countPbPremcalcTmp(Map params) throws SystemException, Exception {
		return pbPremcalcTmpDao.count(params);
	}

	@Override
	public Result findPbPremcalcTmpByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = pbPremcalcTmpDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<PbPremcalcTmp> searchResult = pbPremcalcTmpDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPbPremcalcTmpByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<PbPremcalcTmp> searchResult = pbPremcalcTmpDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPbPremcalcTmpByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		PbPremcalcTmp persisted = pbPremcalcTmpDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updatePbPremcalcTmp(PbPremcalcTmp pbPremcalcTmp) throws SystemException, Exception {

		if (pbPremcalcTmp == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = pbPremcalcTmpDao.update(pbPremcalcTmp);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(pbPremcalcTmp);
		return result;
	}

	@Override
	public Result insertPbPremcalcTmp(PbPremcalcTmp pbPremcalcTmp) throws SystemException, Exception {

		if (pbPremcalcTmp == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		pbPremcalcTmp.setDcreate(new Date());
		BigDecimal oid = pbPremcalcTmpDao.insert(pbPremcalcTmp);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(pbPremcalcTmp);
		return result;
	}

	@Override
	public Result removePbPremcalcTmp(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		PbPremcalcTmp persisted = pbPremcalcTmpDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = pbPremcalcTmpDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public PbPremcalcTmpDao getPbPremcalcTmpDao() {
		return pbPremcalcTmpDao;
	}

	public void setPbPremcalcTmpDao(PbPremcalcTmpDao pbPremcalcTmpDao) {
		this.pbPremcalcTmpDao = pbPremcalcTmpDao;
	}
}
