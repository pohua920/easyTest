package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.ReinsInwardClaimInsDataDao;
import com.tlg.prpins.entity.ReinsInwardClaimInsData;
import com.tlg.prpins.service.ReinsInwardClaimInsDataService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ReinsInwardClaimInsDataServiceImpl implements ReinsInwardClaimInsDataService{

	private ReinsInwardClaimInsDataDao reinsInwardClaimInsData;

	@Override
	public int countReinsInwardClaimInsData(Map params) throws SystemException, Exception {
		return reinsInwardClaimInsData.count(params);
	}

	@Override
	public Result findReinsInwardClaimInsDataByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = reinsInwardClaimInsData.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<ReinsInwardClaimInsData> searchResult = reinsInwardClaimInsData.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findReinsInwardClaimInsDataByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<ReinsInwardClaimInsData> searchResult = reinsInwardClaimInsData.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findReinsInwardClaimInsDataByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		ReinsInwardClaimInsData persisted = reinsInwardClaimInsData.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateReinsInwardClaimInsData(ReinsInwardClaimInsData reinsInwardInsData) throws SystemException, Exception {

		if (reinsInwardInsData == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		reinsInwardInsData.setModifyDate(new Date());
		boolean status = reinsInwardClaimInsData.update(reinsInwardInsData);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(reinsInwardInsData);
		return result;
	}

	@Override
	public Result insertReinsInwardClaimInsData(ReinsInwardClaimInsData reinsInwardInsData) throws SystemException, Exception {

		if (reinsInwardInsData == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		reinsInwardInsData.setCreateDate(new Date());
		BigDecimal oid = reinsInwardClaimInsData.insert(reinsInwardInsData);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(reinsInwardInsData);
		return result;
	}

	@Override
	public Result removeReinsInwardClaimInsData(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		ReinsInwardClaimInsData persisted = reinsInwardClaimInsData.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = reinsInwardClaimInsData.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public ReinsInwardClaimInsDataDao getReinsInwardClaimInsDataDao() {
		return reinsInwardClaimInsData;
	}

	public void setReinsInwardClaimInsDataDao(ReinsInwardClaimInsDataDao reinsInwardClaimInsData) {
		this.reinsInwardClaimInsData = reinsInwardClaimInsData;
	}
}
