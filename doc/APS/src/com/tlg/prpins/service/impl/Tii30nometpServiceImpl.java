package com.tlg.prpins.service.impl;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.Tii30nometpDao;
import com.tlg.prpins.entity.Tii30nometp;
import com.tlg.prpins.service.Tii30nometpService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

/** mantis：CAR0504，處理人員：CC009，需求單編號：CAR0504 微型電動二輪車【已領牌&未領牌】資料交換作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class Tii30nometpServiceImpl implements Tii30nometpService{
	private static final Logger logger = Logger.getLogger(Tii30nometpServiceImpl.class);
	private Tii30nometpDao tii30nometpDao;
	
	@Override
	public Result removeTii30nometp(Tii30nometp entity) throws SystemException, Exception {
		if (null == entity) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = tii30nometpDao.remove(entity);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	@Override
	public Result insertTii30nometp(Tii30nometp entity) throws SystemException, Exception {
		if (entity == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		tii30nometpDao.insert(entity);
		
		if(tii30nometpDao.isUnique(entity)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(entity);
		return result;
	}

	public Tii30nometpDao getTii30nometpDao() {
		return tii30nometpDao;
	}

	public void setTii30nometpDao(Tii30nometpDao tii30nometpDao) {
		this.tii30nometpDao = tii30nometpDao;
	}

}
