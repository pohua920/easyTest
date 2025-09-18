package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.ReinsInwardMainDataDao;
import com.tlg.prpins.entity.ReinsInwardMainData;
import com.tlg.prpins.service.ReinsInwardMainDataService;
import com.tlg.util.Constants;
import com.tlg.util.DateUtils;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ReinsInwardMainDataServiceImpl implements ReinsInwardMainDataService{

	private ReinsInwardMainDataDao reinsInwardMainDataDao;

	@Override
	public int countReinsInwardMainData(Map params) throws SystemException, Exception {
		return reinsInwardMainDataDao.count(params);
	}
	
	@Override
	public int queryCurrentEndorseNo(Map params) throws SystemException, Exception {
		return reinsInwardMainDataDao.queryCurrentEndorseNo(params);
	}

	@Override
	public Result findReinsInwardMainDataByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = reinsInwardMainDataDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<ReinsInwardMainData> searchResult = reinsInwardMainDataDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findReinsInwardMainDataByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<ReinsInwardMainData> searchResult = reinsInwardMainDataDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findReinsInwardMainDataByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		ReinsInwardMainData persisted = reinsInwardMainDataDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateReinsInwardMainData(ReinsInwardMainData reinsInwardMainData) throws SystemException, Exception {

		if (reinsInwardMainData == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		reinsInwardMainData.setModifyDate(DateUtils.getADSysDateTimeString());
		boolean status = reinsInwardMainDataDao.update(reinsInwardMainData);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(reinsInwardMainData);
		return result;
	}

	@Override
	public Result insertReinsInwardMainData(ReinsInwardMainData reinsInwardMainData) throws SystemException, Exception {

		if (reinsInwardMainData == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		reinsInwardMainData.setCreateDate(DateUtils.getADSysDateTimeString());
		BigDecimal oid = reinsInwardMainDataDao.insert(reinsInwardMainData);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(reinsInwardMainData);
		return result;
	}

	@Override
	public Result removeReinsInwardMainData(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		ReinsInwardMainData persisted = reinsInwardMainDataDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = reinsInwardMainDataDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public ReinsInwardMainDataDao getReinsInwardMainDataDao() {
		return reinsInwardMainDataDao;
	}

	public void setReinsInwardMainDataDao(ReinsInwardMainDataDao reinsInwardMainDataDao) {
		this.reinsInwardMainDataDao = reinsInwardMainDataDao;
	}
}
