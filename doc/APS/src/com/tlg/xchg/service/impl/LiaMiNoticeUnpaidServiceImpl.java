package com.tlg.xchg.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.xchg.dao.LiaMiNoticeUnpaidDao;
import com.tlg.xchg.entity.LiaMiNoticeUnpaid;
import com.tlg.xchg.service.LiaMiNoticeUnpaidService;

@Transactional(value="xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class LiaMiNoticeUnpaidServiceImpl implements LiaMiNoticeUnpaidService{

	private LiaMiNoticeUnpaidDao liaMiNoticeUnpaidDao;
	
	@Override
	public Result insertLiaMiNoticeUnpaid(LiaMiNoticeUnpaid liaMiNoticeUnpaid) throws SystemException, Exception {
		if (liaMiNoticeUnpaid == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!liaMiNoticeUnpaidDao.isUnique(liaMiNoticeUnpaid)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		liaMiNoticeUnpaidDao.insert(liaMiNoticeUnpaid);
		
		if(liaMiNoticeUnpaidDao.isUnique(liaMiNoticeUnpaid)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(liaMiNoticeUnpaid);
		return result;
	}
	
	@Override
	public Result updateLiaMiNoticeUnpaid(LiaMiNoticeUnpaid liaMiNoticeUnpaid) throws SystemException, Exception {
		if (liaMiNoticeUnpaid == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = liaMiNoticeUnpaidDao.update(liaMiNoticeUnpaid);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(liaMiNoticeUnpaid);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result findLiaMiNoticeUnpaidByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<LiaMiNoticeUnpaid> searchResult = liaMiNoticeUnpaidDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public LiaMiNoticeUnpaidDao getLiaMiNoticeUnpaidDao() {
		return liaMiNoticeUnpaidDao;
	}

	public void setLiaMiNoticeUnpaidDao(LiaMiNoticeUnpaidDao liaMiNoticeUnpaidDao) {
		this.liaMiNoticeUnpaidDao = liaMiNoticeUnpaidDao;
	}

}
