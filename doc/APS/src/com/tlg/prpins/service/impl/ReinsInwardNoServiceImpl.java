package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.ReinsInwardNoDao;
import com.tlg.prpins.entity.ReinsInwardNo;
import com.tlg.prpins.service.ReinsInwardNoService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ReinsInwardNoServiceImpl implements ReinsInwardNoService{

	private ReinsInwardNoDao reinsInwardNoDao;

	@Override
	public int countReinsInwardNo(Map params) throws SystemException, Exception {
		return reinsInwardNoDao.count(params);
	}

	@Override
	public Result findReinsInwardNoByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = reinsInwardNoDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<ReinsInwardNo> searchResult = reinsInwardNoDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findReinsInwardNoByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<ReinsInwardNo> searchResult = reinsInwardNoDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findReinsInwardNoByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		ReinsInwardNo persisted = reinsInwardNoDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateReinsInwardNo(ReinsInwardNo reinsInwardNo) throws SystemException, Exception {

		if (reinsInwardNo == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = reinsInwardNoDao.update(reinsInwardNo);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(reinsInwardNo);
		return result;
	}

	@Override
	public Result insertReinsInwardNo(ReinsInwardNo reinsInwardNo) throws SystemException, Exception {

		if (reinsInwardNo == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		BigDecimal oid = reinsInwardNoDao.insert(reinsInwardNo);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(reinsInwardNo);
		return result;
	}

	@Override
	public Result removeReinsInwardNo(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		ReinsInwardNo persisted = reinsInwardNoDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = reinsInwardNoDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public ReinsInwardNoDao getReinsInwardNoDao() {
		return reinsInwardNoDao;
	}

	public void setReinsInwardNoDao(ReinsInwardNoDao reinsInwardNoDao) {
		this.reinsInwardNoDao = reinsInwardNoDao;
	}
}
