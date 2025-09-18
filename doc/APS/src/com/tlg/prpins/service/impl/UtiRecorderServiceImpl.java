package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.UtiRecorderDao;
import com.tlg.prpins.entity.UtiRecorder;
import com.tlg.prpins.service.UtiRecorderService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class UtiRecorderServiceImpl implements UtiRecorderService{

	private UtiRecorderDao utiRecorderDao;

	@Override
	public int countUtiRecorder(Map params) throws SystemException, Exception {
		return utiRecorderDao.count(params);
	}

	@Override
	public Result findUtiRecorderByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = utiRecorderDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<UtiRecorder> searchResult = utiRecorderDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findUtiRecorderByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<UtiRecorder> searchResult = utiRecorderDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}


	@Override
	public Result updateUtiRecorder(UtiRecorder utiRecorder) throws SystemException, Exception {

		if (utiRecorder == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = utiRecorderDao.update(utiRecorder);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(utiRecorder);
		return result;
	}

	@Override
	public Result insertUtiRecorder(UtiRecorder utiRecorder) throws SystemException, Exception {

		if (utiRecorder == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = utiRecorderDao.insert(utiRecorder);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(utiRecorder);
		return result;
	}




	public UtiRecorderDao getUtiRecorderDao() {
		return utiRecorderDao;
	}

	public void setUtiRecorderDao(UtiRecorderDao utiRecorderDao) {
		this.utiRecorderDao = utiRecorderDao;
	}
}
