package com.tlg.prpins.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.ClaimCompulsoryApplicantDao;
import com.tlg.prpins.entity.ClaimCompulsoryApplicant;
import com.tlg.prpins.service.ClaimCompulsoryApplicantService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ClaimCompulsoryApplicantServiceImpl implements ClaimCompulsoryApplicantService{

	private ClaimCompulsoryApplicantDao claimCompulsoryApplicantDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countClaimCompulsoryApplicant(Map params) throws SystemException, Exception {
		return claimCompulsoryApplicantDao.count(params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findClaimCompulsoryApplicantByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<ClaimCompulsoryApplicant> searchResult = claimCompulsoryApplicantDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findClaimCompulsoryApplicantByUK(String oid) throws SystemException,Exception {

		if (StringUtil.isSpace(oid)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("oid", oid);
		ClaimCompulsoryApplicant persisted = claimCompulsoryApplicantDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateClaimCompulsoryApplicant(ClaimCompulsoryApplicant claimCompulsoryApplicant) throws SystemException, Exception {

		if (claimCompulsoryApplicant == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(claimCompulsoryApplicantDao.isUnique(claimCompulsoryApplicant)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = claimCompulsoryApplicantDao.update(claimCompulsoryApplicant);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(claimCompulsoryApplicant);
		return result;
	}

	@Override
	public Result insertClaimCompulsoryApplicant(ClaimCompulsoryApplicant claimCompulsoryApplicant) throws SystemException, Exception {

		if (claimCompulsoryApplicant == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!claimCompulsoryApplicantDao.isUnique(claimCompulsoryApplicant)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		claimCompulsoryApplicantDao.insert(claimCompulsoryApplicant);
		
		if(claimCompulsoryApplicantDao.isUnique(claimCompulsoryApplicant)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(claimCompulsoryApplicant);
		return result;
	}

	@Override
	public Result removeClaimCompulsoryApplicant(String oid) throws SystemException, Exception {

		if (StringUtil.isSpace(oid)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("oid", oid);
		ClaimCompulsoryApplicant persisted = claimCompulsoryApplicantDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = claimCompulsoryApplicantDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public ClaimCompulsoryApplicantDao getClaimCompulsoryApplicantDao() {
		return claimCompulsoryApplicantDao;
	}

	public void setClaimCompulsoryApplicantDao(ClaimCompulsoryApplicantDao claimCompulsoryApplicantDao) {
		this.claimCompulsoryApplicantDao = claimCompulsoryApplicantDao;
	}

}
