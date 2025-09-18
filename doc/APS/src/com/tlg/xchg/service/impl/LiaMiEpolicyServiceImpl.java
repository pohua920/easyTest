package com.tlg.xchg.service.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.xchg.dao.LiaMiEpolicyDao;
import com.tlg.xchg.entity.LiaMiEpolicy;
import com.tlg.xchg.service.LiaMiEpolicyService;

@Transactional(value="xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class LiaMiEpolicyServiceImpl implements LiaMiEpolicyService{

	private LiaMiEpolicyDao liaMiEpolicyDao;
	
	@Override
	public Result insertLiaMiEpolicy(LiaMiEpolicy liaMiEpolicy) throws SystemException, Exception {
		if (liaMiEpolicy == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!liaMiEpolicyDao.isUnique(liaMiEpolicy)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		liaMiEpolicyDao.insert(liaMiEpolicy);
		
		if(liaMiEpolicyDao.isUnique(liaMiEpolicy)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(liaMiEpolicy);
		return result;
	}

	public LiaMiEpolicyDao getLiaMiEpolicyDao() {
		return liaMiEpolicyDao;
	}

	public void setLiaMiEpolicyDao(LiaMiEpolicyDao liaMiEpolicyDao) {
		this.liaMiEpolicyDao = liaMiEpolicyDao;
	}

}
