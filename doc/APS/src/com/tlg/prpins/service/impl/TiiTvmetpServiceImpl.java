package com.tlg.prpins.service.impl;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.TiiTvmetpDao;
import com.tlg.prpins.entity.TiiTvmetp;
import com.tlg.prpins.service.TiiTvmetpService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

/** mantis：CAR0504，處理人員：CC009，需求單編號：CAR0504 微型電動二輪車【已領牌&未領牌】資料交換作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class TiiTvmetpServiceImpl implements TiiTvmetpService{
	private static final Logger logger = Logger.getLogger(TiiTvmetpServiceImpl.class);
	private TiiTvmetpDao tiiTvmetpDao;
	
	@Override
	public Result removeTiiTvmetp(TiiTvmetp entity) throws SystemException, Exception {
		if (null == entity) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = tiiTvmetpDao.remove(entity);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	@Override
	public Result insertTiiTvmetp(TiiTvmetp entity) throws SystemException, Exception {
		if (entity == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		tiiTvmetpDao.insert(entity);
		
		if(tiiTvmetpDao.isUnique(entity)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(entity);
		return result;
	}

	public TiiTvmetpDao getTiiTvmetpDao() {
		return tiiTvmetpDao;
	}

	public void setTiiTvmetpDao(TiiTvmetpDao tiiTvmetpDao) {
		this.tiiTvmetpDao = tiiTvmetpDao;
	}

}
