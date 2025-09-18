package com.tlg.msSqlMob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.ApplicantForEndorseCheckVo;
import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.ApplicantEndorseDao;
import com.tlg.msSqlMob.entity.ApplicantEndorse;
import com.tlg.msSqlMob.service.ApplicantEndorseService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ApplicantEndorseServiceImpl implements ApplicantEndorseService{

	private ApplicantEndorseDao applicantEndorseDao;

	@Override
	public int countApplicantEndorse(Map params) throws SystemException, Exception {
		return applicantEndorseDao.count(params);
	}

	@Override
	public Result findApplicantEndorseByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = applicantEndorseDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<ApplicantEndorse> searchResult = applicantEndorseDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findApplicantEndorseByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<ApplicantEndorse> searchResult = applicantEndorseDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findEndorseForCheck() throws SystemException, Exception {

		Result result = new Result();
		List<ApplicantForEndorseCheckVo> searchResult = applicantEndorseDao.findEndorseForCheck();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findApplicantEndorseByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		ApplicantEndorse persisted = applicantEndorseDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateApplicantEndorse(ApplicantEndorse applicantEndorse) throws SystemException, Exception {

		if (applicantEndorse == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(applicantEndorseDao.isUnique(applicantEndorse)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = applicantEndorseDao.update(applicantEndorse);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(applicantEndorse);
		return result;
	}

	@Override
	public Result insertApplicantEndorse(ApplicantEndorse applicantEndorse) throws SystemException, Exception {

		if (applicantEndorse == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!applicantEndorseDao.isUnique(applicantEndorse)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		applicantEndorseDao.insert(applicantEndorse);
		
		if(applicantEndorseDao.isUnique(applicantEndorse)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(applicantEndorse);
		return result;
	}

	@Override
	public Result removeApplicantEndorse(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("transactionId", transactionId);
		ApplicantEndorse persisted = applicantEndorseDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = applicantEndorseDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public ApplicantEndorseDao getApplicantEndorseDao() {
		return applicantEndorseDao;
	}

	public void setApplicantEndorseDao(ApplicantEndorseDao applicantEndorseDao) {
		this.applicantEndorseDao = applicantEndorseDao;
	}
}
