package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.ReinsInwardInsDataDao;
import com.tlg.prpins.entity.ReinsInwardInsData;
import com.tlg.prpins.service.ReinsInwardInsDataService;
import com.tlg.util.Constants;
import com.tlg.util.DateUtils;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ReinsInwardInsDataServiceImpl implements ReinsInwardInsDataService{

	private ReinsInwardInsDataDao reinsInwardInsDataDao;

	@Override
	public int countReinsInwardInsData(Map params) throws SystemException, Exception {
		return reinsInwardInsDataDao.count(params);
	}

	@Override
	public Result findReinsInwardInsDataByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = reinsInwardInsDataDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<ReinsInwardInsData> searchResult = reinsInwardInsDataDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findReinsInwardInsDataByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<ReinsInwardInsData> searchResult = reinsInwardInsDataDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findReinsInwardInsDataByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		ReinsInwardInsData persisted = reinsInwardInsDataDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateReinsInwardInsData(ReinsInwardInsData reinsInwardInsData) throws SystemException, Exception {

		if (reinsInwardInsData == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		reinsInwardInsData.setModifyDate(DateUtils.getADSysDateTimeString());
		boolean status = reinsInwardInsDataDao.update(reinsInwardInsData);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(reinsInwardInsData);
		return result;
	}

	@Override
	public Result insertReinsInwardInsData(ReinsInwardInsData reinsInwardInsData) throws SystemException, Exception {

		if (reinsInwardInsData == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		reinsInwardInsData.setCreateDate(DateUtils.getADSysDateTimeString());
		BigDecimal oid = reinsInwardInsDataDao.insert(reinsInwardInsData);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(reinsInwardInsData);
		return result;
	}

	@Override
	public Result removeReinsInwardInsData(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		ReinsInwardInsData persisted = reinsInwardInsDataDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = reinsInwardInsDataDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public ReinsInwardInsDataDao getReinsInwardInsDataDao() {
		return reinsInwardInsDataDao;
	}

	public void setReinsInwardInsDataDao(ReinsInwardInsDataDao reinsInwardInsDataDao) {
		this.reinsInwardInsDataDao = reinsInwardInsDataDao;
	}
}
