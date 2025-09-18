package com.tlg.xchg.service.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.xchg.dao.MiEpolicysmsDao;
import com.tlg.xchg.entity.MiEpolicysms;
import com.tlg.xchg.service.MiEpolicysmsService;

@Transactional(value="xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class MiEpolicysmsServiceImpl implements MiEpolicysmsService{

	private MiEpolicysmsDao miEpolicysmsDao;
	
	@Override
	public Result insertMiEpolicysms(MiEpolicysms miEpolicysms) throws SystemException, Exception {
		if (miEpolicysms == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!miEpolicysmsDao.isUnique(miEpolicysms)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		miEpolicysmsDao.insert(miEpolicysms);
		
		if(miEpolicysmsDao.isUnique(miEpolicysms)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(miEpolicysms);
		return result;
	}

	public MiEpolicysmsDao getMiEpolicysmsDao() {
		return miEpolicysmsDao;
	}

	public void setMiEpolicysmsDao(MiEpolicysmsDao miEpolicysmsDao) {
		this.miEpolicysmsDao = miEpolicysmsDao;
	}

}
