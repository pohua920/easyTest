package com.tlg.xchg.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.xchg.dao.NewepolicymainDao;
import com.tlg.xchg.entity.Newepolicymain;
import com.tlg.xchg.service.NewepolicymainService;

@Transactional(value="xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class NewepolicymainServiceImpl implements NewepolicymainService{

	private NewepolicymainDao newepolicymainDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result selectForFirBatchSendmail(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Newepolicymain> searchResult = newepolicymainDao.selectForFirBatchSendmail(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result insertNewepolicymain(Newepolicymain newepolicymain) throws SystemException, Exception {
		if (newepolicymain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!newepolicymainDao.isUnique(newepolicymain)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		newepolicymainDao.insert(newepolicymain);
		
		if(newepolicymainDao.isUnique(newepolicymain)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(newepolicymain);
		return result;
	}

	public NewepolicymainDao getNewepolicymainDao() {
		return newepolicymainDao;
	}

	public void setNewepolicymainDao(NewepolicymainDao newepolicymainDao) {
		this.newepolicymainDao = newepolicymainDao;
	}

}
