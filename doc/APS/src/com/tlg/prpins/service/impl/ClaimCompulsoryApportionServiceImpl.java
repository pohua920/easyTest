package com.tlg.prpins.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.ClaimCompulsoryApportionDao;
import com.tlg.prpins.entity.ClaimCompulsoryApportion;
import com.tlg.prpins.service.ClaimCompulsoryApportionService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ClaimCompulsoryApportionServiceImpl implements ClaimCompulsoryApportionService{

	private ClaimCompulsoryApportionDao claimCompulsoryApportionDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countClaimCompulsoryApportion(Map params) throws SystemException, Exception {
		return claimCompulsoryApportionDao.count(params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findClaimCompulsoryApportionByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<ClaimCompulsoryApportion> searchResult = claimCompulsoryApportionDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findClaimCompulsoryApportionByUK(String oid) throws SystemException,Exception {

		if (StringUtil.isSpace(oid)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("oid", oid);
		ClaimCompulsoryApportion persisted = claimCompulsoryApportionDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateClaimCompulsoryApportion(ClaimCompulsoryApportion claimCompulsoryApportion) throws SystemException, Exception {

		if (claimCompulsoryApportion == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(claimCompulsoryApportionDao.isUnique(claimCompulsoryApportion)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = claimCompulsoryApportionDao.update(claimCompulsoryApportion);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(claimCompulsoryApportion);
		return result;
	}

	@Override
	public Result insertClaimCompulsoryApportion(ClaimCompulsoryApportion claimCompulsoryApportion) throws SystemException, Exception {

		if (claimCompulsoryApportion == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!claimCompulsoryApportionDao.isUnique(claimCompulsoryApportion)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		claimCompulsoryApportionDao.insert(claimCompulsoryApportion);
		
		if(claimCompulsoryApportionDao.isUnique(claimCompulsoryApportion)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(claimCompulsoryApportion);
		return result;
	}

	@Override
	public Result removeClaimCompulsoryApportion(String oid) throws SystemException, Exception {

		if (StringUtil.isSpace(oid)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("oid", oid);
		ClaimCompulsoryApportion persisted = claimCompulsoryApportionDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = claimCompulsoryApportionDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public ClaimCompulsoryApportionDao getClaimCompulsoryApportionDao() {
		return claimCompulsoryApportionDao;
	}

	public void setClaimCompulsoryApportionDao(ClaimCompulsoryApportionDao claimCompulsoryApportionDao) {
		this.claimCompulsoryApportionDao = claimCompulsoryApportionDao;
	}

}
