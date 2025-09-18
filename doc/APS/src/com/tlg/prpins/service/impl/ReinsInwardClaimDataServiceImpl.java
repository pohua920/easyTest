package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.ReinsInwardClaimDataDao;
import com.tlg.prpins.entity.ReinsInwardClaimData;
import com.tlg.prpins.service.ReinsInwardClaimDataService;
import com.tlg.util.Constants;
import com.tlg.util.DateUtils;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ReinsInwardClaimDataServiceImpl implements ReinsInwardClaimDataService{

	private ReinsInwardClaimDataDao reinsInwardClaimData;

	@Override
	public int countReinsInwardClaimData(Map params) throws SystemException, Exception {
		return reinsInwardClaimData.count(params);
	}


	@Override
	public Result findReinsInwardClaimDataByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = reinsInwardClaimData.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<ReinsInwardClaimData> searchResult = reinsInwardClaimData.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findReinsInwardClaimDataByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<ReinsInwardClaimData> searchResult = reinsInwardClaimData.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findReinsInwardClaimDataByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		ReinsInwardClaimData persisted = reinsInwardClaimData.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateReinsInwardClaimData(ReinsInwardClaimData reinsInwardMainData) throws SystemException, Exception {

		if (reinsInwardMainData == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		reinsInwardMainData.setModifyDate(new Date());
		boolean status = reinsInwardClaimData.update(reinsInwardMainData);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(reinsInwardMainData);
		return result;
	}

	@Override
	public Result insertReinsInwardClaimData(ReinsInwardClaimData reinsInwardMainData) throws SystemException, Exception {

		if (reinsInwardMainData == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		reinsInwardMainData.setCreateDate(new Date());
		BigDecimal oid = reinsInwardClaimData.insert(reinsInwardMainData);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(reinsInwardMainData);
		return result;
	}

	@Override
	public Result removeReinsInwardClaimData(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		ReinsInwardClaimData persisted = reinsInwardClaimData.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = reinsInwardClaimData.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public ReinsInwardClaimDataDao getReinsInwardClaimDataDao() {
		return reinsInwardClaimData;
	}

	public void setReinsInwardClaimDataDao(ReinsInwardClaimDataDao reinsInwardClaimData) {
		this.reinsInwardClaimData = reinsInwardClaimData;
	}
}
