package com.tlg.msSqlMob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.ChubbInsuredEndorseDao;
import com.tlg.msSqlMob.entity.ChubbInsuredEndorse;
import com.tlg.msSqlMob.service.ChubbInsuredEndorseService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：，處理人員：BJ016，需求單編號：  行動裝置險安達線下批單資料檔下載*/
@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ChubbInsuredEndorseServiceImpl implements ChubbInsuredEndorseService{

	private ChubbInsuredEndorseDao chubbInsuredEndorseDao;

	@SuppressWarnings("rawtypes")
	@Override
	public int countChubbInsuredEndorse(Map params) throws SystemException, Exception {
		return chubbInsuredEndorseDao.count(params);
	}

	@Override
	public Result findChubbInsuredEndorseByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = chubbInsuredEndorseDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<ChubbInsuredEndorse> searchResult = chubbInsuredEndorseDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findChubbInsuredEndorseByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<ChubbInsuredEndorse> searchResult = chubbInsuredEndorseDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findChubbInsuredEndorseByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		ChubbInsuredEndorse persisted = chubbInsuredEndorseDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateChubbInsuredEndorse(ChubbInsuredEndorse chubbInsuredEndorse) throws SystemException, Exception {

		if (chubbInsuredEndorse == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(chubbInsuredEndorseDao.isUnique(chubbInsuredEndorse)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = chubbInsuredEndorseDao.update(chubbInsuredEndorse);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(chubbInsuredEndorse);
		return result;
	}

	@Override
	public Result insertChubbInsuredEndorse(ChubbInsuredEndorse chubbInsuredEndorse) throws SystemException, Exception {

		if (chubbInsuredEndorse == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!chubbInsuredEndorseDao.isUnique(chubbInsuredEndorse)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		chubbInsuredEndorseDao.insert(chubbInsuredEndorse);
		
		if(chubbInsuredEndorseDao.isUnique(chubbInsuredEndorse)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(chubbInsuredEndorse);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result removeChubbInsuredEndorse(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("transactionId", transactionId);
		ChubbInsuredEndorse persisted = chubbInsuredEndorseDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = chubbInsuredEndorseDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public ChubbInsuredEndorseDao getChubbInsuredEndorseDao() {
		return chubbInsuredEndorseDao;
	}

	public void setChubbInsuredEndorseDao(ChubbInsuredEndorseDao chubbInsuredEndorseDao) {
		this.chubbInsuredEndorseDao = chubbInsuredEndorseDao;
	}
}
