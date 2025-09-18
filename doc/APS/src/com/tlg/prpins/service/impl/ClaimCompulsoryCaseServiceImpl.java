package com.tlg.prpins.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.ClaimCompulsoryCaseDao;
import com.tlg.prpins.entity.ClaimCompulsoryCase;
import com.tlg.prpins.service.ClaimCompulsoryCaseService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ClaimCompulsoryCaseServiceImpl implements ClaimCompulsoryCaseService{

	private ClaimCompulsoryCaseDao claimCompulsoryCaseDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countClaimCompulsoryCase(Map params) throws SystemException, Exception {
		return claimCompulsoryCaseDao.count(params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findClaimCompulsoryCaseByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<ClaimCompulsoryCase> searchResult = claimCompulsoryCaseDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findClaimCompulsoryCaseByUK(String oid) throws SystemException,Exception {

		if (StringUtil.isSpace(oid)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("oid", oid);
		ClaimCompulsoryCase persisted = claimCompulsoryCaseDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateClaimCompulsoryCase(ClaimCompulsoryCase claimCompulsoryCase) throws SystemException, Exception {

		if (claimCompulsoryCase == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(claimCompulsoryCaseDao.isUnique(claimCompulsoryCase)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = claimCompulsoryCaseDao.update(claimCompulsoryCase);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(claimCompulsoryCase);
		return result;
	}

	@Override
	public Result insertClaimCompulsoryCase(ClaimCompulsoryCase claimCompulsoryCase) throws SystemException, Exception {

		if (claimCompulsoryCase == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!claimCompulsoryCaseDao.isUnique(claimCompulsoryCase)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		claimCompulsoryCaseDao.insert(claimCompulsoryCase);
		
		if(claimCompulsoryCaseDao.isUnique(claimCompulsoryCase)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(claimCompulsoryCase);
		return result;
	}

	@Override
	public Result removeClaimCompulsoryCase(String oid) throws SystemException, Exception {

		if (StringUtil.isSpace(oid)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("oid", oid);
		ClaimCompulsoryCase persisted = claimCompulsoryCaseDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = claimCompulsoryCaseDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public ClaimCompulsoryCaseDao getClaimCompulsoryCaseDao() {
		return claimCompulsoryCaseDao;
	}

	public void setClaimCompulsoryCaseDao(ClaimCompulsoryCaseDao claimCompulsoryCaseDao) {
		this.claimCompulsoryCaseDao = claimCompulsoryCaseDao;
	}

}
