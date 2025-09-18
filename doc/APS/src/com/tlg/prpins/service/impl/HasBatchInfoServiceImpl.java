package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.HasBatchInfoDao;
import com.tlg.prpins.entity.HasBatchInfo;
import com.tlg.prpins.service.HasBatchInfoService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class HasBatchInfoServiceImpl implements HasBatchInfoService{

	private HasBatchInfoDao hasBatchInfoDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findHasBatchInfoByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<HasBatchInfo> searchResult = hasBatchInfoDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result findHasBatchInfoByUK(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if(!params.containsKey("prgId")) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		HasBatchInfo hasBatchInfo = hasBatchInfoDao.findByUK(params);
		if (null == hasBatchInfo) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(hasBatchInfo);
		}
		return result;
	}
	
	

	public HasBatchInfoDao getHasBatchInfoDao() {
		return hasBatchInfoDao;
	}

	public void setHasBatchInfoDao(HasBatchInfoDao hasBatchInfoDao) {
		this.hasBatchInfoDao = hasBatchInfoDao;
	}

}
