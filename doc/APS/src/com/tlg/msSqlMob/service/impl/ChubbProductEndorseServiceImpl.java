package com.tlg.msSqlMob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.ChubbProductEndorseDao;
import com.tlg.msSqlMob.entity.ChubbProductEndorse;
import com.tlg.msSqlMob.service.ChubbProductEndorseService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：，處理人員：BJ016，需求單編號：  行動裝置險安達線下批單資料檔下載*/
@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ChubbProductEndorseServiceImpl implements ChubbProductEndorseService{

	private ChubbProductEndorseDao chubbProductEndorseDao;

	@SuppressWarnings("rawtypes")
	@Override
	public int countChubbProductEndorse(Map params) throws SystemException, Exception {
		return chubbProductEndorseDao.count(params);
	}

	@Override
	public Result findChubbProductEndorseByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = chubbProductEndorseDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<ChubbProductEndorse> searchResult = chubbProductEndorseDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findChubbProductEndorseByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<ChubbProductEndorse> searchResult = chubbProductEndorseDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findChubbProductEndorseByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		ChubbProductEndorse persisted = chubbProductEndorseDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateChubbProductEndorse(ChubbProductEndorse chubbProductEndorse) throws SystemException, Exception {

		if (chubbProductEndorse == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(chubbProductEndorseDao.isUnique(chubbProductEndorse)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = chubbProductEndorseDao.update(chubbProductEndorse);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(chubbProductEndorse);
		return result;
	}

	@Override
	public Result insertChubbProductEndorse(ChubbProductEndorse chubbProductEndorse) throws SystemException, Exception {

		if (chubbProductEndorse == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!chubbProductEndorseDao.isUnique(chubbProductEndorse)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		chubbProductEndorseDao.insert(chubbProductEndorse);
		
		if(chubbProductEndorseDao.isUnique(chubbProductEndorse)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(chubbProductEndorse);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result removeChubbProductEndorse(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("transactionId", transactionId);
		ChubbProductEndorse persisted = chubbProductEndorseDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = chubbProductEndorseDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public ChubbProductEndorseDao getChubbProductEndorseDao() {
		return chubbProductEndorseDao;
	}

	public void setChubbProductEndorseDao(ChubbProductEndorseDao chubbProductEndorseDao) {
		this.chubbProductEndorseDao = chubbProductEndorseDao;
	}
}
