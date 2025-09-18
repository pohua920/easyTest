package com.tlg.msSqlMob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.ChubbCustomerEndorseDao;
import com.tlg.msSqlMob.entity.ChubbCustomerEndorse;
import com.tlg.msSqlMob.service.ChubbCustomerEndorseService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：，處理人員：BJ016，需求單編號：  行動裝置險安達線下批單資料檔下載*/
@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ChubbCustomerEndorseServiceImpl implements ChubbCustomerEndorseService{

	private ChubbCustomerEndorseDao chubbCustomerEndorseDao;

	@SuppressWarnings("rawtypes")
	@Override
	public int countChubbCustomerEndorse(Map params) throws SystemException, Exception {
		return chubbCustomerEndorseDao.count(params);
	}

	@Override
	public Result findChubbCustomerEndorseByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = chubbCustomerEndorseDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<ChubbCustomerEndorse> searchResult = chubbCustomerEndorseDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findChubbCustomerEndorseByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<ChubbCustomerEndorse> searchResult = chubbCustomerEndorseDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findChubbCustomerEndorseByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		ChubbCustomerEndorse persisted = chubbCustomerEndorseDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateChubbCustomerEndorse(ChubbCustomerEndorse chubbCustomerEndorse) throws SystemException, Exception {

		if (chubbCustomerEndorse == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(chubbCustomerEndorseDao.isUnique(chubbCustomerEndorse)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = chubbCustomerEndorseDao.update(chubbCustomerEndorse);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(chubbCustomerEndorse);
		return result;
	}

	@Override
	public Result insertChubbCustomerEndorse(ChubbCustomerEndorse chubbCustomerEndorse) throws SystemException, Exception {

		if (chubbCustomerEndorse == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!chubbCustomerEndorseDao.isUnique(chubbCustomerEndorse)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		chubbCustomerEndorseDao.insert(chubbCustomerEndorse);
		
		if(chubbCustomerEndorseDao.isUnique(chubbCustomerEndorse)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(chubbCustomerEndorse);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result removeChubbCustomerEndorse(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("transactionId", transactionId);
		ChubbCustomerEndorse persisted = chubbCustomerEndorseDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = chubbCustomerEndorseDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public ChubbCustomerEndorseDao getChubbCustomerEndorseDao() {
		return chubbCustomerEndorseDao;
	}

	public void setChubbCustomerEndorseDao(ChubbCustomerEndorseDao chubbCustomerEndorseDao) {
		this.chubbCustomerEndorseDao = chubbCustomerEndorseDao;
	}
}
