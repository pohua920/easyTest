package com.tlg.xchg.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.xchg.dao.LiaMiNoticeCancleDao;
import com.tlg.xchg.entity.LiaMiNoticeCancle;
import com.tlg.xchg.service.LiaMiNoticeCancleService;

@Transactional(value="xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class LiaMiNoticeCancleServiceImpl implements LiaMiNoticeCancleService{

	private LiaMiNoticeCancleDao liaMiNoticeCancleDao;
	
	@Override
	public Result insertLiaMiNoticeCancle(LiaMiNoticeCancle liaMiNoticeCancle) throws SystemException, Exception {
		if (liaMiNoticeCancle == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!liaMiNoticeCancleDao.isUnique(liaMiNoticeCancle)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		liaMiNoticeCancleDao.insert(liaMiNoticeCancle);
		
		if(liaMiNoticeCancleDao.isUnique(liaMiNoticeCancle)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(liaMiNoticeCancle);
		return result;
	}
	
	@Override
	public Result updateLiaMiNoticeCancle(LiaMiNoticeCancle liaMiNoticeCancle) throws SystemException, Exception {
		if (liaMiNoticeCancle == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = liaMiNoticeCancleDao.update(liaMiNoticeCancle);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(liaMiNoticeCancle);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result findLiaMiNoticeCancleByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<LiaMiNoticeCancle> searchResult = liaMiNoticeCancleDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public LiaMiNoticeCancleDao getLiaMiNoticeCancleDao() {
		return liaMiNoticeCancleDao;
	}

	public void setLiaMiNoticeCancleDao(LiaMiNoticeCancleDao liaMiNoticeCancleDao) {
		this.liaMiNoticeCancleDao = liaMiNoticeCancleDao;
	}
}
