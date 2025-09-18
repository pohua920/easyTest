package com.tlg.xchg.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.xchg.dao.UndwrtIpb902iDao;
import com.tlg.xchg.entity.UndwrtIpb902i;
import com.tlg.xchg.service.UndwrtIpb902iService;

@Transactional(value="xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class UndwrtIpb902iServiceImpl implements UndwrtIpb902iService{

	private UndwrtIpb902iDao undwrtIpb902iDao;

	@Override
	public int countUndwrtIpb902i(Map params) throws SystemException, Exception {
		return undwrtIpb902iDao.count(params);
	}

	@Override
	public Result findUndwrtIpb902iByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = undwrtIpb902iDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<UndwrtIpb902i> searchResult = undwrtIpb902iDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findUndwrtIpb902iByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<UndwrtIpb902i> searchResult = undwrtIpb902iDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findUndwrtIpb902iByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		UndwrtIpb902i persisted = undwrtIpb902iDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateUndwrtIpb902i(UndwrtIpb902i undwrtIpb902i) throws SystemException, Exception {

		if (undwrtIpb902i == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = undwrtIpb902iDao.update(undwrtIpb902i);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(undwrtIpb902i);
		return result;
	}
	
	@Override
	public Result updateUndwrtIpb902iSendtime(UndwrtIpb902i undwrtIpb902i) throws SystemException, Exception {

		if (undwrtIpb902i == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = undwrtIpb902iDao.updateSendtime(undwrtIpb902i);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(undwrtIpb902i);
		return result;
	}

	@Override
	public Result insertUndwrtIpb902i(UndwrtIpb902i undwrtIpb902i) throws SystemException, Exception {

		if (undwrtIpb902i == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = undwrtIpb902iDao.insert(undwrtIpb902i);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(undwrtIpb902i);
		return result;
	}

	@Override
	public Result removeUndwrtIpb902i(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		UndwrtIpb902i persisted = undwrtIpb902iDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = undwrtIpb902iDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	@Override
	public Result findUnsendUndwrtIpb902iData() throws SystemException, Exception {
		Result result = new Result();
		List<UndwrtIpb902i> searchResult = undwrtIpb902iDao.selectByDistinctIdno();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}


	public UndwrtIpb902iDao getUndwrtIpb902iDao() {
		return undwrtIpb902iDao;
	}

	public void setUndwrtIpb902iDao(UndwrtIpb902iDao undwrtIpb902iDao) {
		this.undwrtIpb902iDao = undwrtIpb902iDao;
	}


}
