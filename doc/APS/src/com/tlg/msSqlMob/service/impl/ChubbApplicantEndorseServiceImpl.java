package com.tlg.msSqlMob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.ChubbApplicantEndorseDao;
import com.tlg.msSqlMob.entity.ChubbApplicantEndorse;
import com.tlg.msSqlMob.service.ChubbApplicantEndorseService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：，處理人員：BJ016，需求單編號：  行動裝置險安達線下批單資料檔下載*/
@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ChubbApplicantEndorseServiceImpl implements ChubbApplicantEndorseService{

	private ChubbApplicantEndorseDao chubbApplicantEndorseDao;

	@SuppressWarnings("rawtypes")
	@Override
	public int countChubbApplicantEndorse(Map params) throws SystemException, Exception {
		return chubbApplicantEndorseDao.count(params);
	}

	@Override
	public Result findChubbApplicantEndorseByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = chubbApplicantEndorseDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<ChubbApplicantEndorse> searchResult = chubbApplicantEndorseDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findChubbApplicantEndorseByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<ChubbApplicantEndorse> searchResult = chubbApplicantEndorseDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findChubbApplicantEndorseByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		ChubbApplicantEndorse persisted = chubbApplicantEndorseDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateChubbApplicantEndorse(ChubbApplicantEndorse chubbApplicantEndorse) throws SystemException, Exception {

		if (chubbApplicantEndorse == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(chubbApplicantEndorseDao.isUnique(chubbApplicantEndorse)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = chubbApplicantEndorseDao.update(chubbApplicantEndorse);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(chubbApplicantEndorse);
		return result;
	}

	@Override
	public Result insertChubbApplicantEndorse(ChubbApplicantEndorse chubbApplicantEndorse) throws SystemException, Exception {

		if (chubbApplicantEndorse == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!chubbApplicantEndorseDao.isUnique(chubbApplicantEndorse)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		chubbApplicantEndorseDao.insert(chubbApplicantEndorse);
		
		if(chubbApplicantEndorseDao.isUnique(chubbApplicantEndorse)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(chubbApplicantEndorse);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result removeChubbApplicantEndorse(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("transactionId", transactionId);
		ChubbApplicantEndorse persisted = chubbApplicantEndorseDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = chubbApplicantEndorseDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public ChubbApplicantEndorseDao getChubbApplicantEndorseDao() {
		return chubbApplicantEndorseDao;
	}

	public void setChubbApplicantEndorseDao(ChubbApplicantEndorseDao chubbApplicantEndorseDao) {
		this.chubbApplicantEndorseDao = chubbApplicantEndorseDao;
	}
}
